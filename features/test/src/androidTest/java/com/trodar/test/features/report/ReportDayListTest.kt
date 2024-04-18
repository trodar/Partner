package com.trodar.test.features.report

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.trodar.common.recources.K
import com.trodar.common_impl.di.RepositoryModule
import com.trodar.domain.preach.usecase.SimplePreachUseCase
import com.trodar.report.domain.PreachDeleteUseCase
import com.trodar.report.domain.PreachOfMonthUseCase
import com.trodar.report.presentation.DayList
import com.trodar.report.presentation.DayListViewModel
import com.trodar.report.presentation.DayScreen
import com.trodar.room.PreachRepository
import com.trodar.room.SimplePreachRepository
import com.trodar.room.di.PreachDataModule
import com.trodar.room.preach.PreachDbEntity
import com.trodar.room.simplepreach.SimplePreachDbEntity
import com.trodar.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(PreachDataModule::class, RepositoryModule::class)
@MediumTest
class ReportDayListTest: BaseTest() {

    @Inject
    lateinit var deleteUseCase: PreachDeleteUseCase

    @Inject
    lateinit var preachOfMonthUseCase: PreachOfMonthUseCase

    @Inject
    lateinit var simplePreachUseCase: SimplePreachUseCase

    @Inject
    lateinit var preachRepository: PreachRepository

    @Inject
    lateinit var simplePreachRepository: SimplePreachRepository
    @OptIn(ExperimentalMaterial3Api::class)
    override fun setUp() {
        super.setUp()

        coEvery { preachRepository.deletePreach(any()) }
        coEvery { simplePreachRepository.getSimplePreachItem("202312") } returns  getSimplePreachItemFlow()
        coEvery { preachRepository.getPreachesOfMonth(any()) } returns getPreachListFlow()

        val dayListViewModel = DayListViewModel(
            getDayScreen(),
            deleteUseCase,
            preachOfMonthUseCase,
            simplePreachUseCase
        )
        composeRule.setContent {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            DayList(
                arrayOf(""),
                NavHostController(InstrumentationRegistry.getInstrumentation().targetContext),
                scrollBehavior,
                dayListViewModel
            ) { _ -> }
        }
    }

    @Test
    fun itemVisibleAndClickableTest() = run {
        composeRule.onNodeWithTag(K.ReportTag.dayItem + "1").assertIsDisplayed()
        composeRule.onNodeWithTag(K.ReportTag.dayItem + "1").assertHasClickAction()
        composeRule.onNodeWithTag(K.ReportTag.dayItem + "2").assertIsDisplayed()
        composeRule.onNodeWithTag(K.ReportTag.dayItem + "2").assertHasClickAction()
        composeRule.onNodeWithTag(K.ReportTag.dayItem + "3").assertIsDisplayed()
        composeRule.onNodeWithTag(K.ReportTag.dayItem + "3").assertHasClickAction()
        composeRule.onNodeWithTag(K.ReportTag.dayItem + "3").performClick()
    }
}

fun getSimplePreachItemFlow() = MutableStateFlow(
    SimplePreachDbEntity(1, true, 0, Date())
)

fun getDayScreen() = DayScreen("2023", "12")

fun getPreachListFlow() = MutableStateFlow(
    listOf(
        PreachDbEntity(
            1,
            123,
            1,
            0,
            0,
            0,
            "first line"
            ,
            Date()
        ),
        PreachDbEntity(
            2,
            110,
            1,
            0,
            0,
            0,
            "second line"
            ,
            Date()
        ),
        PreachDbEntity(
            3,
            65,
            1,
            0,
            0,
            0,
            "thirty line"
            ,
            Date()
        ),
    )
)
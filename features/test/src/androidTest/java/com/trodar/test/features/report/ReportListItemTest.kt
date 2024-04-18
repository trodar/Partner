package com.trodar.test.features.report

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.trodar.common.recources.K
import com.trodar.common_impl.di.RepositoryModule
import com.trodar.report.domain.ListPreachUseCase
import com.trodar.report.domain.ReportStateUseCase
import com.trodar.report.presentation.ReportScreen
import com.trodar.report.presentation.ReportViewModel
import com.trodar.room.PreachRepository
import com.trodar.room.SimplePreachRepository
import com.trodar.room.di.PreachDataModule
import com.trodar.room.preach.PreachMonthPeriodTuple
import com.trodar.room.preach.RoomPreachYearsPeriodTuple
import com.trodar.room.simplepreach.SimplePreachDbEntity
import com.trodar.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(PreachDataModule::class, RepositoryModule::class)
@MediumTest
class ReportListItemTest : BaseTest() {

    private val yearToppleFlow =
        MutableStateFlow(
            RoomPreachYearsPeriodTuple(
                13723,
                1,
                0,
                0,
                0
            )
        )

    private val monthSimplePeriodList = getSimpleListFlow()
    private val monthPreachPeriodList = getMonthListFlow()

    @Inject
    lateinit var preachRepository: PreachRepository

    @Inject
    lateinit var simplePreachRepository: SimplePreachRepository

    @Inject
    lateinit var listPreachUseCase: ListPreachUseCase

    @Inject
    lateinit var stateUseCase: ReportStateUseCase

    @Inject
    lateinit var dispatcher: CoroutineDispatcher

    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    override fun setUp() {
        super.setUp()

        coEvery {
            preachRepository.getYearPeriodList(
                any(),
                any()
            )
        } returns yearToppleFlow andThen yearToppleFlow
        coEvery {
            simplePreachRepository.getMonthsPeriodList(
                any(),
                any()
            )
        } returns monthSimplePeriodList
        coEvery {
            preachRepository.getMonthsPeriodList(any(), any())
        } returns monthPreachPeriodList

        coEvery {
            preachRepository.observeYearListExpanded()
        } returns MutableStateFlow(setOf("2024 - 2023"))

        coEvery { preachRepository.getSumHoursYearPeriod(any(), any()) } returns 112

        coEvery { preachRepository.getFirstDate() } returns getDate()


        val reportViewModel = ReportViewModel(
            listPreachUseCase,
            stateUseCase,
            dispatcher
        )

        composeRule.setContent {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            ReportScreen(reportViewModel, scrollBehavior) { _, _ -> }
        }
    }

    @Test
    fun testTest() = run {
        step("check year items visible and clickable") {
            composeRule.onNodeWithTag(K.ReportTag.yearItem + "0").assertIsDisplayed()
            composeRule.onNodeWithTag(K.ReportTag.yearItem + "0").assertHasClickAction()
            composeRule.onNodeWithTag(K.ReportTag.yearItem + "5").assertIsDisplayed()
            composeRule.onNodeWithTag(K.ReportTag.yearItem + "5").assertHasClickAction()
        }
        step("click on first year item and check child items") {
            composeRule.onNodeWithTag(K.ReportTag.yearItem + "1").performClick()
            composeRule.onNodeWithTag(K.ReportTag.monthItem + "0").assertIsDisplayed()
            composeRule.onNodeWithTag(K.ReportTag.monthItem + "0").assertHasClickAction()
            composeRule.onNodeWithTag(K.ReportTag.monthItem + "2").assertIsDisplayed()
            composeRule.onNodeWithTag(K.ReportTag.monthItem + "2").assertHasClickAction()
            composeRule.onNodeWithTag(K.ReportTag.monthItem + "2").performClick()
        }
    }
}
private fun getDate(stringDate: String = "2018-01-06"): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.parse(stringDate)!!
}
fun getSimpleListFlow() = MutableStateFlow(
    listOf(
        SimplePreachDbEntity(
            1,
            true,
            0,
            getDate("2023-12-01")
        )
    )
)

fun getMonthListFlow() = MutableStateFlow(
    listOf(
        PreachMonthPeriodTuple(
            "2023",
            "10",
            12,
            1,
            0,
            0,
            0
        ),
        PreachMonthPeriodTuple(
            "2023",
            "11",
            12,
            1,
            0,
            0,
            0
        ),
        PreachMonthPeriodTuple(
            "2023",
            "12",
            12,
            1,
            0,
            0,
            0
        ),
    )
)
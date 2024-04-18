package com.trodar.test.features.preach

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.trodar.common.Core
import com.trodar.common.Repository
import com.trodar.common.recources.K
import com.trodar.common_impl.di.RepositoryModule
import com.trodar.common_impl.entity.PreachCountDataEntity
import com.trodar.domain.preach.entity.SimplePreachEntity
import com.trodar.domain.preach.usecase.SimplePreachUseCase
import com.trodar.preach.domain.PreachInsertUseCase
import com.trodar.preach.domain.PreachService
import com.trodar.preach.presentation.PreachScreen
import com.trodar.preach.presentation.PreachViewModel
import com.trodar.room.SimplePreachRepository
import com.trodar.room.di.PreachDataModule
import com.trodar.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(PreachDataModule::class, RepositoryModule::class)
@MediumTest
class PreachTimeItemTest : BaseTest() {
    private val countEntityEmpty = PreachCountDataEntity()
    private val simpleEntityEmpty = SimplePreachEntity()

    private val countEntityFlow = MutableStateFlow(countEntityEmpty)
    private val simpleEntityFlow = MutableStateFlow(simpleEntityEmpty)

    @Inject
    lateinit var preachSharedRepository: Repository

    @Inject
    lateinit var simplePreachRepository: SimplePreachRepository

    @Inject
    lateinit var preachInsertUseCase: PreachInsertUseCase

    @Inject
    lateinit var simplePreachUseCase: SimplePreachUseCase


    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    override fun setUp() {
        super.setUp()

        coEvery { preachSharedRepository.preachData() } returns countEntityFlow
        coEvery { simplePreachRepository.getSimplePreachItem(any()) } returns simpleEntityFlow
        every { Core.preachServiceClass } returns PreachService::class.java

        val preachViewModel = PreachViewModel(
            ApplicationProvider.getApplicationContext(),
            preachSharedRepository,
            preachInsertUseCase,
            simplePreachUseCase
        )


        composeRule.setContent {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            PreachScreen(preachViewModel, scrollBehavior)
        }
    }

    @Test
    fun testControlsDisplayedAndDisabled() = run {

        step("time button and edit displayed and disabled") {
            composeRule.onNodeWithTag(K.PreachTag.timeButtonPlus).assertIsDisplayed()
            composeRule.onNodeWithTag(K.PreachTag.timeButtonMinus).assertIsDisplayed()
            composeRule.onNodeWithTag(K.PreachTag.timeTextTime).assertIsDisplayed()
            composeRule.onNodeWithTag(K.PreachTag.timeButtonPlus).assertIsNotEnabled()
            composeRule.onNodeWithTag(K.PreachTag.timeButtonMinus).assertIsNotEnabled()
        }
        step("service button displayed and disabled") {
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonStart).assertIsDisplayed()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonStart).assertIsEnabled()

            composeRule.onNodeWithTag(K.PreachTag.serviceEditTestDescription).assertDoesNotExist()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonCancel).assertDoesNotExist()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonSave).assertDoesNotExist()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonPause).assertDoesNotExist()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonFinish).assertDoesNotExist()
        }
        step("item button and edit displayed and disabled") {
            K.PreachTag.preachItemList.forEachIndexed { index, item ->
                if (index == 4) return@forEachIndexed
                composeRule.onNodeWithTag(item.plusButton).assertIsDisplayed()
                composeRule.onNodeWithTag(item.minusButton).assertIsDisplayed()
                composeRule.onNodeWithTag(item.minusButton).assertIsDisplayed()

                composeRule.onNodeWithTag(item.plusButton).assertIsNotEnabled()
                composeRule.onNodeWithTag(item.minusButton).assertIsNotEnabled()
            }

        }
        step("simple preach checkbox and buttons checked") {
            composeRule.onNodeWithTag(K.PreachTag.preachItemList[4].plusButton).assertIsDisplayed()
            composeRule.onNodeWithTag(K.PreachTag.preachItemList[4].minusButton).assertIsDisplayed()
            composeRule.onNodeWithTag(K.PreachTag.simpleCheckBox).assertIsDisplayed()
            composeRule.onNodeWithTag(K.PreachTag.preachItemList[4].plusButton).assertIsEnabled()
            composeRule.onNodeWithTag(K.PreachTag.preachItemList[4].minusButton).assertIsEnabled()
            composeRule.onNodeWithTag(K.PreachTag.simpleCheckBox).assertIsEnabled()
        }
    }

    @Test
    fun testClickButtonAndChangeEnabled() = run {

        step("Click Start Button and check Pause, finish button ") {
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonStart).performClick()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonStart).assertDoesNotExist()

            composeRule.onNodeWithTag(K.PreachTag.serviceButtonPause).assertIsDisplayed()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonFinish).assertIsDisplayed()

        }
        step("item button and edit displayed and enabled") {
            K.PreachTag.preachItemList.forEachIndexed { index, item ->
                composeRule.onNodeWithTag(item.plusButton).assertIsDisplayed()
                composeRule.onNodeWithTag(item.minusButton).assertIsDisplayed()
                if (index != 4)
                    composeRule.onNodeWithTag(item.textNumber).assertIsDisplayed()

                composeRule.onNodeWithTag(item.plusButton).assertIsEnabled()
                composeRule.onNodeWithTag(item.minusButton).assertIsEnabled()
            }
        }
        step("service button click finish") {

            composeRule.onNodeWithTag(K.PreachTag.serviceButtonFinish).performClick()

            composeRule.onNodeWithTag(K.PreachTag.serviceEditTestDescription).assertIsDisplayed()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonSave).assertIsDisplayed()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonCancel).assertIsDisplayed()
        }

        step("service fill edit text and click cancel") {
            composeRule.onNodeWithTag(K.PreachTag.serviceEditTestDescription)
                .performTextInput("test edit text")
            composeRule.onNodeWithTag(K.PreachTag.serviceEditTestDescription)
                .assertTextContains("test edit text")

            composeRule.onNodeWithTag(K.PreachTag.serviceButtonCancel).performClick()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonStart).assertIsDisplayed()

        }
    }

    @Test
    fun clickPlusMinusButtonsAndCheckBoxItemRows() = run {
        step("Click Start Button") {
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonStart).performClick()
            composeRule.onNodeWithTag(K.PreachTag.serviceButtonStart).assertDoesNotExist()
        }

        step("Click plus and minus buttons ") {

            K.PreachTag.preachItemList.forEach {
                composeRule.onNodeWithTag(it.plusButton).performClick()
                composeRule.onNodeWithTag(it.minusButton).performClick()
            }
        }
        step("Click and check checkBox") {
            composeRule.onNodeWithTag(K.PreachTag.simpleCheckBox).performClick()
            composeRule.onNodeWithTag(K.PreachTag.simpleCheckBox).assertIsToggleable().assertIsOff()
            simpleEntityFlow.value = simpleEntityFlow.value.copy(preached = true)
            composeRule.onNodeWithTag(K.PreachTag.simpleCheckBox).assertIsToggleable().assertIsOn()
            simpleEntityFlow.value = simpleEntityFlow.value.copy(preached = false)
            composeRule.onNodeWithTag(K.PreachTag.simpleCheckBox).assertIsToggleable().assertIsOff()
        }

        step("change count of item") {
            countEntityFlow.value =
                countEntityFlow.value.copy(publication = 1, study = 2, returns = 3, video = 5)
            composeRule.onNodeWithTag(K.PreachTag.preachItemList[0].textNumber)
                .assertTextContains("1")
            composeRule.onNodeWithTag(K.PreachTag.preachItemList[3].textNumber)
                .assertTextContains("2")
            composeRule.onNodeWithTag(K.PreachTag.preachItemList[1].textNumber)
                .assertTextContains("3")
            composeRule.onNodeWithTag(K.PreachTag.preachItemList[2].textNumber)
                .assertTextContains("5")
        }

    }
}



































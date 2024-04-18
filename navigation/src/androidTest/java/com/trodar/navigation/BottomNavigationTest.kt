package com.trodar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.trodar.common.constants.PreachConstant.TIME_OUT
import com.trodar.common.recources.K
import com.trodar.navigation.presentation.domain.BottomParameters
import com.trodar.navigation.presentation.route.BottomTitle
import com.trodar.navigation.presentation.screen.FakeScreen
import com.trodar.navigation.presentation.screen.MainScreen
import com.trodar.navigation.presentation.screen.ScreenProvider
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BottomNavigationTest: TestCase(
kaspressoBuilder = Kaspresso.Builder.withComposeSupport(
customize = {
    flakySafetyParams = FlakySafetyParams.custom(timeoutMs = TIME_OUT, intervalMs = 1000)
},
lateComposeCustomize = { composeBuilder ->
    composeBuilder.semanticsBehaviorInterceptors = mutableListOf()
}))
{


    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {

        composeRule.setContent {
            MainScreen(
                NavHostController(LocalContext.current),
                bottomScreenProvider = BottomScreenProviderTest.getBottomScreens()
            )
        }
    }

    @Test
    fun testClickAllButtonsOnBottomNavigation(
    ) = run {
        step("Click on Territory Button") {
            onComposeScreen<BottomNavigation>(composeRule) {
                composeRule.onNodeWithTag(K.NavigationTag.navigation_bar_item_column[1]).assertIsDisplayed()
                composeRule.onNodeWithTag(K.NavigationTag.navigation_bar_item_column[1]).performClick()
            }
        }
        step("Click on Preach Button") {
            onComposeScreen<BottomNavigation>(composeRule) {
                composeRule.onNodeWithTag(K.NavigationTag.navigation_bar_item_column[0]).assertIsDisplayed()
                composeRule.onNodeWithTag(K.NavigationTag.navigation_bar_item_column[0]).performClick()
            }
        }
        step("Click on Notepad Button") {
            onComposeScreen<BottomNavigation>(composeRule) {
                composeRule.onNodeWithTag(K.NavigationTag.navigation_bar_item_column[2]).assertIsDisplayed()
                composeRule.onNodeWithTag(K.NavigationTag.navigation_bar_item_column[2]).performClick()
            }
        }
        step("Click on Report Button") {
            onComposeScreen<BottomNavigation>(composeRule) {
                composeRule.onNodeWithTag(K.NavigationTag.navigation_bar_item_column[3]).assertIsDisplayed()
                composeRule.onNodeWithTag(K.NavigationTag.navigation_bar_item_column[3]).performClick()
            }
        }
    }
}

class BottomScreenProviderTest(override val screen: @Composable (BottomParameters) -> Unit): ScreenProvider {

    companion object {
        fun getBottomScreens(): Map<String, ScreenProvider> = mapOf(
            BottomTitle.PREACH.toString() to BottomScreenProviderTest { FakeScreen() },
            BottomTitle.NOTEPAD.toString() to BottomScreenProviderTest { FakeScreen() },
            BottomTitle.REPORT.toString() to BottomScreenProviderTest { FakeScreen() },
            BottomTitle.TERRITORY.toString() to BottomScreenProviderTest { FakeScreen()  },
        )
    }
}
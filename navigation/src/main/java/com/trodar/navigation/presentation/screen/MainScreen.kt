package com.trodar.navigation.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.trodar.navigation.presentation.navigation.BottomNav
import com.trodar.navigation.presentation.navigation.NavGraph
import com.trodar.navigation.presentation.route.BottomRoute


@Composable
fun MainScreen(
    navController: NavHostController,
    bottomScreenProvider: Map<String, ScreenProvider>,
) {
    val currentSelectedScreen by navController.currentScreenAsState()

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            BottomNav(
                modifier = Modifier,
                navController = navController,
                currentSelectedScreen = currentSelectedScreen,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        )
        {
            NavGraph(
                navHostController = navController,
                bottomScreenProvider = bottomScreenProvider,
                paddingValues = paddingValues,
                onShowBottomBar = {

                }
            )
        }
    }
}

@Stable
@Composable
private fun NavController.currentScreenAsState(): State<BottomRoute> {
    val selectedItem = remember { mutableStateOf<BottomRoute>(BottomRoute.Preach) }
    DisposableEffect(key1 = this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == BottomRoute.Preach.route } -> {
                    selectedItem.value = BottomRoute.Preach
                }

                destination.hierarchy.any { it.route == BottomRoute.Territory.route } -> {
                    selectedItem.value = BottomRoute.Territory
                }

                destination.hierarchy.any { it.route == BottomRoute.Report.route } -> {
                    selectedItem.value = BottomRoute.Report
                }

                destination.hierarchy.any { it.route == BottomRoute.Notepad.route } -> {
                    selectedItem.value = BottomRoute.Notepad
                }
            }
        }
        addOnDestinationChangedListener(listener)
        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }
    return selectedItem
}

package com.trodar.navigation.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.trodar.navigation.presentation.domain.BottomParameters
import com.trodar.navigation.presentation.route.BottomRoute
import com.trodar.navigation.presentation.route.NotepadRoute
import com.trodar.navigation.presentation.route.PreachRoute
import com.trodar.navigation.presentation.route.ReportRoute
import com.trodar.navigation.presentation.route.TerritoryRoute
import com.trodar.navigation.presentation.screen.ScreenProvider

@Composable
fun NavGraph(
    navHostController: NavHostController,
    bottomScreenProvider: Map<String, ScreenProvider>,
    paddingValues: PaddingValues,
    onShowBottomBar: (Boolean) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomRoute.Preach.route
    ) {
        //navHostController.navigatorProvider.addNavigator("dialog", ComposeNavigator()) //
        addPreachRoute( bottomScreenProvider, onShowBottomBar)
        addTerritoryRoute( bottomScreenProvider, onShowBottomBar)
        addNotepadRoute(paddingValues, bottomScreenProvider, onShowBottomBar)
        addReportRoute(paddingValues, bottomScreenProvider, onShowBottomBar)
    }
}

fun NavGraphBuilder.addPreachRoute(
    bottomScreenProvider: Map<String, ScreenProvider>,
    onShowBottomBar: (Boolean) -> Unit
) {
    onShowBottomBar(true)
    navigation(
        route = BottomRoute.Preach.route,
        startDestination = PreachRoute.Preach.route
    ) {

        //navHostController.navigatorProvider.addNavigator(ComposeNavigator())// for test only
        composable(route = PreachRoute.Preach.route) {
            bottomScreenProvider[BottomRoute.Preach.route]?.screen?.invoke(BottomParameters())
        }
    }
}

fun NavGraphBuilder.addTerritoryRoute(
    bottomScreenProvider: Map<String, ScreenProvider>,
    onShowBottomBar: (Boolean) -> Unit,
) {
    onShowBottomBar(true)
    navigation(
        route = BottomRoute.Territory.route,
        startDestination = TerritoryRoute.fake.route
    ) {
        //navHostController.navigatorProvider.addNavigator(ComposeNavigator())// for test only
        composable(route = TerritoryRoute.fake.route) {
            bottomScreenProvider[BottomRoute.Territory.route]?.screen?.invoke(BottomParameters())
        }
    }
}

fun NavGraphBuilder.addNotepadRoute(
    paddingValues: PaddingValues,
    bottomScreenProvider: Map<String, ScreenProvider>,
    onShowBottomBar: (Boolean) -> Unit,
) {
    navigation(
        route = BottomRoute.Notepad.route,
        startDestination = NotepadRoute.List.route
    ) {
       //navHostController.navigatorProvider.addNavigator(ComposeNavigator())// for test only
        composable(route = NotepadRoute.List.route) {
            onShowBottomBar(true)
            bottomScreenProvider[BottomRoute.Notepad.route]?.screen?.invoke(BottomParameters(
                paddingValues = paddingValues

            ))
        }
        composable(route = NotepadRoute.Edit.route,
            arguments = listOf(
                navArgument(NotepadRoute.ID) {
                    type = NavType.IntType
                    defaultValue = 0
                },
            )
        ) {
            onShowBottomBar(false)
            val id = it.arguments?.getInt(NotepadRoute.ID)
            bottomScreenProvider[NotepadRoute.Edit.route]?.screen?.invoke(BottomParameters(
                args = arrayOf(id.toString()),
                paddingValues = paddingValues
            ))
        }
    }
}

fun NavGraphBuilder.addReportRoute(
    paddingValues: PaddingValues,
    bottomScreenProvider: Map<String, ScreenProvider>,
    onShowBottomBar: (Boolean) -> Unit,
) {
    onShowBottomBar(true)
    //navHostController.navigatorProvider.addNavigator(ComposeNavigator())// for test only
    navigation(
        route = BottomRoute.Report.route,
        startDestination = ReportRoute.YearMonth.route
    ) {

        composable(route = ReportRoute.YearMonth.route) {
            bottomScreenProvider[BottomRoute.Report.route]?.screen?.invoke(
                BottomParameters(
                    paddingValues = paddingValues
                )
            )
        }
        composable(route = ReportRoute.Days.route,
            arguments = listOf(
                navArgument(ReportRoute.DAYS_YEAR_KEY) {
                    type = NavType.StringType
                },
                navArgument(ReportRoute.DAYS_MONTH_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            val year = it.arguments?.getString(ReportRoute.DAYS_YEAR_KEY).toString()
            val month = it.arguments?.getString(ReportRoute.DAYS_MONTH_KEY).toString()
            bottomScreenProvider[ReportRoute.Days.route]?.screen?.invoke(
                BottomParameters(
                    args =  arrayOf(
                        year,
                        month,
                    ),
                )
            )
        }
        composable(route = ReportRoute.EditDay.route,
            arguments = listOf(
                navArgument(ReportRoute.EDIT_DAY_ID) {
                    type = NavType.IntType
                }
            )
        )
        {
            val id = it.arguments?.getInt(ReportRoute.EDIT_DAY_ID)
            bottomScreenProvider[ReportRoute.EditDay.route]?.screen?.invoke(
                BottomParameters(
                    args = arrayOf(id.toString())
                )
            )
        }
    }
}















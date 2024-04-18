package com.trodar.jwpartner.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.notepad.presentation.NotepadEdit
import com.example.notepad.presentation.NotepadScreen
import com.trodar.navigation.presentation.domain.BottomParameters
import com.trodar.navigation.presentation.route.BottomRoute
import com.trodar.navigation.presentation.route.NotepadRoute
import com.trodar.navigation.presentation.route.ReportRoute
import com.trodar.navigation.presentation.screen.FakeScreen
import com.trodar.navigation.presentation.screen.ScreenProvider
import com.trodar.preach.presentation.PreachScreen
import com.trodar.report.presentation.DayList
import com.trodar.report.presentation.PreachEdit
import com.trodar.report.presentation.ReportScreen

class BottomScreenProvider(override val screen: @Composable (bottomParameters: BottomParameters) -> Unit) :
    ScreenProvider {

    companion object {
        @OptIn(ExperimentalMaterial3Api::class)
        fun getBottomScreens(
            navController: NavHostController,
            scrollBehavior: TopAppBarScrollBehavior
        ): Map<String, ScreenProvider> = mapOf(
            BottomRoute.Preach.route to BottomScreenProvider {
                PreachScreen(
                    scrollBehavior = scrollBehavior
                )
            },
            BottomRoute.Report.route to BottomScreenProvider {
                ReportScreen(
                    scrollBehavior = scrollBehavior,
                    //paddingValues = it.paddingValues,
                    onMonthClick = { year, month ->
                        navController.navigate(ReportRoute.Days.passYearAndMonth(year, month))
                    }
                )
            },
            ReportRoute.Days.route to BottomScreenProvider {
                DayList(
                    args = it.args,
                    navController = navController,
                    scrollBehavior = scrollBehavior,
                ) { id ->
                    navController.navigate(ReportRoute.EditDay.passPreachId(id))
                }
            },
            ReportRoute.EditDay.route to BottomScreenProvider {
                if (it.args.isEmpty()) return@BottomScreenProvider
                val id = it.args[0].toInt()
                PreachEdit(preachId = id) {
                    navController.popBackStack()
                }
            },
            BottomRoute.Territory.route to BottomScreenProvider { FakeScreen() }, //arrayOf(BottomTitle.TERRITORY.name)
            BottomRoute.Notepad.route to BottomScreenProvider {
                NotepadScreen(
                    scrollBehavior = scrollBehavior,
                    //paddingValues = it.paddingValues,
                    onClick = { id -> navController.navigate(NotepadRoute.Edit.passId(id)) },
                    onNewClick = { navController.navigate(NotepadRoute.Edit.passId()) },
                )
            },
            NotepadRoute.Edit.route to BottomScreenProvider { bottom ->
                val id = bottom.args[0].toInt()
                NotepadEdit(
                    id,
                    navController = navController,
                    paddingValues = bottom.paddingValues
                    )
            },
        )
    }
}
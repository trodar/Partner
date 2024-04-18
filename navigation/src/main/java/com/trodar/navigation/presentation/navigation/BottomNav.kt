package com.trodar.navigation.presentation.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.trodar.common.recources.K
import com.trodar.navigation.presentation.route.BottomRoute
import com.trodar.navigation.presentation.route.getBottomItems

@Composable
fun BottomNav(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    currentSelectedScreen: BottomRoute,
) {
    val navigationBarItem = remember { getBottomItems() }
    val context = LocalContext.current
    val color = MaterialTheme.colorScheme.primary

    NavigationBar(
        modifier = modifier
            .drawBehind {
            val strokeWidth = 2 * density
            drawLine(
                color,
                Offset(0f, 0f),
                Offset(size.width, 0f), 
                strokeWidth
            )
        },
        tonalElevation = 1.dp,
        containerColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        navigationBarItem.forEachIndexed {index, item ->
            NavigationBarItem(
                modifier = Modifier.testTag(K.NavigationTag.navigation_bar_item_column[index]),
                selected = currentSelectedScreen == item,
                onClick = { navController.navigateToRootScreen(item) },
                icon = {
                    Icon(
                        modifier = Modifier.size(if (currentSelectedScreen == item) 32.dp else 48.dp),
                        painter = painterResource(id = item.iconId),
                        contentDescription = context.getString(item.title.id)
                    )
                },
                label = { Text(text = context.getString(item.title.id)) },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

private fun NavController.navigateToRootScreen(bottomRoute: BottomRoute) {
    navigate(bottomRoute.route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
    }
}
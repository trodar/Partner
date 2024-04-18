package com.trodar.navigation.presentation.route

sealed class TerritoryRoute(val route: String) {
        data object fake: TerritoryRoute("fake_territory")
}
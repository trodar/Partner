package com.trodar.navigation.presentation.navigation

import androidx.compose.runtime.Composable
import dagger.Provides

interface ComposableScreenProvider {
    val tittle: String
    val screenFun: @Composable () -> Unit
}
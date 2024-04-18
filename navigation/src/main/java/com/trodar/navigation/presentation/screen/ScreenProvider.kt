package com.trodar.navigation.presentation.screen

import androidx.compose.runtime.Composable
import com.trodar.navigation.presentation.domain.BottomParameters

interface ScreenProvider {
    val screen: @Composable (bottomParameters: BottomParameters) -> Unit
}
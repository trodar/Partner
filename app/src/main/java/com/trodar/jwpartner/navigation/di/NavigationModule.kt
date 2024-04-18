package com.trodar.jwpartner.navigation.di

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.trodar.navigation.presentation.navigation.ComposableScreenProvider
import com.trodar.preach.presentation.PreachScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    fun getScreenList(
        preachScreen: FeatureScreen.FeaturePreachScreen
    ): List<ComposableScreenProvider> {
        return listOf(preachScreen)
    }
}

sealed class FeatureScreen(
    override val tittle: String,
    override val screenFun: @Composable () -> Unit
): ComposableScreenProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    data object FeaturePreachScreen : FeatureScreen(
        "Preach",
        { PreachScreen(
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        ) }
    )

}
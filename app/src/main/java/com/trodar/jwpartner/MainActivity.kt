package com.trodar.jwpartner

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.trodar.jwpartner.navigation.BottomScreenProvider
import com.trodar.navigation.presentation.screen.MainScreen
import com.trodar.theme.themes.JWPartnerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private fun setupOnCreate() {
        window.setResizeSoftInputMode()
    }
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setupOnCreate()
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JWPartnerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val topAppBarState = rememberTopAppBarState()
                    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
                    MainScreen(
                        navController,
                        BottomScreenProvider.getBottomScreens(navController, scrollBehavior)
                    )
                }
            }
        }
    }
}

fun Window.setResizeSoftInputMode() {
    @Suppress("DEPRECATION")
    //or android:windowSoftInputMode="adjustResize" in manifest
    setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}



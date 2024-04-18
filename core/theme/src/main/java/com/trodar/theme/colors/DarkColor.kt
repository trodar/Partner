package com.trodar.theme.colors

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val md_theme_dark_primary = Color(0xFF00344A)
val md_theme_dark_onPrimary = Color(0xFF7ED0FF)
val md_theme_dark_primaryContainer = Color(0xFF004C6A)
val md_theme_dark_onPrimaryContainer = Color(0xFFC5E7FF)
val md_theme_dark_secondary = Color(0xFFB6C9D8)
val md_theme_dark_onSecondary = Color(0xFF20333E)
val md_theme_dark_secondaryContainer = Color(0xFF374955)
val md_theme_dark_onSecondaryContainer = Color(0xFFD1E5F4)
val md_theme_dark_tertiary = Color(0xFF79D1FF)
val md_theme_dark_onTertiary = Color(0xFF003549)
val md_theme_dark_tertiaryContainer = Color(0xFF004C68)
val md_theme_dark_onTertiaryContainer = Color(0xFFC3E8FF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF191C1E)
val md_theme_dark_onBackground = Color(0xFFE1E2E5)
val md_theme_dark_surface = Color(0xFF191C1E)
val md_theme_dark_onSurface = Color(0xFFE1E2E5)
val md_theme_dark_surfaceVariant = Color(0xFF41484D)
val md_theme_dark_onSurfaceVariant = Color(0xFFC1C7CE)
val md_theme_dark_outline = Color(0xFF8B9297)
val md_theme_dark_inverseOnSurface = Color(0xFF191C1E)
val md_theme_dark_inverseSurface = Color(0xFFE1E2E5)
val md_theme_dark_inversePrimary = Color(0xFF00658B)
val md_theme_dark_surfaceTint = Color(0xFF7ED0FF)
val md_theme_dark_outlineVariant = Color(0xFF41484D)
val md_theme_dark_scrim = Color(0xFF000000)



val DarkLineBoxGradient = arrayOf(
    0.0f to Color.LightGray,
    0.5f to Color.Gray,
    1f to Color.DarkGray
)

@Composable
fun textFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    focusedIndicatorColor = Color.Black,
    unfocusedIndicatorColor = Color.DarkGray,
)
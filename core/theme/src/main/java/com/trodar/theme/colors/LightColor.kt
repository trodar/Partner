package com.trodar.theme.colors

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val md_theme_light_primary = Color(0xFF357488)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFC5E7FF)
val md_theme_light_onPrimaryContainer = Color(0xFF001E2D)
val md_theme_light_secondary = Color(0xFF4E616D)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFD1E5F4)
val md_theme_light_onSecondaryContainer = Color(0xFF0A1E28)
val md_theme_light_tertiary = Color(0xFF006689)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFCCFAFF)
val md_theme_light_onTertiaryContainer = Color(0xFF004D44)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFFFF)
val md_theme_light_onBackground = Color(0xFF191C1E)
val md_theme_light_surface = Color(0xFFFBFCFF)
val md_theme_light_onSurface = Color(0xFF191C1E)
val md_theme_light_surfaceVariant = Color(0xFFDDE3EA)
val md_theme_light_onSurfaceVariant = Color(0xFF41484D)
val md_theme_light_outline = Color(0xFF71787E)
val md_theme_light_inverseOnSurface = Color(0xFFF0F1F3)
val md_theme_light_inverseSurface = Color(0xFF2E3133)
val md_theme_light_inversePrimary = Color(0xFF7ED0FF)
val md_theme_light_surfaceTint = Color(0xFF00658B)
val md_theme_light_outlineVariant = Color(0xFFC1C7CE)
val md_theme_light_scrim = Color(0xFF000000)


val LightLineBoxGradient = arrayOf(
    0.0f to Color.LightGray,
    0.5f to Color.Gray,
    1f to Color.DarkGray
)
@Composable
fun outlineButtonColor(): ButtonColors {
    return ButtonDefaults.outlinedButtonColors(
        containerColor = Color.Transparent,
        contentColor = if (isSystemInDarkTheme()) md_theme_dark_onBackground else md_theme_light_onBackground,
        disabledContainerColor = if (isSystemInDarkTheme()) md_theme_dark_onPrimary else md_theme_light_onPrimary,
        disabledContentColor = if (isSystemInDarkTheme()) md_theme_dark_primary else md_theme_light_primary,
    )
}
@Composable
fun iconButtonColor(): IconButtonColors {
    return androidx.compose.material3.IconButtonDefaults.iconButtonColors(
        containerColor = Color.Transparent,
        contentColor = if (isSystemInDarkTheme()) Color.White else md_theme_light_onPrimary,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = if (isSystemInDarkTheme()) Color.Black else Color.Black,
    )
}

//@Composable
//fun buttonColors() {
//
//    ButtonDefaults.buttonColors(
//        containerColor = if (isSystemInDarkTheme()) md_theme_dark_primary else md_theme_light_primary,
//        contentColor = if (isSystemInDarkTheme()) md_theme_dark_onPrimary else md_theme_light_onPrimary,
//        disabledContainerColor = if (isSystemInDarkTheme()) md_theme_dark_primary else md_theme_light_primary,
//        disabledContentColor = if (isSystemInDarkTheme()) md_theme_dark_onPrimary else md_theme_light_onPrimary,
//    )
//}

package com.dwhr.myexplorer.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.dwhr.myexplorer.data.model.AppThemeMode

private val LightColors = lightColorScheme(
    primary = ExplorerOrange,
    onPrimary = ExplorerBlack,
    primaryContainer = ExplorerAmber,
    onPrimaryContainer = ExplorerBlack,
    secondary = ColorTokens.secondaryLight,
    onSecondary = ExplorerBlack,
    background = ExplorerCream,
    onBackground = ExplorerDarkGray,
    surface = ColorTokens.surfaceLight,
    onSurface = ExplorerDarkGray,
    surfaceVariant = ExplorerSand,
    onSurfaceVariant = ExplorerSlate,
)

private val DarkColors = darkColorScheme(
    primary = ExplorerOrange,
    onPrimary = ExplorerBlack,
    primaryContainer = ColorTokens.primaryContainerDark,
    onPrimaryContainer = ExplorerCream,
    secondary = ExplorerAmber,
    onSecondary = ExplorerBlack,
    background = ExplorerDarkGray,
    onBackground = ExplorerCream,
    surface = ExplorerSurfaceDark,
    onSurface = ExplorerCream,
    surfaceVariant = ExplorerSlate,
    onSurfaceVariant = ExplorerSand,
)

private val AmoledColors = darkColorScheme(
    primary = ExplorerOrange,
    onPrimary = ExplorerBlack,
    primaryContainer = ColorTokens.primaryContainerAmoled,
    onPrimaryContainer = ExplorerCream,
    secondary = ExplorerAmber,
    onSecondary = ExplorerBlack,
    background = ExplorerBlack,
    onBackground = ExplorerCream,
    surface = ExplorerBlack,
    onSurface = ExplorerCream,
    surfaceVariant = ExplorerDarkGray,
    onSurfaceVariant = ExplorerSand,
)

@Composable
fun MyExplorerTheme(
    themeMode: AppThemeMode,
    dynamicColor: Boolean,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val view = LocalView.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && themeMode == AppThemeMode.LIGHT ->
            dynamicLightColorScheme(context)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && themeMode != AppThemeMode.LIGHT ->
            dynamicDarkColorScheme(context)
        themeMode == AppThemeMode.LIGHT -> LightColors
        themeMode == AppThemeMode.AMOLED -> AmoledColors
        else -> DarkColors
    }

    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = colorScheme.background.toArgb()
        window.navigationBarColor = colorScheme.background.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
            themeMode == AppThemeMode.LIGHT
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

private object ColorTokens {
    val secondaryLight = Color(0xFFB85C00)
    val surfaceLight = Color(0xFFFFFBF7)
    val primaryContainerDark = Color(0xFF4A2800)
    val primaryContainerAmoled = Color(0xFF3A1E00)
}

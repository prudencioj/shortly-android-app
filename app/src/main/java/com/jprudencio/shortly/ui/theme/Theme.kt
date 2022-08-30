package com.jprudencio.shortly.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColors(
    primary = Cyan,
    secondary = Red,
    primaryVariant = DarkViolet,
    background = OffWhite,
    onBackground = White
)

private val LightColorScheme = lightColors(
    primary = Cyan,
    secondary = Red,
    primaryVariant = DarkViolet,
    background = OffWhite,
    onBackground = GrayishViolet,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ShortlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(colorScheme.background, darkIcons = !darkTheme)
    }

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content
    )
}
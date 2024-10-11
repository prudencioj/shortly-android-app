package com.jprudencio.shortly.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColors(
    primary = Cyan,
    secondary = Red,
    primaryVariant = DarkViolet,
    background = OffWhite,
    onBackground = GrayishViolet,
    error = Red,
    secondaryVariant = VeryDarkViolet,
    onPrimary = White,
    onSecondary = Gray,
    surface = White,
    onSurface = LightGray
)

private val LightColorScheme = lightColors(
    primary = Cyan,
    secondary = Red,
    primaryVariant = DarkViolet,
    background = OffWhite,
    onBackground = GrayishViolet,
    error = Red,
    secondaryVariant = VeryDarkViolet,
    onPrimary = White,
    onSecondary = Gray,
    surface = White,
    onSurface = LightGray
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

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content
    )
}
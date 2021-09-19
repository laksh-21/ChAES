package com.example.chaes.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = Color(0xFF221E22),
    onPrimary = Color(0xFF1DB954),
    surface = Color(0xFF1DB954),
    background = Color(0xFFF5F5F5),
    primaryVariant = Color(0xFF0A0A0A),
    secondary = Color(0xFF1BA24B)
)

private val DarkThemeColors = darkColors(
    primary = Color(0xFF2B2B2B),
    onPrimary = Color(0xFF1DB954),
    surface = Color(0xFF1DB954),
    background = Color.Black,
    primaryVariant = Color(0xFF0A0A0A),
    secondary = Color(0xFF1BA24B)
)

@Composable
fun ChAESTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: Colors? = null,
    content: @Composable () -> Unit
) {
    val myColors = colors ?: if (isDarkTheme) DarkThemeColors else LightThemeColors

    MaterialTheme(
        colors = myColors,
        content = content,
//        typography = JetchatTypography,
//        shapes = JetchatShapes
    )
}
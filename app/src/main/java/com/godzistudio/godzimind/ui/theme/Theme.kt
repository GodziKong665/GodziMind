package com.godzistudio.godzimind.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Teal,
    onPrimary = Color.White,
    primaryContainer = TealSoft,
    onPrimaryContainer = Color(0xFF00201D),
    secondary = Indigo,
    onSecondary = Color.White,
    secondaryContainer = LavenderSoft,
    onSecondaryContainer = Color(0xFF1B1642),
    tertiary = Lavender,
    onTertiary = Color.White,
    tertiaryContainer = LavenderSoft,
    onTertiaryContainer = Color(0xFF251A45),
    background = Mist,
    onBackground = Ink,
    surface = Cloud,
    onSurface = Ink,
    surfaceVariant = TealMist,
    onSurfaceVariant = Slate,
    outline = Color(0xFF8B9A95),
    outlineVariant = Line,
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8FD8CF),
    onPrimary = Color(0xFF003731),
    primaryContainer = Color(0xFF005048),
    onPrimaryContainer = TealSoft,
    secondary = Color(0xFFBFC7F5),
    onSecondary = Color(0xFF26305B),
    secondaryContainer = Color(0xFF3B4675),
    onSecondaryContainer = Color(0xFFDDE1FF),
    tertiary = Color(0xFFD3BDF5),
    onTertiary = Color(0xFF38265A),
    tertiaryContainer = Color(0xFF4F3D73),
    onTertiaryContainer = LavenderSoft,
    background = Night,
    onBackground = NightText,
    surface = NightSurface,
    onSurface = NightText,
    surfaceVariant = NightSurfaceVariant,
    onSurfaceVariant = Color(0xFFB7C7C1),
    outline = Color(0xFF82938D),
    outlineVariant = NightLine,
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC)
)

@Composable
fun GodziMindTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = GodziTypography,
        content = content
    )
}

package com.oletob.rpncalc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RpnColorScheme = darkColorScheme(
    primary = Accent,
    onPrimary = OnAccent,
    secondary = ButtonSecondary,
    onSecondary = Color.White,
    background = AppBackground,
    onBackground = Color.White,
    surface = PanelBackground,
    onSurface = Color.White,
    surfaceVariant = ButtonNumber,
    onSurfaceVariant = Muted,
    outline = Divider
)

@Composable
fun RpnCalcTheme(content: @Composable () -> Unit) {
    // Fixed brand palette, independent of system theme — matches the legacy app's single AppTheme.
    MaterialTheme(
        colorScheme = RpnColorScheme,
        content = content
    )
}

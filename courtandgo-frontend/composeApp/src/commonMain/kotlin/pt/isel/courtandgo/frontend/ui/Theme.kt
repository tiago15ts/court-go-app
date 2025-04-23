package pt.isel.courtandgo.frontend.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Azul Marinho: #001F3F (principal)
private val NavyBlue = Color(0xFF001F3F)
private val NavyLight = Color(0xFF2C3E50)
private val NavyDark = Color(0xFF000C1A)
private val AccentGreen = Color(0xFF00B894) // Opcional: cor de destaque

val LightNavyColorScheme = lightColorScheme(
    primary = NavyBlue,
    onPrimary = Color.White,
    secondary = AccentGreen,
    onSecondary = Color.White,
    background = Color(0xFFF0F4F8),
    surface = Color(0x82AEE8),
    onSurface = Color.Black,
    error = Color(0xFFD32F2F),
    onError = Color.White
)

val DarkNavyColorScheme = darkColorScheme(
    primary = NavyLight,
    onPrimary = Color.White,
    secondary = AccentGreen,
    onSecondary = Color.Black,
    background = NavyDark,
    surface = NavyLight,
    onSurface = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black
)


@Composable
fun CourtAndGoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkNavyColorScheme else LightNavyColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

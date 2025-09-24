package com.rezazavareh7.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.rezazavareh7.designsystem.custom.JokerCustomColorPalette
import com.rezazavareh7.designsystem.custom.LocalJokerColorPalette
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette

private val DarkColorScheme =
    darkColorScheme(
        primary = JokerDarkPrimary,
        onPrimary = JokerDarkOnPrimary,
        primaryContainer = JokerDarkPrimaryContainer,
        onPrimaryContainer = JokerDarkOnPrimaryContainer,
        secondary = JokerDarkSecondary,
        onSecondary = JokerDarkOnSecondary,
        error = JokerDarkError,
        onError = JokerDarkOnError,
        errorContainer = JokerDarkErrorContainer,
        onErrorContainer = JokerDarkOnErrorContainer,
        surface = JokerDarkSurface,
        onSurface = JokerDarkOnSurface,
        onSurfaceVariant = JokerDarkOnSurfaceVariant,
        surfaceVariant = JokerDarkSurfaceVariant,
        outline = JokerDarkOutline,
        background = JokerDarkBackground,
        onBackground = JokerDarkOnBackground,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = JokerLightPrimary,
        onPrimary = JokerLightOnPrimary,
        primaryContainer = JokerLightPrimaryContainer,
        onPrimaryContainer = JokerLightOnPrimaryContainer,
        secondary = JokerLightSecondary,
        onSecondary = JokerLightOnSecondary,
        error = JokerLightError,
        onError = JokerLightOnError,
        errorContainer = JokerLightErrorContainer,
        onErrorContainer = JokerLightOnErrorContainer,
        surface = JokerLightSurface,
        onSurface = JokerLightOnSurface,
        onSurfaceVariant = JokerLightOnSurfaceVariant,
        surfaceVariant = JokerLightSurfaceVariant,
        outline = JokerLightOutline,
        background = JokerLightBackground,
        onBackground = JokerLightOnBackground,
    )

val OnLightCustomJokerColorsPalette =
    JokerCustomColorPalette(
        yellow = Color(0XFFFFC107),
    )

val OnDarkCustomJokerColorsPalette =
    JokerCustomColorPalette(
        yellow = Color(0XFFFFD54F),
    )

@Composable
fun JokerMovieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                if (darkTheme) DarkColorScheme else LightColorScheme
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    val customIconsPalette = LocalJokerIconPalette.current
    val customColorPalette =
        if (darkTheme) OnDarkCustomJokerColorsPalette else OnLightCustomJokerColorsPalette

    CompositionLocalProvider(
        LocalJokerIconPalette provides customIconsPalette,
        LocalJokerColorPalette provides customColorPalette,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = replyTypography,
            content = content,
        )
    }
}

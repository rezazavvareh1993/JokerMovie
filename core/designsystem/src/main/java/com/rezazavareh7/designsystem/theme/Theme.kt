package com.rezazavareh7.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
        outline = JokerDarkOutline,
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
        outline = JokerLightOutline,
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

    CompositionLocalProvider(
        LocalJokerIconPalette provides customIconsPalette,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = replyTypography,
            content = content,
        )
    }
}

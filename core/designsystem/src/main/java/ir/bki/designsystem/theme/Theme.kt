package ir.bki.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import ir.bki.designsystem.R
import ir.bki.designsystem.custom.LocalPalizCustomColorPalette
import ir.bki.designsystem.custom.LocalPalizIconPalette
import ir.bki.designsystem.custom.PalizCustomColorPalette
import ir.bki.designsystem.custom.PalizIconPalette

private val DarkColorScheme =
    darkColorScheme(
        primary = Purple80,
        secondary = PurpleGrey80,
        tertiary = Pink80,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40,
//    primary = HipixiLightPrimary,
//    onPrimary = HipixiLightOnPrimary,
//    primaryContainer = HipixiLightPrimaryContainer,
//    onPrimaryContainer = HipixiLightOnPrimaryContainer,
//    secondary = HipixiLightSecondary,
//    onSecondary = HipixiLightOnSecondary,
//    error = HipixiLightError,
//    onError = HipixiLightOnError,
//    errorContainer = HipixiLightErrorContainer,
//    onErrorContainer = HipixiLightOnErrorContainer,
//    surface = HipixiLightSurface,
//    onSurface = HipixiLightOnSurface,
//    inverseSurface = HipixiLightInverseSurface,
//    onSurfaceVariant = HipixiLightOnSurfaceVariant,
//    surfaceContainer = HipixiLightSurfaceContainer,
//    surfaceContainerHigh = HipixiLightSurfaceContainerHigh,
//    surfaceContainerLow = HipixiLightSurfaceContainerLow,
//    outline = HipixiLightOutline,
//    outlineVariant = HipixiLightOutlineVariant,
//    scrim = HipixiLightScrim,
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

val onLightPalizIconPalette =
    PalizIconPalette(
        icPalizSampleIcon = R.drawable.ic_sample_search,
        icPalizError = R.drawable.ic_paliz_error_light,
        icPalizBack = R.drawable.ic_sample_search,
    )

val OnLightPalizCustomColorsPalette =
    PalizCustomColorPalette(
        sampleColor = Color(color = 0xFFEAECFF),
    )

@Composable
fun PalizTheme(
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

    val customColorsPalette = OnLightPalizCustomColorsPalette
    val customIconsPalette = onLightPalizIconPalette

    CompositionLocalProvider(
        LocalPalizCustomColorPalette provides customColorsPalette,
        LocalPalizIconPalette provides customIconsPalette,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}

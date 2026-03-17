package ru.vysokov.recipesappcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val RecipesAppDarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    error = AccentColorDark,
    onError = TextPrimaryColor,
    tertiary = AccentBlueDark,
    tertiaryContainer = SliderTrackColor,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    surfaceVariant = SurfaceVariantColorDark,
    onSurfaceVariant = TextSecondaryColor,
    outline = DividerColorDark,
    onPrimary = TextPrimaryColorDark,
    onSecondary = TextSecondaryColorDark,
)
private val RecipesAppLightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    error = AccentColor,
    onError = TextPrimaryColor,
    tertiary = AccentBlue,
    tertiaryContainer = SliderTrackColorDark,
    background = BackgroundColor,
    surface = SurfaceColor,
    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = TextSecondaryColor,
    outline = DividerColor,
    onPrimary = TextPrimaryColor,
    onSecondary = TextSecondaryColor,
)

@Composable
fun RecipesAppComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> RecipesAppDarkColorScheme
        else -> RecipesAppLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = recipesAppTypography,
        content = content
    )
}
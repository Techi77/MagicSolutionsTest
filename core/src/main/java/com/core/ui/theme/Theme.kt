package com.core.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorScheme = lightColorScheme(
    background = ColorsBlue50,
    onBackground = TransparencyBlack100,
    primary = ColorsBlueA300,
    primaryContainer = ColorsBlue200,
    onPrimary = TransparencyWhite100,
    onPrimaryContainer = ColorsBlue400,
    tertiary = ColorsOrangeA200,
    onTertiary = ColorsOrange100,
    secondary = NeturalKeyColorSurfaceBright,
    secondaryContainer = NeturalKeyColorOutlineLowely,
    onSecondary = TransparencyBlack40,
    onSecondaryContainer = NeturalKeyColorOutline,
    error = Error,
    errorContainer = ErrorContainer,
    onError = Error2,
)

@Composable
fun MagicDownloaderTheme(
    content: @Composable () -> Unit,
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity?)?.window?.apply {
                statusBarColor = Color.Transparent.toArgb()
                navigationBarColor = Color.Transparent.toArgb()

                WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = lightColorScheme,
        shapes = MagicShapes,
        typography = Typography,
        content = content
    )
}
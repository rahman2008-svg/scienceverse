package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
  primary = Purple40,
  secondary = PurpleGrey40,
  tertiary = Pink40
)

private val GalaxyColorScheme = darkColorScheme(
  primary = GalaxyPrimary,
  secondary = GalaxySecondary,
  tertiary = GalaxyTertiary,
  background = GalaxyBackground,
  surface = GalaxySurface,
  onPrimary = Color(0xFF0F0F12),
  onSecondary = Color.White,
  onTertiary = Color(0xFF0F0F12),
  onBackground = GalaxyOnBackground,
  onSurface = GalaxyOnSurface
)

private val LabColorScheme = darkColorScheme(
  primary = LabPrimary,
  secondary = LabSecondary,
  tertiary = LabTertiary,
  background = LabBackground,
  surface = LabSurface,
  onPrimary = Color(0xFF090D0A),
  onSecondary = Color.White,
  onTertiary = Color(0xFF090D0A),
  onBackground = LabOnBackground,
  onSurface = LabOnSurface
)

enum class ScienceAppTheme {
  GALAXY,
  LABORATORY
}

@Composable
fun ScienceTheme(
  themeState: ScienceAppTheme = ScienceAppTheme.GALAXY,
  content: @Composable () -> Unit
) {
  val colorScheme = when (themeState) {
    ScienceAppTheme.GALAXY -> GalaxyColorScheme
    ScienceAppTheme.LABORATORY -> LabColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  ScienceTheme(themeState = ScienceAppTheme.GALAXY, content = content)
}

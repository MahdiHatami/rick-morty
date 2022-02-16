package com.metis.rickmorty.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorPrimary = Color(0xFF1976D2)
private val colorPrimaryVariant = Color(0xFF004ba0)
private val colorSecondary = Color(0xFFE1E2E1)

private val DarkColors = darkColors(
  primary = colorPrimary,
  primaryVariant = colorPrimaryVariant,
  secondary = colorSecondary
)

private val LightColors = lightColors(
  primary = colorPrimary,
  primaryVariant = colorPrimaryVariant,
  secondary = colorSecondary
)

@Composable
fun RickMortyTheme(
  darkTheme: Boolean,
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colors = if (darkTheme) DarkColors else LightColors,
    content = content

  )
}
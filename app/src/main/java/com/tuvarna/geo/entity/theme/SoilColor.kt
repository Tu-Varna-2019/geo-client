package com.tuvarna.geo.entity.theme

import androidx.compose.ui.graphics.Color

class SoilColor {
  fun mapDOMSOIToColorMapping(): Map<String, Color> {
    return mapOf(
      "Af" to Color.Red,
      "Zt" to Color.Green,
      "DS" to Color.Yellow,
      "ST" to Color.Blue,
      "RK" to Color.Gray,
      "WR" to Color.Cyan,
      "GL" to Color.Magenta,
      "Nd" to Color.LightGray,
      "I" to Color.DarkGray,
      "We" to Color.Red,
      "Be" to Color.Blue,
      "Re" to Color.Red,
    )
  }
}

package com.tuvarna.geo.entity

import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.entity.theme.SoilColor
import kotlin.math.sqrt

private const val EARTHRADIUS = 6371000

class PointEntity(var position: LatLng) {

  private val soilColor: SoilColor = SoilColor()

  fun getColorToSoilType(soilType: String): Color {
    return soilColor.mapDOMSOIToColorMapping()[soilType] ?: Color.Cyan
  }

  fun convertPositionToLocations(sqkm: Double): List<LatLng> {
    val halfSideLength = sqrt(sqkm * 1000000) / 2
    val latOffset = Math.toDegrees(halfSideLength / EARTHRADIUS)
    val lngOffset =
      Math.toDegrees(halfSideLength / (EARTHRADIUS * Math.cos(Math.toRadians(position.latitude))))

    val south = position.latitude - latOffset
    val west = position.longitude - lngOffset
    val north = position.latitude + latOffset
    val east = position.longitude + lngOffset

    return listOf(
      LatLng(north, east),
      LatLng(south, east),
      LatLng(south, west),
      LatLng(north, west),
    )
  }
}

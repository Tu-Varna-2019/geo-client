package com.tuvarna.geo.entity

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.rest_api.models.Soil
import kotlin.math.sqrt

private const val EARTHRADIUS = 6371000

class PointEntity(var position: LatLng) {

  private val soilTypeToColor: Map<String, Color> =
    mapOf(
      "Af" to Color.Red,
      "Zt" to Color.Green,
      "DS" to Color.Yellow,
      "ST" to Color.Blue,
      "RK" to Color.Gray,
      "WR" to Color.Cyan,
      "GL" to Color.Magenta,
      "ND" to Color.LightGray,
    )

  fun getColorToSoilType(soilType: String): Color {
    return soilTypeToColor[soilType]!!
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

class UserMarkerState {
  // var mapMarkersToDangers = mutableMapOf<LatLng, Soil>()
  val initialPoisition: LatLng = LatLng(0.0, 0.0)
  var markerPositions = mutableStateOf(listOf<LatLng>())
  var clickedMarker = mutableStateOf(initialPoisition)
  var topBarTitleText = mutableStateOf("Geo")
  var dangerUserChoice = mutableStateOf(DangerOptions.None)

  fun hasUserClickedMarker(): Boolean {
    return !clickedMarker.value.equals(initialPoisition)
  }

  fun isSoilAlreadyRetrieved(): Soil? {
    //  return mapMarkersToDangers[clickedMarker.value]
    return null
  }
}

enum class DangerOptions {
  None,
  Soil,
  Earthquake,
  // Details,
}

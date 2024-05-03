package com.tuvarna.geo.entity

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.rest_api.models.Earthquake
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
      "Nd" to Color.LightGray,
      "I" to Color.DarkGray,
      "We" to Color.Red,
      "Be" to Color.Blue,
      "Re" to Color.Red,
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

@SuppressLint("MutableCollectionMutableState")
class UserMarkerState {
  val initialPoisition: LatLng = LatLng(0.0, 0.0)
  var clickedMarker = mutableStateOf(initialPoisition)
  var topBarTitleText = mutableStateOf("Geo")
  var dangerUserChoice = mutableStateOf(DangerOptions.None)

  fun hasUserClickedMarker(): Boolean {
    return !clickedMarker.value.equals(initialPoisition)
  }
}

enum class DangerOptions {
  None,
  Soil,
  Earthquake,
}

sealed class DangerData {
  object NoDataYet : DangerData()

  data class SoilData(val soil: Soil) : DangerData()

  data class EarthquakeData(val earthquake: Earthquake) : DangerData()
}

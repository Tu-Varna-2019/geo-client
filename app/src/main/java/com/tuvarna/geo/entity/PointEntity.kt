package com.tuvarna.geo.entity

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.rest_api.models.Earthquake
import com.tuvarna.geo.rest_api.models.Soil
import java.io.InvalidClassException
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

@SuppressLint("MutableCollectionMutableState")
class UserMarkerState {
  var mapMarkersToDangers = mutableStateMapOf<LatLng, DangerType<*>>()
  val initialPoisition: LatLng = LatLng(0.0, 0.0)
  var clickedMarker = mutableStateOf(initialPoisition)
  var topBarTitleText = mutableStateOf("Geo")
  var dangerUserChoice = mutableStateOf(DangerOptions.None)

  fun hasUserClickedMarker(): Boolean {
    return !clickedMarker.value.equals(initialPoisition)
  }

  inline fun <reified T> isDangerAlreadyRetrieved(): T? {
    return mapMarkersToDangers[clickedMarker.value]?.getDanger() as? T
  }
}

enum class DangerOptions {
  None,
  Soil,
  Earthquake,
}

inline fun <reified T> dangerType(danger: T): DangerType<T> {
  when (danger) {
    is Soil,
    is Earthquake -> return DangerType(danger)
    else -> throw InvalidClassException("Error, data type is invalid!")
  }
}

class DangerType<T>(private val danger: T) {
  fun getDanger(): T {
    return danger
  }
}

data class SoilData(val latLng: LatLng, val soil: Soil)

data class EarthquakeData(val latLng: LatLng, val earthquake: Earthquake)

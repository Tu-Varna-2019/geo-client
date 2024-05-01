package com.tuvarna.geo.entity

import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.rest_api.models.Soil

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

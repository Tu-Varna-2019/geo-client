package com.tuvarna.geo.entity

import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.model.LatLng

class UserMarkerState {

  val initialPoisition: LatLng = LatLng(0.0, 0.0)
  var markerPositions = mutableStateOf(listOf<LatLng>())
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

package com.tuvarna.geo.entity.state

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.entity.RiskChoices

private val INITIAL_POSITION = LatLng(0.0, 0.0)

@SuppressLint("MutableCollectionMutableState")
class UserMarkerState {

  var clickedMarker = mutableStateOf(INITIAL_POSITION)
  var topBarTitleText = mutableStateOf("Geo")
  var userChoiceForDataType = mutableStateOf(RiskChoices.None)

  fun hasUserClickedMarker(): Boolean {
    return !clickedMarker.value.equals(INITIAL_POSITION)
  }

  fun resetMarkerState() {
    clickedMarker.value = INITIAL_POSITION
  }

  fun changeTitle(title: String) {
    topBarTitleText.value = title
  }
}

package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.controller.UIFeedback
import com.tuvarna.geo.entity.SoilData
import com.tuvarna.geo.repository.ApiPayload
import com.tuvarna.geo.repository.DangerRepository
import com.tuvarna.geo.rest_api.models.DangerDTO
import com.tuvarna.geo.rest_api.models.Earthquake
import com.tuvarna.geo.rest_api.models.Soil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dangerRepository: DangerRepository) :
  UIStateViewModel() {

  private val _soil = MutableStateFlow<SoilData>(LatLng(0.00, 0.00), Soil())
  private val _earthquake = MutableStateFlow<Earthquake>(Earthquake())

  val soil: StateFlow<Soil> = _soil
  val earthquake: StateFlow<Earthquake> = _earthquake

  fun retrieveSoil(point: DangerDTO) {
    Timber.d(
      "User clicked the map with coordinates %.6f, %.6f  and chose a soil type! Moving on...",
      point.latitude,
      point.longitude,
    )
    viewModelScope.launch {
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

      val message =
        when (val result: ApiPayload<Soil> = dangerRepository.getSoil(point)) {
          is ApiPayload.Success -> {
            val soilData: Soil = result.data!!
            Timber.d("Soil type received! Payload received from server %s", soilData)
            _soil.value = soilData
            returnStatus = UIFeedback.States.Success
            result.message!!
          }
          is ApiPayload.Failure -> {
            returnStatus = UIFeedback.States.Failed
            result.message
          }
        }
      mutableStateFlow.value = UIFeedback(state = returnStatus, message = message)
    }
  }

  fun retrieveEarthquake(point: DangerDTO) {
    Timber.d(
      "User clicked the map with coordinates %.6f , %.6f and chose a earthquake! Moving on...",
      point.latitude,
      point.longitude,
    )
    viewModelScope.launch {
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

      val message =
        when (val result: ApiPayload<Earthquake> = dangerRepository.getEarthquake(point)) {
          is ApiPayload.Success -> {
            Timber.d("Earthquake received! Payload received from server %s", earthquake)
            _earthquake.value = result.data!!

            returnStatus = UIFeedback.States.Success
            result.message!!
          }
          is ApiPayload.Failure -> {
            returnStatus = UIFeedback.States.Failed
            result.message
          }
        }
      mutableStateFlow.value = UIFeedback(state = returnStatus, message = message)
    }
  }
}

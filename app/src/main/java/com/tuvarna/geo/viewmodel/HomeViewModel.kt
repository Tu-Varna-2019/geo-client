package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.controller.UIFeedback
import com.tuvarna.geo.entity.DangerData
import com.tuvarna.geo.repository.ApiPayload
import com.tuvarna.geo.repository.DangerRepository
import com.tuvarna.geo.rest_api.models.DangerDTO
import com.tuvarna.geo.rest_api.models.Earthquake
import com.tuvarna.geo.rest_api.models.Soil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dangerRepository: DangerRepository) :
  UIStateViewModel() {
  private val _dangerData = MutableStateFlow(mapOf<LatLng, DangerData>())
  val dangerData: StateFlow<Map<LatLng, DangerData>> = _dangerData.asStateFlow()

  private fun updateDangerData(latLng: LatLng, danger: DangerData) {
    _dangerData.update { map -> map + (latLng to danger) }
  }

  fun getDangerByLatLang(latLng: LatLng): DangerData? {
    return _dangerData.value[latLng]
  }

  fun removeDangerByLatLng(latLng: LatLng) {
    _dangerData.update { map -> map - latLng }
  }

  fun addDangerData(latLng: LatLng, danger: DangerData = DangerData.NoDataYet) {
    _dangerData.value += (latLng to danger)
  }

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
            val dangerData: Soil = result.data!!
            Timber.d("Soil type received! Payload received from server %s", dangerData)
            updateDangerData(
              LatLng(point.latitude!!, point.longitude!!),
              DangerData.SoilData(dangerData),
            )
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
            val dangerData: Earthquake = result.data!!
            Timber.d("Earthquake received! Payload received from server %s", dangerData)
            updateDangerData(
              LatLng(point.latitude!!, point.longitude!!),
              DangerData.EarthquakeData(dangerData),
            )
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

package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.controller.UIFeedback
import com.tuvarna.geo.entity.RiskHierarchy
import com.tuvarna.geo.repository.ApiPayload
import com.tuvarna.geo.repository.RiskRepository
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
class HomeViewModel @Inject constructor(private val dangerRepository: RiskRepository) :
  UIStateViewModel() {
  private val _riskLocations = MutableStateFlow(mapOf<LatLng, RiskHierarchy>())
  val riskLocations: StateFlow<Map<LatLng, RiskHierarchy>> = _riskLocations.asStateFlow()

  private fun updateRiskByLocation(latLng: LatLng, risk: RiskHierarchy) {
    _riskLocations.update { map -> map + (latLng to risk) }
  }

  fun getRiskByLocation(latLng: LatLng): RiskHierarchy? {
    return _riskLocations.value[latLng]
  }

  fun removeRiskByLocation(latLng: LatLng) {
    _riskLocations.update { map -> map - latLng }
  }

  fun addRiskByLocation(latLng: LatLng, risk: RiskHierarchy = RiskHierarchy.NoDataYet) {
    _riskLocations.value += (latLng to risk)
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
            val riskLocations: Soil = result.data!!
            Timber.d("Soil type received! Payload received from server %s", riskLocations)
            updateRiskByLocation(
              LatLng(point.latitude!!, point.longitude!!),
              RiskHierarchy.SoilSubHierarchy(riskLocations),
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
            val riskLocations: Earthquake = result.data!!
            Timber.d("Earthquake received! Payload received from server %s", riskLocations)
            updateRiskByLocation(
              LatLng(point.latitude!!, point.longitude!!),
              RiskHierarchy.EarthquakeSubHierarchy(riskLocations),
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

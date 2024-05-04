package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.controller.UIFeedback
import com.tuvarna.geo.entity.RiskChoices
import com.tuvarna.geo.entity.RiskHierarchy
import com.tuvarna.geo.repository.ApiPayload
import com.tuvarna.geo.repository.RiskRepository
import com.tuvarna.geo.rest_api.models.DangerDTO
import com.tuvarna.geo.rest_api.models.Earthquake
import com.tuvarna.geo.rest_api.models.Soil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HomeViewModel @Inject constructor(private val dangerRepository: RiskRepository) :
  UIStateViewModel() {

  private val _riskLocations = MutableStateFlow(mapOf<LatLng, RiskHierarchy>())
  val riskLocations = _riskLocations.asStateFlow()

  private fun updateRiskByLocation(latLng: LatLng, risk: RiskHierarchy) {
    _riskLocations.update { map -> map + (latLng to risk) }
  }

  fun updateRiskStateByLocation(latLng: LatLng, riskChoices: RiskChoices) {
    val riskHierarchy = getRiskByLocation(latLng)!!
    riskHierarchy.riskState = riskChoices
    _riskLocations.update { map -> map + (latLng to riskHierarchy) }

    if (mutableStateFlow.value.state != UIFeedback.States.Success)
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Success)
  }

  fun getRiskByLocation(latLng: LatLng): RiskHierarchy? {
    return _riskLocations.value[latLng]
  }

  fun removeRiskByLocation(latLng: LatLng) {
    _riskLocations.update { map -> map - latLng }
  }

  fun purgeAllRisks() {
    _riskLocations.value = emptyMap()
  }

  fun addRiskByLocation(latLng: LatLng, risk: RiskHierarchy = RiskHierarchy()) {
    _riskLocations.value += (latLng to risk)
  }

  fun getNearestLocation(latLng: LatLng): LatLng? {
    return _riskLocations.value.keys.find {
      abs(it.latitude - latLng.latitude) < 0.5 && abs(it.longitude - latLng.longitude) < 0.5
    }
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
            val risk = RiskHierarchy()
            risk.soil = riskLocations
            updateRiskByLocation(LatLng(point.latitude!!, point.longitude!!), risk)
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
            val risk = RiskHierarchy()
            risk.earthquake = riskLocations
            updateRiskByLocation(LatLng(point.latitude!!, point.longitude!!), risk)

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

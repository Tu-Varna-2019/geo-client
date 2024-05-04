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

    /* Bug: This part solves the problem regarding the recomposition of the _riskLocations
    to properly re-add the polygon if the user tries to re-choose the first risk type
    (i.e he start from: 1.Soil, 2.Earthquake, 3.Soil).
    However it also introduces another bug, where after that action the  snackbar manager won't appear for future marker creations, due to the mutableStateFlow.value.
     */
    if (mutableStateFlow.value.state != UIFeedback.States.Nothing)
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Nothing)
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

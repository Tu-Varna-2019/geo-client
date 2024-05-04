package com.tuvarna.geo.repository

import com.tuvarna.geo.rest_api.apis.RiskControllerApi
import com.tuvarna.geo.rest_api.models.Earthquake
import com.tuvarna.geo.rest_api.models.RiskDTO
import com.tuvarna.geo.rest_api.models.Soil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RiskRepository @Inject constructor(private val dangerApi: RiskControllerApi) :
  BaseRepository {
  suspend fun getSoil(point: RiskDTO): ApiPayload<Soil> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Sending point latitude/longitude: %s", point)

        val response = dangerApi.getSoil(point)

        ApiPayload.Success(response.message, response.data!!)
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }

  suspend fun getEarthquake(point: RiskDTO): ApiPayload<Earthquake> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Sending point latitude/longitude: %s", point)
        val response = dangerApi.getEarthquake(point)

        ApiPayload.Success(response.message, response.data!!)
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }
}

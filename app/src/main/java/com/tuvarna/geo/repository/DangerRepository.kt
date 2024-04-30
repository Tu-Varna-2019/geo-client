package com.tuvarna.geo.repository

import com.tuvarna.geo.rest_api.apis.DangerControllerApi
import com.tuvarna.geo.rest_api.models.DangerDTO
import com.tuvarna.geo.rest_api.models.Earthquake
import com.tuvarna.geo.rest_api.models.Soil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DangerRepository @Inject constructor(private val dangerApi: DangerControllerApi) :
  BaseRepository {
  suspend fun getSoil(point: DangerDTO): ApiPayload<Soil> {
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

  suspend fun getEarthquake(point: DangerDTO): ApiPayload<Earthquake> {
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

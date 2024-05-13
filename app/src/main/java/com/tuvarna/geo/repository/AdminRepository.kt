package com.tuvarna.geo.repository

import com.tuvarna.geo.controller.apis.AdminControllerApi
import com.tuvarna.geo.controller.models.LoggerDTO
import com.tuvarna.geo.controller.models.UserInfoDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminRepository @Inject constructor(private val adminControllerApi: AdminControllerApi) :
  BaseRepository {
  suspend fun getLogs(userType: String): ApiPayload<List<LoggerDTO>> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Retrieving logs...")
        val response = adminControllerApi.getLogs(userType)
        ApiPayload.Success(response.message, response.data)
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }

  suspend fun sendLog(userType: String, loggerDTO: LoggerDTO): ApiPayload<Any> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Sending log: %s", loggerDTO)
        val response = adminControllerApi.saveLog(userType, loggerDTO)
        ApiPayload.Success(response.message, response.data)
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }

  suspend fun blockUser(email: String, isblocked: Boolean): ApiPayload<Any> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Sending a request to block= %s user: %s", isblocked, email)
        val response = adminControllerApi.blockUser(email, isblocked)
        ApiPayload.Success(response.message, response.data)
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }

  suspend fun promoteUser(email: String, userType: String): ApiPayload<Any> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Sending a request to promote= %s user: %s", userType, email)
        val response = adminControllerApi.promoteUser(email, userType)
        ApiPayload.Success(response.message, response.data)
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }

  suspend fun getUsers(userType: String): ApiPayload<List<UserInfoDTO>> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Sending a request to retrieve all  users with type: %s", userType)

        val response = adminControllerApi.getUsers(userType)
        ApiPayload.Success(response.message, response.data)
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }
}

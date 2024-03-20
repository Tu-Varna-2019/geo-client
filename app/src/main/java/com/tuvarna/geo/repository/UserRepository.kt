package com.tuvarna.geo.repository

import com.tuvarna.geo.controller.ApiResult
import com.tuvarna.geo.entity.User
import com.tuvarna.geo.mapper.UserMapper
import com.tuvarna.geo.rest_api.apis.RegisterControllerApi
import com.tuvarna.geo.rest_api.infrastructure.ClientError
import com.tuvarna.geo.rest_api.infrastructure.ClientException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val registerApi: RegisterControllerApi) {

  suspend fun login(user: User): ApiResult {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Logging in user: %s", user)

        val userDTO = UserMapper.UserMapper.toDto(user, "")
        // TODO: Implement login API
        val response = registerApi.create(userDTO)

        ApiResult.Success(response.message ?: "Success")
      } catch (e: ClientException) {
        val clientError = e.response as? ClientError<*>
        val errorMessage =
          when (val body = clientError?.body) {
            is String -> parseErrorMessage(body)
            else -> clientError?.message ?: "Unknown client error"
          }
        Timber.d("Client error during user login: $errorMessage")
        ApiResult.Error(IOException(errorMessage))
      } catch (e: Exception) {
        Timber.e(e, "Unexpected error during user login")
        ApiResult.Error(IOException("Unexpected error: ${e.localizedMessage}"))
      }
    }
  }

  suspend fun register(user: User, userType: String): ApiResult {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Registering user: %s", user)

        val userDTO = UserMapper.UserMapper.toDto(user, userType)
        val response = registerApi.create(userDTO)

        ApiResult.Success(response.message ?: "Success")
      } catch (e: ClientException) {
        val clientError = e.response as? ClientError<*>
        val errorMessage =
          when (val body = clientError?.body) {
            is String -> parseErrorMessage(body)
            else -> clientError?.message ?: "Unknown client error"
          }
        Timber.d("Client error during user registration: $errorMessage")
        ApiResult.Error(IOException(errorMessage))
      } catch (e: Exception) {
        Timber.e(e, "Unexpected error during user registration")
        ApiResult.Error(IOException("Unexpected error: ${e.localizedMessage}"))
      }
    }
  }

  fun parseErrorMessage(jsonResponse: String): String {
    return try {
      JSONObject(jsonResponse).getString("message")
    } catch (e: JSONException) {
      "Error parsing error message"
    }
  }
}

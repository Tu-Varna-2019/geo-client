package com.tuvarna.geo.repository

import com.tuvarna.geo.controller.ApiResult
import com.tuvarna.geo.rest_api.infrastructure.ClientError
import com.tuvarna.geo.rest_api.infrastructure.ClientException
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

interface CommonRepository {
  fun <T> handleApiException(e: Exception): ApiResult<T> {
    return when (e) {
      is ClientException -> {
        val clientError = e.response as? ClientError<*>
        val errorMessage =
          when (val body = clientError?.body) {
            is String -> parseApiErrorMessage(body)
            else -> clientError?.message ?: "Unknown client error"
          }
        Timber.d("Client error: $errorMessage")
        ApiResult.Error(errorMessage)
      }
      else -> {
        Timber.e(e, "Unexpected error")
        ApiResult.Error("Unexpected error: ${e.localizedMessage}")
      }
    }
  }

  private fun parseApiErrorMessage(jsonResponse: String): String {
    return try {
      JSONObject(jsonResponse).getString("message")
    } catch (e: JSONException) {
      "Error parsing error message"
    }
  }
}

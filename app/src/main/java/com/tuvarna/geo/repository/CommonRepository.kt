package com.tuvarna.geo.repository

import com.tuvarna.geo.controller.ApiResult
import com.tuvarna.geo.rest_api.infrastructure.ClientError
import com.tuvarna.geo.rest_api.infrastructure.ClientException
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException

interface CommonRepository {
  fun handleApiException(e: Exception): ApiResult {
    return when (e) {
      is ClientException -> {
        val clientError = e.response as? ClientError<*>
        val errorMessage =
          when (val body = clientError?.body) {
            is String -> parseApiErrorMessage(body)
            else -> clientError?.message ?: "Unknown client error"
          }
        Timber.d("Client error: $errorMessage")
        ApiResult.Error(IOException(errorMessage))
      }
      else -> {
        Timber.e(e, "Unexpected error")
        ApiResult.Error(IOException("Unexpected error: ${e.localizedMessage}"))
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

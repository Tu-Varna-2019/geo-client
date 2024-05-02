package com.tuvarna.geo.repository

import com.tuvarna.geo.rest_api.infrastructure.ClientError
import com.tuvarna.geo.rest_api.infrastructure.ClientException
import com.tuvarna.geo.rest_api.infrastructure.ServerError
import com.tuvarna.geo.rest_api.infrastructure.ServerException
import org.json.JSONObject
import timber.log.Timber

interface BaseRepository {
  fun handleApiError(err: Exception): ApiPayload<Nothing> {

    val errorMessage: String =
      when (err) {
        is ClientException -> {
          val clientError = err.response as ClientError<*>
          JSONObject(clientError.body.toString()).getString("message")
        }
        is ServerException -> {
          val serverError = err.response as ServerError<*>
          JSONObject(serverError.body.toString()).getString("message")
        }
        else -> {
          Timber.e("Unknown API error %s", err)
          "Failed to connect to server. Try again!"
        }
      }
    return ApiPayload.Failure(errorMessage)
  }
}

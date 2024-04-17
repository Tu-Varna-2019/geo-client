package com.tuvarna.geo.repository

import android.os.Parcelable
import com.tuvarna.geo.controller.ApiResponse
import com.tuvarna.geo.rest_api.infrastructure.ClientError
import com.tuvarna.geo.rest_api.infrastructure.ServerError
import org.json.JSONObject
import timber.log.Timber

interface BaseRepository {
  fun <T : Parcelable> handleApiError(err: Exception): ApiResponse<T> {
    val errorMessage: String

    when (err) {
      is ClientError<*> -> {
        errorMessage = JSONObject(err.body.toString()).getString("message")
      }
      is ServerError<*> -> {
        errorMessage = JSONObject(err.body.toString()).getString("message")
      }
      else -> {
        Timber.e("Unknown API error")
        errorMessage = "Unknown error!"
      }
    }
    return ApiResponse.Parse<T>(errorMessage)
  }
}

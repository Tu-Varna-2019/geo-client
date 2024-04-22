package com.tuvarna.geo.repository

sealed class ApiPayload<out T> {
  data class Success<out T>(val message: String?, val data: T?) : ApiPayload<T>()

  data class Failure(val message: String) : ApiPayload<Nothing>()
}

package com.tuvarna.geo.controller

sealed class ApiResult {
  data class Success(val message: String) : ApiResult()

  data class Error(val exception: Exception) : ApiResult()
}

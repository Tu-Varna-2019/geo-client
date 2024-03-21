package com.tuvarna.geo.controller

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ApiResult<T> : Parcelable {

  @Parcelize data object Empty : ApiResult<Nothing>()

  @Parcelize data object Loading : ApiResult<Nothing>()

  @Parcelize
  data class Success<T : Parcelable>(val message: String, val data: T? = null) : ApiResult<T>()

  @Parcelize data class Error<T>(val message: String) : ApiResult<T>()
}

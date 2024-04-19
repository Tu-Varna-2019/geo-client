package com.tuvarna.geo.controller

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ApiResponse<T> : Parcelable {
  @Parcelize data object DoNothing : ApiResponse<Nothing>()

  @Parcelize data object Waiting : ApiResponse<Nothing>()

  @Parcelize
  data class Parse<T : Parcelable>(val message: String, val data: T? = null) : ApiResponse<T>()
}

package com.tuvarna.geo.viewmodel

import com.tuvarna.geo.controller.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IBaseViewModel {
  val _uiState: MutableStateFlow<ApiResponse<*>>
  val uiState: StateFlow<ApiResponse<*>>
}

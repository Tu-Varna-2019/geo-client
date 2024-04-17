package com.tuvarna.geo.viewmodel

import androidx.lifecycle.ViewModel
import com.tuvarna.geo.controller.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public abstract class UIStateViewModel : ViewModel(), IBaseViewModel {
  override val _uiState = MutableStateFlow<ApiResponse<*>>(ApiResponse.DoNothing)
  override val uiState: StateFlow<ApiResponse<*>> = _uiState
}

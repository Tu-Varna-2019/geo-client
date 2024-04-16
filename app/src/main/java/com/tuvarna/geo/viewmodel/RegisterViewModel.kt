package com.tuvarna.geo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.controller.ApiResult
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
  ViewModel() {

  private val _uiState = MutableStateFlow<ApiResult<Nothing>>(ApiResult.Empty)
  val uiState: StateFlow<ApiResult<Nothing>> = _uiState

  fun register(user: UserEntity, userType: String) {
    Timber.d("UserEntity %s clicked the registration button! Moving on...", user)
    viewModelScope.launch {
      _uiState.value = ApiResult.Loading
      val result = userRepository.register(user, userType)
      _uiState.value = result
    }
  }
}

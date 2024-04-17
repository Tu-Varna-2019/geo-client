package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.controller.ApiResponse
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
  UIStateViewModel() {
  fun register(user: UserEntity, userType: String) {
    Timber.d("UserEntity %s clicked the registration button! Moving on...", user)
    viewModelScope.launch {
      _uiState.value = ApiResponse.Waiting
      val result = userRepository.register(user, userType)
      _uiState.value = result
    }
  }
}

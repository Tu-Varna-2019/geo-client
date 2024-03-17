package com.tuvarna.geo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.controller.ApiResult
import com.tuvarna.geo.entity.User
import com.tuvarna.geo.repository.UserRepository
import com.tuvarna.geo.viewmodel.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
  ViewModel() {

  private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
  val uiState: StateFlow<UiState> = _uiState

  fun register(user: User, userType: String) {
    Timber.d("User %s clicked the registration button! Moving on...", user)
    viewModelScope.launch {
      _uiState.value = UiState.Loading
      val result = userRepository.register(user, userType)
      _uiState.value =
        when (result) {
          is ApiResult.Success -> UiState.Success(result.data)
          is ApiResult.Error ->
            UiState.Error(result.exception.message ?: "An unknown error occurred")
        }
    }
  }
}

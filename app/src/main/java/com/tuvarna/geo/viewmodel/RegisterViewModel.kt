package com.tuvarna.geo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.controller.ApiResult
import com.tuvarna.geo.entity.User
import com.tuvarna.geo.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
  ViewModel() {

  private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Empty)
  val uiState: StateFlow<RegisterUiState> = _uiState

  fun register(user: User, userType: String) {
    viewModelScope.launch {
      _uiState.value = RegisterUiState.Loading
      val result = userRepository.register(user, userType)
      _uiState.value =
        when (result) {
          is ApiResult.Success -> RegisterUiState.Success(result.data)
          is ApiResult.Error ->
            RegisterUiState.Error(result.exception.message ?: "An unknown error occurred")
        }
    }
  }

  sealed class RegisterUiState {
    object Empty : RegisterUiState()

    object Loading : RegisterUiState()
    // Changed Success state to potentially hold data (like a success message or user object)
    data class Success(val message: String) :
      RegisterUiState() // Adjust according to what your API returns

    data class Error(val message: String) : RegisterUiState()
  }
}

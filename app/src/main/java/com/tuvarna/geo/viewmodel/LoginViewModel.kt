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
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

  private val _uiState = MutableStateFlow<ApiResult<*>>(ApiResult.Empty)
  val uiState: StateFlow<ApiResult<*>> = _uiState

  fun login(user: UserEntity) {
    Timber.d("User %s clicked the login button! Moving on...", user)
    viewModelScope.launch {
      _uiState.value = ApiResult.Loading
      val result = userRepository.login(user)
      _uiState.value =
        when (result) {
          is ApiResult.Success<*> -> {
            val parsedUser = (result as ApiResult.Success<UserEntity>).data
            ApiResult.Success(result.message, parsedUser)
          }
          is ApiResult.Error -> ApiResult.Error<Nothing>(result.message)
          else -> throw IllegalStateException("Unexpected ApiResult type: $result")
        }
    }
  }
}

package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.controller.UIFeedback
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.repository.ApiPayload
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
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

      val message =
        when (val result = userRepository.register(user, userType)) {
          is ApiPayload.Success -> {
            Timber.d("User registered successfully! Payload received from server %s", result)

            returnStatus = UIFeedback.States.Success
            result.message!!
          }
          is ApiPayload.Failure -> {
            returnStatus = UIFeedback.States.Failed
            result.message
          }
        }
      mutableStateFlow.value = UIFeedback(state = returnStatus, message = message)
    }
  }
}

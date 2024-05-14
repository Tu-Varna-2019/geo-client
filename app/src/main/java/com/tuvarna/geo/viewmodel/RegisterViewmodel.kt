package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.navigation.UIFeedback
import com.tuvarna.geo.repository.ApiPayload
import com.tuvarna.geo.repository.UserRepository
import com.tuvarna.geo.viewmodel.states.LoggerManager
import com.tuvarna.geo.viewmodel.states.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewmodel
@Inject
constructor(private val userRepository: UserRepository, private val loggerManager: LoggerManager) :
  UIState() {
  private val USER_TYPE: String = "customer"

  fun register(user: UserEntity, userType: String) {
    Timber.d("UserEntity %s clicked the registration button! Moving on...", user)
    viewModelScope.launch {
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

      val message =
        when (val result = userRepository.register(user, userType)) {
          is ApiPayload.Success -> {
            Timber.d("User registered successfully! Payload received from server %s", result)
            loggerManager.sendLog(
              user.username,
              USER_TYPE,
              "User has registered an account to the system!",
            )

            returnStatus = UIFeedback.States.Success
            result.message!!
          }
          is ApiPayload.Failure -> {
            returnStatus = UIFeedback.States.Failed
            loggerManager.sendLog(
              "None",
              USER_TYPE,
              "User has encountered an issue while registering: " + result.message,
            )
            result.message
          }
        }
      mutableStateFlow.value = UIFeedback(state = returnStatus, message = message)
    }
  }
}

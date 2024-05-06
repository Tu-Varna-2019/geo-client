package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.controller.LoggerManager
import com.tuvarna.geo.controller.UIFeedback
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.repository.ApiPayload
import com.tuvarna.geo.repository.UserRepository
import com.tuvarna.geo.storage.UserSessionStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
  private val loggerManager: LoggerManager,
  private val userRepository: UserRepository,
  private val userSessionStorage: UserSessionStorage,
) : UIStateViewModel() {
  private val USER_TYPE: String = "customer"

  fun login(user: UserEntity) {
    Timber.d("User %s clicked the login button! Moving on...", user)
    viewModelScope.launch {
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

      val message =
        when (val result: ApiPayload<UserEntity> = userRepository.login(user)) {
          is ApiPayload.Success -> {
            val parsedUser = result.data

            userSessionStorage.putUserProps(
              newId = parsedUser!!.id,
              newUsername = parsedUser.username,
              newEmail = parsedUser.email,
              newUserType = parsedUser.usertype.type,
              newAccessToken = parsedUser.accessToken,
            )
            Timber.d("User logged in! Payload received from server %s", parsedUser)
            loggerManager.sendLog(
              parsedUser.username,
              parsedUser.usertype.type,
              "User has logged in to the system!",
            )
            returnStatus = UIFeedback.States.Success
            result.message!!
          }
          is ApiPayload.Failure -> {
            loggerManager.sendLog(user.username, USER_TYPE, "User failed to log in to the system!")

            returnStatus = UIFeedback.States.Failed
            result.message
          }
        }
      mutableStateFlow.value = UIFeedback(state = returnStatus, message = message)
    }
  }
}

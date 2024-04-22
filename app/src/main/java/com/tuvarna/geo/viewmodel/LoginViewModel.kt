package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
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
  private val userRepository: UserRepository,
  private val userSessionStorage: UserSessionStorage,
) : UIStateViewModel() {

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
            )
            Timber.d("User logged in! Payload received from server %s", parsedUser)
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

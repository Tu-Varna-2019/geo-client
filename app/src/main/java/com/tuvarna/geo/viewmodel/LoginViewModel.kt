package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.controller.ApiResponse
import com.tuvarna.geo.entity.UserEntity
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
      _uiState.value = ApiResponse.Waiting
      val result = userRepository.login(user)
      _uiState.value =
        when (result) {
          is ApiResponse.Parse<*> -> {
            val parsedUser = (result as ApiResponse.Parse<UserEntity>).data
            Timber.d("User logged in! Payload received from server %s", parsedUser)

            userSessionStorage.putUserProps(
              newId = parsedUser!!.id,
              newUsername = parsedUser.username,
              newEmail = parsedUser.email,
              newUserType = parsedUser.usertype.type,
            )

            ApiResponse.Parse(result.message, parsedUser)
          }
          is ApiResponse.Parse -> ApiResponse.Parse<Nothing>(result.message)
          else -> throw IllegalStateException("Unexpected ApiResponse type: $result")
        }
    }
  }
}

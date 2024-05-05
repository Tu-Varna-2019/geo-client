package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.controller.UIFeedback
import com.tuvarna.geo.repository.AdminRepository
import com.tuvarna.geo.repository.ApiPayload
import com.tuvarna.geo.rest_api.models.LoggerDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private val adminRepositoy: AdminRepository) :
  UIStateViewModel() {

  fun sendLog(userType: String, loggerDTO: LoggerDTO) {
    Timber.d("%s is sending log %s to the server! Moving on...", userType, loggerDTO)
    viewModelScope.launch {
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

      val message =
        when (val result: ApiPayload<Any> = adminRepositoy.sendLog(userType, loggerDTO)) {
          is ApiPayload.Success -> {
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

  fun fetchLogs(userType: String) {
    Timber.d("%s sent a request to retrieve all logs to  the server! Moving on...", userType)
    viewModelScope.launch {
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

      val message =
        when (val result: ApiPayload<Any> = adminRepositoy.getLogs(userType)) {
          is ApiPayload.Success -> {
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

  fun blockUser(email: String, isblocked: Boolean) {
    Timber.d("Admin sent a request to block=%s user: %s! Moving on...", isblocked, email)
    viewModelScope.launch {
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

      val message =
        when (val result: ApiPayload<Any> = adminRepositoy.blockUser(email, isblocked)) {
          is ApiPayload.Success -> {
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

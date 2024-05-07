package com.tuvarna.geo.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.controller.LoggerManager
import com.tuvarna.geo.controller.UIFeedback
import com.tuvarna.geo.repository.AdminRepository
import com.tuvarna.geo.repository.ApiPayload
import com.tuvarna.geo.rest_api.models.LoggerDTO
import com.tuvarna.geo.rest_api.models.UserInfoDTO
import com.tuvarna.geo.storage.UserSessionStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AdminViewModel
@Inject
constructor(
  private val adminRepositoy: AdminRepository,
  private val loggerManager: LoggerManager,
  private val userSessionStorage: UserSessionStorage,
) : UIStateViewModel() {
  private val _userLogs = MutableStateFlow<List<LoggerDTO>>(emptyList())
  val userLogs = _userLogs.asStateFlow()

  private val _userInfos = MutableStateFlow<List<UserInfoDTO>>(emptyList())
  val userInfos = _userInfos.asStateFlow()

  private fun updateUserInfoIsBlocked(email: String) {
    _userInfos.value =
      _userInfos.value.map { userInfo ->
        if (userInfo.email == email)
          UserInfoDTO(userInfo.username, userInfo.email, !userInfo.isblocked!!, userInfo.userType)
        else userInfo
      }
  }

  fun logUserViewNavigation(viewName: String) {
    CoroutineScope(Dispatchers.IO).launch {
      loggerManager.sendLog(
        userSessionStorage.readUsername(),
        userSessionStorage.readUserType(),
        "User clicked a button to navigate to: $viewName",
      )
    }
  }

  fun fetchLogs(userType: String) {
    Timber.d("%s sent a request to retrieve all logs to  the server! Moving on...", userType)
    viewModelScope.launch {
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

      val message =
        when (val result: ApiPayload<Any> = adminRepositoy.getLogs(userType)) {
          is ApiPayload.Success -> {
            _userLogs.value = (result.data as List<LoggerDTO>?)!!

            loggerManager.sendLog(
              userSessionStorage.readUsername(),
              userSessionStorage.readUserType(),
              "Admin chose to fetch logs for $userType ",
            )
            returnStatus = UIFeedback.States.Success
            result.message!!
          }
          is ApiPayload.Failure -> {

            loggerManager.sendLog(
              userSessionStorage.readUsername(),
              userSessionStorage.readUserType(),
              "Admin has failed to fetch logs for $userType ",
            )

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
            updateUserInfoIsBlocked(email)

            loggerManager.sendLog(
              userSessionStorage.readUsername(),
              userSessionStorage.readUserType(),
              "Admin chose to block=$isblocked user: $email",
            )

            returnStatus = UIFeedback.States.Success
            result.message!!
          }
          is ApiPayload.Failure -> {
            loggerManager.sendLog(
              userSessionStorage.readUsername(),
              userSessionStorage.readUserType(),
              "Admin has failed to block=$isblocked user: $email",
            )

            returnStatus = UIFeedback.States.Failed
            result.message
          }
        }
      mutableStateFlow.value = UIFeedback(state = returnStatus, message = message)
    }
  }

  fun getUsers(userType: String) {
    Timber.d("Admin sent a request to fetch all users with type:  %s! Moving on...", userType)
    viewModelScope.launch {
      mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

      val message =
        when (val result: ApiPayload<List<UserInfoDTO>> = adminRepositoy.getUsers(userType)) {
          is ApiPayload.Success -> {
            _userInfos.value = result.data!!

            loggerManager.sendLog(
              userSessionStorage.readUsername(),
              userSessionStorage.readUserType(),
              "Admin send a request to fetch all users: $userType",
            )

            returnStatus = UIFeedback.States.Success
            result.message!!
          }
          is ApiPayload.Failure -> {
            loggerManager.sendLog(
              userSessionStorage.readUsername(),
              userSessionStorage.readUserType(),
              "Admin has failed sending a request to fetch all users: $userType",
            )

            returnStatus = UIFeedback.States.Failed
            result.message
          }
        }
      mutableStateFlow.value = UIFeedback(state = returnStatus, message = message)
    }
  }

  fun exportTableToCSV(context: Context, logs: List<LoggerDTO>, uri: Uri) {
    Timber.d("Admin is exporting the logging table into CSV! Moving on...")
    mutableStateFlow.value = UIFeedback(state = UIFeedback.States.Waiting)

    logs.let { log ->
      context.contentResolver.openFileDescriptor(uri, "w")?.use { parcelFileDescriptor ->
        val fileOutputStream = FileOutputStream(parcelFileDescriptor.fileDescriptor)
        val csvData =
          "username,event,ip,timestamp\n" +
            log.joinToString("\n") { "${it.username},${it.event},${it.ip},${it.timestamp}" }
        fileOutputStream.write(csvData.toByteArray())
        fileOutputStream.close()

        CoroutineScope(Dispatchers.IO).launch {
          loggerManager.sendLog(
            userSessionStorage.readUsername(),
            userSessionStorage.readUserType(),
            "Admin has successfully exported the log table into CSV!",
          )
        }
      }
    }
  }
}

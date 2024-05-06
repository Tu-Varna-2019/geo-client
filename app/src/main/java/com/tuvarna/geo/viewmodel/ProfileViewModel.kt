package com.tuvarna.geo.viewmodel

import com.tuvarna.geo.controller.LoggerManager
import com.tuvarna.geo.storage.UserSessionStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
  private val loggerManager: LoggerManager,
  private val userSessionStorage: UserSessionStorage,
) : UIStateViewModel() {

  fun logUserViewNavigation(viewName: String) {
    CoroutineScope(Dispatchers.IO).launch {
      loggerManager.sendLog(
        userSessionStorage.readUsername(),
        userSessionStorage.readUserType(),
        "User clicked a button to navigate to: $viewName",
      )
    }
  }

  fun deleteAccount() {}

  fun renameUsername() {}

  fun renameEmail(email: String) {}

  fun changePassword(oldPassword: String, newPassword: String) {}
}

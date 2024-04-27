package com.tuvarna.geo.viewmodel

import com.tuvarna.geo.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
  UIStateViewModel() {

  fun logout() {}

  fun deleteAccount() {}

  fun renameUsername() {}

  fun renameEmail(email: String) {}

  fun changePassword(oldPassword: String, newPassword: String) {}
}

package com.tuvarna.geo.viewmodel

import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.controller.ApiResponse
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) :
  UIStateViewModel() {

  fun clickOnMap(user: UserEntity) {
    Timber.d("User %s clicked the map!", user)
    viewModelScope.launch {
      _uiState.value = ApiResponse.Waiting
      // TODO: Add something
    }
  }
}

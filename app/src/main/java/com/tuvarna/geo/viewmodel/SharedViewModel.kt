package com.tuvarna.geo.viewmodel

import com.tuvarna.geo.repository.UserRepository

interface SharedViewModel {
  val userRepository: UserRepository
}

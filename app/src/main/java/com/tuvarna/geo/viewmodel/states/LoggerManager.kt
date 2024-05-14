package com.tuvarna.geo.viewmodel.states

import com.tuvarna.geo.controller.models.LoggerDTO
import com.tuvarna.geo.repository.AdminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.InetAddress
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LoggerManager @Inject constructor(private val adminRepository: AdminRepository) {
  suspend fun sendLog(username: String, userType: String, event: String) {
    withContext(Dispatchers.IO) {
      val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
      val clientIP = InetAddress.getLocalHost().hostAddress
      Timber.d("%s is sending log %s to the server! Moving on...", userType, username)
      adminRepository.sendLog(userType, LoggerDTO(username, event, clientIP, timestamp))
    }
  }
}

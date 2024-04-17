package com.tuvarna.geo.repository

import com.tuvarna.geo.controller.ApiResponse
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.mapper.UserMapper
import com.tuvarna.geo.rest_api.apis.LoginControllerApi
import com.tuvarna.geo.rest_api.apis.RegisterControllerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository
@Inject
constructor(
  private val registerApi: RegisterControllerApi,
  private val loginApi: LoginControllerApi,
) : BaseRepository {
  suspend fun login(user: UserEntity): ApiResponse<UserEntity> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Logging in user: %s", user)
        val userDTO = UserMapper.UserMapper.toLoginUserDTO(user)
        val response = loginApi.authenticateUser(userDTO)
        ApiResponse.Parse(response.message ?: "Success", UserEntity(response.data!!))
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }

  suspend fun register(user: UserEntity, userType: String): ApiResponse<Nothing> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Registering user: %s", user)
        val userDTO = UserMapper.UserMapper.toRegisterUserDTO(user, userType)
        val response = registerApi.create(userDTO)
        ApiResponse.Parse(response.message ?: "Success", null)
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }
}

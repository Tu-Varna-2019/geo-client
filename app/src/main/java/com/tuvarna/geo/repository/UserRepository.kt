package com.tuvarna.geo.repository

import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.mapper.UserMapper
import com.tuvarna.geo.rest_api.apis.AuthControllerApi
import com.tuvarna.geo.rest_api.models.LoginUserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val authApi: AuthControllerApi) : BaseRepository {
  suspend fun login(user: UserEntity): ApiPayload<UserEntity> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Logging in user: %s", user)
        val userDTO: LoginUserDTO = UserMapper.toLoginUserDTO(user)
        val response = authApi.login(userDTO)

        ApiPayload.Success(response.message, UserEntity(response.data!!))
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }

  suspend fun register(user: UserEntity, userType: String): ApiPayload<Nothing> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Registering user: %s", user)
        val userDTO = UserMapper.toRegisterUserDTO(user, userType)
        val response = authApi.create(userDTO)

        ApiPayload.Success(response.message, null)
      } catch (e: Exception) {
        handleApiError(e)
      }
    }
  }
}

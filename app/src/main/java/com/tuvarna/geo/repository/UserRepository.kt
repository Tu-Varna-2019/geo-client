package com.tuvarna.geo.repository

import com.tuvarna.geo.apis.RegisterControllerApi
import com.tuvarna.geo.controller.ApiResult
import com.tuvarna.geo.entity.User
import com.tuvarna.geo.model.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val registerApi: RegisterControllerApi) {

  suspend fun login(username: String, password: String) {
    // TODO: Finish me!

    //    return try {
    //      val response = apiService.login(LoginCredentials(username, password))
    //      if (response.isSuccessful && response.body() != null) {
    //
    //        Result.Success(response.body()!!)
    //      } else {
    //
    //        Result.Error(IOException("Error logging in, response code: ${response.code()}"))
    //      }
    //    } catch (e: Exception) {
    //      Result.Error(e)
    //    }
  }

  suspend fun register(user: User, userType: String): ApiResult<String> {
    return withContext(Dispatchers.IO) {
      try {
        Timber.d("Registering user: %s", user)

        val userDTO =
          UserDTO(
            username = user.username,
            email = user.email,
            password = user.password,
            usertype = userType,
            isblocked = user.isblocked,
          )

        val response = registerApi.create(userDTO)
        Timber.d("API response for register: %s", response)
        ApiResult.Success(response)
      } catch (e: Exception) {
        Timber.d("Error registering user: $e")
        ApiResult.Error(e)
      }
    }
  }
}

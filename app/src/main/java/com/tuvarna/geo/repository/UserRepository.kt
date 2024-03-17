package com.tuvarna.geo.repository

import com.tuvarna.geo.apis.RegisterControllerApi
import com.tuvarna.geo.controller.ApiResult
import com.tuvarna.geo.entity.User
import com.tuvarna.geo.infrastructure.ClientException
import com.tuvarna.geo.infrastructure.ServerException
import com.tuvarna.geo.model.UserDTO
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val registerApi: RegisterControllerApi) {

  suspend fun login(username: String, password: String) {
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

  fun register(user: User, userType: String): ApiResult<String> {
    return try {

      val userDTO =
        UserDTO(
          username = user.username,
          email = user.email,
          password = user.password,
          usertype = userType,
          isblocked = user.isblocked,
        )

      val response = registerApi.create(userDTO)

      ApiResult.Success(response)
    } catch (e: IOException) {

      ApiResult.Error(e)
    } catch (e: ClientException) {
      // Handle client-side errors (e.g., bad request, unauthorized)
      ApiResult.Error(e)
    } catch (e: ServerException) {
      // Handle server-side errors (e.g., internal server error)
      ApiResult.Error(e)
    } catch (e: Exception) {
      // Handle other exceptions
      ApiResult.Error(e)
    }
  }
}

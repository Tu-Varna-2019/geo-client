package com.tuvarna.geo.mapper

import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.rest_api.models.LoginUserDTO
import com.tuvarna.geo.rest_api.models.RegisterUserDTO

class UserMapper {

  object UserMapper {

    fun toRegisterUserDTO(user: UserEntity, userType: String): RegisterUserDTO {
      return RegisterUserDTO(user.username, user.email, user.password, user.isblocked, userType)
    }

    fun toLoginUserDTO(user: UserEntity): LoginUserDTO {
      return LoginUserDTO(user.email, user.password)
    }
  }
}

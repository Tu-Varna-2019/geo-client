package com.tuvarna.geo.mapper

import com.tuvarna.geo.entity.User
import com.tuvarna.geo.rest_api.models.LoginUserDTO
import com.tuvarna.geo.rest_api.models.RegisterUserDTO

class UserMapper {

  object UserMapper {
    fun toEntity(userDto: RegisterUserDTO): User {
      return User(0, userDto.username!!, userDto.email!!, userDto.password!!, userDto.isblocked!!)
    }

    fun toRegisterUserDTO(user: User, userType: String): RegisterUserDTO {
      return RegisterUserDTO(user.username, user.email, user.password, user.isblocked, userType)
    }

    fun toLoginUserDTO(user: User): LoginUserDTO {
      return LoginUserDTO(user.email, user.password)
    }
  }
}

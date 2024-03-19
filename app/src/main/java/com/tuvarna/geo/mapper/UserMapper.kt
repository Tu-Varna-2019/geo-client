package com.tuvarna.geo.mapper

import com.tuvarna.geo.entity.User
import com.tuvarna.geo.models.UserDTO

class UserMapper {

  object UserMapper {
    fun toEntity(userDto: UserDTO): User {
      return User(0, userDto.username!!, userDto.email!!, userDto.password!!, userDto.isblocked!!)
    }

    fun toDto(user: User, userType: String): UserDTO {
      return UserDTO(user.username, user.email, user.password, userType, user.isblocked)
    }
  }
}

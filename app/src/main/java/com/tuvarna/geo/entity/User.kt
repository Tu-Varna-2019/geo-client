package com.tuvarna.geo.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class User {

  var id by mutableIntStateOf(0)
  var username by mutableStateOf("")
  var email by mutableStateOf("")
  var isblocked by mutableStateOf(false)
  var password by mutableStateOf("")
  var usertype by mutableStateOf(UserType(0, ""))

  constructor(
    id: Int,
    username: String,
    email: String,
    password: String,
    isblocked: Boolean,
    usertype: UserType = UserType(0, ""),
  ) {
    this.id = id
    this.username = username
    this.email = email
    this.password = password
    this.isblocked = isblocked
    this.usertype = usertype
  }

  @Override
  override fun toString(): String {
    return "User(id=$id, username='$username', email='$email', isblocked=$isblocked, password='$password', usertype=$usertype)"
  }
}

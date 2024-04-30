package com.tuvarna.geo.entity

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.tuvarna.geo.rest_api.models.LoggedInUserDTO
import kotlinx.parcelize.Parcelize

@Parcelize
class SoilEntity() : Parcelable {

  var id by mutableIntStateOf(0)
  var username by mutableStateOf("")
  var email by mutableStateOf("")
  var isblocked by mutableStateOf(false)
  var password by mutableStateOf("")
  var usertype by mutableStateOf(UserTypeEntity(0, ""))

  constructor(
    id: Int,
    username: String,
    email: String,
    password: String,
    isblocked: Boolean,
    usertype: UserTypeEntity = UserTypeEntity(0, ""),
  ) : this() {
    this.id = id
    this.username = username
    this.email = email
    this.password = password
    this.isblocked = isblocked
    this.usertype = usertype
  }

  constructor(restApiUser: LoggedInUserDTO) : this() {
    this.username = restApiUser.username!!
    this.email = restApiUser.email!!
    this.password = ""
    this.isblocked = false
    this.usertype = UserTypeEntity(0, restApiUser.userType!!)
  }

  @Override
  override fun toString(): String {
    return "User(id=$id, username='$username', email='$email', isblocked=$isblocked, password='$password', usertype=$usertype)"
  }
}

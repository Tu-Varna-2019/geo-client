package com.tuvarna.geo.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class EntityUserType {

  var id by mutableIntStateOf(0)
  var type by mutableStateOf("")

  constructor(id: Int, type: String) {
    this.id = id
    this.type = type
  }

  @Override
  override fun toString(): String {
    return "UserType(id=$id, type='$type')"
  }
}

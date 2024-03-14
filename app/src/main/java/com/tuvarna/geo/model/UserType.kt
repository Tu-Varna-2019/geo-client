package com.tuvarna.geo.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UserType {

  var id by mutableIntStateOf(0)
  var type by mutableStateOf("")

  constructor(id: Int, type: String) {
    this.id = id
    this.type = type
  }
}

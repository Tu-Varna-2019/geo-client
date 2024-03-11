package com.tuvarna.geo.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
class User {

        var id by mutableIntStateOf(0)
        var username by mutableStateOf("")
        var email by mutableStateOf("")
        var password by mutableStateOf("")

        constructor(id: Int, username: String, email: String, password: String) {
            this.id = id
            this.username = username
            this.email = email
            this.password = password
        }
}

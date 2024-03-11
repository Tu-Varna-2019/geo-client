package com.tuvarna.geo.helpers

import android.util.Patterns

class Utils{
    companion object {
        fun isValidEmail(email: String): Boolean {
            return !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        // chars >= 8 chars, lowercase + uppercase +digits
        fun isValidPassword(password: String): Boolean {
            val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$".toRegex()
            return !password.matches(passwordRegex)
        }
    }
}

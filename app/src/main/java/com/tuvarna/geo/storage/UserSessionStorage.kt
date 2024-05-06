package com.tuvarna.geo.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class UserSessionStorage(private val context: Context) {
  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

  private var id = intPreferencesKey("id")
  private var username = stringPreferencesKey("username")
  private var email = stringPreferencesKey("email")
  private var userType = stringPreferencesKey("userType")
  private var accessToken = stringPreferencesKey("accessToken")

  fun readUserProps(): Flow<UserStorage> {
    return context.dataStore.data.map { preferences ->
      UserStorage(
        id = preferences[id]!!,
        username = preferences[username]!!,
        email = preferences[email]!!,
        userType = preferences[userType]!!,
      )
    }
  }

  suspend fun readUsername(): String {
    return runBlocking { context.dataStore.data.map { preference -> preference[username] ?: "" } }
      .first()
  }

  suspend fun readUserType(): String {
    return runBlocking { context.dataStore.data.map { preference -> preference[userType] ?: "" } }
      .first()
  }

  fun readAccessToken(): Flow<String> {

    return context.dataStore.data.map { preferences -> preferences[accessToken]!! }
  }

  suspend fun putUserProps(
    newId: Int = 0,
    newUsername: String = "",
    newEmail: String = "",
    newUserType: String = "",
    newAccessToken: String = "",
  ) {
    context.dataStore.edit { settings ->
      settings[id] = newId
      settings[username] = newUsername
      settings[email] = newEmail
      settings[userType] = newUserType
      settings[accessToken] = newAccessToken
    }
  }
}

data class UserStorage(val id: Int, val username: String, val email: String, val userType: String)

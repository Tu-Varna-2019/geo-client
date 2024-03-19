package com.tuvarna.geo.view.public

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuvarna.geo.entity.User

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginView(navController: NavController) {
  val user by remember { mutableStateOf(User(0, "", "", "", false)) }

  val keyboardController = LocalSoftwareKeyboardController.current

  Column(
    modifier = Modifier.fillMaxSize().padding(10.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    OutlinedTextField(
      value = user.username,
      onValueChange = { user.username = it },
      label = { Text("Username") },
      keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
      modifier = Modifier.fillMaxWidth().padding(10.dp),
    )

    OutlinedTextField(
      value = user.password,
      onValueChange = { user.password = it },
      label = { Text("Password") },
      visualTransformation = PasswordVisualTransformation(),
      keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
      modifier = Modifier.fillMaxWidth().padding(10.dp),
    )

    Row(
      modifier = Modifier.fillMaxWidth().padding(10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Button(
        onClick = { keyboardController?.hide() },
        modifier = Modifier.weight(1f).padding(end = 10.dp),
      ) {
        Text("Login")
      }

      Button(
        onClick = { navController.navigate("signUp") },
        modifier = Modifier.weight(1f).padding(start = 10.dp),
      ) {
        Text("Sign up")
      }
    }
  }
}

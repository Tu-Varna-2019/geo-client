package com.tuvarna.geo.view.public

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app_iliyan.view.components.dialog_box.SnackbarManager
import com.tuvarna.geo.R
import com.tuvarna.geo.entity.User
import com.tuvarna.geo.helpers.Utils
import com.tuvarna.geo.viewmodel.RegisterViewModel

@Composable
fun SignUpView(navController: NavController) {

  val registerViewModel = hiltViewModel<RegisterViewModel>()

  val user by remember { mutableStateOf(User(0, "", "", "", false)) }

  val confirmPassword = remember { mutableStateOf("") }

  Box(contentAlignment = Alignment.TopCenter) {
    Column(modifier = Modifier.padding(13.dp)) {
      Text(
        text = "Sign Up",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
      )

      Spacer(modifier = Modifier.height(70.dp))

      Image(
        painter = painterResource(id = R.drawable.earth),
        contentDescription = "Eearth Icon",
        modifier = Modifier.size(110.dp).fillMaxWidth().align(Alignment.CenterHorizontally),
      )

      Spacer(modifier = Modifier.height(60.dp))

      SignUpForm(
        user = user,
        confirmPassword = confirmPassword,
        registerViewModel = registerViewModel,
      )
    }
  }
}

@Composable
fun SignUpForm(
  user: User,
  confirmPassword: MutableState<String>,
  registerViewModel: RegisterViewModel,
) {

  val state = registerViewModel.uiState.collectAsState().value
  val isUsernameValid = user.username.isEmpty()
  val isEmailValid = user.email.isEmpty() || Utils.isValidEmail(user.email)
  val isPasswordValid = user.password.isEmpty() || Utils.isValidPassword(user.password)

  val isConfirmPasswordValid = confirmPassword.value != user.password

  val isSubmitBtnDisabled =
    isUsernameValid || isEmailValid || isPasswordValid || isConfirmPasswordValid

  OutlinedTextField(
    value = user.username,
    isError = isUsernameValid,
    onValueChange = { user.username = it },
    label = { Text("Username") },
    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    modifier = Modifier.fillMaxWidth().padding(10.dp),
  )

  if (isUsernameValid) {
    Text(
      text = "Username cannot be empty",
      color = Color.Red,
      style = MaterialTheme.typography.bodyMedium,
      modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp, 0.dp, 0.dp),
    )
  }

  OutlinedTextField(
    value = user.email,
    isError = isEmailValid,
    onValueChange = { user.email = it },
    label = { Text("Email") },
    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    modifier = Modifier.fillMaxWidth().padding(10.dp),
  )

  if (isEmailValid) {
    Text(
      text = "Email is invalid",
      color = Color.Red,
      style = MaterialTheme.typography.bodyMedium,
      modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp, 0.dp, 0.dp),
    )
  }

  OutlinedTextField(
    value = user.password,
    isError = isPasswordValid,
    onValueChange = { user.password = it },
    label = { Text("Password") },
    visualTransformation = PasswordVisualTransformation(),
    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    modifier = Modifier.fillMaxWidth().padding(10.dp),
  )

  if (isPasswordValid) {
    Text(
      text =
        "Password does not conform the rules: minimum 8 characters long, at least 1 lowercase, uppercase and digits",
      color = Color.Red,
      style = MaterialTheme.typography.bodyMedium,
      modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp, 0.dp, 0.dp),
    )
  }

  OutlinedTextField(
    value = confirmPassword.value,
    onValueChange = { confirmPassword.value = it },
    label = { Text("Confirm Password") },
    visualTransformation = PasswordVisualTransformation(),
    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    modifier = Modifier.fillMaxWidth().padding(10.dp),
  )

  if (isConfirmPasswordValid) {
    Text(
      text = "Passwords do not match",
      color = Color.Red,
      style = MaterialTheme.typography.bodyMedium,
      modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp, 0.dp, 0.dp),
    )
  }

  Row(
    modifier = Modifier.fillMaxWidth().padding(10.dp),
    horizontalArrangement = Arrangement.Center,
  ) {
    Button(
      onClick = { registerViewModel.register(user, "consumer") },
      enabled = !isSubmitBtnDisabled,
      modifier = Modifier.fillMaxWidth().padding(10.dp),
    ) {
      Text("Sign up")
    }
    Spacer(modifier = Modifier.height(8.dp))

    Button(
      onClick = { /* TODO: Add back to login screen fun */},
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text("Back")
    }

    when (state) {
      is RegisterViewModel.RegisterUiState.Loading -> CircularProgressIndicator()
      is RegisterViewModel.RegisterUiState.Success -> {
        // Show success snackbar
        LaunchedEffect(state.message) { SnackbarManager.showSnackbar(state.message) }
      }
      is RegisterViewModel.RegisterUiState.Error -> {
        // Show error snackbar
        LaunchedEffect(state.message) { SnackbarManager.showSnackbar(state.message) }
      }
      else -> {}
    }
  }
}

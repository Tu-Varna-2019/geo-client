package com.tuvarna.geo

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.tuvarna.geo.ui.theme.helpers.Utils

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen() {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isUsernameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var doPasswordsMatchError by remember { mutableStateOf(false) }

    // Access the keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                isUsernameError = false},
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        // Display error message
        if (isUsernameError) {
            Text(
                text = "Username cannot be empty",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isEmailError = false},
            label = { Text("Email") },

            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        // Display error message
        if (isEmailError) {
            Text(
                text = "Email is invalid",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isPasswordError = false},
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        // Display error message
        if (isPasswordError) {
            Text(
                text =
                "Password does not conform the rules: minimum 8 characters long, at least 1 lowercase, uppercase and digits ",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                doPasswordsMatchError = false},
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        if (doPasswordsMatchError) {
            Text(
                text = "Passwords do not match",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    isUsernameError = username.isEmpty()
                    isEmailError = email.isEmpty() || Utils.isValidEmail(email)
                    isPasswordError = password.isEmpty() || Utils.isValidPassword(password)
                    doPasswordsMatchError = password != confirmPassword

                    // Update error states
                    if (isUsernameError || isEmailError || isPasswordError) {
                        keyboardController?.hide()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text("Sign up")
            }
        }
    }
}

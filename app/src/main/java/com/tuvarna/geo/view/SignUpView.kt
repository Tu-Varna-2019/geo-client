package com.tuvarna.geo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.tuvarna.geo.helpers.Utils
import com.tuvarna.geo.model.User

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpView() {

    val user by remember { mutableStateOf(User(0, "", "", "",false)) }

    var confirmPassword by remember { mutableStateOf("") }

    var isUsernameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var doPasswordsMatchError by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = user.username,
            onValueChange = {
                user.username = it
                isUsernameError = false
            },
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )


        if (isUsernameError) {
            Text(
                text = "Username cannot be empty",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 0.dp, 0.dp )
            )
        }

        OutlinedTextField(
            value = user.email,
            onValueChange = {
                user.email = it
                isEmailError = false},
            label = { Text("Email") },

            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        if (isEmailError) {
            Text(
                text = "Email is invalid",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
            )
        }

        OutlinedTextField(
            value = user.password,
            onValueChange = {
                user.password = it
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

        if (isPasswordError) {
            Text(
                text =
                "Password must contain minimum 8 characters long, at least 1 lowercase, uppercase and digits ",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
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
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 0.dp, 0.dp )
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
                    isUsernameError = user.username.isEmpty()
                    isEmailError = user.email.isEmpty() || Utils.isValidEmail(user.email)
                    isPasswordError = user.password.isEmpty() || Utils.isValidPassword(user.password)
                    doPasswordsMatchError = user.password != confirmPassword

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

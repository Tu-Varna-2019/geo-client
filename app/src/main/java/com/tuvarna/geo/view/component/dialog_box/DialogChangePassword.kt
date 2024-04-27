package com.tuvarna.geo.view.component.dialog_box

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.tuvarna.geo.helpers.Utils

@Composable
fun DialogChangePassword(
  title: String,
  onConfirm: (String, String) -> Unit,
  onDismiss: () -> Unit,
) {
  val openDialog = remember { mutableStateOf(true) }
  val oldPassword = remember { mutableStateOf("") }
  val newPassword = remember { mutableStateOf("") }
  val confirmNewPassword = remember { mutableStateOf("") }

  val isOldPasswordValid = Utils.isValidPassword(oldPassword.value)
  val isNewPasswordValid =
    Utils.isValidPassword(newPassword.value) || newPassword.value == oldPassword.value
  val isConfirmNewPasswordValid = confirmNewPassword.value != newPassword.value

  val isConfirmBtnDisabled = isOldPasswordValid || isNewPasswordValid || isConfirmNewPasswordValid

  if (openDialog.value) {
    AlertDialog(
      onDismissRequest = { openDialog.value = false },
      title = { Text(text = title) },
      text = {
        Column {
          Text(text = "Enter your old password")
          TextField(
            value = oldPassword.value,
            isError = isOldPasswordValid,
            onValueChange = { oldPassword.value = it },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text("Enter your old password") },
          )
          // Display error message
          if (isOldPasswordValid) {
            Text(
              text = "Old password is invalid",
              color = Color.Red,
              style = MaterialTheme.typography.bodyMedium,
            )
          }

          Text(text = "Enter your new password")
          TextField(
            value = newPassword.value,
            isError = isNewPasswordValid,
            onValueChange = { newPassword.value = it },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text("Enter your new password") },
          )
          // Display error message
          if (isNewPasswordValid) {
            val errorMessage =
              if (newPassword.value == oldPassword.value) {
                "New password cannot be the same as the old one"
              } else {
                "New password is invalid"
              }

            Text(
              text = errorMessage,
              color = Color.Red,
              style = MaterialTheme.typography.bodyMedium,
            )
          }

          Text(text = "Confirm your new password")
          TextField(
            value = confirmNewPassword.value,
            isError = isConfirmNewPasswordValid,
            onValueChange = { confirmNewPassword.value = it },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text("Confirm your new password") },
          )
          // Display error message
          if (isConfirmNewPasswordValid) {
            Text(
              text = "New password does not match",
              color = Color.Red,
              style = MaterialTheme.typography.bodyMedium,
            )
          }
        }
      },
      confirmButton = {
        Button(
          enabled = !isConfirmBtnDisabled,
          onClick = {
            onConfirm(oldPassword.value, newPassword.value)
            openDialog.value = false
          },
        ) {
          Text("Confirm")
        }
      },
      dismissButton = {
        Button(
          onClick = {
            onDismiss()
            openDialog.value = false
          }
        ) {
          Text("Cancel")
        }
      },
    )
  }
}

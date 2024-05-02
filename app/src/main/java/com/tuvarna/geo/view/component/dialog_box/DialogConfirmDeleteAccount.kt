package com.tuvarna.geo.view.component.dialog_box

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.tuvarna.geo.helpers.Utils

@Composable
fun DialogConfirmDeleteAccount(
  title: String,
  message: String,
  onConfirm: (String) -> Unit,
  onDismiss: () -> Unit,
) {
  val openDialog = remember { mutableStateOf(true) }
  var password = remember { mutableStateOf("") }

  var isPasswordError = password.value.isEmpty() || Utils.isValidPassword(password.value)
  if (openDialog.value) {
    AlertDialog(
      onDismissRequest = { openDialog.value = false },
      title = { Text(text = title) },
      text = {
        Column {
          Text(text = message)
          TextField(
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            isError = isPasswordError,
            onValueChange = { password.value = it },
            placeholder = { Text("Enter your password") },
          )
        }
      },
      confirmButton = {
        Button(
          enabled = !isPasswordError,
          onClick = {
            onConfirm(password.value)
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

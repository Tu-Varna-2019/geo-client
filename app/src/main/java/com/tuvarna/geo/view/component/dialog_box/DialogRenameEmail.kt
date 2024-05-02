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
import com.tuvarna.geo.helpers.Utils

@Composable
fun DialogRenameEmail(
  title: String,
  message: String,
  userEmail: String,
  onConfirm: (String) -> Unit,
  onDismiss: () -> Unit,
) {
  val openDialog = remember { mutableStateOf(true) }
  val email = remember { mutableStateOf(userEmail) }
  val initialEmail = userEmail

  val isConfirmBtnDisabled =
    email.value.isEmpty() || email.value == initialEmail || Utils.isValidEmail(email.value)

  if (openDialog.value) {
    AlertDialog(
      onDismissRequest = { openDialog.value = false },
      title = { Text(text = title) },
      text = {
        Column {
          Text(text = message)
          TextField(
            value = email.value,
            isError = isConfirmBtnDisabled,
            onValueChange = { email.value = it },
            placeholder = { Text("Enter your email") },
          )
          // Display error message
          if (isConfirmBtnDisabled) {
            // Display error message based on the type of error
            val errorMessage =
              if (email.value.isEmpty()) {
                "Email is empty"
              } else if (email.value == initialEmail) {
                "Email cannot be the same"
              } else {
                "Email is invalid"
              }
            Text(
              text = errorMessage,
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
            onConfirm(email.value)
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

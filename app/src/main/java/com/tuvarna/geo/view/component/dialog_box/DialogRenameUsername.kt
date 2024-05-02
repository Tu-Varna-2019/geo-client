package com.tuvarna.geo.view.component.dialog_box

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun DialogRenameUsername(
  title: String,
  message: String,
  userUsername: String,
  onConfirm: (String) -> Unit,
  onDismiss: () -> Unit,
) {
  val openDialog = remember { mutableStateOf(true) }
  val username = remember { mutableStateOf(userUsername) }
  val initialUsername = userUsername

  val isConfirmBtnDisabled = username.value.isEmpty() || username.value == initialUsername

  if (openDialog.value) {
    AlertDialog(
      onDismissRequest = { openDialog.value = false },
      title = { Text(text = title) },
      text = {
        Column {
          Text(text = message)
          TextField(
            value = username.value,
            isError = isConfirmBtnDisabled,
            onValueChange = { username.value = it },
            placeholder = { Text("Incorrect username") },
          )
        }
      },
      confirmButton = {
        Button(
          enabled = !isConfirmBtnDisabled,
          onClick = {
            onConfirm(username.value)
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

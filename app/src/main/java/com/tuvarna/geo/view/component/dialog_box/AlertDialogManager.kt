package com.tuvarna.geo.view.component.dialog_box

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun AlertDialogManager(
  title: String,
  message: String,
  onConfirm: () -> Unit,
  onDismiss: () -> Unit,
) {
  val openDialog = remember { mutableStateOf(true) }

  if (openDialog.value) {
    AlertDialog(
      onDismissRequest = { openDialog.value = false },
      title = { Text(text = title) },
      text = { Text(text = message) },
      confirmButton = {
        Button(
          onClick = {
            onConfirm()
            openDialog.value = false
          }
        ) {
          Text("Yes")
        }
      },
      dismissButton = {
        Button(
          onClick = {
            onDismiss()
            openDialog.value = false
          }
        ) {
          Text("No")
        }
      },
    )
  }
}

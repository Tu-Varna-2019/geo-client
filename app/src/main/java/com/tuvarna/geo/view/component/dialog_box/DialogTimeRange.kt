package com.tuvarna.geo.view.component.dialog_box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun convertStrToLocalDateTime(dateStr: String): LocalDateTime {
  return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTimeRange(
  title: String,
  onConfirm: (LocalDateTime, LocalDateTime) -> Unit,
  onDismiss: () -> Unit,
) {
  val currentDate = LocalDateTime.now()
  val openDialog = remember { mutableStateOf(true) }
  val dateFormatter = remember { mutableStateOf(DatePickerDefaults.dateFormatter()) }

  val dateStateStart =
    rememberDatePickerState(
      yearRange = 2000..currentDate.year,
      initialSelectedDateMillis = currentDate.toInstant(ZoneOffset.UTC).toEpochMilli(),
    )
  val dateStateEnd =
    rememberDatePickerState(
      yearRange = 2000..currentDate.year,
      initialSelectedDateMillis = currentDate.toInstant(ZoneOffset.UTC).toEpochMilli(),
    )

  val dateStateStartToLocalDate =
    LocalDateTime.ofInstant(
      Instant.ofEpochMilli(dateStateStart.selectedDateMillis ?: 0),
      ZoneOffset.UTC,
    )
  val dateStateEndToLocalDate =
    LocalDateTime.ofInstant(
      Instant.ofEpochMilli(dateStateEnd.selectedDateMillis ?: 0),
      ZoneOffset.UTC,
    )

  val compareDates =
    dateStateStartToLocalDate.year == dateStateEndToLocalDate.year &&
      kotlin.math.abs(
        dateStateEndToLocalDate.month.value - dateStateStartToLocalDate.month.value
      ) <= 3

  if (openDialog.value) {
    AlertDialog(
      onDismissRequest = { openDialog.value = false },
      title = { Text(text = title) },
      text = {
        Column {
          DatePicker(
            title = { Text(text = "Start date") },
            dateFormatter = dateFormatter.value,
            state = dateStateStart,
            showModeToggle = true,
          )
          Spacer(modifier = Modifier.height(16.dp))
          DatePicker(
            title = { Text(text = "End date") },
            dateFormatter = dateFormatter.value,
            state = dateStateEnd,
            showModeToggle = true,
          )
        }
      },
      confirmButton = {
        Button(
          enabled = compareDates,
          onClick = {
            onConfirm(dateStateStartToLocalDate, dateStateEndToLocalDate)
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

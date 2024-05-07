package com.tuvarna.geo.view.component.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tuvarna.geo.rest_api.models.LoggerDTO
import com.tuvarna.geo.view.component.table.TableKeyValueTextRow

@Composable
fun UserLogsTable(userLogs: List<LoggerDTO>) {
  Box(
    modifier =
      Modifier.padding(16.dp)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
        .border(BorderStroke(1.dp, Color(151, 212, 168)), shape = RoundedCornerShape(16.dp))
        .padding(8.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      userLogs.forEachIndexed { index, userLog ->
        TableKeyValueTextRow("Username:", userLog.username!!)
        TableKeyValueTextRow("Event:", userLog.event!!)
        TableKeyValueTextRow("Ip:", userLog.ip!!)
        TableKeyValueTextRow("Timestamp:", userLog.timestamp!!)

        if (index < userLogs.size - 1)
          Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp),
          )
      }
    }
  }
}

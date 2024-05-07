package com.tuvarna.geo.view.component.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuvarna.geo.rest_api.models.UserInfoDTO
import com.tuvarna.geo.view.component.table.TableKeyValueTextRow
import com.tuvarna.geo.viewmodel.AdminViewModel

@Composable
fun UserManagementTable(adminViewModel: AdminViewModel, users: List<UserInfoDTO>) {
  Box(
    modifier =
      Modifier.padding(16.dp)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .background(color = Color(151, 212, 168), shape = RoundedCornerShape(16.dp))
        .border(BorderStroke(1.dp, Color.Black))
        .padding(8.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      for (user: UserInfoDTO in users) {
        TableKeyValueTextRow("Username:", user.username!!)
        TableKeyValueTextRow("Email:", user.email!!)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          Text(
            text = "Block:",
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.Black,
          )
          Switch(
            checked = user.isblocked!!,
            onCheckedChange = { checked -> adminViewModel.blockUser(user.email, checked) },
            modifier = Modifier.padding(8.dp),
          )
        }
        TableKeyValueTextRow("userType:", user.userType!!)
      }
    }
  }
}

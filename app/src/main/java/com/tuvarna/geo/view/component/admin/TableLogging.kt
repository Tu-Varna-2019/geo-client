package com.tuvarna.geo.view.component.admin

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.rest_api.models.LoggerDTO
import com.tuvarna.geo.view.component.home.TableRow
import com.tuvarna.geo.viewmodel.AdminViewModel

@Composable
fun TableLogging(userLogs: List<LoggerDTO>) {
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
      for (userLog: LoggerDTO in userLogs) {
        TableRow("Username:", userLog.username!!)
        TableRow("Event:", userLog.event!!)
        TableRow("Ip:", userLog.ip!!)
        TableRow("Timestamp:", userLog.timestamp!!)
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun AdminTabOption(adminViewModel: AdminViewModel, admin: UserEntity) {
  val userLogs by adminViewModel.userLogs.collectAsState()

  var searchText by remember { mutableStateOf("") }
  var selectedTab by remember { mutableStateOf("User logs") }

  val filteredUserLogs = userLogs.filter { it.username!!.contains(searchText, ignoreCase = true) }

  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    Text(
      text = "Logs",
      color = Color.Black,
      style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.SemiBold),
    )
    Column(modifier = Modifier.fillMaxWidth().padding(25.dp)) {
      TextField(
        value = searchText,
        onValueChange = { newText -> searchText = newText },
        modifier = Modifier.fillMaxWidth().padding(16.dp).fillMaxWidth(0.8f),
        shape = RoundedCornerShape(22.dp),
        leadingIcon = {
          Icon(imageVector = Icons.Default.Search, contentDescription = "Search here")
        },
        label = { Text("Search something...") },
        colors =
          TextFieldDefaults.textFieldColors(
            containerColor = Color(151, 212, 168),
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
          ),
        singleLine = true,
      )

      Spacer(modifier = Modifier.height(16.dp))
      TabBar(
        selectedTab = selectedTab,
        userType = admin.username,
        onTabSelected = { selectedTab = it },
      )

      when (selectedTab) {
        "User logs" -> {
          LaunchedEffect(key1 = Unit) { adminViewModel.fetchLogs("customer") }
          TableLogging(userLogs = filteredUserLogs)
        }
        "Admin logs" -> {
          LaunchedEffect(key1 = Unit) { adminViewModel.fetchLogs("admin") }
          TableLogging(userLogs = filteredUserLogs)
        }
        "User management" -> {
          // adminViewModel.fetchLogs("dbt")
          TableLogging(userLogs = filteredUserLogs)
        }
        else -> {}
      }
      Button(
        onClick = { /*REMINDER: PROHIBIT ADMINS TO EXPORT LOGS OLDER THAN 3 MONTHS*/ },
        modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.CenterHorizontally),
        colors = ButtonDefaults.buttonColors(containerColor = Color(151, 212, 168)),
      ) {
        Icon(imageVector = Icons.Default.Done, contentDescription = "Export")
        Text(text = "Export logs to CSV")
      }
    }
  }
}

@Composable
fun TabBar(selectedTab: String, userType: String, onTabSelected: (String) -> Unit) {
  // TODO: Please improve this
  val tabOptions =
    if (userType != "admin") listOf("User logs", "Admin logs", "User management")
    else listOf("User logs", "User management")

  TabRow(selectedTabIndex = tabOptions.indexOf(selectedTab)) {
    tabOptions.forEachIndexed { index, text ->
      Tab(selected = selectedTab == text, onClick = { onTabSelected(text) }, text = { Text(text) })
    }
  }
}

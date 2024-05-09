package com.tuvarna.geo.view.component.admin

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.view.component.dialog_box.DialogTimeRange
import com.tuvarna.geo.view.component.dialog_box.convertStrToLocalDateTime
import com.tuvarna.geo.viewmodel.AdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun AdminPanel(navController: NavController, adminViewModel: AdminViewModel, admin: UserEntity) {
  val showExportLogsTimeRangeDialog = remember { mutableStateOf(false) }
  val userLogs by adminViewModel.userLogs.collectAsState()
  val users by adminViewModel.userInfos.collectAsState()

  val context = LocalContext.current
  var userLogSorted by remember { mutableStateOf(userLogs) }
  val exportCsvLauncher =
    rememberLauncherForActivityResult(contract = ActivityResultContracts.CreateDocument()) { uri ->
      uri?.let { adminViewModel.exportTableToCSV(context, userLogSorted, it) }
    }
  var showDropDownMenu by remember { mutableStateOf(false) }

  var searchText by remember { mutableStateOf("") }
  var selectedTab by remember { mutableStateOf("User logs") }
  val filteredUserLogs = userLogs.filter { it.username!!.contains(searchText, ignoreCase = true) }
  val filteredUsers = users.filter { it.email!!.contains(searchText, ignoreCase = true) }

  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    Text(
      text = "Logs",
      color = Color.Black,
      style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.SemiBold),
    )

    Button(
      modifier = Modifier.align(Alignment.TopStart),
      onClick = {
        adminViewModel.logUserViewNavigation("ProfileView")
        navController.popBackStack()
      },
      colors =
        ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Color.Transparent),
    ) {
      Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
    }

    Column(modifier = Modifier.fillMaxWidth().padding(25.dp)) {
      Spacer(modifier = Modifier.height(16.dp))
      TabBar(
        selectedTab = selectedTab,
        userType = admin.username,
        onTabSelected = { selectedTab = it },
      )
      Spacer(modifier = Modifier.height(16.dp))

      Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        TextField(
          value = searchText,
          onValueChange = { newText -> searchText = newText },
          modifier = Modifier.weight(1f),
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
        Box {
          IconButton(
            onClick = { showDropDownMenu = !showDropDownMenu }
            //  modifier = Modifier.weight(0.5f),
          ) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "SotBy")
          }

          Spacer(modifier = Modifier.height(20.dp))
          MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(surface = Color(151, 212, 168))
          ) {
            DropdownMenu(
              expanded = showDropDownMenu,
              onDismissRequest = { showDropDownMenu = false },
            ) {
              DropdownMenuItem(onClick = { /**/ }, text = { Text("Create new chat") })
              DropdownMenuItem(onClick = { /**/ }, text = { Text("Add friend") })
            }
          }
        }
      }
      Box(modifier = Modifier.weight(1f)) {
        when (selectedTab) {
          "User logs" -> {
            LaunchedEffect(key1 = Unit) { adminViewModel.fetchLogs("customer") }
            UserLogsTable(userLogs = filteredUserLogs)
          }
          "Admin logs" -> {
            LaunchedEffect(key1 = Unit) { adminViewModel.fetchLogs("admin") }
            UserLogsTable(userLogs = filteredUserLogs)
          }
          "User management" -> {
            LaunchedEffect(key1 = Unit) { adminViewModel.getUsers("customer") }
            UserManagementTable(adminViewModel = adminViewModel, users = filteredUsers)
          }
          else -> {}
        }
      }

      if (showExportLogsTimeRangeDialog.value) {
        DialogTimeRange(
          title = "Choose time range to export logs",
          onConfirm = { dateStart, dateEnd ->
            userLogSorted =
              userLogs.filter {
                val logTimestamp = convertStrToLocalDateTime(it.timestamp!!)
                Timber.d(
                  "Timestamp of the sort: ${logTimestamp} ($dateStart, $dateEnd) = ${logTimestamp.isAfter(dateStart) && logTimestamp.isBefore(dateEnd)}"
                )
                logTimestamp.isAfter(dateStart) && logTimestamp.isBefore(dateEnd)
              }
            CoroutineScope(Dispatchers.Main).launch {
              exportCsvLauncher.launch("output.csv")
              showExportLogsTimeRangeDialog.value = false
            }
          },
          onDismiss = { showExportLogsTimeRangeDialog.value = false },
        )
      }
      if (selectedTab != "User management") {
        Button(
          onClick = { showExportLogsTimeRangeDialog.value = true },
          modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.CenterHorizontally),
          colors = ButtonDefaults.buttonColors(containerColor = Color(151, 212, 168)),
        ) {
          Icon(imageVector = Icons.Default.Done, contentDescription = "Export")
          Text(text = "Export logs to CSV")
        }
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
    tabOptions.forEachIndexed { _, text ->
      Tab(selected = selectedTab == text, onClick = { onTabSelected(text) }, text = { Text(text) })
    }
  }
}

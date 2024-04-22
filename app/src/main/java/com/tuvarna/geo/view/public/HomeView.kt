package com.tuvarna.geo.view.public

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.storage.UserSessionStorage
import com.tuvarna.geo.storage.UserStorage
import com.tuvarna.geo.view.component.accessibility.LoadingIndicator
import com.tuvarna.geo.viewmodel.HomeViewModel

@Composable
fun HomeView(navController: NavController, userSessionStorage: UserSessionStorage) {

  val homeViewModel = hiltViewModel<HomeViewModel>()
  val userProps: UserStorage =
    userSessionStorage.readUserProps().collectAsState(initial = UserStorage(0, "", "", "")).value
  val user by remember { mutableStateOf(UserEntity(0, "", "", "", false)) }

  user.id = userProps.id
  user.username = userProps.username
  user.email = userProps.email
  user.usertype.type = userProps.userType

  LoadingIndicator(
    stateFlow = homeViewModel.stateFlow.collectAsState().value,
    navController = navController,
    route = "",
  )

  Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
    Column(
      modifier = Modifier.padding(5.dp).fillMaxWidth(0.8f),
      verticalArrangement = Arrangement.Center,
    ) {
      Text(
        text = user.id.toString() + user.username + " " + user.email + " " + user.usertype.type,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
      )

      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

package com.tuvarna.geo.view.public

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.storage.UserSessionStorage
import com.tuvarna.geo.storage.UserStorage
import com.tuvarna.geo.view.component.accessibility.LoadingIndicator
import com.tuvarna.geo.view.private.GeoMap
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

  GeoMap(navController, user)
}

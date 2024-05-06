package com.tuvarna.geo.view.private

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.entity.UserTypeEntity
import com.tuvarna.geo.storage.UserSessionStorage
import com.tuvarna.geo.storage.UserStorage
import com.tuvarna.geo.view.component.accessibility.LoadingIndicator
import com.tuvarna.geo.view.component.admin.AdminTabOption
import com.tuvarna.geo.viewmodel.AdminViewModel

@Composable
fun AdminView(navController: NavController, userSessionStorage: UserSessionStorage) {

  val adminViewModel = hiltViewModel<AdminViewModel>()
  val userProps: UserStorage =
    userSessionStorage.readUserProps().collectAsState(initial = UserStorage(0, "", "", "")).value
  val admin = UserEntity(userProps.id, userProps.username, userProps.email, "", false)
  admin.usertype = UserTypeEntity(0, userProps.userType)

  LoadingIndicator(
    stateFlow = adminViewModel.stateFlow.collectAsState().value,
    navController = navController,
    route = "",
  )

  AdminTabOption(adminViewModel = adminViewModel, admin = admin)
}

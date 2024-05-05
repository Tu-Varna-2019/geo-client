package com.tuvarna.geo.view.private

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tuvarna.geo.view.component.accessibility.LoadingIndicator
import com.tuvarna.geo.viewmodel.AdminViewModel

@Composable
fun AdminView(navController: NavController) {

  val adminViewModel = hiltViewModel<AdminViewModel>()

  LoadingIndicator(
    stateFlow = adminViewModel.stateFlow.collectAsState().value,
    navController = navController,
    route = "",
  )
  Text(text = "admin")
}

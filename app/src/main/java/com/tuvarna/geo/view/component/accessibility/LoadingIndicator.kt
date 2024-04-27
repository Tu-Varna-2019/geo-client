package com.tuvarna.geo.view.component.accessibility

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.tuvarna.geo.controller.UIFeedback
import com.tuvarna.geo.view.component.dialog_box.SnackbarManager

@Composable
fun LoadingIndicator(stateFlow: UIFeedback, navController: NavController, route: String = "") {

  when (stateFlow.state) {
    UIFeedback.States.Waiting -> {
      Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center,
      ) {
        CircularProgressIndicator()
      }
    }
    else -> Unit
  }
  if (stateFlow.message.isNotEmpty()) {
    LaunchedEffect(stateFlow.message) {
      SnackbarManager.showSnackbar(stateFlow.message)
      if (route.isNotEmpty() && stateFlow.state != UIFeedback.States.Failed)
        navController.navigate(route)
    }
  }
}

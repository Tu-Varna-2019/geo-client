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
import com.example.app_iliyan.view.components.dialog_box.SnackbarManager
import com.tuvarna.geo.controller.ApiResponse

@Composable
fun LoadingIndicator(uiState: ApiResponse<*>, navController: NavController, route: String) {
  when (uiState) {
    is ApiResponse.Waiting -> {
      Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center,
      ) {
        CircularProgressIndicator()
      }
    }
    is ApiResponse.Parse<*> -> {
      LaunchedEffect(uiState.message) {
        SnackbarManager.showSnackbar(uiState.message)
        navController.navigate(route)
      }
    }
    is ApiResponse.Parse -> {
      LaunchedEffect(uiState.message) { SnackbarManager.showSnackbar(uiState.message.toString()) }
    }
    else -> Unit
  }
}

package com.tuvarna.geo.view.component.accessibility

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.app_iliyan.view.components.dialog_box.SnackbarManager
import com.tuvarna.geo.viewmodel.ui.UiState

@Composable
fun LoadingIndicator(uiState: State<UiState>, navController: NavController, route: String) {
  val state = uiState.value

  when (state) {
    is UiState.Loading -> {
      Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center,
      ) {
        CircularProgressIndicator()
      }
    }
    is UiState.Success -> {

      LaunchedEffect(state.message) {
        SnackbarManager.showSnackbar(state.message)
        navController.navigate(route)
      }
    }
    is UiState.Error -> {

      LaunchedEffect(state.message) { SnackbarManager.showSnackbar(state.message) }
    }
    else -> Unit
  }
}

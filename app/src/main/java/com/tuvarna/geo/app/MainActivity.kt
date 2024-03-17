package com.tuvarna.geo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.app_iliyan.view.components.dialog_box.SnackbarManager
import com.tuvarna.geo.controller.NavController
import com.tuvarna.geo.ui.theme.FeatherAndroidTasksTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent { SnackbarManager.ScaffoldSnackbar { FeatherAndroidTasksTheme { NavController() } } }
  }
}

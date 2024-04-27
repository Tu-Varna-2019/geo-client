package com.tuvarna.geo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tuvarna.geo.controller.NavController
import com.tuvarna.geo.storage.UserSessionStorage
import com.tuvarna.geo.ui.theme.FeatherAndroidTasksTheme
import com.tuvarna.geo.view.component.dialog_box.SnackbarManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject lateinit var userSessionStorage: UserSessionStorage

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      SnackbarManager.ScaffoldSnackbar {
        FeatherAndroidTasksTheme { NavController(userSessionStorage) }
      }
    }
  }
}

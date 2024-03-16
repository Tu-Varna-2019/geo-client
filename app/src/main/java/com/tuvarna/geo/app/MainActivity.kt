package com.tuvarna.geo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tuvarna.geo.ui.theme.FeatherAndroidTasksTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent { FeatherAndroidTasksTheme { NavController() } }
  }
}

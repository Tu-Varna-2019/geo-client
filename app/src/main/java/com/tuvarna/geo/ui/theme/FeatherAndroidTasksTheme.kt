package com.tuvarna.geo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FeatherAndroidTasksTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = MaterialTheme.colorScheme.copy(primary = DefaultProjectColor),
    typography = MaterialTheme.typography,
    shapes = MaterialTheme.shapes,
    content = content,
  )
}

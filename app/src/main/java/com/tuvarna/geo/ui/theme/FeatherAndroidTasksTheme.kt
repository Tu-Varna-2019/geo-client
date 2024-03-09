package com.tuvarna.geo.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun FeatherAndroidTasksTheme(content: @Composable () -> Unit) {
    // Customize your theme colors here
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(primary = BabyblueColor),
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}

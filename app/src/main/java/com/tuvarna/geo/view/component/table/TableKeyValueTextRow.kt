package com.tuvarna.geo.view.component.table

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableKeyValueTextRow(name: String, value: String) {
  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
    Text(
      text = name,
      fontSize = 15.sp,
      textAlign = TextAlign.Start,
      modifier = Modifier.padding(vertical = 8.dp),
      color = Color.Black,
    )
    Text(
      text = value,
      fontSize = 15.sp,
      textAlign = TextAlign.End,
      modifier = Modifier.padding(vertical = 8.dp),
      color = Color.Black,
    )
  }
}

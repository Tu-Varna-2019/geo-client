package com.tuvarna.geo.view.component.table

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableKeyValueTextRow(name: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = name,
      fontSize = 15.sp,
      textAlign = TextAlign.Start,
      modifier = Modifier.padding(vertical = 8.dp),
      color = Color.Black,
      fontWeight = FontWeight.Bold,
    )
    Text(
      text = value,
      fontSize = 15.sp,
      textAlign = TextAlign.End,
      modifier = Modifier.padding(vertical = 8.dp),
      overflow = TextOverflow.Ellipsis,
      color = Color.Black,
    )
  }
}

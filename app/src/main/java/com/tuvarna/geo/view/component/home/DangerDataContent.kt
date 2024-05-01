package com.tuvarna.geo.view.component.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuvarna.geo.rest_api.models.Earthquake
import com.tuvarna.geo.rest_api.models.Soil

@Composable
fun SoilDataContent(soil: Soil) {
  if (soil.gid != null) {
    Box(
      modifier =
        Modifier.padding(16.dp)
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .background(color = Color(151, 212, 168), shape = RoundedCornerShape(16.dp))
          .border(BorderStroke(1.dp, Color.Black))
          .padding(8.dp)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        TableRow("Gid:", soil.gid.toString())
        TableRow("Snum:", soil.snum.toString())
        TableRow("Faosoil:", soil.faosoil ?: "None")
        TableRow("Domsoi:", soil.domsoi ?: "None")
        TableRow("Phases:", soil.phase1 + " " + soil.phase2)
        TableRow("Misclus:", soil.misclu1 + " " + soil.misclu2)
        TableRow("Country:", soil.country ?: "None")
        TableRow("SQ Kilometers:", soil.sqkm.toString())
      }
    }
  }
}

@Composable
 fun TableRow(name: String, value: String) {
  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
    Text(
      text = name,
      fontSize = 18.sp,
      textAlign = TextAlign.Start,
      modifier = Modifier.padding(vertical = 8.dp),
      color = Color.Black,
    )
    Text(
      text = value,
      fontSize = 18.sp,
      textAlign = TextAlign.End,
      modifier = Modifier.padding(vertical = 8.dp),
      color = Color.Black,
    )
  }
}

@Composable
fun EarthquakeDataContent(earthquake: Earthquake) {
  if (earthquake.id != null) {
    Box(
      modifier =
        Modifier.padding(16.dp)
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .background(color = Color(151, 212, 168), shape = RoundedCornerShape(16.dp))
          .border(BorderStroke(1.dp, Color.Black))
          .padding(8.dp)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        TableRow("Id:", earthquake.id.toString())
        TableRow("Frequency: ", earthquake.dn.toString())
      }
    }
  }
}

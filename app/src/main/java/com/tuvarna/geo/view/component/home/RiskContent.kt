package com.tuvarna.geo.view.component.home

import androidx.compose.foundation.background
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
import com.tuvarna.geo.config.SoilTypesDTO
import java.util.Locale

@Composable
fun SoilTableContent(soil: Soil, soilTypes: SoilTypesDTO) {

    val soilType = soilTypes::class.members.firstOrNull { it.name == soil.domsoi }?.call(soilTypes) as? String
    val formattedSoilType = soilType?.lowercase()?.split(' ')?.joinToString(" ") {
        it.replaceFirstChar { if(it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    val country = soil.country
    val formattedCountry = country?.lowercase()?.split(' ')?.joinToString(" ") {
        it.replaceFirstChar { if(it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

  if (soil.gid != null) {
    Box(
      modifier =
        Modifier.padding(16.dp)
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .background(color = Color(151, 212, 168), shape = RoundedCornerShape(16.dp))

          .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(8.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)) {
        TableRow("Gid:", soil.gid.toString())
        TableRow("Snum:", soil.snum.toString())
        TableRow("Domsoi:", formattedSoilType ?: "No data")
          if (!soil.phase1.isNullOrBlank() || !soil.phase2.isNullOrBlank()) {
              TableRow("Phases:", listOfNotNull(soil.phase1, soil.phase2).joinToString(" "))
          }
          if (!soil.misclu1.isNullOrBlank() || !soil.misclu2.isNullOrBlank()) {
              TableRow("Misclus:", listOfNotNull(soil.misclu1, soil.misclu2).joinToString(" "))
          }
        TableRow("Country:", formattedCountry ?: "No data")
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
      modifier = Modifier.padding(vertical = 4.dp),
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
fun EarthquakeTableContent(earthquake: Earthquake) {
  if (earthquake.id != null) {
    Box(
      modifier =
        Modifier.padding(16.dp)
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .background(color = Color(151, 212, 168), shape = RoundedCornerShape(16.dp))
          .padding(8.dp)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        TableRow("Id:", earthquake.id.toString())
        TableRow("Frequency: ", earthquake.dn.toString())
      }
    }
  }
}

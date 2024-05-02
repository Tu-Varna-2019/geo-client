package com.tuvarna.geo.view.component.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.tuvarna.geo.rest_api.models.Soil

@Composable
fun SoilDetails(mapMarkersToDangers: MutableMap<LatLng, Soil>) {
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
      mapMarkersToDangers.forEach { (key, value) ->
        TableRow("Latitude:", key.latitude.toString())
        TableRow("Longitude:", key.longitude.toString())

        TableRow("Gid:", value.gid.toString())
        TableRow("Snum:", value.snum.toString())
      }
    }
  }
}

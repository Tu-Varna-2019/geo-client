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
import com.tuvarna.geo.rest_api.models.Soil
import com.tuvarna.geo.view.component.table.TableKeyValueTextRow

@Composable
fun SoilTable(soil: Soil) {
  if (soil.gid != null) {

    Box(
      modifier =
        Modifier.padding(16.dp)
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .background(color = Color(151, 212, 168), shape = RoundedCornerShape(16.dp))
          .border(BorderStroke(1.dp, Color(151, 212, 168)), shape = RoundedCornerShape(16.dp))
          .padding(8.dp)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        // TableKeyValueTextRow("Gid:", soil.gid.toString())
        TableKeyValueTextRow("Sequential code:", soil.snum.toString())
        TableKeyValueTextRow("Dominant soil:", soil.faosoil ?: "None") // Bk
        TableKeyValueTextRow("Texture class:", soil.faosoil ?: "None") // 2/3
        TableKeyValueTextRow("Slope class:", soil.faosoil ?: "None") // ab

        if (soil.phase1 != null || soil.phase2 != null)
          TableKeyValueTextRow("Phases:", "${soil.phase1 ?: ""} ${soil.phase2 ?: ""}")
        if (soil.misclu1 != null || soil.misclu2 != null)
          TableKeyValueTextRow("Miscues:", "${soil.misclu1 ?: ""} ${soil.misclu2 ?: ""}")

        if (soil.permafrost != null) {
          TableKeyValueTextRow(
            "Permafrost:",
            if (soil.permafrost == "1") "Permafrost" else "Discontinuous permafrost",
          )
        }
        TableKeyValueTextRow("Country:", soil.country?.lowercase()?.capitalize() ?: "None")
        TableKeyValueTextRow("Square kilometers:", soil.sqkm.toString())
      }
    }
  }
}

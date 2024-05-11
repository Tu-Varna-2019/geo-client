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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tuvarna.geo.mapper.SoilMapper
import com.tuvarna.geo.rest_api.models.Soil
import com.tuvarna.geo.view.component.table.TableKeyValueTextRow

@Composable
fun SoilTable(soil: Soil) {
  if (soil.gid != null) {
    val soilMapper =
      SoilMapper(
        context = LocalContext.current,
        faosoi = soil.faosoil!!,
        domsoi = soil.domsoi,
        phases = soil.phase1 ?: soil.phase2,
        misc1 = soil.misclu1,
        misc2 = soil.misclu2,
      )
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
        TableKeyValueTextRow("Dominant soil:", soilMapper.domsoiFullDescription)

        if (soilMapper.textureClass != "")
          TableKeyValueTextRow("Texture class:", soilMapper.textureClass)
        if (soilMapper.slopeClass != "") TableKeyValueTextRow("Slope class:", soilMapper.slopeClass)

        if (soilMapper.mappingUnit != "")
          TableKeyValueTextRow("Mapping unit:", soilMapper.mappingUnit)

        if (soilMapper.phaseFullDescription != "")
          TableKeyValueTextRow("Phases:", soilMapper.phaseFullDescription)
        if (soilMapper.misc1FullDescription != "")
          TableKeyValueTextRow("Miscellaneous 1:", soilMapper.misc1FullDescription)
        if (soilMapper.misc2FullDescription != "")
          TableKeyValueTextRow("Miscellaneous 2:", soilMapper.misc2FullDescription)

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

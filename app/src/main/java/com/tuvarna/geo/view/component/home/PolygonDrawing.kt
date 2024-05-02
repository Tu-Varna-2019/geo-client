package com.tuvarna.geo.view.component.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.Polygon
import com.tuvarna.geo.entity.PointEntity

@Composable
@GoogleMapComposable
fun PolygonDrawing(
  pointEntity: PointEntity,
  sqkm: Double,
  polygonColor: Color = Color(120, 85, 84),
) {

  Polygon(
    points = pointEntity.convertPositionToLocations(sqkm),
    strokeColor = MaterialTheme.colorScheme.tertiary,
    strokeWidth = 3F,
    fillColor = polygonColor,
  )
}

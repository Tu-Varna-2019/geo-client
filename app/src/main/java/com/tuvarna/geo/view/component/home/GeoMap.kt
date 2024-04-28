package com.tuvarna.geo.view.component.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.tuvarna.geo.entity.DataTypeOptions
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.view.theme.MapsTheme
import timber.log.Timber

@SuppressLint("RestrictedApi")
@Composable
fun GeoMap(navController: NavController, user: UserEntity) {
  val cameraPositionState = rememberCameraPositionState { null }
  var markerPositions by remember { mutableStateOf(listOf<LatLng>()) }
  var clickedMarker by remember { mutableStateOf(LatLng(0.0, 0.0)) }

  val uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
  val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.TERRAIN)) }

  Box(Modifier.fillMaxSize()) {
    MapsTheme {
      GoogleMap(
        modifier = Modifier.matchParentSize(),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        onMapClick = { latLng ->
          markerPositions = markerPositions + latLng
          Timber.d("LatLng was invoked ${latLng}")
        },
      ) {
        markerPositions.forEach { position ->
          Marker(
            state = rememberMarkerState(position = position),
            onClick = {
              clickedMarker = position
              cameraPositionState.move(CameraUpdateFactory.newLatLng(position))
              true
            },
            title = "Soil/Earthquake",
          )
        }
      }
    }
  }
  GeoBottomSheet(clickedMarker, navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GeoBottomSheet(clickedMarker: LatLng, navController: NavController) {
  var detailsState by remember { mutableStateOf(false) }

  BottomSheetScaffold(
    sheetContent = {
      if (detailsState) {
        GeoMapDetails(clickedMarker = clickedMarker)
        Spacer(modifier = Modifier.height(56.dp))
      }
      Divider(
        color = Color.Gray,
        thickness = 2.dp,
        modifier = Modifier.width(260.dp).align(Alignment.CenterHorizontally),
      )
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
      ) {
        Spacer(modifier = Modifier.height(30.dp))
        Button(
          colors = ButtonDefaults.buttonColors(containerColor = Color(0, 69, 12)),
          onClick = { detailsState = !detailsState },
        ) {
          Icon(Icons.Filled.List, contentDescription = null)
        }
        Button(
          colors = ButtonDefaults.buttonColors(containerColor = Color(0, 69, 12)),
          onClick = { navController.navigate("profile") },
        ) {
          Icon(Icons.Filled.AccountCircle, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  ) {}
}

@Composable
fun GeoMapDetails(clickedMarker: LatLng) {
  var dataTypeChoice by remember { mutableStateOf(DataTypeOptions.None) }

  when (dataTypeChoice) {
    DataTypeOptions.None -> {
      Box(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "Choose a type",
          textAlign = TextAlign.Center,
          modifier = Modifier.align(Alignment.Center),
        )
      }
      Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
      ) {
        Box(
          modifier =
            Modifier.weight(1f)
              .height(40.dp)
              .clickable { dataTypeChoice = DataTypeOptions.Soil }
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0, 69, 12))
        ) {
          Text(text = "Soil", color = Color.White, modifier = Modifier.align(Alignment.Center))
        }
        Box(
          modifier =
            Modifier.weight(1f)
              .height(40.dp)
              .clickable { dataTypeChoice = DataTypeOptions.Earthquake }
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0, 69, 12))
        ) {
          Text(
            text = "Earthquake",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
          )
        }
      }
    }
    DataTypeOptions.Soil -> {
      Text(text = "Soil")
    }
    DataTypeOptions.Earthquake -> {
      Text(text = "Earthquake")
    }
  }
}

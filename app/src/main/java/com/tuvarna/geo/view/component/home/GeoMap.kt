package com.tuvarna.geo.view.component.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.tuvarna.geo.rest_api.models.DangerDTO
import com.tuvarna.geo.view.component.accessibility.LoadingIndicator
import com.tuvarna.geo.view.theme.MapsTheme
import com.tuvarna.geo.viewmodel.HomeViewModel
import timber.log.Timber

val defaultLatLng: LatLng = LatLng(0.0, 0.0)
var markerPositions = mutableStateOf(listOf<LatLng>())
var clickedMarker = mutableStateOf(defaultLatLng)
var topbarTitle = mutableStateOf("Geo")
var detailsState = mutableStateOf(false)

@SuppressLint("RestrictedApi")
@Composable
fun GeoMap(navController: NavController, user: UserEntity) {
  val cameraPositionState = rememberCameraPositionState { null }
  val uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
  val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.TERRAIN)) }

  Box(Modifier.fillMaxSize()) {
    MapsTheme {
      GoogleMap(
        modifier = Modifier.matchParentSize(),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        onMapLongClick = { latLng ->
          markerPositions.value += latLng
          clickedMarker.value = latLng
          cameraPositionState.move(CameraUpdateFactory.newLatLng(latLng))
          detailsState.value = !detailsState.value
          Timber.d("LatLng was invoked ${latLng}")
        },
      ) {
        markerPositions.value.forEach { position ->
          Marker(
            state = rememberMarkerState(position = position),
            onClick = {
              clickedMarker.value = position
              cameraPositionState.move(CameraUpdateFactory.newLatLng(position))
              detailsState.value = !detailsState.value
              true
            },
            title = "Soil/Earthquake",
            anchor = Offset(0.10f, 0.10f),
            snippet = "snippet",
          )
        }
      }
    }
  }
  GeoBottomSheet(navController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GeoBottomSheet(navController: NavController) {

  BottomSheetScaffold(
    topBar = {
      TopAppBar(
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
          ),
        modifier = Modifier.height(56.dp),
        title = {
          Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(11.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(topbarTitle.value)

            Button(
              colors = ButtonDefaults.buttonColors(containerColor = Color(0, 69, 12)),
              onClick = { navController.navigate("profile") },
            ) {
              Icon(Icons.Filled.AccountCircle, contentDescription = null)
            }
          }
        },
      )
    },
    sheetContent = {
      if (detailsState.value) {
        GeoMapDetails()
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
        if (!clickedMarker.value.equals(defaultLatLng)) {
          Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0, 69, 12)),
            onClick = {
              markerPositions.value = markerPositions.value.filter { it != clickedMarker.value }
              clickedMarker.value = defaultLatLng
            },
          ) {
            Icon(Icons.Filled.Clear, contentDescription = null)
          }
        }
        Spacer(modifier = Modifier.height(16.dp))
      }
    },
  ) {}
}

@Composable
fun GeoMapDetails() {
  var dataTypeChoice by remember { mutableStateOf(DataTypeOptions.None) }
  val homeViewModel = hiltViewModel<HomeViewModel>()

  when (dataTypeChoice) {
    DataTypeOptions.None -> {
      Box(modifier = Modifier.fillMaxWidth()) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.fillMaxWidth(),
        ) {
          LoadingIndicator(
            stateFlow = homeViewModel.stateFlow.collectAsState().value,
            navController = null,
          )
          topbarTitle.value = "Choose a type"

          Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
          ) {
            Button(
              colors = ButtonDefaults.buttonColors(containerColor = Color(0, 69, 12)),
              onClick = { dataTypeChoice = DataTypeOptions.Soil },
              shape = RoundedCornerShape(10.dp),
            ) {
              Text(text = "Soil", color = Color.White)
            }
            Button(
              colors = ButtonDefaults.buttonColors(containerColor = Color(0, 69, 12)),
              onClick = { dataTypeChoice = DataTypeOptions.Earthquake },
              shape = RoundedCornerShape(10.dp),
            ) {
              Text(text = "Earthquake", color = Color.White)
            }
          }
        }
      }
    }
    DataTypeOptions.Soil -> {
      LaunchedEffect(key1 = Unit) {
        homeViewModel.retrieveSoil(
          DangerDTO(clickedMarker.value.latitude, clickedMarker.value.longitude)
        )
      }
      val soil = homeViewModel.soil.collectAsState()
      Text(text = soil.value.country ?: "")
    }
    DataTypeOptions.Earthquake -> {
      LaunchedEffect(key1 = Unit) {
        homeViewModel.retrieveEarthquake(
          DangerDTO(clickedMarker.value.latitude, clickedMarker.value.longitude)
        )
      }
      val earthquake = homeViewModel.earthquake.collectAsState()
      Text(text = earthquake.value.id.toString())
    }
  }
}

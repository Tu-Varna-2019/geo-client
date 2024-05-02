package com.tuvarna.geo.view.component.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.tuvarna.geo.R
import com.tuvarna.geo.entity.DangerOptions
import com.tuvarna.geo.entity.PointEntity
import com.tuvarna.geo.entity.UserMarkerState
import com.tuvarna.geo.rest_api.models.DangerDTO
import com.tuvarna.geo.rest_api.models.Soil
import com.tuvarna.geo.view.component.accessibility.LoadingIndicator
import com.tuvarna.geo.view.theme.MapsTheme
import com.tuvarna.geo.viewmodel.HomeViewModel
import timber.log.Timber

private val userMarkerState: UserMarkerState = UserMarkerState()
private val uiColorStyle: Color = Color(151, 212, 168)
private var soil = mutableStateOf<Soil>(Soil())

@SuppressLint("RestrictedApi")
@Composable
fun GeoMap(navController: NavController) {
  val cameraPositionState = rememberCameraPositionState {}
  val homeViewModel = hiltViewModel<HomeViewModel>()

  Box(Modifier.fillMaxSize()) {
    MapsTheme {
      GoogleMap(
        modifier = Modifier.matchParentSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapType = MapType.TERRAIN),
        uiSettings = MapUiSettings(compassEnabled = false),
        onMapLongClick = { latLng ->
          userMarkerState.markerPositions.value += latLng
          userMarkerState.clickedMarker.value = latLng
          cameraPositionState.move(CameraUpdateFactory.newLatLng(latLng))
          userMarkerState.topBarTitleText.value = "Choose a type"
          Timber.d("LatLng was invoked ${latLng}")
        },
      ) {
        userMarkerState.markerPositions.value.forEach { position ->
          val pointEntity: PointEntity = PointEntity(position)

          when (userMarkerState.dangerUserChoice.value) {
            DangerOptions.Soil -> {
              val soil = homeViewModel.soil.collectAsState().value
              if (soil.sqkm != null && soil.domsoi != null)
                PolygonDrawing(
                  PointEntity(position),
                  soil.sqkm,
                  pointEntity.getColorToSoilType(soil.domsoi),
                )
            }
            DangerOptions.Earthquake -> {
              val earthquake = homeViewModel.earthquake.collectAsState().value
              if (earthquake.dn != null) PolygonDrawing(PointEntity(position), earthquake.dn * 10.0)
            }
            else -> {}
          }
          Marker(
            state = rememberMarkerState(position = position),
            onClick = {
              userMarkerState.clickedMarker.value = position
              cameraPositionState.move(CameraUpdateFactory.newLatLng(position))
              true
            },
            title = "Soil/Earthquake",
            anchor = Offset(0.10f, 0.10f),
          )
        }
      }
    }
  }
  TopBottomBar(homeViewModel, navController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBottomBar(homeViewModel: HomeViewModel, navController: NavController) {
  val bottomsheetState = rememberBottomSheetScaffoldState()

  BottomSheetScaffold(
    scaffoldState = bottomsheetState,
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
            Text(userMarkerState.topBarTitleText.value, color = uiColorStyle)

            Button(
              colors = ButtonDefaults.buttonColors(containerColor = uiColorStyle),
              onClick = { navController.navigate("profile") },
            ) {
              Icon(Icons.Filled.AccountCircle, contentDescription = null)
            }
          }
        },
      )
    },
    sheetContent = { BottomBar(homeViewModel = homeViewModel, bottomsheetState = bottomsheetState) },
  ) {
    LoadingIndicator(
      stateFlow = homeViewModel.stateFlow.collectAsState().value,
      navController = null,
    )
  }
}

@Composable
fun DangerBottomBarContent(homeViewModel: HomeViewModel) {
  when (userMarkerState.dangerUserChoice.value) {
    DangerOptions.None -> {
      Box(modifier = Modifier.fillMaxWidth()) {
        userMarkerState.topBarTitleText.value = "Choose a type"
      }
    }
    DangerOptions.Soil -> {

      LaunchedEffect(key1 = Unit) {
        homeViewModel.retrieveSoil(
          DangerDTO(
            userMarkerState.clickedMarker.value.latitude,
            userMarkerState.clickedMarker.value.longitude,
          )
        )
      }
      soil.value = homeViewModel.soil.collectAsState().value
      // userMarkerState.mapMarkersToDangers[userMarkerState.clickedMarker.value] = soil
      SoilDataContent(soil = soil.value)
      userMarkerState.topBarTitleText.value = "Soil"
    }
    DangerOptions.Earthquake -> {
      LaunchedEffect(key1 = Unit) {
        homeViewModel.retrieveEarthquake(
          DangerDTO(
            userMarkerState.clickedMarker.value.latitude,
            userMarkerState.clickedMarker.value.longitude,
          )
        )
      }
      val earthquake = homeViewModel.earthquake.collectAsState()
      EarthquakeDataContent(earthquake = earthquake.value)
      userMarkerState.topBarTitleText.value = "Earthquake"
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(homeViewModel: HomeViewModel, bottomsheetState: BottomSheetScaffoldState) {
  if (userMarkerState.hasUserClickedMarker()) {
    DangerBottomBarContent(homeViewModel)
    Spacer(modifier = Modifier.height(56.dp))
  }
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth().padding(16.dp),
  ) {
    Spacer(modifier = Modifier.height(30.dp))
    //    NavigationBarItem(
    //      icon = {
    //        Icon(
    //          Icons.Filled.ArrowBack,
    //          contentDescription = null,
    //          modifier = Modifier.padding(8.dp).size(35.dp),
    //        )
    //      },
    //      label = { Text("Back") },
    //      selected = false,
    //      onClick = { /*TODO: add something  */ },
    //    )
    if (userMarkerState.hasUserClickedMarker()) {
      LaunchedEffect(key1 = Unit) { bottomsheetState.bottomSheetState.expand() }
      NavigationBar(containerColor = Color.Transparent) {
        NavigationBarItem(
          icon = {
            Image(
              painter = painterResource(R.drawable.soil),
              contentDescription = null,
              modifier = Modifier.padding(8.dp).size(35.dp),
            )
          },
          label = { Text("Soil") },
          selected = userMarkerState.dangerUserChoice.value == DangerOptions.Soil,
          onClick = {
            userMarkerState.dangerUserChoice.value =
              if (userMarkerState.dangerUserChoice.value == DangerOptions.Soil) DangerOptions.None
              else DangerOptions.Soil
          },
        )
        NavigationBarItem(
          icon = {
            Image(
              painter = painterResource(R.drawable.earthquake),
              contentDescription = null,
              modifier = Modifier.padding(8.dp).size(35.dp),
            )
          },
          label = { Text("Earthquake") },
          selected = userMarkerState.dangerUserChoice.value == DangerOptions.Earthquake,
          onClick = {
            userMarkerState.dangerUserChoice.value =
              if (userMarkerState.dangerUserChoice.value == DangerOptions.Earthquake)
                DangerOptions.None
              else DangerOptions.Earthquake
          },
        )
        NavigationBarItem(
          icon = {
            Icon(
              Icons.Filled.Clear,
              contentDescription = null,
              modifier = Modifier.padding(8.dp).size(35.dp),
            )
          },
          label = { Text("Remove") },
          selected = false,
          onClick = {
            // Remove marker from the collection of markers
            userMarkerState.markerPositions.value =
              userMarkerState.markerPositions.value.filter {
                it != userMarkerState.clickedMarker.value
              }
            userMarkerState.clickedMarker.value = userMarkerState.initialPoisition
          },
        )
      }
    } else {
      Text(text = "Nothing to see here...")
      userMarkerState.topBarTitleText.value = "Geo"
    }
    Spacer(modifier = Modifier.height(16.dp))
  }
}

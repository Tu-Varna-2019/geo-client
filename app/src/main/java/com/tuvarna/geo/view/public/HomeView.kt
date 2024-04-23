package com.tuvarna.geo.view.public

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

@Composable
fun HomeView(navController: NavController) {

  Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
    Column(
      modifier = Modifier.padding(5.dp).fillMaxWidth(0.8f),
      verticalArrangement = Arrangement.Center,
    ) {
      Text(
        text = "Home for user ",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
      )

      Spacer(modifier = Modifier.height(20.dp))
    }
    MyMapScreen()
  }
}

@Composable
fun GoogleMapView() {
  val context = LocalContext.current
  val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
  var hasPermission by remember { mutableStateOf(false) }
  var lastKnownLocation by remember { mutableStateOf<LatLng?>(null) }

  DisposableEffect(Unit) {
    if (
      ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
        PackageManager.PERMISSION_GRANTED
    ) {
      hasPermission = true
    } else {
      ActivityCompat.requestPermissions(
        context as ComponentActivity,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        1,
      )
    }

    onDispose {
      // Clean up code here if needed
    }
  }

  val mapView = remember { MapView(context) }

  if (hasPermission) {
    fusedLocationClient.lastLocation
      .addOnSuccessListener { location ->
        location?.let { lastKnownLocation = LatLng(location.latitude, location.longitude) }
      }
      .addOnFailureListener { e -> Log.e("GoogleMapView", "Failed to get last known location", e) }

    lastKnownLocation?.let { currentLocation ->
      LaunchedEffect(currentLocation) {
        mapView.getMapAsync { googleMap ->
          googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
          googleMap.addMarker(MarkerOptions().position(currentLocation).title("Your Location"))
        }
      }
    }
  } else {
    // Show some UI indicating that permission is not granted
    Text(text = "Location permission is required to view the map")
  }

  AndroidView(
    factory = { ctx ->
      mapView.apply {
        onCreate(null)
        onResume()
      }
    }
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyMapScreen() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Slide up for more info", style = MaterialTheme.typography.bodyMedium)
            }
        },
        sheetPeekHeight = 50.dp
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.weight(1f))
            GoogleMapView()

            BottomAppBar {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    }
                ) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
                Spacer(Modifier.weight(1f, true))
                Text("Map with Bottom Sheet", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


@Preview
@Composable
fun PreviewMapScreen() {
  MyMapScreen()
}

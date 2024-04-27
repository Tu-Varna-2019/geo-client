package com.tuvarna.geo.view.private

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tuvarna.geo.entity.UserEntity
import timber.log.Timber

@SuppressLint("RestrictedApi")
@Composable
fun GeoMap(navController: NavController, user: UserEntity) {
  val context = LocalContext.current
  val mapView = remember { MapView(context) }
  val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
  var lastKnownLocation by remember { mutableStateOf<LatLng?>(null) }

  if (
    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
      PackageManager.PERMISSION_GRANTED
  ) {
    fusedLocationClient.lastLocation
      .addOnSuccessListener { location ->
        location?.let { lastKnownLocation = LatLng(location.latitude, location.longitude) }
      }
      .addOnFailureListener { e -> Timber.e("GeoMap", "Failed to get last known location", e) }

    Timber.d("Latlng: ${lastKnownLocation}")
    lastKnownLocation?.let { currentLocation ->
      LaunchedEffect(currentLocation) {
        mapView.getMapAsync { googleMap ->
          googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
          googleMap.addMarker(MarkerOptions().position(currentLocation).title("Your Location"))
        }
      }
    }
  } else {
    Text(text = "Location permission is required to use Geo map!")
    ActivityCompat.requestPermissions(
      context as ComponentActivity,
      arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
      1,
    )
  }

  AndroidView(
    factory = { _ ->
      mapView.apply {
        onCreate(null)
        onResume()
      }
    }
  )
  GeoBottomSheet(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GeoBottomSheet(navController: NavController) {
  var isTextShown by remember { mutableStateOf(false) }

  BottomSheetScaffold(
    sheetContent = {
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
      ) {
        if (isTextShown) Text(text = "ok")
        Spacer(modifier = Modifier.height(30.dp))
        Button(
          colors = ButtonDefaults.buttonColors(containerColor = Color(0, 69, 12)),
          onClick = { isTextShown = true },
        ) {
          Icon(Icons.Filled.List, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(16.dp))
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

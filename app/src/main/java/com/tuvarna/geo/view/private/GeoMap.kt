package com.tuvarna.geo.view.private

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RestrictedApi")
@Composable
fun GeoMap() {
  val context = LocalContext.current
  val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
  var hasPermission by remember { mutableStateOf(false) }
  var lastKnownLocation by remember { mutableStateOf<LatLng?>(null) }

  var openBottomSheet by rememberSaveable { mutableStateOf(false) }
  var skipPartiallyExpanded by remember { mutableStateOf(false) }
  var edgeToEdgeEnabled by remember { mutableStateOf(false) }
  val bottomSheetState =
    rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

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
      .addOnFailureListener { e -> Log.e("GeoMap", "Failed to get last known location", e) }

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

  Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(4.dp)) {
    Row(
      Modifier.toggleable(
        value = skipPartiallyExpanded,
        role = Role.Checkbox,
        onValueChange = { checked -> skipPartiallyExpanded = checked },
      )
    ) {
      Checkbox(checked = skipPartiallyExpanded, onCheckedChange = null)
      Spacer(Modifier.width(16.dp))
      Text("Skip partially expanded State")
    }
    Row(
      Modifier.toggleable(
        value = edgeToEdgeEnabled,
        role = Role.Checkbox,
        onValueChange = { checked -> edgeToEdgeEnabled = checked },
      )
    ) {
      Checkbox(checked = edgeToEdgeEnabled, onCheckedChange = null)
      Spacer(Modifier.width(16.dp))
      Text("Toggle edge to edge enabled")
    }
    Button(
      onClick = { openBottomSheet = !openBottomSheet },
      modifier = Modifier.align(Alignment.CenterHorizontally),
    ) {
      Text(text = "Show Bottom Sheet")
    }
  }

  if (openBottomSheet) {
    val windowInsets = if (edgeToEdgeEnabled) WindowInsets(0) else BottomSheetDefaults.windowInsets

    ModalBottomSheet(
      onDismissRequest = { openBottomSheet = false },
      sheetState = bottomSheetState,
      windowInsets = windowInsets,
    ) {
      LazyColumn {
        items(25) {
          ListItem(
            headlineContent = { Text("Item $it") },
            leadingContent = {
              Icon(Icons.Default.Favorite, contentDescription = "Localized description")
            },
            colors =
              ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer),
          )
        }
      }
    }
  }
}

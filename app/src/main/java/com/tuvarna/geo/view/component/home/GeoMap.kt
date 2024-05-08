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
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.getValue
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
import com.tuvarna.geo.entity.PointEntity
import com.tuvarna.geo.entity.RiskChoices
import com.tuvarna.geo.entity.RiskHierarchy
import com.tuvarna.geo.entity.state.UserMarkerState
import com.tuvarna.geo.rest_api.models.Earthquake
import com.tuvarna.geo.rest_api.models.RiskDTO
import com.tuvarna.geo.rest_api.models.Soil
import com.tuvarna.geo.config.SoilTypesDTO
import com.tuvarna.geo.view.component.accessibility.LoadingIndicator
import com.tuvarna.geo.view.theme.MapsTheme
import com.tuvarna.geo.viewmodel.HomeViewModel

private val userMarkerState: UserMarkerState = UserMarkerState()
private val uiColorStyle: Color = Color(151, 220, 168)

@SuppressLint("RestrictedApi")
@Composable
fun GeoMap(navController: NavController) {
    val cameraPositionState = rememberCameraPositionState {}
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val risk by homeViewModel.riskLocations.collectAsState()

    Box(Modifier.fillMaxSize()) {
        MapsTheme {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(mapType = MapType.TERRAIN),
                uiSettings = MapUiSettings(compassEnabled = false),
                onMapLongClick = { latLng ->
                    // Check if user is trying to click to an existing marker
                    val nearLocation = homeViewModel.getNearestLocation(latLng)
                    userMarkerState.clickedMarker.value =
                        if (nearLocation != null) {
                            nearLocation
                        } else {
                            homeViewModel.addRiskByLocation(latLng)
                            latLng
                        }
                    cameraPositionState.move(CameraUpdateFactory.newLatLng(latLng))
                    userMarkerState.changeTitle("Choose a type")
                },
            ) {
                risk.forEach { (position, datatype) ->
                    val pointEntity = PointEntity(position)
                    when (datatype.riskState) {
                        RiskChoices.Soil -> {
                            val soil: Soil = datatype.soil
                            if (soil.sqkm != null && soil.domsoi != null) {
                                PolygonDrawing(pointEntity, soil.sqkm, pointEntity.getColorToSoilType(soil.domsoi))
                            }
                        }
                        RiskChoices.Earthquake -> {
                            val earthquake = datatype.earthquake
                            if (earthquake.dn != null) PolygonDrawing(pointEntity, earthquake.dn * 10.0)
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
    val bottomSheetState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        topBar = {
            TopAppBar(
                colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                modifier = Modifier.height(0.dp),
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(11.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(userMarkerState.topBarTitleText.value, color = uiColorStyle)

                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = uiColorStyle),
                            onClick = {
                                homeViewModel.logUserViewNavigation("Profile")
                                navController.navigate("profile")
                            },
                        ) {
                            Icon(Icons.Filled.AccountCircle, contentDescription = null)
                        }
                    }
                },
            )
        },
        sheetContent = { BottomBar(homeViewModel = homeViewModel, bottomSheetState = bottomSheetState) },
    ) {
        LoadingIndicator(
            stateFlow = homeViewModel.stateFlow.collectAsState().value,
            navController = null,
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(homeViewModel: HomeViewModel, bottomSheetState: BottomSheetScaffoldState) {
    if (userMarkerState.hasUserClickedMarker()) {
        BottomBarContent(homeViewModel)
        Spacer(modifier = Modifier.height(0.dp))
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        if (userMarkerState.hasUserClickedMarker()) {
            LaunchedEffect(key1 = Unit) { bottomSheetState.bottomSheetState.expand() }
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
                    selected = userMarkerState.userChoiceForDataType.value == RiskChoices.Soil,
                    onClick = {
                        userMarkerState.userChoiceForDataType.value =
                            if (userMarkerState.userChoiceForDataType.value == RiskChoices.Soil) RiskChoices.None
                            else RiskChoices.Soil
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
                    selected = userMarkerState.userChoiceForDataType.value == RiskChoices.Earthquake,
                    onClick = {
                        userMarkerState.userChoiceForDataType.value =
                            if (userMarkerState.userChoiceForDataType.value == RiskChoices.Earthquake)
                                RiskChoices.None
                            else RiskChoices.Earthquake
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
                        homeViewModel.removeRiskByLocation(userMarkerState.clickedMarker.value)
                        userMarkerState.resetMarkerState()
                    },
                )
                if (homeViewModel.riskLocations.value.size > 1) {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = null,
                                modifier = Modifier.padding(8.dp).size(35.dp),
                            )
                        },
                        label = { Text("Clear all") },
                        selected = false,
                        onClick = {
                            homeViewModel.purgeAllRisks()
                            userMarkerState.resetMarkerState()
                        },
                    )
                }
            }
        } else {
            Text(text = "Nothing to see here...")
            userMarkerState.changeTitle("Geo")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BottomBarContent(homeViewModel: HomeViewModel) {
    val riskLocations: RiskHierarchy?
    var soil: Soil? = null
    var earthquake: Earthquake? = null

    when (userMarkerState.userChoiceForDataType.value) {
        // If the user hasn't chosen a data type yet
        RiskChoices.None -> {
            Box(modifier = Modifier.fillMaxWidth()) { userMarkerState.changeTitle("Choose a type") }
        }
        // If user has chosen a data type
        RiskChoices.Soil -> {
            riskLocations = homeViewModel.getRiskByLocation(userMarkerState.clickedMarker.value)

            // First check if the user clicked on a marker with already fetched risk, in order to avoid
            // making a redundant api call
            if (
                riskLocations != null &&
                riskLocations.riskState != RiskChoices.None &&
                riskLocations.soil != Soil()
            ) {
                homeViewModel.updateRiskStateByLocation(
                    userMarkerState.clickedMarker.value,
                    RiskChoices.Soil,
                )
                soil = riskLocations.soil
            } else
            // If no existing risk data is found, make an api call
            {
                LaunchedEffect(key1 = Unit) {
                    homeViewModel.retrieveSoil(
                        RiskDTO(
                            userMarkerState.clickedMarker.value.latitude,
                            userMarkerState.clickedMarker.value.longitude,
                        )
                    )
                }
            }
            // In here put all of the available data types to visualize the respective table
            soil?.let {
                SoilTableContent(soil = it, soilTypes = SoilTypesDTO())
                userMarkerState.changeTitle("Soil")
            }
        }
        RiskChoices.Earthquake -> {
            riskLocations = homeViewModel.getRiskByLocation(userMarkerState.clickedMarker.value)
            // First check if the user clicked on a marker with already fetched risk, in order to avoid
            // making a redundant api call
            if (
                riskLocations != null &&
                riskLocations.riskState != RiskChoices.None &&
                riskLocations.earthquake != Earthquake()
            ) {
                homeViewModel.updateRiskStateByLocation(
                    userMarkerState.clickedMarker.value,
                    RiskChoices.Earthquake,
                )
                earthquake = riskLocations.earthquake
            } else
            // If no existing risk data is found, make an api call
            {
                LaunchedEffect(key1 = Unit) {
                    homeViewModel.retrieveEarthquake(
                        RiskDTO(
                            userMarkerState.clickedMarker.value.latitude,
                            userMarkerState.clickedMarker.value.longitude,
                        )
                    )
                }
            }
            // In here put all of the available data types to visualize the respective table
            earthquake?.let {
                EarthquakeTableContent(earthquake = it)
                userMarkerState.changeTitle("Earthquake")
            }
        }
    }
}

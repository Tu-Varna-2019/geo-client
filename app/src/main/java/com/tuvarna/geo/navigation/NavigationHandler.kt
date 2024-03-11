package com.tuvarna.geo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavigationViewFunction(
    startDestination: String, composableStartDest: String, firstView: @Composable (NavController) -> Unit,
    composableEndDest: String, secondView: @Composable () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(composableStartDest) {
            firstView(navController)
        }
        composable(composableEndDest) {
            secondView()
        }
    }
}

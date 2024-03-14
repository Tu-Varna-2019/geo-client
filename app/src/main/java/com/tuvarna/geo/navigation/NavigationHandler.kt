package com.tuvarna.geo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavigationViewFunction(
    composableStartDest: String, startComposable : @Composable (NavController) -> Unit,
    composableEndDest: String, endComposable: @Composable () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = composableStartDest) {
        composable(composableStartDest) {
            startComposable(navController)
        }
        composable(composableEndDest) {
            endComposable()
        }
    }
}

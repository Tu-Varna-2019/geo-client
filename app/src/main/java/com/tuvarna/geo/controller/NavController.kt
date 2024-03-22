package com.tuvarna.geo.controller

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tuvarna.geo.view.public.HomeView
import com.tuvarna.geo.view.public.LoginView
import com.tuvarna.geo.view.public.SignUpView

sealed class Screen(val route: String) {
  object Login : Screen("login")

  object SignUp : Screen("signup")

  object Home : Screen("home")
}

@Composable
fun NavController() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = Screen.Login.route) {
    composable(Screen.Login.route) { LoginView(navController) }
    composable(Screen.SignUp.route) { SignUpView(navController) }
    composable(Screen.Home.route) { HomeView(navController) }
  }
}

package com.tuvarna.geo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tuvarna.geo.storage.UserSessionStorage
import com.tuvarna.geo.view.private.AdminView
import com.tuvarna.geo.view.private.HomeView
import com.tuvarna.geo.view.private.ProfileView
import com.tuvarna.geo.view.public.LoginView
import com.tuvarna.geo.view.public.RegisterView

sealed class Screen(val route: String) {
  object Login : Screen("login")

  object SignUp : Screen("signup")

  object Home : Screen("home")

  object Profile : Screen("profile")

  object Admin : Screen("admin")
}

@Composable
fun NavController(userSessionStorage: UserSessionStorage) {
  val navController = rememberNavController()
  // TODO: Change startDest to Login
  NavHost(navController = navController, startDestination = Screen.Home.route) {
    composable(Screen.Login.route) { LoginView(navController) }
    composable(Screen.SignUp.route) { RegisterView(navController) }
    composable(Screen.Home.route) { HomeView(navController, userSessionStorage) }
    composable(Screen.Profile.route) { ProfileView(navController, userSessionStorage) }
    composable(Screen.Admin.route) { AdminView(navController, userSessionStorage) }
  }
}

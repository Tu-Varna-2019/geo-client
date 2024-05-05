package com.tuvarna.geo.view.private

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tuvarna.geo.R
import com.tuvarna.geo.entity.UserEntity
import com.tuvarna.geo.storage.UserSessionStorage
import com.tuvarna.geo.storage.UserStorage
import com.tuvarna.geo.view.component.dialog_box.AlertDialogManager
import com.tuvarna.geo.view.component.dialog_box.DialogChangePassword
import com.tuvarna.geo.view.component.dialog_box.DialogConfirmDeleteAccount
import com.tuvarna.geo.view.component.dialog_box.DialogRenameEmail
import com.tuvarna.geo.view.component.dialog_box.DialogRenameUsername
import com.tuvarna.geo.viewmodel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val FORBIDDEN_USER_TYPE_ADMIN_PANEL: String = "customer"

@Composable
fun ProfileView(navController: NavController, userSessionStorage: UserSessionStorage) {

  val profileViewModel = hiltViewModel<ProfileViewModel>()
  val userProps: UserStorage =
    userSessionStorage.readUserProps().collectAsState(initial = UserStorage(0, "", "", "")).value
  val user by remember { mutableStateOf(UserEntity(0, "", "", "", false)) }

  user.id = userProps.id
  user.username = userProps.username
  user.email = userProps.email
  user.usertype.type = userProps.userType

  val showAlertDialog = remember { mutableStateOf(false) }
  val showDeleteAccountDialog = remember { mutableStateOf(false) }
  val showRenameUsernameDialog = remember { mutableStateOf(false) }
  val showRenameEmailDialog = remember { mutableStateOf(false) }
  val showChangePasswordDialog = remember { mutableStateOf(false) }

  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    Card(
      modifier =
        Modifier.padding(0.dp)
          .fillMaxSize()
          .padding(8.dp)
          .wrapContentHeight()
          .align(Alignment.TopCenter),
      elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
      colors =
        CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.LightGray),
      shape = RoundedCornerShape(8.dp),
    ) {
      Button(
        onClick = { navController.popBackStack() },
        colors =
          ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            containerColor = Color.Transparent,
          ),
      ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
      }

      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Spacer(modifier = Modifier.height(100.dp))

        Image(
          painter = painterResource(id = R.drawable.profile),
          contentDescription = user.username,
          modifier = Modifier.size(100.dp, 100.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "Username: " + user.username,
          style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black),
          modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Text(
          text = "Edit",
          style = TextStyle(fontSize = 18.sp, color = Color.Blue),
          modifier =
            Modifier.align(Alignment.CenterHorizontally).clickable {
              showRenameUsernameDialog.value = true
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Email: " + user.email,
          style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black),
          modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Text(
          text = "Edit",
          style = TextStyle(fontSize = 18.sp, color = Color.Blue),
          modifier =
            Modifier.align(Alignment.CenterHorizontally).clickable {
              showRenameEmailDialog.value = true
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Edit password",
          style = TextStyle(fontSize = 18.sp, color = Color.Blue),
          modifier =
            Modifier.align(Alignment.CenterHorizontally).clickable {
              showChangePasswordDialog.value = true
            },
        )

        Spacer(modifier = Modifier.height(80.dp))
        if (userProps.userType != FORBIDDEN_USER_TYPE_ADMIN_PANEL)
          Button(
            onClick = { navController.navigate("admin") },
            modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color(204, 136, 0)),
          ) {
            Text(text = "Admin panel")
          }

        Button(
          onClick = { profileViewModel.logout() },
          modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.CenterHorizontally),
        ) {
          Text("Log out")
        }

        Button(
          onClick = { showAlertDialog.value = true },
          modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.CenterHorizontally),
          colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        ) {
          Text("Delete account")
        }
        if (showAlertDialog.value) {
          AlertDialogManager(
            title = "Delete Account",
            message = "Are you sure you want to delete your account?",
            onConfirm = {
              CoroutineScope(Dispatchers.Main).launch {
                showAlertDialog.value = false
                showDeleteAccountDialog.value = true
              }
            },
            onDismiss = { showAlertDialog.value = false },
          )
        }

        // Delete account dialog
        if (showDeleteAccountDialog.value) {
          DialogConfirmDeleteAccount(
            title = "Delete Account",
            message = "Please enter your password to confirm the deletion of your account",
            onConfirm = { password -> profileViewModel.deleteAccount() },
            onDismiss = { showDeleteAccountDialog.value = false },
          )
        }

        // Rename username dialog
        if (showRenameUsernameDialog.value) {
          DialogRenameUsername(
            title = "Rename username",
            message = "Please enter your username",
            userUsername = user.username,
            onConfirm = { username ->
              CoroutineScope(Dispatchers.Main).launch {
                profileViewModel.renameUsername()
                showRenameUsernameDialog.value = false
              }
            },
            onDismiss = { showRenameUsernameDialog.value = false },
          )
        }

        // Rename email dialog
        if (showRenameEmailDialog.value) {
          DialogRenameEmail(
            title = "Rename email",
            message = "Please enter your email",
            userEmail = user.email,
            onConfirm = { email ->
              CoroutineScope(Dispatchers.Main).launch {
                profileViewModel.renameEmail(email)
                showRenameEmailDialog.value = false
              }
            },
            onDismiss = { showRenameEmailDialog.value = false },
          )
        }

        // Change password dialog
        if (showChangePasswordDialog.value) {
          DialogChangePassword(
            title = "Change password",
            onConfirm = { oldPassword, newPassword ->
              CoroutineScope(Dispatchers.Main).launch {
                profileViewModel.changePassword(oldPassword, newPassword)
                showChangePasswordDialog.value = false
              }
            },
            onDismiss = { showChangePasswordDialog.value = false },
          )
        }
      }
    }
  }
}

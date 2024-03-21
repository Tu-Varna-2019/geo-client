package com.tuvarna.geo.view.public

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tuvarna.geo.entity.EntityUser

@Composable
fun HomeView(navController: NavController, user: EntityUser) {

  Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
    Column(
      modifier = Modifier.padding(5.dp).fillMaxWidth(0.8f),
      verticalArrangement = Arrangement.Center,
    ) {
      Text(
        text = "Home for user ${user.email}",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
      )

      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

package com.example.dtapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.example.dtapp.R
import com.example.dtapp.navigation.Screen

@Composable
fun NavigationDrawer(onDestinationClicked: (route: String) -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Text(
            text = getString(context, R.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 24.dp, top = 48.dp)
        )
        Spacer(Modifier.height(48.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { onDestinationClicked(Screen.About.route) }
            .padding(start = 24.dp)) {
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Info, contentDescription = "")
                Spacer(Modifier.width(8.dp))
                Text(text = Screen.About.route,)
            }
            Spacer(Modifier.height(12.dp))
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { onDestinationClicked(Screen.Home.route) }
            .padding(start = 24.dp)) {
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Home, contentDescription = "")
                Spacer(Modifier.width(8.dp))
                Text(text = Screen.Home.route,)
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}
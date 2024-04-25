package com.example.dtapp.view

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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dtapp.R
import com.example.dtapp.navigation.Screen

@Composable
fun NavigationDrawer(onClick: (route: String) -> Unit) {
    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .fillMaxHeight()
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 24.dp, top = 48.dp)
        )
        Spacer(Modifier.height(48.dp))

        DrawerElement(
            routeName = Screen.Home.route,
            icon = Icons.Filled.Home,
            onClick = onClick
        )

        DrawerElement(
            routeName = Screen.Net.route,
            icon = Icons.Filled.Refresh,
            onClick = onClick
        )

        DrawerElement(
            routeName = Screen.About.route,
            icon = Icons.Filled.Info,
            onClick = onClick
        )
    }
}

@Composable
fun DrawerElement(routeName: String, icon: ImageVector, onClick: (route: String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick(routeName) }
        .padding(start = 24.dp)) {
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = "")
            Spacer(Modifier.width(8.dp))
            Text(text = routeName)
        }
        Spacer(Modifier.height(12.dp))
    }
}
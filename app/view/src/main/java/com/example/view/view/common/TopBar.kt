package com.example.view.view.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, buttonIcon: ImageVector, onClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.testTag("topBar"),
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(
                modifier = Modifier.testTag("backButton"),
                onClick = { onClick() }
            ) {
                Icon(
                    imageVector = buttonIcon,
                    contentDescription = ""
                )
            }
        }
    )
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
}
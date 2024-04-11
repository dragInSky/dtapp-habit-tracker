package com.example.dtapp.view.aboutscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dtapp.R
import com.example.dtapp.view.common.TopBar

@Composable
fun AboutScreen(onClick: () -> Unit) {
    var isNavigationPerformed by remember { mutableStateOf(false) }

    Column {
        TopBar(
            title = R.string.about_screen_name.toString(),
            buttonIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = {
                if (!isNavigationPerformed) {
                    onClick()
                    isNavigationPerformed = true
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = R.string.app_name.toString(),
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = R.string.about_author_name.toString(),
                fontWeight = FontWeight.Light
            )
            Text(
                text = R.string.about_author_mail.toString(),
                fontWeight = FontWeight.Light
            )
            Text(
                text = R.string.about_author_github.toString(),
                fontWeight = FontWeight.Light
            )
            Text(
                text = R.string.about_version.toString(),
                fontWeight = FontWeight.Thin
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            isNavigationPerformed = false
        }
    }
}
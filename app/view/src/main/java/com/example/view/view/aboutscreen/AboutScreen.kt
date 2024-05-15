package com.example.view.view.aboutscreen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.view.R
import com.example.view.view.common.TopBar

@Composable
fun AboutScreen(onClick: () -> Unit) {
    var isNavigationPerformed by remember { mutableStateOf(false) }

    Column {
        TopBar(
            title = stringResource(R.string.about_screen_name),
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
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.about_author_name),
                fontWeight = FontWeight.Light
            )
            Text(
                text = stringResource(R.string.about_author_mail),
                fontWeight = FontWeight.Light
            )
            Text(
                text = stringResource(R.string.about_author_github),
                fontWeight = FontWeight.Light
            )
            Text(
                text = stringResource(R.string.about_version),
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
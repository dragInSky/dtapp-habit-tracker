package com.example.dtapp.view.netscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dtapp.R
import com.example.dtapp.view.common.TopBar
import com.example.dtapp.viewmodels.NetViewModel

@Composable
fun NetScreen(
    onClick: () -> Unit,
    netViewModel: NetViewModel = viewModel()
) {
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
            Button(
                onClick = { netViewModel.onUploadClick() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.netUpload),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { netViewModel.onLoadClick() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.netLoad),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            isNavigationPerformed = false
        }
    }
}
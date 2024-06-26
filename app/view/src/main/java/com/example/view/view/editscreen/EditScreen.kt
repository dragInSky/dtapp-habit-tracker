package com.example.view.view.editscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.entities.HabitInfo
import com.example.view.R
import com.example.view.view.common.TopBar
import com.example.view.viewmodels.EditViewModel
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditScreen(
    onClick: () -> Unit,
    editViewModel: EditViewModel = viewModel(),
    id: Int = HabitInfo.DEFAULT_ID
) {
    var isNavigationPerformed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = stringResource(R.string.edit_screen_name),
            buttonIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = {
                if (!isNavigationPerformed) {
                    onClick()
                    isNavigationPerformed = true
                }
            }
        )

        Column(modifier = Modifier.padding(8.dp)) {
            HidingTextField(
                text = editViewModel.name,
                placeHolder = stringResource(R.string.habit_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("nameField"),
                onTextChanged = { editViewModel.changeName(it) }
            )

            HidingTextField(
                text = editViewModel.description,
                placeHolder = stringResource(R.string.habit_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("descriptionField"),
                onTextChanged = { editViewModel.changeDescription(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                Spinner(
                    text = stringResource(R.string.habit_priority, ""),
                    items = com.example.domain.entities.Priority.values().map { it.getName() },
                    selectedItem = editViewModel.selectedPriority,
                    onItemSelected = { editViewModel.changePriority(it) }
                )
            }
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                RadioButtons(
                    items = com.example.domain.entities.Type.values().map { it.name.lowercase(Locale.ROOT) },
                    selectedItem = editViewModel.selectedType,
                    onItemSelected = { editViewModel.changeType(it) }
                )
            }
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(12.dp))

            Row {
                HidingTextField(
                    text = editViewModel.count,
                    placeHolder = stringResource(R.string.habit_count),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("countField"),
                    onTextChanged = { editViewModel.changeCount(it) }
                )

                HidingTextField(
                    text = editViewModel.frequency,
                    placeHolder = stringResource(R.string.habit_frequency),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("countField"),
                    onTextChanged = { editViewModel.changeFrequency(it) }
                )
            }
        }
    }

    SaveButton {
        if (!isNavigationPerformed) {
            editViewModel.onSaveClicked(
                onClick = onClick,
                id = id
            )
            isNavigationPerformed = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            isNavigationPerformed = false
        }
    }
}
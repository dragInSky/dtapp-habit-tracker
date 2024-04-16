package com.example.dtapp.view.editscreen

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.example.dtapp.R
import com.example.dtapp.models.Priority
import com.example.dtapp.models.Type
import com.example.dtapp.view.common.TopBar
import com.example.dtapp.viewmodels.EditViewModel
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditScreen(
    onClick: () -> Unit,
    editViewModel: EditViewModel,
    id: Int = -1
) {
    val context = LocalContext.current

    var isNavigationPerformed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = getString(context, R.string.edit_screen_name),
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
                text = editViewModel.name.value,
                placeHolder = getString(context, R.string.habit_name),
                modifier = Modifier.fillMaxWidth(),
                onTextChanged = { editViewModel.name.value = it }
            )

            HidingTextField(
                text = editViewModel.description.value,
                placeHolder = getString(context, R.string.habit_description),
                modifier = Modifier.fillMaxWidth(),
                onTextChanged = { editViewModel.description.value = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                Spinner(
                    text = getString(context, R.string.habit_priority) + " ",
                    items = Priority.values().map { it.getName() },
                    selectedItem = editViewModel.selectedPriority.value,
                    onItemSelected = { editViewModel.selectedPriority.value = it }
                )
            }
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                RadioButtons(
                    items = Type.values().map { it.name.lowercase(Locale.ROOT) },
                    selectedItem = editViewModel.selectedType.value,
                    onItemSelected = { editViewModel.selectedType.value = it }
                )
            }
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(12.dp))

            Row {
                HidingTextField(
                    text = editViewModel.times.value,
                    placeHolder = getString(context, R.string.habit_times),
                    modifier = Modifier.weight(1f),
                    onTextChanged = { editViewModel.times.value = it }
                )

                HidingTextField(
                    text = editViewModel.period.value,
                    placeHolder = getString(context, R.string.habit_period),
                    modifier = Modifier.weight(1f),
                    onTextChanged = { editViewModel.period.value = it }
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
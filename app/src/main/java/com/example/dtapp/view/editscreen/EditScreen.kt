package com.example.dtapp.view.editscreen

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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dtapp.R
import com.example.dtapp.models.Priority
import com.example.dtapp.models.Type
import com.example.dtapp.view.common.TopBar
import com.example.dtapp.viewmodels.HabitsViewModel

@Composable
fun EditScreen(
    navController: NavController,
    id: Int = -1,
    habitsViewModel: HabitsViewModel = viewModel()
) {
    val context = LocalContext.current

    val habit = habitsViewModel.getHabitById(id)

    var selectedPriorityItem by remember {
        mutableStateOf(habit?.priority?.text ?: Priority.MEDIUM.text)
    }
    var selectedTypeItem by remember { mutableStateOf(habit?.type?.text ?: Type.GOOD.text) }
    var nameText by remember { mutableStateOf(habit?.nameText ?: "") }
    var descriptionText by remember { mutableStateOf(habit?.descriptionText ?: "") }
    var timesText by remember { mutableStateOf(habit?.timesText ?: "") }
    var periodText by remember { mutableStateOf(habit?.periodText ?: "") }

    var isNavigationPerformed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = getString(context, R.string.edit_screen_name),
            buttonIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onButtonClicked = {
                if (!isNavigationPerformed) {
                    navController.popBackStack()
                    isNavigationPerformed = true
                }
            }
        )

        Column(modifier = Modifier.padding(8.dp)) {
            HidingTextField(
                text = nameText,
                placeHolder = getString(context, R.string.habit_name),
                modifier = Modifier.fillMaxWidth(),
                onTextChanged = { nameText = it }
            )

            HidingTextField(
                text = descriptionText,
                placeHolder = getString(context, R.string.habit_description),
                modifier = Modifier.fillMaxWidth(),
                onTextChanged = { descriptionText = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier.background(Color.White)
            ) {
                Spinner(
                    text = getString(context, R.string.habit_priority),
                    items = Priority.values().map { it.text },
                    selectedItem = selectedPriorityItem,
                    onItemSelected = { selectedPriorityItem = it }
                )
            }
            HorizontalDivider(thickness = 1.dp, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier.background(Color.White)
            ) {
                RadioButtons(
                    items = Type.values().map { it.text },
                    selectedItem = selectedTypeItem,
                    onItemSelected = { selectedTypeItem = it }
                )
            }
            HorizontalDivider(thickness = 1.dp, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))

            Row {
                HidingTextField(text = timesText,
                    placeHolder = getString(context, R.string.habit_times),
                    modifier = Modifier.weight(1f),
                    onTextChanged = { timesText = it })

                HidingTextField(text = periodText,
                    placeHolder = getString(context, R.string.habit_period),
                    modifier = Modifier.weight(1f),
                    onTextChanged = { periodText = it })
            }
        }
    }

    SaveButton(
        navController = navController,
        id = habit?.id ?: -1,
        selectedPriority = selectedPriorityItem,
        selectedType = selectedTypeItem,
        nameText = nameText,
        descriptionText = descriptionText,
        timesText = timesText,
        periodText = periodText
    )

    DisposableEffect(Unit) {
        onDispose {
            isNavigationPerformed = false
        }
    }
}
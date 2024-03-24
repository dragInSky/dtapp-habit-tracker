package com.example.dtapp.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavController
import com.example.dtapp.Ambient
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.ui.HidingTextField
import com.example.dtapp.R
import com.example.dtapp.models.Priority
import com.example.dtapp.models.Type
import com.example.dtapp.ui.RadioButtons
import com.example.dtapp.navigation.Screen
import com.example.dtapp.ui.Spinner
import com.example.dtapp.ui.CenterTextButton

@Composable
fun EditScreen(context: Context, navController: NavController, id: Int = -1) {
    val habit = Ambient.habitList.find { it.id == id }

    var selectedPriorityItem by remember {
        mutableStateOf(habit?.priority?.text ?: Priority.MEDIUM.text)
    }
    var selectedTypeItem by remember { mutableStateOf(habit?.type?.text ?: Type.GOOD.text) }
    var nameText by remember { mutableStateOf(habit?.nameText ?: "") }
    var descriptionText by remember { mutableStateOf(habit?.descriptionText ?: "") }
    var timesText by remember { mutableStateOf(habit?.timesText ?: "") }
    var periodText by remember { mutableStateOf(habit?.periodText ?: "") }

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        HidingTextField(text = nameText,
            placeHolder = getString(context, R.string.habit_name),
            modifier = Modifier.fillMaxWidth(),
            onTextChanged = { nameText = it })

        HidingTextField(text = descriptionText,
            placeHolder = getString(context, R.string.habit_description),
            modifier = Modifier.fillMaxWidth(),
            onTextChanged = { descriptionText = it })

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.background(Color.White)
        ) {
            Spinner(text = getString(context, R.string.habit_priority),
                items = Priority.values().map { it.text },
                selectedItem = selectedPriorityItem,
                onItemSelected = { selectedPriorityItem = it })
        }
        Divider(color = Color.Black, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.background(Color.White)
        ) {
            RadioButtons(items = Type.values().map { it.text },
                selectedItem = selectedTypeItem,
                onItemSelected = { selectedTypeItem = it })
        }
        Divider(color = Color.Black, thickness = 1.dp)
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

    CenterTextButton(getString(context, R.string.habit_save_button)) {
        HabitInfo.habitListAction(
            context, habit?.id ?: -1,
            selectedPriorityItem,
            selectedTypeItem,
            nameText,
            descriptionText,
            timesText,
            periodText
        )

        navController.navigate(Screen.Home.route)
    }
}
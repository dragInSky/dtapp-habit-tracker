package com.example.dtapp

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavController
import com.example.dtapp.ui.theme.AppColors
import java.util.UUID
import kotlin.math.absoluteValue

@Composable
fun EditHabitScreen(context: Context, navController: NavController, id: Int = -1) {
    val habitToEdit = Ambient.habitList.find { it.id == id }
    val habit = if (id != -1) Ambient.habitList[Ambient.habitList.indexOf(habitToEdit)] else null

    var selectedSpinnerItem by remember {
        mutableStateOf(habit?.priorityText ?: Ambient.priorities[2])
    }
    var selectedTypeItem by remember { mutableStateOf(habit?.typeText ?: Ambient.types[1]) }
    var nameText by remember { mutableStateOf(habit?.nameText ?: "") }
    var descriptionText by remember { mutableStateOf(habit?.descriptionText ?: "") }
    var timesText by remember { mutableStateOf(habit?.timesText ?: "") }
    var periodicityText by remember { mutableStateOf(habit?.periodText ?: "") }

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        HidingTextField(text = nameText,
            placeHolder = getString(context, R.string.habit_name),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            onTextChanged = { nameText = it })

        HidingTextField(text = descriptionText,
            placeHolder = getString(context, R.string.habit_description),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            onTextChanged = { descriptionText = it })

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.background(
                Color.White, shape = RoundedCornerShape(
                    topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp
                )
            )
        ) {
            Spinner(text = getString(context, R.string.habit_priority),
                items = Ambient.priorities,
                selectedItem = selectedSpinnerItem,
                onItemSelected = { selectedSpinnerItem = it })
        }
        Divider(color = Color.Black, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.background(
                Color.White, shape = RoundedCornerShape(
                    topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp
                )
            )
        ) {
            RadioButtons(items = Ambient.types,
                selectedItem = selectedTypeItem,
                onItemSelected = { selectedTypeItem = it })
        }
        Divider(color = Color.Black, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))

        Row {
            HidingTextField(text = timesText,
                placeHolder = getString(context, R.string.habit_times),
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White),
                onTextChanged = { timesText = it })

            HidingTextField(text = periodicityText,
                placeHolder = getString(context, R.string.habit_period),
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White),
                onTextChanged = { periodicityText = it })
        }
    }

    SaveButton(
        navController,
        context,
        habit?.id ?: -1,
        selectedSpinnerItem,
        selectedTypeItem,
        nameText,
        descriptionText,
        timesText,
        periodicityText
    )
}

@Composable
fun SaveButton(
    navController: NavController,
    context: Context,
    id: Int,
    selectedSpinner: String,
    selectedType: String,
    nameText: String,
    descriptionText: String,
    timesText: String,
    periodicityText: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            onClick = { //вынести во ViewModel
                val habit =
                    HabitInfo(id = if (id == -1) UUID.randomUUID().hashCode().absoluteValue else id,
                        selectedSpinner,
                        selectedType,
                        nameText.ifEmpty { getString(context, R.string.habit_name) },
                        descriptionText.ifEmpty { getString(context, R.string.habit_description) },
                        timesText.ifEmpty { getString(context, R.string.habit_times) },
                        periodicityText.ifEmpty { getString(context, R.string.habit_period) })

                if (habit.id != id) {
                    Ambient.habitList.add(habit)
                } else {
                    val habitToEdit = Ambient.habitList.find { it.id == id }
                    Ambient.habitList[Ambient.habitList.indexOf(habitToEdit)] = habit
                }

                navController.navigate(Screen.Home.route)
            }, colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.Darkest, contentColor = Color.White
            )
        ) {
            Text(
                text = getString(context, R.string.habit_save_button),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
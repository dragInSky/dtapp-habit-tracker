package com.example.dtapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dtapp.ui.theme.AppColors
import com.example.dtapp.ui.theme.DtappTheme
import java.util.UUID
import kotlin.math.absoluteValue

class HabitCreatorActivity : ComponentActivity() {
    private val priorities = listOf("highest", "high", "medium", "low")
    private val types = listOf("good", "bad")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DtappTheme {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    AppColors.gradientBottom,
                                    AppColors.gradientMid,
                                    AppColors.gradientTop
                                )
                            )
                        )
                ) {
                    CreateHabit()
                }
            }
        }
    }

    @Composable
    fun CreateHabit() {
        val habit = intent.getSerializableExtra(getString(R.string.habit_item)) as HabitInfo?

        var selectedSpinnerItem by remember {
            mutableStateOf(habit?.priorityText ?: priorities[2])
        }
        var selectedTypeItem by remember { mutableStateOf(habit?.typeText ?: types[1]) }
        var nameText by remember { mutableStateOf(habit?.nameText ?: "") }
        var descriptionText by remember { mutableStateOf(habit?.descriptionText ?: "") }
        var timesText by remember { mutableStateOf(habit?.timesText ?: "") }
        var periodicityText by remember { mutableStateOf(habit?.periodText ?: "") }

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            HidingTextField(
                text = nameText,
                placeHolder = "name of the habit",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                onTextChanged = { nameText = it }
            )

            HidingTextField(
                text = descriptionText,
                placeHolder = "description",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                onTextChanged = { descriptionText = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
            ) {
                Spinner(
                    text = "priority: ",
                    items = priorities,
                    selectedItem = selectedSpinnerItem,
                    onItemSelected = { selectedSpinnerItem = it }
                )
            }
            Divider(color = Color.Black, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
            ) {
                RadioButtons(
                    items = types,
                    selectedItem = selectedTypeItem,
                    onItemSelected = { selectedTypeItem = it }
                )
            }
            Divider(color = Color.Black, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Row {
                HidingTextField(
                    text = timesText,
                    placeHolder = "times",
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White),
                    onTextChanged = { timesText = it }
                )

                HidingTextField(
                    text = periodicityText,
                    placeHolder = "period",
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White),
                    onTextChanged = { periodicityText = it }
                )
            }
        }

        SaveButton(
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
                    val habit = HabitInfo(
                        id = if (id == -1) UUID.randomUUID().hashCode().absoluteValue else id,
                        selectedSpinner,
                        selectedType,
                        nameText.ifEmpty { "name" },
                        descriptionText.ifEmpty { "description" },
                        timesText.ifEmpty { "times" },
                        periodicityText.ifEmpty { "period" }
                    )

                    if (habit.id != id) {
                        Habits.habitList.add(habit)
                    } else {
                        val habitToEdit = Habits.habitList.find { it.id == id }
                        Habits.habitList[Habits.habitList.indexOf(habitToEdit)] = habit
                    }

                    finish()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Darkest,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "save habit",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
package com.example.dtapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dtapp.ui.theme.AppColors
import com.example.dtapp.ui.theme.DtappTheme

class MainActivity : ComponentActivity() {
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
                    DisplayHabits()
                }
            }
        }
    }

    private fun habitsPreprocess() {
        val id = intent.getIntExtra("id", -1)
        if (id != -1) {
            val habit = HabitInfo(
                id,
                intent.getStringExtra("priority") ?: "priority",
                intent.getStringExtra("type") ?: "type",
                intent.getStringExtra("name") ?: "name",
                intent.getStringExtra("description") ?: "description",
                intent.getStringExtra("times") ?: "X",
                intent.getStringExtra("period") ?: "period"
            )

            if (intent.getBooleanExtra("isEdit", false)) {
                val habitToEdit = Habits.habitList.find { it.id == id }
                Habits.habitList[Habits.habitList.indexOf(habitToEdit)] = habit
            } else {
                Habits.habitList.add(habit)
            }
        }
    }

    @Composable
    fun DisplayHabits() {
        habitsPreprocess()

        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            items(Habits.habitList) { habitInfo ->
                Show(habitInfo)
            }
        }

        AddButton()
    }

    @Composable
    fun Show(habit: HabitInfo) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clickable {
                    val intent =
                        Intent(
                            this@MainActivity,
                            HabitCreatorActivity::class.java
                        ).apply {
                            putExtra("isEdit", true)
                            putExtra("id", habit.id)
                            putExtra("priority", habit.priorityText)
                            putExtra("type", habit.typeText)
                            putExtra("name", habit.nameText)
                            putExtra("description", habit.descriptionText)
                            putExtra("times", habit.timesText)
                            putExtra("period", habit.periodText)
                        }
                    startActivity(intent)
                }
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.weight(0.8f)) {
                Text(text = habit.nameText, fontWeight = FontWeight.Bold)
                Text(
                    text = habit.descriptionText,
                    fontSize = 12.sp
                )
                Row {
                    Text(
                        text = "${habit.typeText} — ${habit.priorityText}",
                        fontSize = 12.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                Text(
                    text = habit.timesText,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "times a",
                    fontSize = 12.sp
                )
                Text(
                    text = habit.periodText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Divider(color = Color.Black, thickness = 1.dp)
    }

    @Composable
    fun AddButton() {
        Box(
            modifier = Modifier.padding(24.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(this@MainActivity, HabitCreatorActivity::class.java)
                    startActivity(intent)
                },
                containerColor = AppColors.Darkest,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "FAB add action", tint = Color.White)
            }
        }
    }
}
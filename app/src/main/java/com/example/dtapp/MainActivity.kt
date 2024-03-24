package com.example.dtapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dtapp.ui.theme.AppColors
import com.example.dtapp.ui.theme.DtappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DtappTheme {
                Surface(
                    color = Color.Transparent, modifier = Modifier
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
                    HabitList()
                }
            }
        }
    }

    @Composable
    fun HabitList() {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            items(Habits.habitList) { habitInfo ->
                HabitItem(this@MainActivity, habitInfo)
            }
        }

        AddButton()
    }

    @Composable
    fun AddButton() {
        Box(
            modifier = Modifier.padding(24.dp), contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(this@MainActivity, HabitCreatorActivity::class.java)
                    startActivity(intent)
                }, containerColor = AppColors.Darkest, contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "FAB add action", tint = Color.White)
            }
        }
    }
}
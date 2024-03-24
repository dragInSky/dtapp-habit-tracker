package com.example.dtapp

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dtapp.ui.theme.AppColors

@Composable
fun HabitsScreen(context: Context, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        items(Ambient.habitList) { habitInfo ->
            HabitItem(context, navController, habitInfo)
        }
    }

    AddButton(navController)
}

@Composable
fun AddButton(navController: NavController) {
    Box(
        modifier = Modifier.padding(24.dp), contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { navController.navigate(Screen.EDIT.createRoute()) },
            containerColor = AppColors.Darkest, contentColor = Color.White
        ) {
            Icon(Icons.Filled.Add, "FAB add action", tint = Color.White)
        }
    }
}
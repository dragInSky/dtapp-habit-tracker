package com.example.dtapp.screens

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dtapp.Ambient
import com.example.dtapp.models.Type
import com.example.dtapp.ui.util.HabitItem
import com.example.dtapp.navigation.Screen
import com.example.dtapp.ui.theme.Purple40
import com.example.dtapp.ui.util.Tabs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitsScreen(context: Context, navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 2 })

    Column {
        Tabs(pagerState, Type.values().map { it.text })

        HorizontalPager(state = pagerState) { page ->
            val key = if (page == 0) Type.GOOD else Type.BAD
            val habitList = Ambient.habitList.filter { e -> e.type == key }

            LazyColumn(
                modifier = Modifier.padding(8.dp),
            ) {
                items(habitList) { habitInfo ->
                    HabitItem(context, navController, habitInfo)
                }
            }
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
            containerColor = Purple40,
            contentColor = Color.White
        ) {
            Icon(Icons.Filled.Add, "FAB add action", tint = Color.White)
        }
    }
}
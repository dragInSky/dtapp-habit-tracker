package com.example.dtapp.screens

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dtapp.Ambient
import com.example.dtapp.models.Type
import com.example.dtapp.ui.HabitItem
import com.example.dtapp.navigation.Screen
import com.example.dtapp.ui.AddButton
import com.example.dtapp.ui.Tabs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitsScreen(context: Context, navController: NavController) {
    val types = Type.values()
    val pagerState = rememberPagerState(pageCount = { types.size })

    Column {
        Tabs(pagerState, Type.values().map { it.text })

        HorizontalPager(state = pagerState) { page ->
            val habitList = Ambient.habitList.filter { e -> e.type == types[page] }

            LazyColumn(
                modifier = Modifier.padding(8.dp),
            ) {
                items(habitList) { habitInfo ->
                    HabitItem(context, navController, habitInfo)
                }
            }
        }
    }

    AddButton("habit add action") { navController.navigate(Screen.EDIT.createRoute()) }
}
package com.example.dtapp.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dtapp.Ambient
import com.example.dtapp.models.Type
import com.example.dtapp.navigation.Screen
import com.example.dtapp.ui.theme.Purple40

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitPager(
    types: Array<Type>,
    modifier: Modifier,
    context: Context,
    navController: NavController,
    pagerState: PagerState
) {
    var isNavigationPerformed by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
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

        FloatingActionButton(
            onClick = {
                if (!isNavigationPerformed) {
                    navController.navigate(Screen.Edit.createRoute())
                    isNavigationPerformed = true
                }
            },
            containerColor = Purple40,
            contentColor = Color.White,
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
        ) {
            Icon(Icons.Filled.Add, "habit add action", tint = Color.White)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            isNavigationPerformed = false
        }
    }
}
package com.example.dtapp.view.habitsscreen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dtapp.navigation.Screen
import com.example.dtapp.view.theme.Purple40
import com.example.dtapp.viewmodels.HabitsViewModel

@Composable
fun HabitPager(
    navController: NavController,
    modifier: Modifier,
    pagerState: PagerState,
    habitsViewModel: HabitsViewModel = viewModel()
) {
    Box(modifier = modifier) {
        HorizontalPager(state = pagerState) { page ->
            val habitList = habitsViewModel.getPagedHabits(page)

            LazyColumn(
                modifier = Modifier.padding(8.dp),
            ) {
                items(habitList) { habitInfo ->
                    HabitItem(
                        navController = navController,
                        habit = habitInfo
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.Edit.createRoute()) {
                    launchSingleTop = true
                }
            },
            containerColor = Purple40,
            contentColor = Color.White,
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "habit add action",
                tint = Color.White
            )
        }
    }
}
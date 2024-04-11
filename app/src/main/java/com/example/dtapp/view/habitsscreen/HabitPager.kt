package com.example.dtapp.view.habitsscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dtapp.navigation.Screen
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
            LazyColumn(
                modifier = Modifier.padding(8.dp),
            ) {
                items(habitsViewModel.pagedHabits(page)) { habitInfo ->
                    HabitItem(
                        onClick = {
                            navController.navigate(Screen.Edit.createRoute(habitInfo.id)) {
                                launchSingleTop = true
                            }
                        },
                        habit = habitInfo
                    )
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { habitsViewModel.clear() }
    }
}
package com.example.dtapp.view.habitsscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
            habitsViewModel.pagingHabits(page)
            habitsViewModel.changeHabits()

            LazyColumn(
                modifier = Modifier.padding(8.dp),
            ) {
                items(habitsViewModel.filteredHabits) { habitInfo ->
                    HabitItem(
                        navController = navController,
                        habit = habitInfo
                    )
                }
            }
        }
    }
}
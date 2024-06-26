package com.example.view.view.habitsscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.domain.entities.Type
import com.example.view.navigation.Screen
import com.example.view.viewmodels.HabitsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HabitPager(
    navController: NavController,
    modifier: Modifier,
    pagerState: PagerState,
    habitsViewModel: HabitsViewModel = viewModel()
) {
    val goodHabits by habitsViewModel.goodHabitFlow.collectAsStateWithLifecycle(emptyList())
    val badHabits by habitsViewModel.badHabitFlow.collectAsStateWithLifecycle(emptyList())

    Box(modifier = modifier) {
        HorizontalPager(state = pagerState) { page ->
            val habits =
                if (page == Type.GOOD.ordinal) goodHabits
                else badHabits

            LazyColumn(
                modifier = Modifier.padding(8.dp),
            ) {
                items(habits) { habitInfo ->
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
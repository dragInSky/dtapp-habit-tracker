package com.example.view.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.domain.entities.HabitInfo
import com.example.view.view.aboutscreen.AboutScreen
import com.example.view.view.editscreen.EditScreen
import com.example.view.view.habitsscreen.HabitsScreen
import com.example.view.viewmodels.EditViewModel
import kotlinx.coroutines.Job

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavHost(navController: NavHostController, openDrawer: () -> Job) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.About.route) {
            AboutScreen(onClick = { navController.popBackStack() })
        }

        composable(Screen.Home.route) {
            HabitsScreen(
                navController = navController,
                openDrawer = openDrawer
            )
        }

        composable(
            Screen.Edit.route, arguments = listOf(navArgument("habitId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("habitId") ?: HabitInfo.DEFAULT_ID
            val editViewModel: EditViewModel = viewModel()

            LaunchedEffect(id) {
                if (id != HabitInfo.DEFAULT_ID)
                    editViewModel.habitInit(id = id)
            }

            EditScreen(
                onClick = { navController.popBackStack() },
                editViewModel = editViewModel,
                id = id
            )
        }
    }
}
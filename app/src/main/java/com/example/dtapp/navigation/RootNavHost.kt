package com.example.dtapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dtapp.view.aboutscreen.AboutScreen
import com.example.dtapp.view.editscreen.EditScreen
import com.example.dtapp.view.habitsscreen.HabitsScreen
import com.example.dtapp.viewmodels.EditViewModel
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
            val id = backStackEntry.arguments?.getInt("habitId") ?: -1
            val editViewModel: EditViewModel = viewModel()
            editViewModel.habitInit(id = id)

            EditScreen(
                onClick = { navController.popBackStack() },
                editViewModel = editViewModel,
                id = id
            )
        }
    }
}
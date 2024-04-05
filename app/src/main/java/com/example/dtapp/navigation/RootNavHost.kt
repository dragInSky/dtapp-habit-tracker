package com.example.dtapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dtapp.ui.aboutscreen.AboutScreen
import com.example.dtapp.ui.editscreen.EditScreen
import com.example.dtapp.ui.habitsscreen.HabitsScreen
import kotlinx.coroutines.Job

@Composable
fun RootNavHost(openDrawer: () -> Job) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Screen.Home.route
    ) {
        composable(Screen.About.route) {
            AboutScreen()
        }
        composable(Screen.Home.route) {
            HabitsScreen(openDrawer)
        }
        composable(
            Screen.Edit.route, arguments = listOf(navArgument("habitId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("habitId") ?: -1
            EditScreen(id)
        }
    }
}
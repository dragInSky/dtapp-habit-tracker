package com.example.dtapp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dtapp.screens.AboutScreen
import com.example.dtapp.screens.EditScreen
import com.example.dtapp.screens.HabitsScreen
import kotlinx.coroutines.Job

@Composable
fun RootNavHost(context: Context, navController: NavHostController, openDrawer: () -> Job) {
    NavHost(
        navController = navController, startDestination = Screen.Home.route
    ) {
        composable(Screen.About.route) {
            AboutScreen(context, navController)
        }
        composable(Screen.Home.route) {
            HabitsScreen(context, navController, openDrawer)
        }
        composable(
            Screen.EDIT.route, arguments = listOf(navArgument("habitId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("habitId") ?: -1
            EditScreen(context, navController, id)
        }
    }
}
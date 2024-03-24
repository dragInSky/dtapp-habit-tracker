package com.example.dtapp

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun RootNavHost(context: Context) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
//        composable(Screen.Info.route) {
//            EditHabitScreen(context)
//        }
        composable(Screen.Home.route) {
            HabitsScreen(context, navController)
        }
        composable(
            Screen.EDIT.route,
            arguments = listOf(navArgument("habitId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("habitId") ?: -1
            EditHabitScreen(context, navController, id)
        }
    }
}
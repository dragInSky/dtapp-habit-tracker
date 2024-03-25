package com.example.dtapp.navigation

private object Route {
    const val ABOUT = "ABOUT"
    const val HOME = "HOME"
    const val EDIT = "EIT/{habitId}"
}

sealed class Screen(val route: String) {
    object About : Screen(Route.ABOUT)
    object Home : Screen(Route.HOME)
    object EDIT : Screen(Route.EDIT) {
        fun createRoute(habitId: Int = -1) = Route.EDIT.replace("{habitId}", habitId.toString())
    }
}
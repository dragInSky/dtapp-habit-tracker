package com.example.dtapp.navigation

private object Route {
    const val ABOUT = "ABOUT"
    const val HOME = "HOME"
    const val EDIT = "EDIT/{habitId}"
}

sealed class Screen(val route: String) {
    data object About : Screen(Route.ABOUT)
    data object Home : Screen(Route.HOME)
    data object Edit : Screen(Route.EDIT) {
        fun createRoute(habitId: Int = -1) = Route.EDIT.replace("{habitId}", habitId.toString())
    }
}
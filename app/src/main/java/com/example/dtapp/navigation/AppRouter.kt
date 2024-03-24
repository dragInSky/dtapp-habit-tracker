package com.example.dtapp.navigation

private object Route {
    const val INFO = "info"
    const val HOME = "home"
    const val EDIT = "edit/{habitId}"
}

sealed class Screen(val route: String) {
    object Info : Screen(Route.INFO)
    object Home : Screen(Route.HOME)
    object EDIT : Screen(Route.EDIT) {
        fun createRoute(habitId: Int = -1) =
            Route.EDIT.replace("{habitId}", habitId.toString())
    }
}
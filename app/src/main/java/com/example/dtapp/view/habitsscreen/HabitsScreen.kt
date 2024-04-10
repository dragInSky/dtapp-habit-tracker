package com.example.dtapp.view.habitsscreen

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.dtapp.view.theme.PurpleGrey80
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(
    navController: NavController,
    openDrawer: () -> Job
) {
    BottomSheetScaffold(
        sheetContent = BottomSheetContent(navController = navController),
        containerColor = PurpleGrey80
    ) {
        HabitsScreenContent(
            navController = navController,
            openDrawer = openDrawer
        )
    }
}
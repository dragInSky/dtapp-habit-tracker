package com.example.view.view.habitsscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Job

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(
    navController: NavController,
    openDrawer: () -> Job
) {
    BottomSheetScaffold(
        sheetContent = BottomSheetContent(navController = navController),
        containerColor = MaterialTheme.colorScheme.background,
        sheetPeekHeight = 116.dp
    ) {
        HabitsScreenContent(
            navController = navController,
            openDrawer = openDrawer
        )
    }
}
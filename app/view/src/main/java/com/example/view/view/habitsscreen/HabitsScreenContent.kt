package com.example.view.view.habitsscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.view.R
import com.example.view.view.common.TopBar
import kotlinx.coroutines.Job

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HabitsScreenContent(
    navController: NavController,
    openDrawer: () -> Job
) {
    val pagerState = rememberPagerState(pageCount = { com.example.domain.entities.Type.values().size })

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                title = stringResource(R.string.home_screen_name),
                buttonIcon = Icons.Filled.Menu,
                onClick = { openDrawer() }
            )

            Tab(
                pagerState = pagerState,
                pages = com.example.domain.entities.Type.values().map { it.getName() }
            )

            HabitPager(
                navController = navController,
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            )
        }
    }
}
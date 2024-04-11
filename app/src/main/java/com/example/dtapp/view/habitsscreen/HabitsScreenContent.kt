package com.example.dtapp.view.habitsscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.dtapp.R
import com.example.dtapp.models.Type
import com.example.dtapp.view.common.TopBar
import kotlinx.coroutines.Job

@Composable
fun HabitsScreenContent(
    navController: NavController,
    openDrawer: () -> Job
) {
    val context = LocalContext.current

    val pagerState = rememberPagerState(pageCount = { Type.values().size })

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                title = ContextCompat.getString(context, R.string.home_screen_name),
                buttonIcon = Icons.Filled.Menu,
                onClick = { openDrawer() }
            )

            Tab(
                pagerState = pagerState,
                pages = Type.values().map { it.getName() }
            )

            HabitPager(
                navController = navController,
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            )
        }
    }
}
package com.example.dtapp.ui.habitsscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.dtapp.R
import com.example.dtapp.models.Type
import com.example.dtapp.ui.common.TopBar
import kotlinx.coroutines.Job

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitsScreen(openDrawer: () -> Job) {
    val context = LocalContext.current

    val types = Type.values()
    val pagerState = rememberPagerState(pageCount = { types.size })

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = ContextCompat.getString(context, R.string.home_screen_name),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() })

        Tab(pagerState, Type.values().map { it.text })

        HabitPager(types, Modifier.weight(1f), pagerState)
    }
}
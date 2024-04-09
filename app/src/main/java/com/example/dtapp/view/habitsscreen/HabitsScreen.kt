package com.example.dtapp.view.habitsscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.dtapp.R
import com.example.dtapp.models.Type
import com.example.dtapp.view.common.TopBar
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(navController: NavController, openDrawer: () -> Job) {
    val context = LocalContext.current

    val pagerState = rememberPagerState(pageCount = { Type.values().size })

    BottomSheetScaffold(
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
            ) {
                Text("Bottom Sheet Content", modifier = Modifier.align(Alignment.Center))
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                title = ContextCompat.getString(context, R.string.home_screen_name),
                buttonIcon = Icons.Filled.Menu,
                onButtonClicked = { openDrawer() }
            )

            Tab(
                pagerState = pagerState,
                pages = Type.values().map { it.text }
            )

            HabitPager(
                navController = navController,
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            )
        }
    }
}
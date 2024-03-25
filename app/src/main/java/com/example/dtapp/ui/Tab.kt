package com.example.dtapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tab(pagerState: PagerState, pages: List<String>) {
    val scrollCoroutineScope = rememberCoroutineScope()

    Box {
        TabRow(
            selectedTabIndex = pagerState.currentPage, indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            }, modifier = Modifier.align(Alignment.TopCenter)
        ) {
            pages.forEachIndexed { index, title ->
                Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .clickable {
                        scrollCoroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                    .padding(8.dp)) {
                    Text(text = title)
                }
            }
        }
    }
}
package com.example.dtapp.view.habitsscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dtapp.R
import com.example.dtapp.entities.SortOrder
import com.example.dtapp.navigation.Screen
import com.example.dtapp.view.editscreen.HidingTextField
import com.example.dtapp.viewmodels.HabitsViewModel

@Composable
fun BottomSheetContent(
    navController: NavController,
    habitsViewModel: HabitsViewModel = viewModel()
): @Composable (ColumnScope.() -> Unit) {
    return {
        Box(
            Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.filtersAndSorts),
                    fontWeight = FontWeight.Light
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.sortByPriority)
                    )
                    IconButton(
                        onClick = { habitsViewModel.setSortOrder(SortOrder.Descending) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = ""
                        )
                    }
                    IconButton(
                        onClick = { habitsViewModel.setSortOrder(SortOrder.Ascending) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = ""
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    HidingTextField(
                        text = habitsViewModel.search,
                        placeHolder = stringResource(R.string.search),
                        modifier = Modifier.weight(1f),
                        onTextChanged = { habitsViewModel.changeSearchField(it) }
                    )
                    IconButton(
                        onClick = { habitsViewModel.clear() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = ""
                        )
                    }
                }
            }

            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.Edit.createRoute()) {
                        launchSingleTop = true
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 24.dp, end = 24.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "habit add action",
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}
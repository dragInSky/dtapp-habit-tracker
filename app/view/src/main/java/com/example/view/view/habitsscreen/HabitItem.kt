package com.example.view.view.habitsscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.entities.DateProducer
import com.example.domain.entities.HabitInfo
import com.example.view.R
import com.example.view.viewmodels.HabitsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HabitItem(
    onClick: () -> Unit,
    habit: HabitInfo,
    habitsViewModel: HabitsViewModel = viewModel()
) {
    val context = LocalContext.current

    var leftToDo by remember { mutableStateOf(habit.doneDates.size) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)
        .clickable { onClick() }
        .padding(12.dp)
    ) {
        Column(modifier = Modifier.weight(0.55f)) {
            if (habit.name.isNotEmpty()) {
                Text(
                    text = habit.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = DateProducer.dateFromInt(habit.date),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (habit.description.isNotEmpty()) {
                Text(
                    text = habit.description,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(lineHeight = 12.sp)
                )
            }

            Text(
                text = stringResource(R.string.habit_priority, habit.priority.getName()),
                fontSize = 12.sp
            )
        }

        Column(modifier = Modifier.weight(0.25f)) {
            if (habit.frequency > 0) {
                Row {
                    Text(
                        text = habit.frequency.toString(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(R.string.habit_count_in),
                        fontSize = 10.sp
                    )
                }
            }
            if (habit.count > 0) {
                Row {
                    Text(
                        text = "$leftToDo/${habit.count}",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = " " + stringResource(R.string.habit_left),
                        fontSize = 10.sp
                    )
                }
            }
        }

        IconButton(
            modifier = Modifier
                .weight(0.2f)
                .align(Alignment.CenterVertically),
            onClick = { leftToDo = habitsViewModel.onDoneClick(context, habit) }
        ) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "",
                Modifier.size(48.dp)
            )
        }
    }
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
    Spacer(modifier = Modifier.height(12.dp))
}
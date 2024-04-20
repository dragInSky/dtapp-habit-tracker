package com.example.dtapp.view.habitsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.R

@Composable
fun HabitItem(onClick: () -> Unit, habit: HabitInfo) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)
        .clickable { onClick() }
        .padding(12.dp)
    ) {
        Column(modifier = Modifier.weight(0.8f)) {
            if (habit.name.isNotEmpty()) {
                Text(
                    text = habit.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = habit.date,
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

        Column(modifier = Modifier.weight(0.2f)) {
            if (habit.times.isNotEmpty() || habit.period.isNotEmpty()) {
                Text(
                    text = habit.times,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.habit_times_period_con),
                    fontSize = 12.sp
                )
                Text(
                    text = habit.period,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
    Spacer(modifier = Modifier.height(12.dp))
}
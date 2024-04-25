package com.example.dtapp.view.habitsscreen

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
import com.example.dtapp.models.DateProducer

@RequiresApi(Build.VERSION_CODES.O)
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

        Column(modifier = Modifier.weight(0.2f)) {
            if (habit.count > 0 || habit.frequency > 0) {
                Row {
                    Text(
                        text = habit.count.toString(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(R.string.habit_count_in),
                        fontSize = 12.sp
                    )
                }
                Row {
                    Text(
                        text = habit.frequency.toString(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(R.string.habit_frequency_in),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
    Spacer(modifier = Modifier.height(12.dp))
}
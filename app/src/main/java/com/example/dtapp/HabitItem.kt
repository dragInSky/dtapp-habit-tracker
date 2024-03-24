package com.example.dtapp

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.startActivity

@Composable
fun HabitItem(context: Context, habit: HabitInfo) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .clickable { //вынести во ViewModel
            val intent = Intent(
                context, HabitCreatorActivity::class.java
            ).apply { putExtra(getString(context, R.string.habit_item), habit) }
            startActivity(context, intent, null)
        }
        .padding(12.dp)) {
        Column(modifier = Modifier.weight(0.8f)) {
            Text(
                text = habit.nameText,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = habit.descriptionText,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(lineHeight = 12.sp)
            )
            Row {
                Text(
                    text = "${habit.typeText} — ${habit.priorityText}", fontSize = 12.sp
                )
            }
        }

        Column(modifier = Modifier.weight(0.2f)) {
            Text(
                text = habit.timesText,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = getString(context, R.string.habit_times_period_con), fontSize = 12.sp
            )
            Text(
                text = habit.periodText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    Divider(color = Color.Black, thickness = 1.dp)
}
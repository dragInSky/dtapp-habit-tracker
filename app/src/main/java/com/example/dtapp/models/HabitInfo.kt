package com.example.dtapp.models

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.dtapp.Ambient
import com.example.dtapp.R
import java.util.UUID
import kotlin.math.absoluteValue

data class HabitInfo(
    val id: Int,
    val priority: Priority, val type: Type,
    val nameText: String, val descriptionText: String,
    val timesText: String, val periodText: String
) {
    companion object {
        private fun habitInit(
            context: Context, id: Int,
            selectedPriority: String, selectedType: String,
            nameText: String, descriptionText: String,
            timesText: String, periodText: String
        ): HabitInfo {
            return HabitInfo(id = if (id == -1) UUID.randomUUID().hashCode().absoluteValue else id,
                priority = Priority.values().find { it.text == selectedPriority }!!,
                type = Type.values().find { it.text == selectedType }!!,
                nameText.ifEmpty { ContextCompat.getString(context, R.string.habit_name) },
                descriptionText.ifEmpty {
                    ContextCompat.getString(
                        context,
                        R.string.habit_description
                    )
                },
                timesText.ifEmpty { ContextCompat.getString(context, R.string.habit_times) },
                periodText.ifEmpty { ContextCompat.getString(context, R.string.habit_period) })
        }

        fun habitListAction(
            context: Context, id: Int,
            selectedPriority: String, selectedType: String,
            nameText: String, descriptionText: String,
            timesText: String, periodText: String
        ) {
            val habit = habitInit(
                context, id, selectedPriority, selectedType,
                nameText, descriptionText, timesText, periodText
            )

            if (habit.id != id) {
                Ambient.habitList.add(habit)
            } else {
                val habitToEdit = Ambient.habitList.find { it.id == id }
                val idxToEdit = Ambient.habitList.indexOf(habitToEdit)
                Ambient.habitList[idxToEdit] = habit
            }
        }
    }
}
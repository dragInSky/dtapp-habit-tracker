package com.example.dtapp.models

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.dtapp.R
import java.util.UUID
import kotlin.math.absoluteValue

data class HabitInfo(
    val id: Int,
    val priority: Priority,
    val type: Type,
    val nameText: String,
    val descriptionText: String,
    val timesText: String,
    val periodText: String
) {
    companion object {
        const val DEFAULT_ID = -1

        fun habitInit(
            context: Context,
            id: Int,
            selectedPriority: String,
            selectedType: String,
            nameText: String,
            descriptionText: String,
            timesText: String,
            periodText: String
        ): HabitInfo {
            return HabitInfo(if (id == DEFAULT_ID) UUID.randomUUID()
                .hashCode().absoluteValue else id,
                Priority.values().find { it.text == selectedPriority }!!,
                Type.values().find { it.text == selectedType }!!,
                nameText.ifEmpty { ContextCompat.getString(context, R.string.habit_name) },
                descriptionText.ifEmpty {
                    ContextCompat.getString(
                        context, R.string.habit_description
                    )
                },
                timesText.ifEmpty { ContextCompat.getString(context, R.string.habit_times) },
                periodText.ifEmpty { ContextCompat.getString(context, R.string.habit_period) })
        }
    }
}
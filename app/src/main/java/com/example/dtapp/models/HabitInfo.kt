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
    val name: String,
    val description: String,
    val times: String,
    val period: String
) {
    companion object {
        const val DEFAULT_ID = -1

        fun habitInit(
            context: Context,
            id: Int,
            selectedPriority: String,
            selectedType: String,
            name: String,
            description: String,
            times: String,
            period: String
        ): HabitInfo {
            return HabitInfo(if (id == DEFAULT_ID) UUID.randomUUID()
                .hashCode().absoluteValue else id,
                Priority.values().find { it.text == selectedPriority }!!,
                Type.values().find { it.text == selectedType }!!,
                name.ifEmpty { ContextCompat.getString(context, R.string.habit_name) },
                description.ifEmpty {
                    ContextCompat.getString(
                        context, R.string.habit_description
                    )
                },
                times.ifEmpty { ContextCompat.getString(context, R.string.habit_times) },
                period.ifEmpty { ContextCompat.getString(context, R.string.habit_period) })
        }
    }
}
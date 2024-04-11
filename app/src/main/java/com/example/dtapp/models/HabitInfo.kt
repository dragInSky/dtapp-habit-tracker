package com.example.dtapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

        @RequiresApi(Build.VERSION_CODES.O)
        fun habitInit(
            id: Int,
            selectedPriority: String,
            selectedType: String,
            name: String,
            description: String,
            times: String,
            period: String
        ): HabitInfo {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val current = LocalDateTime.now().format(formatter)

            return HabitInfo(
                if (id == DEFAULT_ID) UUID.randomUUID().hashCode().absoluteValue else id,
                Priority.values().find { it.getName() == selectedPriority }!!,
                Type.values().find { it.getName() == selectedType }!!,
                name.ifEmpty { current.toString() },
                description,
                times,
                period
            )
        }
    }
}
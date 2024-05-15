package com.example.domain.converters

import androidx.room.TypeConverter
import com.example.domain.entities.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority): Int {
        return priority.ordinal
    }

    @TypeConverter
    fun toPriority(ordinal: Int): Priority {
        return Priority.values()[ordinal]
    }
}
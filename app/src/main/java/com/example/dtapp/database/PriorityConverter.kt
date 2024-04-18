package com.example.dtapp.database

import androidx.room.TypeConverter
import com.example.dtapp.models.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(type: Priority): String {
        return type.name
    }

    @TypeConverter
    fun toPriority(data: String): Priority {
        return Priority.valueOf(data)
    }
}
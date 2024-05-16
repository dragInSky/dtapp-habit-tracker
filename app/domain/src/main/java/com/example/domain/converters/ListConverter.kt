package com.example.domain.converters

import androidx.room.TypeConverter

class ListConverter {
    @TypeConverter
    fun fromList(list: List<Int>): String = list.joinToString(",")

    @TypeConverter
    fun toList(value: String): List<Int> {
        return listOf(*value.split(",")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
            .toTypedArray())
    }
}

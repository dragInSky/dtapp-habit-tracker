package com.example.domain.converters

import androidx.room.TypeConverter

class ListConverter {
    @TypeConverter
    fun fromList(list: MutableList<Int>): String {
        return list.joinToString { "," }
    }

    @TypeConverter
    fun toList(str: String): MutableList<Int> {
        return str.split(",").filter { it.isNotBlank() }.map { it.toInt() }.toMutableList()
    }
}

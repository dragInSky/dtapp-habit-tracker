package com.example.domain.converters

import androidx.room.TypeConverter
import com.example.domain.entities.Type

class MyTypeConverter {
    @TypeConverter
    fun fromType(type: Type): Int {
        return type.ordinal
    }

    @TypeConverter
    fun toType(ordinal: Int): Type {
        return Type.values()[ordinal]
    }
}
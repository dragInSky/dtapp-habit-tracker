package com.example.dtapp.database

import androidx.room.TypeConverter
import com.example.dtapp.entities.Type

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
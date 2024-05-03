package com.example.data.database

import androidx.room.TypeConverter

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
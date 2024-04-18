package com.example.dtapp.database

import androidx.room.TypeConverter
import com.example.dtapp.models.Type

class MyTypeConverter {
    @TypeConverter
    fun fromType(type: Type): String {
        return type.name
    }

    @TypeConverter
    fun toType(data: String): Type {
        return Type.valueOf(data)
    }
}
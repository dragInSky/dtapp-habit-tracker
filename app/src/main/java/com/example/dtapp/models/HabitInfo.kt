package com.example.dtapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dtapp.database.MyTypeConverter
import com.example.dtapp.database.PriorityConverter

@Entity
@TypeConverters(MyTypeConverter::class)
data class HabitInfo(
    @TypeConverters(PriorityConverter::class) val priority: Priority,
    @TypeConverters(MyTypeConverter::class) val type: Type,
    val name: String,
    val description: String,
    val times: String,
    val period: String,
    val date: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {
    companion object {
        const val DEFAULT_ID = -1

        @RequiresApi(Build.VERSION_CODES.O)
        fun habitInit(
            selectedPriority: String,
            selectedType: String,
            name: String,
            description: String,
            times: String,
            period: String,
            date: String,
            id: Int
        ): HabitInfo {
            return HabitInfo(
                Priority.values().find { it.getName() == selectedPriority }!!,
                Type.values().find { it.getName() == selectedType }!!,
                name,
                description,
                times,
                period,
                if (id == DEFAULT_ID) DateProducer.getStringDate() else date,
                if (id == DEFAULT_ID) 0 else id
            )
        }
    }
}
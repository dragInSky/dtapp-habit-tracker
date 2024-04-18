package com.example.dtapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dtapp.database.MyTypeConverter
import com.example.dtapp.database.PriorityConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@TypeConverters(MyTypeConverter::class)
data class HabitInfo(
    @ColumnInfo @TypeConverters(PriorityConverter::class) val priority: Priority,
    @ColumnInfo @TypeConverters(MyTypeConverter::class) val type: Type,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val times: String,
    @ColumnInfo val period: String,
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
            id: Int
        ): HabitInfo {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val current = LocalDateTime.now().format(formatter)

            return HabitInfo(
                Priority.values().find { it.getName() == selectedPriority }!!,
                Type.values().find { it.getName() == selectedType }!!,
                name.ifEmpty { current.toString() },
                description,
                times,
                period,
                if (id == DEFAULT_ID) 0 else id
            )
        }
    }
}
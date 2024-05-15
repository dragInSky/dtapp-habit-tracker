package com.example.domain.entities

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.domain.converters.ListConverter
import com.example.domain.converters.MyTypeConverter
import com.example.domain.converters.PriorityConverter

@Entity
@TypeConverters(ListConverter::class, PriorityConverter::class, MyTypeConverter::class)
data class HabitInfo(
    @TypeConverters(PriorityConverter::class) val priority: Priority,
    @TypeConverters(MyTypeConverter::class) val type: Type,
    val name: String,
    val description: String,
    val count: Int,
    val frequency: Int,
    val date: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = DEFAULT_ID,
    val uid: String = "",
    @TypeConverters(ListConverter::class) val doneDates: List<Int> = emptyList()
) {
    companion object {
        const val DEFAULT_ID = 0

        @RequiresApi(Build.VERSION_CODES.O)
        fun habitInit(
            selectedPriority: String,
            selectedType: String,
            name: String,
            description: String,
            count: String,
            frequency: String,
            uid: String,
            id: Int
        ): HabitInfo {
            return HabitInfo(
                Priority.values().find { it.getName() == selectedPriority }!!,
                Type.values().find { it.getName() == selectedType }!!,
                name,
                description,
                count.toIntOrNull() ?: 0,
                frequency.toIntOrNull() ?: 0,
                DateProducer.getIntDate(),
                id,
                uid
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HabitInfo

        if (priority != other.priority) return false
        if (type != other.type) return false
        if (count != other.count) return false
        if (frequency != other.frequency) return false
        if (date != other.date) return false
        if (doneDates != other.doneDates) return false

        return true
    }

    override fun hashCode(): Int {
        var result = priority.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + count
        result = 31 * result + frequency
        result = 31 * result + date
        result = 31 * result + doneDates.hashCode()
        return result
    }
}
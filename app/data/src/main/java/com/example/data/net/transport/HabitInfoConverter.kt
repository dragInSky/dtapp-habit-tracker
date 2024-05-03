package com.example.data.net.transport

import com.example.dtapp.entities.HabitInfo
import com.example.dtapp.entities.Priority
import com.example.dtapp.entities.Type

class HabitInfoConverter {
    fun fromTransport(transportHabitInfo: TransportHabitInfo): HabitInfo {
        return HabitInfo(
            priority = Priority.values()[transportHabitInfo.priority],
            type = Type.values()[transportHabitInfo.type],
            name = transportHabitInfo.title,
            description = transportHabitInfo.description,
            count = transportHabitInfo.count,
            frequency = transportHabitInfo.frequency,
            date = transportHabitInfo.date,
            doneDates = transportHabitInfo.done_dates,
            uid = transportHabitInfo.uid
        )
    }

    fun toTransport(habitInfo: HabitInfo): TransportHabitInfo {
        return TransportHabitInfo(
            count = habitInfo.count,
            date = habitInfo.date,
            description = habitInfo.description.ifEmpty { "no description" },
            frequency = habitInfo.frequency,
            priority = habitInfo.priority.ordinal,
            title = habitInfo.name.ifEmpty { "no title" },
            type = habitInfo.type.ordinal,
            done_dates = habitInfo.doneDates,
            uid = habitInfo.uid
        )
    }
}
package com.example.dtapp.net.transport

import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.Priority
import com.example.dtapp.models.Type

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
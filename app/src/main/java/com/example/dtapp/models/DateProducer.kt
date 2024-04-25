package com.example.dtapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DateProducer {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private val dateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0)
        @RequiresApi(Build.VERSION_CODES.O)
        fun getIntDate(): Int {
            val now = LocalDateTime.now()
            val secondsSinceReference = ChronoUnit.SECONDS.between(dateTime, now)

            return secondsSinceReference.toInt()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun dateFromInt(seconds: Int): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            val newDateTime = dateTime.plusSeconds(seconds.toLong())

            return newDateTime.format(formatter)
        }
    }
}
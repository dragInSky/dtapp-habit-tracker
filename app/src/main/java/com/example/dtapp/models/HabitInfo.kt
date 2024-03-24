package com.example.dtapp.models

data class HabitInfo(
    val id: Int,
    val priority: Priority, val type: Type,
    val nameText: String, val descriptionText: String,
    val timesText: String, val periodText: String
)
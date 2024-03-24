package com.example.dtapp

import java.io.Serializable

data class HabitInfo(
    val id: Int,
    val priorityText: String, val typeText: String,
    val nameText: String, val descriptionText: String,
    val timesText: String, val periodText: String
) : Serializable
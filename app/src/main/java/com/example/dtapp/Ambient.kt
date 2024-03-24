package com.example.dtapp

import androidx.compose.runtime.mutableStateListOf

object Ambient {
    val priorities = listOf("highest", "high", "medium", "low")
    val types = listOf("good", "bad")

    val habitList = mutableStateListOf<HabitInfo>()
}
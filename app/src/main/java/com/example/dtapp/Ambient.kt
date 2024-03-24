package com.example.dtapp

import androidx.compose.runtime.mutableStateListOf
import com.example.dtapp.models.HabitInfo

object Ambient {
    val habitList = mutableStateListOf<HabitInfo>()
}
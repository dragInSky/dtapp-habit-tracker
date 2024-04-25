package com.example.dtapp.net.transport

import kotlinx.serialization.Serializable

@Serializable
data class TransportHabitInfo(
    val count: Int,
    val date: Int,
    val description: String,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String = "",
    val done_dates: List<Int> = emptyList()
)
package com.example.data.net.transport

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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
    val done_dates: List<Int> = emptyList(),
    @Transient val id: Int = 0
)
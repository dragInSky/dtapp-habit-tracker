package com.example.domain.entities

import java.util.Locale

enum class Priority {
    HIGH,
    MEDIUM,
    LOW;

    fun getName(): String = name.lowercase(Locale.ROOT)
}
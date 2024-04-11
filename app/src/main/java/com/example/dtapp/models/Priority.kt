package com.example.dtapp.models

import java.util.Locale

enum class Priority {
    HIGHEST,
    HIGH,
    MEDIUM,
    LOW;

    fun getName(): String = name.lowercase(Locale.ROOT)
}
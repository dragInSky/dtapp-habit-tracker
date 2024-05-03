package com.example.domain.entities

import java.util.Locale

enum class Type {
    GOOD,
    BAD;

    fun getName(): String = name.lowercase(Locale.ROOT)
}
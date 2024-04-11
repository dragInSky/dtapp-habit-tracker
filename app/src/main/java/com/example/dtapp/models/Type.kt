package com.example.dtapp.models

import java.util.Locale

enum class Type {
    GOOD,
    BAD;

    fun getName(): String = name.lowercase(Locale.ROOT)
}
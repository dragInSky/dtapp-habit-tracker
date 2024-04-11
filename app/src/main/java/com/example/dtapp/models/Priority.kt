package com.example.dtapp.models

import com.example.dtapp.R

enum class Priority(val text: String) {
    HIGHEST(R.string.priority_highest.toString()),
    HIGH(R.string.priority_high.toString()),
    MEDIUM(R.string.priority_medium.toString()),
    LOW(R.string.priority_low.toString())
}
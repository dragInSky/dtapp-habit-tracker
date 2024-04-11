package com.example.dtapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dtapp.Model
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.Type

class HabitsViewModel : ViewModel() {
    var filteredHabits: MutableList<HabitInfo> = mutableStateListOf()

    var search = mutableStateOf("")
    var predicate: MutableState<((HabitInfo) -> Boolean)?> = mutableStateOf(null)
    private var sortRule = mutableStateOf(true)

    fun changeHabits() {
        if (predicate.value != null) {
            filteredHabits = filteredHabits.filter(predicate.value!!).toMutableList()
        }

        if (sortRule.value)
            filteredHabits.sortBy { it.name }
        else
            filteredHabits.sortByDescending { it.name }
    }

    fun pagingHabits(page: Int) {
        if (page == 0) goodHabits()
        else  badHabits()
    }

    fun sortBy() {
        sortRule.value = true
    }

    fun sortByDesc() {
        sortRule.value = false
    }

    private fun goodHabits() {
        filteredHabits = Model.habits.filter { e -> e.type == Type.GOOD }.toMutableList()
    }

    private fun badHabits() {
        filteredHabits = Model.habits.filter { e -> e.type == Type.BAD }.toMutableList()
    }
}

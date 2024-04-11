package com.example.dtapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dtapp.Model
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.Type

class HabitsViewModel : ViewModel() {
    private var filteredHabits: MutableList<HabitInfo> = mutableStateListOf()

    private var searchRule: MutableState<((HabitInfo) -> Boolean)?> = mutableStateOf(null)
    private var sortRule = mutableStateOf(true)

    private var _search = mutableStateOf("")
    val search: State<String> = _search

    fun clear() {
        sortRule.value = true

        clearSearch()
    }

    fun clearSearch() {
        searchRule.value = null
        _search.value = ""
    }

    fun pagedHabits(page: Int): List<HabitInfo> {
        if (page == 0) goodHabits()
        else badHabits()

        sortAndFilterHabits()

        return filteredHabits
    }

    private fun sortAndFilterHabits() {
        if (searchRule.value != null) {
            filteredHabits = filteredHabits.filter(searchRule.value!!).toMutableList()
        }

        if (sortRule.value)
            filteredHabits.sortBy { it.priority.ordinal }
        else
            filteredHabits.sortByDescending { it.priority.ordinal }
    }

    fun changeSearchField(name: String) {
        _search.value = name
        nameFilter(name)
    }

    private fun nameFilter(name: String) {
        searchRule.value = { habit -> habit.name.contains(name) }
    }

    fun sortAsc() {
        sortRule.value = true
    }

    fun sortDesc() {
        sortRule.value = false
    }

    private fun goodHabits() {
        filteredHabits = Model.habits.filter { e -> e.type == Type.GOOD }.toMutableList()
    }

    private fun badHabits() {
        filteredHabits = Model.habits.filter { e -> e.type == Type.BAD }.toMutableList()
    }
}

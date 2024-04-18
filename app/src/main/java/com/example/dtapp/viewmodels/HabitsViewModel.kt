package com.example.dtapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dtapp.Model
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HabitsViewModel : ViewModel() {
    private val _goodHabits = MutableStateFlow<List<HabitInfo>>(emptyList())
    private val _badHabits = MutableStateFlow<List<HabitInfo>>(emptyList())

    private val _searchRule = MutableStateFlow<((HabitInfo) -> Boolean)?>(null)
    private val _sortRule = MutableStateFlow<Boolean?>(null)

    private val _search = mutableStateOf("")
    val search: State<String> = _search

    init {
        observeGoodHabits()
        observeBadHabits()
    }

    fun clear() {
        _sortRule.value = null
        clearSearch()
    }

    fun clearSearch() {
        _searchRule.value = null
        _search.value = ""
    }

    fun pagedHabits(page: Int): Flow<List<HabitInfo>> {
        val habits = if (page == 0) _goodHabits else _badHabits

        val sorted = sort(habits)

        return filter(sorted)
    }

    private fun sort(habits: Flow<List<HabitInfo>>): Flow<List<HabitInfo>> {
        return combine(
            habits,
            _sortRule
        ) { resHabits, sort ->
            sort?.let { asc ->
                if (asc) resHabits.sortedBy { it.priority }
                else resHabits.sortedByDescending { it.priority }
            } ?: resHabits
        }
    }

    private fun filter(habits: Flow<List<HabitInfo>>): Flow<List<HabitInfo>> {
        return combine(
            habits,
            _searchRule
        ) { resHabits, search ->
            search?.let { rule ->
                resHabits.filter(rule)
            } ?: resHabits
        }
    }

    fun changeSearchField(name: String) {
        _search.value = name
        nameFilter(name)
    }

    private fun nameFilter(name: String) {
        _searchRule.value = { habit -> habit.name.contains(name) }
    }

    fun sortAsc() {
        _sortRule.value = true
    }

    fun sortDesc() {
        _sortRule.value = false
    }

    private fun observeGoodHabits() {
        viewModelScope.launch {
            Model.database.habitDao().findByType(Type.GOOD)
                .asFlow()
                .collect { habits -> _goodHabits.value = habits }
        }
    }

    private fun observeBadHabits() {
        viewModelScope.launch {
            Model.database.habitDao().findByType(Type.BAD)
                .asFlow()
                .collect { habits -> _badHabits.value = habits }
        }
    }
}

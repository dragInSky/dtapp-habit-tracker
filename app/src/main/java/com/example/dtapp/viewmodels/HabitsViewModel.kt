package com.example.dtapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dtapp.Model
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.SortOrder
import com.example.dtapp.models.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HabitsViewModel : ViewModel() {
    private val _goodHabits = MutableStateFlow<List<HabitInfo>>(emptyList())
    private val _badHabits = MutableStateFlow<List<HabitInfo>>(emptyList())

    private val _defaultSearchRule: (HabitInfo) -> Boolean = { it.name.contains("") }
    private val _searchRule = MutableStateFlow(_defaultSearchRule)
    val sortOrder = MutableStateFlow(SortOrder.Default)

    private val _search = mutableStateOf("")
    val search: State<String> = _search

    init {
        observeGoodHabits()
        observeBadHabits()
    }

    fun pagedHabits(page: Int): Flow<List<HabitInfo>> {
        return if (page == Type.GOOD.ordinal) sort(filter(_goodHabits)) else sort(filter(_badHabits))
    }

    private fun sort(habits: Flow<List<HabitInfo>>): Flow<List<HabitInfo>> {
        return combine(
            habits,
            sortOrder
        ) { resHabits, sortRule ->
            when (sortRule) {
                SortOrder.Ascending -> resHabits.sortedBy { it.priority }
                SortOrder.Descending -> resHabits.sortedByDescending { it.priority }
                SortOrder.Default -> resHabits
            }
        }
    }

    private fun filter(habits: Flow<List<HabitInfo>>): Flow<List<HabitInfo>> {
        return combine(
            habits,
            _searchRule
        ) { resHabits, searchRule ->
            resHabits.filter(searchRule)
        }
    }

    fun clear() {
        sortOrder.value = SortOrder.Default
        clearSearch()
    }

    fun clearSearch() {
        _searchRule.value = _defaultSearchRule
        _search.value = ""
    }

    fun changeSearchField(name: String) {
        _search.value = name
        _searchRule.value = { habit -> habit.name.contains(name) }
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

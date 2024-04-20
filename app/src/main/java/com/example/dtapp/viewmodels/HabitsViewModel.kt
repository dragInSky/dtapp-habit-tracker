package com.example.dtapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.example.dtapp.App
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.SortOrder
import com.example.dtapp.models.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class HabitsViewModel : ViewModel() {
    private val _defaultSearchRule: (HabitInfo) -> Boolean = { it.name.contains("") }
    private val _searchRule = MutableStateFlow(_defaultSearchRule)

    private val sortOrder = MutableStateFlow(SortOrder.Default)

    private var _search by mutableStateOf("")
    val search: String = _search

    var goodHabitFlow = App.instance.database.habitDao().loadByType(Type.GOOD).asFlow()
        private set
    var badHabitFlow = App.instance.database.habitDao().loadByType(Type.BAD).asFlow()
        private set

    fun setSortOrder(sortOrder: SortOrder) {
        this.sortOrder.value = sortOrder
    }

    fun applyConditions(page: Int) {
        if (page == Type.GOOD.ordinal) {
            goodHabitFlow = sort(filter(goodHabitFlow))
        } else {
            badHabitFlow = sort(filter(badHabitFlow))
        }
    }

    // TODO suspend/Dispatcher
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
        _search = ""
    }

    fun changeSearchField(name: String) {
        _search = name
        _searchRule.value = { habit -> habit.name.contains(name) }
    }
}

package com.example.dtapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dtapp.App
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.SortOrder
import com.example.dtapp.models.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HabitsViewModel : ViewModel() {
    private val sortOrder = MutableStateFlow(SortOrder.Default)

    var search by mutableStateOf("")
        private set

    private val _defaultSearchRule: (HabitInfo) -> Boolean = { it.name.contains("") }
    private var searchRule = MutableStateFlow(_defaultSearchRule)

    lateinit var goodHabitFlow: Flow<List<HabitInfo>>
        private set
    lateinit var badHabitFlow: Flow<List<HabitInfo>>
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            goodHabitFlow =
                sort(
                    filter(
                        App.instance.database.habitDao().loadByType(Type.GOOD.ordinal)
                    )
                )
            badHabitFlow =
                sort(
                    filter(
                        App.instance.database.habitDao().loadByType(Type.BAD.ordinal)
                    )
                )
        }
    }

    private fun sort(habits: Flow<List<HabitInfo>>): Flow<List<HabitInfo>> {
        return combine(
            habits,
            sortOrder
        ) { resHabits, sortRule ->
            when (sortRule) {
                SortOrder.Ascending -> resHabits.sortedBy { it.priority }
                SortOrder.Descending -> resHabits.sortedByDescending { it.priority }
                SortOrder.Default -> resHabits.sortedBy { it.date }
            }
        }
    }

    private fun filter(habits: Flow<List<HabitInfo>>): Flow<List<HabitInfo>> {
        return combine(
            habits,
            searchRule
        ) { resHabits, searchRule ->
            resHabits.filter(searchRule)
        }
    }

    fun setSortOrder(sortOrder: SortOrder) {
        this.sortOrder.value = sortOrder
    }

    fun clear() {
        search = ""
        sortOrder.value = SortOrder.Default
        searchRule.value = _defaultSearchRule
    }

    fun changeSearchField(name: String) {
        search = name
        searchRule.value = { habit -> habit.name.contains(name) }
    }
}

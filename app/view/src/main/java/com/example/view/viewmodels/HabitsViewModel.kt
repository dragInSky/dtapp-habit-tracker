package com.example.view.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repositories.HabitRepository
import com.example.view.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HabitsViewModel : ViewModel() {
    private val habitRepository: HabitRepository = App.instance.appComponent.getHabitRepository()

    private val sortOrder = MutableStateFlow(com.example.domain.entities.SortOrder.Default)

    var search by mutableStateOf("")
        private set

    private val _defaultSearchRule: (com.example.domain.entities.HabitInfo) -> Boolean = { it.name.contains("") }
    private var searchRule = MutableStateFlow(_defaultSearchRule)

    lateinit var goodHabitFlow: Flow<List<com.example.domain.entities.HabitInfo>>
        private set
    lateinit var badHabitFlow: Flow<List<com.example.domain.entities.HabitInfo>>
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            goodHabitFlow =
                sort(
                    filter(
                        habitRepository.loadByType(com.example.domain.entities.Type.GOOD.ordinal)
                    )
                )
            badHabitFlow =
                sort(
                    filter(
                        habitRepository.loadByType(com.example.domain.entities.Type.BAD.ordinal)
                    )
                )
        }
    }

    private fun sort(habits: Flow<List<com.example.domain.entities.HabitInfo>>): Flow<List<com.example.domain.entities.HabitInfo>> {
        return combine(
            habits,
            sortOrder
        ) { resHabits, sortRule ->
            when (sortRule) {
                com.example.domain.entities.SortOrder.Ascending -> resHabits.sortedBy { it.priority }
                com.example.domain.entities.SortOrder.Descending -> resHabits.sortedByDescending { it.priority }
                com.example.domain.entities.SortOrder.Default -> resHabits.sortedBy { it.date }
            }
        }
    }

    private fun filter(habits: Flow<List<com.example.domain.entities.HabitInfo>>): Flow<List<com.example.domain.entities.HabitInfo>> {
        return combine(
            habits,
            searchRule
        ) { resHabits, searchRule ->
            resHabits.filter(searchRule)
        }
    }

    fun setSortOrder(sortOrder: com.example.domain.entities.SortOrder) {
        this.sortOrder.value = sortOrder
    }

    fun clear() {
        search = ""
        sortOrder.value = com.example.domain.entities.SortOrder.Default
        searchRule.value = _defaultSearchRule
    }

    fun changeSearchField(name: String) {
        search = name
        searchRule.value = { habit -> habit.name.contains(name) }
    }
}

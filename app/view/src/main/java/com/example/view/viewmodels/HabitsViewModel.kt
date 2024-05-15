package com.example.view.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repositories.HabitRepository
import com.example.domain.entities.DateProducer
import com.example.domain.entities.HabitInfo
import com.example.domain.entities.SortOrder
import com.example.domain.entities.Type
import com.example.view.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HabitsViewModel : ViewModel() {
    private val habitRepository: HabitRepository = App.instance.appComponent.getHabitRepository()

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
                        habitRepository.loadByType(Type.GOOD.ordinal)
                    )
                )
            badHabitFlow =
                sort(
                    filter(
                        habitRepository.loadByType(Type.BAD.ordinal)
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDoneClick(habit: HabitInfo) {
        val curDate = DateProducer.getIntDate()
        habit.doneDates.add(curDate)
        habitRepository.habitDone(habit, curDate)
    }
}

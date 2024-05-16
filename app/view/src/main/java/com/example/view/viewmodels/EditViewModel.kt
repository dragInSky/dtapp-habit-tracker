package com.example.view.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.repositories.HabitRepository
import com.example.domain.entities.HabitInfo
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.view.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel : ViewModel() {
    private val habitRepository: HabitRepository = App.instance.appComponent.getHabitRepository()

    var selectedPriority by mutableStateOf(Priority.MEDIUM.getName())
        private set
    var selectedType by mutableStateOf(Type.GOOD.getName())
        private set
    var name by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var count by mutableStateOf("")
        private set
    var frequency by mutableStateOf("")
        private set
    var doneDates by mutableStateOf(listOf<Int>())
        private set
    private var uid: String = ""

    fun changePriority(priority: String) {
        selectedPriority = priority
    }

    fun changeType(type: String) {
        selectedType = type
    }

    fun changeName(name: String) {
        this.name = name
    }

    fun changeDescription(description: String) {
        this.description = description
    }

    fun changeCount(count: String) {
        this.count = count
    }

    fun changeFrequency(frequency: String) {
        this.frequency = frequency
    }

    fun habitInit(id: Int) {
        lateinit var habit: HabitInfo
        viewModelScope.launch(Dispatchers.IO) {
            habit = getHabitById(id).asFlow().first()

            selectedPriority = habit.priority.getName()
            selectedType = habit.type.getName()
            name = habit.name
            description = habit.description
            count = if (habit.count > 0) habit.count.toString() else ""
            frequency = if (habit.frequency > 0) habit.frequency.toString() else ""
            uid = habit.uid
            doneDates = habit.doneDates
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveClicked(onClick: () -> Unit, id: Int) {
        val habit = HabitInfo.habitInit(
            selectedPriority = selectedPriority,
            selectedType = selectedType,
            name = name,
            description = description,
            count = count,
            frequency = frequency,
            uid = uid,
            id = id,
            doneDates = doneDates
        )

        addOrUpdate(habit)

        onClick()
    }

    private fun getHabitById(id: Int): LiveData<HabitInfo> {
        return habitRepository.loadById(id).asLiveData()
    }

    private fun addOrUpdate(habit: HabitInfo) {
        habitRepository.addOrUpdateHabit(habit)
    }
}
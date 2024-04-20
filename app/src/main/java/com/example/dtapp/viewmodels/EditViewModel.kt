package com.example.dtapp.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dtapp.App
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.Priority
import com.example.dtapp.models.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel : ViewModel() {
    var selectedPriority by mutableStateOf(Priority.MEDIUM.getName())
        private set
    var selectedType by mutableStateOf(Type.GOOD.getName())
        private set
    var name by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var times by mutableStateOf("")
        private set
    var period by mutableStateOf("")
        private set
    private var date: String = ""

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

    fun changeTimes(times: String) {
        this.times = times
    }

    fun changePeriod(period: String) {
        this.period = period
    }

    fun habitInit(id: Int) {
        viewModelScope.launch {
            val habit = getHabitById(id).asFlow().first()

            selectedPriority = habit.priority.getName()
            selectedType = habit.type.getName()
            name = habit.name
            description = habit.description
            times = habit.times
            period = habit.period
            date = habit.date
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveClicked(onClick: () -> Unit, id: Int) {
        val habit = HabitInfo.habitInit(
            selectedPriority = selectedPriority,
            selectedType = selectedType,
            name = name,
            description = description,
            times = times,
            period = period,
            date = date,
            id = id
        )

        addOrUpdate(id, habit)

        onClick()
    }

    private fun getHabitById(id: Int): LiveData<HabitInfo> {
        return App.instance.database.habitDao().loadById(id)
    }

    // withContext
    private fun addOrUpdate(id: Int, habit: HabitInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == HabitInfo.DEFAULT_ID) {
                App.instance.database.habitDao().insertAll(habit)
            } else {
                App.instance.database.habitDao().update(habit)
            }
        }
    }
}
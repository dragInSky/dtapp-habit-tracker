package com.example.dtapp.viewmodels

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
import com.example.dtapp.App
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.Priority
import com.example.dtapp.models.Type
import com.example.dtapp.net.Client
import com.example.dtapp.net.transport.HabitInfoConverter
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
    var count by mutableStateOf("")
        private set
    var frequency by mutableStateOf("")
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
            id = id
        )

        addOrUpdate(id, habit)

        onClick()
    }

    private fun getHabitById(id: Int): LiveData<HabitInfo> {
        return App.instance.database.habitDao().loadById(id).asLiveData()
    }

    private fun addOrUpdate(id: Int, habit: HabitInfo) {
        val converter = HabitInfoConverter()
        val transportHabit = converter.toTransport(habit)
        val client = Client()

        viewModelScope.launch(Dispatchers.IO) {
            if (id == HabitInfo.DEFAULT_ID) {
                App.instance.database.habitDao().insertAll(habit)
            } else {
                App.instance.database.habitDao().update(habit)
            }

            client.addOrUpdateHabit(transportHabit)
        }
    }
}
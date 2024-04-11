package com.example.dtapp.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dtapp.Model
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.Priority
import com.example.dtapp.models.Type

class EditViewModel : ViewModel() {
    var selectedPriority = mutableStateOf(Priority.MEDIUM.getName())
    var selectedType = mutableStateOf(Type.GOOD.getName() )
    var name = mutableStateOf("")
    var description = mutableStateOf("")
    var times = mutableStateOf("")
    var period = mutableStateOf("")

    fun habitInit(id: Int) {
        val habit = getHabitById(id)

        if (habit != null) {
            selectedPriority.value = habit.priority.getName()
            selectedType.value = habit.type.getName()
            name.value = habit.name
            description.value = habit.description
            times.value = habit.times
            period.value = habit.period
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveClicked(onCLick: () -> Unit, id: Int) {
        val habit = HabitInfo.habitInit(
            id = id,
            selectedPriority = selectedPriority.value,
            selectedType = selectedType.value,
            name = name.value,
            description = description.value,
            times = times.value,
            period = period.value
        )

        addOrUpdate(id, habit)

        onCLick()
    }

    private fun getHabitById(id: Int): HabitInfo? {
        return Model.habits.find { it.id == id }
    }

    private fun addOrUpdate(id: Int, habit: HabitInfo) {
        if (id == HabitInfo.DEFAULT_ID) {
            Model.habits.add(habit)
        } else {
            val habitToEdit = Model.habits.find { it.id == id }
            val idxToEdit = Model.habits.indexOf(habitToEdit)
            Model.habits[idxToEdit] = habit
        }
    }
}
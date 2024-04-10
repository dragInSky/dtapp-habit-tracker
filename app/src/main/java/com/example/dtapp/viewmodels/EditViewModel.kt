package com.example.dtapp.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.dtapp.Model
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.Priority
import com.example.dtapp.models.Type

class EditViewModel : ViewModel() {
    var selectedPriority = mutableStateOf(Priority.MEDIUM.text)
    var selectedType = mutableStateOf(Type.GOOD.text)
    var name = mutableStateOf("")
    var description = mutableStateOf("")
    var times = mutableStateOf("")
    var period = mutableStateOf("")

    fun habitInit(id: Int) {
        val habit = getHabitById(id)

        if (habit != null) {
            selectedPriority.value = habit.priority.text
            selectedType.value = habit.type.text
            name.value = habit.name
            description.value = habit.description
            times.value = habit.times
            period.value = habit.period
        }
    }

    fun onSaveClicked(context: Context, navController: NavController, id: Int) {
        val habit = HabitInfo.habitInit(
            context = context,
            id = id,
            selectedPriority = selectedPriority.value,
            selectedType = selectedType.value,
            name = name.value,
            description = description.value,
            times = times.value,
            period = period.value
        )

        addOrUpdate(id, habit)

        navController.popBackStack()
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
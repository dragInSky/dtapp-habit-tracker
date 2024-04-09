package com.example.dtapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dtapp.Model
import com.example.dtapp.models.HabitInfo

class EditViewModel : ViewModel() {
    fun addOrUpdate(id: Int, habit: HabitInfo) {
        if (id == HabitInfo.DEFAULT_ID) {
            Model.habits.add(habit)
        } else {
            val habitToEdit = Model.habits.find { it.id == id }
            val idxToEdit = Model.habits.indexOf(habitToEdit)
            Model.habits[idxToEdit] = habit
        }
    }
}
package com.example.dtapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dtapp.Model
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.models.Type

class HabitsViewModel : ViewModel() {
    fun getHabitById(id: Int): HabitInfo? {
        return Model.habits.find { it.id == id }
    }

    fun getPagedHabits(page: Int): List<HabitInfo> {
        return if (page == 0) getGoodHabits() else getBadHabits()
    }

    private fun getGoodHabits(): List<HabitInfo> {
        return Model.habits.filter { e -> e.type == Type.GOOD }
    }

    private fun getBadHabits(): List<HabitInfo> {
        return Model.habits.filter { e -> e.type == Type.BAD }
    }
}
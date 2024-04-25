package com.example.dtapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dtapp.models.HabitInfo

@Dao
interface HabitDao {
    @Query("SELECT * FROM habitInfo")
    fun getAll(): List<HabitInfo>

    @Query("SELECT * FROM habitInfo WHERE id = :habitId")
    fun loadById(habitId: Int): LiveData<HabitInfo>

    @Query("SELECT * FROM habitInfo WHERE type = :habitType")
    fun loadByType(habitType: Int): LiveData<List<HabitInfo>>

    @Insert
    suspend fun insertAll(vararg habits: HabitInfo)

    @Update
    suspend fun update(habit: HabitInfo)

    @Delete
    fun delete(habit: HabitInfo)
}
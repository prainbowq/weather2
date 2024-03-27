package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert
    fun insertData(weather: Weather)

    @Query("SELECT * FROM Weather")
    fun getAll(): List<Weather>
}
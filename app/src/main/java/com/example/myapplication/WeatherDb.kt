package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Weather::class],
    version = 1
)
abstract class WeatherDb : RoomDatabase() {
    abstract val dao: WeatherDao
}
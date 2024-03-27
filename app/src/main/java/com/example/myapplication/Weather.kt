package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Weather(
    @PrimaryKey
    val city: String,
    val maxTemp: String,
    val minTemp: String,
    val weatherTalk: String,
    val hourTemp: String,
    val cloud: String,
    val day: String
)
package com.example.capstoneapp.data

data class WorkoutPreference(
    val level: String?,
    val target: String?,
    val option: String?,
    val days: List<Int>?,
    val workoutIds: IntArray?
)

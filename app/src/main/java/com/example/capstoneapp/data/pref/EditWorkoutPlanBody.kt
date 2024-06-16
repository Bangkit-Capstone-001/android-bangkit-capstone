package com.example.capstoneapp.data.pref

data class EditWorkoutPlanBody(
    val days: List<Int>? = null,
    val workoutIds: List<String>? = null
)

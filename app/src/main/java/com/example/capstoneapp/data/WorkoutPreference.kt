package com.example.capstoneapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutPreference(
    val level: String?,
    val target: String?,
    val option: String?,
    val days: List<Int>?,
    val workoutIds: List<String>?
) : Parcelable

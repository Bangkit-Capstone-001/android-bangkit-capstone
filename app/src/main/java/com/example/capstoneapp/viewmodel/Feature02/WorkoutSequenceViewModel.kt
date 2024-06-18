package com.example.capstoneapp.viewmodel.Feature02

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutSequenceViewModel : ViewModel() {

    private var _workoutIndex = MutableLiveData<Int>()
    val workoutIndex: LiveData<Int> = _workoutIndex

    init {
        _workoutIndex.value = 0
    }

    fun incrementWorkoutIndex() {
        _workoutIndex.value = _workoutIndex.value?.plus(1)
    }
}
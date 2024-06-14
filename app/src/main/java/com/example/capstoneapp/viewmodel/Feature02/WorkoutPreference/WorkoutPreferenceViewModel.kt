package com.example.capstoneapp.viewmodel.Feature02.WorkoutPreference

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutPreferenceViewModel() : ViewModel() {

    private var _preferenceIndex = MutableLiveData<Int>()
    val preferenceIndex: LiveData<Int> = _preferenceIndex

    init {
        _preferenceIndex.value = 0
    }

    fun incrementPreferenceIndex() {
        _preferenceIndex.value = _preferenceIndex.value?.plus(1)
    }

    fun resetPreferenceIndex() {
        _preferenceIndex.value = 0
    }

}
package com.example.capstoneapp.viewmodel.Feature02.WorkoutValidation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.response.DataItem

class WorkoutValidationViewModel(private val repository: UserRepository) : ViewModel() {

    val _favoriteWorkouts = MutableLiveData<List<DataItem>>()
    var favoriteWorkouts: LiveData<List<DataItem>> = _favoriteWorkouts

    private val _recommendedWorkouts = MutableLiveData<List<DataItem>>()
    val recommendedWorkouts: LiveData<List<DataItem>> = _recommendedWorkouts
}
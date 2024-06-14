package com.example.capstoneapp.viewmodel.Feature02.WorkoutList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.WorkoutPreference
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.RandomPreferenceWorkoutResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutListViewModel(private val repository: UserRepository) : ViewModel() {

    private val _workouts = MutableLiveData<RandomPreferenceWorkoutResponse>()
    val workouts: LiveData<RandomPreferenceWorkoutResponse> = _workouts

    fun getRandomPreferenceWorkout(token: String, preference: WorkoutPreference) {
        val client = ApiConfig.getApiService().getRandomPreferenceWorkout(token, preference.level!!, preference.target!!, preference.option!!)
        try {
            client.enqueue(object : Callback<RandomPreferenceWorkoutResponse> {
                override fun onResponse(
                    call: Call<RandomPreferenceWorkoutResponse>,
                    response: Response<RandomPreferenceWorkoutResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _workouts.value = response.body()
                        } ?: run {
                            Log.e("WorkoutList E1", "No Value")
                        }
                    } else {
                        Log.e("Workout E2", "Message : " + response.message() + "\nStatus : " + response.code())
                    }
                }

                override fun onFailure(call: Call<RandomPreferenceWorkoutResponse>, t: Throwable) {
                    Log.e("WorkoutList E3", "onFailure")
                }

            })
        } catch (e: Exception) {
            Log.e("WorkoutList Exception", e.toString())
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}
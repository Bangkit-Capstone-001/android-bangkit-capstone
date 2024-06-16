package com.example.capstoneapp.viewmodel.Feature02.WorkoutValidation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.WorkoutPreference
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.DataItem
import com.example.capstoneapp.data.response.RandomPreferenceWorkoutResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutValidationViewModel(private val repository: UserRepository) : ViewModel() {

    val _favoriteWorkouts = MutableLiveData<List<DataItem>>()
    var favoriteWorkouts: LiveData<List<DataItem>> = _favoriteWorkouts

    private val _recommendedWorkouts = MutableLiveData<List<DataItem>>()
    val recommendedWorkouts: LiveData<List<DataItem>> = _recommendedWorkouts

    fun addFavoriteWorkouts(dataItem: DataItem) {
        val currentFavorites = _favoriteWorkouts.value?.toMutableList() ?: mutableListOf()

        currentFavorites.add(dataItem)

        _favoriteWorkouts.value = currentFavorites
    }

    fun removeFavoriteWorkouts(dataItem: DataItem) {
        val currentFavorites = _favoriteWorkouts.value?.toMutableList() ?: mutableListOf()

        currentFavorites.remove(dataItem)

        _favoriteWorkouts.value = currentFavorites
    }

    fun getRecommendedWorkouts(token: String, preference: WorkoutPreference) {
        val client = ApiConfig.getApiService().getRecommendedWorkouts(token, preference.target!!, preference.option!!)
        try {
            client.enqueue(object : Callback<RandomPreferenceWorkoutResponse> {
                override fun onResponse(
                    call: Call<RandomPreferenceWorkoutResponse>,
                    response: Response<RandomPreferenceWorkoutResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _recommendedWorkouts.value = (response.body()!!.data as List<DataItem>?)!!
                        } ?: run {
                            Log.e("WorkoutValidation E1", "No Value")
                        }
                    } else {
                        Log.e("WorkoutValidation E2", "Message : " + response.message() + "\nStatus : " + response.code())
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
package com.example.capstoneapp.viewmodel.Feature02.WorkoutPlanDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.DataItem
import com.example.capstoneapp.data.response.DeleteWorkoutPlanResponse
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.data.response.RandomPreferenceWorkoutResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutPlanDetailViewModel(private val repository: UserRepository) : ViewModel() {

    val _favoriteWorkouts = MutableLiveData<List<DataItem>>()
    var favoriteWorkouts: LiveData<List<DataItem>> = _favoriteWorkouts

    val _recommendedWorkouts = MutableLiveData<List<DataItem>>()
    var recommendedWorkouts: LiveData<List<DataItem>> = _recommendedWorkouts

    private val _deleteResponse = MutableLiveData<DeleteWorkoutPlanResponse>()
    val deleteResponse: LiveData<DeleteWorkoutPlanResponse> = _deleteResponse

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

    fun getRecommendedWorkout(token: String, workouts: GetDataItem) {
        val client = ApiConfig.getApiService().getRecommendedWorkouts(token, workouts.target!!, workouts.option!!)
        Log.d("Input", "Target : ${workouts.target!!}\nOption : ${workouts.option!!}" )
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
                            Log.e("WorkoutPlanDetail E1", "No Value")
                        }
                    } else {
                        Log.e("WorkoutPlanDetail E2", "Message : " + response.message() + "\nStatus : " + response.code() + "\nFull message : " + response.toString())
                    }
                }

                override fun onFailure(call: Call<RandomPreferenceWorkoutResponse>, t: Throwable) {
                    Log.e("WorkoutPlanDetail E3", "onFailure")
                }

            })
        } catch (e: Exception) {
            Log.e("WorkoutPlanDetail Exception", e.toString())
        }
    }

    fun deleteWorkoutPlan(token: String, workoutId: String) {
        val client = ApiConfig.getApiService().deleteWorkoutPlan(token, workoutId)
        try {
            client.enqueue(object : Callback<DeleteWorkoutPlanResponse> {
                override fun onResponse(
                    call: Call<DeleteWorkoutPlanResponse>,
                    response: Response<DeleteWorkoutPlanResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _deleteResponse.value = response.body()
                        } ?: run {
                            Log.e("Delete E1", "No Value")
                        }
                    } else {
                        Log.e("Delete E2", "Message : " + response.message() + "\nStatus : " + response.code() + "\nFull message : " + response.toString())
                    }
                }

                override fun onFailure(call: Call<DeleteWorkoutPlanResponse>, t: Throwable) {
                    Log.e("Delete E3", "onFailure")
                }

            })
        } catch (e: Exception) {
            Log.e("Delete Exception", e.toString())
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}
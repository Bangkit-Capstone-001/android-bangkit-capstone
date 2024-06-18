package com.example.capstoneapp.viewmodel.Feature02.WorkoutValidation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.WorkoutPreference
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.DataItem
import com.example.capstoneapp.data.response.PostWorkoutPlanResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutValidationViewModel(private val repository: UserRepository) : ViewModel() {

    val _favoriteWorkouts = MutableLiveData<List<DataItem>>()
    var favoriteWorkouts: LiveData<List<DataItem>> = _favoriteWorkouts

    private val _postWorkoutPlanResult = MutableLiveData<Result<PostWorkoutPlanResponse>>()
    val postWorkoutPlanResult: LiveData<Result<PostWorkoutPlanResponse>> = _postWorkoutPlanResult

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

    fun postWorkoutPlan(token: String, preference: WorkoutPreference) {
        val client = ApiConfig.getApiService().postWorkoutPlan(token, preference.workoutIds!!, preference.days!!, preference.level!!, preference.option!!, preference.target!!)
        try {
            client.enqueue(object : Callback<PostWorkoutPlanResponse> {
                override fun onResponse(
                    call: Call<PostWorkoutPlanResponse>,
                    response: Response<PostWorkoutPlanResponse>
                ) {
                    if (response.isSuccessful) {
                        _postWorkoutPlanResult.value = Result.success(response.body()!!)
                    } else {
                        _postWorkoutPlanResult.value = Result.failure(Throwable(response.message()))
                        Log.e("POST FAILED 1", "Failed code : ${response.code()}\nFailed message : ${response}")
                    }
                }

                override fun onFailure(call: Call<PostWorkoutPlanResponse>, t: Throwable) {
                    _postWorkoutPlanResult.value = Result.failure(t)
                    Log.e("POST FAILED 2", "Failed")
                }

            })
        } catch (e: Exception) {
            Log.e("EXCEPTION", e.message.toString())
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}
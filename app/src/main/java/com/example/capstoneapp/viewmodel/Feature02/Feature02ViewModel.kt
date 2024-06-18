package com.example.capstoneapp.viewmodel.Feature02

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.data.response.GetWorkoutPlanResponse
import com.example.capstoneapp.data.response.PostWorkoutPlanResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Feature02ViewModel(private val repository: UserRepository) : ViewModel() {

    private val _response = MutableLiveData<GetWorkoutPlanResponse>()
    val response: LiveData<GetWorkoutPlanResponse> = _response

    private val _workoutPlans = MutableLiveData<List<GetDataItem>>()
    val workoutPlans: LiveData<List<GetDataItem>> = _workoutPlans

    fun getWorkoutPlans(token: String) {
        val client = ApiConfig.getApiService().getWorkoutPlan(token)
        try {
            client.enqueue(object : Callback<GetWorkoutPlanResponse> {
                override fun onResponse(
                    call: Call<GetWorkoutPlanResponse>,
                    response: Response<GetWorkoutPlanResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _response.value = response.body()
                            _workoutPlans.value = (_response.value!!.data as List<GetDataItem>?)
                        } ?: run {
                            Log.e("Get Plan E1", "No Value")
                        }
                    } else {
                        Log.e("Get Plan E2", "Message : " + response.message() + "\nStatus : " + response.code())
                    }
                }

                override fun onFailure(call: Call<GetWorkoutPlanResponse>, t: Throwable) {
                    Log.e("Get Plan E3", "onFailure")
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
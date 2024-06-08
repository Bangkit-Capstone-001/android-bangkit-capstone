package com.example.capstoneapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.DataFood
import com.example.capstoneapp.data.response.GetDietPlanResponse
import com.example.capstoneapp.data.response.GetFoodResponse
import com.example.capstoneapp.data.response.GetProfileResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    /**
     * userError is used to track API response (get profile)
     * planError is used to track API response (get plan)
     * foodError is used to track API response (get random food)
     */
    // LiveData Variables
    private val _userProfile = MutableLiveData<GetProfileResponse>()
    val userProfile: LiveData<GetProfileResponse> get() = _userProfile
    private val _userPlan = MutableLiveData<GetDietPlanResponse>()
    val userPlan: LiveData<GetDietPlanResponse> get() = _userPlan
    private val _randomFood = MutableLiveData<GetFoodResponse>()
    val randomFood: LiveData<GetFoodResponse> get() = _randomFood

    private val _userMsg = MutableLiveData<String>()
    val userMsg: LiveData<String> get() = _userMsg
    private val _userError = MutableLiveData<Boolean>()
    val userError: LiveData<Boolean> get() = _userError
    private val _planError = MutableLiveData<Boolean>()
    val planError: LiveData<Boolean> get() = _planError
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProfile(token: String) {
        _isLoading.value = true
        _userError.value = false
        Log.e("Token", "$token")    // get token
        val client = ApiConfig.getApiService().getProfile(token)
        client.enqueue(object : Callback<GetProfileResponse> {
            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        _userProfile.value = (response.body())
                        _userError.value = false
                        _userMsg.value = responseBody.message ?: "Success"
                    } ?: run {
                        _userError.value = true
                        _userMsg.value = "Null response"
                        Log.e(TAG, "Null response")
                    }
                } else {
                    _userMsg.value = response.message()
                    _userError.value = true
                    Log.e(
                        TAG,
                        "Bad request: ${response.code()} - ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _userMsg.value = t.message ?: "Unknown error"
                Log.e(TAG, "onFailure: ${_userMsg.value}")
            }
        })
    }

    fun getDietPlan(token: String) {
        _isLoading.value = true
        _planError.value = false
        val client = ApiConfig.getApiService().getDietPlan(token)
        client.enqueue(object : Callback<GetDietPlanResponse> {
            override fun onResponse(
                call: Call<GetDietPlanResponse>,
                response: Response<GetDietPlanResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _userPlan.value = (response.body())
                        _planError.value = false
                    } ?: run {
                        _planError.value = true
                        Log.e(TAG, "Get Plan | Null response")
                    }
                } else {
                    _planError.value = true
                    Log.e(
                        TAG,
                        "Get Plan | Bad request: ${response.code()} - ${
                            response.errorBody()?.string()
                        }"
                    )
                }
            }

            override fun onFailure(call: Call<GetDietPlanResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Get Plan | onFailure")
            }
        })
    }

    fun getRandomFood(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRandomizedFoods(token, 5)
        client.enqueue(object : Callback<GetFoodResponse> {
            override fun onResponse(
                call: Call<GetFoodResponse>,
                response: Response<GetFoodResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _randomFood.value = (response.body())
                    } ?: run {
                        Log.e(TAG, "Random Food | Null response")
                    }
                } else {
                    _planError.value = true
                    Log.e(
                        TAG,
                        "Random Food | Bad request: ${response.code()} - ${
                            response.errorBody()?.string()
                        }"
                    )
                }
            }

            override fun onFailure(call: Call<GetFoodResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Random Food | onFailure")
            }
        })
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
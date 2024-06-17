package com.example.capstoneapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.AddDietPlanResponse
import com.example.capstoneapp.data.response.AddWeightResponse
import com.example.capstoneapp.data.response.EditProfileResponse
import com.example.capstoneapp.data.response.GetFoodResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    /**
     * this view model implements 3 APIs: edit profile, add diet plan. track weight
     *
     * message returns general message for this activity
     * isError is used for tracking API (edit profile)
     * addPlanError is used for tracking API (add diet plan)
     * addWeightError is used for tracking API (add weight)
     */
    // LiveData Variables
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError
    private val _addPlanError = MutableLiveData<Boolean>()
    val addPlanError: LiveData<Boolean> get() = _addPlanError
    private val _addWeightError = MutableLiveData<Boolean>()
    val addWeightError: LiveData<Boolean> get() = _addWeightError

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun editProfile(
        token: String,
        name: String,
        age: Int,
        gender: String,
        currentHeight: Float,
        currentWeight: Float,
        goal: String,
        activityLevel: String
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().editProfile(
            token,
            name,
            age,
            gender,
            currentHeight,
            currentWeight,
            goal,
            activityLevel
        )
        client.enqueue(object : Callback<EditProfileResponse> {
            override fun onResponse(
                call: Call<EditProfileResponse>,
                response: Response<EditProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        _isError.value = false
                        _message.value = responseBody.message ?: "Success"
                    } ?: run {
                        _isError.value = true
                        _message.value = "Null response"
                        Log.e(TAG, "Null response")
                    }
                } else {
                    _isError.value = true
                    Log.e(TAG, "Bad request: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message ?: "Unknown error"
                Log.e(TAG, "onFailure: ${_message.value}")
            }
        })
    }

    fun addDietPlan(token: String, weightTarget: Float, duration: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().addDietPlan(token, weightTarget, duration)
        client.enqueue(object : Callback<AddDietPlanResponse> {
            override fun onResponse(
                call: Call<AddDietPlanResponse>,
                response: Response<AddDietPlanResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        _addPlanError.value = false
                        // _message.value = responseBody.message ?: "Success"
                    } ?: run {
                        _addPlanError.value = true
                        Log.e(TAG, "Add diet plan | Null response")
                    }
                } else {
                    _addPlanError.value = true
                    Log.e(TAG, "Add diet plan | Bad request: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AddDietPlanResponse>, t: Throwable) {
                _isLoading.value = false
                _addPlanError.value = true
                Log.e(TAG, "Add diet plan | onFailure: Unknown error")
            }
        })
    }

    fun addWeight(token: String, date: String, weight: Float) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().addWeight(token, date, weight)
        client.enqueue(object : Callback<AddWeightResponse> {
            override fun onResponse(
                call: Call<AddWeightResponse>,
                response: Response<AddWeightResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        _addWeightError.value = false
                        // _message.value = responseBody.message ?: "Success"
                    } ?: run {
                        _addWeightError.value = true
                        Log.e(TAG, "Add weight | Null response")
                    }
                } else {
                    _addWeightError.value = true
                    Log.e(TAG, "Add weight | Bad request: ${parseWeightError(response)}")
                    Log.e(TAG, "Date ${date}")
                }
            }

            override fun onFailure(call: Call<AddWeightResponse>, t: Throwable) {
                _isLoading.value = false
                _addWeightError.value = true
                Log.e(TAG, "Add weight | onFailure: Unknown error")
            }
        })
    }

    private fun parseWeightError(response: Response<AddWeightResponse>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            JSONObject(errorBody ?: "").getString("message")
        } catch (e: Exception) {
            response.message()
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}
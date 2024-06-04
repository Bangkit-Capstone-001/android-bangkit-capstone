package com.example.capstoneapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.EditProfileResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    // LiveData Variables
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError
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

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}
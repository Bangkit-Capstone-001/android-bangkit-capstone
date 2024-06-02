package com.example.capstoneapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.GetProfileResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    // LiveData Variables
    private val _userProfile = MutableLiveData<GetProfileResponse>()
    val userProfile: LiveData<GetProfileResponse> get() = _userProfile
    private val _userMsg = MutableLiveData<String>()
    val userMsg: LiveData<String> get() = _userMsg
    private val _userError = MutableLiveData<Boolean>()
    val userError: LiveData<Boolean> get() = _userError
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProfile(token: String) {
        _isLoading.value = true
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
                    _userError.value = true
                    Log.e(TAG, "Bad request")
                }
            }

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _userMsg.value = t.message ?: "Unknown error"
                Log.e(TAG, "onFailure: ${_userMsg.value}")
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
package com.example.capstoneapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.response.RegisterResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SignupViewModel(private val repository: UserRepository) : ViewModel() {
    // LiveData Variables
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun handleSignup(email: String, pass: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(email, pass)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
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
                    _message.value = parseError(response)
                    Log.e(TAG, "Bad request: ${_message.value}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message ?: "Unknown error"
                Log.e(TAG, "onFailure: ${_message.value}")
            }
        })
    }

    private fun parseError(response: Response<RegisterResponse>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            JSONObject(errorBody ?: "").getString("message")
        } catch (e: Exception) {
            response.message()
        }
    }

    companion object {
        private const val TAG = "SignupViewModel"
    }
}
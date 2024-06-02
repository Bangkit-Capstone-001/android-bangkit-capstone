package com.example.capstoneapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.LoginResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    // LiveData Variables
    private val _resp = MutableLiveData<LoginResponse>()
    val resp: LiveData<LoginResponse> get() = _resp
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun handleLogin(name: String, pass: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(name, pass)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        if (responseBody.status != 200) {
                            _isError.value = true
                            _message.value = responseBody.message ?: "Error"
                            Log.e(TAG, "${_message.value}")
                        } else {
                            _isError.value = false
                            _resp.value = responseBody
                            _message.value = responseBody.message ?: "Success"
                        }
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

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _message.value = t.message ?: "Unknown error"
                Log.e(TAG, "onFailure: ${_message.value}")
            }
        })
    }

    private fun parseError(response: Response<LoginResponse>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            JSONObject(errorBody ?: "").getString("message")
        } catch (e: Exception) {
            response.message()
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
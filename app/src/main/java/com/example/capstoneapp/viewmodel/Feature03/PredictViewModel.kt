package com.example.capstoneapp.viewmodel.Feature03

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.DataFood
import com.example.capstoneapp.data.response.GetFoodResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictViewModel(private val repository: UserRepository) : ViewModel() {
    // LiveData Variables
    private val _predictRes = MutableLiveData<List<DataFood>>()
    val predictRes: LiveData<List<DataFood>> get() = _predictRes

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun predictImage(token: String, file: MultipartBody.Part) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().predictImage(token, file)
        client.enqueue(object : Callback<GetFoodResponse> {
            override fun onResponse(
                call: Call<GetFoodResponse>,
                response: Response<GetFoodResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        _predictRes.value = (responseBody.data as List<DataFood>?)
                        _message.value = responseBody.message ?: "Success"
                        _isError.value = false
                    } ?: run {
                        _message.value = "Null response"
                        _isError.value = true
                    }
                } else {
                    _message.value = parseError(response)
                    _isError.value = true
                    Log.e(TAG, "Bad request: ${_message.value}")
                }
            }

            override fun onFailure(call: Call<GetFoodResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${_message.value}")
                _isLoading.value = false
                _message.value = t.message ?: "Unknown error"
                _isError.value = true
            }
        })
    }

    private fun parseError(response: Response<GetFoodResponse>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            JSONObject(errorBody ?: "").getString("message")
        } catch (e: Exception) {
            response.message()
        }
    }

    companion object {
        private const val TAG = "PredictViewModel"
    }
}
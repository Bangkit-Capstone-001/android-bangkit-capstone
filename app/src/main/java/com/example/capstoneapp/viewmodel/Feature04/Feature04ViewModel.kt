package com.example.capstoneapp.viewmodel.Feature04

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.GetTrackerResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Feature04ViewModel(private val repository: UserRepository) : ViewModel() {

    private val _getTrackerResponse = MutableLiveData<GetTrackerResponse>()
    val getTrackerResponse: LiveData<GetTrackerResponse> = _getTrackerResponse

    fun getTrackerData(token: String) {
        val client = ApiConfig.getApiService().getTrackerData(token)
        try {
            client.enqueue(object : Callback<GetTrackerResponse> {
                override fun onResponse(
                    call: Call<GetTrackerResponse>,
                    response: Response<GetTrackerResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { responseBody ->
                            _getTrackerResponse.value = responseBody
                        } ?: run {
                            Log.e("GetTrackerData E1", "Null Response")
                        }
                    } else {
                        Log.e("GetTrackerData E2", "Bad request: ${response.code()} - ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<GetTrackerResponse>, t: Throwable) {
                    Log.e("GetTrackerData E3", "onFailure: " + t.toString())
                }

            })
        } catch (e: Exception) {
            Log.e("GetTrackerData E", e.message.toString())
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}
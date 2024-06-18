package com.example.capstoneapp.viewmodel.Feature04

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.GetTrackerResponse
import com.example.capstoneapp.data.response.PostTrackerResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Feature04ViewModel(private val repository: UserRepository) : ViewModel() {

    private val _getTrackerResponse = MutableLiveData<GetTrackerResponse>()
    val getTrackerResponse: LiveData<GetTrackerResponse> = _getTrackerResponse

    val _postTrackerResponse = MutableLiveData<PostTrackerResponse>()
    val postTrackerResponse: LiveData<PostTrackerResponse> = _postTrackerResponse

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
                        Log.e(
                            "GetTrackerData E2",
                            "Bad request: ${response.code()} - ${response.errorBody()?.string()}"
                        )
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

    fun postTracker(token: String, date: String, weight: Float) {
        val client = ApiConfig.getApiService().postTrackerData(token, date, weight)
        try {
            client.enqueue(object : Callback<PostTrackerResponse> {
                override fun onResponse(
                    call: Call<PostTrackerResponse>,
                    response: Response<PostTrackerResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { responseBody ->
                            _postTrackerResponse.value = responseBody
                        } ?: run {
                            Log.e("PostTrackerData E1", "Null Response")
                        }
                    } else {
                        val message = parseChartError(response)
                        Log.e("PostTrackerData E2", "Bad request: ${message}")
                        handleError(message)
                    }
                }

                override fun onFailure(call: Call<PostTrackerResponse>, t: Throwable) {
                    Log.e("PostTrackerData E3", "onFailure: " + t.toString())
                }
            })
        } catch (e: Exception) {
            Log.e("PostTrackerData E", e.message.toString())
        }
    }

    private fun parseChartError(response: Response<PostTrackerResponse>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            JSONObject(errorBody ?: "").getString("message")
        } catch (e: Exception) {
            response.message()
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    private fun handleError(message: String) {
        _postTrackerResponse.postValue(PostTrackerResponse(status = 500, message = message))
    }
}
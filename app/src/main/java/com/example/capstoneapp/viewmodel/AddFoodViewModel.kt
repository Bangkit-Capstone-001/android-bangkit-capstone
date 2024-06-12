package com.example.capstoneapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.response.AddDietPlanResponse
import com.example.capstoneapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFoodViewModel(private val repository: UserRepository) : ViewModel() {
    private val _addFoodError = MutableLiveData<Boolean>()
    val addFoodError: LiveData<Boolean> get() = _addFoodError
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun addFood(token: String, foodId: String, quantity: Float, mealtime: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().addFood(token, foodId, quantity, mealtime)
        client.enqueue(object : Callback<AddDietPlanResponse> {
            override fun onResponse(
                call: Call<AddDietPlanResponse>,
                response: Response<AddDietPlanResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        _addFoodError.value = false
                    } ?: run {
                        _addFoodError.value = true
                        Log.e(TAG, "Add food | Null response")
                    }
                } else {
                    _addFoodError.value = true
                    Log.e(TAG, "Add food | Bad request: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AddDietPlanResponse>, t: Throwable) {
                _isLoading.value = false
                _addFoodError.value = true
                Log.e(TAG, "Add food | onFailure: Unknown error")
            }
        })
    }

    companion object {
        private const val TAG = "AddViewModel"
    }
}
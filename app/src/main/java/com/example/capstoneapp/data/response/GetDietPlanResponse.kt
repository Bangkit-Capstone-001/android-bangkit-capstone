package com.example.capstoneapp.data.response

import com.google.gson.annotations.SerializedName

data class GetDietPlanResponse(

	@field:SerializedName("data")
	val data: DataPlan? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataPlan(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("calorie")
	val calorie: Float? = null,

	@field:SerializedName("remainingCalories")
	val remainingCalories: Float? = null,

	@field:SerializedName("calorieEaten")
	val calorieEaten: Float? = null,

	@field:SerializedName("weightTarget")
	val weightTarget: Int? = null
)

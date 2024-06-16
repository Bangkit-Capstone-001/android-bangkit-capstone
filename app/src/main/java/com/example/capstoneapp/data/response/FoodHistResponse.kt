package com.example.capstoneapp.data.response

import com.google.gson.annotations.SerializedName

data class FoodHistResponse(

	@field:SerializedName("data")
	val data: List<DataFoodHist?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataFoodHist(

	@field:SerializedName("date")
	val date: Long? = null,

	@field:SerializedName("mealtime")
	val mealtime: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("calories")
	val calories: Any? = null,

	@field:SerializedName("food")
	val food: DataFood? = null
)

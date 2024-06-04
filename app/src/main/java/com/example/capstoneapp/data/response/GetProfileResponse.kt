package com.example.capstoneapp.data.response

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Data(

	@field:SerializedName("goal")
	val goal: String? = null,

	@field:SerializedName("currentWeight")
	val currentWeight: Any? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("currentHeight")
	val currentHeight: Int? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("activityLevel")
	val activityLevel: String? = null,

	@field:SerializedName("bmi")
	val bmi: Any? = null
)

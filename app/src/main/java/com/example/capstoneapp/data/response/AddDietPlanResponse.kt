package com.example.capstoneapp.data.response

import com.google.gson.annotations.SerializedName

/**
 * Actually, yhe response contains data: weightTarget, duration, calorie
 * but we do not use that
 *
 * This response is also used as: AddFoodResponse
 */
data class AddDietPlanResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

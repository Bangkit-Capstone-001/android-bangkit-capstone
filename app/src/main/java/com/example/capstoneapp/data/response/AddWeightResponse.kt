package com.example.capstoneapp.data.response

import com.google.gson.annotations.SerializedName

data class AddWeightResponse(

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

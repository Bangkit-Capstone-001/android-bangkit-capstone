package com.example.capstoneapp.data.response

import com.google.gson.annotations.SerializedName

data class AddWeightResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("weight")
	val weight: Double? = null
)

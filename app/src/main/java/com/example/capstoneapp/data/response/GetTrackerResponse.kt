package com.example.capstoneapp.data.response

import com.google.gson.annotations.SerializedName

data class GetTrackerResponse(

	@field:SerializedName("data")
	val data: List<GetTrackerDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class GetTrackerDataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("weight")
	val weight: Float? = null
)

package com.example.capstoneapp.data.response

import com.google.gson.annotations.SerializedName

data class PostWorkoutPlanResponse(

	@field:SerializedName("data")
	val data: DataPostWorkoutPlan? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class WorkoutsItem(

	@field:SerializedName("short_description")
	val shortDescription: String? = null,

	@field:SerializedName("instructions")
	val instructions: String? = null,

	@field:SerializedName("rating")
	val rating: Float? = null,

	@field:SerializedName("equipment")
	val equipment: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("guide_img_url")
	val guideImgUrl: String? = null,

	@field:SerializedName("body_group")
	val bodyGroup: String? = null,

	@field:SerializedName("youtube_title")
	val youtubeTitle: String? = null,

	@field:SerializedName("youtube_links")
	val youtubeLinks: String? = null,

	@field:SerializedName("exercise_name")
	val exerciseName: String? = null,

	@field:SerializedName("option")
	val option: String? = null
)

data class DataPostWorkoutPlan(

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("workouts")
	val workouts: List<WorkoutsItem?>? = null,

	@field:SerializedName("days")
	val days: List<Int?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("option")
	val option: String? = null,

	@field:SerializedName("target")
	val target: String? = null
)

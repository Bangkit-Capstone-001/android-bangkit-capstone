package com.example.capstoneapp.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetWorkoutPlanResponse(

	@field:SerializedName("data")
	val data: List<GetDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class GetWorkoutsItem(

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

	@field:SerializedName("youtube_links")
	val youtubeLinks: String? = null,

	@field:SerializedName("youtube_title")
	val youtubeTitle: String? = null,

	@field:SerializedName("exerciseImages")
	val exerciseImages: List<String?>? = null,

	@field:SerializedName("exercise_name")
	val exerciseName: String? = null,

	@field:SerializedName("option")
	val option: String? = null
) : Parcelable

@Parcelize
data class GetDataItem(

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("workouts")
	val workouts: List<GetWorkoutsItem?>? = null,

	@field:SerializedName("days")
	val days: List<Int?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("option")
	val option: String? = null,

	@field:SerializedName("target")
	val target: String? = null
) : Parcelable

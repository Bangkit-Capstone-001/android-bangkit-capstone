package com.example.capstoneapp.data.retrofit

import com.example.capstoneapp.data.pref.EditWorkoutPlanBody
import com.example.capstoneapp.data.response.AddDietPlanResponse
import com.example.capstoneapp.data.response.AddWeightResponse
import com.example.capstoneapp.data.response.DeleteWorkoutPlanResponse
import com.example.capstoneapp.data.response.EditProfileResponse
import com.example.capstoneapp.data.response.FoodHistResponse
import com.example.capstoneapp.data.response.GetDietPlanResponse
import com.example.capstoneapp.data.response.GetFoodResponse
import com.example.capstoneapp.data.response.GetProfileResponse
import com.example.capstoneapp.data.response.GetTrackerResponse
import com.example.capstoneapp.data.response.GetWorkoutPlanResponse
import com.example.capstoneapp.data.response.LoginResponse
import com.example.capstoneapp.data.response.PostTrackerResponse
import com.example.capstoneapp.data.response.PostWorkoutPlanResponse
import com.example.capstoneapp.data.response.RandomPreferenceWorkoutResponse
import com.example.capstoneapp.data.response.RegisterResponse
import com.example.capstoneapp.data.response.UpdateWorkoutPlanResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * ---------------------------------- Profile & Auth
     */
    @FormUrlEncoded
    @POST("api/auth/register")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api/auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("api/auth/logout")
    fun logout(
        @Header("Authorization") token: String
    ): Call<EditProfileResponse>

    @GET("/api/user-profile")
    fun getProfile(
        @Header("Authorization") token: String
    ): Call<GetProfileResponse>

    @FormUrlEncoded
    @PUT("api/user-profile")
    fun editProfile(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("age") age: Int,
        @Field("gender") gender: String,
        @Field("currentHeight") currentHeight: Float,
        @Field("currentWeight") currentWeight: Float,
        @Field("goal") goal: String,
        @Field("activityLevel") activityLevel: String
    ): Call<EditProfileResponse>

    /**
     * ---------------------------------- Diet Plan (calories)
     */

    @FormUrlEncoded
    @POST("api/diet-plan")
    fun addDietPlan(
        @Header("Authorization") token: String,
        @Field("weightTarget") weightTarget: Float,
        @Field("duration") duration: Int
    ): Call<AddDietPlanResponse>

    @GET("/api/diet-plan")
    fun getDietPlan(
        @Header("Authorization") token: String
    ): Call<GetDietPlanResponse>

    /**
     * ---------------------------------- Food (retrieve food, add history, etc)
     */

    @GET("/api/food-analysis/foods")
    fun getRandomizedFoods(
        @Header("Authorization") token: String,
        @Query("randomize") num: Int
    ): Call<GetFoodResponse>

    @GET("/api/food-analysis/foods/all")
    fun getAllFoods(
        @Header("Authorization") token: String
    ): Call<GetFoodResponse>

    @FormUrlEncoded
    @POST("/api/food-analysis")
    fun addFood(
        @Header("Authorization") token: String,
        @Field("foodId") foodId: String,
        @Field("quantity") quantity: Float,
        @Field("mealtime") mealtime: String,
    ): Call<AddDietPlanResponse>

    @GET("/api/food-analysis/today")
    fun getTodaysFood(
        @Header("Authorization") token: String,
        @Query("mealtime") mealtime: String
    ): Call<FoodHistResponse>

    /**
     * ---------------------------------- Workouts (retrieve workouts)
     */

    @GET("/api/workout/random")
    fun getRandomPreferenceWorkout(
        @Header("Authorization") token: String,
        @Query("level") level: String,
        @Query("target") target: String,
        @Query("option") option: String
    ): Call<RandomPreferenceWorkoutResponse>

    @GET("/api/workout/recommendations")
    fun getRecommendedWorkouts(
        @Header("Authorization") token: String,
        @Query("target") target: String,
        @Query("option") option: String
    ): Call<RandomPreferenceWorkoutResponse>

    @FormUrlEncoded
    @POST("/api/workout/plan")
    fun postWorkoutPlan(
        @Header("Authorization") token: String,
        @Field("workoutIds") workoutIds: List<String>,
        @Field("days") days: List<Int>,
        @Field("level") level: String,
        @Field("option") option: String,
        @Field("target") target: String
    ): Call<PostWorkoutPlanResponse>

    @GET("/api/workout/plan")
    fun getWorkoutPlan(
        @Header("Authorization") token: String
    ): Call<GetWorkoutPlanResponse>

    @DELETE("/api/workout/plan/{id}")
    fun deleteWorkoutPlan(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<DeleteWorkoutPlanResponse>

    @PUT("/api/workout/plan/{id}")
    fun updateWorkoutPlan(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body editWorkoutPlanBody: EditWorkoutPlanBody
    ): Call<UpdateWorkoutPlanResponse>

    @Multipart
    @POST("/api/food-analysis/picture")
    fun predictImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Call<GetFoodResponse>

    /**
     * ---------------------------------- Tracker (Get and Post weight data)
     */

    @GET("/api/tracker")
    fun getTrackerData(
        @Header("Authorization") token: String
    ): Call<GetTrackerResponse>

    @FormUrlEncoded
    @POST("/api/tracker")
    fun postTrackerData(
        @Header("Authorization") token: String,
        @Field("date") date: String,
        @Field("weight") weight: Float
    ): Call<PostTrackerResponse>

    @FormUrlEncoded
    @POST("api/tracker")
    fun addWeight(
        @Header("Authorization") token: String,
        @Field("date") date: String,
        @Field("weight") weight: Float,
    ): Call<AddWeightResponse>
}
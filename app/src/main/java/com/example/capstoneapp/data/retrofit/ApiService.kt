package com.example.capstoneapp.data.retrofit

import com.example.capstoneapp.data.response.AddDietPlanResponse
import com.example.capstoneapp.data.response.AddWeightResponse
import com.example.capstoneapp.data.response.EditProfileResponse
import com.example.capstoneapp.data.response.FoodHistResponse
import com.example.capstoneapp.data.response.GetDietPlanResponse
import com.example.capstoneapp.data.response.GetFoodResponse
import com.example.capstoneapp.data.response.GetProfileResponse
import com.example.capstoneapp.data.response.LoginResponse
import com.example.capstoneapp.data.response.PostWorkoutPlanResponse
import com.example.capstoneapp.data.response.RandomPreferenceWorkoutResponse
import com.example.capstoneapp.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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
     * ---------------------------------- Tracker (track weight)
     */

    @FormUrlEncoded
    @POST("api/tracker")
    fun addWeight(
        @Header("Authorization") token: String,
        @Field("date") date: String,
        @Field("weight") weight: Float,
    ): Call<AddWeightResponse>

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
}
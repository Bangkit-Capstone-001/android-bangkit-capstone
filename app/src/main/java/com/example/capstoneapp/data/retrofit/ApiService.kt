package com.example.capstoneapp.data.retrofit

import com.example.capstoneapp.data.response.EditProfileResponse
import com.example.capstoneapp.data.response.GetProfileResponse
import com.example.capstoneapp.data.response.LoginResponse
import com.example.capstoneapp.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
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
}
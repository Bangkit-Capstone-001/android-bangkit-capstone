package com.example.capstoneapp.di

import android.content.Context
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.data.pref.UserPreference
import com.example.capstoneapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}
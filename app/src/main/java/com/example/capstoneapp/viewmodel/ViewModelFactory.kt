package com.example.capstoneapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneapp.data.UserRepository
import com.example.capstoneapp.di.Injection
import com.example.capstoneapp.ui.Feature02.WorkoutPlanDetail.WorkoutPlanDetailActivity
import com.example.capstoneapp.viewmodel.Feature02.Feature02ViewModel
import com.example.capstoneapp.viewmodel.Feature02.WorkoutList.WorkoutListViewModel
import com.example.capstoneapp.viewmodel.Feature02.WorkoutPlanDetail.WorkoutPlanDetailViewModel
import com.example.capstoneapp.viewmodel.Feature02.WorkoutValidation.WorkoutValidationViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PredictViewModel::class.java) -> {
                PredictViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddFoodViewModel::class.java) -> {
                AddFoodViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WorkoutListViewModel::class.java) -> {
                WorkoutListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WorkoutValidationViewModel::class.java) -> {
                WorkoutValidationViewModel(repository) as T
            }
            modelClass.isAssignableFrom(Feature02ViewModel::class.java) -> {
                Feature02ViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WorkoutPlanDetailViewModel::class.java) -> {
                WorkoutPlanDetailViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
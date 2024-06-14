package com.example.capstoneapp.ui.Feature02.WorkoutList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneapp.R
import com.example.capstoneapp.data.WorkoutPreference
import com.example.capstoneapp.databinding.ActivityWorkoutListBinding
import com.example.capstoneapp.ui.WelcomeActivity
import com.example.capstoneapp.viewmodel.Feature02.WorkoutList.WorkoutListViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class WorkoutListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutListBinding
    private val viewModel by viewModels<WorkoutListViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preference = intent.getParcelableExtra(KEY_PREFERENCE) as WorkoutPreference?
        Log.d("FROM WORKOUT LIST", preference.toString())

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                val token = "Bearer ${user.token}"
                viewModel.getRandomPreferenceWorkout(token, preference!!)
            }
        }

        viewModel.workouts.observe(this) { workouts ->
            Log.d("WORKOUTS", workouts.toString())
        }

        setAction()
    }

    private fun setAction() {
        binding.workoutListIvBackButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val KEY_PREFERENCE = "key-preference"
    }
}
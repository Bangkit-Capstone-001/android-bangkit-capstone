package com.example.capstoneapp.ui.Feature02.WorkoutPreference

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivityWorkoutPreferenceBinding

class WorkoutPreferenceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAction()
    }

    private fun setAction() {
        binding.workoutPreferenceIvBackButton.setOnClickListener {
            finish()
        }
    }
}
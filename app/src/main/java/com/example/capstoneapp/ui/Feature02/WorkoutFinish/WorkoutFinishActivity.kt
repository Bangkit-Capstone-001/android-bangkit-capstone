package com.example.capstoneapp.ui.Feature02.WorkoutFinish

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivityWorkoutFinishBinding

class WorkoutFinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
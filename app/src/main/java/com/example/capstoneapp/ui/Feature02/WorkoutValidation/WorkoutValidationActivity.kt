package com.example.capstoneapp.ui.Feature02.WorkoutValidation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneapp.R
import com.example.capstoneapp.data.WorkoutPreference
import com.example.capstoneapp.databinding.ActivityWorkoutValidationBinding
import com.example.capstoneapp.ui.Feature02.WorkoutList.WorkoutListActivity

class WorkoutValidationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutValidationBinding
    private var preference: WorkoutPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutValidationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = intent.getParcelableExtra(WorkoutListActivity.KEY_PREFERENCE) as WorkoutPreference?
        Log.d("FROM WORKOUT VALIDATION", preference.toString())

        setAction()
    }

    private fun setAction() {
        binding.workoutValidationIvBackButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val KEY_PREFERENCE = "key-preference"
    }
}
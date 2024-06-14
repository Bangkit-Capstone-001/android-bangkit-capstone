package com.example.capstoneapp.ui.Feature02.WorkoutList

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneapp.R
import com.example.capstoneapp.data.WorkoutPreference
import com.example.capstoneapp.databinding.ActivityWorkoutListBinding

class WorkoutListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preference = intent.getParcelableExtra(KEY_PREFERENCE) as WorkoutPreference?
        Log.d("FROM WORKOUT LIST", preference.toString())

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
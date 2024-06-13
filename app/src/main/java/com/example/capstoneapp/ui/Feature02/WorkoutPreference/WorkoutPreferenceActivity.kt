package com.example.capstoneapp.ui.Feature02.WorkoutPreference

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneapp.R
import com.example.capstoneapp.data.WorkoutPreference
import com.example.capstoneapp.databinding.ActivityWorkoutPreferenceBinding
import com.example.capstoneapp.viewmodel.Feature02.WorkoutPreference.WorkoutPreferenceViewModel

class WorkoutPreferenceActivity : AppCompatActivity(), WorkoutLevelFragment.OnValueTransferListener {

    private lateinit var binding: ActivityWorkoutPreferenceBinding
    private lateinit var viewModel: WorkoutPreferenceViewModel
    private lateinit var preference: WorkoutPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WorkoutPreferenceViewModel::class.java]

        viewModel.preferenceIndex.observe(this) { index ->
            Log.d("preferenceIndex", index.toString())
            setPreferenceSelection(index)
        }

        setAction()
    }

    private fun setAction() {
        binding.workoutPreferenceIvBackButton.setOnClickListener {
            finish()
        }
    }

    private fun setPreferenceSelection(preferenceIndex: Int) {
        val fragment = when (preferenceIndex) {
            0 -> WorkoutLevelFragment()
            else -> null
        }

        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.workoutPreferenceClFragmentPlaceholder.id, fragment)
                .commit()
        }
    }

    override fun onValueTransfer(value: String) {
        Log.d("STRING RECEIVED", value)
    }
}
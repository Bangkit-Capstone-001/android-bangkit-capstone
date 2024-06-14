package com.example.capstoneapp.ui.Feature02.WorkoutPreference

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneapp.R
import com.example.capstoneapp.data.WorkoutPreference
import com.example.capstoneapp.databinding.ActivityWorkoutPreferenceBinding
import com.example.capstoneapp.ui.Feature02.WorkoutList.WorkoutListActivity
import com.example.capstoneapp.viewmodel.Feature02.WorkoutPreference.WorkoutPreferenceViewModel

class WorkoutPreferenceActivity : AppCompatActivity(), OnValueTransferListener, OnArrayValueTransferListener {

    private lateinit var binding: ActivityWorkoutPreferenceBinding
    private lateinit var viewModel: WorkoutPreferenceViewModel
    private var preference: WorkoutPreference = WorkoutPreference(null, null, null, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WorkoutPreferenceViewModel::class.java]

        viewModel.preferenceIndex.observe(this) { index ->
            Log.d("preferenceIndex", index.toString())
            setPreferenceSelection(index)
            setProgressBar(index)
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
            1 -> MuscleTargetFragment()
            2 -> ExerciseOptionFragment()
            3 -> WorkoutFrequencyFragment()
            else -> null
        }

        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.workoutPreferenceClFragmentPlaceholder.id, fragment)
                .commit()
        }
    }

    private fun setProgressBar(preferenceIndex: Int) {
        when (preferenceIndex) {
            0 -> binding.workoutPreferenceIvProgressBar.setImageResource(R.drawable.img_progress_bar_0)
            1 -> binding.workoutPreferenceIvProgressBar.setImageResource(R.drawable.img_progress_bar_1)
            2 -> binding.workoutPreferenceIvProgressBar.setImageResource(R.drawable.img_progress_bar_2)
            3 -> binding.workoutPreferenceIvProgressBar.setImageResource(R.drawable.img_progress_bar_3)
        }
    }

    override fun onValueTransfer(tag:String, value: String) {
        Log.d("STRING RECEIVED FROM $tag", value)

        when (tag) {
            "WorkoutLevel" -> preference = preference.copy(level = value)
            "MuscleTarget" -> preference = preference.copy(target = value)
            "ExerciseOption" -> preference = preference.copy(option = value)

        }

        viewModel.incrementPreferenceIndex()

        Log.d("PREFERENCE", preference.toString())
        Log.d("preferenceIndex", viewModel.preferenceIndex.toString())
    }

    override fun onArrayValueTransferListener(tag: String, value: MutableList<Int>) {
        Log.d("ARRAY RECEIVED FROM $tag", value.toString())

        preference = preference.copy(days = value as List<Int>)
        Log.d("PREFERENCE", preference.toString())

        val intent = Intent(this, WorkoutListActivity::class.java)
        intent.putExtra(WorkoutListActivity.KEY_PREFERENCE, preference)
        startActivity(intent)
        // Kalo Navigasi aneh, edit ini
    }

    override fun onResume() {
        super.onResume()
        viewModel.resetPreferenceIndex()
    }
}
package com.example.capstoneapp.ui.Feature02.WorkoutSequence

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneapp.R
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.data.response.GetWorkoutsItem
import com.example.capstoneapp.databinding.ActivityWorkoutSequenceBinding
import com.example.capstoneapp.ui.Feature02.WorkoutFinish.WorkoutFinishActivity
import com.example.capstoneapp.viewmodel.Feature02.WorkoutPreference.WorkoutPreferenceViewModel
import com.example.capstoneapp.viewmodel.Feature02.WorkoutSequenceViewModel

class WorkoutSequenceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutSequenceBinding
    private var workoutList: List<GetWorkoutsItem>? = null
    private var detail: GetDataItem? = null
    private var currentWorkout: Int = 0
    private lateinit var viewModel: WorkoutSequenceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutSequenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WorkoutSequenceViewModel::class.java]

        detail = intent.getParcelableExtra(KEY_WORKOUTS) as GetDataItem?
        detail!!.workouts!!.forEach {
            Log.d("Workouts Ids", it!!.id.toString())
        }
        workoutList = (detail!!.workouts as List<GetWorkoutsItem>?)!!

        setAction()
        setDisplay()
    }

    private fun setDisplay() {
        viewModel.workoutIndex.observe(this) {
            if (it < workoutList!!.size) {
                setWorkoutItem(workoutList?.get(it)!!)
            } else {
                startActivity(Intent(this, WorkoutFinishActivity::class.java))
                finish()
            }

        }
    }

    private fun setWorkoutItem(workoutItem: GetWorkoutsItem) {
        binding.workoutSequenceTvPageTitle.text = "${workoutItem.bodyGroup} Body Workout"
        binding.workoutSequenceTvWorkoutNamePlaceholder.text = workoutItem.exerciseName
        binding.workoutSequenceTvDescriptionPlaceholder.text = workoutItem.shortDescription
        binding.workoutSequenceTvInstructionPlaceholder.text = workoutItem.instructions
    }

    private fun setAction() {
        binding.workoutSequenceIvBackButton.setOnClickListener {
            finish()
        }

        binding.workoutSequenceClNextWorkoutButton.setOnClickListener {
            viewModel.incrementWorkoutIndex()
        }
    }

    companion object {
        const val KEY_WORKOUTS = "workouts"
    }
}
package com.example.capstoneapp.ui.Feature02.WorkoutFinish

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneapp.R
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.databinding.ActivityWorkoutFinishBinding
import com.example.capstoneapp.ui.Feature02.WorkoutSequence.WorkoutSequenceActivity

class WorkoutFinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutFinishBinding
    private var detail: GetDataItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detail = intent.getParcelableExtra(WorkoutSequenceActivity.KEY_WORKOUTS) as GetDataItem?

        setAction()
        setDisplay()
    }

    private fun setDisplay() {
        binding.workoutFinishTvPageTitle.text = "${detail!!.target} Body Workout"
    }

    private fun setAction() {
        binding.workoutStartClCloseButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val KEY_WORKOUTS = "workouts"
    }
}
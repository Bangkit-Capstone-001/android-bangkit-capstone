package com.example.capstoneapp.ui.Feature02.WorkoutSequence

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneapp.R
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.data.response.GetWorkoutsItem
import com.example.capstoneapp.databinding.ActivityWorkoutSequenceBinding
import com.example.capstoneapp.ui.Feature02.WorkoutFinish.WorkoutFinishActivity
import com.example.capstoneapp.viewmodel.Feature02.WorkoutSequenceViewModel
import com.google.android.material.carousel.CarouselLayoutManager

class WorkoutSequenceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutSequenceBinding
    private var workoutList: List<GetWorkoutsItem>? = null
    private var detail: GetDataItem? = null
    private lateinit var viewModel: WorkoutSequenceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = getColor(R.color.black)
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
                val intent = Intent(this, WorkoutFinishActivity::class.java)
                intent.putExtra(WorkoutFinishActivity.KEY_WORKOUTS, detail)
                startActivity(intent)
                finish()
            }

        }
    }

    private fun setWorkoutItem(workoutItem: GetWorkoutsItem) {
        binding.workoutSequenceTvPageTitle.text = "${workoutItem.bodyGroup} Body Workout"
        binding.workoutSequenceTvWorkoutNamePlaceholder.text = workoutItem.exerciseName
        binding.workoutSequenceTvDescriptionPlaceholder.text = workoutItem.shortDescription
        binding.workoutSequenceTvInstructionPlaceholder.text = workoutItem.instructions
        binding.workoutSequenceIvYoutubeAnchorPlaceholder.setOnClickListener {
            val youtubeUrl = workoutItem.youtubeLinks
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
            startActivity(intent)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Log.d("Youtube URL", youtubeUrl.toString())
            }
        }

        val imageUrls = workoutItem.exerciseImages ?: emptyList()
        val adapter = CarouselAdapter(imageUrls)
        binding.workoutSequenceRvWorkoutCarousel.adapter = adapter
        binding.workoutSequenceRvWorkoutCarousel.layoutManager = CarouselLayoutManager()
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
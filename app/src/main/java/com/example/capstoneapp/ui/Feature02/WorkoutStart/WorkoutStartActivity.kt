package com.example.capstoneapp.ui.Feature02.WorkoutStart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.data.response.GetWorkoutsItem
import com.example.capstoneapp.databinding.ActivityWorkoutStartBinding
import com.example.capstoneapp.ui.Feature02.WorkoutSequence.WorkoutSequenceActivity

class WorkoutStartActivity : AppCompatActivity() {

    private var detail: GetDataItem? = null
    private lateinit var binding: ActivityWorkoutStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.workoutStartRvWorkoutItemList.layoutManager = layoutManager

        detail = intent.getParcelableExtra(KEY_DETAIL) as GetDataItem?

        setDisplay(detail!!)
        setAction()
    }

    private fun setAction() {
        binding.workoutStartIvBackButton.setOnClickListener {
            finish()
        }

        binding.workoutStartClStartWorkoutButton.setOnClickListener {
            val intent = Intent(this, WorkoutSequenceActivity::class.java)
            intent.putExtra(WorkoutSequenceActivity.KEY_WORKOUTS, detail)
            startActivity(intent)
        }
    }

    private fun setDisplay(detail: GetDataItem) {
        binding.workoutStartTvPageTitle.text = "${detail.option} Body Workout"
        setAdapter(detail.workouts!!)
    }

    private fun setAdapter(workouts: List<GetWorkoutsItem?>) {
        val adapter = SelectedWorkoutAdapter()
        adapter.submitList(workouts)
        binding.workoutStartRvWorkoutItemList.adapter = adapter
    }

    companion object {
        const val KEY_DETAIL = "key-detail"
    }
}
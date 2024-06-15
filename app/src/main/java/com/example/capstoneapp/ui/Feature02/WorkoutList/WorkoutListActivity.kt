package com.example.capstoneapp.ui.Feature02.WorkoutList

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneapp.data.WorkoutPreference
import com.example.capstoneapp.data.response.DataItem
import com.example.capstoneapp.databinding.ActivityWorkoutListBinding
import com.example.capstoneapp.viewmodel.Feature02.WorkoutList.WorkoutListViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class WorkoutListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutListBinding
    private val viewModel by viewModels<WorkoutListViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.workoutListRvWorkoutItemList.layoutManager = layoutManager

        val preference = intent.getParcelableExtra(KEY_PREFERENCE) as WorkoutPreference?
        Log.d("FROM WORKOUT LIST", preference.toString())

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                val token = "Bearer ${user.token}"
                viewModel.getRandomPreferenceWorkout(token, preference!!)
            }
        }

        viewModel.workouts.observe(this) { workouts ->
            Log.d("WORKOUTS", workouts.toString())
            setAdapter(workouts.data ?: emptyList<DataItem>())
        }

        setAction()
    }

    private fun setAction() {
        binding.workoutListIvBackButton.setOnClickListener {
            finish()
        }
    }

    private fun setAdapter(workoutList: List<DataItem?>) {
        val adapter = WorkoutListAdapter()
        adapter.submitList(workoutList)
        binding.workoutListRvWorkoutItemList.adapter = adapter
    }

    companion object {
        const val KEY_PREFERENCE = "key-preference"
    }
}
package com.example.capstoneapp.ui.Feature02.WorkoutList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneapp.data.WorkoutPreference
import com.example.capstoneapp.data.response.DataItem
import com.example.capstoneapp.databinding.ActivityWorkoutListBinding
import com.example.capstoneapp.ui.Feature02.WorkoutValidation.WorkoutValidationActivity
import com.example.capstoneapp.viewmodel.Feature02.WorkoutList.WorkoutListViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class WorkoutListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutListBinding
    private val viewModel by viewModels<WorkoutListViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var preference: WorkoutPreference? = null
    private val selectedWorkouts = mutableListOf<String>()
    private lateinit var allWorkouts: List<DataItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.workoutListRvWorkoutItemList.layoutManager = layoutManager

        preference = intent.getParcelableExtra(KEY_PREFERENCE) as WorkoutPreference?
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
            allWorkouts = workouts.data as List<DataItem>
        }

        setAction()
    }

    private fun setAction() {
        binding.workoutListIvBackButton.setOnClickListener {
            finish()
        }

        binding.workoutListClGenerateWorkoutButton.setOnClickListener {
            viewModel.getSession().observe(this) { user ->
                if (user.isLogin) {
                    val token = "Bearer ${user.token}"
                    viewModel.getRandomPreferenceWorkout(token, preference!!)
                }
            }
        }

        binding.workoutListClSaveWorkoutButton.setOnClickListener {
            if (checkAmountOfWorkout()) {
                preference = preference?.copy(workoutIds = selectedWorkouts)

                val intent = Intent(this, WorkoutValidationActivity::class.java)
                intent.putExtra(WorkoutValidationActivity.KEY_PREFERENCE, preference)
                startActivity(intent)
                // Kalo Navigasi aneh, edit ini
            }
        }
    }

    private fun checkAmountOfWorkout() : Boolean {
        return when (preference?.level) {
            "Beginner" -> selectedWorkouts.size >= 5
            "Intermediate" -> selectedWorkouts.size >= 7
            "Advance" -> selectedWorkouts.size >= 10
            else -> false
        }
    }

    private fun setAdapter(workoutList: List<DataItem?>) {
        val adapter = WorkoutListAdapter(
            onItemClicked = {workoutItem -> onWorkoutItemClicked(workoutItem)},
            isSelected = { id -> selectedWorkouts.contains(id)}
        )
        adapter.submitList(workoutList)
        binding.workoutListRvWorkoutItemList.adapter = adapter
    }

    private fun onWorkoutItemClicked(workoutItem: DataItem) {
        Log.d("CLICKED ITEM", workoutItem.toString())

        if (!selectedWorkouts.contains(workoutItem.id.toString())) {
            selectedWorkouts.add(workoutItem.id.toString())
        } else {
            selectedWorkouts.remove(workoutItem.id.toString())
        }
        Log.d("WORKOUTS", selectedWorkouts.toString())
    }

    companion object {
        const val KEY_PREFERENCE = "key-preference"
    }
}
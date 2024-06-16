package com.example.capstoneapp.ui.Feature02.WorkoutList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
    private val selectedWorkoutDataItem = mutableListOf<DataItem>()
    private lateinit var allWorkouts: List<DataItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.workoutListRvWorkoutItemList.layoutManager = layoutManager

        preference = intent.getParcelableExtra(KEY_PREFERENCE) as WorkoutPreference?

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                val token = "Bearer ${user.token}"
                viewModel.getRandomPreferenceWorkout(token, preference!!)
            }
        }

        viewModel.workouts.observe(this) { workouts ->
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
                // Add here
                preference = preference?.copy(workoutIds = selectedWorkouts, selectedWorkouts = selectedWorkoutDataItem)

                val intent = Intent(this, WorkoutValidationActivity::class.java)
                intent.putExtra(WorkoutValidationActivity.KEY_PREFERENCE, preference)
                startActivity(intent)
                // Kalo Navigasi aneh, edit ini
            } else {
                val alertDialog = AlertDialog.Builder(this).apply {
                    setTitle("Can not proceed yet!")
                    setMessage("You need to choose at least " + determineAmountOfWorkout() + " workouts")
                    setPositiveButton("Close") { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                }

                alertDialog.show()
            }
        }
    }

    private fun determineAmountOfWorkout() : String {
        return when (preference?.level) {
            "Beginner" -> "5"
            "Intermediate" -> "7"
            "Advance" -> "10"
            else -> ""
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

        if (!selectedWorkouts.contains(workoutItem.id.toString())) {
            selectedWorkouts.add(workoutItem.id.toString())
            selectedWorkoutDataItem.add(workoutItem)
        } else {
            selectedWorkouts.remove(workoutItem.id.toString())
            selectedWorkoutDataItem.remove(workoutItem)
        }
    }

    companion object {
        const val KEY_PREFERENCE = "key-preference"
    }
}
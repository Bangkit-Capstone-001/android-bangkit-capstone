package com.example.capstoneapp.ui.Feature02.WorkoutValidation

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneapp.R
import com.example.capstoneapp.data.pref.WorkoutPreference
import com.example.capstoneapp.data.response.DataItem
import com.example.capstoneapp.databinding.ActivityWorkoutValidationBinding
import com.example.capstoneapp.ui.Feature02.WorkoutList.WorkoutListActivity
import com.example.capstoneapp.ui.Feature02.WorkoutList.WorkoutListAdapter
import com.example.capstoneapp.viewmodel.Feature02.WorkoutValidation.WorkoutValidationViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class WorkoutValidationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutValidationBinding
    private var preference: WorkoutPreference? = null
    private var days = mutableListOf<Int>()
    private var selectedWorkoutsId = mutableListOf<String>()
    private val viewModel by viewModels<WorkoutValidationViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.black)
        binding = ActivityWorkoutValidationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManagerFavorite = LinearLayoutManager(this)
        binding.workoutValidationRvFavoriteWorkoutList.layoutManager = layoutManagerFavorite

        val layoutManagerRecommended = LinearLayoutManager(this)
        binding.workoutValidationRvFavoriteWorkoutList.layoutManager = layoutManagerRecommended

        preference =
            intent.getParcelableExtra(WorkoutListActivity.KEY_PREFERENCE) as WorkoutPreference?
        Log.d("FROM WORKOUT VALIDATION", preference?.workoutIds.toString())

        days = preference?.days as MutableList<Int>
        Log.d("DAYS", days.toString())

        viewModel._favoriteWorkouts.value = preference?.selectedWorkouts
        selectedWorkoutsId = preference?.workoutIds as MutableList<String>

        viewModel.favoriteWorkouts.observe(this) { workouts ->
            setFavoriteAdapter(workouts)
        }

        initDisplay(preference!!)
        onSelection()
        setAction()

        viewModel.postWorkoutPlanResult.observe(this) { result ->
            result.onSuccess { response ->
                Log.d("WorkoutValidation", "Post Success : $response")
                val resultIntent = Intent()
                resultIntent.putExtra("shouldFinishSequentially", true)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }.onFailure { throwable ->
                Log.e("WorkoutValidation", "Post Failed : ${throwable.message}")
            }
        }
    }

    private fun setAction() {
        binding.workoutValidationIvBackButton.setOnClickListener {
            finish()
        }

        binding.workoutValidationClSaveWorkoutPlanButton.setOnClickListener {
            preference = preference?.copy(days = days, workoutIds = selectedWorkoutsId)

            if (validateWorkout() && !isDayEmpty()) {
                Log.d("PREF DAYS", preference?.days.toString())
                Log.d("PREF WORKOUTS", preference?.workoutIds.toString())

                viewModel.getSession().observe(this) { user ->
                    val token = "Bearer ${user.token}"
                    viewModel.postWorkoutPlan(token, preference!!)
                }
            }
        }
    }

    // Validation
    private fun validateWorkout(): Boolean {
        if (!checkAmountOfWorkout()) {
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

        return checkAmountOfWorkout()
    }

    private fun isDayEmpty(): Boolean {
        if (days.isEmpty()) {
            val alertDialog = AlertDialog.Builder(this).apply {
                setTitle("Can not proceed yet!")
                setMessage("You need to choose at least one day")
                setPositiveButton("Close") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
            }

            alertDialog.show()
        }
        return days.isEmpty()
    }

    private fun determineAmountOfWorkout(): String {
        return when (preference?.level) {
            "Beginner" -> "5"
            "Intermediate" -> "7"
            "Advance" -> "10"
            else -> ""
        }
    }

    private fun checkAmountOfWorkout(): Boolean {
        return when (preference?.level) {
            "Beginner" -> selectedWorkoutsId.size >= 5
            "Intermediate" -> selectedWorkoutsId.size >= 7
            "Advance" -> selectedWorkoutsId.size >= 10
            else -> false
        }
    }


    // FAVORITE WORKOUTS
    private fun setFavoriteAdapter(workoutList: List<DataItem?>) {
        val adapter = WorkoutListAdapter(
            onItemClicked = { workoutItem -> onWorkoutItemClicked(workoutItem) },
            isSelected = { id -> selectedWorkoutsId.contains(id) }
        )
        adapter.submitList(workoutList)
        binding.workoutValidationRvFavoriteWorkoutList.adapter = adapter
    }

    private fun onWorkoutItemClicked(workoutItem: DataItem) {
        if (!selectedWorkoutsId.contains(workoutItem.id.toString())) {
            selectedWorkoutsId.add(workoutItem.id.toString())
            viewModel.addFavoriteWorkouts(workoutItem)
        } else {
            selectedWorkoutsId.remove(workoutItem.id.toString())
            viewModel.removeFavoriteWorkouts(workoutItem)
        }
    }

    // DAYS
    private fun initDisplay(preference: WorkoutPreference) {
        preference.days.let { days ->
            if (days != null) {
                days.forEach { day ->
                    setClickedOnDayUI(day)
                }
            }
        }
    }

    private fun onSelection() {
        binding.workoutValidationClDayContainerSunday.setOnClickListener {
            setDays(0)
        }

        binding.workoutValidationClDayContainerMonday.setOnClickListener {
            setDays(1)
        }

        binding.workoutValidationClDayContainerTuesday.setOnClickListener {
            setDays(2)
        }

        binding.workoutValidationClDayContainerWednesday.setOnClickListener {
            setDays(3)
        }

        binding.workoutValidationClDayContainerThursday.setOnClickListener {
            setDays(4)
        }

        binding.workoutValidationClDayContainerFriday.setOnClickListener {
            setDays(5)
        }

        binding.workoutValidationClDayContainerSaturday.setOnClickListener {
            setDays(6)
        }
    }

    private fun setDays(dayIndex: Int) {
        if (!days.contains(dayIndex)) {
            days.add(dayIndex)
            setClickedOnDayUI(dayIndex)
        } else {
            days.remove(dayIndex)
            setClickedOffDayUI(dayIndex)
        }

        Log.d("DAYS", days.toString())
    }

    private fun setClickedOnDayUI(dayIndex: Int) {
        val lightBlue = ContextCompat.getColor(this, R.color.paleBlue)
        val darkBlue = ContextCompat.getColor(this, R.color.darkBlue)
        when (dayIndex) {
            0 -> {binding.workoutValidationClDayContainerSunday.setBackgroundColor(lightBlue!!)
                binding.workoutValidationTvDaySunday.setTextColor(darkBlue!!)}
            1 -> {binding.workoutValidationClDayContainerMonday.setBackgroundColor(lightBlue!!)
                binding.workoutValidationTvDayMonday.setTextColor(darkBlue!!)}
            2 -> {binding.workoutValidationClDayContainerTuesday.setBackgroundColor(lightBlue!!)
                binding.workoutValidationTvDayTuesday.setTextColor(darkBlue!!)}
            3 -> {binding.workoutValidationClDayContainerWednesday.setBackgroundColor(lightBlue!!)
                binding.workoutValidationTvDayWednesday.setTextColor(darkBlue!!)}
            4 -> {binding.workoutValidationClDayContainerThursday.setBackgroundColor(lightBlue!!)
                binding.workoutValidationTvDayThursday.setTextColor(darkBlue!!)}
            5 -> {binding.workoutValidationClDayContainerFriday.setBackgroundColor(lightBlue!!)
                binding.workoutValidationTvDayFriday.setTextColor(darkBlue!!)}
            6 -> {binding.workoutValidationClDayContainerSaturday.setBackgroundColor(lightBlue!!)
                binding.workoutValidationTvDaySaturday.setTextColor(darkBlue!!)}
        }
    }

    private fun setClickedOffDayUI(dayIndex: Int) {
        val white = ContextCompat.getColor(this, R.color.white)
        val darkBlue = ContextCompat.getColor(this, R.color.darkBlue)
        when (dayIndex) {
            0 -> {binding.workoutValidationClDayContainerSunday.setBackgroundColor(darkBlue)
                binding.workoutValidationTvDaySunday.setTextColor(white)}
            1 -> {binding.workoutValidationClDayContainerMonday.setBackgroundColor(darkBlue)
                binding.workoutValidationTvDayMonday.setTextColor(white)}
            2 -> {binding.workoutValidationClDayContainerTuesday.setBackgroundColor(darkBlue)
                binding.workoutValidationTvDayTuesday.setTextColor(white)}
            3 -> {binding.workoutValidationClDayContainerWednesday.setBackgroundColor(darkBlue)
                binding.workoutValidationTvDayWednesday.setTextColor(white)}
            4 -> {binding.workoutValidationClDayContainerThursday.setBackgroundColor(darkBlue)
                binding.workoutValidationTvDayThursday.setTextColor(white)}
            5 -> {binding.workoutValidationClDayContainerFriday.setBackgroundColor(darkBlue)
                binding.workoutValidationTvDayFriday.setTextColor(white)}
            6 -> {binding.workoutValidationClDayContainerSaturday.setBackgroundColor(darkBlue)
                binding.workoutValidationTvDaySaturday.setTextColor(white)}
        }
    }

    companion object {
        const val KEY_PREFERENCE = "key-preference"
    }
}
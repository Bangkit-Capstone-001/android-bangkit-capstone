package com.example.capstoneapp.ui.Feature02.WorkoutPlanDetail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.data.response.GetWorkoutsItem
import com.example.capstoneapp.databinding.ActivityWorkoutPlanDetailBinding

class WorkoutPlanDetailActivity : AppCompatActivity() {

    private var detail: GetDataItem? = null
    private lateinit var binding: ActivityWorkoutPlanDetailBinding
    private var daysLocal = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutPlanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detail = intent.getParcelableExtra(KEY_DETAIL) as GetDataItem?

        initDisplay(detail!!)
        setAction()
        onDaySelection()
    }

    private fun setAction() {
        binding.workoutPlanDetailIvBackButton.setOnClickListener {
            finish()
        }
    }

    private fun initDisplay(detail: GetDataItem) {
        setInitDays(detail.days!!)
    }

    private fun setInitDays(days: List<Int?>) {
        days.forEach { setClickedOnDayUI(it!!) }
        daysLocal = days as MutableList<Int>
    }

    private fun setMyWorkoutAdapter(workouts: List<GetWorkoutsItem>) {

    }

    private fun onDaySelection() {
        binding.workoutPlanDetailClDayContainerSunday.setOnClickListener {
            setDays(0)
        }

        binding.workoutPlanDetailClDayContainerMonday.setOnClickListener {
            setDays(1)
        }

        binding.workoutPlanDetailClDayContainerTuesday.setOnClickListener {
            setDays(2)
        }

        binding.workoutPlanDetailClDayContainerWednesday.setOnClickListener {
            setDays(3)
        }

        binding.workoutPlanDetailClDayContainerThursday.setOnClickListener {
            setDays(4)
        }

        binding.workoutPlanDetailClDayContainerFriday.setOnClickListener {
            setDays(5)
        }

        binding.workoutPlanDetailClDayContainerSaturday.setOnClickListener {
            setDays(6)
        }
    }

    private fun setDays(dayIndex: Int) {
        if (!daysLocal.contains(dayIndex)) {
            daysLocal.add(dayIndex)
            setClickedOnDayUI(dayIndex)
        } else {
            daysLocal.remove(dayIndex)
            setClickedOffDayUI(dayIndex)
        }

        Log.d("DAY LOCAL", daysLocal.toString())
    }

    private fun setClickedOnDayUI(dayIndex: Int) {
        val lightBlue = Color.parseColor("#2970FF")
        when (dayIndex) {
            0 -> binding.workoutPlanDetailClDayContainerSunday.setBackgroundColor(lightBlue)
            1 -> binding.workoutPlanDetailClDayContainerMonday.setBackgroundColor(lightBlue)
            2 -> binding.workoutPlanDetailClDayContainerTuesday.setBackgroundColor(lightBlue)
            3 -> binding.workoutPlanDetailClDayContainerWednesday.setBackgroundColor(lightBlue)
            4 -> binding.workoutPlanDetailClDayContainerThursday.setBackgroundColor(lightBlue)
            5 -> binding.workoutPlanDetailClDayContainerFriday.setBackgroundColor(lightBlue)
            6 -> binding.workoutPlanDetailClDayContainerSaturday.setBackgroundColor(lightBlue)
        }
    }

    private fun setClickedOffDayUI(dayIndex: Int) {
        val darkBlue = Color.parseColor("#223767")
        when (dayIndex) {
            0 -> binding.workoutPlanDetailClDayContainerSunday.setBackgroundColor(darkBlue)
            1 -> binding.workoutPlanDetailClDayContainerMonday.setBackgroundColor(darkBlue)
            2 -> binding.workoutPlanDetailClDayContainerTuesday.setBackgroundColor(darkBlue)
            3 -> binding.workoutPlanDetailClDayContainerWednesday.setBackgroundColor(darkBlue)
            4 -> binding.workoutPlanDetailClDayContainerThursday.setBackgroundColor(darkBlue)
            5 -> binding.workoutPlanDetailClDayContainerFriday.setBackgroundColor(darkBlue)
            6 -> binding.workoutPlanDetailClDayContainerSaturday.setBackgroundColor(darkBlue)
        }
    }

    companion object {
        const val KEY_DETAIL = "key-detail"
    }
}
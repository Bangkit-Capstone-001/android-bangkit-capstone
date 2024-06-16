package com.example.capstoneapp.ui.Feature02.WorkoutPlanDetail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneapp.data.response.DataItem
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.data.response.GetWorkoutsItem
import com.example.capstoneapp.databinding.ActivityWorkoutPlanDetailBinding
import com.example.capstoneapp.ui.Feature02.WorkoutList.WorkoutListAdapter
import com.example.capstoneapp.viewmodel.Feature02.WorkoutPlanDetail.WorkoutPlanDetailViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class WorkoutPlanDetailActivity : AppCompatActivity() {

    private var detail: GetDataItem? = null
    private lateinit var binding: ActivityWorkoutPlanDetailBinding
    private var daysLocal = mutableListOf<Int>()
    private var selectedWorkoutsId = mutableListOf<String>()
    private val viewModel by viewModels<WorkoutPlanDetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutPlanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoriteWorkoutLayoutManager = LinearLayoutManager(this)
        binding.workoutPlanDetailRvFavoriteWorkoutList.layoutManager = favoriteWorkoutLayoutManager

        val recommendedWorkoutLayoutManager = LinearLayoutManager(this)
        binding.workoutPlanDetailRvRecommendedWorkoutList.layoutManager = recommendedWorkoutLayoutManager

        detail = intent.getParcelableExtra(KEY_DETAIL) as GetDataItem?

        initDisplay(detail!!)
        setAction(detail!!)
        onDaySelection()

        viewModel.deleteResponse.observe(this) { result ->
            if (result.status == 200) {
                finish()
            }
        }
    }

    private fun setAction(detail: GetDataItem) {
        binding.workoutPlanDetailIvBackButton.setOnClickListener {
            finish()
        }

        binding.workoutPlanDetailClDeleteWorkoutPlanButton.setOnClickListener {
            viewModel.getSession().observe(this) { user ->
                val token = "Bearer ${user.token}"
                val listId: List<String> = listOf(detail.id!!)
                Log.d("List ID", listId.toString())
//                viewModel.deleteWorkoutPlan(token, listId)
            }
        }
    }

    private fun initDisplay(detail: GetDataItem) {
        setInitDays(detail.days!!)
        setInitFavoriteWorkouts(detail.workouts!!)
        setInitRecommendedWorkout(detail)
    }

    private fun setInitRecommendedWorkout(detail: GetDataItem) {
        viewModel.getSession().observe(this) { user ->
            val token = "Bearer ${user.token}"
            viewModel.getRecommendedWorkout(token, detail!!)
        }

        viewModel.recommendedWorkouts.observe(this) { workouts ->
            setRecommendedAdapter(workouts)
        }
    }

    private fun setInitFavoriteWorkouts(workouts: List<GetWorkoutsItem?>) {
        val dataItemWorkouts = workouts.map { workout ->
            DataItem(
                shortDescription = workout!!.shortDescription,
                instructions = workout.instructions,
                rating = workout.rating,
                equipment = workout.equipment,
                id = workout.id,
                guideImgUrl = workout.guideImgUrl,
                bodyGroup = workout.bodyGroup,
                youtubeLinks = workout.youtubeLinks,
                youtubeTitle = workout.youtubeTitle,
                exerciseImages = workout.exerciseImages,
                exerciseName = workout.exerciseName,
                option = workout.option
            )
        }

        viewModel._favoriteWorkouts.value = dataItemWorkouts
        dataItemWorkouts.forEach {
            selectedWorkoutsId.add(it.id!!)
        }
        setFavoriteAdapter(dataItemWorkouts)
    }

    private fun setFavoriteAdapter(workoutList: List<DataItem?>) {
        val adapter = WorkoutListAdapter(
            onItemClicked = {workoutItem -> onWorkoutItemClicked(workoutItem)},
            isSelected = { id -> selectedWorkoutsId.contains(id)}
        )
        adapter.submitList(workoutList)
        binding.workoutPlanDetailRvFavoriteWorkoutList.adapter = adapter
    }

    private fun setRecommendedAdapter(workoutList: List<DataItem?>) {
        val adapter = WorkoutListAdapter(
            onItemClicked = {workoutItem -> onWorkoutItemClicked(workoutItem)},
            isSelected = { id -> selectedWorkoutsId.contains(id)}
        )
        adapter.submitList(workoutList)
        binding.workoutPlanDetailRvRecommendedWorkoutList.adapter = adapter
    }

    private fun onWorkoutItemClicked(workoutItem: DataItem) {
        if (!selectedWorkoutsId.contains(workoutItem.id.toString())) {
            selectedWorkoutsId.add(workoutItem.id.toString())
            viewModel.addFavoriteWorkouts(workoutItem)
        } else {
            selectedWorkoutsId.remove(workoutItem.id.toString())
            viewModel.removeFavoriteWorkouts(workoutItem)
        }

        Log.d("Workout Size", selectedWorkoutsId.size.toString())

    }

    // DAYS
    private fun setInitDays(days: List<Int?>) {
        days.forEach { setClickedOnDayUI(it!!) }
        daysLocal = days as MutableList<Int>
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
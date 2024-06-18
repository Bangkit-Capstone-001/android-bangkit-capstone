package com.example.capstoneapp.ui.Feature02

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneapp.R
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.databinding.FragmentFeature02Binding
import com.example.capstoneapp.ui.Feature02.WorkoutPreference.WorkoutPreferenceActivity
import com.example.capstoneapp.viewmodel.Feature02.Feature02ViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory
import java.time.DayOfWeek
import java.time.LocalDate

class Feature02Fragment : Fragment() {

    private var _binding: FragmentFeature02Binding? = null
    private val binding get() = _binding!!
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private val viewModel: Feature02ViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private var currentDay: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeature02Binding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allPlanLayoutManager = LinearLayoutManager(requireContext())
        binding.mainFragment2RvMyWorkoutPlans.layoutManager = allPlanLayoutManager

        val dailyPlanLayoutManager = LinearLayoutManager(requireContext())
        binding.mainFragment2RvWorkoutPlans.layoutManager = dailyPlanLayoutManager

        setAction()

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val shouldFinishSequentially = result.data?.getBooleanExtra("shouldFinishSequentially", false) ?: false
            }
        }

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                val token = "Bearer ${user.token}"
                viewModel.getWorkoutPlans(token)
            }
        }

        viewModel.workoutPlans.observe(viewLifecycleOwner) { plans ->
            // set Adapter ada 2 :
            initDay(plans)
            setMyWorkoutPlans(plans)
            onDaySelection(plans)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                val token = "Bearer ${user.token}"
                viewModel.getWorkoutPlans(token)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAction() {
        binding.mainFragment2IvAddWorkoutPlanButton.setOnClickListener {
            val intent = Intent(requireContext(), WorkoutPreferenceActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private fun setMyWorkoutPlans(plans: List<GetDataItem>) {
        val adapter = WorkoutPlanAdapter()
        adapter.submitList(plans)
        binding.mainFragment2RvMyWorkoutPlans.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initDay(plans: List<GetDataItem>) {
        val day = getCurrentDay()
        when (day) {
            "SUNDAY" -> processOnDay(0, plans)
            "MONDAY" -> processOnDay(1, plans)
            "TUESDAY" -> processOnDay(2, plans)
            "WEDNESDAY" -> processOnDay(3, plans)
            "THURSDAY" -> processOnDay(4, plans)
            "FRIDAY" -> processOnDay(5, plans)
            "SATURDAY" -> processOnDay(6, plans)
        }
    }

    private fun onDaySelection(plans: List<GetDataItem>) {
        binding.mainFragment2ClDayContainerSunday.setOnClickListener {
            processOnDay(0, plans)
        }

        binding.mainFragment2ClDayContainerMonday.setOnClickListener {
            processOnDay(1, plans)
        }

        binding.mainFragment2ClDayContainerTuesday.setOnClickListener {
            processOnDay(2, plans)
        }

        binding.mainFragment2ClDayContainerWednesday.setOnClickListener {
            processOnDay(3, plans)
        }

        binding.mainFragment2ClDayContainerThursday.setOnClickListener {
            processOnDay(4, plans)
        }

        binding.mainFragment2ClDayContainerFriday.setOnClickListener {
            processOnDay(5, plans)
        }

        binding.mainFragment2ClDayContainerSaturday.setOnClickListener {
            processOnDay(6, plans)
        }
    }

    private fun processOnDay(dayIndex: Int, plans: List<GetDataItem>) {
        var filteredPlans: List<GetDataItem> = plans.filter { it.days!!.contains(dayIndex) }
        setDailyWorkoutPlans(filteredPlans)
        setDayUI(dayIndex)
    }

    private fun setDayUI(dayIndex: Int) {
        setClickedOffDayUI(currentDay)
        currentDay = dayIndex
        setClickedOnDayUI(currentDay)
    }

    private fun setClickedOnDayUI(dayIndex: Int) {
        val lightBlue = context?.let { ContextCompat.getColor(it, R.color.paleBlue) }
        val darkBlue = context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
        when (dayIndex) {
            0 -> {binding.mainFragment2ClDayContainerSunday.setBackgroundColor(lightBlue!!)
                  binding.mainFragment2TvDaySunday.setTextColor(darkBlue!!)}
            1 -> {binding.mainFragment2ClDayContainerMonday.setBackgroundColor(lightBlue!!)
                  binding.mainFragment2TvDayMonday.setTextColor(darkBlue!!)}
            2 -> {binding.mainFragment2ClDayContainerTuesday.setBackgroundColor(lightBlue!!)
                  binding.mainFragment2TvDayTuesday.setTextColor(darkBlue!!)}
            3 -> {binding.mainFragment2ClDayContainerWednesday.setBackgroundColor(lightBlue!!)
                  binding.mainFragment2TvDayWednesday.setTextColor(darkBlue!!)}
            4 -> {binding.mainFragment2ClDayContainerThursday.setBackgroundColor(lightBlue!!)
                  binding.mainFragment2TvDayThursday.setTextColor(darkBlue!!)}
            5 -> {binding.mainFragment2ClDayContainerFriday.setBackgroundColor(lightBlue!!)
                  binding.mainFragment2TvDayFriday.setTextColor(darkBlue!!)}
            6 -> {binding.mainFragment2ClDayContainerSaturday.setBackgroundColor(lightBlue!!)
                binding.mainFragment2TvDaySaturday.setTextColor(darkBlue!!)}
        }
    }

    private fun setClickedOffDayUI(dayIndex: Int) {
        val white = context?.let { ContextCompat.getColor(it, R.color.white) }
        val darkBlue = Color.parseColor("#223767")
        when (dayIndex) {
            0 -> {binding.mainFragment2ClDayContainerSunday.setBackgroundColor(darkBlue)
                  binding.mainFragment2TvDaySunday.setTextColor(white!!)}
            1 -> {binding.mainFragment2ClDayContainerMonday.setBackgroundColor(darkBlue)
                  binding.mainFragment2TvDayMonday.setTextColor(white!!)}
            2 -> {binding.mainFragment2ClDayContainerTuesday.setBackgroundColor(darkBlue)
                  binding.mainFragment2TvDayTuesday.setTextColor(white!!)}
            3 -> {binding.mainFragment2ClDayContainerWednesday.setBackgroundColor(darkBlue)
                  binding.mainFragment2TvDayWednesday.setTextColor(white!!)}
            4 -> {binding.mainFragment2ClDayContainerThursday.setBackgroundColor(darkBlue)
                  binding.mainFragment2TvDayThursday.setTextColor(white!!)}
            5 -> {binding.mainFragment2ClDayContainerFriday.setBackgroundColor(darkBlue)
                  binding.mainFragment2TvDayFriday.setTextColor(white!!)}
            6 -> {binding.mainFragment2ClDayContainerSaturday.setBackgroundColor(darkBlue)
                  binding.mainFragment2TvDaySaturday.setTextColor(white!!)}
        }
    }

    private fun setDailyWorkoutPlans(plans: List<GetDataItem>) {
        val adapter = DailyWorkoutPlanAdapter()
        adapter.submitList(plans)
        binding.mainFragment2RvWorkoutPlans.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDay(): String {
        val currentDate = LocalDate.now()
        return currentDate.dayOfWeek.toString()
    }
}
package com.example.capstoneapp.ui.Feature02

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneapp.R
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.databinding.FragmentFeature02Binding
import com.example.capstoneapp.ui.Feature02.WorkoutPreference.WorkoutPreferenceActivity
import com.example.capstoneapp.viewmodel.Feature02.Feature02ViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

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
                if (shouldFinishSequentially) {

                }
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

    private fun onDaySelection(plans: List<GetDataItem>) {
        var filteredPlans: List<GetDataItem> = emptyList()
        binding.mainFragment2ClDayContainerSunday.setOnClickListener {
            filteredPlans = plans.filter { it.days!!.contains(0) }
            setDailyWorkoutPlans(filteredPlans)
            setDayUI(0)
        }

        binding.mainFragment2ClDayContainerMonday.setOnClickListener {
            filteredPlans = plans.filter { it.days!!.contains(1) }
            setDailyWorkoutPlans(filteredPlans)
            setDayUI(1)
        }

        binding.mainFragment2ClDayContainerTuesday.setOnClickListener {
            filteredPlans = plans.filter { it.days!!.contains(2) }
            setDailyWorkoutPlans(filteredPlans)
            setDayUI(2)
        }

        binding.mainFragment2ClDayContainerWednesday.setOnClickListener {
            filteredPlans = plans.filter { it.days!!.contains(3) }
            setDailyWorkoutPlans(filteredPlans)
            setDayUI(3)
        }

        binding.mainFragment2ClDayContainerThursday.setOnClickListener {
            filteredPlans = plans.filter { it.days!!.contains(4) }
            setDailyWorkoutPlans(filteredPlans)
            setDayUI(4)
        }

        binding.mainFragment2ClDayContainerFriday.setOnClickListener {
            filteredPlans = plans.filter { it.days!!.contains(5) }
            setDailyWorkoutPlans(filteredPlans)
            setDayUI(5)
        }

        binding.mainFragment2ClDayContainerSaturday.setOnClickListener {
            filteredPlans = plans.filter { it.days!!.contains(6) }
            setDailyWorkoutPlans(filteredPlans)
            setDayUI(6)
        }
    }

    private fun setDayUI(dayIndex: Int) {
        setClickedOffDayUI(currentDay)
        currentDay = dayIndex
        setClickedOnDayUI(currentDay)
    }

    private fun setClickedOnDayUI(dayIndex: Int) {
        val lightBlue = Color.parseColor("#2970FF")
        when (dayIndex) {
            0 -> binding.mainFragment2ClDayContainerSunday.setBackgroundColor(lightBlue)
            1 -> binding.mainFragment2ClDayContainerMonday.setBackgroundColor(lightBlue)
            2 -> binding.mainFragment2ClDayContainerTuesday.setBackgroundColor(lightBlue)
            3 -> binding.mainFragment2ClDayContainerWednesday.setBackgroundColor(lightBlue)
            4 -> binding.mainFragment2ClDayContainerThursday.setBackgroundColor(lightBlue)
            5 -> binding.mainFragment2ClDayContainerFriday.setBackgroundColor(lightBlue)
            6 -> binding.mainFragment2ClDayContainerSaturday.setBackgroundColor(lightBlue)
        }
    }

    private fun setClickedOffDayUI(dayIndex: Int) {
        val darkBlue = Color.parseColor("#223767")
        when (dayIndex) {
            0 -> binding.mainFragment2ClDayContainerSunday.setBackgroundColor(darkBlue)
            1 -> binding.mainFragment2ClDayContainerMonday.setBackgroundColor(darkBlue)
            2 -> binding.mainFragment2ClDayContainerTuesday.setBackgroundColor(darkBlue)
            3 -> binding.mainFragment2ClDayContainerWednesday.setBackgroundColor(darkBlue)
            4 -> binding.mainFragment2ClDayContainerThursday.setBackgroundColor(darkBlue)
            5 -> binding.mainFragment2ClDayContainerFriday.setBackgroundColor(darkBlue)
            6 -> binding.mainFragment2ClDayContainerSaturday.setBackgroundColor(darkBlue)
        }
    }

    private fun setDailyWorkoutPlans(plans: List<GetDataItem>) {
        val adapter = DailyWorkoutPlanAdapter()
        adapter.submitList(plans)
        binding.mainFragment2RvWorkoutPlans.adapter = adapter
    }
}
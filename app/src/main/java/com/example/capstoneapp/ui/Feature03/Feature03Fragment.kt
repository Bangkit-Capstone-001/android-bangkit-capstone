package com.example.capstoneapp.ui.Feature03

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.FragmentFeature03Binding
import com.example.capstoneapp.viewmodel.MainViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlin.math.roundToInt

class Feature03Fragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFeature03Binding.inflate(inflater, container, false)

        setupAction(binding)
        observeViewModel(binding)
        return binding.root
    }

    private fun setupAction(binding: FragmentFeature03Binding) {
        binding.buttonCamera.setOnClickListener {
            startActivity(Intent(activity, PredictActivity::class.java))
        }
        binding.buttonAdd.setOnClickListener {
            startActivity(Intent(activity, AddFoodActivity::class.java))
        }

        binding.rvFood.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFood.adapter = FoodAdapter()
    }

    private fun observeViewModel(binding: FragmentFeature03Binding) {
        mainViewModel.userPlan.observe(viewLifecycleOwner) { resp ->
            resp?.let {
                if (it.status == 200) {
                    generateChart(binding, it.data?.calorie!!, it.data?.calorieEaten!!, it.data?.remainingCalories!!)
                    generateGoalTarget(binding, it.data?.goal!!, it.data?.currentWeight!!, it.data?.weightTarget!!, it.data?.duration!!)
                }
            }
        }
        mainViewModel.randomFood.observe(viewLifecycleOwner) { res ->
            if (res.status == 200) {
                val adapter = FoodAdapter()
                adapter.submitList(res.data)
                binding.rvFood.adapter = adapter
            }
        }
    }

    private fun generateGoalTarget(binding: FragmentFeature03Binding, goal: String, currentWeight: Int, targetWeight: Int, duration: Int) {
        if (goal == "weightGain") {
            binding.tvGoalTarget.text = "${currentWeight}kg → ${currentWeight+targetWeight}kg ($duration days)"
        } else if (goal == "weightLoss") {
            binding.tvGoalTarget.text = "${currentWeight}kg → ${currentWeight-targetWeight}kg ($duration days)"
        } else {
            binding.tvGoalTarget.text = "Maintain your ${currentWeight}kg of weight"
        }
    }

    private fun generateChart(binding: FragmentFeature03Binding, total: Float, cons: Float, rem: Float) {
        val calVariables: ArrayList<PieEntry> = ArrayList()
        calVariables.add(PieEntry(cons, "Consumed"))
        calVariables.add(PieEntry(rem, "Needs"))

        val colors = ArrayList<Int>()
        context?.let { ContextCompat.getColor(it, R.color.mediumBlue) }?.let { colors.add(it) }
        context?.let { ContextCompat.getColor(it, R.color.paleBlue) }?.let { colors.add(it) }

        val pieData = PieDataSet(calVariables, "")
        pieData.colors = colors
        pieData.valueTextSize = 16f

        binding.pieChart.legend.isEnabled = false

        val data = PieData(pieData)
        binding.pieChart.data = data
        binding.pieChart.centerText = "You need \ntotal: ${total.roundToInt()} cal"
        binding.pieChart.setCenterTextSize(15f)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.animateY(1000)
    }

    companion object {}
}
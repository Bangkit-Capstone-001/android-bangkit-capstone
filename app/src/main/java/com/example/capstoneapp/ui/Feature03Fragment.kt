package com.example.capstoneapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.FragmentFeature03Binding
import com.example.capstoneapp.helper.attrToActivity
import com.example.capstoneapp.helper.attrToGoal
import com.example.capstoneapp.viewmodel.MainViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

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
        observeViewModel(binding)
        binding.buttonCamera.setOnClickListener {
            startActivity(Intent(activity, PredictActivity::class.java))
        }

        return binding.root
    }

    private fun observeViewModel(binding: FragmentFeature03Binding) {
        mainViewModel.userPlan.observe(viewLifecycleOwner) { resp ->
            resp?.let {
                if (it.status == 200) {
                    generateChart(binding, it.data?.calorie!!, it.data?.calorieEaten!!)
                }
            }
        }
    }

    private fun generateChart(binding: FragmentFeature03Binding, total: Float, cons: Float) {
        val calVariables: ArrayList<PieEntry> = ArrayList()
        calVariables.add(PieEntry(cons, "Consumed"))
        calVariables.add(PieEntry(total-cons, "Needs"))

        val colors = ArrayList<Int>()
        context?.let { ContextCompat.getColor(it, R.color.mediumBlue) }?.let { colors.add(it) }
        context?.let { ContextCompat.getColor(it, R.color.paleBlue) }?.let { colors.add(it) }

        val pieData = PieDataSet(calVariables, "")
        pieData.colors = colors
        pieData.valueTextSize = 16f

        binding.pieChart.legend.isEnabled = false

        val data = PieData(pieData)
        binding.pieChart.data = data
        binding.pieChart.centerText = "You need \ntotal: ${total.toInt()} cal"
        binding.pieChart.setCenterTextSize(15f)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.animateY(1000)
    }

    companion object {}
}
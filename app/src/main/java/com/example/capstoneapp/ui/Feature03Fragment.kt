package com.example.capstoneapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.FragmentFeature03Binding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class Feature03Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFeature03Binding.inflate(inflater, container, false)
        generateChart(binding)

        return binding.root
    }

    private fun generateChart(binding: FragmentFeature03Binding) {
        val calVariables: ArrayList<PieEntry> = ArrayList()
        calVariables.add(PieEntry(20f, "Consumed"))
        calVariables.add(PieEntry(100f, "Needs"))

        val colors = ArrayList<Int>()
        context?.let { ContextCompat.getColor(it, R.color.mediumBlue) }?.let { colors.add(it) }
        context?.let { ContextCompat.getColor(it, R.color.paleBlue) }?.let { colors.add(it) }

        val pieData = PieDataSet(calVariables, "")
        pieData.colors = colors
        pieData.valueTextSize = 16f

        binding.pieChart.legend.isEnabled = false

        val data = PieData(pieData)
        binding.pieChart.data = data
        binding.pieChart.centerText = "Your calories \nTotal: 3500 cal"
        binding.pieChart.description.isEnabled = false
        binding.pieChart.animateY(1000)
    }

    companion object {}
}
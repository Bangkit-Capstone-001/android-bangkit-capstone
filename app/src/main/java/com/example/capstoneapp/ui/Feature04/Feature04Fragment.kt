package com.example.capstoneapp.ui.Feature04

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.capstoneapp.R
import com.example.capstoneapp.data.response.GetTrackerDataItem
import com.example.capstoneapp.data.response.PostTrackerResponse
import com.example.capstoneapp.databinding.FragmentFeature04Binding
import com.example.capstoneapp.viewmodel.Feature04.Feature04ViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class Feature04Fragment : Fragment() {

    private val viewModel: Feature04ViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private var _binding: FragmentFeature04Binding? = null
    private val binding get() = _binding!!
    private lateinit var lineChart: LineChart
    private var theDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeature04Binding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart = binding.fragmentFeature04LcLineChart

        viewModel.getTrackerResponse.observe(viewLifecycleOwner) { response ->
            if (response.status == 200) {
                Log.d("TRACKER DATA", response.data.toString())
                setLineChartData(response.data ?: emptyList())
            }
        }

        viewModel.postTrackerResponse.observe(viewLifecycleOwner) { response ->
            if (response.status == 200 || response.status == 500) {
                try {
                    if (response.status == 200) {
                        getTrackerData()
                        setSuccess()
                    } else {
                        showErrorDialog(response.message.toString())
                        resetState()
                    }
                } catch (e: Exception) {
                    showErrorDialog(e.message.toString())
                    resetState()
                }
            }
        }

        getTrackerData()
        onSaveClicked()
        setAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTrackerData() {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                val token = "Bearer ${user.token}"
                viewModel.getTrackerData(token)
            }
        }
    }

    private fun setSuccess() {
        showSuccessDialog()
        binding.fragmentFeature04TieWeightFormInputEditText.setText("")
        binding.fragmentFeature04TvDateSelected.text = ""
        theDate = null
    }

    private fun resetState() {
        viewModel._postTrackerResponse.postValue(
            PostTrackerResponse(
                status = 0,
                message = null
            )
        )
    }

    private fun setAction() {
        binding.fragmentFeature04IvCalendar.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setTitleText("Select date")
                    .setTheme(R.style.CustomDatePicker)
                    .build()

            datePicker.show(parentFragmentManager, "SelectDate")

            datePicker.addOnPositiveButtonClickListener { selectedDate ->
                Log.d("Selected Date 1", selectedDate.toString())
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.timeInMillis = selectedDate

                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = format.format(calendar.time)

                Log.d("Selected Date 2", formattedDate.toString())

                theDate = formattedDate.toString()
                binding.fragmentFeature04TvDateSelected.text = theDate
            }
        }
    }

    private fun setLineChartData(dataItems: List<GetTrackerDataItem?>) {
        val entries = dataItems.mapIndexed { index, dataItem ->
            Entry(index.toFloat(), dataItem?.weight ?: 0f)
        }

        val dates = dataItems.map {
            it?.date ?: ""
        }

        val lineDataSet = LineDataSet(entries, "").apply {
            axisDependency = YAxis.AxisDependency.LEFT
            setDrawFilled(true)
            setDrawCircles(true)
            lineWidth = 2f
            circleRadius = 4f
            fillColor = resources.getColor(R.color.paleBlue, null)
            color = resources.getColor(R.color.mediumBlue, null)
            setCircleColor(resources.getColor(R.color.contrastYellow, null))
            valueTextSize = 8f
        }

        val lineData = LineData(lineDataSet)
        lineChart.data = lineData
        lineChart.invalidate()

        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            granularity = 1f
            setDateValueFormatter(dates)
        }

        lineChart.axisLeft.apply {
            setDrawGridLines(false)
        }

        lineChart.axisRight.isEnabled = false
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false

        lineChart.xAxis.setLabelCount(dates.size, true)
        lineChart.xAxis.textSize = 6f
        lineChart.axisLeft.textSize = 8f
    }

    private fun XAxis.setDateValueFormatter(dates: List<String>) {
        val interval = when {
            dates.size <= 5 -> 1
            dates.size <= 10 -> 3
            else -> dates.size / 2
        }

        valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return when (interval) {
                    1 -> dates.getOrNull(index) ?: ""
                    3 -> if (index % 3 == 0) dates.getOrNull(index) ?: "" else ""
                    else -> {
                        val middleIndex = dates.size / 2
                        when (index) {
                            0 -> dates.first()
                            middleIndex -> dates[middleIndex]
                            dates.size - 1 -> dates.last()
                            else -> ""
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onSaveClicked() {
        binding.fragmentFeature04BtnSaveWeight.setOnClickListener {
            val weight = binding.fragmentFeature04TieWeightFormInputEditText.text.toString()
            if (weight.isEmpty() || theDate == null) {
                showErrorDialog("Make sure your data is completed.")
            } else {
                viewModel.getSession().observe(viewLifecycleOwner) { user ->
                    if (user.isLogin && theDate != null) {
                        val token = "Bearer ${user.token}"
                        viewModel.postTracker(token, theDate!!, weight.toFloat())
                    }
                }
            }
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.warning)
            setMessage(message)
            setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.success)
            setMessage("Your weight is successfully tracked.")
            setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
                resetState()
            }
            create()
            show()
        }
    }

    companion object {}
}
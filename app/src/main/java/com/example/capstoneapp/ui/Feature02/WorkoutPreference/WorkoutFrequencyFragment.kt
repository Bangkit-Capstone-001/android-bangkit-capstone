package com.example.capstoneapp.ui.Feature02.WorkoutPreference

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.FragmentWorkoutFrequencyBinding
import com.example.capstoneapp.databinding.FragmentWorkoutLevelBinding

class WorkoutFrequencyFragment : Fragment() {

    private var _binding: FragmentWorkoutFrequencyBinding? = null
    private val binding get() = _binding!!
    private var listener: OnValueTransferListener? = null
    // Make Sure Hari : [0, 1, 2, 3, 4, 5, 6]
    private val days = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutFrequencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnValueTransferListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnStringTransferListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSelection()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onSelection() {
        binding.workoutFrequencyClDayContainerSunday.setOnClickListener {
            setDays(0)
        }

        binding.workoutFrequencyClDayContainerMonday.setOnClickListener {
            setDays(1)
        }

        binding.workoutFrequencyClDayContainerTuesday.setOnClickListener {
            setDays(2)
        }

        binding.workoutFrequencyClDayContainerWednesday.setOnClickListener {
            setDays(3)
        }

        binding.workoutFrequencyClDayContainerThursday.setOnClickListener {
            setDays(4)
        }

        binding.workoutFrequencyClDayContainerFriday.setOnClickListener {
            setDays(5)
        }

        binding.workoutFrequencyClDayContainerSaturday.setOnClickListener {
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
        if (days.isNotEmpty()) {
            binding.workoutFrequencyClSelectWorkoutButton.visibility = View.VISIBLE
        } else {
            binding.workoutFrequencyClSelectWorkoutButton.visibility = View.GONE
        }
    }

    private fun setClickedOnDayUI(dayIndex: Int) {
        val lightBlue = Color.parseColor("#2970FF")
        when (dayIndex) {
            0 -> binding.workoutFrequencyClDayContainerSunday.setBackgroundColor(lightBlue)
            1 -> binding.workoutFrequencyClDayContainerMonday.setBackgroundColor(lightBlue)
            2 -> binding.workoutFrequencyClDayContainerTuesday.setBackgroundColor(lightBlue)
            3 -> binding.workoutFrequencyClDayContainerWednesday.setBackgroundColor(lightBlue)
            4 -> binding.workoutFrequencyClDayContainerThursday.setBackgroundColor(lightBlue)
            5 -> binding.workoutFrequencyClDayContainerFriday.setBackgroundColor(lightBlue)
            6 -> binding.workoutFrequencyClDayContainerSaturday.setBackgroundColor(lightBlue)
        }
    }

    private fun setClickedOffDayUI(dayIndex: Int) {
        val darkBlue = Color.parseColor("#223767")
        when (dayIndex) {
            0 -> binding.workoutFrequencyClDayContainerSunday.setBackgroundColor(darkBlue)
            1 -> binding.workoutFrequencyClDayContainerMonday.setBackgroundColor(darkBlue)
            2 -> binding.workoutFrequencyClDayContainerTuesday.setBackgroundColor(darkBlue)
            3 -> binding.workoutFrequencyClDayContainerWednesday.setBackgroundColor(darkBlue)
            4 -> binding.workoutFrequencyClDayContainerThursday.setBackgroundColor(darkBlue)
            5 -> binding.workoutFrequencyClDayContainerFriday.setBackgroundColor(darkBlue)
            6 -> binding.workoutFrequencyClDayContainerSaturday.setBackgroundColor(darkBlue)
        }
    }
}
package com.example.capstoneapp.ui.Feature02.WorkoutPreference

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.FragmentWorkoutFrequencyBinding
import com.example.capstoneapp.databinding.FragmentWorkoutLevelBinding

class WorkoutFrequencyFragment : Fragment() {

    private var _binding: FragmentWorkoutFrequencyBinding? = null
    private val binding get() = _binding!!
    private var listener: OnArrayValueTransferListener? = null
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
        if (context is OnArrayValueTransferListener) {
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
        setAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAction() {
        binding.workoutFrequencyClSelectWorkoutButton.setOnClickListener {
            listener?.onArrayValueTransferListener("WorkoutFrequency", days)
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
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
        val lightBlue = context?.let { ContextCompat.getColor(it, R.color.paleBlue) }
        val darkBlue = context?.let { ContextCompat.getColor(it, R.color.darkBlue) }
        when (dayIndex) {
            0 -> {binding.workoutFrequencyClDayContainerSunday.setBackgroundColor(lightBlue!!)
                binding.workoutFrequencyTvDaySunday.setTextColor(darkBlue!!)}
            1 -> {binding.workoutFrequencyClDayContainerMonday.setBackgroundColor(lightBlue!!)
                binding.workoutFrequencyTvDayMonday.setTextColor(darkBlue!!)}
            2 -> {binding.workoutFrequencyClDayContainerTuesday.setBackgroundColor(lightBlue!!)
                binding.workoutFrequencyTvDayTuesday.setTextColor(darkBlue!!)}
            3 -> {binding.workoutFrequencyClDayContainerWednesday.setBackgroundColor(lightBlue!!)
                binding.workoutFrequencyTvDayWednesday.setTextColor(darkBlue!!)}
            4 -> {binding.workoutFrequencyClDayContainerThursday.setBackgroundColor(lightBlue!!)
                binding.workoutFrequencyTvDayThursday.setTextColor(darkBlue!!)}
            5 -> {binding.workoutFrequencyClDayContainerFriday.setBackgroundColor(lightBlue!!)
                binding.workoutFrequencyTvDayFriday.setTextColor(darkBlue!!)}
            6 -> {binding.workoutFrequencyClDayContainerSaturday.setBackgroundColor(lightBlue!!)
                binding.workoutFrequencyTvDaySaturday.setTextColor(darkBlue!!)}
        }
    }

    private fun setClickedOffDayUI(dayIndex: Int) {
        val white = context?.let { ContextCompat.getColor(it, R.color.white) }
        val darkBlue = Color.parseColor("#223767")
        when (dayIndex) {
            0 -> {binding.workoutFrequencyClDayContainerSunday.setBackgroundColor(darkBlue)
                binding.workoutFrequencyTvDaySunday.setTextColor(white!!)}
            1 -> {binding.workoutFrequencyClDayContainerMonday.setBackgroundColor(darkBlue)
                binding.workoutFrequencyTvDayMonday.setTextColor(white!!)}
            2 -> {binding.workoutFrequencyClDayContainerTuesday.setBackgroundColor(darkBlue)
                binding.workoutFrequencyTvDayTuesday.setTextColor(white!!)}
            3 -> {binding.workoutFrequencyClDayContainerWednesday.setBackgroundColor(darkBlue)
                binding.workoutFrequencyTvDayWednesday.setTextColor(white!!)}
            4 -> {binding.workoutFrequencyClDayContainerThursday.setBackgroundColor(darkBlue)
                binding.workoutFrequencyTvDayThursday.setTextColor(white!!)}
            5 -> {binding.workoutFrequencyClDayContainerFriday.setBackgroundColor(darkBlue)
                binding.workoutFrequencyTvDayFriday.setTextColor(white!!)}
            6 -> {binding.workoutFrequencyClDayContainerSaturday.setBackgroundColor(darkBlue)
                binding.workoutFrequencyTvDaySaturday.setTextColor(white!!)}
        }
    }
}
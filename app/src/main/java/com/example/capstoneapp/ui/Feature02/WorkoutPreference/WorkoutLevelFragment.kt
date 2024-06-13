package com.example.capstoneapp.ui.Feature02.WorkoutPreference

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstoneapp.R
import com.example.capstoneapp.data.WorkoutPreference
import com.example.capstoneapp.databinding.FragmentWorkoutLevelBinding

class WorkoutLevelFragment() : Fragment() {

    private var _binding: FragmentWorkoutLevelBinding? = null
    private val binding get() = _binding!!
    private var listener: OnValueTransferListener? = null

    interface OnValueTransferListener {
        fun onValueTransfer(value: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutLevelBinding.inflate(inflater, container, false)
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
        var az: String? = null
    }

    private fun onSelection() {
        binding.workoutLevelClPreferences1.setOnClickListener {
            processSelection("Beginner")
        }

        binding.workoutLevelClPreferences2.setOnClickListener {
            Log.d("CLICK VALUE", "Intermediate")
            processSelection("Intermediate")
        }

        binding.workoutLevelClPreferences3.setOnClickListener {
            Log.d("CLICK VALUE", "Advance")
            processSelection("Advance")
        }
    }

    private fun processSelection(clickValue: String) {
        listener?.onValueTransfer(clickValue)
        parentFragmentManager.beginTransaction().remove(this).commit()

    }
}
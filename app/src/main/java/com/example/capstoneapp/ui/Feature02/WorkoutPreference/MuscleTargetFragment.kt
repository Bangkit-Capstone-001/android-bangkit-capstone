package com.example.capstoneapp.ui.Feature02.WorkoutPreference

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneapp.databinding.FragmentMuscleTargetBinding

class MuscleTargetFragment : Fragment() {

    private var _binding: FragmentMuscleTargetBinding? = null
    private val binding get() = _binding!!
    private var listener: OnValueTransferListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMuscleTargetBinding.inflate(inflater, container, false)
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
        binding.muscleTargetClPreferences1.setOnClickListener {
            processSelection("Upper")
        }

        binding.muscleTargetClPreferences2.setOnClickListener {
            processSelection("Lower")
        }

        binding.muscleTargetClPreferences3.setOnClickListener {
            processSelection("Full")
        }
    }

    private fun processSelection(clickValue: String) {
        listener?.onValueTransfer("MuscleTarget", clickValue)
        parentFragmentManager.beginTransaction().remove(this).commit()
    }
}
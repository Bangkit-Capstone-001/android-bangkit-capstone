package com.example.capstoneapp.ui.Feature02.WorkoutPreference

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneapp.databinding.FragmentExerciseOptionBinding

class ExerciseOptionFragment : Fragment() {

    private var _binding: FragmentExerciseOptionBinding? = null
    private val binding get() = _binding!!
    private var listener: OnValueTransferListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseOptionBinding.inflate(inflater, container, false)
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

    private fun onSelection() {
        binding.exerciseOptionClPreferences1.setOnClickListener {
            processSelection("Body Only")
        }

        binding.exerciseOptionClPreferences2.setOnClickListener {
            processSelection("Minimal Equipments")
        }

        binding.exerciseOptionClPreferences3.setOnClickListener {
            processSelection("Full Gym Equipment")
        }
    }

    private fun processSelection(clickValue: String) {
        listener?.onValueTransfer("ExerciseOption", clickValue)
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

}
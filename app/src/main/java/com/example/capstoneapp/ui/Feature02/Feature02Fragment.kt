package com.example.capstoneapp.ui.Feature02

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.FragmentFeature02Binding
import com.example.capstoneapp.ui.Feature02.WorkoutPreference.WorkoutPreferenceActivity

class Feature02Fragment : Fragment() {

    private var _binding: FragmentFeature02Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeature02Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAction()
    }

    private fun setAction() {
        binding.mainFragment2IvAddWorkoutPlanButton.setOnClickListener {
            startActivity(Intent(activity, WorkoutPreferenceActivity::class.java))
        }
    }
}
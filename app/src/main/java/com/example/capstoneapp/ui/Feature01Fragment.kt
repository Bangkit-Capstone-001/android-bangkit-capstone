package com.example.capstoneapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.FragmentFeature01Binding

class Feature01Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFeature01Binding.inflate(inflater, container, false)
        val mainBinding = (requireActivity() as MainActivity).binding
        val bottomNavigationView = mainBinding.bottomNavigation

        binding.buttonWorkout.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.bottom_2
        }

        return binding.root
    }

    companion object {}
}
package com.example.capstoneapp.ui

import android.content.Intent
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

        binding.buttonSettings.setOnClickListener{
            startActivity(Intent(activity, ProfileActivity::class.java))
        }

        binding.buttonWorkout.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.bottom_2
        }

        binding.buttonTrackfood.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.bottom_3
        }

        binding.buttongroupFood.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_breakfast -> binding.tvDummy1.text = getString(R.string.breakfast)
                    R.id.button_lunch -> binding.tvDummy1.text = getString(R.string.lunch)
                    R.id.button_dinner -> binding.tvDummy1.text = getString(R.string.dinner)
                }
            }
        }

        return binding.root
    }

    companion object {}
}
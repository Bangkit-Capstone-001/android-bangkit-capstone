package com.example.capstoneapp.ui.Feature04

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.FragmentFeature04Binding
import com.example.capstoneapp.viewmodel.Feature04.Feature04ViewModel
import com.example.capstoneapp.viewmodel.MainViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class Feature04Fragment : Fragment() {

    private val viewModel: Feature04ViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private var _binding: FragmentFeature04Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeature04Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTrackerResponse.observe(viewLifecycleOwner) { response ->
            if (response.status == 200) {
                Log.d("TRACKER DATA", response.data.toString())
            }
        }

        getTrackerData()
    }

    private fun getTrackerData() {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                val token = "Bearer ${user.token}"
                viewModel.getTrackerData(token)
            }
        }
    }

    companion object {}
}
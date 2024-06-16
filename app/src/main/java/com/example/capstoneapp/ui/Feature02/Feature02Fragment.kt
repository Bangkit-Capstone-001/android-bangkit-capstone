package com.example.capstoneapp.ui.Feature02

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneapp.R
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.databinding.FragmentFeature02Binding
import com.example.capstoneapp.ui.Feature02.WorkoutPreference.WorkoutPreferenceActivity
import com.example.capstoneapp.viewmodel.Feature02.Feature02ViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class Feature02Fragment : Fragment() {

    private var _binding: FragmentFeature02Binding? = null
    private val binding get() = _binding!!
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private val viewModel: Feature02ViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeature02Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allPlanLayoutManager = LinearLayoutManager(requireContext())
        binding.mainFragment2RvMyWorkoutPlans.layoutManager = allPlanLayoutManager

        val dailyPlanLayoutManager = LinearLayoutManager(requireContext())
        binding.mainFragment2RvWorkoutPlans.layoutManager = dailyPlanLayoutManager

        setAction()

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val shouldFinishSequentially = result.data?.getBooleanExtra("shouldFinishSequentially", false) ?: false
                if (shouldFinishSequentially) {
                    viewModel.getSession().observe(viewLifecycleOwner) { user ->
                        if (user.isLogin) {
                            val token = "Bearer ${user.token}"
                            viewModel.getWorkoutPlans(token)
                        }
                    }
                }
            }
        }

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                val token = "Bearer ${user.token}"
                viewModel.getWorkoutPlans(token)
            }
        }

        viewModel.workoutPlans.observe(viewLifecycleOwner) { plans ->
            // set Adapter ada 2 :
            setMyWorkoutPlans(plans)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAction() {
        binding.mainFragment2IvAddWorkoutPlanButton.setOnClickListener {
            val intent = Intent(requireContext(), WorkoutPreferenceActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private fun setMyWorkoutPlans(plans: List<GetDataItem>) {
        val adapter = WorkoutPlanAdapter()
        adapter.submitList(plans)
        binding.mainFragment2RvMyWorkoutPlans.adapter = adapter
    }
}
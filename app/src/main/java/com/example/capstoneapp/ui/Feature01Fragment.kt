package com.example.capstoneapp.ui

//import androidx.activity.viewModels
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.FragmentFeature01Binding
import com.example.capstoneapp.helper.attrToActivity
import com.example.capstoneapp.helper.attrToGoal
import com.example.capstoneapp.viewmodel.MainViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class Feature01Fragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFeature01Binding.inflate(inflater, container, false)
        val mainBinding = (requireActivity() as MainActivity).binding
        val bottomNavigationView = mainBinding.bottomNavigation

        binding.buttonSettings.setOnClickListener {
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
        observeViewModel(binding)
        return binding.root
    }

    private fun observeViewModel(binding: FragmentFeature01Binding) {
        mainViewModel.userProfile.observe(viewLifecycleOwner) { resp ->
            resp?.let {
                if (it.status == 200) {
                    binding.tvName.text = it.data?.name
                    val weightT = it.data?.currentWeight ?: 0
                    val bmiT = it.data?.currentHeight ?: 0
                    val heightT = it.data?.currentHeight ?: 0
                    binding.tvUserinfo.text = "$weightT kg | $heightT cm | BMI: $bmiT"
                    binding.tvGoalValue.text = attrToGoal(it.data?.goal ?: "None")
                    binding.tvActValue.text = attrToActivity(it.data?.activityLevel ?: "None")
                    // Check data completion
                    if (it.data?.goal == null) {
                        showWarning()
                    }
                }
            }
        }
    }

    private fun showWarning() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("One more step!")
            setMessage("To start using this app, please complete your data and set up your goals. ")
            setPositiveButton(R.string.ok) { _, _ ->
                val intent = Intent(requireActivity(), ProfileActivity::class.java)
                startActivity(intent)
            }
            create()
            show()
        }
    }

    companion object {}
}
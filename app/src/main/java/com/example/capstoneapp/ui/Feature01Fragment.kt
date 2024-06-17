package com.example.capstoneapp.ui

//import androidx.activity.viewModels
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneapp.R
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.data.pref.UserPreference
import com.example.capstoneapp.databinding.ActivityMainBinding
import com.example.capstoneapp.databinding.FragmentFeature01Binding
import com.example.capstoneapp.helper.FoodAdapter
import com.example.capstoneapp.helper.FoodHistAdapter
import com.example.capstoneapp.helper.attrToActivity
import com.example.capstoneapp.helper.attrToGoal
import com.example.capstoneapp.helper.listFood
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

        var _token = ""
        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                _token = "Bearer ${user.token}"
                setupAction(binding, mainBinding, _token)
            }
        }

        observeViewModel(binding)
        return binding.root
    }

    private fun setupAction(binding: FragmentFeature01Binding, mainBinding: ActivityMainBinding,
    token: String) {
        val bottomNavigationView = mainBinding.bottomNavigation

        binding.rvFood.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFood.adapter = FoodHistAdapter()

        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(activity, ProfileActivity::class.java))
        }

        binding.buttonWorkout.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.bottom_2
        }

        binding.buttonTrackfood.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.bottom_3
        }

        binding.buttongroupFood.check(R.id.button_breakfast)
        mainViewModel.getTodaysFood(token, "breakfast")

        binding.buttongroupFood.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_breakfast -> mainViewModel.getTodaysFood(token, "breakfast")
                    R.id.button_lunch -> mainViewModel.getTodaysFood(token, "lunch")
                    R.id.button_dinner -> mainViewModel.getTodaysFood(token, "dinner")
                }
            }
        }
    }

    private fun observeViewModel(binding: FragmentFeature01Binding) {
        mainViewModel.userProfile.observe(viewLifecycleOwner) { resp ->
            resp?.let {
                if (it.status == 200) {
                    binding.tvName.text = it.data?.name
                    val weightT = it.data?.currentWeight ?: 0
                    val bmiT = it.data?.bmi ?: 0
                    val heightT = it.data?.currentHeight ?: 0
                    binding.tvUserinfo.text = "$weightT kg | $heightT cm | BMI: $bmiT"
                    binding.tvGoalValue.text = attrToGoal(it.data?.goal ?: "None")
                    binding.tvActValue.text = attrToActivity(it.data?.activityLevel ?: "None")
                    // Check data completion
                    if (it.data?.goal == null) {
                        showFillDataWarning()
                    }
                }
            }
        }
        mainViewModel.userError.observe(viewLifecycleOwner) { userError ->
            if (userError) {
                showErrorLogin()
            }
        }
        mainViewModel.histFood.observe(viewLifecycleOwner) { hist ->
            if (hist.status == 200) {
                val adapter = FoodHistAdapter()
                adapter.submitList(hist.data)
                binding.rvFood.adapter = adapter
            }
        }
    }

    private fun showFillDataWarning() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(R.string.one_more)
            setMessage(R.string.one_more_desc)
            setPositiveButton(R.string.ok) { _, _ ->
                val intent = Intent(requireActivity(), ProfileActivity::class.java)
                startActivity(intent)
            }
            create()
            show()
        }
    }

    private fun showErrorLogin() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(R.string.token_unknown)
            setMessage(R.string.login_again)
            setPositiveButton(R.string.ok) { _, _ ->
                mainViewModel.logout(null)
            }
            setCancelable(false)
            create()
            show()
        }
    }

    companion object {}
}
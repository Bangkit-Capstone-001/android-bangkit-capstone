package com.example.capstoneapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivityProfileBinding
import com.example.capstoneapp.viewmodel.MainViewModel
import com.example.capstoneapp.viewmodel.ProfileViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var _token = ""

        profileViewModel.getSession().observe(this) { user ->
            _token = "Bearer ${user.token}"
            setupAction(_token)
        }
        showLoading(false)
        observeViewModel()
    }

    private fun observeViewModel() {
        profileViewModel.message.observe(this) { message ->
            if (profileViewModel.isError.value != true) {
                showSuccessDialog()
            } else {
                showErrorDialog(getString(R.string.unknown_error))
            }
        }

        profileViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupAction(t: String) {
        binding.buttonLogout.setOnClickListener {
            mainViewModel.logout()
            finish()
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.edName.text.toString()
            val age = binding.edAge.text.toString()
            val gender = binding.edGender.text.toString()
            val height = binding.edHeight.text.toString()
            val weight = 0F
            val goal = binding.edGoal.text.toString()
            val activityLevel = binding.edAct.text.toString()

            if (validateInput(name, age, gender, height, weight, goal, activityLevel)) {

                val fAge = age.toInt()
                val fHeight = height.toFloat()

                profileViewModel.editProfile(
                    t, name, fAge, gender, fHeight, weight, goal, activityLevel
                )
            }
        }
    }

    private fun validateInput(
        name: String, age: String, gender: String, currentHeight: String, currentWeight: Float, goal: String, activityLevel: String
    ): Boolean {
        return when {
            name.isEmpty() -> {
                showErrorDialog("Name cannot be empty")
                false
            }

            age.isEmpty() -> {
                showErrorDialog("Age cannot be empty")
                false
            }

            gender.isEmpty() -> {
                showErrorDialog("Gender cannot be empty")
                false
            }

            currentHeight.isEmpty() -> {
                showErrorDialog("Height cannot be empty")
                false
            }

            goal.isEmpty() -> {
                showErrorDialog("Goal cannot be empty")
                false
            }

            activityLevel.isEmpty() -> {
                showErrorDialog("Activity level cannot be empty")
                false
            }

            else -> true
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.warning)
            setMessage(message)
            setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.success)
            setMessage(getString(R.string.edit_success))
            setPositiveButton(R.string.ok) { _, _ -> finish() }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

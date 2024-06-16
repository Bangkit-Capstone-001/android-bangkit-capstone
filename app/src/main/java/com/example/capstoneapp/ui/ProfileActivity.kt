package com.example.capstoneapp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivityProfileBinding
import com.example.capstoneapp.helper.activityToAttr
import com.example.capstoneapp.helper.attrToActivityDropdown
import com.example.capstoneapp.helper.attrToGoal
import com.example.capstoneapp.helper.goalToAttr
import com.example.capstoneapp.viewmodel.MainViewModel
import com.example.capstoneapp.viewmodel.ProfileViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.black)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var _token = ""

        profileViewModel.getSession().observe(this) { user ->
            if (user.token != "") {
                _token = "Bearer ${user.token}"
                setupAction(_token)
            }
        }

        setupView()
        showLoading(false)
        observeViewModel()
    }

    private fun observeViewModel() {
        mainViewModel.userProfile.observe(this) { resp ->
            if (resp.status == 200) {
                binding.edName.setText(resp.data?.name)
                binding.edAge.setText(resp.data?.age?.toString() ?: "")
                binding.edGender.setText(resp.data?.gender ?: "")
                binding.edHeight.setText(resp.data?.currentHeight?.toString() ?: "")
                binding.edWeight.setText(resp.data?.currentWeight?.let { it.roundToInt().toString() } ?: "")
                binding.edGoal.setText(resp.data?.goal?.let { attrToGoal(it) } ?: "")
                binding.edAct.setText(resp.data?.activityLevel?.let { attrToActivityDropdown(it) } ?: "")
            }
        }

        profileViewModel.message.observe(this) { message ->
            if (profileViewModel.isError.value != true &&
                profileViewModel.addPlanError.value != true &&
                profileViewModel.addWeightError.value != true
            ) {
                showSuccessDialog()
            } else {
                showErrorDialog(getString(R.string.unknown_error))
            }
        }

        profileViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupView() {
        val genderOptions = arrayOf("Male", "Female")
        var adapter = ArrayAdapter(this, R.layout.item_option, genderOptions)
        binding.edGender.setAdapter(adapter)

        val goalOptions = arrayOf("Weight Gain", "Weight Loss", "Maintain Body")
        adapter = ArrayAdapter(this, R.layout.item_option, goalOptions)
        binding.edGoal.setAdapter(adapter)

        val activityOptions = arrayOf(
            "Index 4 (Active): Moves a lot", "Index 3 (Moderate): Moves moderately",
            "Index 2 (Light): Moves a little", "Index 1 (Sedentary): Moves rarely"
        )
        adapter = ArrayAdapter(this, R.layout.item_option, activityOptions)
        binding.edAct.setAdapter(adapter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAction(t: String) {
        // Prefilled field
        mainViewModel.getProfile(t)

        binding.buttonLogout.setOnClickListener {
            mainViewModel.logout(t)
            finish()
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.edName.text.toString()
            val age = binding.edAge.text.toString()
            val gender = binding.edGender.text.toString()
            val height = binding.edHeight.text.toString()
            val weight = binding.edWeight.text.toString()
            val goal = binding.edGoal.text.toString()
            val activityLevel = binding.edAct.text.toString()
            val weightTarget = binding.edWeightTarget.text.toString()
            val duration = binding.edDuration.text.toString()

            if (validateInput(
                    name, age, gender, height, weight, goal, activityLevel,
                    weightTarget, duration
                )
            ) {

                val fAge = age.toInt()
                val fWeight = weight.toFloat()
                val fHeight = height.toFloat()
                val fGoal = goalToAttr(goal)
                val fAct = activityToAttr(activityLevel)
                val fWeightTarget = weightTarget.toFloat()
                val fDuration = duration.toInt()

                val currentDate = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val fDate = currentDate.format(formatter)

                profileViewModel.editProfile(
                    t, name, fAge, gender, fHeight, fWeight, fGoal, fAct
                )
                profileViewModel.addWeight(t, fDate, fWeight)

                profileViewModel.isError.observe(this) { err ->
                    if(!err) {
                        profileViewModel.addDietPlan(t, fWeightTarget, fDuration)
                    }
                }
            }
        }
    }

    private fun validateInput(
        name: String, age: String, gender: String, currentHeight: String, currentWeight: String,
        goal: String, activityLevel: String,
        weightTarget: String, duration: String

    ): Boolean {
        return when {
            name.isEmpty() -> {
                showErrorDialog("Name cannot be empty")
                false
            }

            name.length > 12 -> {
                showErrorDialog("Name should be at most 12 characters")
                false
            }

            age.isEmpty() -> {
                showErrorDialog("Age cannot be empty")
                false
            }

            age.toInt() <= 0 -> {
                showErrorDialog("Age must be grater than 0")
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

            currentHeight.toInt() <= 0 -> {
                showErrorDialog("Height must be greater than 0")
                false
            }

            currentWeight.isEmpty() -> {
                showErrorDialog("Weight cannot be empty")
                false
            }

            currentWeight.toInt() <= 0 -> {
                showErrorDialog("Weight must be greater than 0")
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

            weightTarget.isEmpty() -> {
                showErrorDialog("Weight target cannot be empty")
                false
            }

            weightTarget.toInt() < 0 -> {
                showErrorDialog("Weight target must be greater than 0")
                false
            }

            duration.isEmpty() -> {
                showErrorDialog("Duration target cannot be empty")
                false
            }

            duration.toInt() < 14 -> {
                showErrorDialog("Duration target must be minimal 14 days")
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
            setCancelable(false)
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

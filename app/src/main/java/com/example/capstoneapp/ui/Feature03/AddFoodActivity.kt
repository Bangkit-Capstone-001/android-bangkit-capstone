package com.example.capstoneapp.ui.Feature03

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivityAddFoodBinding
import com.example.capstoneapp.ui.MainActivity
import com.example.capstoneapp.viewmodel.Feature03.AddFoodViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class AddFoodActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddFoodBinding
    private val addFoodViewModel by viewModels<AddFoodViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodName = intent.getStringExtra("EXTRA_FOOD")
        var _token = ""

        addFoodViewModel.getSession().observe(this) { user ->
            _token = "Bearer ${user.token}"
            setupAction(_token)
        }

        showLoading(false)
        setupView(foodName)
        observeViewModel()
    }

    fun observeViewModel() {
        addFoodViewModel.addFoodError.observe(this) { err ->
            if (!err) {
                showSuccessDialog()
            }
        }

        addFoodViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    fun setupView(foodName: String?) {
        window.statusBarColor = getColor(R.color.black)
        val mealOptions = arrayOf("Breakfast", "Lunch", "Dinner")
        var adapter = ArrayAdapter(this, R.layout.item_option, mealOptions)
        binding.edMealtime.setAdapter(adapter)

        val foodOptions = listFood
        adapter = ArrayAdapter(this, R.layout.item_option, foodOptions)

        val ddFood = binding.edFood
        ddFood.setAdapter(adapter)
        ddFood.setOnItemClickListener { _, _, _, _ -> }
        ddFood.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val input = ddFood.text.toString()
                if (input !in foodOptions) {
                    ddFood.error = "Please select food from the list"
                    ddFood.setText("")
                }
            }
        }

        // Setup prefilled
        if (foodName != null) {
            ddFood.setText(foodName)
        }
    }

    fun setupAction(t: String) {
        binding.buttonSave.setOnClickListener() {
            val food = binding.edFood.text.toString()
            val mealtime = binding.edMealtime.text.toString()
            val quantity = binding.edQuantity.text.toString()

            if (validateInput(food, quantity, mealtime)) {
                val foodId = dictFood[food]
                val fQuantity = quantity.toFloat()
                val fMealType = mealtime.lowercase()

                if (foodId != null) {
                    addFoodViewModel.addFood(t, foodId, fQuantity, fMealType)
                }
            }
        }
    }

    fun validateInput(food: String, quantity: String, mealtime: String): Boolean {
        return when {
            food.isEmpty() -> {
                showErrorDialog("Food cannot be empty")
                false
            }

            quantity.isEmpty() -> {
                showErrorDialog("This cannot be empty")
                false
            }

            mealtime.isEmpty() -> {
                showErrorDialog("Mealtime cannot be empty")
                false
            }

            else -> {
                true
            }
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
            setMessage("Success add food for today!")
            setPositiveButton(R.string.ok) { _, _ ->
                val intent = Intent(this@AddFoodActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
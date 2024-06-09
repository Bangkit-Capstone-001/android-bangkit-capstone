package com.example.capstoneapp.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivityAddFoodBinding

class AddFoodActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    fun setupView() {
        val mealOptions = arrayOf("Breakfast", "Lunch", "Dinner")
        var adapter = ArrayAdapter(this, R.layout.item_option, mealOptions)
        binding.edMealtime.setAdapter(adapter)
    }
}
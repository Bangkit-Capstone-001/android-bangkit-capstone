package com.example.capstoneapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivityMainBinding
import com.example.capstoneapp.databinding.ActivityProfileBinding
import com.example.capstoneapp.viewmodel.MainViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        binding.buttonLogout.setOnClickListener {
            mainViewModel.logout()
            finish()
        }

        setContentView(binding.root)
    }
}
package com.example.capstoneapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivityMainBinding
import com.example.capstoneapp.ui.Feature04.Feature04Fragment
import com.example.capstoneapp.viewmodel.MainViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                val token = user.token
                mainViewModel.getProfile("Bearer $token")
                mainViewModel.getDietPlan("Bearer $token")
            }
        }

        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_1 -> {
                    replaceFragment(Feature01Fragment())
                    true
                }

                R.id.bottom_2 -> {
                    replaceFragment(Feature02Fragment())
                    true
                }

                R.id.bottom_3 -> {
                    replaceFragment(Feature03Fragment())
                    true
                }

                R.id.bottom_4 -> {
                    replaceFragment(Feature04Fragment())
                    true
                }

                else -> false
            }
        }
        // default fragment
        replaceFragment(Feature01Fragment())
        observeViewModel()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
    }

    private fun observeViewModel() {
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
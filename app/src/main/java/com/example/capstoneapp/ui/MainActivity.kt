package com.example.capstoneapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivityMainBinding
import com.example.capstoneapp.ui.Feature04.Feature04Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
    }
}
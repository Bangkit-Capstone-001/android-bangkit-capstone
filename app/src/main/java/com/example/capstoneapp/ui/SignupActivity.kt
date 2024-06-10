package com.example.capstoneapp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivitySignupBinding
import com.example.capstoneapp.viewmodel.SignupViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel: SignupViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        showLoading(false)
        observeViewModel()
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val pass = binding.edRegisterPassword.text.toString()

            if (validateInput(name, email, pass)) {
                signupViewModel.handleSignup(email, pass, name)
                this.email = email
            }
        }
    }

    private fun validateInput(name: String, email: String, pass: String): Boolean {
        return when {
            name.isEmpty() -> {
                showErrorDialog("Name cannot be empty")
                false
            }

            email.isEmpty() -> {
                showErrorDialog("Email cannot be empty")
                false
            }

            pass.isEmpty() -> {
                showErrorDialog("Password cannot be empty")
                false
            }

            else -> true
        }
    }

    private fun observeViewModel() {
        signupViewModel.message.observe(this) { message ->
            if (signupViewModel.isError.value == true) {
                showErrorDialog(message)
            } else {
                showSuccessDialog(email)
            }
        }

        signupViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.regis_failed)
            setMessage(message)
            setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }

    private fun showSuccessDialog(email: String) {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.success)
            setMessage(getString(R.string.regis_success, email))
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
package com.example.capstoneapp.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneapp.R
import com.example.capstoneapp.data.pref.UserModel
import com.example.capstoneapp.databinding.ActivityLoginBinding
import com.example.capstoneapp.viewmodel.LoginViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        showLoading(false)
        observeViewModel()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val pass = binding.edLoginPassword.text.toString()
            viewModel.handleLogin(email, pass)
        }
    }

    private fun observeViewModel() {
        viewModel.resp.observe(this) { resp ->
            resp?.let {
                if (it.status == 200) {
                    val userModel = UserModel(resp.message ?: "", resp.idToken ?: "", true)
                    viewModel.saveSession(userModel)
                    showSuccessDialog("Berhasil login")
                }
            }
        }

        viewModel.message.observe(this) { message ->
            if (viewModel.isError.value == true) {
                showErrorDialog(message)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.error)
            setMessage(message)
            setPositiveButton(R.string.ok, null)
            create()
            show()
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.success)
            setMessage(message)
            setPositiveButton(R.string.ok) { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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
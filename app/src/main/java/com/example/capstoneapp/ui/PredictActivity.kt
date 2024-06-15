package com.example.capstoneapp.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.helper.imageToUri
import com.dicoding.picodiploma.loginwithanimation.helper.reduceFileImage
import com.dicoding.picodiploma.loginwithanimation.helper.uriToFile
import com.example.capstoneapp.R
import com.example.capstoneapp.databinding.ActivityPredictBinding
import com.example.capstoneapp.helper.PredictionAdapter
import com.example.capstoneapp.viewmodel.PredictViewModel
import com.example.capstoneapp.viewmodel.ViewModelFactory
import com.yalantis.ucrop.UCrop
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter

class PredictActivity : AppCompatActivity() {
    lateinit var binding: ActivityPredictBinding
    private val predictViewModel by viewModels<PredictViewModel> { ViewModelFactory.getInstance(this) }
    private var currentImageUri: Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        predictViewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                setupAction(user.token)
            }
        }

        setupView()
        observeViewModel()
        showLoading(false)
    }

    /**
     * ---------------------------------- General settings & permission
     */

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    @RequiresApi(Build.VERSION_CODES.O)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Camera access allowed", this)
                startCamera()
            } else {
                showErrorDialog("Camera access denied!")
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAction(token: String?) {
        binding.buttonGal.setOnClickListener {
            startGallery()
        }
        binding.buttonCam.setOnClickListener {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                requestPermissionLauncher.launch(REQUIRED_PERMISSION)
            }
        }

        binding.buttonPredict.setOnClickListener {
            currentImageUri?.let {
                addImage("Bearer $token", this)
            } ?: run {
                showToast("No image found", this)
            }
        }
    }

    private fun observeViewModel() {
        predictViewModel.isError.observe(this) { error ->
            if (!error) {
                val res = predictViewModel.predictRes.value
                val adapter = PredictionAdapter()
                adapter.submitList(res)
                binding.rvFood.adapter = adapter
                binding.cardView2.visibility = View.VISIBLE
            }
            val message = predictViewModel.message.value
            binding.tvInfo.text = message
            showLoading(false)
        }
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
        binding.rvFood.layoutManager = LinearLayoutManager(this)
        binding.rvFood.adapter = PredictionAdapter()
    }

    /**
     * ---------------------------------- UCrop
     */

    private val uCropContract = object : ActivityResultContract<List<Uri>, Uri?>() {
        override fun createIntent(context: Context, input: List<Uri>): Intent {
            val inputUri = input[0]
            val outputUri = input[1]

            val uCrop = UCrop.of(inputUri, outputUri)
                .withAspectRatio(5f, 5f)
                .withMaxResultSize(224, 224)

            return uCrop.getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return if (resultCode == RESULT_OK && intent != null) {
                UCrop.getOutput(intent)
            } else {
                null
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startUCrop(inputUri: Uri?) {
        inputUri?.let { uri ->
            val timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            val outputUri = File(filesDir, "$timestamp.jpg").toUri()
            val listUri = listOf(uri, outputUri)

            uCropLauncher.launch(listUri)
        } ?: showToast("No image selected", this)
    }

    private val uCropLauncher = registerForActivityResult(uCropContract) { croppedUri ->
        croppedUri?.let {
            currentImageUri = it
            showImage()
        } ?: run {
            showToast("Cropping was canceled", this)
        }
    }


    /**
     * ---------------------------------- Camera
     */

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startCamera() {
        currentImageUri = imageToUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            currentImageUri?.let { uri ->
                startUCrop(uri)
            }
        }
    }

    /**
     * ---------------------------------- Gallery
     */

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            startUCrop(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    /**
     * ---------------------------------- Load images
     */

    private fun showImage() {
        currentImageUri?.let {
            binding.ivAddPreview.setImageURI(it)
            binding.ivAddPreview.invalidate()
        }
        binding.tvInfo.text = getString(R.string.predict_info)
        binding.cardView2.visibility = View.GONE
    }

    private fun addImage(token: String, context: Context) {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()

            showLoading(true)

            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val fileReq = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )

            // addViewModel.addStory(token, fileRes, descriptionRes)
            predictViewModel.predictImage(token, fileReq)
        } ?: showToast("No image selected", context)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.error)
            setMessage(message)
            setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }

    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
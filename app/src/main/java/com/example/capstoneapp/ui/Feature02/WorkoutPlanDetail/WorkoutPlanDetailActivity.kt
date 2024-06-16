package com.example.capstoneapp.ui.Feature02.WorkoutPlanDetail

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneapp.R
import com.example.capstoneapp.data.response.GetDataItem

class WorkoutPlanDetailActivity : AppCompatActivity() {

    private var detail: GetDataItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detail = intent.getParcelableExtra(KEY_DETAIL) as GetDataItem?
        Log.d("FROM DETAIL", detail.toString())
    }

    companion object {
        const val KEY_DETAIL = "key-detail"
    }
}
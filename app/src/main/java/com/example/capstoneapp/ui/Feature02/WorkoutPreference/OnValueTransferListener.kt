package com.example.capstoneapp.ui.Feature02.WorkoutPreference

interface OnValueTransferListener {
    fun onValueTransfer(tag: String, value: String)
}

interface OnArrayValueTransferListener {
    fun onArrayValueTransferListener(tag: String, value: MutableList<Int>)
}
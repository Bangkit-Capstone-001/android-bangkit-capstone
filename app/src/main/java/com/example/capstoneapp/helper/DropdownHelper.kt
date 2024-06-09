package com.example.capstoneapp.helper

fun goalToAttr(g: String): String {
    return when (g) {
        "Weight Gain" -> "weightGain"
        "Weight Loss" -> "weightLoss"
        "Maintain Body" -> "weightMaintain"
        else -> "unknown"
    }
}

fun attrToGoal(g: String): String {
    return when (g) {
        "weightGain" -> "Weight Gain"
        "weightLoss" -> "Weight Loss"
        "weightMaintain" -> "Maintain Body"
        else -> "unknown"
    }
}

fun activityToAttr(a: String): String {
    return when (a) {
        "Index 4 (Active): Moves a lot" -> "active"
        "Index 3 (Moderate): Moves moderately" -> "moderate"
        "Index 2 (Light): Moves a little" -> "light"
        "Index 1 (Sedentary): Moves rarely" -> "sedentary"
        else -> "unknown"
    }
}

fun attrToActivity(a: String): String {
    return when (a) {
        "active" -> "Active"
        "moderate" -> "Moderate"
        "light" -> "Light"
        "sedentary" -> "Sedentary"
        else -> "unknown"
    }
}
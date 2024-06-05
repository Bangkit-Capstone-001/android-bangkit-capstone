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
        "Index 2 (Light): Moves a litte" -> "light"
        "Index 1 (Sedentary): Moves rarely" -> "sedentary"
        else -> "unknown"
    }
}

fun attrToActivity(a: String): String {
    return when (a) {
        "active" -> "➃ Active"
        "moderate" -> "➂ Moderate"
        "light" -> "➁ Light"
        "sedentary" -> "➀ Sedentary"
        else -> "unknown"
    }
}
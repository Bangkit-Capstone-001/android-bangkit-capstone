package com.example.capstoneapp.ui.Feature03

import com.example.capstoneapp.data.response.GetFoodResponse

val dictFood: MutableMap<String, String> = mutableMapOf()

val listFood: ArrayList<String> = arrayListOf()

fun retrieveAllFood(response: GetFoodResponse) {
    for (food in response.data!!) {
        if (food != null) {
            food.namaBahanMakanan?.let { food.id?.let { it1 -> dictFood.put(it, it1) } }
        }
        if (food != null) {
            food.namaBahanMakanan?.let { listFood.add(it) }
        }
    }
}
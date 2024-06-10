package com.example.capstoneapp.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneapp.data.response.DataFoodHist
import com.example.capstoneapp.databinding.ItemFoodBinding

class FoodHistAdapter : ListAdapter<DataFoodHist, FoodHistAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    override fun getItem(position: Int): DataFoodHist {
        return currentList[position]
    }

    // set-up display & onClick adapter
    inner class MyViewHolder(val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foodHist: DataFoodHist) {
            binding.tvFoodName.text = "${foodHist.food?.namaBahanMakanan}"
            binding.tvFoodNutrition.text = "${foodHist.calories} cal"
            binding.tvQuantity.text = "${foodHist.quantity} grams"
            binding.tvQuantity.visibility = View.VISIBLE
        }
    }

    // check for changes
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataFoodHist>() {
            override fun areItemsTheSame(oldItem: DataFoodHist, newItem: DataFoodHist): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataFoodHist, newItem: DataFoodHist): Boolean {
                return oldItem == newItem
            }
        }
    }
}
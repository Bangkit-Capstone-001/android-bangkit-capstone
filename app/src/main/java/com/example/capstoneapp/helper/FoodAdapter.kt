package com.example.capstoneapp.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneapp.data.response.DataFood
import com.example.capstoneapp.databinding.ItemFoodBinding

/**
 * This is initially used as random food adapter
 * Cannot be clicked
 */
class FoodAdapter : ListAdapter<DataFood, FoodAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    override fun getItem(position: Int): DataFood {
        return currentList[position]
    }

    // set-up display & onClick adapter
    inner class MyViewHolder(val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: DataFood) {
            binding.tvFoodName.text = "• ${food.namaBahanMakanan}"
            binding.tvFoodNutrition.text = "${food.komposisiEnergiKal} cal/${food.komposisiPer}"
        }
    }

    // check for changes
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataFood>() {
            override fun areItemsTheSame(oldItem: DataFood, newItem: DataFood): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataFood, newItem: DataFood): Boolean {
                return oldItem == newItem
            }
        }
    }
}
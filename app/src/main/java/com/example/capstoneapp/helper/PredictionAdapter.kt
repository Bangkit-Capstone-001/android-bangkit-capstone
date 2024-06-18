package com.example.capstoneapp.helper

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneapp.data.response.DataFood
import com.example.capstoneapp.databinding.ItemPredictionBinding
import com.example.capstoneapp.ui.Feature03.AddFoodActivity

class PredictionAdapter : ListAdapter<DataFood, PredictionAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemPredictionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    inner class MyViewHolder(val binding: ItemPredictionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: DataFood) {
            binding.tvFoodName.text = "${food.namaBahanMakanan}"
            binding.tvInfo.text =
                "Nutritions per ${food.komposisiPer}:" +
                        "\n• ${food.komposisiEnergiKal} cal  • ${food.komposisiKarbohidratG}g carbs, " +
                        "\n• ${food.komposisiProteinG}g protein  • ${food.komposisiLemakG}g fat"

            binding.buttonAdd.setOnClickListener {
                val context = it.context
                val intent = Intent(context, AddFoodActivity::class.java).apply {
                    putExtra("EXTRA_FOOD", food.namaBahanMakanan)
                }
                context.startActivity(intent)
            }
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
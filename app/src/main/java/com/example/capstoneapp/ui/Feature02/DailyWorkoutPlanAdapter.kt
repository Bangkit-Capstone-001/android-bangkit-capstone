package com.example.capstoneapp.ui.Feature02

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.databinding.ItemDailyPlansBinding

class DailyWorkoutPlanAdapter : ListAdapter<GetDataItem, DailyWorkoutPlanAdapter.DailyWorkoutPlanViewHolder>(DIFF_CALLBACK) {
    class DailyWorkoutPlanViewHolder(private val binding: ItemDailyPlansBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(planItem: GetDataItem) {
            val target = "${planItem.target} Body"
            val option = "(${planItem.option})"
            binding.itemDailyPlansTvPlanTarget.text = target
            binding.itemDailyPlansTvPlanOption.text = option


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWorkoutPlanViewHolder {
        val binding = ItemDailyPlansBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyWorkoutPlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyWorkoutPlanViewHolder, position: Int) {
        val planItem = getItem(position)
        holder.bind(planItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GetDataItem>() {
            override fun areItemsTheSame(oldItem: GetDataItem, newItem: GetDataItem): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: GetDataItem, newItem: GetDataItem): Boolean = oldItem == newItem

        }
    }
}
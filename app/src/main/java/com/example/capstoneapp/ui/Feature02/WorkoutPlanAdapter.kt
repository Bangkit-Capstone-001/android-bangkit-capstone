package com.example.capstoneapp.ui.Feature02

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneapp.data.response.GetDataItem
import com.example.capstoneapp.databinding.ItemMyPlansBinding
import com.example.capstoneapp.databinding.ItemWorkoutBinding

class WorkoutPlanAdapter(): ListAdapter<GetDataItem, WorkoutPlanAdapter.WorkoutPlanViewHolder>(DIFF_CALLBACK) {

    class WorkoutPlanViewHolder(private val binding: ItemMyPlansBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(planItem: GetDataItem) {
            val target = "${planItem.target} Body"
            val option = "(${planItem.option})"
            binding.itemMyPlansTvPlanTarget.text = target
            binding.itemMyPlansTvPlanOption.text = option
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutPlanViewHolder {
        val binding = ItemMyPlansBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutPlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutPlanViewHolder, position: Int) {
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
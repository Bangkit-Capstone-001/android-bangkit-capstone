package com.example.capstoneapp.ui.Feature02.WorkoutList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneapp.R
import com.example.capstoneapp.data.response.DataItem
import com.example.capstoneapp.databinding.ItemWorkoutBinding

class WorkoutListAdapter : ListAdapter<DataItem, WorkoutListAdapter.WorkoutListViewHolder>(DIFF_CALLBACK) {
    class WorkoutListViewHolder(val binding: ItemWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workoutItem: DataItem) {
            binding.itemWorkoutTvWorkoutTitlePlaceholder.text = workoutItem.exerciseName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutListViewHolder {
        val binding = ItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutListViewHolder, position: Int) {
        val workoutItem = getItem(position)
        holder.bind(workoutItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean = oldItem == newItem

        }
    }
}
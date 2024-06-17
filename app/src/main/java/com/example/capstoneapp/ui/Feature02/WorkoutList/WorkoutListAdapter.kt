package com.example.capstoneapp.ui.Feature02.WorkoutList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneapp.R
import com.example.capstoneapp.data.response.DataItem
import com.example.capstoneapp.databinding.ItemWorkoutBinding
import com.squareup.picasso.Picasso

class WorkoutListAdapter(
    private val onItemClicked: (DataItem) -> Unit,
    private val isSelected: (String) -> Boolean) : ListAdapter<DataItem, WorkoutListAdapter.WorkoutListViewHolder>(DIFF_CALLBACK) {
    class WorkoutListViewHolder(
        private val binding: ItemWorkoutBinding,
        private val onItemClicked: (DataItem) -> Unit,
        private val isSelected: (String) -> Boolean) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workoutItem: DataItem) {
            binding.itemWorkoutTvWorkoutTitlePlaceholder.text = workoutItem.exerciseName
            try {
                val imageUrl = workoutItem.exerciseImages?.get(0)
                Picasso.get().load(imageUrl).resize(90, 50).centerCrop().into(binding.itemWorkoutIvWorkoutImagePlaceholder)
            } catch (e: Exception) {
                Log.e("IMAGE INSERTION", e.message.toString())
            }

            if (isSelected(workoutItem.id.toString())) {
                binding.itemWorkoutIvLikeButton.setImageResource(R.drawable.img_like_icon_clicked)
            } else {
                binding.itemWorkoutIvLikeButton.setImageResource(R.drawable.img_like_icon)
            }

            binding.itemWorkoutIvLikeButton.setOnClickListener {
                onItemClicked(workoutItem)
                if (isSelected(workoutItem.id.toString())) {
                    binding.itemWorkoutIvLikeButton.setImageResource(R.drawable.img_like_icon_clicked)
                } else {
                    binding.itemWorkoutIvLikeButton.setImageResource(R.drawable.img_like_icon)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutListViewHolder {
        val binding = ItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutListViewHolder(binding, onItemClicked, isSelected)
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
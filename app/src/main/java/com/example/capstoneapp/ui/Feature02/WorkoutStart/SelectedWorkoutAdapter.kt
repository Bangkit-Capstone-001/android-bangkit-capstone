package com.example.capstoneapp.ui.Feature02.WorkoutStart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneapp.data.response.GetWorkoutsItem
import com.example.capstoneapp.databinding.ItemWorkout2Binding
import com.squareup.picasso.Picasso

class SelectedWorkoutAdapter : ListAdapter<GetWorkoutsItem, SelectedWorkoutAdapter.SelectedWorkoutViewHolder>(DIFF_CALLBACK) {

    class SelectedWorkoutViewHolder(private val binding: ItemWorkout2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: GetWorkoutsItem) {
            binding.itemWorkout2TvWorkoutTitlePlaceholder.text = workout.exerciseName
            try {
                val imageUrl = workout.exerciseImages?.get(0)
                Picasso.get().load(imageUrl).resize(90, 50).centerCrop().into(binding.itemWorkout2IvWorkoutImagePlaceholder)
            } catch (e: Exception) {
                Log.e("INSERTION ERROR", e.message.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedWorkoutViewHolder {
        val binding = ItemWorkout2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectedWorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedWorkoutViewHolder, position: Int) {
        val workoutItem = getItem(position)
        holder.bind(workoutItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GetWorkoutsItem>() {
            override fun areItemsTheSame(
                oldItem: GetWorkoutsItem,
                newItem: GetWorkoutsItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: GetWorkoutsItem,
                newItem: GetWorkoutsItem
            ): Boolean = oldItem == newItem

        }
    }
}
package com.example.capstoneapp.ui.Feature02.WorkoutSequence

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneapp.R
import com.squareup.picasso.Picasso

class CarouselAdapter(private val items: List<String?>) :
    RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {
    class CarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.carousel_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        Picasso.get()
            .load(items[position])
            .centerCrop()
            .fit()
            .into(holder.imageView)
    }
}
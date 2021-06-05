package com.example.snkrs.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.snkrs.R
import com.example.snkrs.model.Movie
import com.example.snkrs.network.ApiClient
import com.squareup.picasso.Picasso

class MovieViewHolder(
  itemView: View
) : RecyclerView.ViewHolder(itemView) {
  private val imageView: ImageView = itemView.findViewById(R.id.person_iv)
  fun bind(data: Movie, onItemClick: (Movie) -> Unit) {
    Picasso.get().load("${ApiClient.TMDB_IMAGES_BASE_URL}original${data.posterPath}").resize(300,500).into(imageView)
    itemView.setOnClickListener{onItemClick(data)}
  }
}
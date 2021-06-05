package com.example.snkrs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snkrs.R
import com.example.snkrs.model.Movie

class MovieAdapter(
  private var data: List<Movie>,
  private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    return MovieViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.layout_card_item, parent, false)
    );
  }
  
  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    holder.bind(data[position], onClick)
  }
  
  override fun getItemCount(): Int {
    return data.size
  }
  
  fun setData(data: List<Movie>){
    this.data = data;
    notifyDataSetChanged()
  }
}
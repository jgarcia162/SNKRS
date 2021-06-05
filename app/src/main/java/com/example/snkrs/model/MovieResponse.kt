package com.example.snkrs.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
  val page: Int,
  @SerializedName("total_results")
  val total: Int,
  @SerializedName("total_pages")
  val totalPages: Int,
  val results: List<Movie>
)
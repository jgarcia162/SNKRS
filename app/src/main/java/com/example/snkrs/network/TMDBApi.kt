package com.example.snkrs.network

import com.example.snkrs.model.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
  @GET("movie/top_rated")
  fun getTopRated(@Query("api_key") apiKey: String): Single<MovieResponse>
}
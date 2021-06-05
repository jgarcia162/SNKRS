package com.example.snkrs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.snkrs.adapter.CarouselLayoutManager
import com.example.snkrs.adapter.MovieAdapter
import com.example.snkrs.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity() {
  private lateinit var movieAdapter: MovieAdapter
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val repository = Repository(ApiClient(HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BASIC
    }))
    
    val recyclerView: RecyclerView = findViewById(R.id.top_rated_movies_rv)
    recyclerView.layoutManager = CarouselLayoutManager(this)
    
    repository
      .getTopRated()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeBy(
        onSuccess = {
          movieAdapter = MovieAdapter(it.results) { movie ->
            Toast.makeText(applicationContext, movie.title, Toast.LENGTH_SHORT).show()
          }
          recyclerView.adapter = movieAdapter
          PagerSnapHelper().attachToRecyclerView(recyclerView)
        },
        onError = {
          it.printStackTrace()
        }
      )
  }
}


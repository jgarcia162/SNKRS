package com.example.snkrs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.snkrs.adapter.CarouselLayoutManager
import com.example.snkrs.adapter.MovieAdapter
import com.example.snkrs.extensions.toast
import com.example.snkrs.model.Movie
import com.example.snkrs.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private val moviesViewModel: MoviesViewModel by viewModels()
  private lateinit var topRatedAdapter: MovieAdapter
  private lateinit var upcomingAdapter: MovieAdapter
  private lateinit var topRatedRV: RecyclerView
  private lateinit var upcomingRV: RecyclerView
  @Inject lateinit var repository: Repository
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    topRatedRV = findViewById(R.id.top_rated_movies_rv)
    upcomingRV = findViewById(R.id.upcoming_movies_rv)
  
    initRV()
  }
  
  private fun initRV() {
    topRatedRV.layoutManager = CarouselLayoutManager(this)
    upcomingRV.layoutManager = CarouselLayoutManager(this)
    val onMovieClicked: (Movie) -> Unit = { movie ->
      Toast.makeText(applicationContext, movie.title, Toast.LENGTH_SHORT).show()
    }
    topRatedAdapter = MovieAdapter(emptyList(), onMovieClicked)
    topRatedRV.adapter = topRatedAdapter
    PagerSnapHelper().attachToRecyclerView(topRatedRV)
    
    upcomingAdapter = MovieAdapter(emptyList(), onMovieClicked)
    upcomingRV.adapter = upcomingAdapter
    PagerSnapHelper().attachToRecyclerView(upcomingRV)
  }
  
  override fun onStart() {
    super.onStart()
    observeLiveData()
    moviesViewModel.getTopRatedMovies({
      toast("movies loaded")
    }, {
      toast("couldnt load movies")
    })
    
    moviesViewModel.getUpcomingMovies({
      toast("movies loaded")
    }, {
      toast("couldnt load movies")
    })
  }
  
  private fun observeLiveData(){
    moviesViewModel.topRatedMoviesLiveData.observe(this, {movies ->
      topRatedAdapter.setData(movies)
    })
    moviesViewModel.upcomingMoviesLiveData.observe(this, {movies ->
      upcomingAdapter.setData(movies)
    })
  }
}


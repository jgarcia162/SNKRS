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
import com.example.snkrs.caching.CacheInterceptor
import com.example.snkrs.caching.ForceCacheInterceptor
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity() {
  private var cacheSize: Long =  (5 * 1024 * 1024).toLong()
  private lateinit var repository: Repository
  private lateinit var apiClient: ApiClient
  
  private lateinit var movieAdapter: MovieAdapter
  
  private lateinit var topRatedRV: RecyclerView
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    topRatedRV = findViewById(R.id.top_rated_movies_rv)
  
    initRV()
    initApiClient()
  }
  
  private fun initRV() {
    topRatedRV.layoutManager = CarouselLayoutManager(this)
    movieAdapter = MovieAdapter(emptyList()) { movie ->
      Toast.makeText(applicationContext, movie.title, Toast.LENGTH_SHORT).show()
    }
    topRatedRV.adapter = movieAdapter
    PagerSnapHelper().attachToRecyclerView(topRatedRV)
  }
  
  private fun initApiClient() {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BASIC
    }
    
    apiClient = ApiClient(
      loggingInterceptor,
      CacheInterceptor(),
      ForceCacheInterceptor(this),
      Cache(cacheDir, cacheSize)
    )
  }
  
  override fun onStart() {
    super.onStart()
    repository = Repository(apiClient)
    repository
      .getTopRated()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeBy(
        onSuccess = {
          movieAdapter.setData(it.results)
        },
        onError = {
          it.printStackTrace()
        }
      )
  }
}


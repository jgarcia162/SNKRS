package com.example.snkrs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.snkrs.Repository
import com.example.snkrs.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MoviesViewModel @Inject constructor(
  private val repository: Repository,
  @Named("main") private val mainThreadScheduler: Scheduler,
  @Named("io") private val ioScheduler: Scheduler
): ViewModel() {
  private var compositeDisposable: CompositeDisposable = CompositeDisposable()
  
  private val topRatedMoviesList = MutableLiveData<List<Movie>>()
  val topRatedMoviesLiveData: LiveData<List<Movie>> = topRatedMoviesList
  
  fun getTopRatedMovies(
    onLoadComplete: () -> Unit = {},
    onLoadFailed: (Throwable) -> Unit = {}
  ){
    compositeDisposable += repository
      .getTopRated()
      .subscribeOn(ioScheduler)
      .observeOn(mainThreadScheduler)
      .subscribeBy(
        onSuccess = {
          topRatedMoviesList.value = it.results
          onLoadComplete()
        },
        onError = {
          onLoadFailed(it)
        }
      )
  }
}
package com.example.snkrs.network

import com.example.snkrs.BuildConfig
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import com.example.snkrs.caching.CacheInterceptor
import com.example.snkrs.caching.ForceCacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** This is a wrapper around the following APIs:
 *
 * [tmdbApi]
 *
 * Use ApiClient for easier abstraction and implementation should new APIs be introduced and for
 * easier integration with a Dependency Injection framework*/

class ApiClient(
  private var loggingInterceptor: HttpLoggingInterceptor?,
  private var cacheInterceptor: CacheInterceptor,
  private var forceCacheInterceptor: ForceCacheInterceptor,
  private val cache: Cache
) {
//  private val userAuthHeader: String get() = "Authentication ${BuildTmdbApiKey}"
  
  var tmdbBaseUrl: String = TMDB_BASE_URL
    set(value) {
      field = value
      initTmdbApi()
    }
  
  private lateinit var tmdbApi: TMDBApi
  
  private fun initTmdbApi() {
    tmdbApi = createApi(tmdbBaseUrl)
  }
  
  private inline fun <reified T : Any> createApi(baseUrl: String): T {
    val builder = OkHttpClient.Builder()
    
    builder.cache(cache)
    builder.addNetworkInterceptor(cacheInterceptor)
    builder.addInterceptor(forceCacheInterceptor)
    
    loggingInterceptor?.let {
      builder.addInterceptor(it)
    }
    
    val client = builder.build()
    
    val retrofit = Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
      .client(client)
      .build()
    
    return retrofit.create(T::class.java)
  }
  
  init {
    initTmdbApi()
  }
  
  companion object {
    const val TMDB_BASE_URL: String = "https://api.themoviedb.org/3/"
    const val TMDB_IMAGES_BASE_URL: String = "https://image.tmdb.org/t/p/"
  }
  
  /**
   * Gets all [Person]s
   *
   * See [tmdbApi.getPeople]
   */
  fun getTopRated() = tmdbApi.getTopRated(BuildConfig.TmdbApiKey)
  
}
package com.example.snkrs.caching

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * The CacheInterceptor adds cache control to our retrofit client. This allows us to retrieve our
 * data from a retrofit cache versus the network. This helps avoid rate-limits and reduces bandwidth
 * use.
 * */
class CacheInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val response: Response = chain.proceed(chain.request())
    val cacheControl = CacheControl.Builder()
      // if the cache is older than 1 hour, a network request is made
      .maxAge(1, TimeUnit.HOURS)
      //if there is no network availability and the cache is older than 1 day, we make a network
      // request
      .maxStale(1, TimeUnit.DAYS)
      .build()
    
    return response.newBuilder()
      .header("Cache-Control", cacheControl.toString())
      .build()
  }
}
package com.example.snkrs

import com.example.snkrs.network.ApiClient
import javax.inject.Inject

class Repository @Inject constructor(private val apiClient: ApiClient) {
  fun getTopRated() = apiClient.getTopRated();
  fun getUpcoming() = apiClient.getUpcoming();
}
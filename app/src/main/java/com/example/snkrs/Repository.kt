package com.example.snkrs

import com.example.snkrs.network.ApiClient

class Repository(private val apiClient: ApiClient) {
  fun getTopRated() = apiClient.getTopRated();
}
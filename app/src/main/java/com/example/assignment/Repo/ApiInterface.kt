package com.example.assignment.Repo

import com.example.assignment.data.NewsApi
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query




interface APIService {
    @GET("/v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String?,
        @Query("apiKey") key: String
    ): Response<NewsApi>

    @GET("/v2/everything")
    suspend fun getSearchedNews(
        @Query("q") query: String,
        @Query("apiKey") key: String
    ): Response<NewsApi>

}
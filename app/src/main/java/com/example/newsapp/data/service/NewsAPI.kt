package com.example.newsapp.data.service

import com.example.newsapp.data.model.HeadlinesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines")
    fun getHeadlineNews(
        @Query("apiKey") key:String,
        @Query("country") country:String,
        @Query("pageSize") pageSize:Int,
        @Query("category") category:String
    ): Call<HeadlinesResponse>

    @GET("everything")
    fun getSearchNews(
        @Query("apiKey") key:String,
        @Query("q") keyword:String,
        @Query("pageSize") pageSize:Int,
    ): Call<HeadlinesResponse>
}
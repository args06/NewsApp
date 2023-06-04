package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.data.model.HeadlinesResponse
import com.example.newsapp.data.service.NewsServiceAPI
import com.example.newsapp.helper.Constant.DEFAULT_COUNTRY
import com.example.newsapp.helper.Constant.DETAIL_TAB_VALUE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _headlineNews = MutableLiveData<List<ArticlesItem>>()
    val headlineNews: LiveData<List<ArticlesItem>> = _headlineNews

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getHeadlineNews(category: String, size: Int = 20) {
        _isLoading.value = true
        val client =
            NewsServiceAPI.getApiService().getHeadlineNews(BuildConfig.API_KEY, DEFAULT_COUNTRY,
                size, category)
        client.enqueue(object : Callback<HeadlinesResponse> {
            override fun onResponse(
                call: Call<HeadlinesResponse>, response: Response<HeadlinesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _headlineNews.value = response.body()?.articles
                } else {
                    Log.e("Response", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<HeadlinesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failure", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getSearchNews(keyword: String, size: Int = 25) {
        _isLoading.value = true
        val client =
            NewsServiceAPI.getApiService().getSearchNews(BuildConfig.API_KEY, keyword, size)
        client.enqueue(object : Callback<HeadlinesResponse> {
            override fun onResponse(
                call: Call<HeadlinesResponse>, response: Response<HeadlinesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _headlineNews.value = response.body()?.articles
                } else {
                    Log.e("Response", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<HeadlinesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failure", "onFailure: ${t.message.toString()}")
            }
        })
    }
}
package com.example.mymedia.retrofit

import com.example.mymedia.BuildConfig
import com.example.mymedia.data.remote.SearchRemoteDatasource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.YOUTUBE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SearchRemoteDatasource by lazy {
        retrofit.create(SearchRemoteDatasource::class.java)
    }
}
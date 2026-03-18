package com.example.mymedia.data.remote

import com.example.mymedia.BuildConfig
import com.example.mymedia.data.model.ApiResponse
import com.example.mymedia.data.model.NoneSearchItem
import com.example.mymedia.data.model.SearchItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRemoteDatasource {

    @GET("search")
    suspend fun searchVideos(
        @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("q") query: String,
        @Query("pageToken") pageToken: String,
        @Query("type") type: String = "video",
        @Query("part") part: String = "snippet",
        @Query("regionCode") regionCode: String = "KR",
        @Query("maxResults") maxResults: Int = 24,
    ): ApiResponse<SearchItem>

    @GET("search")
    suspend fun searchChannel(
        @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("q") query: String,
        @Query("pageToken") pageToken: String,
        @Query("type") type: String = "channel",
        @Query("part") part: String = "snippet",
        @Query("regionCode") regionCode: String = "KR",
        @Query("maxResults") maxResults: Int = 24,
    ): ApiResponse<SearchItem>

    @GET("search")
    suspend fun searchMostPopularLiveVideos(
        @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("chart") chart: String = "mostPopular",
        @Query("type") type: String = "video",
        @Query("part") part: String = "snippet",
        @Query("relevanceLanguage") relevanceLanguage: String = "ko",
        @Query("eventType") eventType: String = "live",
        @Query("maxResults") maxResults: Int = 5,
    ): ApiResponse<SearchItem>

    @GET("videos")
    suspend fun MostPopularVideos(
        @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet,contentDetails",
        @Query("chart") chart: String = "mostPopular",
        @Query("regionCode") regionCode: String = "KR",
        @Query("maxResults") maxResults: Int = 50,
    ): ApiResponse<NoneSearchItem>

    @GET("videos")
    suspend fun getVideoByCategory(
        @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet,contentDetails",
        @Query("chart") chart: String = "mostPopular",
        @Query("regionCode") regionCode: String = "KR",
        @Query("videoCategoryId") videoCategoryId: String,
        @Query("maxResults") maxResults: Int = 50,
    ): ApiResponse<NoneSearchItem>

    @GET("videoCategories")
    suspend fun getCategoryList(
        @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("regionCode") regionCode: String = "KR",
        @Query("part") part: String = "snippet",
    ): ApiResponse<NoneSearchItem>

    @GET("channels")
    suspend fun getChannel(
        @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet",
        @Query("id") id: String,
    ): ApiResponse<NoneSearchItem>
}
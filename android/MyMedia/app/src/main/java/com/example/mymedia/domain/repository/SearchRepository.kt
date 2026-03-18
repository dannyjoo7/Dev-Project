package com.example.mymedia.domain.repository

import com.example.mymedia.data.model.ApiResponse
import com.example.mymedia.data.model.SearchItem

interface SearchRepository {
    suspend fun getSearchItem(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ApiResponse<SearchItem>
}
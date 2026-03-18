package com.example.mymedia.domain.model

import com.example.mymedia.data.model.ApiResponse
import com.example.mymedia.data.model.SearchItem
import java.util.Date


fun ApiResponse<SearchItem>.toMediaItem(): List<MediaItem> {
    // ApiResponse에서 SearchItem 목록을 가져옵니다.
    val searchItems = this.items ?: emptyList()

    // 각 SearchItem을 MediaItem으로 변환한 후 목록에 추가합니다.
    val mediaItems = searchItems.map { searchItem ->
        MediaItem(
            id = searchItem.id?.videoId ?: "",
            title = searchItem.snippet?.title ?: "",
            description = searchItem.snippet?.description ?: "",
            datetime = Date(), // 날짜 정보를 설정해야 합니다.
            thumbnail = searchItem.snippet?.thumbnails?.default?.url ?: "",
            isFavorite = false, // 초기값 설정 필요
            nextPage = "" // 다음 페이지 정보 설정 필요
        )
    }

    return mediaItems
}

fun SearchResponse<VideoDocumentResponse>.toVideoEntity() = SearchEntity<VideoDocumentEntity>(
    meta = meta?.toEntity(),
    documents = documents?.map { response ->
        response.toEntity()
    }
)

fun MetaResponse.toEntity() = MetaEntity(
    totalCount = totalCount,
    pageableCount = pageableCount,
    isEnd = isEnd
)


fun ImageDocumentResponse.toEntity() = ImageDocumentEntity(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetime = datetime
)

fun VideoDocumentResponse.toEntity() = VideoDocumentEntity(
    title = title,
    url = url,
    playTime = playTime,
    thumbnail = thumbnail,
    author = author,
    datetime = datetime
)
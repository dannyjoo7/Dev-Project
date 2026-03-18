package com.example.mymedia.domain.model

import java.util.Date

open class MediaItem(
    open val id: String,
    open val title: String,
    open val description: String,
    open val datetime: Date,
    open val thumbnail: String,
    open var isFavorite: Boolean,
    open val nextPage: String,
)

data class VideoItem(
    override val id: String,
    override val title: String,
    override val description: String,
    override val datetime: Date,
    override val thumbnail: String,
    override var isFavorite: Boolean,
    override val nextPage: String,
    val channelId: String,
) : MediaItem(id, title, description, datetime, thumbnail, isFavorite, nextPage)

data class ChannelItem(
    override val id: String,
    override val title: String,
    override val description: String,
    override val datetime: Date,
    override val thumbnail: String,
    override var isFavorite: Boolean,
    override val nextPage: String
) : MediaItem(id, title, description, datetime, thumbnail, isFavorite, nextPage)

data class Category(
    val id: String,
    val title: String,
)

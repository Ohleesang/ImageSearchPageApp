package com.example.imagesearchpageapp.retrofit.data.video

import com.example.imagesearchpageapp.data.ItemDocument
import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("datetime")
    val dateTime: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbNailUrl: String,
) {
    fun toItemDocument() = ItemDocument(
        dateTime = this.dateTime,
        title = "[VIDEO]" + this.title,
        thumbNailUrl = this.thumbNailUrl
    )
}
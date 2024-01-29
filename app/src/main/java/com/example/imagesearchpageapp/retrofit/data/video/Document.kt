package com.example.imagesearchpageapp.retrofit.data.video

import com.google.gson.annotations.SerializedName

data class Document(
//    val author: String,
    @SerializedName("datetime")
    val dateTime: String,
//    val play_time: Int,
    @SerializedName("thumbnail")
    val thumbNailUrl: String,
    @SerializedName("title")
    val title: String,
//    val url: String
)
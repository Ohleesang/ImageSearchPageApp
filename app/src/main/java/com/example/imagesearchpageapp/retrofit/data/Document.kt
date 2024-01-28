package com.example.imagesearchpageapp.retrofit.data

import com.google.gson.annotations.SerializedName

data class Document(
//    val collection: String,
    @SerializedName("datetime")
    var dateTime: String,//dateTime

    @SerializedName("display_sitename")
    var siteName: String,//SiteName
//    val doc_url: String,
//    val height: Int,
//    val image_url: String,
    @SerializedName("thumbnail_url")
    val thumbNailUrl: String,//Thumnail_Url
//    val width: Int
)
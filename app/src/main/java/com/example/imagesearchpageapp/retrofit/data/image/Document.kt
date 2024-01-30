package com.example.imagesearchpageapp.retrofit.data.image

import com.example.imagesearchpageapp.data.ItemDocument
import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("datetime")
    var dateTime: String,//dateTime

    @SerializedName("display_sitename")
    var siteName: String,//SiteName
    @SerializedName("thumbnail_url")
    val thumbNailUrl: String,//Thumnail_Url
){
    fun toItemDocument() = ItemDocument(
        dateTime = this.dateTime,
        title = "[IMAGE] " + this.siteName,
        thumbNailUrl = this.thumbNailUrl
    )
}
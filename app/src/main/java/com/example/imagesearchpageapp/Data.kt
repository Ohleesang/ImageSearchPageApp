package com.example.imagesearchpageapp

import com.example.imagesearchpageapp.retrofit.data.Document

class Item(
    var isLike: Boolean = false,
    dateTime: String,
    siteName: String,
    thumbNailUrl: String,
) : Document(dateTime, siteName, thumbNailUrl)

object ListItem {
    var mItems = mutableListOf<Item>()
    val likeItems = mutableListOf<Item>()

    fun addLikeItems(item: Item) {
        likeItems.add(item)
    }

    fun deleteLikeItems(item: Item): Int {
        val idx = likeItems.indexOf(item)
        likeItems.removeAt(idx)
        return idx
    }
}

object SharedPerferences {
    const val SAVE_QUERY = "SAVE_QUERY"
}
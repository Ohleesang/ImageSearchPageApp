package com.example.imagesearchpageapp

data class Item(
    val thumbNaileUri: Int,
    val siteName: String,
    val dateTime: String,
    var isLike: Boolean,
)

object List {
    val mItems = mutableListOf<Item>()
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
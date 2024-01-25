package com.example.imagesearchpageapp

data class CardItem(
    val thumbNaileUri: Int,
    val siteName: String,
    val dateTime: String,
    var isLike: Boolean,
)

object ListItem {
    var mCardItems = mutableListOf<CardItem>()
    val likeCardItems = mutableListOf<CardItem>()

    fun addLikeItems(cardItem: CardItem) {
        likeCardItems.add(cardItem)
    }

    fun deleteLikeItems(cardItem: CardItem): Int {
        val idx = likeCardItems.indexOf(cardItem)
        likeCardItems.removeAt(idx)
        return idx
    }
}
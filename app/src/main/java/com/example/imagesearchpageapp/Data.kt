package com.example.imagesearchpageapp

data class Item(val thumbNaileUri: Int, val siteName: String, val dateTime: String,var isLike:Boolean)

object List{
    val mItems = mutableListOf<Item>()
}
package com.example.imagesearchpageapp

data class Item(val thumbNaileUri: Int, val siteName: String, val dateTime: String)

object List{
    val mItems = mutableListOf<Item>()
}
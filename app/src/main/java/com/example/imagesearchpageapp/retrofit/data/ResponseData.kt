package com.example.imagesearchpageapp.retrofit.data

import com.example.imagesearchpageapp.CardItem


data class ResponseData(
    val documents: List<Document>,
    val meta: Meta
)
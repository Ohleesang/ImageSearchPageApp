package com.example.imagesearchpageapp.retrofit

import com.example.imagesearchpageapp.retrofit.data.image.ResponseImageData
import com.example.imagesearchpageapp.retrofit.data.video.ResponseVideoData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DaumSearchApi {
    companion object {
        const val REST_API_KEY = "adb7936a82e698ff32d333f91b06fdfa"

    }

    @GET("image")
    suspend fun searchImages(
        @Header("Authorization") authorization: String = "KakaoAK $REST_API_KEY",
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") pageSize: Int = 5,
        @Query("sort") sort: String = "recency",
    ): ResponseImageData

    @GET("vclip")
    suspend fun searchVideos(
        @Header("Authorization") authorization: String = "KakaoAK $REST_API_KEY",
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") pageSize: Int = 5,
        @Query("sort") sort: String = "recency",
    ): ResponseVideoData
}
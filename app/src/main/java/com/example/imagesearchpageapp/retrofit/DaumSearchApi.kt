package com.example.imagesearchpageapp.retrofit


import com.example.imagesearchpageapp.BuildConfig
import com.example.imagesearchpageapp.retrofit.data.image.ResponseImageData
import com.example.imagesearchpageapp.retrofit.data.video.ResponseVideoData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DaumSearchApi {
    companion object {
        const val REST_API_KEY = BuildConfig.DAUM_SEARCH_API

    }

    @GET("image")
    suspend fun searchImages(
        @Header("Authorization") authorization: String = "KakaoAK $REST_API_KEY",
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") pageSize: Int = 50,
        @Query("sort") sort: String = "recency",
    ): ResponseImageData

    @GET("vclip")
    suspend fun searchVideos(
        @Header("Authorization") authorization: String = "KakaoAK $REST_API_KEY",
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") pageSize: Int = 30,
        @Query("sort") sort: String = "recency",
    ): ResponseVideoData
}
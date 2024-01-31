package com.example.imagesearchpageapp.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 *  앱 내부 관련 데이터 처리를 위해 데이터 클래스 를 생성
 */
data class AppData(val application: Application) {


    // SharedPreferences 인스턴스 생성
    val prefQuery: SharedPreferences =
        application.getSharedPreferences(SearchRepository.PREF_QUERY, Context.MODE_PRIVATE)
    val prefUserLike: SharedPreferences =
        application.getSharedPreferences(SearchRepository.PREF_LIKE_ITEM, Context.MODE_PRIVATE)

}
package com.example.imagesearchpageapp.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 *  뷰모델 내에서 처리못하고 해당 액티비티에서 처리해야될 기능들의 인스턴스를 저장
 */
data class AppData(val application: Application) {
    val prefQuery: SharedPreferences =
        application.getSharedPreferences(SearchRepository.PREF_QUERY, Context.MODE_PRIVATE)
    val prefUserLike: SharedPreferences =
        application.getSharedPreferences(SearchRepository.PREF_LIKE_ITEM, Context.MODE_PRIVATE)
}
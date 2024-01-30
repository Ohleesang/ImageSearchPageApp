package com.example.imagesearchpageapp.data


import android.app.Application
import com.example.imagesearchpageapp.data.UserData

class SearchRepository(application: Application) {

    //SharedPreferences 기능들 정의 되어 있는 클래스
    val userData = UserData(application)

    companion object {
        const val MAX_SEARCH_VIDEO = 15
        const val MAX_SEARCH_IMAGE = 50
    }
}
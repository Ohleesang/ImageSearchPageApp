package com.example.imagesearchpageapp


import android.app.Application
import com.example.imagesearchpageapp.data.UserData

class SearchRepository(application: Application) {
    val userData = UserData(application)
}
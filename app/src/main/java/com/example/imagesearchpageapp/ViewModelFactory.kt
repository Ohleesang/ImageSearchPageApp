package com.example.imagesearchpageapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearchpageapp.data.SearchRepository
import com.example.imagesearchpageapp.ui.mypage.MyPageViewModel
import com.example.imagesearchpageapp.ui.search.SearchViewModel

/**
 *  Viewmodel 에 repoitory 연결하기 위한 클래스
 *  해당 viewmodel의 종류에 따라 연결이 가능
 */
class ViewModelFactory(private val repository: SearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MyPageViewModel::class.java) -> {
                MyPageViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
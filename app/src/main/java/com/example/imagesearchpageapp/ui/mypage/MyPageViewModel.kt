package com.example.imagesearchpageapp.ui.mypage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchpageapp.SearchRepository
import com.example.imagesearchpageapp.data.Item
import com.example.imagesearchpageapp.data.UserData

class MyPageViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _likeList = MutableLiveData<List<Item>?>()
    val likeList: LiveData<List<Item>?> get() = _likeList


    fun initLikeList(){
        val list = searchRepository.userData.getUserLikeData()
        _likeList.value = list
    }
    fun addLikeList(item: Item){
        item.isLike = !item.isLike
        val list = if(likeList.value.isNullOrEmpty()) mutableListOf<Item>()
        else likeList.value!!.toMutableList()
        list.add(item)
        searchRepository.userData.addUserLikeData(item)
        _likeList.value = list
    }

    fun removeLikeList(item:Item){
        item.isLike =!item.isLike
        val list = likeList.value?.toMutableList()
        list?.remove(item)
        searchRepository.userData.deleteUserLikeData(item)
        _likeList.value = list
    }
}
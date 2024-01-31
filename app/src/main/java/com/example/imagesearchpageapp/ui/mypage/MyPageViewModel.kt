package com.example.imagesearchpageapp.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchpageapp.data.SearchRepository
import com.example.imagesearchpageapp.data.Item

class MyPageViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _likeList = MutableLiveData<List<Item>?>()
    val likeList: LiveData<List<Item>?> get() = _likeList


    fun initLikeList(){
        val list = searchRepository.getUserLikeData()
        _likeList.value = list
    }
    fun addLikeList(item: Item){
        item.isLike = !item.isLike
        val list = if(likeList.value.isNullOrEmpty()) mutableListOf<Item>()
        else likeList.value!!.toMutableList()
        list.add(item)
        searchRepository.addUserLikeData(item)
        _likeList.value = list
    }

    fun removeLikeList(item:Item){
        item.isLike =!item.isLike
        val list = likeList.value?.toMutableList()
        list?.remove(item)
        searchRepository.deleteUserLikeData(item)
        _likeList.value = list
    }
}
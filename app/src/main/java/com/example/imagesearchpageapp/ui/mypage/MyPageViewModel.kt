package com.example.imagesearchpageapp.ui.mypage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchpageapp.data.Item
import com.example.imagesearchpageapp.data.UserData

class MyPageViewModel : ViewModel() {

    private val _likeList = MutableLiveData<List<Item>?>()
    val likeList: LiveData<List<Item>?> get() = _likeList


    fun initLikeList(context: Context){
        val list = UserData(context).getUserLikeData()
        _likeList.value = list
    }
    fun addLikeList(context: Context,item: Item){
        item.isLike = !item.isLike
        val list = if(likeList.value.isNullOrEmpty()) mutableListOf<Item>()
        else likeList.value!!.toMutableList()
        list.add(item)
        UserData(context).addUserLikeData(item)
        _likeList.value = list
    }

    fun removeLikeList(context: Context,item:Item){
        item.isLike =!item.isLike
        val list = likeList.value?.toMutableList()
        list?.remove(item)
        UserData(context).deleteUserLikeData(item)
        _likeList.value = list
    }
}
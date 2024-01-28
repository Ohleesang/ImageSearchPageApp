package com.example.imagesearchpageapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchpageapp.Item
import com.example.imagesearchpageapp.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel : ViewModel() {

    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> get() = _itemList



    //이미지 검색
    fun fetchSearchImage(query: String?) : Boolean {
        if(query.isNullOrBlank()) return false
        CoroutineScope(Dispatchers.Main).launch {
            val documents = searchImages(query)
            val newItems = mutableListOf<Item>()
            documents.forEach {
                newItems.add(Item(false,it))
            }
            _itemList.value = newItems
        }
        return true
    }
    private suspend fun searchImages(query: String) = withContext(Dispatchers.IO) {
        RetrofitInstance.api.searchImages(query = query).documents
    }
}
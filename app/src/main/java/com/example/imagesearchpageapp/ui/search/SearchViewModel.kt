package com.example.imagesearchpageapp.ui.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchpageapp.data.Item
import com.example.imagesearchpageapp.data.UserData
import com.example.imagesearchpageapp.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel : ViewModel() {

    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> get() = _itemList

    private val _savedQuery = MutableLiveData<String?>()
    val savedQuery: LiveData<String?> get() = _savedQuery


    /**
     *  이미지 검색
     */
    fun fetchSearchImage(query: String?): Boolean {
        if (query.isNullOrBlank()) return false
        CoroutineScope(Dispatchers.Main).launch {
            val newItems = searchImages(query)
            _itemList.value = newItems
        }
        return true
    }

    private suspend fun searchImages(query: String) = withContext(Dispatchers.IO) {
        val documents = RetrofitInstance.api.searchImages(query = query).documents
        val newItems = mutableListOf<Item>()
        documents.forEach {
            newItems.add(Item(false, it))
        }
        newItems.sortByDescending { it.document.dateTime }
        newItems
    }

    /**
     * 검색어 저장
     */
    fun setSavedQuery(context: Context,query :String?){
        _savedQuery.value = query
        UserData(context).saveQueryData(query)
    }

    fun initSavedQuery(context: Context){
        val savedUserQuery = UserData(context).loadQueryData()
        _savedQuery.value = savedUserQuery
    }
}
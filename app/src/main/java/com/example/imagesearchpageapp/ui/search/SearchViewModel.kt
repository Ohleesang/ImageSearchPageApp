package com.example.imagesearchpageapp.ui.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearchpageapp.SearchRepository
import com.example.imagesearchpageapp.data.Item
import com.example.imagesearchpageapp.retrofit.RetrofitInstance
import com.example.imagesearchpageapp.retrofit.data.image.Document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> get() = _itemList

    private val _savedQuery = MutableLiveData<String?>()
    val savedQuery: LiveData<String?> get() = _savedQuery

    private val _scrolledY = MutableLiveData<Int>()
    val scrolledY: LiveData<Int> get() = _scrolledY

    /**
     *  이미지 검색
     */
    fun fetchSearchImage(query: String?): Boolean {
        if (query.isNullOrBlank()) return false
        viewModelScope.launch {
            val newImageItems = searchImages(query)
            val newVideoItems = searchVideos(query)
            var newItems = newImageItems + newVideoItems
            newItems = newItems.sortedByDescending { it.document.dateTime }
            _itemList.value = newItems
        }
        return true
    }

    private suspend fun searchVideos(query: String) = withContext(Dispatchers.IO) {
        val documents = RetrofitInstance.api.searchVideos(query = query).documents
        val newItems = mutableListOf<Item>()
        documents.forEach {
            newItems.add(Item(false, Document(it.dateTime,"[video] " + it.title,it.thumbNailUrl)))
        }
        newItems
    }
    private suspend fun searchImages(query: String) = withContext(Dispatchers.IO) {
        val documents = RetrofitInstance.api.searchImages(query = query).documents
        val newItems = mutableListOf<Item>()
        documents.forEach {
            newItems.add(Item(false, Document(it.dateTime,"[image] " + it.siteName,it.thumbNailUrl)))
        }
        newItems
    }

    /**
     *  좋아요 처리
     */
    fun uncheckedLikeItem(item: Item) {
        val list = _itemList.value?.find { it.document == item.document }
        if (list != null) list.isLike = false
    }

    /**
     * 검색어 저장
     */
    fun setSavedQuery(query: String?) {
        _savedQuery.value = query
        searchRepository.userData.saveQueryData(query)
    }

    fun initSavedQuery() {
        val savedUserQuery = searchRepository.userData.loadQueryData()
        _savedQuery.value = savedUserQuery
    }

    /**
     * 스크롤 위치
     */
    fun checkScrollY(y :Int){
        _scrolledY.value = y
    }
}
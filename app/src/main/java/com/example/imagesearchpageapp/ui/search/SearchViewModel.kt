package com.example.imagesearchpageapp.ui.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearchpageapp.data.SearchRepository
import com.example.imagesearchpageapp.data.Item
import com.example.imagesearchpageapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> get() = _itemList

    private val _savedQuery = MutableLiveData<String?>()
    val savedQuery: LiveData<String?> get() = _savedQuery

    private val _scrolledY = MutableLiveData<Int>()
    val scrolledY: LiveData<Int> get() = _scrolledY

    private var searchPage = 1

    private val _snackBarStr = MutableLiveData<String>()
    val snackBarStr :LiveData<String> get() = _snackBarStr

    val isViewSnackBar = MutableLiveData<Boolean>()


    /**
     *  이미지 검색
     */


    // 검색 버튼 및 검색을 처음 시동
    fun fetchSearchImage(query: String?): Boolean {
        searchPage = 1
        if (query.isNullOrBlank()) return false
        viewModelScope.launch {
            val newImageItems = async { searchImages(query, searchPage) }
            val newVideoItems = async { searchVideos(query, searchPage) }
            var newItems = newImageItems.await() + newVideoItems.await()
            _itemList.value = newItems
            _itemList.value = _itemList.value?.sortedByDescending { it.itemDocument.dateTime }
        }
        return true
    }

    private suspend fun searchVideos(query: String, page: Int) = withContext(Dispatchers.IO) {
        val documents = RetrofitInstance.api.searchVideos(query = query, page = page).documents
        val newItems = mutableListOf<Item>()
        documents.forEach {
            val itemDocument = it.toItemDocument()
            newItems.add(Item(false, itemDocument))
        }
        newItems
    }

    private suspend fun searchImages(query: String, page: Int) = withContext(Dispatchers.IO) {
        val documents = RetrofitInstance.api.searchImages(query = query, page = page).documents
        val newItems = mutableListOf<Item>()
        documents.forEach {
            val itemDocument = it.toItemDocument()
            newItems.add(Item(false, itemDocument))
        }
        newItems
    }

    // 추가 검색 부분
    fun scrolledOverSearch(query: String?): Boolean {
        if (query.isNullOrBlank()) return false

        searchPage++
        if (searchPage > SearchRepository.MAX_SEARCH_IMAGE) {
            //IMAGE 페이지 수 초과 하면 아무 것도 실행 x
            viewSnackBar(" 이미지 검색 페이지 초과 ! \n 더이상 검색 할수 없어요.")
            return false
        }
        else if (searchPage > SearchRepository.MAX_SEARCH_VIDEO) {
            //VIDEO 페이지 수 초과 하면 IMAGE 만 검색
            viewSnackBar(" VIDEO 검색 페이지 수 초과! \n 이미지만 검색 됩니다.")
            viewModelScope.launch {
                var newImageItems = searchImages(query, searchPage)
                newImageItems =
                    newImageItems.sortedByDescending { it.itemDocument.dateTime }.toMutableList()
                _itemList.value = _itemList.value?.plus(newImageItems)
            }
        } else {
            //IMAGE,VIDEO 동시 실행
            viewModelScope.launch {
                val newImageItems = async { searchImages(query, searchPage) }
                val newVideoItems = async { searchVideos(query, searchPage) }
                var newItems = newImageItems.await() + newVideoItems.await()
                newItems = newItems.sortedByDescending { it.itemDocument.dateTime }
                _itemList.value = _itemList.value?.plus(newItems)
            }
        }
        return true
    }

    /**
     *  좋아요(취소) 처리
     *  좋아요 처리는 객체를 참조 해서 자동 으로 구성됨
     */
    fun checkLikeItemList(newData: List<Item>, liveDataLikeList: LiveData<List<Item>?>): List<Item> {

        val likeItemList = liveDataLikeList.value?.toList()

        likeItemList?.forEach { item ->
            newData.find { it==item }?.isLike = true
        }
        return newData
    }

    fun disLikeItem(item: Item) {
        val findItem = _itemList.value?.find { it.itemDocument == item.itemDocument }
        if (findItem != null) findItem.isLike = false
    }

    /**
     * 검색어 저장
     */
    fun setSavedQuery(query: String?) {
        _savedQuery.value = query
        searchRepository.saveQueryData(query)
    }

    fun initSavedQuery() {
        val savedUserQuery = searchRepository.loadQueryData()
        _savedQuery.value = savedUserQuery
    }

    /**
     * 스크롤 위치
     */
    fun checkScrollY(y: Int) {
        _scrolledY.value = y
    }

    /**
     * 스낵바 관련 처리
     */
    private fun viewSnackBar(str : String){
        isViewSnackBar.value = true
        _snackBarStr.value = str
    }

}
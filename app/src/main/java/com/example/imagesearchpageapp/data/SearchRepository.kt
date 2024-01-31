package com.example.imagesearchpageapp.data


import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.UUID

/**
 *  ViewModel 이 참조할 데이터 들 및 기능 구현
 */
class SearchRepository(appData: AppData) {

    companion object {
        const val MAX_SEARCH_VIDEO = 15
        const val MAX_SEARCH_IMAGE = 50

        const val PREF_QUERY = "pref_query"
        const val SAVE_QUERY = "save_query"

        const val PREF_LIKE_ITEM = "pref_like_item"
    }

    private val prefQuery: SharedPreferences = appData.prefQuery
    private val prefUserLike: SharedPreferences = appData.prefUserLike

    /**
     *  검색 쿼리 값 저장
     */

    fun saveQueryData(query: String?) {

        val pref = prefQuery
        val edit = pref.edit()// 수정 모드
        //(key,value) 형태
        edit.putString(SAVE_QUERY, query)
        edit.apply() // 저장 완료

    }

    fun loadQueryData(): String {
        val pref = prefQuery
        return pref.getString(SAVE_QUERY, "") ?: ""
    }

    /**
     *  내 보관함 저장
     *  json 형식으로 value 값으로 저장 하여 관리
     *  key 값은 UUID 메소드 이용 하여 임의로 저장
     */


    // 저장된 유저 데이터(좋아요) 반환하기
    fun getUserLikeData(): List<Item> {
        val pref = prefUserLike
        val values = pref.all.values

        //Document 객체로 전환
        val documentList = mutableListOf<ItemDocument>()
        values.forEach {
            documentList.add(Gson().fromJson(it.toString(), ItemDocument::class.java))
        }

        //Item Instance 생성
        val itemList = mutableListOf<Item>()
        documentList.forEach { document ->
            itemList.add(Item(true, document))
        }
        itemList.sortByDescending { it.itemDocument.dateTime }
        return itemList
    }

    // 좋아요 데이터 추가
    fun addUserLikeData(item: Item) {
        val pref = prefUserLike
        val json = Gson().toJson(item.itemDocument)
        val edit = pref.edit()
        val key = UUID.randomUUID().toString()
        edit.putString(key, json)
        edit.apply()
    }

    //좋아요 데이터 제거
    fun deleteUserLikeData(item: Item) {
        val pref = prefUserLike
        val json = Gson().toJson(item.itemDocument)
        val map = pref.all
        //key 값을 찾는다
        val key = map.entries.find { it.value == json }?.key
        if (key != null) {
            val edit = pref.edit()
            edit.remove(key)
            edit.apply()
        }
    }

}
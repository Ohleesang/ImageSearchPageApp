package com.example.imagesearchpageapp.data

import android.app.Application
import android.content.Context
import com.example.imagesearchpageapp.retrofit.data.image.Document
import com.google.gson.Gson
import java.util.UUID

/**
 *  SharedPreference 를 이용하여 UserData를 관리하는 클래스
 */
class UserData(private val application: Application) {
    companion object {
        const val USER_PREF = "user_pref"
        const val SAVE_QUERY = "save_query"

        const val USER_LIKE = "user_like"
    }


    /**
     *  내 보관함 저장
     *  json 형식으로 value 값으로 저장 하여 관리
     *  key 값은 UUID 메소드 이용 하여 임의로 저장
     */


    // 저장된 유저 데이터(좋아요) 반환하기
    fun getUserLikeData(): List<Item> {
        val pref = application.getSharedPreferences(USER_LIKE, Context.MODE_PRIVATE)
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
        val pref = application.getSharedPreferences(USER_LIKE, Context.MODE_PRIVATE)
        val json = Gson().toJson(item.itemDocument)
        val edit = pref.edit()
        val key = UUID.randomUUID().toString()
        edit.putString(key, json)
        edit.apply()
    }

    //좋아요 데이터 제거
    fun deleteUserLikeData(item: Item) {
        val pref = application.getSharedPreferences(USER_LIKE, Context.MODE_PRIVATE)
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

    /**
     *  검색 쿼리 값 저장
     */
    fun saveQueryData(query: String?) {

        val pref = application.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
        val edit = pref.edit()// 수정 모드
        //(key,value) 형태
        edit.putString(SAVE_QUERY, query)
        edit.apply() // 저장 완료

    }

    fun loadQueryData(): String {
        val pref = application.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
        return pref.getString(SAVE_QUERY, "") ?: ""
    }

}
package com.example.imagesearchpageapp

import android.content.Context

class UserData(private val context: Context) {
    companion object {
        const val SAVE_QUERY = "save_query"
        const val USER_PREF = "user_pref"
    }

    fun saveData(query: String) {

        val pref = context.getSharedPreferences(USER_PREF, 0)
        val edit = pref.edit()// 수정 모드
        //(key,value) 형태
        edit.putString(SAVE_QUERY, query)
        edit.apply() // 저장 완료

    }

    fun loadData(): String {
        val pref = context.getSharedPreferences(USER_PREF, 0)
        return pref.getString(SAVE_QUERY, "") ?: ""
    }

}
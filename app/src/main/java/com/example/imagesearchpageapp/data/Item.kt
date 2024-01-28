package com.example.imagesearchpageapp.data

import com.example.imagesearchpageapp.retrofit.data.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Item(
    var isLike: Boolean = false,
    var document: Document,
) {
    init {
        document.dateTime = parsingDateTime(document.dateTime)
        if (document.siteName.isNullOrBlank()) document.siteName = "웹문서"
    }

    private fun parsingDateTime(oldDateTime: String): String {


        // oldDateTime 을 LocalDateTime 객체로 파싱
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val parsedDateTime = LocalDateTime.parse(oldDateTime, formatter)

        // LocalDateTime 값을 새로 지정한 Formatter 값으로 바꿔서 Return
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return parsedDateTime.format(outputFormatter)
    }
}

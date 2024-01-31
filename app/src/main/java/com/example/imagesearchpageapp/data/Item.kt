package com.example.imagesearchpageapp.data

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 *  2개의 검색 결과를 ItemDocument 로 통합
 */
data class ItemDocument(
    var dateTime: String,
    var title: String,
    val thumbNailUrl: String,
)

/**
 *  RecyclerVIew 에 보여질 Item
 *  인스턴스 가 생성될 때, dateTime 파싱 및 title 빈 문자열 처리
 */
data class Item(
    var isLike: Boolean = false,
    var itemDocument: ItemDocument,
) {
    init {
        itemDocument.dateTime = parsingDateTime(itemDocument.dateTime)
        if (itemDocument.title.isNullOrBlank()) itemDocument.title = "웹문서"
    }

    private fun parsingDateTime(oldDateTime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return try {
            // oldDateTime 을 LocalDateTime 객체로 파싱
            val parsedDateTime = LocalDateTime.parse(oldDateTime, formatter)

            // LocalDateTime 값을 새로 지정한 Formatter 값으로 바꿔서 Return
            parsedDateTime.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            // oldDateTime 문자열이 올바른 형식을 가지고 있지 않는 경우
            // 원래의 oldDateTime 문자열을 그대로 반환
            oldDateTime
        }
    }

    /**
     *  해당 인스턴스와 다른 인스턴스의 실질적으로 같은지 판별
     */
    override fun equals(other: Any?): Boolean {
        other as Item
        val otherItemDocument = other.itemDocument
        val isSame =
            otherItemDocument == this.itemDocument
        if (isSame) return true

        return false
    }
}

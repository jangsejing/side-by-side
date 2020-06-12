package com.ddd.airplane.data.response

/**
 * 편성표
 */
data class ScheduleData(
    val name: String,
    val typeList: List<Type>
) {

    // ksb, sbs ...
    // 뉴스, 스포츠 ...
    data class Type(
        val name: String
    )

}

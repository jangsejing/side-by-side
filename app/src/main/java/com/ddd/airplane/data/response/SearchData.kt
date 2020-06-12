package com.ddd.airplane.data.response

import com.ddd.airplane.data.response.chat.ProgramData

/**
 * 검색
 */
data class SearchData(
    val items: List<ProgramData>?,
    val pageInfo: PageInfoData?
)
package com.ddd.airplane.data.response

import com.ddd.airplane.data.response.chat.ProgramData

/**
 * 구독한 주제 채팅방 조회
 */
data class SubscribeData(
    val items: List<ProgramData>?,
    val pageInfo: PageInfoData? = null // 페이징 정보
)
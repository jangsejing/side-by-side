package com.ddd.airplane.data.response.chat

/**
 * 프로그램 정보
 */
open class ProgramData(
    val roomId: Long? = 0,
    val roomUserCount: Int? = 0,
    val subjectId: Long? = 0,
    val subjectName: String? = null,
    val subjectDescription: String? = null,
    val subjectSubscribeCount: Int? = 0,
    val subjectThumbnailUrl: String? = null
)
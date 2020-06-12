package com.ddd.airplane.data.response.home

/**
 * 홈 데이터
 */
data class BannerData(
    var title: String? = null,
    var thumbnailUrl: String? = null,
    val roomId: Long?
)

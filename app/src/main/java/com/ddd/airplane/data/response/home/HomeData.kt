package com.ddd.airplane.data.response.home

import com.ddd.airplane.common.consts.Home

/**
 * 홈 데이터
 */
data class HomeData(
    var list: ArrayList<ItemData>? = null
) {
    data class ItemData(
        var homeStyle: Home.Style = Home.Style.EMPTY,
        val spanCount: Int = 0,
        val style: String? = null,
        var title: String? = null,
        var item: Any? = null
    )
}
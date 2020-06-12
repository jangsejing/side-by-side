package com.ddd.airplane.common.consts

object Home {

    /**
     * 홈 스타일
     */
    enum class Style(val style: String) {
        TOP_BANNER("topBanner"),
        RECTANGLE_BANNER("rectangleBanner"),
        HORIZONTAL("horizontal"),
        GRID("grid"), // Grid (Span Count 는 서버에서 내려줌)
        RANK("rank"),
        EMPTY("empty")
    }
}

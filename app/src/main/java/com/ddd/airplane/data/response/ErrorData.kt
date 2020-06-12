package com.ddd.airplane.data.response

/**
 * 통신 에러
 */
data class ErrorData(
    val timestamp: String? = null,
    val status: Int? = 0,
    val error: String? = null,
    val error_description: String? = null,
    val message: String? = null,
    val path: String? = null
)
package com.ddd.airplane.data.request

/**
 * 회원가입
 */
data class SignUpRequest(
    val email: String,
    val password: String,
    val nickname: String
)
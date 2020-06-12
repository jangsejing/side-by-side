package com.ddd.airplane.common.utils

import java.util.regex.Pattern

object StringUtils {

    // TODO unit test 때문에 Patterns 를 못씀 > 임시조치
    private val EMAIL_ADDRESS = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    /**
     * 이메일 유효성 검사
     *
     * @param email
     * @return
     */
    fun isValidEmail(email: String?): Boolean =
        if (email.isNullOrEmpty()) {
            false
        } else {
            EMAIL_ADDRESS.matcher(email).matches()
        }

    /**
     * 이메일 유효성 검사
     *
     * @param password
     * @return
     */
    fun isValidPassword(password: String?): Boolean =
        if (password.isNullOrEmpty()) {
            false
        } else {
            password.length >= 4
        }
}
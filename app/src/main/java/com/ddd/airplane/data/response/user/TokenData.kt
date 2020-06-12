package com.ddd.airplane.data.response.user

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 토큰발급, 로그인
 */
data class TokenData(
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("token_type") val tokenType: String?,
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("expires_in") val expiresIn: String?,
    @SerializedName("scope") val scope: String?
)
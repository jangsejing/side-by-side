package com.ddd.airplane.repository.network

import com.ddd.airplane.common.base.BaseRepository
import com.ddd.airplane.common.interfaces.OnNetworkStatusListener
import com.ddd.airplane.common.interfaces.OnResponseListener
import com.ddd.airplane.common.manager.TokenManager
import com.ddd.airplane.data.request.SignUpRequest
import com.ddd.airplane.data.response.ErrorData
import com.ddd.airplane.data.response.user.TokenData
import com.ddd.airplane.repository.network.retrofit.RetrofitManager
import com.ddd.airplane.repository.network.retrofit.request

/**
 * UserRepository
 */
object UserRepository : BaseRepository() {

    private val service = RetrofitManager.user

    fun setOnNetworkStatusListener(status: OnNetworkStatusListener?) = apply {
        this.status = status
    }

    fun setOnErrorListener(error: ((ErrorData?) -> Unit)?) = apply {
        this.error = error
    }

    /**
     * 계정생성
     */
    suspend fun postAccounts(signUpRequest: SignUpRequest) =
        service.postAccounts(signUpRequest).request(status, error)

    /**
     * 토큰발급
     */
    suspend fun postAccessToken(email: String, password: String) =
        service.postAccessToken(email, password, "password").request(status, error)

    /**
     * 토큰 새로고침
     */
    suspend fun postTokenRefresh(refreshToken: String?) =
        service.postTokenRefreshCoruoutine(refreshToken, TokenManager.REFRESH_TOKEN)
            .request(status, error)

    /**
     * 유저정보 조회
     */
    suspend fun getAccounts(email: String) =
        service.getAccounts(email).request(status, error)

}
package com.ddd.airplane.common.manager

import com.ddd.airplane.common.interfaces.OnNetworkStatusListener
import com.ddd.airplane.repository.network.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * 토큰 관리
 */
object TokenManager {

    private const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
    private const val TOKEN_TYPE = "token_type"

    /**
     * 토큰 여부
     */
    fun isExist(): Boolean {
        return !accessToken.isNullOrBlank()
    }

    /**
     * Set Token
     */
    fun setToken(accessToken: String?, refreshToken: String?, tokenType: String?) {
        PreferencesManager.run {
            putValue(ACCESS_TOKEN, accessToken)
            putValue(REFRESH_TOKEN, refreshToken)
            putValue(TOKEN_TYPE, tokenType)
        }
    }

    /**
     * Remove
     *
     */
    fun removeToken() {
        PreferencesManager.run {
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            remove(TOKEN_TYPE)
        }
    }

    /**
     * Access Token
     */
    val accessToken: String?
        get() = PreferencesManager.getString(ACCESS_TOKEN)

    /**
     * Access Token
     */
    val refreshToken: String?
        get() = PreferencesManager.getString(REFRESH_TOKEN)

    /**
     * Token Type
     * Authorization 에서 사용
     */
    val tokenType: String?
        get() = PreferencesManager.getString(TOKEN_TYPE)

    /**
     * 토큰발급
     */
    suspend fun getAccessToken(
        status: OnNetworkStatusListener?,
        email: String,
        password: String,
        listener: ((Boolean) -> Unit)? = null
    ) {

        // 토큰 지우고 발급
        removeToken()

        UserRepository
            .setOnNetworkStatusListener(
                status?.showProgress(true)
            )
            .setOnErrorListener {
                listener?.invoke(false)
            }
            .postAccessToken(email, password)
            ?.let { response ->
                setToken(response.accessToken, response.refreshToken, response.tokenType)
                listener?.invoke(true)
            }
    }

    /**
     * 토큰발급
     */
    suspend fun getAccessToken(
        status: OnNetworkStatusListener?,
        scope: CoroutineScope,
        email: String,
        password: String
    ) = withContext(scope.coroutineContext) {

        // 토큰 지우고 발급
        removeToken()

        var isSuccess = false
        UserRepository
            .setOnNetworkStatusListener(
                status?.showProgress(true)
            )
            .setOnErrorListener {

            }
            .postAccessToken(email, password)
            ?.let { response ->
                setToken(response.accessToken, response.refreshToken, response.tokenType)
                isSuccess = true
            }

        isSuccess
    }

//    /**
//     * 토큰 재발급
//     */
//    fun onRefreshToken(
//        status: OnNetworkStatusListener?,
//        listener: ((Boolean) -> Unit)? = null
//    ) {
//
//        Timber.d(">> onRefreshToken")
//
//        refreshToken?.let { token ->
//
//            // 토큰 지우고 발급
//            removeToken()
//
//            RetrofitManager
//                .user
//                .postTokenRefresh(token, REFRESH_TOKEN)
//                .request(status, object : OnResponseListener<TokenData> {
//
//                    override fun onSuccess(response: TokenData) {
//                        // 토큰 세팅
//                        response.let {
//                            setToken(it.accessToken, it.refreshToken, it.tokenType)
//                        }
//                        listener?.invoke(true)
//                    }
//
//                    override fun onError(error: ErrorData) {
//                        listener?.invoke(false)
//                    }
//                })
//        } ?: run {
//            listener?.invoke(false)
//        }
//    }

    /**
     * 토큰 재발급
     */
    suspend fun onRefreshToken(
        status: OnNetworkStatusListener?,
        scope: CoroutineScope
    ): Boolean {
        Timber.d(">> onRefreshToken")
        return withContext(scope.coroutineContext) {
            refreshToken?.let { refreshToken ->

                // 토큰 지우고 발급
                removeToken()

                val response = UserRepository
                    .setOnNetworkStatusListener(
                        status?.showProgress(true)
                    )
                    .postTokenRefresh(refreshToken)

                Timber.d(">> 토큰 : $response")
                response?.let { token ->
                    // 토큰 갱신 성공
                    setToken(token.accessToken, token.refreshToken, token.tokenType)
                    true
                } ?: let {
                    // 토큰 갱신 실패 > 로그인
                    false
                }

            } ?: let {
                // 토큰 없음 > 로그인
                false
            }
        }
    }

}
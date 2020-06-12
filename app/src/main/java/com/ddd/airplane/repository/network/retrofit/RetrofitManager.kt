package com.ddd.airplane.repository.network.retrofit

import com.ddd.airplane.BuildConfig
import com.ddd.airplane.common.manager.TokenManager
import com.ddd.airplane.repository.network.config.ServerInfo
import com.ddd.airplane.repository.network.service.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * 레트로핏 매니저
 *
 * @author jess
 */
object RetrofitManager {

    // 유저
    val user: UserService = create(UserService::class.java)
    // 일반
    val general: GeneralService = create(GeneralService::class.java)
    // 채팅
    val chat: ChatService = create(ChatService::class.java)
    // 좋아요
    val like: LikeService = create(LikeService::class.java)
    // 구독
    val subscribe: SubscribeService = create(SubscribeService::class.java)

    fun init() {

    }

    /**
     * Retrofit Service
     *
     * @param T
     * @param classes
     * @return
     */
    private fun <T> create(classes: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(ServerInfo.DOMAIN.REAL.domain)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient())
            .build()
        return retrofit.create(classes)
    }

    /**
     * HTTP Client 생성
     */
    private fun okHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                getHeader(chain)
            }
            addInterceptor(logger())
        }
        return builder.build()
    }

    /**
     * Header
     *q
     * @param chain
     * @return
     */
    private fun getHeader(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", getToken())
            .addHeader("VersionName", BuildConfig.VERSION_NAME)
            .addHeader("VersionCode", BuildConfig.VERSION_CODE.toString())
            .addHeader("ApplicationId", BuildConfig.APPLICATION_ID)
            .addHeader("IsDebug", BuildConfig.DEBUG.toString())
            .build()
        return chain.proceed(request)
    }

    /**
     * HTTP Logger
     */
    private fun logger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    // 토큰 정보
    private fun getToken(): String = if (TokenManager.isExist()) {
        "${TokenManager.tokenType} ${TokenManager.accessToken}"
    } else {
        "Basic Y2xpZW50SWQ6Y2xpZW50U2VjcmV0"
    }
}
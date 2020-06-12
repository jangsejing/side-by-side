package com.ddd.airplane.repository.network

import com.ddd.airplane.common.base.BaseRepository
import com.ddd.airplane.common.interfaces.OnNetworkStatusListener
import com.ddd.airplane.data.response.ErrorData
import com.ddd.airplane.repository.network.retrofit.RetrofitManager
import com.ddd.airplane.repository.network.retrofit.request

/**
 * GeneralRepository for Coroutine
 */
object SubscribeRepository : BaseRepository() {

    private val service = RetrofitManager.subscribe

    fun setOnNetworkStatusListener(status: OnNetworkStatusListener?) = apply {
        this.status = status
    }

    fun setOnErrorListener(error: ((ErrorData?) -> Unit)?) = apply {
        this.error = error
    }

    /**
     * 방송 구독
     */
    suspend fun postSubscribe(subjectId: Long) =
        service.postSubscribe(subjectId).request(status, error)


    /**
     * 방송 구독 취소
     */
    suspend fun deleteSubscribe(subjectId: Long) =
        service.deleteSubscribe(subjectId).request(status, error)


    /**
     * 구독 방송 리스트
     */
    suspend fun getSubscribe(pageNum: Int = 1) =
        service.getSubscribe(pageNum).request(status, error)
}
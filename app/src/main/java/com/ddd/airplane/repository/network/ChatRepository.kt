package com.ddd.airplane.repository.network

import com.ddd.airplane.common.base.BaseRepository
import com.ddd.airplane.common.interfaces.OnNetworkStatusListener
import com.ddd.airplane.data.response.ErrorData
import com.ddd.airplane.repository.network.retrofit.RetrofitManager
import com.ddd.airplane.repository.network.retrofit.request

/**
 * ChatRepository
 */
object ChatRepository : BaseRepository() {

    private val service = RetrofitManager.chat

    fun setOnNetworkStatusListener(status: OnNetworkStatusListener?) = apply {
        this.status = status
    }

    fun setOnErrorListener(error: ((ErrorData?) -> Unit)?) = apply {
        this.error = error
    }

    /**
     * 홈 리스트
     *
     */
    suspend fun getRoomInfo(roomId: Long) =
        service.getRoomInfo(roomId).request(status, error)


    /**
     * 채팅 메세지 조회
     */
    suspend fun getRoomMessages(
        roomId: Long,
        baseMessageId: Int,
        size: Int,
        direction: String
    ) =
        service.getRoomMessages(roomId, baseMessageId, size, direction).request(status, error)
}
package com.ddd.airplane.repository.network

import com.ddd.airplane.common.base.BaseRepository
import com.ddd.airplane.common.interfaces.OnNetworkStatusListener
import com.ddd.airplane.data.response.ErrorData
import com.ddd.airplane.repository.network.retrofit.RetrofitManager
import com.ddd.airplane.repository.network.retrofit.request
import kotlinx.coroutines.Dispatchers

/**
 * GeneralRepository
 */
object GeneralRepository : BaseRepository() {

    private val service = RetrofitManager.general

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
    suspend fun getHome() = service.getHome().request(status)

    /**
     * 검색
     *
     * @param query
     * @param pageNum
     */
    suspend fun getSearch(query: String, pageNum: Int = 1) =
        service.getSearch(query, pageNum).request(status, error)
}
package com.ddd.airplane.repository.network

import com.ddd.airplane.common.base.BaseRepository
import com.ddd.airplane.common.interfaces.OnNetworkStatusListener
import com.ddd.airplane.data.response.ErrorData
import com.ddd.airplane.repository.network.retrofit.RetrofitManager

/**
 * LikeRepository
 */
object LikeRepository : BaseRepository() {

    private val service = RetrofitManager.like

    fun setOnNetworkStatusListener(status: OnNetworkStatusListener?) = apply {
        this.status = status
    }

    fun setOnErrorListener(error: ((ErrorData?) -> Unit)?) = apply {
        this.error = error
    }


}
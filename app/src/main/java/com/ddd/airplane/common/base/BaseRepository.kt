package com.ddd.airplane.common.base

import com.ddd.airplane.common.interfaces.OnNetworkStatusListener
import com.ddd.airplane.data.response.ErrorData

/**
 * BaseRepository
 */
open class BaseRepository {

    open var status: OnNetworkStatusListener? = null
    open var error: ((ErrorData?) -> Unit)? = null

}
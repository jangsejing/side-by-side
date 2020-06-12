package com.ddd.airplane.common.interfaces

import com.ddd.airplane.data.response.ErrorData

interface OnResponseListener<T> {

    fun onSuccess(response: T)

    fun onError(
        error: ErrorData
    )
}
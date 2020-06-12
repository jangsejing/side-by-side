package com.ddd.airplane.common.interfaces

import android.content.Context
import com.ddd.airplane.data.response.ErrorData

interface OnNetworkStatusListener {

    val context: Context

    fun showProgress(isProgress: Boolean) = apply { }

    fun showErrorDialog(error: ErrorData?) = apply { }

    fun showToast(message: String?) = apply { }

}
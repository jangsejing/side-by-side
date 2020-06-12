package com.ddd.airplane.common.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ddd.airplane.common.interfaces.OnNetworkStatusListener
import com.ddd.airplane.data.response.ErrorData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) :
    AndroidViewModel(application),
    OnNetworkStatusListener {

    // progress
    private val _isProgress = MutableLiveData<Boolean>()
    val isProgress: LiveData<Boolean> = _isProgress

    // error
    private val _isErrorDialog = MutableLiveData<ErrorData>()
    val isErrorDialog: LiveData<ErrorData> = _isErrorDialog

    // toast
    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> = _toast

    override val context: Context
        get() = getApplication()

    /**
     * Progress
     *
     * @param isProgress
     */
    override fun showProgress(isProgress: Boolean) = apply {
        _isProgress.postValue(isProgress)
    }

    /**
     * Error Dialog
     *
     * @param error
     */
    override fun showErrorDialog(error: ErrorData?) = apply {

    }

    /**
     * Toast
     *
     * @param message
     */
    override fun showToast(message: String?) = apply {
        message?.let {
            _toast.postValue(message)
        }
    }

    /**
     * CoroutineScope 내부 Exception 처리 Handler
     */
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }

    /**
     * IO Dispatchers
     */
    val ioDispatchers: CoroutineContext
        get() = Dispatchers.IO + coroutineExceptionHandler

    /**
     * UI Dispatchers
     */
    val uiDispatchers: CoroutineContext
        get() = Dispatchers.Main + coroutineExceptionHandler
}
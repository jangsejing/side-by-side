package com.ddd.airplane.repository.network.retrofit

import android.content.Context
import com.ddd.airplane.R
import com.ddd.airplane.common.interfaces.OnNetworkStatusListener
import com.ddd.airplane.common.manager.TokenManager
import com.ddd.airplane.data.response.ErrorData
import com.ddd.airplane.repository.network.config.HttpStatus
import com.ddd.airplane.repository.network.retrofit.RequestManager.parseErrorBody
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber

/**
 * 네트워크 통신
 */
suspend fun <T> Response<T>?.request(
    status: OnNetworkStatusListener? = null,
    errorListener: ((ErrorData?) -> Unit)? = null
): T? {

    status?.showProgress(false)

    val context = status?.context

    this?.let { response ->
        if (!response.isSuccessful) {

            val error = parseErrorBody(context, response.errorBody())

            // 에러파싱 실패
            if (error.status == HttpStatus.UNAUTHORIZED.code) {

                TokenManager.onRefreshToken(status, CoroutineScope(Dispatchers.IO))
                    .let { isSuccess ->
                        Timber.d(">> 토큰갱신 완료 결과 : $isSuccess")
                        if (!isSuccess) {
                            errorListener?.invoke(error)
                        }

                        status?.showToast(
                            context?.getString(
                                if (isSuccess) R.string.error_network_response_retry
                                else R.string.error_network_response_error
                            )
                        )
                    }
            } else {
                status?.showToast(error.message)
                errorListener?.invoke(error)
            }

            return null
        }
    }

    return this?.body()
}

object RequestManager {

    /**
     * 에러 바디 처리
     */
    fun parseErrorBody(context: Context?, errorBody: ResponseBody?): ErrorData {

        // 에러파싱 실패
        val error = ErrorData(
            status = 400,
            error = "error",
            error_description = "Network is error",
            message = context?.getString(R.string.error_network_response_error) ?: ""
        )

        errorBody?.let {
            try {
                val adapter = Gson().getAdapter(ErrorData::class.java)
                return adapter.fromJson(it.string())
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

        return error
    }

    /**
     * ArrayList<LinkedTreeMap<String, Any>> 를 원하는 class 로 변환함
     *
     * @param T
     * @param list
     * @param classOfT
     * @return
     */
    fun <T> convertList(list: ArrayList<LinkedTreeMap<String, Any>>, classOfT: Class<T>): List<T>? {
        val convert = ArrayList<T>()
        list.forEach {
            val json = Gson().toJsonTree(it).asJsonObject
            val banner = Gson().fromJson(json, classOfT)
            convert.add(banner)
        }
        Timber.d(convert.toString())
        return convert
    }

    /**
     * ArrayList<LinkedTreeMap<String, Any>> 를 원하는 class 로 변환함
     *
     * @param T
     * @param data
     * @param classOfT
     * @return
     */
    fun <T> convertData(data: LinkedTreeMap<String, Any>, classOfT: Class<T>): T? {
        val json = Gson().toJsonTree(data).asJsonObject
        val model = Gson().fromJson(json, classOfT)
        Timber.d(model.toString())
        return model
    }
}
package com.ddd.airplane.presenter.recent.viewmodel

import android.app.Application
import com.ddd.airplane.common.base.BaseViewModel

/**
 * 회원가입 ViewModel
 */
class RecentViewModel(application: Application) : BaseViewModel(application) {


    /**
     *  회원가입
     *
     * @param email
     * @param password
     * @param nickName
     */
    fun getRecnts(email: String, password: String, nickName: String) {
//        RetrofitManager
//            .user
//            .postAccounts(SignUpRequest(email, password, nickName))
//            .request(this, object : OnResponseListener<SignUpData> {
//
//                override fun onSuccess(response: SignUpData) {
//                    _isSucceed.postValue(true)
//                }
//
//                override fun onError(error: ErrorData) {
//                    _isSucceed.postValue(false)
//                }
//
//            })
    }
}

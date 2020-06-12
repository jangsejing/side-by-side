package com.ddd.airplane.presenter.signin.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd.airplane.common.base.BaseViewModel
import com.ddd.airplane.common.manager.MemberManager
import com.ddd.airplane.common.manager.TokenManager
import com.ddd.airplane.repository.network.UserRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel(application: Application) : BaseViewModel(application) {

    private val _isSucceed = MutableLiveData<Boolean>()
    val isSucceed: LiveData<Boolean> = _isSucceed

    /**
     * 로그인
     *
     * @param email
     * @param password
     */
    fun doSignIn(email: String?, password: String?) {

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            return
        }

        viewModelScope.launch {

            var result =
                TokenManager.getAccessToken(this@SignInViewModel, viewModelScope, email, password)
            if (!result) {
                viewModelScope.cancel("토큰 정보 로드 실패")
            }
            Timber.d(">> 토큰 정보 로드 성공")


            result = MemberManager.setAccount(this@SignInViewModel, viewModelScope, email)
            if (!result) {
                viewModelScope.cancel("사용자 프로필 정보 로드 실패")
            }
            Timber.d(">> 사용자 프로필 정보 로드 성공")

            _isSucceed.postValue(true)
            MemberManager.signInResult(true)

        }

    }
}

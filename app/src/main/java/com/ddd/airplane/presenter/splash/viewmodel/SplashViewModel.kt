package com.ddd.airplane.presenter.splash.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.ddd.airplane.common.base.BaseViewModel
import com.ddd.airplane.common.manager.MemberManager
import com.ddd.airplane.common.manager.TokenManager
import com.ddd.airplane.presenter.main.view.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber

class SplashViewModel(application: Application) : BaseViewModel(application) {

    /**
     * Init Flow
     */
    suspend fun onInitFlow() =
        withContext(ioDispatchers) {
            delay(1000)

            // 토큰갱신
            refreshToken()

            // 메인으로 이동
            checkMember()

            true
        }

    /**
     * 토큰갱신
     */
    private suspend fun refreshToken() =
        withContext(viewModelScope.coroutineContext) {
            TokenManager.onRefreshToken(this@SplashViewModel, this)
                .let { isSuccess ->
                    Timber.d(">> 토큰갱신 완료 결과 : $isSuccess")
                    if (!isSuccess) {
                        MemberManager.signOut()
                    }
                }
        }

    /**
     * Token 체크
     */
    private fun checkMember() {
        if (TokenManager.isExist()) {
            startMainActivity()
        } else {
            // 로그인 페이지
            MemberManager.signIn(this.context) {
                if (it) {
                    startMainActivity()
                }
            }
        }
    }

    /**
     * 메인으로 이동
     */
    private fun startMainActivity() {
        Intent(context, MainActivity::class.java).let {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(it)
        }
    }
}
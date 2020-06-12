package com.ddd.airplane.presenter.signup.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd.airplane.common.base.BaseViewModel
import com.ddd.airplane.common.utils.StringUtils
import com.ddd.airplane.data.request.SignUpRequest
import com.ddd.airplane.repository.network.UserRepository
import kotlinx.coroutines.launch

/**
 * 회원가입 ViewModel
 */
class SignUpViewModel(application: Application) : BaseViewModel(application) {

    // 회원가입 성공여부
    private val _isSucceed = MutableLiveData<Boolean>()
    val isSucceed: LiveData<Boolean> = _isSucceed

    /**
     *  회원가입
     *
     * @param email
     * @param password
     * @param nickName
     */
    fun doSignUp(email: String, password: String, nickName: String) {

        if (!isValidEmail(email) || !isValidPassword(password)) {
            return
        }

        viewModelScope.launch {
            UserRepository
                .setOnNetworkStatusListener(
                    this@SignUpViewModel.showProgress(true)
                )
                .setOnErrorListener {
                    setSignUpResult(false)
                }
                .postAccounts(
                    SignUpRequest(
                        email,
                        password,
                        nickName
                    )
                )?.let { response ->
                    setSignUpResult(true)
                }
        }
    }

    /**
     * 회원가입 결과
     *
     * @param isSucceed
     */
    fun setSignUpResult(isSucceed: Boolean) {
        _isSucceed.postValue(isSucceed)
    }

    /**
     * 이메일 유효성 검사
     *
     * @param email
     * @return
     */
    fun isValidEmail(email: String?): Boolean = StringUtils.isValidEmail(email)

    /**
     * 비밀번호 유효성 검사
     *
     * @param password
     * @return
     */
    fun isValidPassword(password: String?): Boolean = StringUtils.isValidPassword(password)


}

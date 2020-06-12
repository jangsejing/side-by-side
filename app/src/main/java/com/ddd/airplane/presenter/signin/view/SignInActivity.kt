package com.ddd.airplane.presenter.signin.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseActivity
import com.ddd.airplane.common.consts.Constants
import com.ddd.airplane.common.extension.showToast
import com.ddd.airplane.databinding.SigninActivityBinding
import com.ddd.airplane.presenter.signin.viewmodel.SignInViewModel
import com.ddd.airplane.presenter.signup.view.SignUpActivity
import kotlinx.android.synthetic.main.signin_activity.*
import timber.log.Timber

/**
 * 로그인
 * @author jess
 */
class SignInActivity : BaseActivity<SigninActivityBinding, SignInViewModel>(),
    View.OnClickListener {

    override val layoutRes = R.layout.signin_activity
    override val viewModelClass = SignInViewModel::class.java

    override fun initDataBinding() {
        super.initDataBinding()
        viewModel.run {
            // 로그인 성공 여부
            isSucceed.observe(this@SignInActivity, Observer {
                if (it) {
                    finish()
                } else {
                    this@SignInActivity.showToast(R.string.sign_in_failed)
                }
            })
        }
    }

    override fun initLayout() {

        val views = arrayOf(bt_sign_in, cl_sign_up_info)
        views.forEach {
            it.setOnClickListener(this)
        }

        bt_sign_in.isEnabled = false

        et_email.setOnValidListener { b ->
            onCheckValid()
        }

        et_password.setOnValidListener { b ->
            onCheckValid()
        }

//        // test
//        et_email.text = "ghi@gmail.com"
//        et_password.text = "password"

    }

    override fun onCreated(savedInstanceState: Bundle?) {

    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            Constants.Request.SIGN_UP.ordinal -> {
                // 로그인 시도
                data?.let {
                    val email = data.getStringExtra(Constants.Extra.EMAIL)
                    val password = data.getStringExtra(Constants.Extra.PASSWORD)
                    viewModel.doSignIn(email, password)
                }
            }
            else -> {

            }
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_sign_in -> {
                viewModel.doSignIn(et_email.text, et_password.text)
            }

            R.id.cl_sign_up_info -> {
                startActivityForResult(
                    Intent(this, SignUpActivity::class.java),
                    Constants.Request.SIGN_UP.ordinal
                )
            }
        }
    }

    /**
     * 유효성 체크후 버튼 활성화
     */
    private fun onCheckValid() {
        Timber.d(">> Email Valid ${et_email.isValid}")
        Timber.d(">> Password Valid ${et_password.isValid}")
        bt_sign_in.isEnabled = et_email.isValid && et_password.isValid
    }
}

package com.ddd.airplane.presenter.signup.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseActivity
import com.ddd.airplane.common.consts.Constants
import com.ddd.airplane.common.extension.showToast
import com.ddd.airplane.databinding.SignupActivityBinding
import com.ddd.airplane.presenter.signup.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.signup_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 회원가입
 * @author jess
 */
class SignUpActivity : BaseActivity<SignupActivityBinding, SignUpViewModel>(),
    View.OnClickListener {

    override val layoutRes = R.layout.signup_activity
    override val viewModelClass = SignUpViewModel::class.java

    override fun initDataBinding() {
        super.initDataBinding()
        viewModel.run {
            isSucceed.observe(this@SignUpActivity, Observer {
                if (it) {
                    // 회원가입 성공
                    showToast(R.string.sign_up_succeed)
                    val intent = Intent().apply {
                        putExtra(Constants.Extra.EMAIL, et_email.text)
                        putExtra(Constants.Extra.PASSWORD, et_password.text)
                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else {
                    // 회원가입 실패
                    showToast(R.string.sign_up_failed)
                }
            })
        }
    }

    override fun initLayout() {

        val views = arrayOf(cl_already, cl_back, bt_sign_up)
        views.forEach {
            it.setOnClickListener(this)
        }

        bt_sign_up.isEnabled = false

        et_email.setOnValidListener { b ->
            onCheckValid()
        }

        et_password.setOnValidListener { b ->
            onCheckValid()
        }

//        // test
//        val ts = System.currentTimeMillis()
//        et_nick_name.text = "$ts"
//        et_email.text = "airplane$ts@airplane.com"
//        et_password.text = "qqqqqq"
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        initListener()
    }

    private fun initListener() {

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cl_back, R.id.cl_already -> {
                finish()
            }

            R.id.bt_sign_up -> {
                viewModel.doSignUp(et_email.text, et_password.text, et_nick_name.text)
            }
        }
    }

    /**
     * 유효성 체크후 버튼 활성화
     */
    private fun onCheckValid() {
        Timber.d(">> Email Valid ${et_email.isValid}")
        Timber.d(">> Password Valid ${et_password.isValid}")
        bt_sign_up.isEnabled = et_email.isValid && et_password.isValid
    }
}

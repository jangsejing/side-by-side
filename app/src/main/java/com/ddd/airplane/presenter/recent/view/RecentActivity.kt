package com.ddd.airplane.presenter.recent.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseActivity
import com.ddd.airplane.common.extension.showToast
import com.ddd.airplane.databinding.SignupActivityBinding
import com.ddd.airplane.presenter.signup.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.signup_activity.*
import kotlinx.coroutines.CoroutineScope

/**
 * 최근 참여한 채팅방
 * @author jess
 */
class RecentActivity : BaseActivity<SignupActivityBinding, SignUpViewModel>(),
    View.OnClickListener {

    override val layoutRes = R.layout.signup_activity
    override val viewModelClass = SignUpViewModel::class.java

    override fun initDataBinding() {
        super.initDataBinding()
        viewModel.run {
            isSucceed.observe(this@RecentActivity, Observer {
                if (it) {
                    showToast(R.string.sign_up_succeed)
                    finish()
                } else {
                    showToast(R.string.sign_up_failed)
                }
            })
        }
    }

    override fun initLayout() {
        val views = arrayOf(bt_sign_up)
        views.forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        initListener()
    }

    private fun initListener() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_sign_up -> {

            }
        }
    }
}

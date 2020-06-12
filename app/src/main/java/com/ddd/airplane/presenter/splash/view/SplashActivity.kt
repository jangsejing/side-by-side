package com.ddd.airplane.presenter.splash.view

import android.os.Bundle
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseActivity
import com.ddd.airplane.databinding.SplashActivityBinding
import com.ddd.airplane.presenter.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 스플래시
 * @author jess
 */
class SplashActivity : BaseActivity<SplashActivityBinding, SplashViewModel>() {

    override val layoutRes = R.layout.splash_activity
    override val viewModelClass = SplashViewModel::class.java

    override fun initDataBinding() {
        super.initDataBinding()
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.onInitFlow().let { isNextFlow ->
                if (isNextFlow) {
                    finish()
                }
            }
        }
    }

    override fun initLayout() {

    }

    override fun onCreated(savedInstanceState: Bundle?) {

    }
}

package com.ddd.airplane.presenter.favorite.view

import android.os.Bundle
import com.ddd.airplane.R
import com.ddd.airplane.common.provider.ContextProvider
import com.ddd.airplane.databinding.FavoriteActivityBinding
import com.ddd.airplane.common.base.BaseActivity
import com.ddd.airplane.presenter.favorite.viewmodel.FavoriteViewModel

/**
 * @author jess
 */
class FavoriteActivity : BaseActivity<FavoriteActivityBinding, FavoriteViewModel>() {

    override val layoutRes = R.layout.favorite_activity
    override val viewModelClass = FavoriteViewModel::class.java

    override fun initDataBinding() {
        super.initDataBinding()

    }

    override fun initLayout() {

    }

    override fun onCreated(savedInstanceState: Bundle?) {

    }

}

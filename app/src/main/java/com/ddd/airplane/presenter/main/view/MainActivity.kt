package com.ddd.airplane.presenter.main.view

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseActivity
import com.ddd.airplane.databinding.MainActivityBinding
import com.ddd.airplane.presenter.main.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 메인
 * @author jess
 */
class MainActivity : BaseActivity<MainActivityBinding, MainViewModel>(), View.OnClickListener {

    override val layoutRes = R.layout.main_activity
    override val viewModelClass = MainViewModel::class.java

    private var recentBottomSheet: BottomSheetBehavior<FrameLayout>? = null

    override fun initDataBinding() {
        super.initDataBinding()
        viewModel.run {
            recent.observe(this@MainActivity, Observer {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    recentBottomSheet?.state = BottomSheetBehavior.STATE_EXPANDED
                }
            })
        }
    }

    override fun initLayout() {

        fl_bottom_sheet.setOnClickListener(this)

        // 최근 본 방송
        recentBottomSheet = BottomSheetBehavior.from(fl_bottom_sheet).apply {

            // 초기값
            state = BottomSheetBehavior.STATE_HIDDEN

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.setFloatingClose()
                            }
                        }

                        else -> {

                        }
                    }
                }
            })
        }

        bnv_main.run {
            fragmentManager = supportFragmentManager
            init()
        }
    }

    override fun onCreated(savedInstanceState: Bundle?) {

    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        viewModel.onPause()
        super.onPause()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_bottom_sheet -> {
                // 최근 본 방송 진입
                viewModel.joinChatRoom()
            }
        }
    }
}

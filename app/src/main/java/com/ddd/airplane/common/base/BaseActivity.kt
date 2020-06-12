package com.ddd.airplane.common.base

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.ddd.airplane.BR
import com.ddd.airplane.R
import com.ddd.airplane.common.extension.showToast
import com.ddd.airplane.common.views.dialog.ProgressDialog

/**
 * @author jess
 */
abstract class BaseActivity<VD : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    /**
     * ViewDataBinding
     */
    lateinit var binding: VD

    /**
     * ViewModel Class
     */
    protected abstract val viewModelClass: Class<VM>

    /**
     * 레이아웃 ID
     */
    protected abstract val layoutRes: Int


    /**
     * 레이아웃 초기화
     */
    abstract fun initLayout()

    /**
     * onCreate 종료
     */
    abstract fun onCreated(savedInstanceState: Bundle?)

    /**
     * Create AAC ViewModel
     */
    private fun <VM : ViewModel> createViewModel(viewModelClass: Class<VM>): VM {
        return ViewModelProviders.of(this).get(viewModelClass)
    }

    /**
     * AAC ViewModel
     */
    protected val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        createViewModel(viewModelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initLayout()
        initNetworkStatus()
        onCreated(savedInstanceState)
    }

    /**
     * 데이터 바인딩 초기화
     */
    protected open fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.run {

            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.brand_black
                )
            )
            lifecycleOwner = this@BaseActivity
            setVariable(BR.viewModel, viewModel)
        }
    }

    /**
     * 네트워크 UI 처리
     */
    private fun initNetworkStatus() {

        val progressDialog = ProgressDialog(this).apply {
            setCancelable(false)
        }

        viewModel.run {
            isProgress.observe(this@BaseActivity, Observer {
                runOnUiThread {
                    if (it) progressDialog.show() else progressDialog.dismiss()
                }
            })

            toast.observe(this@BaseActivity, Observer {
                runOnUiThread {
                    this@BaseActivity.showToast(it)
                }
            })
        }
    }
}

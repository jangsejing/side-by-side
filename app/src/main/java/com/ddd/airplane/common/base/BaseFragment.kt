package com.ddd.airplane.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.ddd.airplane.BR
import com.ddd.airplane.common.extension.showToast
import com.ddd.airplane.common.views.dialog.ProgressDialog

abstract class BaseFragment<VD : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    /**
     * ViewDataBinding
     */
    lateinit var binding: VD

    /**
     * 레이아웃 ID
     */
    protected abstract val layoutRes: Int

    /**
     * ViewModel Class
     */
    protected abstract val viewModelClass: Class<VM>

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
    protected val viewModel by lazy {
        createViewModel(viewModelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            layoutRes,
            container,
            false
        )

        binding.run {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDataBinding()
        initLayout()
        onCreated(savedInstanceState)
    }

    /**
     * 데이터 바인딩 초기화
     */
    protected open fun initDataBinding() {

    }

    /**
     * 네트워크 UI 처리
     */
    private fun initNetworkStatus() {

        var progressDialog: ProgressDialog? = null
        context?.let {
            progressDialog = ProgressDialog(it).apply {
                setCancelable(false)
            }
        }

        viewModel.run {
            isProgress.observe(viewLifecycleOwner, Observer {
                activity?.runOnUiThread {
                    progressDialog?.run {
                        if (it) show() else dismiss()
                    }
                }
            })

            toast.observe(viewLifecycleOwner, Observer {
                activity?.runOnUiThread {
                    context.showToast(it)
                }
            })
        }
    }
}

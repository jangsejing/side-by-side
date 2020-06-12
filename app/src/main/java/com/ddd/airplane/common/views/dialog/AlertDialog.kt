package com.ddd.airplane.common.views.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.StringRes
import com.ddd.airplane.R
import kotlinx.android.synthetic.main.alert_dialog.*

//AlertDialog.Builder(this)
//    .setTitle(R.string.sign_in_info)
//    .setContent(R.string.sign_in_info)
//    .setNegativeButton(R.string.sign_in_info) {
//        showToast("negative")
//    }
//    .setPositiveButton(R.string.sign_in_info) {
//        showToast("positive")
//    }
//    .setCancelable(false)
//    .show()
/**
 * Dialog
 *
 * AlertDialog.Builder(this)
 *      .setTitle(...)
 *      .setContent(...)
 *      .setNegativeButton(...) {
 *          // negative
 *      }
 *      .setPositiveButton(...) {
 *          // positive
 *      }
 *      .setCancelable(false)
 *      .show()
 * @param context
 */
class AlertDialog(
    context: Context,
    private val builder: Builder
) : Dialog(context, R.style.DialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.alert_dialog)

        initDialog()
        apply(builder)

    }

    private fun initDialog() {
        window?.attributes = WindowManager.LayoutParams().apply {
            copyFrom(window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
    }

    class Builder(val context: Context) {

        /**
         * 취소 가능여부
         */
        var cancelable: Boolean = true
            private set

        fun setCancelable(value: Boolean) = apply { cancelable = value }

        /**
         * 타이틀
         */
        @StringRes
        var titleText: Int = 0
            private set

        /**
         * 타이틀
         *
         * @param stringRes    제목
         */
        fun setTitle(
            @StringRes stringRes: Int
        ) = apply {
            this.titleText = stringRes
        }

        /**
         * 컨텐츠
         */
        @StringRes
        var contentText: Int = 0
            private set

        /**
         * 컨텐트 영역 텍스트
         * @param stringRes
         */
        fun setContent(
            @StringRes stringRes: Int
        ) = apply {
            contentText = stringRes
        }

        /**
         * Positive Button
         */
        var positiveText: Int = 0
            private set
        var positiveListener: (() -> Unit)? = null
            private set

        /**
         * @param stringRes      타이틀
         * @param listener  리스너
         */
        fun setPositiveButton(
            @StringRes stringRes: Int,
            listener: (() -> Unit)? = null
        ) = apply {
            positiveText = stringRes
            positiveListener = listener
        }

        /**
         * Negative Button
         */
        var negativeText: Int = 0
            private set
        var negativeListener: (() -> Unit)? = null
            private set

        /**
         * @param stringRes
         * @param listener
         */
        fun setNegativeButton(
            @StringRes stringRes: Int,
            listener: (() -> Unit)? = null
        ) = apply {
            negativeText = stringRes
            negativeListener = listener
        }

        /**
         * Dialog dismiss listener
         */
        var dismissListener: (() -> Unit)? = null
            private set

        fun setOnDismissListener(listener: (() -> Unit)?) = apply {
            dismissListener = listener
        }

        /**
         * Dialog Show
         *
         */
        fun show() {
            AlertDialog(context, this).show()
        }
    }

    /**
     * 적용
     * @param info
     */
    private fun apply(info: Builder) {
        setTitle(info)
        setContent(info)
        setButton(info)
        setCancelable(info.cancelable)
        setOnDismissListener {
            info.dismissListener?.invoke()
        }

        ll_root.setOnClickListener {
            if (this@AlertDialog.builder.cancelable) {
                dismiss()
            }
        }
    }

    /**
     * Title
     *
     * @param info
     */
    private fun setTitle(info: Builder) {
        // 타이틀
        tv_title.run {
            setText(info.titleText)
            visibility = if (this.text.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

    /**
     * 컨텐츠
     *
     * @param info
     */
    private fun setContent(info: Builder) {
        // 타이틀
        tv_content.run {
            setText(info.titleText)
            visibility = if (this.text.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

    /**
     * 버튼
     *
     * @param info
     */
    private fun setButton(info: Builder) {

        tv_negative.run {
            setText(info.negativeText)
            setOnClickListener {
                info.negativeListener?.invoke()
                dismiss()
            }
            visibility = if (this.text.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        tv_positive.run {
            setText(info.positiveText)
            setOnClickListener {
                info.positiveListener?.invoke()
                dismiss()
            }
            visibility = if (this.text.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }
}
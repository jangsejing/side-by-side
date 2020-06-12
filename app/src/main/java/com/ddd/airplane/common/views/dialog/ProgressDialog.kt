package com.ddd.airplane.common.views.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.ddd.airplane.R

/**
 * Progress
 * ProgressDialog.Builder(this)
 *      .setCancelable(...)
 *      .setOnDismissListener {
 *
 *      }
 *      .show()
 * @param context
 */
class ProgressDialog(
    context: Context
) : Dialog(context, R.style.DialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.progress_dialog)
        initDialog()
    }

    private fun initDialog() {
        window?.attributes = WindowManager.LayoutParams().apply {
            copyFrom(window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
    }

    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }
}
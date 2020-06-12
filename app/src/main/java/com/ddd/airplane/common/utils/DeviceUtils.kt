package com.ddd.airplane.common.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

object DeviceUtils {

    /**
     * 키보드 올림
     *
     * @param view
     */
    fun showKeyboard(view: View?) {
        view?.let {
            val inputManager =
                it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    /**
     * 키보드 내림
     *
     * @param view
     */
    fun hideKeyboard(view: View?) {
        view?.let {
            val inputManager =
                it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                0
            )
        }
    }

    /**
     * dp value px
     *
     * @param context
     * @param dp
     * @return
     */
    fun dpToPx(context: Context, dp: Int): Int {
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        )
        return px.roundToInt()
    }

    /**
     * Status Bar Color
     *
     * @param activity
     * @param color
     */
    fun setStatusBarColor(activity: Activity?, @ColorRes color: Int) {
        activity?.let {
            it.window?.statusBarColor =
                ContextCompat.getColor(it, color)
        }
    }

    /**
     * 스크린 너비
     *
     * @param context
     * @param percent 비율 (스크린의 비율 만큼 리턴)
     * @return
     */
    fun getScreenWidth(
        context: Context?,
        percent: Int = 0
    ): Int {
        context?.let {
            val metrics = context.resources.displayMetrics
            return if (percent > 0) {
                metrics.widthPixels * percent / 100
            } else {
                metrics.widthPixels
            }
        }
        return 0
    }

    /**
     * 스크린 높이
     *
     * @param context
     * @param percent 비율 (스크린의 비율 만큼 리턴)
     * @return
     */
    fun getScreenHeight(
        context: Context?,
        percent: Int = 0
    ): Int {
        context?.let {
            val metrics = context.resources.displayMetrics
            return if (percent > 0) {
                metrics.heightPixels * percent / 100
            } else {
                metrics.heightPixels
            }
        }
        return 0
    }

    /**
     * StatusBar 컬러 변경
     *
     * @param activity
     * @param isLight
     */
    fun setLightStatusBar(activity: Activity?, isLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.let {
                val view = it.window.decorView
                if (isLight) {
                    it.window.addFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//                    view.systemUiVisibility =
//                        view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
//                    view.systemUiVisibility =
//                        view.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    it.window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                }
            }
        }
    }
}
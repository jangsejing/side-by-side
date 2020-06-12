package com.ddd.airplane.common.views.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.ddd.airplane.R
import com.ddd.airplane.databinding.HeaderViewBinding
import kotlinx.android.synthetic.main.header_view.view.*


/**
 * 공통 헤더뷰
 *
 * @author jess
 */
class HeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding = HeaderViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        intLayout(attrs, defStyleAttr)
    }

    @SuppressLint("CustomViewStyleable", "Recycle", "CheckResult")
    private fun intLayout(
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        if (attrs != null) {

            val typedValue = context.obtainStyledAttributes(
                attrs,
                R.styleable.HeaderView,
                defStyleAttr,
                0
            )

            // 타이틀
            setTitle(typedValue.getString(R.styleable.HeaderView_title))

//            // 배경
//            setBackground(
//                typedValue.getColor(
//                    R.styleable.HeaderView_android_background,
//                    ContextCompat.getColor(context, R.color.brand_black)
//                )
//            )

//            // 텍스트컬러
//            setTextColor(
//                typedValue.getColor(
//                    R.styleable.HeaderView_android_textColor,
//                    ContextCompat.getColor(context, R.color.brand_white)
//                )
//            )

//            // 옵션 텍스트
//            tv_text_option.text = typedValue.getString(R.styleable.HeaderView_textOption)
//
//            // 왼쪽 옵션 이미지
//            iv_left_option.setImageDrawable(typedValue.getDrawable(R.styleable.HeaderView_leftOption))
//
//            // 왼쪽 옵션 이미지
//            iv_right_option.setImageDrawable(typedValue.getDrawable(R.styleable.HeaderView_rightOption))
        }
    }

//    /**
//     * 배경
//     *
//     * @param color
//     */
//    private fun setBackground(color: Int) {
//        tb_header.setBackgroundColor(color)
//    }
//
//    /**
//     * 텍스트컬러
//     *
//     * @param color
//     */
//    private fun setTextColor(color: Int) {
//        tv_title.setTextColor(color)
//    }

    /**
     * 타이틀
     */
    fun setTitle(title: String?) {
        title?.let {
            tv_title.run {
                visibility = View.VISIBLE
                text = it
            }
        }
    }

//    /**
//     * 종료형태
//     */
//    private fun setBack(isBack: Boolean) {
//        iv_finish.run {
//            visibility = View.VISIBLE
//            setImageResource(
//                if (isBack) {
//                    R.drawable.ic_sample
//                } else {
//                    R.drawable.ic_sample
//                }
//            )
//        }
//    }
}
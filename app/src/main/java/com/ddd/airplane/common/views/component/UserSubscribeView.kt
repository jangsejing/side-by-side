package com.ddd.airplane.common.views.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ddd.airplane.databinding.UserSubscribeViewBinding
import kotlinx.android.synthetic.main.user_subscribe_view.view.*

/**
 *
 * 유저 카운트, 구독자 수 View
 *
 * @author jess
 */
class UserSubscribeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding = UserSubscribeViewBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * 참여자 수
     *
     * @param count
     */
    fun setSubscribeCount(count: Int = 0) {
        tv_subscribe.text = count.toString()
    }

    /**
     * 구독자수
     *
     * @param count
     */
    fun setUserCount(count: Int = 0) {
        tv_count.text = count.toString()
    }

}
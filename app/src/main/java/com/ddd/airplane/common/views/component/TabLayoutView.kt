package com.ddd.airplane.common.views.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.ddd.airplane.R
import com.google.android.material.tabs.TabLayout


/**
 *
 * @author jess
 */
class TabLayoutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr) {

    init {
        initLayout()
    }

    private fun initLayout() {

        this.addOnTabSelectedListener(object : OnTabSelectedListener {

            override fun onTabReselected(tab: Tab?) {

            }

            override fun onTabSelected(tab: Tab?) {
                tab?.let {
                    setScrollPosition(it.position, 0f, true)
                }
            }

            override fun onTabUnselected(tab: Tab?) {

            }
        })
    }

}
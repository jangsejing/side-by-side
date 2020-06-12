package com.ddd.airplane.common.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Fragment 교체
 *
 * @param fragmentManager
 * @param replaceId
 */
fun Fragment.replace(fragmentManager: FragmentManager, replaceId: Int) {
    fragmentManager.beginTransaction().let {
        it.replace(replaceId, this)
        it.commitAllowingStateLoss()
    }
}

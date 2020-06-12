package com.ddd.airplane.common.provider

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * Context Provider
 *
 * @property context
 */
class ContextProvider(val context: Context?) {

    fun getString(@StringRes stringRes: Int) = context?.getString(stringRes) ?: ""

    fun getColor(@ColorRes colorRes: Int) =
        context?.let { ContextCompat.getColor(it, colorRes) } ?: 0

}
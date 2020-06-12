package com.ddd.airplane.common.manager

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.ddd.airplane.common.utils.tryCatch
import timber.log.Timber

/**
 * PreferencesManager
 */
object PreferencesManager {

    private lateinit var prefs: SharedPreferences

    val FILE_NAME = "prefs"
    val SAMPLE_TEXT_KEY = "sampleText"

    fun init(context: Context?) {
        context?.let {
            prefs = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        }
    }

    var sampleText: String?
        get() = prefs.getString(SAMPLE_TEXT_KEY, "")
        set(value) = prefs.edit().putString(SAMPLE_TEXT_KEY, value).apply()


    /**
     * Put Value
     *
     * @param key
     * @param value
     */
    fun putValue(key: String, value: Any?) {
        tryCatch {
            Timber.d(">> key : $key / value : $value")
            val edit = prefs.edit()
            when (value) {
                is Int -> edit.putInt(key, value)
                is Long -> edit.putLong(key, value)
                is Float -> edit.putFloat(key, value)
                is Boolean -> edit.putBoolean(key, value)
                else -> edit.putString(key, value.toString())
            }
            edit.apply()
        }
    }

    /**
     * Remove
     *
     * @param key
     */
    fun remove(key: String) {
        prefs.edit().run {
            remove(key)
            apply()
        }
    }

    /**
     * Int Value
     *
     * @param key
     * @param defValue
     */
    fun getInt(key: String, defValue: Int = 0): Int {
        prefs.getInt(key, defValue).let {
            Timber.d(">> key : $key / value : $it")
            return it
        }
    }

    /**
     * Long Value
     *
     * @param key
     * @param defValue
     */
    fun getLong(key: String, defValue: Long = 0): Long {
        prefs.getLong(key, defValue).let {
            Timber.d(">> key : $key / value : $it")
            return it
        }
    }

    /**
     * Float Value
     *
     * @param key
     * @param defValue
     */
    fun getFloat(key: String, defValue: Float = (0.0).toFloat()): Float {
        prefs.getFloat(key, defValue).let {
            Timber.d(">> key : $key / value : $it")
            return it
        }
    }

    /**
     * Boolean Value
     *
     * @param key
     * @param defValue
     */
    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        prefs.getBoolean(key, defValue).let {
            Timber.d(">> key : $key / value : $it")
            return it
        }
    }

    /**
     * String Value
     *
     * @param key
     * @param defValue
     */
    fun getString(key: String, defValue: String? = null): String? {
        prefs.getString(key, defValue).let {
            Timber.d(">> key : $key / value : $it")
            return it
        }
    }

}
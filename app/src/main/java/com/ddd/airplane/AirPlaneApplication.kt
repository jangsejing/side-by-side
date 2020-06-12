package com.ddd.airplane

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.ddd.airplane.common.manager.PreferencesManager
import com.ddd.airplane.presenter.signin.KakaoSdkAdapter
import com.ddd.airplane.repository.database.room.RoomManager
import com.ddd.airplane.repository.network.retrofit.RetrofitManager
import com.kakao.auth.KakaoSDK
import timber.log.Timber

/**
 * @author jess
 */
class AirPlaneApplication : MultiDexApplication() {

    companion object {
        var instance: AirPlaneApplication? = null
    }

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPreference()
        retrofit()
        timber()
        room()
        kakao()
    }

    override fun onTerminate() {
        RoomManager.close()
        instance = null
        super.onTerminate()
    }

    /**
     * Retrofit
     */
    private fun retrofit() {
        RetrofitManager.init()
    }

    /**
     * Timber
     */
    private fun timber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * Room
     */
    private fun room() {
        RoomManager.init(applicationContext)
    }

    /**
     * Kakao
     */
    private fun kakao() {
        KakaoSDK.init(KakaoSdkAdapter())
    }

    /**
     * SharedPreference
     */
    private fun sharedPreference() {
        PreferencesManager.init(applicationContext)
    }

    fun getGlobalApplicationContext(): AirPlaneApplication {
        checkNotNull(instance) { "this application does not inherit" }
        return instance!!
    }
}

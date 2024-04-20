package com.example.enhanceipc

import android.app.Application
import timber.log.Timber

/**

 * @Author: chen

 * @datetime: 2024/4/20

 * @desc:

 */
class IpcApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
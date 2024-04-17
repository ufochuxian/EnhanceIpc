package com.example.ipclib

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**

 * @Author: chen

 * @datetime: 2024/4/17

 * @desc:

 */
class ServiceManager : Service() {

    enum class ServiceType {
        SERVICE_FIND,//服务发现
        SERVICE_INVOKE //服务调用
    }

    override fun onBind(p0: Intent?): IBinder? {

        return null
    }
}
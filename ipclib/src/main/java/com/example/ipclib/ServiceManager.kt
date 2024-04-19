package com.example.ipclib

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.ipclib.bean.RequestBean
import com.google.gson.Gson

/**

 * @Author: chen

 * @datetime: 2024/4/17

 * @desc:

 */
class ServiceManager : Service() {

    class MyBinder : CocosBinderAIDLInterface.Stub() {

        private val TAG = "ServiceManager"
        val gson = Gson()

        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {

        }

        override fun request(str: String?): String {
            str?.let {
                val requestBean = gson.fromJson(it, RequestBean::class.java)
                Log.d(TAG, "requestBean,name:${requestBean.name}")

                //获取客户端请求的服务类型（1. 服务发现 2. 服务调用）
                when (requestBean.type) {
                    ServiceType.SERVICE_FIND -> {
                        //根据方法名称，从缓存表中获取服务

                    }

                    ServiceType.SERVICE_INVOKE -> {

                    }
                }
            }
            return ""
        }


    }

    enum class ServiceType(val value: Int) {
        SERVICE_FIND(0),//服务发现
        SERVICE_INVOKE(1) //服务调用
    }

    override fun onBind(p0: Intent?): IBinder? {
        return MyBinder()
    }
}
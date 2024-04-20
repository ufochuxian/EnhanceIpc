package com.example.ipclib

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.ipclib.bean.RequestBean
import com.example.ipclib.cache.CacheCenter
import com.google.gson.Gson
import timber.log.Timber

/**

 * @Author: chen

 * @datetime: 2024/4/17

 * @desc:

 */
class ServiceManager : Service() {

    enum class ServiceType(val value: Int) {
        SERVICE_FIND(0),//服务发现
        SERVICE_INVOKE(1) //服务调用
    }

    override fun onBind(p0: Intent?): IBinder {
        return MyBinder()
    }

    class MyBinder : CocosBinderAIDLInterface.Stub() {

        private val gson = Gson()

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
                Timber.d("requestBean,name:${requestBean.name}")

                //获取客户端请求的服务类型（1. 服务发现 2. 服务调用）
                when (requestBean.type) {
                    ServiceType.SERVICE_FIND -> {
                        //根据方法名称，从缓存表中获取服务
                        val method = CacheCenter.getMethod(requestBean)

                        //获取当前类的对象
                        val clazz = requestBean.name?.let { className -> Class.forName(className) }
                        val instance = clazz?.getDeclaredConstructor()?.newInstance()

                        //  根据RequestBean,获取请求方法，所需要的参数
                        val parameters = CacheCenter.getParametersByRequestBean(requestBean)
                        method?.invoke(instance,parameters[0],parameters[1])
                    }
                    ServiceType.SERVICE_INVOKE -> {

                    }
                }
            }
            return "123"
        }
    }
}
package com.example.ipclib

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.ipclib.bean.RequestBean
import com.example.ipclib.cache.CacheCenter
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
                        val method = CacheCenter.getMethod(requestBean)


                        //获取当前类的对象
                        val clazz = Class.forName(requestBean.name)
                        val instance = clazz.getDeclaredConstructor().newInstance()


                        //  根据RequestBean,获取请求方法，所需要的参数
                        val parameters = getParametersByRequestBean(requestBean)
                        method?.invoke(instance,parameters[0],parameters[1])
                    }
                    ServiceType.SERVICE_INVOKE -> {

                    }
                }
            }
            return "123"
        }

        /**
         * 根据参数的class类型和参数value，生成“参数数组”
         */
        private fun getParametersByRequestBean(requestBean: RequestBean): ArrayList<*> {
            val params = arrayListOf<Any>()
            requestBean.parameters?.forEach {
                val clazz = CacheCenter.getClassType(it.parameterClassName)
                //这里为啥需要用gson进行再次序列化，主要是为了应对“对象嵌套对象”的那种场景和数据模型，所以需要进行反序列化
                val param = gson.fromJson(it.paramValue, clazz)
                params.add(param)
            }

            return params
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
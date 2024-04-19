package com.example.ipclib.core

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.ipclib.ClassId
import com.example.ipclib.CocosBinderAIDLInterface
import com.example.ipclib.ServiceManager
import com.example.ipclib.bean.RequestBean
import com.example.ipclib.bean.RequestParameter
import com.example.ipclib.cache.CacheCenter
import com.google.gson.Gson
import java.lang.reflect.Method

object BinderIPC {
    private lateinit var mCtx: Application

    private val gson = Gson()

    var mCocosBinderAIDL: CocosBinderAIDLInterface? = null

    fun init(ctx: Application) {
        this.mCtx = ctx
    }


    //1. 服务注册的方法
    fun <T> register(clazz: Class<T>) {
        //注册交给CacheCenter
        CacheCenter.register(clazz)


    }

    fun open(ctx: Application) {
        open(ctx, null, ServiceManager::class.java)
    }

    private fun open(ctx: Application, packageName: String?, service: Class<ServiceManager>) {
        init(ctx)
        val intent: Intent
        if (packageName.isNullOrEmpty()) {
            //app内启动
            intent = Intent(ctx, ServiceManager::class.java)
        } else {
            //跨app启动
            intent = Intent()
            val component = ComponentName(packageName, service.name)
            intent.component = component
            //如果是跨app启动，需要通过action来进行启动
            intent.action = service.name

        }
        ctx.bindService(intent, CocosServiceConnection(), Context.BIND_AUTO_CREATE)
    }

    class CocosServiceConnection() : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            mCocosBinderAIDL = CocosBinderAIDLInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
        }

        override fun onNullBinding(name: ComponentName?) {
            super.onNullBinding(name)
        }

    }


    /**
     * 提供一个对外提供真正的服务实现的方法
     */

    fun <T> getInstance(clazz: Class<T>, vararg params: Any): T? {

        sendRequest(clazz, clazz.methods[0], ServiceManager.ServiceType.SERVICE_FIND, *params)


        return null
    }

    private fun <T> sendRequest(clazz: Class<T>, method: Method?, type: ServiceManager.ServiceType,vararg params: Any) {
        val className = clazz.getAnnotation(ClassId::class.java)?.value
        var methodName = if (method == null) "getInstance" else method.name

        val requestParameters: ArrayList<RequestParameter> = arrayListOf()
        params.forEachIndexed { _,param ->
            val parameterClassName = param.javaClass.name
            //这一步
            val parameterValue = gson.toJson(param)
            requestParameters.add(RequestParameter(parameterClassName, parameterValue))
        }
        //构造的“请求参数”，发送出去
        val requestBean = RequestBean(className, methodName,type, requestParameters)

        val requestStr = gson.toJson(requestBean)

        //通过binder发送请求过去（其实做的就是一个将对象进行序列化的操作，然后通过进程间通信发送出去）
        mCocosBinderAIDL?.request(requestStr)

    }


}

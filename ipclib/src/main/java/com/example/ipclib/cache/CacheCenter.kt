package com.example.ipclib.cache

import com.example.ipclib.bean.RequestBean
import timber.log.Timber
import java.lang.reflect.Method
import java.util.Objects
import java.util.concurrent.ConcurrentHashMap

/**
 *
 *
 * 这个类提供缓存提供服务的对象以及服务对象对应的方法
 */
object CacheCenter {

    private const val TAG = "CacheCenter"

    //1. map("类名字",对应的类)
    private var mClassMap = ConcurrentHashMap<String, Class<*>>()

    //2. map("类名字",对应的类的所有的方法)
//    private var mMethodMap = ConcurrentHashMap<String, Method>()

    private var mAllMethodMap = ConcurrentHashMap<String, ConcurrentHashMap<String, Method>>()

    //3. 缓存能够提供服务的对象，方便后续使用

    private var mObjectMap = ConcurrentHashMap<String, Objects>()

    fun <T> register(clazz: Class<T>) {
        registerClass(clazz)
        registerMethod(clazz)
    }

    private fun <T> registerClass(clazz: Class<T>) {
        val className = clazz.name
        mClassMap.apply {
            put(className, clazz)
        }
    }

    private fun <T> registerMethod(clazz: Class<T>) {
        val className = clazz.name
        clazz.methods.forEach { method ->
            var methodMap = mAllMethodMap[className]
            if (methodMap.isNullOrEmpty()) {
                methodMap = ConcurrentHashMap()
                mAllMethodMap[className] = methodMap
            }
            val key = getMethodParameters(method)
            methodMap[key] = method
        }
    }

    //使用方法的全名称，生成一个唯一key,这里主要是防止java的方法的重载
    private fun getMethodParameters(method: Method): String {
        val builder = StringBuilder()
        builder.append(method.name)
        method.parameterTypes.forEach { clazz ->
            builder.apply {
                append("-")
                append(clazz.name)
            }
        }
        return builder.toString()
    }

    private fun getMethodParameters(requestBean: RequestBean): String {
        val builder = StringBuilder()
        builder.append(requestBean.methodName)
        requestBean.parameters?.forEach {
            builder.apply {
                append("-")
                append(it.parameterClassName)
            }
        }
        return builder.toString()
    }

    fun getMethod(requestBean: RequestBean): Method? {
        return mAllMethodMap[requestBean.name]?.get(getMethodParameters(requestBean))
    }

    fun getClassType(parameterClassName: String): Class<*>? {
        try {
            return Class.forName(parameterClassName)
        } catch (e: ClassNotFoundException) {
            Timber.tag(TAG)
                .w("[CacheCenter],根据参数类型名称，获取参数的类型class出错了，parameterClassName:%s", parameterClassName)
        }
        return null
    }


}
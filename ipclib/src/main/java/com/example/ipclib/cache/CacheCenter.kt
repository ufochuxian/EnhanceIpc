package com.example.ipclib.cache

import java.lang.reflect.Method
import java.util.Objects
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 *
 *
 * 这个类提供缓存提供服务的对象以及服务对象对应的方法
 */
object CacheCenter {

    //1. map("类名字",对应的类)
    private var mClassMap = ConcurrentHashMap<String, Class<*>>()

    //2. map("类名字",对应的类的所有的方法)
//    private var mMethodMap = ConcurrentHashMap<String, Method>()

    private var allMethodMap = ConcurrentHashMap<String,ConcurrentHashMap<String,Method>>()

    //3. 缓存能够提供服务的对象，方便后续使用

    private var mObjectMap = ConcurrentHashMap<String, Objects>()

    fun <T> register(clazz: Class<T>) {

        registerClass(clazz)

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
            var methodMap = allMethodMap[className]
            if(methodMap.isNullOrEmpty()) ConcurrentHashMap<String,Method>() else methodMap[className] = method
        }

    }


}
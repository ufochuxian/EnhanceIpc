package com.example.ipclib.core

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

object BinderIPC {
    private lateinit var mCtx: Application
    fun init(ctx: Application) {
        this.mCtx = ctx
    }


    //1. 服务注册的方法

    fun <T> register(clazz: Class<T>) {
        //注册交给CacheCenter

    }
}
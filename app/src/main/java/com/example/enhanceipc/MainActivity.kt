package com.example.enhanceipc

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.ipclib.core.BinderIPC

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //1. 开启服务
        BinderIPC.open(application)

        Singlton.user = User("YaoMing",20)

        //2. 服务注册
        BinderIPC.register(Singlton::class.java)

        //3. 服务发现 (1. 通知主进程实例化对象 2. 将对象返回到调用进程（aidl）)


    }
}


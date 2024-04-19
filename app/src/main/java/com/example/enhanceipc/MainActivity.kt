package com.example.enhanceipc

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.enhanceipc.databinding.ActivityMainBinding
import com.example.ipclib.core.BinderIPC

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //1. 开启服务
        BinderIPC.open(application)

        //2. 服务注册
        BinderIPC.register(Singlton::class.java)

//        Singlton.user = User("YaoMing",20)

        //3. 服务发现 (1. 通知主进程实例化对象 2. 将对象返回到调用进程（aidl）)

        activityMainBinding.goToSecondActivity.setOnClickListener {
            val intent = Intent(it.context,SecondActivity::class.java)
            startActivity(intent)
        }

    }
}


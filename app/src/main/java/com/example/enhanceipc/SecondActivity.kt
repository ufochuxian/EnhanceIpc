package com.example.enhanceipc

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.example.ipclib.core.BinderIPC

/**

 * @Author: chen

 * @datetime: 2024/4/18

 * @desc:

 */
class SecondActivity : Activity() {

    //获取其他模块提供服务的接口
    private var mSingleTonService : ISInglton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        BinderIPC.open(application)

        findViewById<TextView>(R.id.getInstanceBtn).setOnClickListener {
             mSingleTonService = BinderIPC.getInstance(ISInglton::class.java, "david",10)
        }



    }
}
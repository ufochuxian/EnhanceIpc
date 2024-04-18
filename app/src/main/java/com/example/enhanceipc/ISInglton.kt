package com.example.enhanceipc

import com.example.ipclib.ClassId


@ClassId("com.example.enhanceipc.Singlton")
interface ISInglton {
    fun sayHello(name:String,age :Int) :User
}
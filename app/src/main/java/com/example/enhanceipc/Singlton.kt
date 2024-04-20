package com.example.enhanceipc

import android.util.Log
import timber.log.Timber


object Singlton : ISInglton {

    private const val TAG = "Singlton"

    lateinit var user: User

    override fun sayHello(name: String, age: Integer): User {
        Timber.i("%s%s", "sayHello,name:$name,age:", age)
        user = User(name,age.toInt())
        return user
    }


}
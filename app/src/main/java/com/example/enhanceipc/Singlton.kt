package com.example.enhanceipc

import timber.log.Timber


object Singlton : ISInglton {

    private const val TAG = "Singlton"

    lateinit var user: User

    override fun sayHello(name: String, age: Integer): User {
        Timber.tag(TAG).i("sayHello,name:${name},age:${age}")
        return user
    }


}
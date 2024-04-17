package com.example.enhanceipc

object Singlton : ISInglton {

    lateinit var user: User
    override fun getUser() :User {
        return user
    }

    override fun sayHello(name: String, age: Int): User {
        return user
    }


}
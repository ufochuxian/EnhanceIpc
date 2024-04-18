package com.example.enhanceipc

object Singlton : ISInglton {

    lateinit var user: User

    override fun sayHello(name: String, age: Int): User {
        return user
    }


}
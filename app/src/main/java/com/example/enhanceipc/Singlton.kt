package com.example.enhanceipc

object Singlton : ISInglton {

    private lateinit var user: User
    override fun getUser() :User {
        return user
    }


}
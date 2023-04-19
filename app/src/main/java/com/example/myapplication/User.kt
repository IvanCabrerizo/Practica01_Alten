package com.example.myapplication

import java.io.Serializable

data class User(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String,) :Serializable

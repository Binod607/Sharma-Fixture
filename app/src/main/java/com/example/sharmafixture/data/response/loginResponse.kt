package com.example.sharmainteriordesign.response

import com.example.sharmafixture.data.model.User


data class loginResponse (
    val success:Boolean?=null,
    val token:String?=null,
    val msg:String?=null,
    val id:String?=null,
    val data:MutableList<User>?=null
)
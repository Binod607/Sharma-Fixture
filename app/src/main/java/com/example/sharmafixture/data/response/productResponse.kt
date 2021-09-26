package com.example.sharmainteriordesign.response

import com.example.sharmafixture.data.model.Product


data class productResponse(
    val success:Boolean?=null,
    val data:ArrayList<Product>?=null,
    val msg:String?=null,
    val id:String?=null
) {
}
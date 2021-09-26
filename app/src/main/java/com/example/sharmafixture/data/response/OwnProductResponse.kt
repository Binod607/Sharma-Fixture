package com.example.sharmainteriordesign.response

import com.example.sharmafixture.data.model.Product


data class OwnProductResponse(val success:Boolean?=null,
                              val data:List<Product>?=null,
                              val msg:String?=null,
                              val id:String?=null) {

}
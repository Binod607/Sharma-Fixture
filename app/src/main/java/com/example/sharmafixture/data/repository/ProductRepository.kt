package com.example.sharmafixture.data.repository

import com.example.sharmafixture.data.model.Product
import com.example.sharmainteriordesign.api.ProductApi
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.response.*
import com.mindorks.framework.roomdatabaseandapi.api.MyApiRequest
import okhttp3.MultipartBody

class ProductRepository: MyApiRequest() {
    val myApi= ServiceBuilder.buildServices(ProductApi::class.java)
    suspend fun insertProduct(product: Product): productResponse {
        return apiRequest {
            myApi.insertProduct(ServiceBuilder.token!!,product)
        }
    }
suspend fun getallProduct():productResponse{
    return apiRequest {
        myApi.getallProduct()
    }
}

    suspend fun getallProduct(id:String): OwnProductResponse {
        return apiRequest {
            myApi.getallProduct(ServiceBuilder.token!!,id!!)
        }
    }
    suspend fun uploadImage(id:String,file: MultipartBody.Part):loginResponse{
        return apiRequest {
            myApi.uploadImage(ServiceBuilder.token!!,id,file)
        }
    }
    suspend fun getinter(id:String):OwnProductResponse{
        return apiRequest {
            myApi.getInter(ServiceBuilder.token!!,id)
        }
    }
}
package com.example.sharmafixture.data.repository

import com.example.sharmafixture.data.model.User
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.api.UserApi
import com.example.sharmainteriordesign.response.loginResponse
import com.mindorks.framework.roomdatabaseandapi.api.MyApiRequest
import okhttp3.MultipartBody

class UserRepository: MyApiRequest() {
    val myApi= ServiceBuilder.buildServices(UserApi::class.java)

    suspend fun registerUSer(user: User):loginResponse{
        return apiRequest {
            myApi.userAdd(user)
        }
    }
    suspend fun checkUSer(user:User): loginResponse {
        return apiRequest {
            myApi.checkUSer(user) }
    }

    suspend fun getMe(id:String):loginResponse{
        return apiRequest {
            myApi.getMe(ServiceBuilder.token!!,id)
        }
    }
    suspend fun uploadUSerImage(id:String, file:MultipartBody.Part):loginResponse{
        return apiRequest {
            myApi.uploadUserImage(ServiceBuilder.token!!,id,file)
        }
    }
    suspend fun updateUser(user:User):loginResponse{
        return apiRequest {
            myApi.updateUser(ServiceBuilder.token!!,user)
        }
    }

}

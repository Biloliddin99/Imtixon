package com.example.projectend.retrofit

import com.example.imtixon.models.Users
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("posts/")
    fun getData():Call<List<Users>>
}
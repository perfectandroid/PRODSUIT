package com.perfect.prodsuit.Api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("MuttatheMulla/UserLogin")
    fun getLogin(@Body body: RequestBody): Call<String>
}
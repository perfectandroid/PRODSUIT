package com.perfect.prodsuit.Api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("UserValidations/ResellerDetails")
    fun getResellerData(@Body body: RequestBody): Call<String>

    @POST("UserValidations/UserLogin")
    fun getLogin(@Body body: RequestBody): Call<String>

    @POST("UserValidations/Verification")
    fun getOtpverification(@Body body: RequestBody): Call<String>

    @POST("UserValidations/SetMPIN")
    fun getSetMPIN(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ChangeMPIN")
    fun getChangeMPIN(@Body body: RequestBody): Call<String>

    @POST("UserValidations/CustomerDetails")
    fun getCustomerDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AddNewCustomer")
    fun addCustomerDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadThroughDetails")
    fun getLeadThrough(@Body body: RequestBody): Call<String>

}
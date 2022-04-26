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

    @POST("UserValidations/MediaTypeDetails")
    fun getMediType(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadFromDetailsList")
    fun getLeadFrom(@Body body: RequestBody): Call<String>

    @POST("UserValidations/MaintenanceMessage")
    fun getMaintenanceMessage(@Body body: RequestBody): Call<String>

    @POST("UserValidations/BannerDetails")
    fun getBannerDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/CollectedByUsers")
    fun getLeadBy(@Body body: RequestBody): Call<String>

    @POST("UserValidations/CategoryDetails")
    fun getProductcategory(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ProductDetails")
    fun getProductDetail(@Body body: RequestBody): Call<String>

    @POST("UserValidations/PriorityDetails")
    fun getProductPriority(@Body body: RequestBody): Call<String>

    @POST("UserValidations/StatusDetails")
    fun getProductStatus(@Body body: RequestBody): Call<String>

    @POST("UserValidations/FollowUpActionDetails")
    fun getFollowUpAction(@Body body: RequestBody): Call<String>

    @POST("UserValidations/FollowUpTypeDetails")
    fun getFollowUpType(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadManagementDetailsList")
    fun getLeadManagementDetailsList(@Body body: RequestBody): Call<String>

    @POST("UserValidations/BranchTypeDetails")
    fun getBranchType(@Body body: RequestBody): Call<String>

    @POST("UserValidations/BranchDetails")
    fun getBranch(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DepartmentDetails")
    fun getDepartment(@Body body: RequestBody): Call<String>

    @POST("UserValidations/EmployeeDetails")
    fun getEmployee(@Body body: RequestBody): Call<String>

    @POST("UserValidations/RoportSettingsList")
    fun getRoportSettingsList(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadHistoryDetails")
    fun getLeadHistoryDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadGenerateReport")
    fun getGeneralReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadInfoetails")
    fun getLeadInfoetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadImageDetails")
    fun getLocationdetails(@Body body: RequestBody): Call<String>
    @POST("UserValidations/LeadInfoetails")
    fun getInfoetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadHistoryDetails")
    fun getQuotationDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadHistoryDetails")
    fun getHistoryActtails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/UpdateLeadGenerateAction")
    fun updateLeadGenerateAction(@Body body: RequestBody): Call<String>


    @POST("UserValidations/NotificationDetailsList")
    fun getNotification(@Body body: RequestBody): Call<String>

    @POST("UserValidations/PincodeDetails")
    fun getPincodeDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AddAgentCustomerRemarks")
    fun getAgentnote(@Body body: RequestBody): Call<String>




}
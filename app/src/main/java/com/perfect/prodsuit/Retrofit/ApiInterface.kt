package com.perfect.prodsuit.Api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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
    @POST("UserValidations/CountryDetails")
    fun getCountryDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/StatesDetails")
    fun getStatesDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DistrictDetails")
    fun getDistrictDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/PostDetails")
    fun getPostDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/UpdateLeadGeneration")
    fun saveUpdateLeadGeneration(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AddNextAction")
    fun saveAddNextAction(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AddNewAction")
    fun saveAddNewAction(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadGenerationList")
    fun getLeadGenerationList(@Body body: RequestBody): Call<String>

  /*  @Multipart
    @POST("UserValidations/AddQuatation")
    fun getquotation(
            @Part("ReqMode") reqmode: RequestBody,
            @Part("BankKey") bankkey: RequestBody,
            @Part("FK_Employee") fkemployee: RequestBody,
            @Part("Token") token: RequestBody,
            @Part("ID_LeadGenerateProduct") id_lead: RequestBody,
            @Part("TrnsDate") date: RequestBody,
            @Part filePart: MultipartBody.Part,
            @Part("Remark") remrk: RequestBody

    ): Call<String>

*/
    @Multipart
    @POST("UserValidations/AddQuatation")
    fun getquotation(@Part("JsonData") JsonData: RequestBody?,
                                   @Part Body: MultipartBody.Part?): Call<String>
 /* fun getquotation( @Part file: MultipartBody.Part, @Part("json") json: RequestBody): Call<String>*/


    @POST("UserValidations/LeadGenerationListDetails")
    fun getLeadGenerationListDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadsDashBoardDetails")
    fun getLeadsDashBoardDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DateWiseExpenseDetails")
    fun getExpense(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AddDocument")
    fun saveAddDocument(@Body body: RequestBody): Call<String>




}
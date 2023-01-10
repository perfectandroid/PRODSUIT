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

    @POST("UserValidations/SubMediaTypeDetails")
    fun getSubMediaTypeDetails(@Body body: RequestBody): Call<String>

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

    @POST("UserValidations/EmployeeDetails")
    fun getEmpUsingBranch(@Body body: RequestBody): Call<String>

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

    @POST("UserValidations/AreaDetails")
    fun getAreaDetails(@Body body: RequestBody): Call<String>

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

   /* @POST("UserValidations/AddQuatation")
    fun getquotation(@Body body: RequestBody): Call<String>*/


    @Multipart
    @POST("UserValidations/AddQuatation")
    fun getquotation(
        @Part("JsonData") JsonData: RequestBody,
        @Part Body: MultipartBody.Part
    ): Call<String>


    @POST("UserValidations/LeadGenerationListDetails")
    fun getLeadGenerationListDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadsDashBoardDetails")
    fun getLeadsDashBoardDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DateWiseExpenseDetails")
    fun getExpense(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AddDocument")
    fun saveAddDocument(@Body body: RequestBody): Call<String>

    @POST("UserValidations/PendingCountDetails")
    fun getPendingCountDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ActionType")
    fun getActionType(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ExpenseType")
    fun getExpenseType(@Body body: RequestBody): Call<String>

    @POST("UserValidations/UpdateExpenseDetails")
    fun getUpdateExpenseDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AgendaDetails")
    fun getAgendaDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ActivitiesDetails")
    fun getActivitylist(@Body body: RequestBody): Call<String>

    @POST("UserValidations/UpdateUserLoginStatus")
    fun addUpdateUserLoginStatus(@Body body: RequestBody): Call<String>

    @POST("UserValidations/NoteDetails")
    fun getNotelist(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DocumentDetails")
    fun getDocumentlist(@Body body: RequestBody): Call<String>

    @POST("UserValidations/QuatationDetails")
    fun getQuotationlist(@Body body: RequestBody): Call<String>

    @POST("UserValidations/EmployeeProfileDetails")
    fun getProfile(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadGenerateReport")
    fun getLeadGenerateReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ProductWiseLeadReport")
    fun getProductWiseLeadReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/PriorityWiseLeadReport")
    fun getPriorityWiseReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ReportNameDetails")
    fun getReportNameDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/GroupingDetails")
    fun getGroupingDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ActionListDetailsReport")
    fun getActionListDetailsReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/StatusListDetailsReport")
    fun getStatusListDetailsReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/NewListDetailsReport")
    fun getNewListDetailsReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/FollowUpListDetailsReport")
    fun getFollowUpListDetailsReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadGenerationDefaultvalueSettings")
    fun getLeadGenerationDefaultvalueSettings(@Body body: RequestBody): Call<String>

    @POST("UserValidations/UpdateNotificationStatus")
    fun getNotifreadstatus(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DeleteLeadGenerate")
    fun getDeleteLead(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AgendaType")
    fun getAgendaType(@Body body: RequestBody): Call<String>

    @POST("UserValidations/UpdateLeadManagement")
    fun getUpdateLeadManagement(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ForgotMPIN")
    fun getForgotMPIN(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DocumentDetails")
    fun getDocumentDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DocumentImageDetails")
    fun getDocumentImageDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AddRemark")
    fun getAddremark(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ReasonDetails")
    fun getReasonDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/StatusDetails")
    fun getCallStatus(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ToDoListLeadDetails")
    fun getToDoListLeadDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/EmployeeAllDetails")
    fun getEmployeeAllDetails(@Body body: RequestBody): Call<String>

    @POST("AppDetails/GetAppType")
    fun getCommonAppData(@Body body: RequestBody): Call<String>

    @POST("AppDetails/GetCompanyCode")
    fun getCompanyCode(@Body body: RequestBody): Call<String>

//    CHANEL   ReqMode =66  SubMode =22
//    CATEGORY   ReqMode =66  SubMode =20
//    COMPANY   ReqMode =66  SubMode =
    @POST("Service/GetCommonPopup")
    fun getCommonPopup(@Body body: RequestBody): Call<String>

    @POST("Service/GetServiceProductDetails")
    fun getServiceProductDetails(@Body body: RequestBody): Call<String>

    // Service ReqMode  69
    @POST("Service/GetServiceDetails")
    fun getServiceDetails(@Body body: RequestBody): Call<String>

    // Complaint  ReqMode 70
    @POST("Service/GetComplaints")
    fun getComplaints(@Body body: RequestBody): Call<String>

    @POST("Service/GetMedia")
    fun getServiceMedia(@Body body: RequestBody): Call<String>

}
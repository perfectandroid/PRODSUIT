package com.perfect.prodsuit.Api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    @POST("PickUpDelivery/PickUpDeliveryDetails")
    fun getPickupDeliveryListDetails(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/BillType")
    fun getPickupDeliveryBillType(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ProductLocationList")
    fun getFloorProductLocationList(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/PickUpDeliveryDetails")
    fun getDeliveryListDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ProductDetails")
    fun getProductDetail(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/ProductDetails")
    fun getPickUpDeliveryProductDetails(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/StandByProductDetails")
    fun getPickUpDeliveryStandByProductDetails(@Body body: RequestBody): Call<String>

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

    @POST("Agenda/AgendaCount")
    fun getAgendaDetailsList(@Body body: RequestBody): Call<String>


    @POST("UserValidations/BranchTypeDetails")
    fun getBranchType(@Body body: RequestBody): Call<String>

    @POST("UserValidations/BranchDetails")
    fun getBranch(@Body body: RequestBody): Call<String>

    @POST("UserValidations/BranchDetails")
    fun getBranchInventory(@Body body: RequestBody): Call<String>

    @POST("UserValidations/EmployeeDetails")
    fun getEmpUsingBranch(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DepartmentDetails")
    fun getDepartment(@Body body: RequestBody): Call<String>

    @POST("Project/LeadList")
    fun getLeadList(@Body body: RequestBody): Call<String>

    @POST("Report/ProjectReport")
    fun getProjectReport(@Body body: RequestBody): Call<String>

    @POST("Report/ServiceNewList")
    fun getServiceNewList(@Body body: RequestBody): Call<String>

    @POST("Report/ProjectReportDetail")
    fun getProjectReportDetail(@Body body: RequestBody): Call<String>

    @POST("Project/WorkTypeDetails")
    fun getWorkTypeDetails(@Body body: RequestBody): Call<String>

    @POST("Project/MeasurementTypeDetails")
    fun getMeasurementTypeDetails(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/GetProductComplaintList")
    fun GetProductComplaintList(@Body body: RequestBody): Call<String>

    @POST("Authorization/AuthorizationCorrectionModuleList")
    fun GetAuthorizationCorrectionModuleList(@Body body: RequestBody): Call<String>

    @POST("UserValidations/EmployeeDetails")
    fun getEmployee(@Body body: RequestBody): Call<String>

    @POST("UserValidations/RoportSettingsList")
    fun getRoportSettingsList(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadHistoryDetails")
    fun getLeadHistoryDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadGenerateReport")
    fun getGeneralReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadInfoDetails")
    fun getLeadInfoetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadImageDetails")
    fun getLocationdetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadInfoDetails")
    fun getInfoetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadHistoryDetails")
    fun getQuotationDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LeadHistoryDetails")
    fun getHistoryActtails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/UpdateLeadGenerateAction")
    fun updateLeadGenerateAction(@Body body: RequestBody): Call<String>

    @POST("UserValidations/SendMail")
    fun sendMailMessage(@Body body: RequestBody): Call<String>


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

        @POST("ServiceFollowUp/UpdateServiceFollowUp")
    fun updateServiceFollowUp(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AddNextAction")
    fun saveAddNextAction(@Body body: RequestBody): Call<String>

    @POST("UserValidations/NotificationUpdate")
    fun notificationUpdate(@Body body: RequestBody): Call<String>

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

    @POST("UserValidations/ServiceDashBoardDetails")
    fun getServiceDashBoardDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DateWiseExpenseDetails")
    fun getExpense(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AddDocument")
    fun saveAddDocument(@Body body: RequestBody): Call<String>

    @POST("UserValidations/PendingCountDetails")
    fun getPendingCountDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/UserTaskList")
    fun getUserTaskList(@Body body: RequestBody): Call<String>

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

    @POST("ServiceFollowUp/ServiceFollowUpdetails")
    fun getServiceFollowUpDetailsList(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/Attendancedetails")
    fun getServiceFollowUpAttendanceList(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/ServiceHistoryDetails")
    fun getServiceFollowUpHistory(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/ServiceAttendedDetails")
    fun getServiceFollowUpMappedServiceAttendedList(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/ReplaceProductdetails")
    fun getServiceFollowUpMappedReplacedProductList(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/AddedService")
    fun getServiceFollowUpMoreServiceAttendedList(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/ServiceType")
    fun getServiceFollowUpServiceType(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/ChangemodeDetailse")
    fun getServiceFollowUpChangeMode(@Body body: RequestBody): Call<String>
    @POST("ServiceFollowUp/PopUpProductdetails")
    fun getServiceFollowUpMoreRepalcedProduct(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/EmployeeWiseTicketSelect")
    fun getServiceFollowUpInfo(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ProductWiseLeadReport")
    fun getProductWiseLeadReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/PriorityWiseLeadReport")
    fun getPriorityWiseReport(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ReportNameDetails")
    fun getReportNameDetails(@Body body: RequestBody): Call<String>

    @POST("Report/ProjectReportNameDetails")
    fun getProjectReportNameDetails(@Body body: RequestBody): Call<String>

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

    @POST("UserValidations/CompanyLogDetails")
    fun getCompanyLogo(@Body body: RequestBody): Call<String>

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

    @POST("ServiceFollowUp/ServiceDetails")
    fun getServiceFollowUpServiceDetails(@Body body: RequestBody): Call<String>

    // Complaint  ReqMode 70
    @POST("Service/GetComplaints")
    fun getComplaints(@Body body: RequestBody): Call<String>

    @POST("Service/GetMedia")
    fun getServiceMedia(@Body body: RequestBody): Call<String>

    @POST("Service/GetCustomerList")
    fun getCustomerList(@Body body: RequestBody): Call<String>

    @POST("Service/UpdateCustomerServiceRegister")
    fun UpdateCustomerServiceRegister(@Body body: RequestBody): Call<String>

    @POST("Service/GetWarrantyDetails")
    fun getWarrantyDetails(@Body body: RequestBody): Call<String>

    @POST("Service/GetProductHistory")
    fun getProductHistory(@Body body: RequestBody): Call<String>

    @POST("Service/GetSalesHistory")
    fun getSalesHistory(@Body body: RequestBody): Call<String>

    @POST("ServiceAssign/ServiceCountDetails")
    fun getServiceCountDetails(@Body body: RequestBody): Call<String>

    @POST("ServiceAssign/ServiceAssignNewDetails")
    fun getServiceAssignNewDetails(@Body body: RequestBody): Call<String>

    @POST("ServiceAssign/ServiceAssignOnGoingDetails")
    fun getServiceAssignOnGoingDetails(@Body body: RequestBody): Call<String>

    @POST("ServiceAssign/RoleDetails")
    fun getRoleDetails(@Body body: RequestBody): Call<String>

    @POST("ServiceAssign/ServiceAssignDetails")
    fun getServiceAssignDetails(@Body body: RequestBody): Call<String>

    @POST("ServiceAssign/CustomerserviceassignUpdate")
    fun getCustomerserviceassignUpdate(@Body body: RequestBody): Call<String>

    @POST("ServiceAssign/CustomerserviceassignEdit")
    fun getCustomerserviceassignEdit(@Body body: RequestBody): Call<String>


    @POST("Service/CustomerServiceRegisterCount")
    fun getCustomerserviceregisterCount(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/PickupandDeliveryCount")
    fun getPickupandDeliveryCount(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/UpdateDeliverStatusDetails")
    fun getPickupandDeliveryUpdateDetails(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/PickUPProductInformationDetails")
    fun getProductInformationDetails(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/DeliveryProductInformationDetails")
    fun getDeliveryProductInformationDetails(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/UpdatePickUpAndDelivery")
    fun getUpdatePickUpAndDelivery(@Body body: RequestBody): Call<String>


    @POST("ServiceFollowUp/FollowUpPaymentMethod")
    fun getFollowUpPaymentMethod(@Body body: RequestBody): Call<String>

    @POST("UserValidations/GetGenralSettings")
    fun getGenralSettings(@Body body: RequestBody): Call<String>

    @POST("WalkingCustomer/WalkingCustomerDetailsList")
    fun getWalkingCustomerDetailsList(@Body body: RequestBody): Call<String>

    @POST("Service/CustomerDueDetils")
    fun getCustomerDueDetils(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/MoreComponentDetails")
    fun getMoreComponentDetails(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/FollowUpActionDetails")
    fun getServiceFollowUpAction(@Body body: RequestBody): Call<String>

    @POST("PickUpDelivery/BillTyep")
    fun getServiceBillType(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/UpdateServiceFollowUp")
    fun UpdateServiceFollowUp(@Body body: RequestBody): Call<String>

    @POST("EMICollection/EMICollectionReportCount")
    fun getEMICollectionReportCount(@Body body: RequestBody): Call<String>

    @POST("EMICollection/FinancePlanTypeDetails")
    fun getFinancePlanTypeDetails(@Body body: RequestBody): Call<String>

    @POST("EMICollection/EMICollectionReport")
    fun getEMICollectionList(@Body body: RequestBody): Call<String>

    @POST("EMICollection/EMIAccountDetails")
    fun getEMIAccountDetails(@Body body: RequestBody): Call<String>

    @POST("EMICollection/UpdateEMICollection")
    fun saveUpdateEMICollection(@Body body: RequestBody): Call<String>

    @POST("UserValidations/LocationUpdate")
    fun UpdateLocationUpdate(@Body body: RequestBody): Call<String>

    @POST("UserValidations/FollowupStatusUpdate")
    fun UpdateFollowupStatusUpdate(@Body body: RequestBody): Call<String>

    @POST("WalkingCustomer/WalkingCustomerAssignedTo")
    fun getWalkingCustomerAssignedTo(@Body body: RequestBody): Call<String>

    @POST("WalkingCustomer/UpdateWalkingCustomer")
    fun UpdateWalkingCustomerData(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AttanceMarkingUpdate")
    fun UpdateAttanceMarkingUpdate(@Body body: RequestBody): Call<String>

    @POST("UserValidations/EmployeeLocationUpdate")
    fun UpdateEmployeeLocationUpdate(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DesignationDetails")
    fun getDesignationDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/EmployeeDetails")
    fun getEmployeeDetails(@Body body: RequestBody): Call<String>

    @POST("UserValidations/EmployeeLocationList")
    fun getEmployeeLocationList(@Body body: RequestBody): Call<String>

    @POST("UserValidations/EmployeeWiseLocationList")
    fun getEmployeeWiseLocationList(@Body body: RequestBody): Call<String>

    @POST("MobileNotification/SaveCustomerFCMToken")
    fun getSaveCustomerFCMToken(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ItemSearchList")
    fun getItemSearchList(@Body body: RequestBody): Call<String>

    @POST("CustomerPortalAPI/GetCustomerAccessDetails")
    fun getCustomerAccessDetails(@Body body: RequestBody): Call<String>

    @POST("Stock/StockRTEmployeeDetails")
    fun getStockRTEmployeeDetails(@Body body: RequestBody): Call<String>

    @POST("Stock/StockRTProductDetails")
    fun getStockRTProductDetails(@Body body: RequestBody): Call<String>

    @POST("Stock/UpdateStockTransfer")
    fun updateStockTransfer(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ProductEnquiryList")
    fun getProductEnquiryList(@Body body: RequestBody): Call<String>

    @POST("UserValidations/ProductEnquiryDetails")
    fun getProductEnquiryDetails(@Body body: RequestBody): Call<String>

    @POST("Authorization/AuthorizationModuleList")
    fun getAuthorizationModuleList(@Body body: RequestBody): Call<String>

    @POST("Authorization/AuthorizationList")
    fun getAuthorizationList(@Body body: RequestBody): Call<String>

    @POST("Authorization/AuthorizationAction")
    fun getAuthorizationAction(@Body body: RequestBody): Call<String>

    @POST("Authorization/AuthorizationRejectUpdate")
    fun saveAuthorizationRejectUpdate(@Body body: RequestBody): Call<String>

    @POST("Authorization/AuthorizationApproveUpdate")
    fun saveAuthorizationApproveUpdate(@Body body: RequestBody): Call<String>

    @POST("Stock/GetStockRequestList")
    fun getStockRequestList(@Body body: RequestBody): Call<String>

    @POST("Stock/GetStockRequestProductList")
    fun getStockRequestProductList(@Body body: RequestBody): Call<String>

    @POST("Stock/StockSTDelete")
    fun StockSTDelete(@Body body: RequestBody): Call<String>

    @POST("Stock/StockSTProductDetails")
    fun getStockSTProductDetails(@Body body: RequestBody): Call<String>

    @POST("Stock/GetStockRequestListInTransfer")
    fun getStockRequestListInTransfer(@Body body: RequestBody): Call<String>

    @POST("Authorization/AuthorizationCorrection")
    fun getAuthorizationCorrection(@Body body: RequestBody): Call<String>

    @POST("WalkingCustomer/WalkingCustomerListByMobileNumer")
    fun getWalkingCustomerListByMobileNumer(@Body body: RequestBody): Call<String>

    @POST("Authorization/AuthorizationCorrectionDetailsList")
    fun getAuthorizationCorrectionDetailsList(@Body body: RequestBody): Call<String>

    @POST("Authorization/AuthorizationCorrectionLeadDetails")
    fun getAuthorizationCorrectionLeadDetails(@Body body: RequestBody): Call<String>


    @POST("WalkingCustomer/WalkingCustomerVoiceDetails")
    fun getVoiceNote(@Body body: RequestBody): Call<String>

    @POST("Project/ProjectList")
    fun getProjectList(@Body body: RequestBody): Call<String>

    @POST("Project/ProjectStages")
    fun getProjectStages(@Body body: RequestBody): Call<String>

    @POST("Project/ProjectTeam")
    fun getProjectTeam(@Body body: RequestBody): Call<String>

    @POST("Project/EmployeeListforProject")
    fun getEmployeeListforProject(@Body body: RequestBody): Call<String>

    @POST("Project/ModeList")
    fun getModeList(@Body body: RequestBody): Call<String>

    @POST("Project/ProductList")
    fun getProductList(@Body body: RequestBody): Call<String>

    @POST("Project/UpdateMaterialUsage")
    fun getUpdateMaterialUsage(@Body body: RequestBody): Call<String>

    @POST("Project/MaterialRequestProductList")
    fun getMaterialRequestProductList(@Body body: RequestBody): Call<String>

    @POST("Project/UpdateMaterialRequest")
    fun getUpdateMaterialRequest(@Body body: RequestBody): Call<String>

    @POST("ServiceAssign/CustomerBalanceDetails")
    fun getCustomerBalance(@Body body: RequestBody): Call<String>
    @POST("UserValidations/AuthorizationDetails")
    fun getAuthorizationDashDetails(@Body body: RequestBody): Call<String>


    @POST("ServiceFollowUp/ServiceHistoryDetails")
    fun getServiceHist(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/WarrantyAMCDetails")
    fun getWarrantyAMC(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/ComplaintListDetails")
    fun getComplaintListDetails(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/FollowUpActionDetails")
    fun getActionTakenAction(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/ServiceInvoiceDetails")
    fun getClosedTicketList(@Body body: RequestBody): Call<String>

    @POST("ServiceAssign/ServiceAssignedWork")
    fun getAssignedTicketList(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/AddedService")
    fun getAddedServiceList(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/OtherChargeDetails")
    fun getOtherCharges(@Body body: RequestBody): Call<String>


    @POST("ServiceFollowUp/GetSubProductDetails")
    fun getSubProductDetails(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/GetProductList")
    fun getCompProductList(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/GetMainProductDetails")
    fun getMainProductDetails(@Body body: RequestBody): Call<String>

    @POST("ServiceFollowUp/GetProductInfo")
    fun getProductInfo(@Body body: RequestBody): Call<String>

    @POST("UserValidations/DashBoardModule")
    fun getDashboardModules(@Body body: RequestBody): Call<String>


    @POST("DashBoard/TileLeadDashBoardDetails")
    fun getDashboardTileDetails(@Body body: RequestBody): Call<String>

    @POST("DashBoard/InventoryMonthlySaleGraph")
    fun getInventoryMonthlySaleDetails(@Body body: RequestBody): Call<String>

    @POST("DashBoard/InventoryStockListCategory")
    fun getStockListCategoryDetails(@Body body: RequestBody): Call<String>

    @POST("Project/UpdateProjectFollowUp")
    fun saveUpdateProjectFollowUp(@Body body: RequestBody): Call<String>

    @POST("UserValidations/AuthorizationDataList")
    fun getAuthorizationDataList(@Body body: RequestBody): Call<String>

    @POST("Project/UnitList")
    fun getUnit(@Body body: RequestBody): Call<String>

    @POST("DashBoard/EmployeeWiseTaegetInPercentage")
    fun getEmployeewise(@Body body: RequestBody): Call<String>

    @POST("Project/ProjectStatus")
    fun getProjectStatus(@Body body: RequestBody): Call<String>

    @POST("Project/ProjectOtherChargeDetails")
    fun getProjectOtherChargeDetails(@Body body: RequestBody): Call<String>

    @POST("Project/OtherChargeTaxCalculationDetails")
    fun getOtherChargeTaxCalculationDetails(@Body body: RequestBody): Call<String>

    @POST("Project/checkDetails")
    fun getProjectcheckDetails(@Body body: RequestBody): Call<String>

    @POST("Project/UpadateSiteVisit")
    fun saveUpadateSiteVisit(@Body body: RequestBody): Call<String>

    @POST("Project/DownloadImage")
    fun saveDownloadImage(@Body body: RequestBody): Call<String>

    @POST("Project/UpdateProjectTransaction")
    fun saveUpdateProjectTransaction(@Body body: RequestBody): Call<String>

    @POST("DashBoard/InventoryProductReorderLevel")
    fun getInventoryProductReorderLevel(@Body body: RequestBody): Call<String>

    // CRM DASH

    // Ticket outstanding =>  DashMode = 4 , DashType = 1
    // Ticket Status =>  DashMode = 5, DashType = 1
    @POST("DashBoard/CRMTileDashBoardDetails")
    fun getCRMTileDashBoardDetails(@Body body: RequestBody): Call<String>

    @POST("DashBoard/CRMStagewiseDetails")
    fun getCRMStagewiseDetails(@Body body: RequestBody): Call<String>

    @POST("DashBoard/CRMcomplaintwise")
    fun getCRMcomplaintwise(@Body body: RequestBody): Call<String>

    @POST("DashBoard/CRMservicewise")
    fun getCRMservicewise(@Body body: RequestBody): Call<String>

    @POST("Project/ProjectSiteVisitCount")
    fun getProjectSiteVisitCount(@Body body: RequestBody): Call<String>

    @POST("Project/ProjectSiteVisitAssign")
    fun getProjectSiteVisitAssign(@Body body: RequestBody): Call<String>



    @POST("DashBoard/Leadstagewiseforcast")
    fun getLeadStagewiseForecast(@Body body: RequestBody): Call<String>



}
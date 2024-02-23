package com.perfect.prodsuit.View.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.*
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ServiceFollowUpMainProductAdapter
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ServiceFollowUPActiivty : AppCompatActivity(), View.OnClickListener,ItemClickListener {

    var TAG                                             = "ServiceFollowUPActiivty"
    lateinit var context                              : Context
    lateinit var servicedetailsViewModel              : ServiceDetailsViewModel
    lateinit var productwisecomplaintViewModel        : ProductWiseComplaintViewModel
    lateinit var serviceFollowUpInfoViewModel         : ServiceFollowUpInfoViewModel
    lateinit var serviceFollowUpMoreServiceViewModel  : ServiceFollowUpMoreServiceViewModel
    lateinit var addServiceViewModel                  : AddServiceViewModel

    private var edtCategory                           : TextInputEditText?   = null
    private var edtSubCategory                        : TextInputEditText?   = null
    private var edtBrand                              : TextInputEditText?   = null
    private var edtComplaint                          : TextInputEditText?   = null

    internal var jsonObjectList                       : JSONObject?     = null
    private var tv_followupticket                     : TextView?       = null
    private var txt_next                              : TextView?       = null
    private var txt_previous                          : TextView?       = null
    private var tv_main_header                        : TextView?       = null
    private var vw_previous                           : View?       = null
    private var imv_infofollowup                      : ImageView?      = null
    private var imback                                : ImageView?      = null
    private var imv_filterfollowup                    : ImageView?      = null
    private var imv_addproduct                        : ImageView?      = null
    private var imv_scannerfollowup                   : ImageView?      = null
    private var rcyler_followup                       : RecyclerView?   = null
    private var rcyler_service3                       : RecyclerView?   = null
    private var recyCompService                       : RecyclerView?   = null
    private var ll_service_Attended                   : LinearLayout?   = null
    private var ll_service_Attended_category          : LinearLayout?   = null
    private var ll_Service3                           : LinearLayout?   = null
    private var progressDialog                        : ProgressDialog? = null
    private var add_Product                           : FloatingActionButton?= null
    private var dialogMoreServiceAttendeSheet         : Dialog?         = null
    private var dialogReplacedMode                    : Dialog?         = null
    private var dialogWarrantydMode                   : Dialog?         = null
    private var recyclr_Product                       : RecyclerView?   = null
    private var recyFollowupAttended                  : RecyclerView?   = null
    var servDetadapter                                : ServiceDetailsAdapter? = null
    var compAdapter                                   : complaint_service_followup? = null
    var adapterServiceAttended                        : ServiceAttendedAdapter? = null
    var serviceFollowUpInfoObjectList                 : JSONObject      = JSONObject()
    var serviceFollowUpInfoObjectList_2               : JSONObject      = JSONObject()
    var jsonArrayServiceType                          : JSONArray       = JSONArray()
    var jsonArrayServicelist                          : JSONArray       = JSONArray()
    var jsonsubproductArrayServiceType                : JSONArray       = JSONArray()
    var complaintfollowup                             : JSONArray       = JSONArray()
    var jsonArrayMoreServiceAttended                  : JSONArray       = JSONArray()
    var sortMoreServiceAttended                       : JSONArray       = JSONArray()
    var selectMoreServiceAttended                     : JSONArray       = JSONArray()
    var runningStatus                                 : String?         = ""
    var FK_Product                                    : String?         = ""
    var NameCriteria                                  : String?         = ""
    var leadShow                                      : String?         = ""
    var ID_Customerserviceregister                    : String          = ""
    var ID_CustomerserviceregisterProductDetails      : String          = ""
    var ID_Category                                   : String          = ""
    var ID_Product                                    : String          = ""
    var BindProduct                                   : String          = ""
    val modelServiceAttendedTemp                                        = ArrayList<ModelServiceAttendedTemp>()
    val modelServiceAttended                                            = ArrayList<ModelServiceAttended>()
    val modelMoreServices                                               = ArrayList<ModelMoreServices>()
    val modelServicesListDetails                                        = ArrayList<ServiceDetailsFullListModel>()
    val modelServicesSubListDetails                                     = ArrayList<ServiceDetailsSubListModel>()
    val modelMoreServicesTemp                                           = ArrayList<ModelMoreServicesTemp>()
    val serviceTab3MainModel                                            = ArrayList<ServiceTab3MainModel>()
    val serviceTab3SubModel                                             = ArrayList<ServiceTab3SubModel>()
    val servicepartsReplacedModel                                       = ArrayList<ServicePartsReplacedModel>()
    val labelpartsreplaceModel                                          = ArrayList<LabelPartsreplaceModel>()
    var serviceFollowUpServiceType                                      = 0
    var serviceFollowUpInfo                                             = 0
    var serviceFollowUpMoreService                                      = 0
    var productwisecomplaintcouny                                       = 0

    lateinit var actionTakenViewModel: ActionTakenViewModel
    lateinit var leadActionViewModel: LeadActionViewModel
    lateinit var otherChargesViewViewModel: OtherChargesViewViewModel
    lateinit var pickupdelibilltypeviewmodel: PickupDeliBillTypeViewModel
    lateinit var paymentMethodeViewModel: PaymentMethodViewModel
    private var rcyler_actionTaken: RecyclerView? = null
    var actionTypeActionList: JSONArray = JSONArray()
    var leadActionList: JSONArray = JSONArray()
    var otherChargeList: JSONArray = JSONArray()
    val actionTakenSelected = ArrayList<ActionTakenMainModel>()
    var otherChargesFinalList = ArrayList<OtherChargesMainModel>()
    var actionTakenActioncouny = 0

    var db_helper: DBHelper? = null
    var replacemodeAdapter:  ServiceReplacedModeAdaper? = null
    var servicewarrantyModeAdapter:  ServiceWarrantyModeAdapter? = null
    var actionTakeActionFilter: ActionTakeActionFilter? = null
    var leadActionAdapter: LeadActionAdapter? = null
    var actionTakenAdapter: ActionTakenAdapter? = null
    var modEditPosition = 0
    var warrantyEditPosition = 0
    var modChanged = 0
    var billTypecount = 0
    var paymentCount = 0
    var billtype=""
    var id_search  : String= ""
    var subMode : String?         = "2"
    var servicesearchcount= 0
    //................................
    private var recy_main_product_topbar                : RecyclerView?       = null
    private var recy_parts_replaced                     : RecyclerView?       = null
    private var recyreplacedmode                        : RecyclerView?       = null
    private var recywarrantymode                        : RecyclerView?       = null
    private var ll_top_bar                              : LinearLayout?       = null
    private var ll_part_repaced2                              : LinearLayout?       = null
    private var ll_ProdWarrantyAmc                      : LinearLayout?       = null
    private var ll_partsReplaced                        : LinearLayout?       = null
    private var ll_service                              : LinearLayout?       = null
    lateinit var subProductViewModel                    : SubProductViewModel
    lateinit var serviceReplacedModeViewModel           : ServiceReplacedModeViewModel
    lateinit var warrantymodeViewModel                  : WarrantyModeViewModel
    lateinit var SubproductDetailsList                  : JSONArray
    lateinit var WarrantyArrayList                      : JSONArray
    lateinit var ReplaceArrayList                       : JSONArray
    var SubproductDetails                               : JSONObject          = JSONObject()
    var ProductSubDetails                               = JSONArray()
    var mainProductInfo                                 = 0
    var warrantycount                                   = 0
    var replacedcount                                   = 0
    var ReplceModeSub                                   = ""
    var WarrantyMode                                    = ""
    var TicketRegDate                                    = ""
    //................................


    // 24-10-2023 Ranjith
    var modeTab = 0  // 0 - Service Attended , 1 = Part Replaced ,2 = Service , 3 =Attendance , 4 = Action taken
    var serviceTab3Adapter : ServiceTab3Adapter? = null
    var FK_Product_Pos : Int?         = 0
    var FK_Product_ID : String?         = ""
    var FK_CustomerWiseProductDetails_ID : String?         = ""
    var addserviceMode              = 0
    lateinit var addserviceArrayList: JSONArray
    private var dialogAddserviceSheet : Dialog? = null
    val addServiceDetailMode        = ArrayList<AddServiceDetailMode>()
    lateinit var serviceFollowUpServiceTypeViewModel: ServiceFollowUpServiceTypeViewModel

    private var dialogServType : Dialog? = null

    private var serviceDetailsArray = JSONArray()
    private var servicePartsArray = JSONArray()
    private var serviceIncentiveArray = JSONArray()
    private var getProductSubDetailsArray = JSONArray()

    private var DateType: Int = 0
    private var tie_DateAttended: TextInputEditText? = null
    private var edttotalSecurityAmount: TextInputEditText? = null
    private var edtnetAmount: TextInputEditText? = null
    private var edtcomponentCharge: TextInputEditText? = null
    private var edttotalServiceCost: TextInputEditText? = null
    private var edtdiscountAmount: TextInputEditText? = null
    private var tie_Selectbilltype: AutoCompleteTextView? = null
    private var edt_other_charges: TextView? = null
    private var tv_payment_type: TextView? = null
    var otherChargesAdapterAdapter: OtherChargesAdapterAdapter? = null
    var serviceFollowAttendanceAdapter: ServiceFollowAttendanceAdapter? = null
    lateinit var billtypeArrayList: JSONArray
    var pickupdeliStatusCount = 0
    lateinit var paymentMethodeArrayList: JSONArray
    private var dialogPaymentMethod: Dialog? = null
    var recyPaymentMethod: RecyclerView? = null
    private var dialogPaymentSheet: Dialog? = null
    private var edtPayMethod: EditText? = null
    private var edtPayRefNo: EditText? = null
    private var edtPayAmount: EditText? = null
    private var txt_pay_method: TextView? = null
    private var txt_pay_Amount: TextView? = null
    private var txt_bal_Amount: TextView? = null
    private var txtPayBalAmount: TextView? = null
    private var img_PayAdd: ImageView? = null
    private var img_PayRefresh: ImageView? = null
    private var btnApply: Button? = null
    var arrPayment = JSONArray()
    var arrPaymentFinal = JSONArray()
    var arrOtherChargeFinal = JSONArray()
    internal var recyPaymentList: RecyclerView? = null
    internal var ll_paymentlist: LinearLayout? = null
    internal var ll_action_taken: LinearLayout? = null
    internal var ll_infofiltradd: LinearLayout? = null
    var adapterPaymentList: PaymentListAdapter? = null
    var arrAddUpdate: String? = "0"
    var applyMode = 0
    var netAmount = 0.0
    var ID_PaymentMethod: String? = ""
    var arrPosition: Int? = 0

    lateinit var serviceFollowUpAttendanceListViewModel: ServiceFollowUpAttendanceListViewModel
    var serviceFollowUpAttendance = 0
    var serviceFollowUpAttendanceArrayList: JSONArray = JSONArray()
    val modelFollowUpAttendance = java.util.ArrayList<ModelFollowUpAttendance>()
    val AttendedEmployeeDetails= JSONArray()
    var saveLeadGenDet = 0
    lateinit var serviceFollowUpSaveModel: ServiceFollowUpSaveViewModel

    var allproductlst                                                   = 0
    lateinit var allProductViewModel: AllProductViewModel
    lateinit var allProductArrayList: JSONArray
    lateinit var allProductSort: JSONArray
    private var dialogAllproductSheet : Dialog? = null
    val calendar11: Calendar? = Calendar.getInstance()
    val calendar22: Calendar? = Calendar.getInstance()

    var mainSubProduct                                                   = 0
    lateinit var mainSubProductViewModel: MainSubProductViewModel
    lateinit var mainSubProductArrayList: JSONArray

    var followupDate : String?= ""
//    Changes 26.10.2023
    var compnantMode                                      = 0
    lateinit var servCompanantViewModel: ServCompanantViewModel
    var servCompanantArray = JSONArray()
    var servicePartsAdapter: ServiceParts_replacedAdapter? = null

    var followUpType                                      = 0
    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    private var dialogFollowupType: Dialog? = null
    var recyFollowupType: RecyclerView? = null
    lateinit var followUpTypeArrayList: JSONArray
    lateinit var followUpTypeSort: JSONArray

    var employee                                      = 0
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    lateinit var employeeSort: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null


    var companantCharge : String? = "0"
    var discountAmount : String? = "0"
    var totalServiceCost : String? = "0"
    var strActiontakenStatusMessage                                      = ""
    var strSumPayMethode = "0"

    var saveAttendanceMark = false


    var categoryDet = 0
    var subCategoryDet = 0
    var brandDet = 0
    var complaintDet = 0

    var ReqMode: String? = ""

    lateinit var productCategoryViewModel: ProductCategoryViewModel
    lateinit var categoryArrayList : JSONArray
    lateinit var categorySort : JSONArray
    private var dialogCategory : Dialog? = null
    var recyCategory: RecyclerView? = null

    lateinit var subCategoryViewModel: SubCategoryViewModel
    lateinit var subCategoryArrayList : JSONArray
    lateinit var subCategorySort : JSONArray
    private var dialogSubCategory : Dialog? = null

    lateinit var brandViewModel: BrandViewModel
    lateinit var brandArrayList : JSONArray
    lateinit var brandSort : JSONArray
    private var dialogBrand : Dialog? = null
    var recyBrand: RecyclerView? = null

    lateinit var serviceComplaintViewModel: ServiceComplaintViewModel
    lateinit var complaintArrayList : JSONArray
    lateinit var complaintSort : JSONArray
    private var dialogComplaint : Dialog? = null
    var recyComplaint: RecyclerView? = null

    var ID_SubCategory: String? = ""
    var ID_Brand: String? = ""
    var ID_Complaint: String? = ""

    var PSValue: String? = ""
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_follow_upactiivty)
        context = this@ServiceFollowUPActiivty

        servicedetailsViewModel = ViewModelProvider(this).get(ServiceDetailsViewModel::class.java)
        serviceFollowUpInfoViewModel =
            ViewModelProvider(this).get(ServiceFollowUpInfoViewModel::class.java)
        serviceFollowUpMoreServiceViewModel =
            ViewModelProvider(this).get(ServiceFollowUpMoreServiceViewModel::class.java)
        productwisecomplaintViewModel =
            ViewModelProvider(this).get(ProductWiseComplaintViewModel::class.java)
        actionTakenViewModel = ViewModelProvider(this).get(ActionTakenViewModel::class.java)
        leadActionViewModel = ViewModelProvider(this).get(LeadActionViewModel::class.java)
        otherChargesViewViewModel =
            ViewModelProvider(this).get(OtherChargesViewViewModel::class.java)
        pickupdelibilltypeviewmodel =
            ViewModelProvider(this).get(PickupDeliBillTypeViewModel::class.java)
        paymentMethodeViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)
        serviceFollowUpInfoViewModel = ViewModelProvider(this).get(ServiceFollowUpInfoViewModel::class.java)
        serviceFollowUpMoreServiceViewModel = ViewModelProvider(this).get(ServiceFollowUpMoreServiceViewModel::class.java)
        productwisecomplaintViewModel = ViewModelProvider(this).get(ProductWiseComplaintViewModel::class.java)
        addServiceViewModel = ViewModelProvider(this).get(AddServiceViewModel::class.java)
        serviceFollowUpServiceTypeViewModel = ViewModelProvider(this).get(ServiceFollowUpServiceTypeViewModel::class.java)
        servCompanantViewModel = ViewModelProvider(this).get(ServCompanantViewModel::class.java)

        subProductViewModel = ViewModelProvider(this).get(SubProductViewModel::class.java)
        serviceReplacedModeViewModel = ViewModelProvider(this).get(ServiceReplacedModeViewModel::class.java)
        warrantymodeViewModel = ViewModelProvider(this).get(WarrantyModeViewModel::class.java)
        serviceFollowUpAttendanceListViewModel = ViewModelProvider(this).get(ServiceFollowUpAttendanceListViewModel::class.java)
        serviceFollowUpSaveModel = ViewModelProvider(this).get(ServiceFollowUpSaveViewModel::class.java)
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        allProductViewModel = ViewModelProvider(this).get(AllProductViewModel::class.java)
        mainSubProductViewModel = ViewModelProvider(this).get(MainSubProductViewModel::class.java)
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        subCategoryViewModel = ViewModelProvider(this).get(SubCategoryViewModel::class.java)
        brandViewModel = ViewModelProvider(this).get(BrandViewModel::class.java)
        serviceComplaintViewModel = ViewModelProvider(this).get(ServiceComplaintViewModel::class.java)

        db_helper = DBHelper(this,null)
        try {
            runningStatus = intent.getStringExtra("runningStatus")
            ID_Customerserviceregister = intent.getStringExtra("customer_service_register").toString()
            ID_CustomerserviceregisterProductDetails = intent.getStringExtra("ID_CustomerserviceregisterProductDetails").toString()
            jsonObjectList = JSONObject(intent.getStringExtra("jsonObject").toString());

            ID_Product = jsonObjectList!!.getString("FK_Product")
            if (PSValue.equals("1")){
                ID_Category = jsonObjectList!!.getString("FK_Category")
            }


        }catch (e : Exception){

        }

        val PSValueSP = applicationContext.getSharedPreferences(Config.SHARED_PREF81, 0)
        PSValue = PSValueSP.getString("PSValue", "")
        setRegviews()
        modeTab = 0
        loadlayout()
        leadShow = "1"


        serviceFollowUpInfo = 0
        loadInfo(ID_Customerserviceregister,ID_CustomerserviceregisterProductDetails)


        checkAttendance()
        clearData()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun checkAttendance() {

        saveAttendanceMark = false
        val UtilityListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF57, 0)
        val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
        var boolAttendance = jsonObj!!.getString("ATTANCE_MARKING").toBoolean()
        if (boolAttendance) {
            val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)
            var status = StatusSP.getString("Status", "")
            if (status.equals("0") || status.equals("")) {
                Common.punchingRedirectionConfirm(this, "", "")
            } else if (status.equals("1")) {
                saveAttendanceMark = true
            }

        } else {
            saveAttendanceMark = true
        }
    }

    private fun clearData() {
        otherChargesFinalList.clear()
    }

    private fun loadlayout() {


        ll_service_Attended_category!!.visibility = View.GONE
        ll_service_Attended!!.visibility = View.GONE
        ll_part_repaced2!!.visibility = View.GONE
//        ll_top_bar!!.visibility = View.GONE
//        ll_Service3!!.visibility = View.GONE
        ll_Service3!!.visibility = View.GONE
        ll_action_taken!!.visibility = View.GONE


//        0 - Service Attended , 1 = Part Replaced ,2 = Service , 3 =Attendance , 4 = Action taken
Log.v("adasdasds","modeTab "+modeTab)
        if (modeTab == 0){
            Log.v("adasdasds","modeTab0 ")
            Log.e(TAG,"MODE  421110   "+PSValue)
            if (PSValue.equals("1")){
                ll_service_Attended!!.visibility = View.VISIBLE
                ll_service_Attended_category!!.visibility = View.GONE
                ll_infofiltradd!!.visibility = View.VISIBLE
            }else {
                ll_service_Attended!!.visibility = View.GONE
                ll_service_Attended_category!!.visibility = View.VISIBLE
                ll_infofiltradd!!.visibility = View.GONE
            }

           //
            txt_previous!!.visibility = View.GONE
            vw_previous!!.visibility = View.GONE
            txt_next!!.visibility = View.VISIBLE

            tv_main_header!!.text = "Service Attended"
            imv_filterfollowup!!.visibility = View.VISIBLE
            imv_addproduct!!.visibility = View.VISIBLE
        }
        if (modeTab == 1){
            Log.e(TAG,"MODE  421111   ")
            ll_part_repaced2!!.visibility = View.VISIBLE

            txt_previous!!.visibility = View.VISIBLE
            vw_previous!!.visibility = View.VISIBLE
            txt_next!!.visibility = View.VISIBLE
            tv_main_header!!.text = "Parts Replaced"
            imv_filterfollowup!!.visibility = View.GONE
            imv_addproduct!!.visibility = View.GONE

        }
        if (modeTab == 2){
            Log.e(TAG,"MODE  421112   ")
            ll_top_bar!!.visibility = View.VISIBLE

            Log.v("adasdasds","modeTab1 ")
            ll_Service3!!.visibility = View.VISIBLE
            txt_previous!!.visibility = View.VISIBLE
            vw_previous!!.visibility = View.VISIBLE
            txt_next!!.visibility = View.VISIBLE
            tv_main_header!!.text = "Service"
            imv_filterfollowup!!.visibility = View.GONE
            imv_addproduct!!.visibility = View.GONE
        }
//        if (modeTab == 2){
//            ll_Service3!!.visibility = View.VISIBLE
//        }
        if (modeTab == 3) {
            Log.e(TAG,"MODE  421113   ")

            Log.v("adasdasds","modeTab4 ")
            ll_action_taken!!.visibility = View.VISIBLE
            tv_main_header!!.text = "Action Taken"
            imv_filterfollowup!!.visibility = View.GONE
            imv_addproduct!!.visibility = View.GONE
            loadActionTaken()
        }
        if (modeTab == 5) {
            Log.e(TAG,"MODE  421114   ")
            Log.v("adasdasds","modeTab5 ")
            finalSave()

        }
    }

    private fun finalSave() {
        val Actionproductdetails = JSONArray()
        for (obj in actionTakenSelected) {
            val jsonObject = JSONObject()
            jsonObject.put("ID_Product", obj.FK_Product)
            jsonObject.put("ID_NextAction", obj.ID_Action)
            jsonObject.put("ID_NextActionLead", obj.ID_leadAction)
            jsonObject.put("FK_ActionType", obj.ID_ActionType)
            jsonObject.put("FK_EmployeeAssign", obj.ID_Emplloyee)
            jsonObject.put("NextActionDate", Config.convertDate(obj.FollowupDate))
            jsonObject.put("ProvideStandBy", obj.ProvideStandBy)
            jsonObject.put("CustomerNote", obj.Customer_note)
            jsonObject.put("EmployeeNote", obj.Employee_note)
            jsonObject.put("SecurityAmount", obj.securityAmount)
            jsonObject.put("OfferPrice", obj.buyBackAmount)
            jsonObject.put("ID_CustomerWiseProductDetails", obj.ID_CustomerWiseProductDetails)
            jsonObject.put("FK_ProductNumberingDetails", "0")
            Actionproductdetails.put(jsonObject)

        }
        Log.v("sdfsdfsd","Actionproductdetails "+Actionproductdetails.toString())
        Log.e(TAG, "serviceDetailsArray      1236541   " + serviceDetailsArray)
        Log.e(TAG, "ProductSubDetails        1236542   " + ProductSubDetails)
        Log.e(TAG, "Actionproductdetails     1236543   " + Actionproductdetails)
        Log.e(TAG, "AttendedEmployeeDetails  1236544   " + AttendedEmployeeDetails)
        Log.e(TAG, "serviceIncentiveArray    1236545   " + serviceIncentiveArray)
        Log.e(TAG, "arrOtherChargeFinal      1236546   " + arrOtherChargeFinal)
        Log.e(TAG, "arrPaymentFinal          1236547   " + arrPaymentFinal)
        Log.e(TAG, "servicePartsArray        1236548   " + servicePartsArray)
        Log.v("sdfsdfsd", "payment  " + arrPaymentFinal.toString())
        Log.v("sdfsdfsd", "other  " + arrOtherChargeFinal.toString())

//        var attendedDate = tie_DateAttended!!.text
//        var totalSecurityAmount = edttotalSecurityAmount!!.text
//        var componentCharge = edtcomponentCharge!!.text
//        var totalServiceCost = edttotalServiceCost!!.text
//        var discountAmount = edtdiscountAmount!!.text
//        var edtnetAmount = tie_DateAttended!!.text

        Log.e(TAG, "ID_Customerserviceregister   " + ID_Customerserviceregister)
        Log.e(TAG, "ID_CustomerserviceregisterProductDetails   " + ID_CustomerserviceregisterProductDetails)

        saveLeadGenDet=0
        saveDetails(Actionproductdetails)


    }

    private fun saveDetails(Actionproductdetails: JSONArray) {
        var StartingDate= Config.convertDate(tie_DateAttended!!.text.toString())
        var totalSecurityAmount = edttotalSecurityAmount!!.text.toString()
//        var componentCharge = edtcomponentCharge!!.text.toString()
//        var totalServiceCost = edttotalServiceCost!!.text.toString()
       // var discountAmount = edtdiscountAmount!!.text.toString()
        var netAmount = edtnetAmount!!.text.toString()
        var otherCharge = edt_other_charges!!.text.toString()

        val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
        val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
        val EnterBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)

        var fk_company=FK_CompanySP.getString("FK_Company", null)
        var FK_BranchCodeUser=FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)
        var UserCode=EnterBySP.getString("UserCode", null)

        try {

            Log.e(TAG, "ID_Customerserviceregister   " + ID_Customerserviceregister)
            Log.e(TAG, "ID_CustomerserviceregisterProductDetails   " + ID_CustomerserviceregisterProductDetails)
            Log.e(TAG, "StartingDate   " + StartingDate)
            Log.e(TAG, "componentCharge   " + companantCharge)
            Log.e(TAG, "totalServiceCost   " + totalServiceCost)
            Log.e(TAG, "otherCharge   " + otherCharge)
            Log.e(TAG, "totalSecurityAmount   " + totalSecurityAmount)
            Log.e(TAG, "netAmount   " + netAmount)
            Log.e(TAG, "discountAmount   " + discountAmount)
            Log.e(TAG, "fk_company   " + fk_company)
            Log.e(TAG, "FK_BranchCodeUser   " + FK_BranchCodeUser)
            Log.e(TAG, "UserCode   " + UserCode)
            Log.e(TAG, "billtype   " + billtype)
            Log.e(TAG, "serviceDetailsArray   " + serviceDetailsArray)
            Log.e(TAG, "ProductSubDetails   " + ProductSubDetails)
            Log.e(TAG, "Actionproductdetails   " + Actionproductdetails)
            Log.e(TAG, "AttendedEmployeeDetails   " + AttendedEmployeeDetails)
            Log.e(TAG, "serviceIncentiveArray   " + serviceIncentiveArray)
            Log.e(TAG, "arrOtherChargeFinal   " + arrOtherChargeFinal)
            Log.e(TAG, "arrPaymentFinal   " + arrPaymentFinal)


            when (Config.ConnectivityUtils.isConnected(this)) {

                true -> {
                    progressDialog = ProgressDialog(context, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    serviceFollowUpSaveModel.saveFollowUp(
                        this,
                        "1",
                        ID_Customerserviceregister,
                        ID_CustomerserviceregisterProductDetails,
                        StartingDate,
                        companantCharge!!,
                        totalServiceCost!!,
                        otherCharge,
                        totalSecurityAmount,
                        netAmount,
                        discountAmount!!,
                        fk_company!!,
                        FK_BranchCodeUser!!,
                        UserCode!!,
                        billtype,
                        "",
                        "'CUSF'",
                        serviceDetailsArray,
                        servicePartsArray,
                        Actionproductdetails,
                        AttendedEmployeeDetails,
                        serviceIncentiveArray,
                        arrOtherChargeFinal,
                        arrPaymentFinal
                    )!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message


                            try {

                                if (msg!!.length > 0) {
                                    if (saveLeadGenDet == 0) {
                                        saveLeadGenDet++
                                        val jObject = JSONObject(msg)
                                        Log.e(TAG, "msg   4120   " + msg)
                                        if (jObject.getString("StatusCode") == "0") {
                                            val jobjt =
                                                jObject.getJSONObject("UpdateServiceFollowUp")
                                            try {

                                                val suceessDialog = Dialog(this)
                                                suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                suceessDialog!!.setCancelable(false)
                                                suceessDialog!!.setContentView(R.layout.success_popup_service)
                                                suceessDialog!!.window!!.attributes.gravity =
                                                    Gravity.CENTER_VERTICAL;

                                                val tv_succesmsg =
                                                    suceessDialog!!.findViewById(R.id.tv_succesmsg) as TextView

                                                val tv_succesok =
                                                    suceessDialog!!.findViewById(R.id.tv_succesok) as TextView
                                                //LeadNumber
                                                tv_succesmsg!!.setText(jobjt.getString("ResponseMessage"))
                                                tv_succesok!!.setOnClickListener {
                                                    suceessDialog!!.dismiss()
//                                                    val i = Intent(this@ServiceFollowUPActiivty, LeadActivity::class.java)
//                                                    startActivity(i)
                                                    finish()

                                                }

                                                suceessDialog!!.show()
                                                suceessDialog!!.getWindow()!!.setLayout(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                                );
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                                val builder = AlertDialog.Builder(
                                                    this@ServiceFollowUPActiivty,
                                                    R.style.MyDialogTheme
                                                )
                                                builder.setMessage(e.toString())
                                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                                    onBackPressed()
                                                }
                                                val alertDialog: AlertDialog = builder.create()
                                                alertDialog.setCancelable(false)
                                                alertDialog.show()

                                            }



                                        } else {
                                            val builder = AlertDialog.Builder(
                                                this@ServiceFollowUPActiivty,
                                                R.style.MyDialogTheme
                                            )
                                            builder.setMessage(jObject.getString("EXMessage"))
                                            builder.setPositiveButton("Ok") { dialogInterface, which ->
                                                onBackPressed()
                                            }
                                            val alertDialog: AlertDialog = builder.create()
                                            alertDialog.setCancelable(false)
                                            alertDialog.show()
                                        }
                                    }

                                } else {

                                }


                            } catch (e: Exception) {

                                Log.e(TAG, "Exception  4133    " + e.toString())
                                val builder = AlertDialog.Builder(
                                    this@ServiceFollowUPActiivty,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(e.toString())
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()

                            }

                        })
                    progressDialog!!.dismiss()
                }
                false -> {
                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception  226666    " + e.toString())
        }


    }


    private fun setRegviews() {
        rcyler_followup      = findViewById(R.id.rcyler_followup)
//        rcyler_service3      = findViewById(R.id.rcyler_service3)
        imv_filterfollowup   = findViewById(R.id.imv_filterfollowup)
        imv_addproduct   = findViewById(R.id.imv_addproduct)
        imv_scannerfollowup  = findViewById(R.id.imv_scannerfollowup)
        imv_infofollowup     = findViewById(R.id.imv_infofollowup)
        tv_followupticket    = findViewById(R.id.tv_followupticket)
        txt_next             = findViewById(R.id.txt_next)
        txt_next    = findViewById(R.id.txt_next)
        txt_previous    = findViewById(R.id.txt_previous)
        tv_main_header    = findViewById(R.id.tv_main_header)
        vw_previous    = findViewById(R.id.vw_previous)
        add_Product          = findViewById(R.id.add_Product)
        imback               = findViewById(R.id.imback)
        recyCompService      = findViewById(R.id.recyCompService)

        edtCategory          = findViewById(R.id.edtCategory)
        edtSubCategory       = findViewById(R.id.edtSubCategory)
        edtBrand             = findViewById(R.id.edtBrand)
        edtComplaint         = findViewById(R.id.edtComplaint)


        recy_main_product_topbar      = findViewById(R.id.recy_main_product_topbar)
        ll_ProdWarrantyAmc     = findViewById(R.id.ll_ProdWarrantyAmc)
        ll_partsReplaced       = findViewById(R.id.ll_partsReplaced)
        ll_action_taken        = findViewById(R.id.ll_action_taken)
        ll_infofiltradd        = findViewById(R.id.ll_infofiltradd)
        ll_service             = findViewById(R.id.ll_service)
        ll_top_bar             = findViewById(R.id.ll_top_bar)
        ll_part_repaced2             = findViewById(R.id.ll_part_repaced2)
        recy_parts_replaced    = findViewById(R.id.recy_parts_replaced)

        ll_service_Attended      = findViewById(R.id.ll_service_Attended)
        ll_service_Attended_category      = findViewById(R.id.ll_service_Attended_category)
//        ll_Service3      = findViewById(R.id.ll_Service3)

        tie_Selectbilltype = findViewById(R.id.tie_Selectbilltype) as AutoCompleteTextView
        tv_payment_type = findViewById(R.id.tv_payment_type)
        edttotalSecurityAmount = findViewById(R.id.edttotalSecurityAmount)
        edtnetAmount = findViewById(R.id.edtnetAmount)
        edtcomponentCharge = findViewById(R.id.edtcomponentCharge)
        edttotalServiceCost = findViewById(R.id.edttotalServiceCost)
        edtdiscountAmount = findViewById(R.id.edtdiscountAmount)
        tie_DateAttended = findViewById(R.id.tie_DateAttended)
        edt_other_charges = findViewById(R.id.edt_other_charges)
        rcyler_followup = findViewById(R.id.rcyler_followup)
        rcyler_actionTaken = findViewById(R.id.rcyler_actionTaken)
        rcyler_service3 = findViewById(R.id.rcyler_service3)
        imv_filterfollowup = findViewById(R.id.imv_filterfollowup)
        imv_scannerfollowup = findViewById(R.id.imv_scannerfollowup)
        imv_infofollowup = findViewById(R.id.imv_infofollowup)
        tv_followupticket = findViewById(R.id.tv_followupticket)
        txt_next = findViewById(R.id.txt_next)
        add_Product = findViewById(R.id.add_Product)
        imback = findViewById(R.id.imback)
        recyCompService = findViewById(R.id.recyCompService)
        ll_action_taken = findViewById(R.id.ll_action_taken)
        ll_service_Attended = findViewById(R.id.ll_service_Attended)
        ll_Service3 = findViewById(R.id.ll_Service3)
        imv_infofollowup!!.setOnClickListener(this)
        imv_scannerfollowup!!.setOnClickListener(this)
        imv_filterfollowup!!.setOnClickListener(this)
        imv_addproduct!!.setOnClickListener(this)
        add_Product!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        txt_next!!.setOnClickListener(this)
        txt_previous!!.setOnClickListener(this)

        edtCategory!!.setOnClickListener(this)
        edtSubCategory!!.setOnClickListener(this)
        edtBrand!!.setOnClickListener(this)
        edtComplaint!!.setOnClickListener(this)

        DecimelFormatters.setDecimelPlace(edtdiscountAmount!!)




    }

    private fun getBillType() {
//         var proddetail = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pickupdelibilltypeviewmodel.getPickupDeliBillType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (billTypecount == 0) {
                                    billTypecount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   227   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("BillType")
                                        billtypeArrayList = jobjt.getJSONArray("BillTypeList")
                                        if (billtypeArrayList.length() > 0) {
//                                             if (proddetail == 0){
//                                                 proddetail++
                                            showbilltype(billtypeArrayList)

//                                             }

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
                                progressDialog!!.dismiss()
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.e(TAG, "catch   " + e)
                        }
                        progressDialog!!.dismiss()
                    })

            }

            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun showbilltype(billtypeArrayList: JSONArray) {

        var searchType = Array<String>(billtypeArrayList.length()) { "" }
        var searchTypeID = Array<String>(billtypeArrayList.length()) { "" }
        for (i in 0 until billtypeArrayList.length()) {
            val objects: JSONObject = billtypeArrayList.getJSONObject(i)
            searchType[i] = objects.getString("BTName");
            searchTypeID[i] = objects.getString("ID_BillType");
            var FK_BillType = objects.getString("ID_BillType")

            Log.e(TAG, "00000111   " + FK_BillType)
            Log.e(TAG, "85456214   " + searchType)

            if (pickupdeliStatusCount == 0) {
                pickupdeliStatusCount++

                val adapter =
                    ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, searchType)
                tie_Selectbilltype!!.setAdapter(adapter)
                tie_Selectbilltype!!.showDropDown()
                //   tie_Selectstatus!!.setText(searchType.get(0), false)
//            tie_Selectstatus!!.setOnClickListener {
                pickupdeliStatusCount = 0
                tie_Selectbilltype!!.showDropDown()
                Log.e(TAG, "7778889999   " + pickupdeliStatusCount)
//            }
                tie_Selectbilltype!!.setOnItemClickListener { parent, view, position, id ->
                    billtype = searchTypeID[position]
                }
            }
        }
    }


    private fun loadInfo(
        customerServiceRegister: String,
        ID_CustomerserviceregisterProductDetails: String
    ) {
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val FK_BranchSP   = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val ID_Employee   = FK_EmployeeSP.getString("FK_Employee",null)
        val ID_Branch     = FK_BranchSP.getString("FK_Branch",null)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpInfoViewModel.getServiceFollowUpInfo(
                    this, customerServiceRegister,ID_CustomerserviceregisterProductDetails,
                    ID_Branch!!,
                    ID_Employee!!
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                Log.v("fsfsfds", "msg")
                                if (serviceFollowUpInfo == 0) {
                                    serviceFollowUpInfo++
                                    Log.e(TAG, "det")
                                    val jObject = JSONObject(msg)
                                    Log.e("fsfsfds   95771    ","   : " +jObject)
                                    Log.e(TAG, "st code " + jObject.getString("StatusCode"))
                                    if (jObject.getString("StatusCode") == "0") {
                                        serviceFollowUpInfoObjectList = jObject.getJSONObject("EmployeeWiseTicketSelect")

                                        FK_Product = serviceFollowUpInfoObjectList.getString("FK_Product")
                                        NameCriteria = serviceFollowUpInfoObjectList.getString("CusNumber")
                                        tv_followupticket!!.setText(serviceFollowUpInfoObjectList.getString("Ticket"))
                                        TicketRegDate = serviceFollowUpInfoObjectList.getString("RegisterdOn")

//                                        Log.e(TAG, "leadShow")

//                                        if (PSValue.equals("1")){
//
//                                        }

                                            serviceFollowUpServiceType = 0
                                            getServiceDetails(FK_Product!!,NameCriteria!!)


                                    } else {

                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->

                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }

                                }


                            } else {
                                //  swipeRefreshLayout.isRefreshing = false
                            }
                        } catch (e: Exception) {

                            Log.v("fsfsfds", "ex3 " + e)
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun openAlertDialogForMoreInfo(jobjt: JSONObject) {


        var dialogView = Dialog(this)
        dialogView!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogView!!.setContentView(R.layout.alert_more_info)
        dialogView!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;


        val window: Window? = dialogView.getWindow()
        window!!.setBackgroundDrawableResource(android.R.color.transparent);
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        var tv_cancel: TextView = dialogView.findViewById<TextView>(R.id.tv_cancel)
        var tv_ticket: TextView = dialogView.findViewById<TextView>(R.id.tv_ticket)
        var tv_prod_regOn: TextView = dialogView.findViewById<TextView>(R.id.tv_prod_regOn)
        var tv_visit_on: TextView = dialogView.findViewById<TextView>(R.id.tv_visit_on)
        var tv_CustomerName: TextView = dialogView.findViewById<TextView>(R.id.tv_CustomerName)
        var tv_phone: TextView = dialogView.findViewById<TextView>(R.id.tv_phone)
        var tv_location: TextView = dialogView.findViewById<TextView>(R.id.tv_location)
        var tv_address: TextView = dialogView.findViewById<TextView>(R.id.tv_address)
        var tv_product_name: TextView = dialogView.findViewById<TextView>(R.id.tv_product_name)
        var tv_category: TextView = dialogView.findViewById<TextView>(R.id.tv_category)
        var tv_service: TextView = dialogView.findViewById<TextView>(R.id.tv_service)
        var tv_description: TextView = dialogView.findViewById<TextView>(R.id.tv_description)
        //   tv_description.setMovementMethod(ScrollingMovementMethod())
        tv_cancel.setOnClickListener(View.OnClickListener {
            dialogView.dismiss()
        })
        tv_ticket.text = jobjt.getString("Ticket")
        tv_prod_regOn.text = jobjt.getString("RegisterdOn")
        tv_visit_on.text = jobjt.getString("VisitOn")
        tv_CustomerName.text = jobjt.getString("CustomerName")
        tv_phone.text = jobjt.getString("Mobile")
        tv_address.text =
            jobjt.getString("Address1") + "," + jobjt.getString("Address2") + "," + jobjt.getString(
                "Address3"
            )
        tv_product_name.text = jobjt.getString("ProductName")
        tv_category.text = jobjt.getString("Category")
        tv_service.text = jobjt.getString("Service")
        tv_description.text = jobjt.getString("Description")


        // alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogView.show()
    }



    private fun getServiceDetails(FK_Product: String, NameCriteria: String) {
        var SubMode = "2"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                servicedetailsViewModel.getServiceDetails(
                    this, FK_Product, NameCriteria,SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceFollowUpServiceType == 0) {
                                    serviceFollowUpServiceType++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceDetails")
                                        Log.e("fsfsfds   95772    ","   : " +jObject)
                                        jsonArrayServiceType =
                                            jobjt.getJSONArray("ServiceAttendedList")

                                        Log.e(TAG,"68222  "+jsonArrayServiceType)

//                                       db_helper!!.insertServiceDetails(jsonArrayServiceType)

//                                        jsonsubproductArrayServiceType = jsonArrayServiceType!!.getJSONObject(0).getJSONArray("ServiceAttendedListDet")
////                                        jsonsubproductArrayServiceType = jobjt.getJSONArray("ServiceAttendedListDet")
////                                        BindProduct = jsonArrayServiceType!!.getJSONObject(0).getString("BindProduct")
//                                        Log.e(TAG, "vvvvvvvvv  1" +jsonArrayServiceType)
//                                        Log.e(TAG, "vvvvvvvvv  2  " +jsonsubproductArrayServiceType.length())
//                                        Log.e(TAG, "vvvvvvvvv  3" +jsonArrayServiceType)
//
//                                        if (jsonArrayServiceType.length() > 0) {
//
//                                            for (i in 0 until jsonArrayServiceType.length()) {
//                                                var jsonObject = jsonArrayServiceType.getJSONObject(i)

                                        for (i in 0 until jsonArrayServiceType.length()) {
                                            var jsonObject = jsonArrayServiceType.getJSONObject(i)
                                            Log.e(TAG," 388...1  "+jsonObject.getString("Product"))

                                            Log.e(TAG,"modelServicesListDetails Add   110881    ")

                                            modelServicesListDetails!!.add(ServiceDetailsFullListModel("0",jsonObject.getString("FK_Category"),jsonObject.getString("MasterProduct"),
                                                jsonObject.getString("FK_Product"),jsonObject.getString("Product"),"-2",
                                                jsonObject.getString("BindProduct"),jsonObject.getString("ComplaintProduct"),jsonObject.getString("Warranty"),
                                                jsonObject.getString("ServiceWarrantyExpireDate"),jsonObject.getString("ReplacementWarrantyExpireDate"),
                                                jsonObject.getString("ID_CustomerWiseProductDetails"),jsonObject.getString("ServiceWarrantyExpired"),
                                                jsonObject.getString("ReplacementWarrantyExpired"),"0","","",false,"0",
                                                ID_Category,edtCategory!!.text.toString(),ID_SubCategory!!,edtSubCategory!!.text.toString(),ID_Brand!!,edtBrand!!.text.toString(),ID_Complaint!!,edtComplaint!!.text.toString()))

                                            var ServiceAttendedListDet = jsonObject.getJSONArray("ServiceAttendedListDet")

                                            for (j in 0 until ServiceAttendedListDet.length()) {
                                                var jsonObjectSub = ServiceAttendedListDet.getJSONObject(j)
                                                Log.e(TAG," 388...2  "+jsonObjectSub.getString("Product"))
                                                Log.e(TAG,"modelServicesListDetails Add   110882    ")
                                                modelServicesListDetails!!.add(ServiceDetailsFullListModel("1",jsonObjectSub.getString("FK_Category"),jsonObjectSub.getString("MasterProduct"),
                                                    jsonObjectSub.getString("FK_Product"),jsonObjectSub.getString("Product"),jsonObjectSub.getString("SLNo"),
                                                    jsonObjectSub.getString("BindProduct"),jsonObjectSub.getString("ComplaintProduct"),jsonObjectSub.getString("Warranty"),
                                                    jsonObjectSub.getString("ServiceWarrantyExpireDate"),jsonObjectSub.getString("ReplacementWarrantyExpireDate"),
                                                    jsonObjectSub.getString("ID_CustomerWiseProductDetails"),jsonObjectSub.getString("ServiceWarrantyExpired"),
                                                    jsonObjectSub.getString("ReplacementWarrantyExpired"),"0","","",false,"0",
                                                    ID_Category,edtCategory!!.text.toString(),ID_SubCategory!!,edtSubCategory!!.text.toString(),ID_Brand!!,edtBrand!!.text.toString(),ID_Complaint!!,edtComplaint!!.text.toString()))


//                                                jObject.put("FK_Category",jsonObjectSub.getString("FK_Category"))
//                                                jObject.put("MasterProduct",jsonObjectSub.getString("MasterProduct"))
//                                                jObject.put("FK_Product",jsonObjectSub.getString("FK_Product"))
//                                                jObject.put("Product",jsonObjectSub.getString("Product"))
//                                                jObject.put("SLNo",jsonObjectSub.getString("SLNo"))
//                                                jObject.put("BindProduct",jsonObjectSub.getString("BindProduct"))
//                                                jObject.put("ComplaintProduct",jsonObjectSub.getString("ComplaintProduct"))
//                                                jObject.put("Warranty",jsonObjectSub.getString("Warranty"))
//                                                jObject.put("ServiceWarrantyExpireDate",jsonObjectSub.getString("ServiceWarrantyExpireDate"))
//                                                jObject.put("ReplacementWarrantyExpireDate",jsonObjectSub.getString("ReplacementWarrantyExpireDate"))
//                                                jObject.put("ID_CustomerWiseProductDetails",jsonObjectSub.getString("ID_CustomerWiseProductDetails"))
//                                                jObject.put("ServiceWarrantyExpired",jsonObjectSub.getString("ServiceWarrantyExpired"))
//                                                jObject.put("ReplacementWarrantyExpired",jsonObjectSub.getString("ReplacementWarrantyExpired"))
//
////                                                var FK_Category_sub = jsonObjectSub.getString("FK_Category")
////                                                var MasterProduct_sub = jsonObjectSub.getString("MasterProduct")
////                                                var ID_Product = jsonObjectSub.getString("FK_Product")
////                                                var FK_Product_sub = jsonObjectSub.getString("Product")
////                                                var SLNo_sub = jsonObjectSub.getString("SLNo")
////                                                var BindProduct_sub = jsonObjectSub.getString("BindProduct")
////                                                var ComplaintProduct_sub = jsonObjectSub.getString("ComplaintProduct")
////                                                var Warranty_sub = jsonObjectSub.getString("Warranty")
////                                                var ServiceWarrantyExpireDate_sub = jsonObjectSub.getString("ServiceWarrantyExpireDate")
////                                                var ReplacementWarrantyExpireDate_sub = jsonObjectSub.getString("ReplacementWarrantyExpireDate")
////                                                var ID_CustomerWiseProductDetails_sub = jsonObjectSub.getString("ID_CustomerWiseProductDetails")
////                                                var ServiceWarrantyExpired_sub = jsonObjectSub.getString("ServiceWarrantyExpired")
////                                                var ReplacementWarrantyExpired_sub = jsonObjectSub.getString("ReplacementWarrantyExpired")
////                                                var writeSub =
////                                                    dbWrite.execSQL("INSERT INTO servicedetailsublist (FK_Product_parent,FK_Category,MasterProduct,FK_Product,FK_Product_sub,SLNo,BindProduct,ComplaintProduct,Warranty,ServiceWarrantyExpireDate,ReplacementWarrantyExpireDate,ID_CustomerWiseProductDetails,ServiceWarrantyExpired,ReplacementWarrantyExpired) VALUES ('$FK_Product','$FK_Category_sub','$MasterProduct_sub','$ID_Product','$FK_Product_sub','$SLNo_sub','$BindProduct_sub','$ComplaintProduct_sub','$Warranty_sub','$ServiceWarrantyExpireDate_sub','$ReplacementWarrantyExpireDate_sub','$ID_CustomerWiseProductDetails_sub','$ServiceWarrantyExpired_sub','$ReplacementWarrantyExpired_sub')")
////                                                Log.e(TAG, "writeSub  " + writeSub)
//                                                jsonArrayServicelist.put(jObject)
//
//                                                Log.e(TAG, "jsonArrayServicelist  " + jsonArrayServicelist)
                                            }
                                        }

                                        for (j in 0 until modelServicesListDetails.size) {
                                            Log.e(TAG,"447..1   "+""+j+"  "+modelServicesListDetails.get(j).Product)
                                        }



////                                                jsonsubproductArrayServiceType = jsonArrayServiceType!!.getJSONObject(i).getJSONArray("ServiceAttendedListDet")
////
////                                                Log.e(TAG, "vvvvvvvvvv  1" +jsonsubproductArrayServiceType)
////                                                val jObject = JSONObject()
//
////                                                modelServicesListDetails!!.add(ServiceDetailsFullListModel((0),jsonObject.getString("FK_Product"),jsonObject.getString("Product"),jsonObject.getString("BindProduct")))
////                                                modelServicesSubListDetails!!.add(ServiceDetailsSubListModel(jsonObject.getString("FK_Category"),jsonObject.getString("Product"),"FK_Product"))
//
////                                                Log.e(TAG, "ddddddddd  2")
                                                val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                                                rcyler_followup!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                servDetadapter = ServiceDetailsAdapter(this@ServiceFollowUPActiivty, modelServicesListDetails)
                                                rcyler_followup!!.adapter = servDetadapter
                                                servDetadapter!!.setClickListener(this@ServiceFollowUPActiivty)


//
//
////                                                val gson = Gson()
////                                                val myList: List<ServiceDetailsFullListModel> = gson.fromJson(jsonArrayServiceType, List::class.java)
//                                            }
//                                        }

                                    }else{
                                        Log.e(TAG,"1000fsdfds  ")
//                                        val builder = AlertDialog.Builder(
//                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme)
//                                        builder.setMessage(jObject.getString("EXMessage"))
//                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                        }
//                                        val alertDialog: AlertDialog = builder.create()
//                                        alertDialog.setCancelable(false)
//                                        alertDialog.show()
                                    }
                                }
                            } else {
//                                Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Log.e(TAG,"392002 +"+e)
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }

            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun loadNetAmount()
    {

        var totalsecurityamount=0.0
        var componentCharge=0.0
        var totalSetviceCost=0.0
        var otherCharge=0.0
        var discount=0.0

        try {
            totalsecurityamount=edttotalSecurityAmount!!.text.toString().toDouble()
        } catch (e: Exception) {
            totalsecurityamount=0.0
        }

        try {
            componentCharge=edtcomponentCharge!!.text.toString().toDouble()
        } catch (e: Exception) {
            componentCharge=0.0
            Log.e(TAG,"12551")
        }

        try {
            totalSetviceCost=edttotalServiceCost!!.text.toString().toDouble()
        } catch (e: Exception) {
            totalSetviceCost=0.0
        }

        try {
            otherCharge=edt_other_charges!!.text.toString().toDouble()
        } catch (e: Exception) {
            otherCharge=0.0
        }

        try {
            discount=edtdiscountAmount!!.text.toString().toDouble()
        } catch (e: Exception) {
            discount=0.0
        }

        netAmount =(totalsecurityamount+componentCharge+totalSetviceCost+otherCharge)-discount

        edtnetAmount!!.setText(DecimelFormatters.set2DecimelPlace(netAmount.toFloat()))
    }

    private fun loadAttendance() {
        val FK_BranchCodeUserSP = this.getSharedPreferences(Config.SHARED_PREF40, 0)
        val FK_EmployeeSP = this.getSharedPreferences(Config.SHARED_PREF1, 0)
        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
        var ID_Branch = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null).toString()
        var ID_Employee = FK_EmployeeSP.getString("FK_Employee", null).toString()
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpAttendanceListViewModel.getServiceFollowUpAttendance(
                    this,
                    ID_Customerserviceregister,
                    ID_Branch,
                    ID_Employee
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceFollowUpAttendance == 0) {
                                    serviceFollowUpAttendance++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        serviceFollowUpAttendanceArrayList = JSONArray()
                                        modelFollowUpAttendance.clear()
                                        val jobjt = jObject.getJSONObject("Attendancedetails")
                                        serviceFollowUpAttendanceArrayList = jobjt.getJSONArray("AttendancedetailsList")
                                        Log.e(TAG,"1337  "+serviceFollowUpAttendanceArrayList)
                                        //  setAttendancetRecycler(serviceFollowUpAttendanceArrayList)

                                        if (serviceFollowUpAttendanceArrayList.length()>0){

                                            for (i in 0 until serviceFollowUpAttendanceArrayList.length()) {
                                                var jsonObject = serviceFollowUpAttendanceArrayList.getJSONObject(i)
                                                modelFollowUpAttendance!!.add(ModelFollowUpAttendance("1",jsonObject.getString("ID_Employee"),
                                                    jsonObject.getString("EmployeeName"),jsonObject.getString("ID_CSAEmployeeType"),jsonObject.getString("Attend"),
                                                    jsonObject.getString("DepartmentID"),jsonObject.getString("Department"),jsonObject.getString("Role"),
                                                    jsonObject.getString("Designation")))
                                            }


                                        }

                                        if (modelFollowUpAttendance.size>0){

                                            attendancePopUp(modelFollowUpAttendance)



                                        }



                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->

                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }

                                }


                            } else {
                                //swipeRefreshLayout.isRefreshing = false
                            }
                        } catch (e: Exception) {
//                            swipeRefreshLayout.visibility = View.GONE
//                            swipeRefreshLayout.isRefreshing = false
//                            tv_nodata.visibility = View.VISIBLE
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun loadActionTaken() {

        Log.e(TAG,"12552")
        edttotalServiceCost!!.setText(Config.changeTwoDecimel(totalServiceCost!!))
        edttotalSecurityAmount!!.setText((Config.changeTwoDecimel("0.00")))
        edtcomponentCharge!!.setText(Config.changeTwoDecimel(companantCharge!!))
        edtdiscountAmount!!.setText(Config.changeTwoDecimel(discountAmount!!))
       // edt_other_charges!!.setText("0.00")
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_DateAttended!!.setText(currentDate)
        loadNetAmount()

        Log.v("sdfsdfds", "modelServicesListDetails " + modelServicesListDetails.toString())
        actionTakenSelected.clear()


        if (PSValue.equals("1")){
            for (i in 0 until modelServicesListDetails.size) {
                var empModel = modelServicesListDetails[i]
                if (empModel.isChekecd) {
                    actionTakenSelected!!.add(
                        ActionTakenMainModel(
                            empModel.FK_Product, empModel.Product, "", "",
                            "", "", false, "0.00", "", "0.00", "", empModel.ID_CustomerWiseProductDetails,"",
                            "","","","","","","",
                            ID_Category,edtCategory!!.text.toString(),ID_SubCategory!!,ID_Brand!!,ID_Complaint!!)
                    )
                }

            }
        }else{


            actionTakenSelected!!.add(
                ActionTakenMainModel(
                    "", "", "", "",
                    "", "", false, "0.00", "", "0.00", "", "","",
                    "","","","","","","",
                    ID_Category,edtCategory!!.text.toString(),ID_SubCategory!!,ID_Brand!!,ID_Complaint!!)
            )

        }




        Log.v("sfsdfdfd", "act size  " + actionTakenSelected.size)
        val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
        rcyler_actionTaken!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//        actionTakenAdapter = ActionTakenAdapter(this@ServiceFollowUPActiivty, actionTakenSelected)
        actionTakenAdapter = ActionTakenAdapter(
            this@ServiceFollowUPActiivty,
            actionTakenSelected,
            object : ActionTakenAdapter.TextChangedListener {
                override fun onTextChanged(position: Int, field: String, newText: String) {
                    Log.v("sfsdfdfd", "position  " + position)
                    Log.v("sfsdfdfd", "field  " + field)
                    Log.v("sfsdfdfd", "newText  " + newText)
                    if (field.equals("customer_note")) {
                        var empModel = actionTakenSelected[position]
                        empModel.Customer_note = newText
                        // actionTakenAdapter!!.notifyItemChanged(position)
                    } else if (field.equals("employee_note")) {
                        var empModel = actionTakenSelected[position]
                        empModel.Employee_note = newText
                        // actionTakenAdapter!!.notifyItemChanged(position)
                    } else if (field.equals("security_amount")) {
                        var empModel = actionTakenSelected[position]
//                        empModel.securityAmount = newText
//                        loadSecurityAmount()
                        // actionTakenAdapter!!.notifyItemChanged(position)
                    } else if (field.equals("buy_back")) {
                        var empModel = actionTakenSelected[position]
                        empModel.buyBackAmount = newText
                        //actionTakenAdapter!!.notifyItemChanged(position)
                    }


                }
            })
        rcyler_actionTaken!!.adapter = actionTakenAdapter
        actionTakenAdapter!!.setClickListener(this@ServiceFollowUPActiivty)

        tie_DateAttended!!.setOnClickListener(View.OnClickListener {
            DateType = 0
            openBottomSheet()
        })
        edt_other_charges!!.setOnClickListener(View.OnClickListener {
          //  edt_other_charges!!.setText("0.00")
            DateType = 0
            actionTakenActioncouny = 0
            getOtherCharges()
        })
        tie_Selectbilltype!!.setOnClickListener(View.OnClickListener {
            billTypecount = 0
            getBillType()
        })

        tv_payment_type!!.setOnClickListener(View.OnClickListener {
            if (billtype.equals("")){
                Config.showCustomToast("Select BillType",context)
            }else{
                paymentMethodPopup()
            }

        })


        edtdiscountAmount!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                var strNetAmount = edtnetAmount!!.text.toString()
                var discAmount = edtdiscountAmount!!.text.toString()

                if (discAmount.equals("") || discAmount.equals(".")){
                    discAmount =" 0.00"
                }

                if (strNetAmount.equals("") || strNetAmount.equals(".")){
                    strNetAmount =" 0.00"
                }
                if (discAmount.toFloat() <= strNetAmount.toFloat()){
                    loadNetAmount()
                }else{
                    Config.showCustomToast("Discount Amount should be less than or equals to Net Amount",context)
//                    Toast.makeText(applicationContext,"Discount Amount should be less than or equals to Net Amount",Toast.LENGTH_SHORT).show()
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })
        edttotalServiceCost!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                loadNetAmount()
            }

            override fun afterTextChanged(s: Editable?) {}
        })


    }

    private fun loadSecurityAmount() {
        var securityAmount = 0.0
        for (obj in actionTakenSelected) {
            var amount = 0.0
            try {
                amount = obj.securityAmount.toDouble()

            } catch (e: Exception) {
                amount=0.0
            }
            securityAmount = securityAmount + amount
        }
        DecimelFormatters.set2DecimelPlace(securityAmount.toFloat())
        edttotalSecurityAmount!!.setText(DecimelFormatters.set2DecimelPlace(securityAmount.toFloat()))
        loadNetAmount()
    }

    private fun getPaymentList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                paymentMethodeViewModel.getPaymentMethod(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (paymentCount == 0) {
                                    paymentCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpPaymentMethod")
                                        paymentMethodeArrayList =
                                            jobjt.getJSONArray("FollowUpPaymentMethodList")
                                        if (paymentMethodeArrayList.length() > 0) {

                                            payMethodPopup(paymentMethodeArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }

            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun payMethodPopup(paymentMethodeArrayList: JSONArray) {
        try {

            dialogPaymentMethod = Dialog(this)
            dialogPaymentMethod!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPaymentMethod!!.setContentView(R.layout.payment_method_popup)
            dialogPaymentMethod!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyPaymentMethod =
                dialogPaymentMethod!!.findViewById(R.id.recyPaymentMethod) as RecyclerView
            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyPaymentMethod!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = PayMethodAdapter(this@ServiceFollowUPActiivty, paymentMethodeArrayList)
            recyPaymentMethod!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)
            dialogPaymentMethod!!.show()
            dialogPaymentMethod!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun paymentMethodPopup() {
        try {

            dialogPaymentSheet = Dialog(this)
            dialogPaymentSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPaymentSheet!!.setContentView(R.layout.emi_payment_bottom_sheet)
            dialogPaymentSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogPaymentSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            //  dialogPaymentSheet!!.setCancelable(false)

            edtPayMethod = dialogPaymentSheet!!.findViewById(R.id.edtPayMethod) as EditText
            edtPayRefNo = dialogPaymentSheet!!.findViewById(R.id.edtPayRefNo) as EditText
            edtPayAmount = dialogPaymentSheet!!.findViewById(R.id.edtPayAmount) as EditText

            txtPayBalAmount = dialogPaymentSheet!!.findViewById(R.id.txtPayBalAmount) as TextView

            txt_pay_method = dialogPaymentSheet!!.findViewById(R.id.txt_pay_method) as TextView
            txt_pay_Amount = dialogPaymentSheet!!.findViewById(R.id.txt_pay_Amount) as TextView
            txt_bal_Amount = dialogPaymentSheet!!.findViewById(R.id.txt_bal_Amount) as TextView

            edtPayMethod!!.addTextChangedListener(watcher)
            edtPayAmount!!.addTextChangedListener(watcher)
            txtPayBalAmount!!.addTextChangedListener(watcher)

//            edtPayMethod = dialogPaymentSheet!! .findViewById(R.id.edtPayMethod) as EditText
//            edtPayRefNo = dialogPaymentSheet!! .findViewById(R.id.edtPayRefNo) as EditText
//            edtPayAmount = dialogPaymentSheet!! .findViewById(R.id.edtPayAmount) as EditText


            DecimelFormatters.setDecimelPlace(edtPayAmount!!)


//            txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
//            txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))


            img_PayAdd = dialogPaymentSheet!!.findViewById(R.id.img_PayAdd) as ImageView
            img_PayRefresh = dialogPaymentSheet!!.findViewById(R.id.img_PayRefresh) as ImageView
            btnApply = dialogPaymentSheet!!.findViewById(R.id.btnApply) as Button

            ll_paymentlist = dialogPaymentSheet!!.findViewById(R.id.ll_paymentlist) as LinearLayout
            recyPaymentList =
                dialogPaymentSheet!!.findViewById(R.id.recyPaymentList) as RecyclerView


             txtPayBalAmount!!.setText(""+edtnetAmount!!.text.toString())

            if (arrPayment.length() > 0) {
                ll_paymentlist!!.visibility = View.VISIBLE
                viewList(arrPayment)
                var payAmnt = 0.00
                for (i in 0 until arrPayment.length()) {
                    //apply your logic
                    val jsonObject = arrPayment.getJSONObject(i)
                    Log.e(TAG, "9451  ")
                    payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                }
                Log.e(TAG, "9452  ")
                val pay = DecimelFormatters.set2DecimelPlace(payAmnt.toFloat())
                //  payAmnt = (DecimelFormatters.set2DecimelPlace(payAmnt.toFloat()))
                Log.e(TAG, "9453  ")
                Log.e(TAG, "payAmnt         475    " + payAmnt)
                //  txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - pay.toFloat()))
                 txtPayBalAmount!!.setText(""+ DecimelFormatters.set2DecimelPlace((DecimalToWordsConverter.commaRemover(edtnetAmount!!.text.toString()).toFloat()) - pay.toFloat()))
                Log.e(TAG, "9454  ")
            } else {
                Log.e(TAG, "9455  ")
                ll_paymentlist!!.visibility = View.GONE
                recyPaymentList!!.adapter = null
                txtPayBalAmount!!.setText(""+edtnetAmount!!.text.toString())
                Log.e(TAG, "9456  ")
            }

            edtPayMethod!!.setOnClickListener {
                Config.disableClick(it)
                paymentCount = 0
                getPaymentList()

            }

            img_PayAdd!!.setOnClickListener {
                validateAddPayment(it)
            }

            img_PayRefresh!!.setOnClickListener {
                arrAddUpdate = "0"
                ID_PaymentMethod = ""
                edtPayMethod!!.setText("")
                edtPayRefNo!!.setText("")
                edtPayAmount!!.setText("")

                if (arrPayment.length() > 0) {
                    var payAmnt = 0.00
                    for (i in 0 until arrPayment.length()) {
                        //apply your logic
                        val jsonObject = arrPayment.getJSONObject(i)
                        payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                    }
                    txtPayBalAmount!!.setText(""+ DecimelFormatters.set2DecimelPlace((edtnetAmount!!.text.toString().toFloat()) - payAmnt.toFloat()))
                } else {
                    txtPayBalAmount!!.setText(""+edtnetAmount!!.text.toString())
                }
            }

            btnApply!!.setOnClickListener {
                val payAmnt =
                    DecimelFormatters.set2DecimelPlace(txtPayBalAmount!!.text.toString().toFloat())
                strSumPayMethode = "0.00"
                arrPaymentFinal = JSONArray()
                var hasId1 =  haspaymentMandtory(actionTakenSelected!!)
                if (hasId1){
                    if ((payAmnt.toFloat()).equals("0.00".toFloat())) {
                        Log.e(TAG, "801 payAmnt  0.00  " + payAmnt.toFloat())
                        applyMode = 1
                        dialogPaymentSheet!!.dismiss()
                        for (i in 0 until arrPayment.length()) {
                            var empModel = arrPayment.getJSONObject(i)
                            val jsonObject = JSONObject()
                            jsonObject.put("PaymentMethod", empModel.getString("MethodID"))
                            jsonObject.put("PAmount", empModel.getString("Amount"))
                            jsonObject.put("Refno", empModel.getString("RefNo"))
                            arrPaymentFinal.put(jsonObject)

                            strSumPayMethode =(strSumPayMethode.toFloat()+(empModel.getString("Amount").toFloat())).toString()
                        }
                        Log.v("sdfsdfsdfds", "arr  " + arrPaymentFinal.toString())
                    } else {
                        Log.e(TAG, "801 payAmnt  0.0clhghfoij    " + payAmnt.toFloat())
                        Config.snackBarWarning(context, it, "Balance Amount should be zero")

                    }
                }else{
                    applyMode = 1
                    dialogPaymentSheet!!.dismiss()
                    for (i in 0 until arrPayment.length()) {
                        var empModel = arrPayment.getJSONObject(i)
                        val jsonObject = JSONObject()
                        jsonObject.put("PaymentMethod", empModel.getString("MethodID"))
                        jsonObject.put("PAmount", empModel.getString("Amount"))
                        jsonObject.put("Refno", empModel.getString("RefNo"))
                        arrPaymentFinal.put(jsonObject)

                        strSumPayMethode =(strSumPayMethode.toFloat()+(empModel.getString("Amount").toFloat())).toString()
                    }
                    Log.v("sdfsdfsdfds", "arr  " + arrPaymentFinal.toString())
                }

            }


            dialogPaymentSheet!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "1012 Exception  " + e.toString())
        }
    }

    var watcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //YOUR CODE
            val outputedText = s.toString()
            Log.e(TAG, "28301    " + outputedText)

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //YOUR CODE
        }

        override fun afterTextChanged(editable: Editable) {
            Log.e(TAG, "28302    ")

            when {
                editable === edtPayMethod!!.editableText -> {
                    Log.e(TAG, "283021    ")
                    if (edtPayMethod!!.text.toString().equals("")) {
//                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
                    } else {
                        //   til_DeliveryDate!!.isErrorEnabled = false
                        txt_pay_method!!.setTextColor(
                            ContextCompat.getColorStateList(
                                context,
                                R.color.black
                            )
                        )
                    }


                }

                editable === edtPayAmount!!.editableText -> {

                    try {
                        var strAmnt = ""
                        Log.e(TAG, "283021    " + edtPayAmount!!.text.toString())
                        edtPayAmount!!.removeTextChangedListener(this)
                        if (edtPayAmount!!.text.toString().equals(".")) {
                            strAmnt = "0.00"
                        } else if (edtPayAmount!!.text.toString().equals("")) {
                            strAmnt = "0.00"
                        } else {
                            strAmnt =
                                DecimalToWordsConverter.commaRemover(edtPayAmount!!.text.toString())
                        }

                        val edtAmt = DecimelFormatters.set2DecimelPlace(strAmnt!!.toFloat())
                        var payAmount = DecimalToWordsConverter.commaRemover(edtPayAmount!!.text.toString())
                        var txt = edtPayAmount!!.text.toString()
                        // Log.e(TAG,"2830213    "+  DecimalToWordsConverter.getDecimelFormateForEditText(txt))

                        //  edtPayAmount!!.setText(DecimalToWordsConverter.getDecimelFormateForEditText(txt))
                        if (!edtPayAmount!!.text.toString().equals("") && payAmount.toFloat() > 0) {
//                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))

                            edtPayAmount!!.setText(
                                DecimalToWordsConverter.getDecimelFormateForEditText(
                                    strAmnt!!
                                )
                            )
                            txt_pay_Amount!!.setTextColor(
                                ContextCompat.getColorStateList(
                                    context,
                                    R.color.black
                                )
                            )

                        } else {
                            //   til_DeliveryDate!!.isErrorEnabled = false
//                            edtPayAmount!!.setText(
//                                DecimalToWordsConverter.getDecimelFormateForEditText(
//                                    strAmnt!!
//                                )
//                            )
//                            txt_pay_Amount!!.setTextColor(
//                                ContextCompat.getColorStateList(
//                                    context,
//                                    R.color.black
//                                )
//                            )
                        }
                        edtPayAmount!!.setSelection(edtPayAmount!!.length())
                        edtPayAmount!!.addTextChangedListener(this)
                    } catch (e: Exception) {
                        Log.e(TAG, "283021 dtrg   " + e.toString())
                    }


                }

                editable === txtPayBalAmount!!.editableText -> {
                    Log.e(TAG, "283021    ")
                    val payAmnt = DecimelFormatters.set2DecimelPlace(
                        DecimalToWordsConverter.commaRemover(txtPayBalAmount!!.text.toString())
                            .toFloat()
                    )
                    if ((payAmnt.toFloat()).equals("0.00".toFloat())) {
                        Log.e(TAG, "801 payAmnt  0.00  " + payAmnt.toFloat())
                        txt_bal_Amount!!.setTextColor(
                            ContextCompat.getColorStateList(
                                context,
                                R.color.black
                            )
                        )
                    } else {
                        Log.e(TAG, "801 payAmnt  0.0clhghfoij    " + payAmnt.toFloat())
                        txt_bal_Amount!!.setTextColor(
                            ContextCompat.getColorStateList(
                                context,
                                R.color.color_mandatory
                            )
                        )

                    }

                }


            }

        }
    }

    private fun validateAddPayment(view: View) {
//        var balAmount =
//            (DecimalToWordsConverter.commaRemover(txtPayBalAmount!!.text.toString())).toFloat()
        //  var payAmount = edtPayAmount!!.text.toString()
        var balAmount = 0.0F
        if (arrAddUpdate.equals("1")){
            balAmount = (DecimalToWordsConverter.commaRemover(txtPayBalAmount!!.text.toString())).toFloat() + (DecimalToWordsConverter.commaRemover(arrPayment.getJSONObject(arrPosition!!).getString("Amount")).toFloat())
        }else{
            balAmount = (DecimalToWordsConverter.commaRemover(txtPayBalAmount!!.text.toString())).toFloat()
        }

        var payAmount = DecimalToWordsConverter.commaRemover(edtPayAmount!!.text.toString())


        Log.e(TAG, "1751   balAmount   : " + balAmount)
        Log.e(TAG, "1751   payAmount   : " + payAmount)
        var hasId = hasPayMethod(arrPayment, "MethodID", ID_PaymentMethod!!)


        if (ID_PaymentMethod.equals("")) {
            Log.e(TAG, "110   Valid   : Select Payment Method")
            edtPayMethod!!.setError("Select Payment Method", null)
            txt_pay_method!!.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.color_mandatory
                )
            )
            Config.snackBarWarning(context, view, "Select Payment Method")
        } else if (hasId == false && arrAddUpdate.equals("0")) {
            txt_pay_method!!.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.color_mandatory
                )
            )
            Config.snackBarWarning(context, view, "PaymentMethod Already exits")
        } else if (payAmount.equals("")) {
//            else if (edtPayAmount!!.text.toString().equals("")){
            txt_pay_Amount!!.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.color_mandatory
                )
            )
            Log.e(TAG, "110   Valid   : Enter Amount")
            Config.snackBarWarning(context, view, "Enter Amount")

        }else if (payAmount.toFloat() <= 0) {
//            else if (edtPayAmount!!.text.toString().equals("")){
            txt_pay_Amount!!.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.color_mandatory
                )
            )
            Log.e(TAG, "110   Valid   : Enter Amount")
            Config.snackBarWarning(context, view, "Amount Should be greater than 0")

        } else if (balAmount < payAmount.toFloat()) {
            Log.e(TAG, "110   Valid   : Enter Amount DD")
            txt_pay_Amount!!.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.color_mandatory
                )
            )
            Config.snackBarWarning(
                context,
                view,
                "Amount should be less than or equal to Bal. Amount"
            )
        } else {

            if (arrAddUpdate.equals("0")) {

                val payAmnt = DecimelFormatters.set2DecimelPlace(balAmount - payAmount.toFloat())
                // txtPayBalAmount!!.text = (balAmount- payAmount.toFloat()).toString()
                txtPayBalAmount!!.text = payAmnt
                val jObject = JSONObject()
                jObject.put("MethodID", ID_PaymentMethod)
                jObject.put("Method", edtPayMethod!!.text.toString())
                jObject.put("RefNo", edtPayRefNo!!.text.toString())
                // jObject.put("Amount",DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))
                jObject.put("Amount", DecimelFormatters.set2DecimelPlace((payAmount).toFloat()))
                arrPayment!!.put(jObject)
            }
            if (arrAddUpdate.equals("1")) {

                val payAmnt = DecimelFormatters.set2DecimelPlace(balAmount - payAmount.toFloat())
                // txtPayBalAmount!!.text = (balAmount- payAmount.toFloat()).toString()
                txtPayBalAmount!!.text = payAmnt

                val jsonObject = arrPayment.getJSONObject(arrPosition!!)
                jsonObject.put("MethodID", ID_PaymentMethod)
                jsonObject.put("Method", edtPayMethod!!.text.toString())
                jsonObject.put("RefNo", edtPayRefNo!!.text.toString())
                //   jsonObject.put("Amount",DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))
                jsonObject.put("Amount", DecimelFormatters.set2DecimelPlace((payAmount).toFloat()))

                arrAddUpdate = "0"


            }

            applyMode = 0
            ID_PaymentMethod = ""
            edtPayMethod!!.setText("")
            edtPayRefNo!!.setText("")
            edtPayAmount!!.setText("")

            if (arrPayment.length() > 0) {
                ll_paymentlist!!.visibility = View.VISIBLE
                viewList(arrPayment)
            } else {
                ll_paymentlist!!.visibility = View.GONE
                recyPaymentList!!.adapter = null
            }


        }

        Log.e(TAG, "110  arrPayment  :  " + arrPayment)
    }

    fun hasPayMethod(json: JSONArray, key: String, value: String): Boolean {
        for (i in 0 until json.length()) {  // iterate through the JsonArray
            // first I get the 'i' JsonElement as a JsonObject, then I get the key as a string and I compare it with the value
            if (json.getJSONObject(i).getString(key) == value) return false
        }
        return true
    }

    private fun viewList(arrPayment: JSONArray) {

        val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
        recyPaymentList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
        adapterPaymentList = PaymentListAdapter(this@ServiceFollowUPActiivty, arrPayment)
        recyPaymentList!!.adapter = adapterPaymentList
        adapterPaymentList!!.setClickListener(this@ServiceFollowUPActiivty)
    }

    private fun loadOtherCharge() {
        TODO("Not yet implemented")
    }

    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker = view.findViewById<DatePicker>(R.id.date_Picker1)

        if (DateType == 0) {
          //  date_Picker.maxDate = System.currentTimeMillis()
        }



        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker!!.getDayOfMonth()
                val mon: Int = date_Picker!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }

                Log.e(TAG,"2132221   TicketRegDate    "+TicketRegDate)
                if (DateType == 0) {
                    var Datess = "" + strDay + "-" + strMonth + "-" + strYear
                    var isValid = Config.convertTimemills(TicketRegDate,Datess)
                    if (isValid){
                        tie_DateAttended!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    }else{
                        Log.e(TAG,"2132222   TicketRegDate    "+TicketRegDate+"  :  "+Datess+"  :  ")
                        tie_DateAttended!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                       // Config.snackBars(context,it,"Attended Date Should be greater than or equal to Ticket register Date")
                        Config.showCustomToast("Attended Date Should be greater than or equal to Ticket register Date",context)
                    }


                }
                if (DateType == 1) {
                    var empModel = actionTakenSelected[modEditPosition]
                    empModel.FollowupDate = strDay + "-" + strMonth + "-" + strYear
                    actionTakenAdapter!!.notifyItemChanged(modEditPosition)

                }


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun getProductWiseComplaint(FK_Category: String, FK_Product: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productwisecomplaintViewModel.getProductWiseComplaint(
                    this,FK_Product,FK_Category
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (productwisecomplaintcouny == 0) {
                                    productwisecomplaintcouny++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "dssadd  12154   "+jObject )
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ComplaintListDetails")
                                        complaintfollowup = jobjt.getJSONArray("ComplaintListDetailsList")
                                        Log.e(TAG, "dssadd  1   "+msg )
                                        if (complaintfollowup.length() > 0) {

                                            if (modChanged == 1){
                                                productWisePopup()
                                            }else{
                                                val jsonObject = complaintfollowup.getJSONObject(0)
                                                Log.e(TAG,"95444     "+jsonObject)

                                                var empModel = modelServicesListDetails[modEditPosition]

                                                empModel.ID_ComplaintList = jsonObject.getString("ID_ComplaintList")
                                                empModel.ComplaintName = jsonObject.getString("ComplaintName")
                                                Log.e(TAG,"954441     "+ empModel.ComplaintName)

                                                servDetadapter!!.notifyItemChanged(modEditPosition)
                                            }



//                                            Log.e(TAG, "dssadd  2" )
//                                            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
//                                            recyCompService!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapter = complaint_service_followup(this@ServiceFollowUPActiivty, complaintfollowup!!)
//                                            recyCompService!!.adapter = adapter
////                                            adapter.setClickListener(this@ServiceFollowUPActiivty)
                                        }

                                    }else{
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme)
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }
                            } else {
//                                Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    private fun getActionTakenAction(FK_Category: String, FK_Product: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                actionTakenViewModel.getActionTakenAction(
                    this, FK_Product, FK_Category
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (actionTakenActioncouny == 0) {
                                    actionTakenActioncouny++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        actionTypeActionList =
                                            jobjt.getJSONArray("FollowUpActionDetailsList")
                                        Log.e(TAG, "dssadd  1" + msg)
                                        if (actionTypeActionList.length() > 0) {

                                            actionTakenActionPopUp()

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }
                            } else {
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }

            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getLeadAction(FK_Category: String, FK_Product: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadActionViewModel.getActionTakenAction(
                    this, FK_Product, FK_Category
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (actionTakenActioncouny == 0) {
                                    actionTakenActioncouny++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        leadActionList =
                                            jobjt.getJSONArray("FollowUpActionDetailsList")
                                        Log.e(TAG, "dssadd  1" + msg)
                                        if (leadActionList.length() > 0) {

                                            LeadActionPopup()

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }
                            } else {
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }

            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getOtherCharges() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                otherChargesViewViewModel.getActionTakenAction(
                    this
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (actionTakenActioncouny == 0) {
                                    actionTakenActioncouny++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("OtherChargeDetails")
                                        otherChargeList =
                                            jobjt.getJSONArray("OtherChargeDetailsList")
                                        Log.e(TAG, "dssadd  1" + msg)
                                        if (otherChargeList.length() > 0) {

                                            otherChargesPopup()

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }
                            } else {
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }

            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun loadMoreServiceAttended() {
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val FK_BranchSP   = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val ID_Employee   = FK_EmployeeSP.getString("FK_Employee",null)
        val ID_Branch     = FK_BranchSP.getString("FK_Branch",null)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceFollowUpMoreServiceViewModel.getServiceFollowUpMoreService(
                    this, ID_Customerserviceregister, ID_Branch!!, ID_Employee!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                Log.e(TAG,"msg   505   "+msg)
                                if (serviceFollowUpMoreService == 0) {
                                    serviceFollowUpMoreService++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("AddedService")
                                        jsonArrayMoreServiceAttended = jobjt.getJSONArray("AddedServiceList")
                                        if (jsonArrayMoreServiceAttended.length()>0){

                                            Log.e(TAG,"20262  ")
                                            Log.e(TAG,"2026  s")
                                            sortMoreServiceAttended = JSONArray()
                                            selectMoreServiceAttended = JSONArray()
                                            // Log.e(TAG,"jsonArrayMappedServiceAttended  368111 "+jsonArrayMappedServiceAttended.length())
                                            modelMoreServices.clear()
                                            modelMoreServicesTemp!!.clear()
                                            for (i in 0 until jsonArrayMoreServiceAttended.length()) {

                                                Log.e(TAG, "position  3681112 " + i)
                                                var jsonObject = jsonArrayMoreServiceAttended.getJSONObject(i)
                                                val jObject = JSONObject()

                                                jObject.put("ID_Services", jsonObject.getString("ID_Services"))
                                                jObject.put("Service", jsonObject.getString("Service"))
                                                jObject.put("isChecked","0")
                                                jObject.put("isCheckedTemp","0")
                                                Log.e(TAG, "jObject  3681112 " + jObject)
                                                sortMoreServiceAttended.put(jObject)

                                                modelMoreServices!!.add(ModelMoreServices(jsonObject.getString("ID_Services"),jsonObject.getString("Service"),"0"))
                                                modelMoreServicesTemp!!.add(ModelMoreServicesTemp(jsonObject.getString("ID_Services"),jsonObject.getString("Service"),"0"))

                                            }

                                            loadMoreServiceAttendedPopup(sortMoreServiceAttended)
                                        }


                                    }
                                }
                            } else {

                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    private fun hasPartReplaced(servicepartsReplacedModel: ArrayList<ServicePartsReplacedModel>): Boolean {

        var isChecked = true
        for (i in 0 until servicepartsReplacedModel.size) {  // iterate through the JsonArray

            Log.e(TAG,"20132       "+servicepartsReplacedModel.get(i).WarrantyMode+"   R:   "+servicepartsReplacedModel.get(i).ReplceMode+"" +
                    "   Q:  "+servicepartsReplacedModel.get(i).Quantity+"  C:  "+servicepartsReplacedModel.get(i).isChecked+
                    "  IDCP:  "+servicepartsReplacedModel.get(i).ID_CustomerWiseProductDetails)
            if (servicepartsReplacedModel.get(i).isChecked.equals("1")){
                isChecked = true
                var Qty = servicepartsReplacedModel.get(i).Quantity
                if (Qty.equals("") || Qty.equals(".")){
                    Qty = "0"
                }

                Log.e(TAG,"2999   "+Qty.toFloat()+  " > 0")

                if (servicepartsReplacedModel.get(i).WarrantyMode.equals("0") || servicepartsReplacedModel.get(i).WarrantyMode.equals("")) {
                    strActiontakenStatusMessage = "Select Waranty Mode for "+servicepartsReplacedModel.get(i).MainProduct
                    isChecked = false
                    break
                }
                else if ( servicepartsReplacedModel.get(i).ReplceMode.equals("0") ||  servicepartsReplacedModel.get(i).ReplceMode.equals("")){
                    strActiontakenStatusMessage = "Select Replace Mode for "+servicepartsReplacedModel.get(i).MainProduct
                    isChecked = false
                    break
                }
                else if (Qty.toFloat() <= 0){
                    strActiontakenStatusMessage = "Enter Valid Quantity "+servicepartsReplacedModel.get(i).MainProduct
                    isChecked = false
                    break
                }

//                if (servicepartsReplacedModel.get(i).WarrantyMode.equals("0") || servicepartsReplacedModel.get(i).ReplceMode.equals("0")  ||
//                    servicepartsReplacedModel.get(i).Quantity.equals("") || servicepartsReplacedModel.get(i).Quantity.equals(".")  || Qty.toFloat() > 0){
//
//                    isChecked = false
//                    break
//                }
            }

        }

        strActiontakenStatusMessage
        return isChecked

    }




    private fun loadMoreServiceAttendedPopup(sortMoreServiceAttended : JSONArray) {

        try {

            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!! .setContentView(R.layout.cs_more_service_attended_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)

            recyFollowupAttended = dialogMoreServiceAttendeSheet!! .findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!! .findViewById(R.id.tv_cancel) as TextView
            var tv_submit = dialogMoreServiceAttendeSheet!! .findViewById(R.id.tv_submit) as TextView



            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)



            Log.e(TAG,"20263  ")
            Log.e(TAG,"20268  s")
            selectMoreServiceAttended = JSONArray()
            selectMoreServiceAttended = sortMoreServiceAttended
            Log.e(TAG,"20264   sortMoreServiceAttended   "+sortMoreServiceAttended.length())
            Log.e(TAG,"20265   selectMoreServiceAttended   "+selectMoreServiceAttended.length())

            modelMoreServicesTemp.clear()
            for (i in 0 until modelMoreServices.size) {
                val ItemsModelTemp = modelMoreServices[i]
                modelMoreServicesTemp!!.add(ModelMoreServicesTemp(ItemsModelTemp.ID_Services,ItemsModelTemp.Service,ItemsModelTemp.isChecked))
            }

            modelServiceAttendedTemp.clear()
            for (i in 0 until modelServiceAttended.size) {
                val ItemsServiceTemp = modelServiceAttended[i]
                modelServiceAttendedTemp!!.add(
                    ModelServiceAttendedTemp(ItemsServiceTemp.ID_ProductWiseServiceDetails,ItemsServiceTemp.SubProduct
                    ,ItemsServiceTemp.ID_Product,ItemsServiceTemp.ID_Services,ItemsServiceTemp.Service,ItemsServiceTemp.ServiceCost,
                    ItemsServiceTemp.ServiceTaxAmount,ItemsServiceTemp.ServiceNetAmount,ItemsServiceTemp.Remarks,ItemsServiceTemp.isChecked,
                    ItemsServiceTemp.isDelete,ItemsServiceTemp.isCheckedAdd,ItemsServiceTemp.ServiceTypeId,ItemsServiceTemp.ServiceTypeName)
                )
            }

            Log.e(TAG,"590   sortMoreServiceAttended   "+sortMoreServiceAttended)
            if (modelMoreServicesTemp.size>0){
                val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                recyFollowupAttended!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                val adapter1 = MoreServiceAttendedAdapter(this@ServiceFollowUPActiivty,modelMoreServices,modelMoreServicesTemp,modelServiceAttendedTemp)
                recyFollowupAttended!!.adapter = adapter1
//                adapter1.setClickListener(this@ServiceFollowUPActiivty)

            }

            tv_cancel!!.setOnClickListener {
                selectMoreServiceAttended = JSONArray()
                dialogMoreServiceAttendeSheet!!.dismiss()
            }
            tv_submit!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
                Log.e(TAG,"20269  s")
                Log.e(TAG,"610  selectMoreServiceAttended   "+selectMoreServiceAttended)

                addDataServiceAttended()
            }

//            recyServiceWarranty!!.setOnTouchListener(object : View.OnTouchListener {
//                override fun onTouch(v: View, event: MotionEvent?): Boolean {
//                    v.parent.requestDisallowInterceptTouchEvent(true)
//                    v.onTouchEvent(event)
//                    return true
//                }
//            })



            dialogMoreServiceAttendeSheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addDataServiceAttended() {
        // modelServiceAttended
        modelMoreServices.clear()
        for (i in 0 until modelMoreServicesTemp.size) {
            val ItemsModel = modelMoreServicesTemp[i]
            Log.e(TAG,"isChecked  76401  "+ItemsModel.isChecked)
            modelMoreServices!!.add(ModelMoreServices(ItemsModel.ID_Services,ItemsModel.Service,ItemsModel.isChecked))
        }

        // 02-05-2023
        modelServiceAttended.clear()

        for (i in 0 until modelServiceAttendedTemp.size) {
            val ItemsAttend3 = modelServiceAttendedTemp[i]
            Log.e(TAG,"77222  : "+ItemsAttend3.ID_Services+"  :  "+ItemsAttend3.Service+"  :  "+ItemsAttend3.isChecked)
            if (ItemsAttend3.isCheckedAdd.equals("1")){
                modelServiceAttended!!.add(ModelServiceAttended(ItemsAttend3.ID_ProductWiseServiceDetails,ItemsAttend3.SubProduct
                    ,ItemsAttend3.ID_Product,ItemsAttend3.ID_Services,ItemsAttend3.Service,ItemsAttend3.ServiceCost,
                    ItemsAttend3.ServiceTaxAmount,ItemsAttend3.ServiceNetAmount,ItemsAttend3.Remarks,ItemsAttend3.isChecked,
                    ItemsAttend3.isDelete,ItemsAttend3.isCheckedAdd,ItemsAttend3.ServiceTypeId,ItemsAttend3.ServiceTypeName))
            }


        }
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation)
        recyclr_Product!!.layoutAnimation = controller
        val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
        rcyler_followup!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        adapterServiceAttended = ServiceAttendedAdapter(this@ServiceFollowUPActiivty, modelServiceAttended,jsonArrayServiceType)
        rcyler_followup!!.scheduleLayoutAnimation()
        rcyler_followup!!.adapter = adapterServiceAttended
        adapterServiceAttended!!.notifyDataSetChanged()


//        adapterServiceAttended!!.notifyDataSetChanged()
    }


    private fun filterBottomData() {

        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.service_followup_filter, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(true)



            val txtCancel       = view.findViewById(R.id.txtReset) as TextView
            val txtSubmit       = view.findViewById(R.id.txtSearch) as TextView
            val tie_Selectarea      = view.findViewById(R.id.tie_Selectarea) as TextInputEditText
            val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
            var isAdmin   = IsAdminSP.getString("IsAdmin", null)
            Log.e(TAG,"isAdmin 796  "+isAdmin)




            txtCancel.setOnClickListener {
                tie_Selectarea!!.text!!.clear()
//                loadLoginEmpDetails("1")
            }

            txtSubmit.setOnClickListener {

////                validateData(dialog1)
                id_search = tie_Selectarea!!.text.toString()
                Log.e(TAG,"ffffffffff  "+ id_search)
                servicesearchcount = 0
                if(id_search.equals(""))
                {
                    tie_Selectarea!!.setError("Please Enter a Valid Customer Id or Serial Number or Mobile Number");

                }
                else{
                    getSearch(id_search!!)
                    dialog1.dismiss()
                }


            }


            dialog1!!.setContentView(view)
            dialog1.show()

            dialog1.show()
        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }

    }
 @SuppressLint("SuspiciousIndentation")
 private fun getSearch(idSearch: String) {
        if (!id_search.equals("")) {
            subMode = "0"
        }
        jsonArrayServiceType = JSONArray()
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                servicedetailsViewModel.getServiceDetails(
                    this, FK_Product!!, id_search, subMode!!
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            Log.e(TAG,"search... 447788"+msg)
                            if (msg!!.length > 0) {
                                if (servicesearchcount == 0) {
                                    servicesearchcount++
                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceDetails")
                                        jsonArrayServiceType =
                                            jobjt.getJSONArray("ServiceAttendedList")

                                            Log.e(TAG,"size 2233 jsonArrayServiceType=="+jsonArrayServiceType.length())
                                        if (jsonArrayServiceType.length() > 0)
                                        {

                                            Log.e(TAG,"size 2233 jsonArrayServiceType== if"+jsonArrayServiceType.length())
                                            modelServicesListDetails.clear()
                                            Log.e(TAG, " 388...0  ")
                                            for (i in 0 until jsonArrayServiceType.length()) {
                                                var jsonObject = jsonArrayServiceType.getJSONObject(i)
                                                Log.e(
                                                    TAG,
                                                    " 388...1  " + jsonObject.getString("Product")
                                                )

                                                //    modelServicesListDetails.clear()

                                                Log.e(TAG,"modelServicesListDetails Add   110883    ")
                                                modelServicesListDetails!!.add(
                                                    ServiceDetailsFullListModel(
                                                        "0",
                                                        jsonObject.getString("FK_Category"),
                                                        jsonObject.getString("MasterProduct"),
                                                        jsonObject.getString("FK_Product"),
                                                        jsonObject.getString("Product"),
                                                        "-2",
                                                        jsonObject.getString("BindProduct"),
                                                        jsonObject.getString("ComplaintProduct"),
                                                        jsonObject.getString("Warranty"),
                                                        jsonObject.getString("ServiceWarrantyExpireDate"),
                                                        jsonObject.getString("ReplacementWarrantyExpireDate"),
                                                        jsonObject.getString("ID_CustomerWiseProductDetails"),
                                                        jsonObject.getString("ServiceWarrantyExpired"),
                                                        jsonObject.getString("ReplacementWarrantyExpired"),
                                                        "0",
                                                        "",
                                                        "",
                                                        false,
                                                        jsonObject.getString("SerchSerialNo"),
                                                        ID_Category,edtCategory!!.text.toString(),
                                                        ID_SubCategory!!,edtSubCategory!!.text.toString(),
                                                        ID_Brand!!,edtBrand!!.text.toString(),
                                                        ID_Complaint!!,edtComplaint!!.text.toString()
                                                    )
                                                )

                                                var ServiceAttendedListDet =
                                                    jsonObject.getJSONArray("ServiceAttendedListDet")

                                                for (j in 0 until ServiceAttendedListDet.length()) {
                                                    var jsonObjectSub =
                                                        ServiceAttendedListDet.getJSONObject(j)
                                                    Log.e(
                                                        TAG,
                                                        " 388...2  " + jsonObjectSub.getString("Product")
                                                    )
                                                    Log.e(TAG,"modelServicesListDetails Add   110884    ")
                                                    modelServicesListDetails!!.add(
                                                        ServiceDetailsFullListModel(
                                                            "1",
                                                            jsonObjectSub.getString("FK_Category"),
                                                            jsonObjectSub.getString("MasterProduct"),
                                                            jsonObjectSub.getString("FK_Product"),
                                                            jsonObjectSub.getString("Product"),
                                                            jsonObjectSub.getString("SLNo"),
                                                            jsonObjectSub.getString("BindProduct"),
                                                            jsonObjectSub.getString("ComplaintProduct"),
                                                            jsonObjectSub.getString("Warranty"),
                                                            jsonObjectSub.getString("ServiceWarrantyExpireDate"),
                                                            jsonObjectSub.getString("ReplacementWarrantyExpireDate"),
                                                            jsonObjectSub.getString("ID_CustomerWiseProductDetails"),
                                                            jsonObjectSub.getString("ServiceWarrantyExpired"),
                                                            jsonObjectSub.getString("ReplacementWarrantyExpired"),
                                                            "0",
                                                            "",
                                                            "",
                                                            false,
                                                            jsonObject.getString("SerchSerialNo"),
                                                            ID_Category,edtCategory!!.text.toString(),
                                                            ID_SubCategory!!,edtSubCategory!!.text.toString(),
                                                            ID_Brand!!,edtBrand!!.text.toString(),
                                                            ID_Complaint!!,edtComplaint!!.text.toString()
                                                        )
                                                    )


                                                }
                                            }

                                            for (j in 0 until modelServicesListDetails.size) {
                                                Log.e(
                                                    TAG,
                                                    "447..1   " + "" + j + "  " + modelServicesListDetails.get(
                                                        j
                                                    ).Product
                                                )
                                            }

                                            val lLayout =
                                                GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                                            rcyler_followup!!.layoutManager =
                                                lLayout as RecyclerView.LayoutManager?
                                            servDetadapter = ServiceDetailsAdapter(
                                                this@ServiceFollowUPActiivty,
                                                modelServicesListDetails
                                            )
                                            rcyler_followup!!.adapter = servDetadapter
                                            servDetadapter!!.setClickListener(this@ServiceFollowUPActiivty)
                                        }
                                        else{
                                            Log.e(TAG,"size 2233 jsonArrayServiceType==  else"+jsonArrayServiceType.length())

                                            val builder = AlertDialog.Builder(
                                                this@ServiceFollowUPActiivty, R.style.MyDialogTheme
                                            )
                                            builder.setMessage("No Data Found")
                                            builder.setPositiveButton("Ok") { dialogInterface, which ->
                                            }
                                            val alertDialog: AlertDialog = builder.create()
                                            alertDialog.setCancelable(false)
                                            alertDialog.show()
                                        }




                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty, R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }
                            } else {
//                                Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "392002 +" + e)
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }

            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }



    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imback -> {
                onBackPressed()
            }
            R.id.imv_infofollowup -> {
//                leadShow = "0"
//                serviceFollowUpInfo = 0
//                loadInfo(ID_Customerserviceregister,ID_CustomerserviceregisterProductDetails)
                openAlertDialogForMoreInfo(serviceFollowUpInfoObjectList)
            }
            R.id.imv_filterfollowup -> {
                filterBottomData()
            }
            R.id.imv_addproduct -> {
                allproductlst = 0
                getAllProductList()
            }
            R.id.add_Product -> {
                loadMoreServiceAttended()
            }
            R.id.txt_next -> {

//                var jObject = JSONObject()
//
//                jObject.put("FK_Product",  "557")
//                jObject.put("ReplacementWarrantyExpireDate",  "")
//
//
//                ProductSubDetails.put(jObject)
//
//                jObject = JSONObject()
//
//                jObject.put("FK_Product",  "591")
//                jObject.put("ReplacementWarrantyExpireDate",  "")
//                ProductSubDetails.put(jObject)
//
//                loadMainProduct()
//               valiadateServiceAttended()
//                modeTab = modeTab+1
//                loadlayout()

                checkAttendance()
                if (saveAttendanceMark) {
                    Config.disableClick(v)
                    if (modeTab == 0){
                        valiadateServiceAttended()
                    }
                    else if (modeTab == 1){

                        validatetab2()
                    }
                    else if (modeTab == 2){
                        validateService3()
                    }
                    else if(modeTab==3)
                    {
                        Config.disableClick(v)
                        validateActiontaken4()

                    }
                }


            }

            R.id.txt_previous -> {
                modeTab = modeTab-1
                loadlayout()
//                ll_service_Attended!!.visibility = View.VISIBLE
//                ll_Service3!!.visibility = View.GONE
            }

            R.id.edtCategory -> {
                categoryDet = 0
                ReqMode = "77"
                getCategory(ReqMode!!,"0")
            }
            R.id.edtSubCategory -> {
                Config.disableClick(v)
                if(ID_Category.equals("")){
                    Config.snackBars(context,v,"Select Category")
                }
                else{
                    subCategoryDet = 0
                    ReqMode = "125"
                    getSubCategory(ReqMode!!,ID_Category!!)
                }
            }
            R.id.edtBrand -> {
                Config.disableClick(v)
                brandDet = 0
                ReqMode = "126"
                getBrands(ReqMode!!)
            }
            R.id.edtComplaint -> {
                Config.disableClick(v)
                complaintDet = 0
                ReqMode = "70"
                if (!ID_Category.equals("")){
                    getComplaints(ReqMode!!,"")
                }else{
                    Config.snackBars(context,v,"Select Category")
                }
            }


        }
    }



    private fun validateActiontaken4() {

        var hasId =  hasactionTakenSelected(actionTakenSelected!!)

        if (hasId){
            var hasId1 =  haspaymentMandtory(actionTakenSelected!!)
            if (hasId1){
                if (billtype.equals("") || billtype.equals("0")){
                    Config.showCustomToast("Select BillType",context)
//                Toast.makeText(applicationContext,"Select BillType",Toast.LENGTH_SHORT).show()
                }
                else{
                    var hasId1 =  haspaymentMandtory(actionTakenSelected!!)
                    Log.e(TAG,"  25585  "+hasId1)
                    if (hasId1){
                        // Payment Validation
                        var netAmntt= edtnetAmount!!.text.toString()
                        if (arrPaymentFinal.length() == 0){
                            Config.showCustomToast("Select payment Methode ",context)
//                            Toast.makeText(applicationContext,"Select payment Methode ",Toast.LENGTH_SHORT).show()
                        }else if (strSumPayMethode.toFloat() != netAmntt.toFloat()){
                            Config.showCustomToast("Net Amount & Payment Amount should be equal",context)
//                            Toast.makeText(applicationContext,"Net Amount & Payment Amount should be equal",Toast.LENGTH_SHORT).show()
                        }else{

                            var Datess = tie_DateAttended!!.text.toString()
                            var isValid = Config.convertTimemills(TicketRegDate,Datess)
                            if (isValid){
                                serviceFollowUpAttendance=0
                                loadAttendance()
                            }else{
                                Config.showCustomToast("Attended Date Should be greater than or equal to Ticket register Date",context)
                            }

//                            serviceFollowUpAttendance=0
//                            loadAttendance()
                        }

                        Log.e(TAG,"  252222    Select payment methode   ")
                    }else{
                        Log.e(TAG,"  252223    Select payment methode   ")
                        var Datess = tie_DateAttended!!.text.toString()
                        var isValid = Config.convertTimemills(TicketRegDate,Datess)
                        if (isValid){
                            serviceFollowUpAttendance=0
                            loadAttendance()
                        }else{
                            Config.showCustomToast("Attended Date Should be greater than or equal to Ticket register Date",context)
                        }

//                        serviceFollowUpAttendance=0
//                        loadAttendance()
                    }
                }
            }
            else{
              //  Config.showCustomToast("Select BillType",context)
                serviceFollowUpAttendance=0
                loadAttendance()
                //Toast.makeText(applicationContext,strActiontakenStatusMessage,Toast.LENGTH_SHORT).show()
            }

//            if (billtype.equals("") || billtype.equals("0")){
//                Config.showCustomToast("Select BillType",context)
////                Toast.makeText(applicationContext,"Select BillType",Toast.LENGTH_SHORT).show()
//            }
//            else{
//                var hasId1 =  haspaymentMandtory(actionTakenSelected!!)
//                Log.e(TAG,"  25585  "+hasId1)
//                if (hasId1){
//                    // Payment Validation
//                        var netAmntt= edtnetAmount!!.text.toString()
//                        if (arrPaymentFinal.length() == 0){
//                            Config.showCustomToast("Select payment Methode ",context)
////                            Toast.makeText(applicationContext,"Select payment Methode ",Toast.LENGTH_SHORT).show()
//                        }else if (strSumPayMethode.toFloat() != netAmntt.toFloat()){
//                            Config.showCustomToast("Net Amount & Payment Amount should be equal",context)
////                            Toast.makeText(applicationContext,"Net Amount & Payment Amount should be equal",Toast.LENGTH_SHORT).show()
//                        }else{
//                            serviceFollowUpAttendance=0
//                            loadAttendance()
//                        }
//
//                    Log.e(TAG,"  252222    Select payment methode   ")
//                }else{
//                    Log.e(TAG,"  252223    Select payment methode   ")
//                        serviceFollowUpAttendance=0
//                        loadAttendance()
//                }
//            }
        }else{
            Config.showCustomToast(strActiontakenStatusMessage,context)
            //Toast.makeText(applicationContext,strActiontakenStatusMessage,Toast.LENGTH_SHORT).show()
        }


    }

    private fun haspaymentMandtory(actionTakenSelected: ArrayList<ActionTakenMainModel>): Boolean {
        var isChecked = true
        for (i in 0 until actionTakenSelected.size) {  // iterate through the JsonArray

            Log.e(TAG,"252224   haspaymentMandtory   "+actionTakenSelected.get(i).actionStatus)
            if (!actionTakenSelected.get(i).actionStatus.equals("4")){
                Log.e(TAG,"25581   haspaymentMandtory   "+actionTakenSelected.get(i).actionStatus)
                if (!actionTakenSelected.get(i).actionStatus.equals("11")){
                    Log.e(TAG,"25582   haspaymentMandtory   "+actionTakenSelected.get(i).actionStatus)
                    isChecked = false
                    break
                }
            }else if (!actionTakenSelected.get(i).actionStatus.equals("11")){
                Log.e(TAG,"25583   haspaymentMandtory   "+actionTakenSelected.get(i).actionStatus)
                if (!actionTakenSelected.get(i).actionStatus.equals("4")){
                    Log.e(TAG,"25584   haspaymentMandtory   "+actionTakenSelected.get(i).actionStatus)
                    isChecked = false
                    break
                }
            }

        }
        return isChecked
    }

    private fun hasactionTakenSelected(actionTakenSelected: ArrayList<ActionTakenMainModel>): Boolean {
        var isChecked = true
        strActiontakenStatusMessage = ""
        for (i in 0 until actionTakenSelected.size) {  // iterate through the JsonArray

            if (actionTakenSelected.get(i).ID_Action.equals("")){
                strActiontakenStatusMessage = "Select Action for "+actionTakenSelected.get(i).Product
                isChecked = false
            }
            else if (actionTakenSelected.get(i).actionStatus.equals("9") && actionTakenSelected.get(i).ID_leadAction.equals("")){
                strActiontakenStatusMessage = "Select Lead Action for "+actionTakenSelected.get(i).Product
                isChecked = false
            }
            else if (actionTakenSelected.get(i).leadActionStatus.equals("1")){
                if (actionTakenSelected.get(i).ID_ActionType.equals("")){
                    strActiontakenStatusMessage = "Select Action Type for "+actionTakenSelected.get(i).Product
                    isChecked = false
                }
                else if (actionTakenSelected.get(i).FollowupDate.equals("")){
                    strActiontakenStatusMessage = "Select FollowUp Date for "+actionTakenSelected.get(i).Product
                    isChecked = false
                }
                else if (actionTakenSelected.get(i).ID_Emplloyee.equals("")){
                    strActiontakenStatusMessage = "Select Assigned Employee for "+actionTakenSelected.get(i).Product
                    isChecked = false
                }

                try {
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                    val c = Calendar.getInstance().time
                    println("Current time => $c")

                    val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val formattedDate = df.format(c)
                    println("Current date => $formattedDate")

                    val date1 = dateFormat.parse(formattedDate)
                    val date2 = dateFormat.parse(actionTakenSelected.get(i).FollowupDate)

                    println("followup date => $date2")

                    calendar11!!.time = date1
                    calendar22!!.time = date2

                    System.out.println("Compare Result : " + calendar22.compareTo(calendar11))

                    System.out.println("Compare Resultt : " + calendar22.compareTo(calendar11))

                    /*    if(calendar22.compareTo(calendar11).equals(-1))
                        {
                            til_VisitDate!!.setError("Visit On Date should be greater than or equal to Today's Date")
                            til_VisitDate!!.setErrorIconDrawable(null)
                        }*/

                    //  System.out.println("Compare Result : " + calendar1.compareTo(calendar2))
                }
                catch(e: ParseException)
                {


                }
                followupDate= calendar22!!.compareTo(calendar11).toString()
                if (followupDate.equals("-1")){
                    /* til_VisitDate!!.setError("Select Visit Date");
                     til_VisitDate!!.setErrorIconDrawable(null)*/
                    strActiontakenStatusMessage = "Visit On Date should be greater than or equal to Today's Date"+actionTakenSelected.get(i).Product

                }
            }

        }
        return isChecked
    }

    private fun validatetab2() {

//        "ProductDetails"":
//        [{""ID_MasterProduct"":""340"",""ID_Product"":""12"",""ID_WarrantyMode"":""1"",""ID_ReplaceMode"":""1"",""Quantity"":""2"",
//            ""ProductAmount"":""100"",""FK_Stock"":""150"",""ID_CustomerWiseProductDetails"":""0""}],

        var hasId =  hasPartReplaced(servicepartsReplacedModel!!)
        Log.e(TAG,"20131  "+hasId)

        if (PSValue.equals("1")){
            if (hasId){

                companantCharge = "0"
                servicePartsArray  = JSONArray()
                for (i in 0 until servicepartsReplacedModel.size) {

                    if (servicepartsReplacedModel[i].isChecked.equals("1")){
                        Log.e(TAG,"32241  ID_MasterProduct                "+servicepartsReplacedModel[i].ID_MasterProduct)
                        Log.e(TAG,"32242  ID_Product                      "+servicepartsReplacedModel[i].ID_Product)
                        Log.e(TAG,"32243  WarrantyMode                    "+servicepartsReplacedModel[i].WarrantyMode)
                        Log.e(TAG,"32244  ReplceMode                      "+servicepartsReplacedModel[i].ReplceMode)
                        Log.e(TAG,"32244  Quantity                        "+servicepartsReplacedModel[i].Quantity)
                        Log.e(TAG,"32244  ProductAmount                   "+servicepartsReplacedModel[i].ProductAmount)
                        Log.e(TAG,"32244  FK_Stock                        "+servicepartsReplacedModel[i].FK_Stock)
                        Log.e(TAG,"1416664  ID_CustomerWiseProductDetails   "+servicepartsReplacedModel[i].ID_CustomerWiseProductDetails)


                        val jObject = JSONObject()

                        jObject.put("ID_MasterProduct", (servicepartsReplacedModel[i].ID_MasterProduct))
                        jObject.put("ID_Product", (servicepartsReplacedModel[i].ID_Product))
                        jObject.put("ID_WarrantyMode", (servicepartsReplacedModel[i].WarrantyMode))
                        jObject.put("ID_ReplaceMode", (servicepartsReplacedModel[i].ReplceMode))
                        jObject.put("Quantity", (servicepartsReplacedModel[i].Quantity))
                        jObject.put("ProductAmount", (servicepartsReplacedModel[i].ProductAmount))
                        jObject.put("FK_Stock", (servicepartsReplacedModel[i].FK_Stock))
                        jObject.put("ID_CustomerWiseProductDetails", (servicepartsReplacedModel[i].ID_CustomerWiseProductDetails))
//                    jObject.put("FK_SubCategory", (servicepartsReplacedModel[i].FK_SubCategory))
//                    jObject.put("FK_Brand", (servicepartsReplacedModel[i].FK_Brand))
//                    jObject.put("FK_Category", (servicepartsReplacedModel[i].FK_Category))

                        servicePartsArray.put(jObject)

                        companantCharge = (companantCharge!!.toFloat() + ((servicepartsReplacedModel[i].Quantity.toFloat() * (servicepartsReplacedModel[i].ProductAmount).toFloat() ))).toString()
                    }
                }

                Log.e(TAG,"companantCharge  253411  "+companantCharge)

                modeTab = modeTab+1
                loadlayout()
            }else{
                Config.showCustomToast(strActiontakenStatusMessage,context)
                // Toast.makeText(applicationContext,""+strActiontakenStatusMessage,Toast.LENGTH_SHORT).show()
            }
        }else{
            Log.e(TAG,"companantCharge  25342   "+companantCharge)
            if (hasId){

                companantCharge = "0"
                servicePartsArray  = JSONArray()
                for (i in 0 until servicepartsReplacedModel.size) {

                    if (servicepartsReplacedModel[i].isChecked.equals("1")){
                        Log.e(TAG,"32241  ID_MasterProduct                "+servicepartsReplacedModel[i].ID_MasterProduct)
                        Log.e(TAG,"32242  ID_Product                      "+servicepartsReplacedModel[i].ID_Product)
                        Log.e(TAG,"32243  WarrantyMode                    "+servicepartsReplacedModel[i].WarrantyMode)
                        Log.e(TAG,"32244  ReplceMode                      "+servicepartsReplacedModel[i].ReplceMode)
                        Log.e(TAG,"32244  Quantity                        "+servicepartsReplacedModel[i].Quantity)
                        Log.e(TAG,"32244  ProductAmount                   "+servicepartsReplacedModel[i].ProductAmount)
                        Log.e(TAG,"32244  FK_Stock                        "+servicepartsReplacedModel[i].FK_Stock)
                        Log.e(TAG,"1416664  ID_CustomerWiseProductDetails   "+servicepartsReplacedModel[i].ID_CustomerWiseProductDetails)


                        val jObject = JSONObject()

                        jObject.put("ID_MasterProduct", (servicepartsReplacedModel[i].ID_MasterProduct))
                        jObject.put("ID_Product", (servicepartsReplacedModel[i].ID_Product))
                        jObject.put("ID_WarrantyMode", (servicepartsReplacedModel[i].WarrantyMode))
                        jObject.put("ID_ReplaceMode", (servicepartsReplacedModel[i].ReplceMode))
                        jObject.put("Quantity", (servicepartsReplacedModel[i].Quantity))
                        jObject.put("ProductAmount", (servicepartsReplacedModel[i].ProductAmount))
                        jObject.put("FK_Stock", (servicepartsReplacedModel[i].FK_Stock))
                        jObject.put("ID_CustomerWiseProductDetails", (servicepartsReplacedModel[i].ID_CustomerWiseProductDetails))
//                    jObject.put("FK_SubCategory", (servicepartsReplacedModel[i].FK_SubCategory))
//                    jObject.put("FK_Brand", (servicepartsReplacedModel[i].FK_Brand))
//                    jObject.put("FK_Category", (servicepartsReplacedModel[i].FK_Category))

                        servicePartsArray.put(jObject)

                        companantCharge = (companantCharge!!.toFloat() + ((servicepartsReplacedModel[i].Quantity.toFloat() * (servicepartsReplacedModel[i].ProductAmount).toFloat() ))).toString()
                    }
                }

                Log.e(TAG,"companantCharge  253412   "+companantCharge)

                modeTab = modeTab+1
                loadlayout()
            }else{
                Config.showCustomToast(strActiontakenStatusMessage,context)
                // Toast.makeText(applicationContext,""+strActiontakenStatusMessage,Toast.LENGTH_SHORT).show()
            }
        }

    }

//    private fun hasPartReplaced(servicepartsReplacedModel: ArrayList<ServicePartsReplacedModel>): Boolean {
//
//        var isChecked = true
//        for (i in 0 until servicepartsReplacedModel.size) {  // iterate through the JsonArray
//
//            Log.e(TAG,"20132       "+servicepartsReplacedModel.get(i).WarrantyMode+"   R:   "+servicepartsReplacedModel.get(i).ReplceMode+"" +
//                    "   Q:  "+servicepartsReplacedModel.get(i).Quantity+"  C:  "+servicepartsReplacedModel.get(i).isChecked)
//            if (servicepartsReplacedModel.get(i).isChecked.equals("1")){
//                isChecked = true
//                if (servicepartsReplacedModel.get(i).WarrantyMode.equals("0") || servicepartsReplacedModel.get(i).ReplceMode.equals("0")  ||
//                    servicepartsReplacedModel.get(i).Quantity.equals("") || servicepartsReplacedModel.get(i).Quantity.equals(".")){
//                    isChecked = false
//                    break
//                }
//            }
//
//        }
//        return isChecked
//
//    }


    private fun productWisePopup() {
        Log.e(TAG,"864  ")
        try {
            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!! .setContentView(R.layout.cs_more_service_attended_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)

            recyFollowupAttended = dialogMoreServiceAttendeSheet!! .findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!! .findViewById(R.id.tv_cancel) as TextView
            var tv_submit = dialogMoreServiceAttendeSheet!! .findViewById(R.id.tv_submit) as TextView
            var tv_main_header = dialogMoreServiceAttendeSheet!! .findViewById(R.id.tv_main_header) as TextView

            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            tv_main_header.setText("Product Wise Complaints")
            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyFollowupAttended!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            compAdapter = complaint_service_followup(this@ServiceFollowUPActiivty, complaintfollowup!!)
            recyFollowupAttended!!.adapter = compAdapter
            compAdapter!!.setClickListener(this@ServiceFollowUPActiivty)

            tv_cancel!!.setOnClickListener {
                selectMoreServiceAttended = JSONArray()
                dialogMoreServiceAttendeSheet!!.dismiss()
            }
            tv_submit!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
                Log.e(TAG,"20269  s")
                Log.e(TAG,"610  selectMoreServiceAttended   "+selectMoreServiceAttended)

                addDataServiceAttended()
            }

//            recyServiceWarranty!!.setOnTouchListener(object : View.OnTouchListener {
//                override fun onTouch(v: View, event: MotionEvent?): Boolean {
//                    v.parent.requestDisallowInterceptTouchEvent(true)
//                    v.onTouchEvent(event)
//                    return true
//                }
//            })


            dialogMoreServiceAttendeSheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun actionTakenActionPopUp() {
        Log.e(TAG, "3316  actionTakenActionPopUp")
        try {
            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!!.setContentView(R.layout.action_taken_action_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)

            recyFollowupAttended =
                dialogMoreServiceAttendeSheet!!.findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!!.findViewById(R.id.tv_cancel) as TextView

            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyFollowupAttended!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            actionTakeActionFilter =
                ActionTakeActionFilter(this@ServiceFollowUPActiivty, actionTypeActionList!!)
            recyFollowupAttended!!.adapter = actionTakeActionFilter
            actionTakeActionFilter!!.setClickListener(this@ServiceFollowUPActiivty)

            tv_cancel!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
            }
            dialogMoreServiceAttendeSheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun LeadActionPopup() {
        Log.e(TAG, "3316  LeadActionPopup")
        try {
            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!!.setContentView(R.layout.action_taken_action_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)

            recyFollowupAttended =
                dialogMoreServiceAttendeSheet!!.findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!!.findViewById(R.id.tv_cancel) as TextView

            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyFollowupAttended!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            leadActionAdapter = LeadActionAdapter(this@ServiceFollowUPActiivty, leadActionList!!)
            recyFollowupAttended!!.adapter = leadActionAdapter
            leadActionAdapter!!.setClickListener(this@ServiceFollowUPActiivty)

            tv_cancel!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
            }
            dialogMoreServiceAttendeSheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun otherChargesPopup() {
        Log.e(TAG, "864  ")
        try {
         //   otherChargesFinalList.clear()

             if (otherChargesFinalList.size == 0){
                 for (i in 0 until otherChargeList.length()) {
                     var empModel = otherChargeList.getJSONObject(i)
                     otherChargesFinalList!!.add(
                         OtherChargesMainModel(
                             empModel.getString("ID_OtherChargeType"),
                             empModel.getString("OctyName"),
                             empModel.getString("OctyTransType"),
                             "0.00"
                         )
                     )
                 }
             }

            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!!.setContentView(R.layout.other_charges_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)
            recyFollowupAttended =
                dialogMoreServiceAttendeSheet!!.findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!!.findViewById(R.id.tv_cancel) as TextView
            var tv_submit = dialogMoreServiceAttendeSheet!!.findViewById(R.id.tv_submit) as TextView

            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyFollowupAttended!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            otherChargesAdapterAdapter =
                OtherChargesAdapterAdapter(this@ServiceFollowUPActiivty, otherChargesFinalList!!)
            recyFollowupAttended!!.adapter = otherChargesAdapterAdapter
            otherChargesAdapterAdapter!!.setClickListener(this@ServiceFollowUPActiivty)

            tv_cancel!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
            }
            tv_submit!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
                otherChargesFinalList = otherChargesAdapterAdapter!!.mList
                var totalAmount = 0.0
                for (i in 0 until otherChargesFinalList.size) {
                    val empModel = otherChargesFinalList[i]
                    val jsonObject = JSONObject()
                    jsonObject.put("ID_OtherChargeType", empModel.ID_OtherChargeType)
                    jsonObject.put("OctyTransType", empModel.OctyTransType)
                    var amount = 0.0
                    if (empModel.OctyAmount.equals("")) {
                        jsonObject.put("OctyAmount", "0")
                    } else {
                        amount = empModel.OctyAmount.toDouble()
                        jsonObject.put("OctyAmount", empModel.OctyAmount)
                    }
                    jsonObject.put("TransTypeID", empModel.OctyTransType)
                    arrOtherChargeFinal.put(jsonObject)
                    totalAmount = totalAmount + amount
                }

                edt_other_charges!!.setText("" + Config.changeTwoDecimel(totalAmount.toString()))
                loadNetAmount()
                Log.v("sfsdfsdfsd", "list" + arrOtherChargeFinal.toString())
            }
            dialogMoreServiceAttendeSheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun attendancePopUp(modelFollowUpAttendance: java.util.ArrayList<ModelFollowUpAttendance>) {
        Log.e(TAG, "864  ")
        try {
            otherChargesFinalList.clear()
            for (i in 0 until otherChargeList.length()) {
                var empModel = otherChargeList.getJSONObject(i)
                otherChargesFinalList!!.add(
                    OtherChargesMainModel(
                        empModel.getString("ID_OtherChargeType"),
                        empModel.getString("OctyName"),
                        empModel.getString("OctyTransType"),
                        ""
                    )
                )
            }
            dialogMoreServiceAttendeSheet = Dialog(this)
            dialogMoreServiceAttendeSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMoreServiceAttendeSheet!!.setContentView(R.layout.other_attendance_sheet)
            dialogMoreServiceAttendeSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogMoreServiceAttendeSheet!!.setCancelable(false)
            recyFollowupAttended =
                dialogMoreServiceAttendeSheet!!.findViewById(R.id.recyFollowupAttended) as RecyclerView
            var tv_cancel = dialogMoreServiceAttendeSheet!!.findViewById(R.id.tv_cancel) as TextView
            var tv_submit = dialogMoreServiceAttendeSheet!!.findViewById(R.id.tv_submit) as TextView

            val window: Window? = dialogMoreServiceAttendeSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyFollowupAttended!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
            serviceFollowAttendanceAdapter = ServiceFollowAttendanceAdapter(this@ServiceFollowUPActiivty, modelFollowUpAttendance)
            recyFollowupAttended!!.adapter = serviceFollowAttendanceAdapter



            tv_cancel!!.setOnClickListener {
                dialogMoreServiceAttendeSheet!!.dismiss()
            }
            tv_submit!!.setOnClickListener {
              //  dialogMoreServiceAttendeSheet!!.dismiss()


//                for (i in 0 until modelFollowUpAttendance.size) {
//                    var empModel = modelFollowUpAttendance[i]
//                    if (empModel.isChecked.equals("1")) {
//                        var jsonObject=new Js
//                    }
//                }
                checkAttendance()
                if (saveAttendanceMark) {
                    Log.v("sfsdfsdfsd","modelFollowUpAttendance "+modelFollowUpAttendance.toString())
                    for (obj in serviceFollowAttendanceAdapter!!.modelFollowUpAttendance) {
                        if(obj.isChecked.equals("1")) {
                            val jsonObject = JSONObject()
                            jsonObject.put("ID_Employee", obj.ID_Employee)
                            jsonObject.put("EmployeeType", obj.ID_CSAEmployeeType)
                            AttendedEmployeeDetails.put(jsonObject)
                        }
                    }
                    if(AttendedEmployeeDetails.length()==0)
                    {
                        Toast.makeText(
                            applicationContext,
                            "Please choose atlest one employee",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else
                    {
                        dialogMoreServiceAttendeSheet!!.dismiss()
                        Log.v("sfsdfsdfdsfs","sdfsddddd "+AttendedEmployeeDetails.toString())
                        finalSave()
                    }
                }


            }
            dialogMoreServiceAttendeSheet!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(position: Int, data: String) {
        Log.e(TAG, "864  ")
        if (data.equals("productwise_cmplt")) {

            modEditPosition = position
            modChanged = 1

//            val jsonObject = complaintfollowup.getJSONObject(position)
            Log.e(TAG, "test entered   ")

            var FK_Category = modelServicesListDetails.get(position).FK_Category
            var FK_Product = modelServicesListDetails.get(position).FK_Product

            productwisecomplaintcouny = 0
            getProductWiseComplaint(FK_Category!!, FK_Product!!)
        }
        if (data.equals("action_taken_action")) {

            Log.e(TAG,"3316   action_taken_action")
            modEditPosition = position

            var FK_Category = "0"
            var FK_Product = "0"

            if (PSValue.equals("1")){
                FK_Category = modelServicesListDetails.get(position).FK_Category
                FK_Product = modelServicesListDetails.get(position).FK_Product
            }else{
                FK_Category = ID_Category
                FK_Product = "0"
            }
            actionTakenActioncouny = 0
            getActionTakenAction(FK_Category!!, FK_Product!!)
        }

        if (data.equals("lead_FollowupDateClick")) {

            Log.e(TAG,"3316   lead_FollowupDateClick")
            modEditPosition = position
            DateType = 1
            openBottomSheet()

        }



        if (data.equals("lead_ActionTypeClick")) {

            Log.e(TAG,"3316   lead_ActionTypeClick")
            modEditPosition = position

            followUpType = 0
            getFollowupType()

        }

        if (data.equals("followuptype")) {
            dialogFollowupType!!.dismiss()

            val jsonObject = followUpTypeSort.getJSONObject(position)
            Log.e(TAG, "ID_ActionType   " + jsonObject.getString("ID_ActionType"))

            var empModel = actionTakenSelected[modEditPosition]

            empModel.ID_ActionType = jsonObject.getString("ID_ActionType")
            empModel.ActionType = jsonObject.getString("ActnTypeName")
            empModel.ActionTypeMode = jsonObject.getString("ActionMode")

            actionTakenAdapter!!.notifyItemChanged(modEditPosition)

        }

        if (data.equals("lead_AssignedToClick")) {
            Log.e(TAG,"3316   lead_AssignedToClick")
            modEditPosition = position

            employee = 0
            getEmployee()

        }

        if (data.equals("employee")) {
            dialogEmployee!!.dismiss()
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))

            var empModel = actionTakenSelected[modEditPosition]

            empModel.ID_Emplloyee = jsonObject.getString("ID_Employee")
            empModel.EmplloyeeName = jsonObject.getString("EmpName")

            actionTakenAdapter!!.notifyItemChanged(modEditPosition)

        }

        if (data.equals("securityAmountChanged")) {


            var securityAmount = "0.00"
            for (i in 0 until actionTakenSelected.size) {
                val empModel = actionTakenSelected[i]
                securityAmount = (securityAmount.toFloat()+(empModel.securityAmount.toFloat())).toString()
            }
            Log.e(TAG,"2944     securityAmount  :  "+securityAmount)


            DecimelFormatters.set2DecimelPlace(securityAmount.toFloat())
            edttotalSecurityAmount!!.setText(DecimelFormatters.set2DecimelPlace(securityAmount.toFloat()))
            loadNetAmount()

        }


        if (data.equals("chkproductwise_cmplt")) {

            modEditPosition = position
            modChanged = 0

//            val jsonObject = complaintfollowup.getJSONObject(position)
            Log.e(TAG, "test entered   ")

            var FK_Category = modelServicesListDetails.get(position).FK_Category
            var FK_Product = modelServicesListDetails.get(position).FK_Product

            productwisecomplaintcouny = 0
            getProductWiseComplaint(FK_Category!!, FK_Product!!)
        }




        if (data.equals("CompServiceFollowUp")) {
            Log.e(TAG, "95444     " + position)
            dialogMoreServiceAttendeSheet!!.dismiss()
            val jsonObject = complaintfollowup.getJSONObject(position)
            Log.e(TAG, "95444     " + jsonObject)

            var empModel = modelServicesListDetails[modEditPosition]

            empModel.ID_ComplaintList = jsonObject.getString("ID_ComplaintList")
            empModel.ComplaintName = jsonObject.getString("ComplaintName")

//            modelServicesListDetails.get(position).ID_ComplaintList =jsonObject.getString("ID_ComplaintList")
//            modelServicesListDetails.get(position).ComplaintName=jsonObject.getString("ComplaintName")
            Log.e(TAG, "954441     " + empModel.ComplaintName)

            servDetadapter!!.notifyItemChanged(modEditPosition)

//            rcyler_followup!!.adapter!!.notifyItemChanged(position)
//            servDetadapter!!.notifyItemChanged(position)
        }

        if (data.equals("actionTakenActionFilter")) {


            Log.e(TAG,"3316   actionTakenActionFilter")
            dialogMoreServiceAttendeSheet!!.dismiss()
            val jsonObject = actionTypeActionList.getJSONObject(position)
            var empModel = actionTakenSelected[modEditPosition]

            empModel.actionName = jsonObject.getString("NxtActnName")
            empModel.ID_Action = jsonObject.getString("ID_NextAction")
            empModel.actionStatus = jsonObject.getString("Status")
            empModel.ID_leadAction = ""
            empModel.leadAction = ""
            empModel.leadActionStatus = ""
            empModel.ID_ActionType = ""
            empModel.ActionType = ""
            empModel.ActionTypeMode = ""
            empModel.FollowupDate = ""
            empModel.ID_Emplloyee = ""
            empModel.EmplloyeeName = ""

            if (!rcyler_actionTaken!!.isComputingLayout && rcyler_actionTaken!!.scrollState == SCROLL_STATE_IDLE) {
                actionTakenAdapter!!.notifyItemChanged(modEditPosition)
            }

        }
        if (data.equals("leadActionAdapterClick")) {

            Log.e(TAG,"3316 leadActionAdapterClick ")
            dialogMoreServiceAttendeSheet!!.dismiss()
            val jsonObject = leadActionList.getJSONObject(position)
            var empModel = actionTakenSelected[modEditPosition]
            empModel.leadAction = jsonObject.getString("NxtActnName")
            empModel.ID_leadAction = jsonObject.getString("ID_NextAction")
            empModel.leadActionStatus = jsonObject.getString("Status")
            empModel.ID_ActionType = ""
            empModel.ActionType = ""
            empModel.ActionTypeMode = ""
            empModel.FollowupDate = ""
            empModel.ID_Emplloyee = ""
            empModel.EmplloyeeName = ""

            actionTakenAdapter!!.notifyItemChanged(modEditPosition)
        }
        if (data.equals("check_click")) {
            var empModel = actionTakenSelected[position]
            Log.v("sdfsdfdsfds", "check value1 " + empModel.ProvideStandBy)
            if (empModel.ProvideStandBy) {
                empModel.ProvideStandBy = true
            } else {
                empModel.ProvideStandBy = false
            }
            actionTakenAdapter!!.notifyItemChanged(position)
        }


        if (data.equals("lead_action")) {
            Log.e(TAG,"3316   lead_action")
            modEditPosition = position
            var FK_Category = modelServicesListDetails.get(position).FK_Category
            var FK_Product = modelServicesListDetails.get(position).FK_Product
            actionTakenActioncouny = 0
            getLeadAction(FK_Category!!, FK_Product!!)
        }
        if (data.equals("paymentMethod")) {
            dialogPaymentMethod!!.dismiss()
            val jsonObject = paymentMethodeArrayList.getJSONObject(position)
            ID_PaymentMethod = jsonObject.getString("ID_PaymentMethod")
            edtPayMethod!!.setText(jsonObject.getString("PaymentName"))
        }
        if (data.equals("deleteArrayList")) {


            val jsonObject = arrPayment.getJSONObject(position)
            var balAmount = (txtPayBalAmount!!.text.toString()).toFloat()
            var Amount = (jsonObject!!.getString("Amount")).toFloat()

            ID_PaymentMethod = ""
            edtPayMethod!!.setText("")
            edtPayRefNo!!.setText("")
            edtPayAmount!!.setText("")

            arrPayment.remove(position)
            adapterPaymentList!!.notifyItemRemoved(position)
            arrAddUpdate = "0"
            if (arrPayment.length() > 0) {
                ll_paymentlist!!.visibility = View.VISIBLE
            } else {
                ll_paymentlist!!.visibility = View.GONE
            }
            applyMode = 0
            txtPayBalAmount!!.text = (balAmount + Amount).toString()
        }
        if (data.equals("editArrayList")) {
            try {
                Log.e(TAG,"3335")
                arrAddUpdate = "1"
                arrPosition = position
                val jsonObject = arrPayment.getJSONObject(position)
                ID_PaymentMethod = jsonObject!!.getString("MethodID")
                edtPayMethod!!.setText("" + jsonObject!!.getString("Method"))
                edtPayRefNo!!.setText("" + jsonObject!!.getString("RefNo"))
                edtPayAmount!!.setText("" + jsonObject!!.getString("Amount"))


                var payAmnt = 0.00
                for (i in 0 until arrPayment.length()) {
                    //apply your logic
                    val jsonObject = arrPayment.getJSONObject(i)
                    payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                }
                val pay = DecimelFormatters.set2DecimelPlace(payAmnt.toFloat())
                //  payAmnt = (DecimelFormatters.set2DecimelPlace(payAmnt.toFloat()))
                Log.e(TAG, "payAmnt         475    " + payAmnt)
                //////////////               txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - pay.toFloat() + (jsonObject!!.getString("Amount").toFloat())))

            } catch (e: Exception) {
            }
        }
//        24-10-2023 Ranjith
        if (data.equals("addService3Click")) {

            FK_Product_Pos  =  position
            FK_Product_ID  =  serviceTab3MainModel[position].FK_Product
            Log.e(TAG,"10491   "+FK_Product_ID)
            addserviceMode = 0
            loadServiceList()

        }

        if (data.equals("addServiceType3Click")){

            modEditPosition = position
            serviceFollowUpServiceType = 0
            loadServiceType()
//            FK_Product_Pos  =  position
//            FK_Product_ID  =  serviceTab3MainModel[position].FK_Product
//            Log.e(TAG,"10491   "+FK_Product_ID)
//            addserviceMode = 0
//            loadServiceList()

        }

        if (data.equals("serviceTypeClick")){

            dialogServType!!.dismiss()
            var jsonObj = jsonArrayServiceType.getJSONObject(position)
            var empModel = serviceTab3MainModel[modEditPosition]

            empModel.ID_ServiceType = jsonObj.getString("ServiceTypeId")
            empModel.ServiceType = jsonObj.getString("ServiceTypeName")

            serviceTab3Adapter!!.notifyItemChanged(modEditPosition)

        }

        if (data.equals("MainProductList")){

            Log.e(TAG,"hhhhhhhhhhhhhhh  "+position)

            var ID_labelMaster = labelpartsreplaceModel.get(position).ID_MasterProduct


            servicePartsAdapter = ServiceParts_replacedAdapter(this@ServiceFollowUPActiivty, servicepartsReplacedModel!!,ID_labelMaster)
            recy_parts_replaced!!.adapter = servicePartsAdapter
            servicePartsAdapter!!.notifyDataSetChanged()

            Log.e(TAG,"hhhhhhhhhhhhhhh  "+ID_labelMaster)
        }

        if (data.equals("WarrantyModeList")){

            var empModel = servicepartsReplacedModel[position]
//            WarrantyMode = empModel.WarrantyMode
//            ReplceModeSub = empModel.ReplceMode

            modEditPosition = position
//            warrantyEditPosition = position
            Log.e(TAG,"1004 check     "+position)
            warrantycount = 0
            getWarrantyMode()
//            dialogWarrantydMode!!.dismiss()
        }

        if (data.equals("ReplacedModeList")){
            modEditPosition = position

            var empModel = servicepartsReplacedModel[position]
//            WarrantyMode = empModel.WarrantyMode
//            ReplceModeSub = empModel.ReplceMode

            Log.e(TAG,"1018 check     "+position)
            replacedcount = 0
            getReplaceMode()
//            dialogReplacedMode!!.dismiss()
        }

        if (data.equals("ReplaceService")){

            dialogReplacedMode!!.dismiss()

            val jsonObject = ReplaceArrayList.getJSONObject(position)
            var empModel = servicepartsReplacedModel[modEditPosition]

            empModel.ReplceName = jsonObject.getString("ServiceTypeName")
            empModel.ReplceMode = jsonObject.getString("ServiceTypeId")
            recy_parts_replaced!!.adapter!!.notifyItemChanged(modEditPosition)


        }
        if (data.equals("warrantyservice")){

            dialogWarrantydMode!!.dismiss()

            val jsonObject = WarrantyArrayList.getJSONObject(position)
            var empModel = servicepartsReplacedModel[modEditPosition]

            empModel.WarrantyName = jsonObject.getString("ServiceTypeName")
            empModel.WarrantyMode = jsonObject.getString("ServiceTypeId")
            recy_parts_replaced!!.adapter!!.notifyItemChanged(modEditPosition)


        }


        if (data.equals("partAddService2Click")){


//            Changes 26.10.2023
            modEditPosition = position
            FK_CustomerWiseProductDetails_ID = servicepartsReplacedModel[position].ID_CustomerWiseProductDetails
            FK_Product_ID = servicepartsReplacedModel[position].ID_MasterProduct
//            serviceFollowUpServiceType = 0
//            loadServiceType()
            compnantMode = 0
            getComponantList()
        }

        if (data.equals("componantListClik")){
            dialogAddserviceSheet!!.dismiss()

            val jsonObject = servCompanantArray.getJSONObject(position)
            var empModel = servicepartsReplacedModel[modEditPosition]

            var posAdd = 0
            for (i in 0 until servicepartsReplacedModel.size) {
                var empModel = servicepartsReplacedModel[i]
                if (empModel.ID_MasterProduct.equals(FK_Product_ID)){
                    posAdd = i+1
                }
            }

            servicepartsReplacedModel!!.add(posAdd,ServicePartsReplacedModel("0","1",empModel.ID_MasterProduct,empModel.MainProduct,
                jsonObject.getString("ID_Product"),jsonObject.getString("Name"),"","0","",
                jsonObject.getString("ProductAmount"),"0","","0","0",FK_CustomerWiseProductDetails_ID!!,
                ID_SubCategory!!,ID_Brand!!,ID_Category))

         //   recy_parts_replaced!!.adapter = servicePartsAdapter
            servicePartsAdapter!!.notifyItemInserted(posAdd)

            for (i in 0 until servicepartsReplacedModel.size) {
                var empModel = servicepartsReplacedModel[i]

                Log.e(TAG,"2526   "+empModel.ID_MasterProduct+"  :  "+empModel.MainProduct+"  :  "+empModel.Componant)
            }

        }

        if (data.equals("AllProductClik")){
            dialogAllproductSheet!!.dismiss()
            val jsonObject = allProductSort.getJSONObject(position)
            var iD_Prod = jsonObject.getString("ID_Product")
            var hasId =  hasCheckDuplicate(modelServicesListDetails!!,iD_Prod!!)
            if (hasId){
                mainSubProduct = 0
                getMainsubProducts(iD_Prod)
            }else{
                Config.showCustomToast("Product Already exists",context)
            }




        }

        if (data.equals("category")){
            dialogCategory!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = categorySort.getJSONObject(position)
            Log.e(TAG,"ID_Category   "+jsonObject.getString("ID_Category"))

            ID_Category = jsonObject.getString("ID_Category")
            edtCategory!!.setText(jsonObject.getString("CategoryName"))

            ID_SubCategory = ""
            edtSubCategory!!.setText("")

            ID_Brand = ""
            edtBrand!!.setText("")

            ID_Complaint = ""
            edtComplaint!!.setText("")
        }

        if (data.equals("SubCategoryClick")) {
            dialogSubCategory!!.dismiss()
            val jsonObject = subCategorySort.getJSONObject(position)
            Log.e(TAG,"ID_SubCategory   "+jsonObject.getString("ID_SubCategory"))

            ID_SubCategory = jsonObject.getString("ID_SubCategory")
            edtSubCategory!!.setText(jsonObject.getString("SubCatName"))

            ID_Brand = ""
            edtBrand!!.setText("")

            ID_Complaint = ""
            edtComplaint!!.setText("")
        }

        if (data.equals("BrandClick")) {
            dialogBrand!!.dismiss()
            val jsonObject = brandSort.getJSONObject(position)
            Log.e(TAG,"ID_Brand   "+jsonObject.getString("ID_Brand"))

            ID_Brand = jsonObject.getString("ID_Brand")
            edtBrand!!.setText(jsonObject.getString("BrandName"))

            ID_Complaint = ""
            edtComplaint!!.setText("")
        }

        if (data.equals("compalint")) {

            dialogComplaint!!.dismiss()
//            val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = complaintSort.getJSONObject(position)
            Log.e(TAG,"ID_ComplaintList   "+jsonObject.getString("ID_ComplaintList"))

            ID_Complaint = jsonObject.getString("ID_ComplaintList")
            edtComplaint!!.setText(jsonObject.getString("ComplaintName"))

        }






//        24-10-2023 Ranjith

    }

    private fun hasCheckDuplicate(modelServicesListDetails: ArrayList<ServiceDetailsFullListModel> , iD_Prod : String): Boolean {

        var isChecked = true
        for (i in 0 until modelServicesListDetails.size) {  // iterate through the JsonArray
            Log.e(TAG,"3750     "+modelServicesListDetails.get(i).FK_Product)

            if (modelServicesListDetails.get(i).FK_Product.equals(iD_Prod)){
                isChecked = false
                break
            }

        }
        return isChecked
    }

    private fun getAllProductList() {
        var Reqmode = "16"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                allProductViewModel.getAllProduct(this,Reqmode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (allproductlst == 0) {
                                    allproductlst++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   3737   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("MainProductDetails")
                                        allProductArrayList = jobjt.getJSONArray("MainProductDetailsList")
                                        if (allProductArrayList.length() > 0) {

                                            allProductListPop(allProductArrayList)
                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun allProductListPop(allProductArrayList: JSONArray) {

        try {

            dialogAllproductSheet = Dialog(this)
            dialogAllproductSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogAllproductSheet!! .setContentView(R.layout.serviceall_product_popup)
            dialogAllproductSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            dialogAllproductSheet!!.setCancelable(true)

            var recycAllproduct = dialogAllproductSheet!! .findViewById(R.id.recycAllproduct) as RecyclerView
            val etsearch = dialogAllproductSheet!!.findViewById(R.id.etsearch) as EditText

            allProductSort = JSONArray()
            for (k in 0 until allProductArrayList.length()) {
                val jsonObject = allProductArrayList.getJSONObject(k)
                allProductSort.put(jsonObject)
            }



            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recycAllproduct!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ServiceAllproductAdapter(this@ServiceFollowUPActiivty, allProductSort)
            recycAllproduct!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    allProductSort = JSONArray()

                    for (k in 0 until allProductArrayList.length()) {
                        val jsonObject = allProductArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ProdName").length) {
                            if (jsonObject.getString("ProdName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                allProductSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "departmentSort               7103    " + allProductSort)
                    val adapter = ServiceAllproductAdapter(this@ServiceFollowUPActiivty, allProductSort)
                    recycAllproduct!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUPActiivty)
                }
            })





            dialogAllproductSheet!!.show()
            val window: Window? = dialogAllproductSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.white);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        }catch (e : Exception){

            Log.e(TAG,"3856  "+e)
        }

    }

    private fun getMainsubProducts(iD_Prod : String) {
        var ReqMode = "115"
//        var iD_Prod = "10716"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                mainSubProductViewModel.getMainSubProductData(this,iD_Prod,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (mainSubProduct == 0) {
                                    mainSubProduct++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   38971   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProductInfo")
                                        mainSubProductArrayList = jobjt.getJSONArray("ProductInfoList")
                                        if (mainSubProductArrayList.length() > 0) {

                                            Log.e(TAG,"38972   mainSubProductArrayList   "+mainSubProductArrayList.length())
                                            for (i in 0 until mainSubProductArrayList.length()) {
                                                var jsonObject = mainSubProductArrayList.getJSONObject(i)
                                                Log.e(
                                                    TAG,
                                                    " 388...1  " + jsonObject.getString("Product")
                                                )

                                                //    modelServicesListDetails.clear()
                                                Log.e(TAG,"modelServicesListDetails Add   110885    ")

                                                modelServicesListDetails!!.add(
                                                    ServiceDetailsFullListModel(
                                                        "0",
                                                        jsonObject.getString("FK_Category"),
                                                        jsonObject.getString("MasterProduct"),
                                                        jsonObject.getString("FK_Product"),
                                                        jsonObject.getString("Product"),
                                                        "-2",
                                                        jsonObject.getString("BindProduct"),
                                                        jsonObject.getString("ComplaintProduct"),
                                                        jsonObject.getString("Warranty"),
                                                        jsonObject.getString("ServiceWarrantyExpireDate"),
                                                        jsonObject.getString("ReplacementWarrantyExpireDate"),
                                                        jsonObject.getString("ID_CustomerWiseProductDetails"),
                                                        jsonObject.getString("ServiceWarrantyExpired"),
                                                        jsonObject.getString("ReplacementWarrantyExpired"),
                                                        "0",
                                                        "",
                                                        "",
                                                        false,
                                                        "0",
                                                        ID_Category,edtCategory!!.text.toString(),
                                                        ID_SubCategory!!,edtSubCategory!!.text.toString(),
                                                        ID_Brand!!,edtBrand!!.text.toString(),
                                                        ID_Complaint!!,edtComplaint!!.text.toString()
                                                    )
                                                )

                                                var ServiceAttendedListDet =
                                                    jsonObject.getJSONArray("ProductInfoListDet")
                                                Log.e(TAG,"38973   ProductInfoListDet   "+ServiceAttendedListDet.length())

                                                for (j in 0 until ServiceAttendedListDet.length()) {
                                                    var jsonObjectSub =
                                                        ServiceAttendedListDet.getJSONObject(j)
                                                    Log.e(
                                                        TAG,
                                                        " 388...2  " + jsonObjectSub.getString("Product")
                                                    )


                                                    Log.e(TAG,"modelServicesListDetails Add   110886    ")
                                                    modelServicesListDetails!!.add(
                                                        ServiceDetailsFullListModel(
                                                            "1",
                                                            jsonObjectSub.getString("FK_Category"),
                                                            jsonObjectSub.getString("MasterProduct"),
                                                            jsonObjectSub.getString("FK_Product"),
                                                            jsonObjectSub.getString("Product"),
                                                            jsonObjectSub.getString("SNo"),
                                                            jsonObjectSub.getString("BindProduct"),
                                                            jsonObjectSub.getString("ComplaintProduct"),
                                                            jsonObjectSub.getString("Warranty"),
                                                            jsonObjectSub.getString("ServiceWarrantyExpireDate"),
                                                            jsonObjectSub.getString("ReplacementWarrantyExpireDate"),
                                                            jsonObjectSub.getString("ID_CustomerWiseProductDetails"),
                                                            jsonObjectSub.getString("ServiceWarrantyExpired"),
                                                            jsonObjectSub.getString("ReplacementWarrantyExpired"),
                                                            "0",
                                                            "",
                                                            "",
                                                            false,
                                                            "0",
                                                            ID_Category,edtCategory!!.text.toString(),
                                                            ID_SubCategory!!,edtSubCategory!!.text.toString(),
                                                            ID_Brand!!,edtBrand!!.text.toString(),
                                                            ID_Complaint!!,edtComplaint!!.text.toString()
                                                        )
                                                    )


                                                }

                                            }

                                            val lLayout =
                                                GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                                            rcyler_followup!!.layoutManager =
                                                lLayout as RecyclerView.LayoutManager?
                                            servDetadapter = ServiceDetailsAdapter(
                                                this@ServiceFollowUPActiivty,
                                                modelServicesListDetails
                                            )
                                            rcyler_followup!!.adapter = servDetadapter
                                            servDetadapter!!.setClickListener(this@ServiceFollowUPActiivty)
                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getFollowupType() {
//         var followUpType = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpTypeViewModel.getFollowupType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (followUpType == 0) {
                                    followUpType++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                        followUpTypeArrayList =
                                            jobjt.getJSONArray("FollowUpTypeDetailsList")
                                        if (followUpTypeArrayList.length() > 0) {
//                                             if (followUpType == 0){
//                                                 followUpType++
                                            followupTypePopup(followUpTypeArrayList)
//                                             }

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun followupTypePopup(followUpTypeArrayList: JSONArray) {

        try {

            dialogFollowupType = Dialog(this)
            dialogFollowupType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupType!!.setContentView(R.layout.followup_type_popup)
            dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupType =
                dialogFollowupType!!.findViewById(R.id.recyFollowupType) as RecyclerView
            val etsearch = dialogFollowupType!!.findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogFollowupType!! .findViewById(R.id.txt_nodata) as TextView

            txt_nodata.text = "Invalid Action Type"

            followUpTypeSort = JSONArray()
            for (k in 0 until followUpTypeArrayList.length()) {
                val jsonObject = followUpTypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpTypeSort.put(jsonObject)
            }

            if (followUpTypeSort.length() <= 0){
                recyFollowupType!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }

            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = FollowupTypeAdapter(this@ServiceFollowUPActiivty, followUpTypeArrayList)
            val adapter = FollowupTypeAdapter(this@ServiceFollowUPActiivty, followUpTypeSort)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    followUpTypeSort = JSONArray()

                    for (k in 0 until followUpTypeArrayList.length()) {
                        val jsonObject = followUpTypeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ActnTypeName").length) {
                            if (jsonObject.getString("ActnTypeName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                followUpTypeSort.put(jsonObject)
                            }

                        }
                    }

                    if (followUpTypeSort.length() <= 0){
                        recyFollowupType!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyFollowupType!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG, "followUpTypeSort               7103    " + followUpTypeSort)
                    val adapter = FollowupTypeAdapter(this@ServiceFollowUPActiivty, followUpTypeSort)
                    recyFollowupType!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUPActiivty)
                }
            })

            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getEmployee() {
//         var employee = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeViewModel.getEmployee(this, "0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (employee == 0) {
                                    employee++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeArrayList =
                                            jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeArrayList.length() > 0) {

                                            employeePopup(employeeArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun employeePopup(employeeArrayList: JSONArray) {
        try {

            dialogEmployee = Dialog(this)
            dialogEmployee!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployee!!.setContentView(R.layout.employee_popup)
            dialogEmployee!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployee = dialogEmployee!!.findViewById(R.id.recyEmployee) as RecyclerView
            val etsearch = dialogEmployee!!.findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogEmployee!! .findViewById(R.id.txt_nodata) as TextView

            txt_nodata.text = "Invalid Employee"


            employeeSort = JSONArray()
            for (k in 0 until employeeArrayList.length()) {
                val jsonObject = employeeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeSort.put(jsonObject)
            }

            if (employeeSort.length() <= 0){
                recyEmployee!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }

            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@ServiceFollowUPActiivty, employeeArrayList)
            val adapter = EmployeeAdapter(this@ServiceFollowUPActiivty, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    employeeSort = JSONArray()

                    for (k in 0 until employeeArrayList.length()) {
                        val jsonObject = employeeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                employeeSort.put(jsonObject)
                            }

                        }
                    }

                    if (employeeSort.length() <= 0){
                        recyEmployee!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyEmployee!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG, "employeeSort               7103    " + employeeSort)
                    val adapter = EmployeeAdapter(this@ServiceFollowUPActiivty, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUPActiivty)
                }
            })

            dialogEmployee!!.show()
            dialogEmployee!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getComponantList() {

        var TransMode = "CUSF"
        var ReqMode = "13"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                servCompanantViewModel.getServCompanant(this,TransMode,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (compnantMode == 0) {
                                    compnantMode++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg  5801121   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        Log.e(TAG,"jObject  5801122   "+jObject)
                                        val jobjt = jObject.getJSONObject("ProductListDetails")
                                        servCompanantArray = jobjt.getJSONArray("ProductSearchList")
                                        Log.e(TAG,"2495    "+servCompanantArray)

                                        if (servCompanantArray.length()>0){
                                            serviceCompnantListPop(servCompanantArray)
                                        }
                                    }
                                }
                            } else {

                            }
                        } catch (e: Exception) {
//                            Toast.makeText(
//                                applicationContext,
//                                "" + Config.SOME_TECHNICAL_ISSUES,
//                                Toast.LENGTH_LONG
//                            ).show()
                        }

                    })
                //  progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun serviceCompnantListPop(servCompanantArray: JSONArray) {

        try {

            dialogAddserviceSheet = Dialog(this)
            dialogAddserviceSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogAddserviceSheet!! .setContentView(R.layout.servicelist_popup)
            dialogAddserviceSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            dialogAddserviceSheet!!.setCancelable(false)

            var recycAdpService = dialogAddserviceSheet!! .findViewById(R.id.recycAdpService) as RecyclerView
            var tv_productList = dialogAddserviceSheet!! .findViewById(R.id.tv_productList) as TextView
            var tv_cancel = dialogAddserviceSheet!! .findViewById(R.id.tv_cancel) as TextView
            var tv_apply = dialogAddserviceSheet!! .findViewById(R.id.tv_apply) as TextView
            var tv_bar = dialogAddserviceSheet!! .findViewById(R.id.tv_bar) as TextView

            tv_productList.setText("Product List")
            tv_apply.visibility = View.GONE
            tv_bar.visibility = View.GONE

            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recycAdpService!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = ServiceCompanantAdapter(this@ServiceFollowUPActiivty, servCompanantArray)
            recycAdpService!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)

//            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
//            recycAdpService!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            val adapter = ServiceDetailAdapter(this@ServiceFollowUPActiivty, addServiceDetailMode!!)
//            recycAdpService!!.adapter = adapter
//            adapter.setClickListener(this@ServiceFollowUPActiivty)

            val window: Window? = dialogAddserviceSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.white);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            tv_cancel!!.setOnClickListener {
                dialogAddserviceSheet!!.dismiss()
            }

            tv_apply!!.setOnClickListener {
                var hasId =  hasServiceTrue(addServiceDetailMode!!)
                if (hasId){

//                    var hasIds1 = hasServiceTrue1(addServiceDetailMode!!)
//
//                    if (hasIds1){
//                        Log.e(TAG,"1308   "+"True")
//
//                    }else{
//                        Log.e(TAG,"1308   "+"Fasle")
//                    }

                    var posAdd = 0
                    for (i in 0 until serviceTab3MainModel.size) {
                        var empModel = serviceTab3MainModel[i]
                        if (empModel.FK_Product.equals(FK_Product_ID)){
                            posAdd = i+1
                        }
                    }

                    for (i in 0 until addServiceDetailMode.size) {
                        if (addServiceDetailMode[i].isChecked){
                            Log.e(TAG,"10492   "+FK_Product_Pos)
                            var empModel = serviceTab3MainModel[FK_Product_Pos!!]
                            var empModelSub = addServiceDetailMode[i]

                            Log.e(TAG,"117111   "+addServiceDetailMode[i].Service)
                            serviceTab3MainModel!!.add(posAdd,ServiceTab3MainModel(empModel.FK_Product,empModel.Product,"1","1",
                                empModelSub.ID_Services,empModelSub.Service,"0","","0.00","0.00",
                                "0.00","",false,empModelSub.FK_TaxGroup,empModelSub.TaxPercentage,empModelSub.ServiceChargeIncludeTax,
                                ID_Category,ID_SubCategory!!,ID_Brand!!,ID_Complaint!!))
                            serviceTab3Adapter!!.notifyItemInserted(posAdd)
                            posAdd++

                        }
                    }
                    dialogAddserviceSheet!!.dismiss()



                }



//                rcyler_service3!!.adapter = serviceTab3Adapter

            }

            dialogAddserviceSheet!!.show()

        }catch (e : Exception){

        }

    }

    private fun loadServiceType() {
        val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        var ID_Branch =  FK_BranchSP.getString("FK_Branch", "0")
        var ID_Employee = FK_EmployeeSP.getString("FK_Employee", "0")
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(this, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                serviceFollowUpServiceTypeViewModel.getServiceFollowUpServiceType(
                    this,
                    "0",
                    ID_Branch!!,
                    ID_Employee!!
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (serviceFollowUpServiceType == 0) {
                                    serviceFollowUpServiceType++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg  580   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt =
                                            jObject.getJSONObject("ServiceType")
                                        jsonArrayServiceType =
                                            jobjt.getJSONArray("ServiceTypeList")

                                        if (jsonArrayServiceType.length()>0){
                                            serviceTypePop(jsonArrayServiceType)
                                        }
                                    }
                                }
                            } else {

                            }
                        } catch (e: Exception) {
//                            Toast.makeText(
//                                applicationContext,
//                                "" + Config.SOME_TECHNICAL_ISSUES,
//                                Toast.LENGTH_LONG
//                            ).show()
                        }

                    })
                //  progressDialog!!.dismiss()
            }
            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun serviceTypePop(jsonArrayServiceType: JSONArray) {

        try {
            dialogServType = Dialog(context)
            dialogServType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogServType!!.setContentView(R.layout.pop_service_type)
            dialogServType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val recycServiceType = dialogServType!!.findViewById(R.id.recycServiceType) as RecyclerView

            val lLayout = GridLayoutManager(context, 1)
            recycServiceType!!.layoutManager = lLayout as RecyclerView.LayoutManager?

            val adapter = ServiceTypeAdapter(context, jsonArrayServiceType)
            recycServiceType!!.adapter = adapter
            adapter!!.setClickListener(this@ServiceFollowUPActiivty)

            dialogServType!!.show()
            dialogServType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogServType!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }catch (e: Exception){

        }

    }


    private fun loadServiceList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                addServiceViewModel.getAddService(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                Log.e("10701    ", "msg")
                                if (addserviceMode == 0) {
                                    addserviceMode++
                                    val jObject = JSONObject(msg)
                                    Log.e("10702    ", ""+jObject)
                                    Log.e(TAG, "st code " + jObject.getString("StatusCode"))
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AddedService")
                                        addserviceArrayList= jobjt.getJSONArray("AddedServiceList")
                                        addServiceDetailMode.clear()
                                        if (addserviceArrayList.length() > 0){
                                            for (i in 0 until addserviceArrayList.length()) {
                                                var  jsonObject = addserviceArrayList.getJSONObject(i)
                                                addServiceDetailMode.add(AddServiceDetailMode(jsonObject.getString("ID_Services"),jsonObject.getString("Service"),
                                                    jsonObject.getString("FK_TaxGroup"),jsonObject.getString("TaxPercentage"),(jsonObject.getString("ServiceChargeIncludeTax").toBoolean()),false))
                                            }

                                        }

                                        if (addServiceDetailMode.size > 0){
                                            addServicePopup()
                                        }

                                    } else {

                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->

                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }

                                }


                            } else {
                                //  swipeRefreshLayout.isRefreshing = false
                            }
                        } catch (e: Exception) {

                            Log.v("fsfsfds", "ex3 " + e)
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun addServicePopup() {
        try {

            dialogAddserviceSheet = Dialog(this)
            dialogAddserviceSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogAddserviceSheet!! .setContentView(R.layout.servicelist_popup)
            dialogAddserviceSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            dialogAddserviceSheet!!.setCancelable(false)

            var recycAdpService = dialogAddserviceSheet!! .findViewById(R.id.recycAdpService) as RecyclerView
            var tv_cancel = dialogAddserviceSheet!! .findViewById(R.id.tv_cancel) as TextView
            var tv_apply = dialogAddserviceSheet!! .findViewById(R.id.tv_apply) as TextView

            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recycAdpService!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ServiceDetailAdapter(this@ServiceFollowUPActiivty, addServiceDetailMode!!)
            recycAdpService!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)

            val window: Window? = dialogAddserviceSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            tv_cancel!!.setOnClickListener {
                dialogAddserviceSheet!!.dismiss()
            }

            tv_apply!!.setOnClickListener {
                var hasId =  hasServiceTrue(addServiceDetailMode!!)

                if (hasId){
                    var hasId2 =  hasServiceCompTrue(serviceTab3MainModel!!)
                    Log.e(TAG,"3645    hasId2   :  "+hasId2)
//                    var hasIds1 = hasServiceTrue1(addServiceDetailMode!!)
//
//                    if (hasIds1){
//                        Log.e(TAG,"1308   "+"True")
//
//                    }else{
//                        Log.e(TAG,"1308   "+"Fasle")
//                    }

                    if (hasId2){
                        var posAdd = 0
                        for (i in 0 until serviceTab3MainModel.size) {
                            var empModel = serviceTab3MainModel[i]
                            if (empModel.FK_Product.equals(FK_Product_ID)){
                                posAdd = i+1
                            }
                        }

                        for (i in 0 until addServiceDetailMode.size) {
                            if (addServiceDetailMode[i].isChecked){
                                Log.e(TAG,"10492   "+FK_Product_Pos)
                                var empModel = serviceTab3MainModel[FK_Product_Pos!!]
                                var empModelSub = addServiceDetailMode[i]

                                Log.e(TAG,"117111   "+addServiceDetailMode[i].Service)
                                serviceTab3MainModel!!.add(posAdd,ServiceTab3MainModel(empModel.FK_Product,empModel.Product,"1","1",
                                    empModelSub.ID_Services,empModelSub.Service,"0","","0.00","0.00",
                                    "0.00","",false,empModelSub.FK_TaxGroup,empModelSub.TaxPercentage,empModelSub.ServiceChargeIncludeTax,
                                    ID_Category,ID_SubCategory!!,ID_Brand!!,ID_Complaint!!))
                                serviceTab3Adapter!!.notifyItemInserted(posAdd)
                                posAdd++

                            }
                        }
                        dialogAddserviceSheet!!.dismiss()
                    }else{
                        Config.showCustomToast(strActiontakenStatusMessage+" Already exist",context)
                       // Toast.makeText(applicationContext,strActiontakenStatusMessage+" Already exist",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Config.showCustomToast("Select atleast one companant",context)
                  //  Toast.makeText(applicationContext,"Select atleast one companant",Toast.LENGTH_SHORT).show()
                }



//                rcyler_service3!!.adapter = serviceTab3Adapter

            }

            dialogAddserviceSheet!!.show()

        }catch (e : Exception){

        }

    }



    private fun hasServiceTrue(addServiceDetailMode: ArrayList<AddServiceDetailMode>): Boolean {

        var isChecked = false
        for (i in 0 until addServiceDetailMode.size) {  // iterate through the JsonArray
            Log.e(TAG,"101666     "+addServiceDetailMode.get(i).isChecked)

            if (addServiceDetailMode.get(i).isChecked){
                isChecked = true
            }

        }
        return isChecked
    }

    private fun hasServiceCompTrue(serviceTab3MainModel: ArrayList<ServiceTab3MainModel>): Boolean {
        var isChecked = true
        strActiontakenStatusMessage = ""
        for (i in 0 until serviceTab3MainModel.size) {  // iterate through the JsonArray
            Log.e(TAG,"101666     "+serviceTab3MainModel.get(i).isChecked)
            for (j in 0 until addServiceDetailMode.size) {

                if (addServiceDetailMode.get(j).isChecked && (addServiceDetailMode.get(j).ID_Services.equals(serviceTab3MainModel.get(i).ID_Service)) &&
                    serviceTab3MainModel.get(i).FK_Product.equals(FK_Product_ID)){
                    strActiontakenStatusMessage = addServiceDetailMode.get(j).Service
                    isChecked = false
                    break

                }
            }

        }
        return isChecked
    }

    private fun hasServiceTrue1(addServiceDetailMode: ArrayList<AddServiceDetailMode>): Boolean {

        var isChecked = true
        for (i in 0 until serviceTab3MainModel.size) {  // iterate through the JsonArray
            Log.e(TAG,"12344    "+FK_Product_ID)
            Log.e(TAG,"12344    "+serviceTab3MainModel.get(i).FK_Product)
            for (j in 0 until addServiceDetailMode.size) {
                if (serviceTab3MainModel.get(i).FK_Product.equals(FK_Product_ID) && serviceTab3MainModel.get(i).ID_Service.equals(addServiceDetailMode.get(j).ID_Services)){
                    isChecked = false
                }
            }

        }
        return isChecked
    }



    private fun valiadateServiceAttended() {


        var hasId =  hasTrue(modelServicesListDetails!!)
        Log.e(TAG,"10166611     "+hasId)
        Log.e(TAG,"101666112   PSValue  "+PSValue)

        if (PSValue.equals("1")){
            if (hasId){
                Log.e(TAG,"10000666666   Checked Box MArked")
//            ll_service_Attended!!.visibility = View.GONE
//            ll_Service3!!.visibility = View.VISIBLE

                modeTab = modeTab+1
                Log.e(TAG,"10166612     modeTab "+modeTab)
                loadlayout()

                // 24-10-2023 Ranjith
                serviceTab3MainModel.clear()
                Log.e(TAG,"1034441     "+modelServicesListDetails.size)
                Log.e(TAG,"1034442     "+serviceTab3MainModel.size)
                for (i in 0 until modelServicesListDetails.size) {

                    var empModel = modelServicesListDetails[i]
                    Log.e(TAG,"1034442     "+empModel.isChekecd+"   :   "+empModel.Product)
                    if (empModel.isChekecd){
                        serviceTab3MainModel!!.add(ServiceTab3MainModel(empModel.FK_Product,empModel.Product,"1","",
                            "0","","0","","0.00","0.00","0.00","",false,"0","0",false,
                            ID_Category,ID_SubCategory!!,ID_Brand!!,ID_Complaint!!))

//                    ID_Category,edtCategory!!.text.toString(),ID_SubCategory!!,edtSubCategory!!.text.toString(),ID_Brand!!,edtCategory!!.text.toString(),ID_Complaint!!,edtCategory!!.text.toString()
                    }

                }

                serviceDetailsArray = JSONArray()
                ProductSubDetails  = JSONArray()

                Log.e(TAG,"modelServicesListDetails    5156661   "+modelServicesListDetails.size)
                for (i in 0 until modelServicesListDetails.size) {
                    if (modelServicesListDetails[i].isChekecd){
                        Log.e(TAG,"1416661  FK_Product                    "+modelServicesListDetails[i].FK_Product)
                        Log.e(TAG,"1416662  Product                       "+modelServicesListDetails[i].Product)
                        Log.e(TAG,"1416663  ID_ComplaintList              "+modelServicesListDetails[i].ID_ComplaintList)
                        Log.e(TAG,"1416664  ID_CustomerWiseProductDetails "+modelServicesListDetails[i].ID_CustomerWiseProductDetails)
                        Log.e(TAG,"1416664  ReplacementWarrantyExpireDate "+modelServicesListDetails[i].ReplacementWarrantyExpireDate)




                        val jObject = JSONObject()
                        val jObject1 = JSONObject()
                        jObject.put("ID_Product", (modelServicesListDetails[i].FK_Product))
                        jObject.put("Product", (modelServicesListDetails[i].Product))
                        jObject.put("ID_ComplaintList", (modelServicesListDetails[i].ID_ComplaintList))
                        jObject.put("Description", (modelServicesListDetails[i].Description))
                        jObject.put("ID_CustomerWiseProductDetails", (modelServicesListDetails[i].ID_CustomerWiseProductDetails))
                        jObject.put("FK_ProductNumberingDetails", ("0"))
                        jObject.put("FK_SubCategory",modelServicesListDetails[i].ID_SubCategory)
                        jObject.put("FK_Brand",modelServicesListDetails[i].ID_Brand)
                        jObject.put("FK_Category",modelServicesListDetails[i].ID_Category)



                        jObject1.put("FK_Product",modelServicesListDetails[i].FK_Product)
                        if (modelServicesListDetails[i].ReplacementWarrantyExpireDate.equals("")){
                            jObject1.put("ReplacementWarrantyExpireDate",modelServicesListDetails[i].ReplacementWarrantyExpireDate)
                        }else{
                            var date = Config.convertDate(modelServicesListDetails[i].ReplacementWarrantyExpireDate)
                            jObject1.put("ReplacementWarrantyExpireDate",date)
                        }
                        jObject1.put("ID_CustomerWiseProductDetails",modelServicesListDetails[i].ID_CustomerWiseProductDetails)


                        serviceDetailsArray.put(jObject)

                        ProductSubDetails.put(jObject1)
                    }
                }

                Log.e(TAG,"1034443     "+serviceTab3MainModel.size)
                if (serviceTab3MainModel.size > 0){
                    val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                    rcyler_service3!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                    serviceTab3Adapter = ServiceTab3Adapter(this@ServiceFollowUPActiivty, serviceTab3MainModel)
                    rcyler_service3!!.adapter = serviceTab3Adapter
                    serviceTab3Adapter!!.setClickListener(this@ServiceFollowUPActiivty)

                }

                loadtab2()
                // 24-10-2023 Ranjith
            }else{
                Log.e(TAG,"10000666666   No Checked Box MArked")
                Config.showCustomToast("No Check box marked",context)
                //Toast.makeText(applicationContext,"No Check box marked",Toast.LENGTH_SHORT).show()
            }

        }else{


            if (ID_Category.equals("")){
                Config.showCustomToast("Select Category",context)
            }else if (ID_Complaint.equals("")){
                Config.showCustomToast("Select Complaint",context)
            }else{
                modeTab = modeTab+1
                Log.e(TAG,"101666131111     modeTab "+modeTab)
                loadlayout()



                Log.e(TAG,"modelServicesListDetails    5156662   "+modelServicesListDetails.size)
                serviceTab3MainModel.clear()

                serviceTab3MainModel!!.add(ServiceTab3MainModel("",edtCategory!!.text.toString(),"1","",
                    "0","","0","","0.00","0.00","0.00","",false,"0","0",false,
                    ID_Category,ID_SubCategory!!,ID_Brand!!,ID_Complaint!!))




                serviceDetailsArray = JSONArray()
                ProductSubDetails  = JSONArray()
//            for (i in 0 until modelServicesListDetails.size) {
//                if (modelServicesListDetails[i].isChekecd){


                val jObject = JSONObject()
                val jObject1 = JSONObject()
                jObject.put("ID_Product", "")
                jObject.put("Product", "")
                jObject.put("ID_ComplaintList",ID_Complaint)
                jObject.put("Description","")
                jObject.put("ID_CustomerWiseProductDetails", "")
                jObject.put("FK_ProductNumberingDetails", ("0"))
                jObject.put("FK_SubCategory",ID_SubCategory)
                jObject.put("FK_Brand",ID_Brand)
                jObject.put("FK_Category",ID_Category)

                jObject1.put("ReplacementWarrantyExpireDate","")
                jObject1.put("ID_CustomerWiseProductDetails","")


                serviceDetailsArray.put(jObject)

                ProductSubDetails.put(jObject1)
//                }
//            }

                Log.e(TAG,"5197773     "+serviceTab3MainModel.size)
                if (serviceTab3MainModel.size > 0){
                    val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                    rcyler_service3!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                    serviceTab3Adapter = ServiceTab3Adapter(this@ServiceFollowUPActiivty, serviceTab3MainModel)
                    rcyler_service3!!.adapter = serviceTab3Adapter
                    serviceTab3Adapter!!.setClickListener(this@ServiceFollowUPActiivty)

                }


                loadtab2()
            }

        }



    }

    private fun loadtab2() {

        // Part Replaced

        if (PSValue.equals("1")){
            Log.e(TAG,"276661   "+ProductSubDetails)
            mainProductInfo = 0
            loadMainProduct()
//        valiadateServiceAttended()
        }else{
            Log.e(TAG,"276662   "+ProductSubDetails)
            servicepartsReplacedModel.clear()
            labelpartsreplaceModel.clear()

            servicepartsReplacedModel!!.add(ServicePartsReplacedModel("1","0","",edtCategory!!.text.toString(),
                "","","","","",
                "","","","","0","",
                ID_SubCategory!!,ID_Brand!!,ID_Category))

            for (i in 0 until servicepartsReplacedModel.size) {
                var empModel = servicepartsReplacedModel[i]
                Log.e(TAG,"2989322  "+"   P:  "+i+"    "+empModel.MainProduct+"    M: "+empModel.is_Master+"  S:   "+empModel.is_Sub+"    C: "+empModel.Componant)
            }

            if (servicepartsReplacedModel.size > 0){

                val lLayout1 = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                recy_parts_replaced!!.layoutManager = lLayout1 as RecyclerView.LayoutManager?
                servicePartsAdapter = ServiceParts_replacedAdapter(this@ServiceFollowUPActiivty, servicepartsReplacedModel!!,"0")
                recy_parts_replaced!!.adapter = servicePartsAdapter
                servicePartsAdapter!!.setClickListener(this@ServiceFollowUPActiivty)
            }

        }

    }

    private fun validateService3() {

        var hasId =  hasTrue3(serviceTab3MainModel!!)
        if (hasId){
            Log.e(TAG,"145888   Checked Box MArked")
            serviceIncentiveArray = JSONArray()
            //discountAmount = "0"
            totalServiceCost = "0"
            for (i in 0 until serviceTab3MainModel.size) {

                if (serviceTab3MainModel[i].isChecked){


                    val jObject = JSONObject()
                    jObject.put("ID_Service", (serviceTab3MainModel[i].ID_Service))
                    jObject.put("ID_MasterProduct", (serviceTab3MainModel[i].FK_Product))
                    jObject.put("ServiceMod", (serviceTab3MainModel[i].ID_ServiceType))
                    jObject.put("ServiceCost", (serviceTab3MainModel[i].ServiceCost))
                    jObject.put("ServiceTax", (serviceTab3MainModel[i].TaxAmount))
                    jObject.put("ServiceNetAmount", (serviceTab3MainModel[i].NetAmount))

                    serviceIncentiveArray.put(jObject)

                    var amount=serviceTab3MainModel[i].NetAmount.toDouble()
                    totalServiceCost =(totalServiceCost!!.toDouble()+amount).toString()

//                    discountAmount = (discountAmount!!.toFloat() +(serviceTab3MainModel[i].NetAmount).toFloat()).toString()

                }
            }
            modeTab = modeTab +1
            loadlayout()
            Log.e(TAG,"143777772     "+serviceIncentiveArray)
        }else{
            Log.e(TAG,"145888   No Checked Box MArked")
        }

    }

    private fun hasTrue3(serviceTab3MainModel: ArrayList<ServiceTab3MainModel>): Boolean {
        var isChecked = true
        for (i in 0 until serviceTab3MainModel.size) {
            if (serviceTab3MainModel.get(i).isChecked){
                isChecked = true
                if (serviceTab3MainModel.get(i).ID_ServiceType.equals("0") || serviceTab3MainModel.get(i).ID_ServiceType.equals("")){
                    isChecked = false
                    Config.showCustomToast("Select Service Type",context)
//                    Toast.makeText(applicationContext,"Select Service Type",Toast.LENGTH_SHORT).show()
                    break
                }
            }
        }
        return isChecked
    }


    private fun hasTrueMast(labelpartsreplaceModel: ArrayList<LabelPartsreplaceModel>, ID_Master: String): Boolean {
        var isChecked = true
        for (i in 0 until labelpartsreplaceModel.size) {  // iterate through the JsonArray


            if (labelpartsreplaceModel.get(i).ID_MasterProduct.equals(ID_Master)){
                isChecked = false
                break
            }

        }
        return isChecked
    }

    private fun hasTrueMast1(servicepartsReplacedModel: ArrayList<ServicePartsReplacedModel>, ID_MasterProduct: String, ID_Product: String): Boolean {
        var isChecked = true
        for (i in 0 until servicepartsReplacedModel.size) {
            //Log.e(TAG,"29893   "+)
            if (servicepartsReplacedModel.get(i).ID_MasterProduct.equals(ID_MasterProduct) && servicepartsReplacedModel.get(i).ID_Product.equals(ID_Product) && servicepartsReplacedModel.get(i).is_Sub.equals("1")){
                isChecked = false
                break
            }
        }
        return isChecked
    }

    private fun hasTrue(modelServicesListDetails: ArrayList<ServiceDetailsFullListModel>): Boolean {

        var isChecked = false
        for (i in 0 until modelServicesListDetails.size) {  // iterate through the JsonArray
            Log.e(TAG,"101666     "+modelServicesListDetails.get(i).isChekecd+"  :   "+modelServicesListDetails.get(i).ID_ComplaintList)

            if (modelServicesListDetails.get(i).isChekecd){
                isChecked = true
                if (modelServicesListDetails.get(i).ID_ComplaintList.equals("0")){
                    isChecked = false
                    break
                }
            }

        }
        return isChecked
    }


    private fun loadMainProduct() {
        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val ID_Employee   = FK_EmployeeSP.getString("FK_Employee",null)
        Log.i("resp900","ID_Employee==   "+ID_Employee)

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                subProductViewModel.getSubProduct(this,ID_Employee!!,ProductSubDetails)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                Log.e("resp900","msg=="+msg)
                                Log.e(TAG,"msg "+msg)

                                if (mainProductInfo == 0) {
                                    mainProductInfo++

                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        //  SubproductDetails=jObject.getJSONObject("SubproductDetails")
                                        val jobjt = jObject.getJSONObject("SubproductDetails")
                                        SubproductDetailsList = jobjt.getJSONArray("SubproductDetailsList")
                                        Log.e(TAG, "SubproductDetailsList 29891 " + SubproductDetailsList)
//                                        Log.e(TAG,"SubproductDetailsList==  "+SubproductDetailsList)
//                                        Log.i("resp900","SubproductDetailsList=="+SubproductDetailsList)

                                        servicepartsReplacedModel.clear()
                                        labelpartsreplaceModel.clear()

                                        for (i in 0 until SubproductDetailsList.length()) {

                                            Log.e(TAG, "position  3681112 " + i)
                                            var jsonObject = SubproductDetailsList.getJSONObject(i)
                                            val jObject = JSONObject()

//                                            ReplceModeSub = jsonObject.getString("ReplceMode")
//                                            WarrantyMode = jsonObject.getString("WarrantyMode")

                                            var master_id  =jsonObject.getString("ID_MasterProduct")

                                            var hasId =  hasTrueMast(labelpartsreplaceModel!!,jsonObject.getString("ID_MasterProduct"))
                                            if (hasId){
                                                Log.e(TAG,"29771   "+i+"  :  "+jsonObject.getString("ID_MasterProduct"))
                                                labelpartsreplaceModel!!.add(LabelPartsreplaceModel(jsonObject.getString("ID_MasterProduct"),jsonObject.getString("MainProduct")))
//                                                Changes 26.10.2023
//                                                for (k in 0 until modelServicesListDetails.size) {
//                                                    if (modelServicesListDetails[i].isChekecd && modelServicesListDetails[i].FK_Product.equals(jsonObject.getString("ID_MasterProduct"))){
                                                        servicepartsReplacedModel!!.add(ServicePartsReplacedModel("1","0",jsonObject.getString("ID_MasterProduct"),jsonObject.getString("MainProduct"),
                                                            jsonObject.getString("ID_Product"),jsonObject.getString("Componant"),"",jsonObject.getString("WarrantyMode"),"",
                                                            jsonObject.getString("ProductAmount"),jsonObject.getString("ReplceMode"),"",jsonObject.getString("FK_Stock"),"0",jsonObject.getString("ID_CustomerWiseProductDetails"),
                                                            ID_SubCategory!!,ID_Brand!!,ID_Category))
//                                                    }
//                                                }


//                                                Changes 26.10.2023
                                            }

                                            for (j in 0 until SubproductDetailsList.length()) {
                                                var jsonObject1 = SubproductDetailsList.getJSONObject(j)

//                                                ReplceModeSub = jsonObject1.getString("ReplceMode")
//                                                WarrantyMode = jsonObject1.getString("WarrantyMode")

                                                if(jsonObject1.getString("ID_MasterProduct").equals(master_id)){
                                                    Log.e(TAG,"29892  "+"    M: "+jsonObject1.getString("ID_MasterProduct")+"    S: "+jsonObject1.getString("ID_Product"))
                                                    var hasId1 =  hasTrueMast1(servicepartsReplacedModel!!,jsonObject1.getString("ID_MasterProduct"),jsonObject1.getString("ID_Product"))
                                                    if (hasId1){
                                                        Log.e(TAG,"29772   "+i+"  :  "+j+"  "+jsonObject1.getString("ID_MasterProduct"))
//                                                        for (k in 0 until modelServicesListDetails.size) {
//                                                            if (modelServicesListDetails[i].isChekecd && modelServicesListDetails[i].FK_Product.equals(jsonObject.getString("ID_MasterProduct"))){
                                                                servicepartsReplacedModel!!.add(ServicePartsReplacedModel("0","1",jsonObject1.getString("ID_MasterProduct"),jsonObject1.getString("MainProduct"),
                                                                    jsonObject1.getString("ID_Product"),jsonObject1.getString("Componant"),"",jsonObject1.getString("WarrantyMode"),"",
                                                                    jsonObject1.getString("ProductAmount"),jsonObject1.getString("ReplceMode"),"",jsonObject1.getString("FK_Stock"),"0",jsonObject1.getString("ID_CustomerWiseProductDetails"),
                                                                    ID_SubCategory!!,ID_Brand!!,ID_Category))
//                                                        modelServicesListDetails[k].ID_CustomerWiseProductDetails
//                                                            }
//                                                        }

                                                    }
                                                }

                                            }
//                                            ReplceMode = jsonObject.getString("ReplceMode")
//                                            Log.e(TAG, "ReplceMode " + ReplceMode)



//                                            servicepartsReplacedModel!!.add(ServicePartsReplacedModel("0",jsonObject.getString("ID_MasterProduct"),jsonObject.getString("MainProduct"),
//                                                jsonObject.getString("ID_Product"),jsonObject.getString("Componant"),"",jsonObject.getString("WarrantyMode"),"",
//                                                jsonObject.getString("ProductAmount"),jsonObject.getString("ReplceMode"),"",jsonObject.getString("FK_Stock"),"0",))

                                            Log.e(TAG, "servicepartsReplacedModel " + servicepartsReplacedModel)

                                        }


                                        for (i in 0 until servicepartsReplacedModel.size) {
                                            var empModel = servicepartsReplacedModel[i]
                                            Log.e(TAG,"29893  "+"   P:  "+i+"    "+empModel.MainProduct+"    M: "+empModel.is_Master+"  S:   "+empModel.is_Sub+"    C: "+empModel.Componant)
                                        }

//                                        for (i in 0 until servicepartsReplacedModel.size) {
//                                            var posAdd = 0
//                                            Log.e(TAG,"29891  "+servicepartsReplacedModel.size)
//                                            var empModel = servicepartsReplacedModel[i]
//                                            if (empModel.is_Master.equals("1") && empModel.is_Sub.equals("0")){
//                                                Log.e(TAG,"29892  "+empModel.MainProduct)
//                                                posAdd++
//                                            }
//                                            for (i in 0 until SubproductDetailsList.length()) {
//                                                var jsonObject = SubproductDetailsList.getJSONObject(i)
//                                                servicepartsReplacedModel!!.add(ServicePartsReplacedModel("1","1",jsonObject.getString("ID_MasterProduct"),jsonObject.getString("MainProduct"),
//                                                    jsonObject.getString("ID_Product"),jsonObject.getString("Componant"),"",jsonObject.getString("WarrantyMode"),"",
//                                                    jsonObject.getString("ProductAmount"),jsonObject.getString("ReplceMode"),"",jsonObject.getString("FK_Stock"),"0",))
//                                                posAdd++
//                                            }
//                                        }



//                                        val lLayout = LinearLayoutManager(this@ServiceFollowUPActiivty, LinearLayoutManager.HORIZONTAL, false)
//                                        recy_main_product_topbar!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        val adapter = ServiceFollowUpMainProductAdapter(this@ServiceFollowUPActiivty, labelpartsreplaceModel!!)
//                                        recy_main_product_topbar!!.adapter = adapter
//                                        adapter.setClickListener(this@ServiceFollowUPActiivty)

                                        if (servicepartsReplacedModel.size > 0){
                                            var Id = labelpartsreplaceModel.get(0).ID_MasterProduct
                                            val lLayout1 = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
                                            recy_parts_replaced!!.layoutManager = lLayout1 as RecyclerView.LayoutManager?
                                            servicePartsAdapter = ServiceParts_replacedAdapter(this@ServiceFollowUPActiivty, servicepartsReplacedModel!!,Id)
                                            recy_parts_replaced!!.adapter = servicePartsAdapter
                                            servicePartsAdapter!!.setClickListener(this@ServiceFollowUPActiivty)
                                        }


                                    }
                                    else
                                    {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->

                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }

                                }


                            } else {

                            }
                        } catch (e: Exception) {

                            Log.e(TAG, "888888888555    " + e)
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })

                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }




    private fun getReplaceMode() {
        Log.i("resp900","ID_Employee==   ")

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceReplacedModeViewModel.getServiceReplacedModeViewModel(this,ReplceModeSub!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                Log.i("resp900","msg=="+msg)
                                Log.e(TAG,"msg "+msg)

                                if (replacedcount == 0) {
                                    replacedcount++

                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        //  SubproductDetails=jObject.getJSONObject("SubproductDetails")
                                        val jobjt = jObject.getJSONObject("ServiceType")
                                        ReplaceArrayList = jobjt.getJSONArray("ServiceTypeList")

                                        if (ReplaceArrayList.length() > 0) {

                                            replaceModePopup(ReplaceArrayList)
                                        }

                                    }
                                    else
                                    {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->

                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }

                                }


                            } else {

                            }
                        } catch (e: Exception) {

                            Log.e(TAG, "888888888555    " + e)
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })

                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }


    private fun replaceModePopup(ReplaceArrayList: JSONArray) {
        Log.e(TAG,"ReplaceArrayList864  "+ReplaceArrayList)
        try {
            dialogReplacedMode = Dialog(this)
            dialogReplacedMode!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogReplacedMode!! .setContentView(R.layout.replcede_mode_popup)
            dialogReplacedMode!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogReplacedMode!!.setCancelable(true)

            recyreplacedmode = dialogReplacedMode!! .findViewById(R.id.recyreplacedmode) as RecyclerView
//            var tv_cancel = dialogReplacedMode!! .findViewById(R.id.tv_cancel) as TextView
//            var tv_submit = dialogReplacedMode!! .findViewById(R.id.tv_submit) as TextView

            val window: Window? = dialogReplacedMode!!.getWindow()
//            window!!.setBackgroundDrawableResource(android.R.color.transparent);
//            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyreplacedmode!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            replacemodeAdapter = ServiceReplacedModeAdaper(this@ServiceFollowUPActiivty,ReplaceArrayList)
            recyreplacedmode!!.adapter = replacemodeAdapter
            replacemodeAdapter!!.setClickListener(this@ServiceFollowUPActiivty)

//            tv_cancel!!.setOnClickListener {
//                selectMoreServiceAttended = JSONArray()
//                dialogReplacedMode!!.dismiss()
//            }
//            tv_submit!!.setOnClickListener {
//                dialogReplacedMode!!.dismiss()
//                Log.e(TAG,"20269  s")
//                Log.e(TAG,"610  selectMoreServiceAttended   "+selectMoreServiceAttended)
//
//                addDataServiceAttended()
//            }


            dialogReplacedMode!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getWarrantyMode() {
        Log.i("resp900","ID_Employee==   ")

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                warrantymodeViewModel.getWarrantyMode(this,WarrantyMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                Log.i("resp900","msg=="+msg)
                                Log.e(TAG,"msg 58555 "+msg)

                                if (warrantycount == 0) {
                                    warrantycount++

                                    val jObject = JSONObject(msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ServiceType")
                                        WarrantyArrayList = jobjt.getJSONArray("ServiceTypeList")

                                        if (WarrantyArrayList.length() > 0) {

                                            warrantyModePopup(WarrantyArrayList)

//                                            val jsonObject = WarrantyArrayList.getJSONObject(0)
//                                            Log.e(TAG,"95444     "+jsonObject)
//
//                                            var empModel = servicepartsReplacedModel[warrantyEditPosition]
//                                            empModel.WarrantyName = jsonObject.getString("ServiceTypeName")
                                        }


                                    }
                                    else
                                    {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->

                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }

                                }


                            } else {

                            }
                        } catch (e: Exception) {

                            Log.e(TAG, "888888888555    " + e)
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })

                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }


    private fun warrantyModePopup(WarrantyArrayList: JSONArray) {
        Log.e(TAG,"WarrantyArrayList864  "+WarrantyArrayList)
        try {
            dialogWarrantydMode = Dialog(this)
            dialogWarrantydMode!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogWarrantydMode!! .setContentView(R.layout.warranty_mode_popup)
            dialogWarrantydMode!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogWarrantydMode!!.setCancelable(true)

            recywarrantymode = dialogWarrantydMode!! .findViewById(R.id.recywarrantymode) as RecyclerView
//            var tv_cancel = dialogWarrantydMode!! .findViewById(R.id.tv_cancel) as TextView
//            var tv_submit = dialogWarrantydMode!! .findViewById(R.id.tv_submit) as TextView

            val window: Window? = dialogWarrantydMode!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
//            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recywarrantymode!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            servicewarrantyModeAdapter = ServiceWarrantyModeAdapter(this@ServiceFollowUPActiivty,WarrantyArrayList)
            recywarrantymode!!.adapter = servicewarrantyModeAdapter
            servicewarrantyModeAdapter!!.setClickListener(this@ServiceFollowUPActiivty)

//            tv_cancel!!.setOnClickListener {
//                selectMoreServiceAttended = JSONArray()
//                dialogWarrantydMode!!.dismiss()
//            }
//            tv_submit!!.setOnClickListener {
//                dialogWarrantydMode!!.dismiss()
//                Log.e(TAG,"20269  s")
//                Log.e(TAG,"610  selectMoreServiceAttended   "+selectMoreServiceAttended)
//
//                addDataServiceAttended()
//            }

            dialogWarrantydMode!!.show()
            // dialogDetailSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        if (modeTab == 0){
            finish()
        }else{
            modeTab = modeTab-1
            loadlayout()
        }
    }



    private fun getCategory(ReqMode : String,ID_CompCategory : String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productCategoryViewModel.getProductCategory(this,ReqMode,ID_CompCategory)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (categoryDet == 0){
                                    categoryDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1278   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                        categoryArrayList = jobjt.getJSONArray("CategoryList")
                                        if (categoryArrayList.length()>0){

                                            categoryPopup(categoryArrayList)

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun categoryPopup(categoryArrayList: JSONArray) {


        try {

            dialogCategory = Dialog(this)
            dialogCategory!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCategory!! .setContentView(R.layout.category_popup)
            dialogCategory!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyCategory = dialogCategory!! .findViewById(R.id.recyCategory) as RecyclerView
            val etsearch = dialogCategory!! .findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogCategory!! .findViewById(R.id.txt_nodata) as TextView

            txt_nodata.text = "Invalid Category"

            categorySort = JSONArray()
            for (k in 0 until categoryArrayList.length()) {
                val jsonObject = categoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                categorySort.put(jsonObject)
            }

            if (categorySort.length() <= 0){
                recyCategory!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = CategoryAdapter(this@ServiceFollowUPActiivty, categorySort)
            recyCategory!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    categorySort = JSONArray()

                    for (k in 0 until categoryArrayList.length()) {
                        val jsonObject = categoryArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("CategoryName").length) {
                            if (jsonObject.getString("CategoryName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                categorySort.put(jsonObject)
                            }

                        }
                    }


                    if (categorySort.length() <= 0){
                        recyCategory!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyCategory!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG,"categorySort               7103    "+categorySort)
                    val adapter = CategoryAdapter(this@ServiceFollowUPActiivty, categorySort)
                    recyCategory!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUPActiivty)
                }
            })

            dialogCategory!!.show()
            dialogCategory!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }

    }

    private fun getSubCategory(ReqMode : String,ID_Category : String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                subCategoryViewModel.getSubCategory(this,ReqMode,ID_Category)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (subCategoryDet == 0){
                                    subCategoryDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   3572   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("SubCategoryDetailsList")
                                        subCategoryArrayList = jobjt.getJSONArray("SubCategoryList")
                                        if (subCategoryArrayList.length()>0){

                                            // productPriorityPopup(prodPriorityArrayList)
                                            subCategoryPopup(subCategoryArrayList)



                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    private fun subCategoryPopup(subCategoryArrayList: JSONArray) {


        try {

            dialogSubCategory = Dialog(this)
            dialogSubCategory!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogSubCategory!! .setContentView(R.layout.category_popup)
            dialogSubCategory!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyCategory = dialogSubCategory!! .findViewById(R.id.recyCategory) as RecyclerView
            val etsearch = dialogSubCategory!! .findViewById(R.id.etsearch) as EditText
            val tv_header = dialogSubCategory!! .findViewById(R.id.tv_header) as TextView
            val txt_nodata = dialogSubCategory!! .findViewById(R.id.txt_nodata) as TextView

            tv_header.setText("Sub Category")
            txt_nodata.text = "Invalid Sub Category"


            subCategorySort = JSONArray()
            for (k in 0 until subCategoryArrayList.length()) {
                val jsonObject = subCategoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                subCategorySort.put(jsonObject)
            }
            if (subCategorySort.length() <= 0){
                recyCategory!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = SubCategoryAdapter(this@ServiceFollowUPActiivty, subCategorySort)
            recyCategory!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    subCategorySort = JSONArray()

                    for (k in 0 until subCategoryArrayList.length()) {
                        val jsonObject = subCategoryArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("SubCatName").length) {
                            if (jsonObject.getString("SubCatName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                subCategorySort.put(jsonObject)
                            }

                        }
                    }

                    if (subCategorySort.length() <= 0){
                        recyCategory!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyCategory!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG,"categorySort               7103    "+subCategorySort)
                    val adapter = SubCategoryAdapter(this@ServiceFollowUPActiivty, subCategorySort)
                    recyCategory!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUPActiivty)
                }
            })

            dialogSubCategory!!.show()
            dialogSubCategory!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }

    }


    private fun getBrands(ReqMode: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                brandViewModel.getBrands(this,ReqMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (brandDet == 0){
                                    brandDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   3747   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("BrandDetails")
                                        brandArrayList = jobjt.getJSONArray("BrandDetailsList")
                                        if (brandArrayList.length()>0){

                                            // productPriorityPopup(prodPriorityArrayList)
                                            brandPopup(brandArrayList)

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun brandPopup(brandArrayList: JSONArray) {

        try {

            dialogBrand = Dialog(this)
            dialogBrand!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBrand!! .setContentView(R.layout.brand_popup)
            dialogBrand!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBrand = dialogBrand!! .findViewById(R.id.recyBrand) as RecyclerView
            val etsearch = dialogBrand!! .findViewById(R.id.etsearch) as EditText
            val tv_header = dialogBrand!! .findViewById(R.id.tv_header) as TextView
            val txt_nodata = dialogBrand!! .findViewById(R.id.txt_nodata) as TextView

            txt_nodata.text = "Invalid Brand"


            brandSort = JSONArray()
            for (k in 0 until brandArrayList.length()) {
                val jsonObject = brandArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                brandSort.put(jsonObject)
            }

            if (brandSort.length() <= 0){
                recyBrand!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }



            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyBrand!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = BrandAdapter(this@ServiceFollowUPActiivty, brandSort)
            recyBrand!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    brandSort = JSONArray()

                    for (k in 0 until brandArrayList.length()) {
                        val jsonObject = brandArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("BrandName").length) {
                            if (jsonObject.getString("BrandName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                brandSort.put(jsonObject)
                            }

                        }
                    }

                    if (brandSort.length() <= 0){
                        recyBrand!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyBrand!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG,"brandSort               7103    "+brandSort)
                    val adapter = BrandAdapter(this@ServiceFollowUPActiivty, brandSort)
                    recyBrand!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUPActiivty)
                }
            })

            dialogBrand!!.show()
            dialogBrand!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }
    }

    private fun getComplaints(ReqMode: String, SubMode: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                serviceComplaintViewModel.getserviceComplaintData(this,ReqMode!!,SubMode!!,ID_Category!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (complaintDet == 0){
                                    complaintDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1920   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ComplaintsDetails")
                                        complaintArrayList = jobjt.getJSONArray("ComplaintDetailsList")
                                        if (complaintArrayList.length()>0){

                                            complaintPopup(complaintArrayList)


                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ServiceFollowUPActiivty,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            }
                        }catch (e : Exception){
                            Toast.makeText(
                                applicationContext,
                                ""+Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun complaintPopup(complaintArrayList: JSONArray) {

        try {

            dialogComplaint = Dialog(this)
            dialogComplaint!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogComplaint!! .setContentView(R.layout.service_complaint_popup)
            dialogComplaint!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyComplaint = dialogComplaint!! .findViewById(R.id.recyComplaint) as RecyclerView
            val etsearch = dialogComplaint!! .findViewById(R.id.etsearch) as EditText
            val txt_nodata = dialogComplaint    !! .findViewById(R.id.txt_nodata) as TextView

            txt_nodata.text = "Invalid Complaint"

            complaintSort = JSONArray()
            for (k in 0 until complaintArrayList.length()) {
                val jsonObject = complaintArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                complaintSort.put(jsonObject)
            }

            if (complaintSort.length() <= 0){
                recyComplaint!!.visibility = View.GONE
                txt_nodata!!.visibility = View.VISIBLE
            }


            val lLayout = GridLayoutManager(this@ServiceFollowUPActiivty, 1)
            recyComplaint!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = ProductPriorityAdapter(this@FollowUpActivity, prodPriorityArrayList)
            val adapter = ServiceComplaintAdapter(this@ServiceFollowUPActiivty, complaintSort)
            recyComplaint!!.adapter = adapter
            adapter.setClickListener(this@ServiceFollowUPActiivty)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    complaintSort = JSONArray()

                    for (k in 0 until complaintArrayList.length()) {
                        val jsonObject = complaintArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ComplaintName").length) {
                            if (jsonObject.getString("ComplaintName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                complaintSort.put(jsonObject)
                            }

                        }
                    }

                    if (complaintSort.length() <= 0){
                        recyComplaint!!.visibility = View.GONE
                        txt_nodata!!.visibility = View.VISIBLE
                    }else{
                        recyComplaint!!.visibility = View.VISIBLE
                        txt_nodata!!.visibility = View.GONE
                    }

                    Log.e(TAG,"complaintSort               2203    "+complaintSort)
                    val adapter = ServiceComplaintAdapter(this@ServiceFollowUPActiivty, complaintSort)
                    recyComplaint!!.adapter = adapter
                    adapter.setClickListener(this@ServiceFollowUPActiivty)
                }
            })

            dialogComplaint!!.show()
            dialogComplaint!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }
    }


    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}
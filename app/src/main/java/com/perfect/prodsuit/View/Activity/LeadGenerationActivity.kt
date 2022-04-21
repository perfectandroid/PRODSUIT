package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class LeadGenerationActivity : AppCompatActivity() , View.OnClickListener , ItemClickListener {

     internal var etdate: EditText? = null
     internal var ettime: EditText? = null
     internal var etdis: EditText? = null
     internal var yr: Int =0
     internal var month:Int = 0
     internal var day:Int = 0
     internal var hr:Int = 0
     internal var min:Int = 0
     private var mYear: Int =0
     private var mMonth:Int = 0
     private var mDay:Int = 0
     private var mHour:Int = 0
     private var mMinute:Int = 0
    val TAG : String = "LeadGenerationActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    private var chipNavigationBar: ChipNavigationBar? = null
    private var llCustomer: LinearLayout? = null
    private var llCustomerDetail: LinearLayout? = null
    private var llProdDetail: LinearLayout? = null
    private var llLeadFrom: LinearLayout? = null
    private var llleadthrough: LinearLayout? = null
    private var llleadby: LinearLayout? = null
    private var llproduct: LinearLayout? = null
    private var llmediatype: LinearLayout? = null
    private var llUploadImages: LinearLayout? = null
    private var lldate: LinearLayout? = null
    private var lllocation: LinearLayout? = null
    private var ll_Todate: LinearLayout? = null
    private var llFollowdate: LinearLayout? = null
    private var llNeedTransfer: LinearLayout? = null
    private var llLocDetail: LinearLayout? = null
    private var txtcustomer: TextView? = null
    private var txtleadfrom: TextView? = null
    private var txtleadthrough: TextView? = null
    private var txtleadby: TextView? = null
    private var txtproduct: TextView? = null
    private var txtMediatype: TextView? = null
    private var txtDate: TextView? = null
    private var txtLocation: TextView? = null
    private var txtok1: TextView? = null
    private var txtok2: TextView? = null
    private var edt_customer: EditText? = null
    private var edtCustname: EditText? = null
    private var edtCustphone: EditText? = null
    private var edtCustemail: EditText? = null
    private var edtCustaddress: EditText? = null
    private var img_search: ImageView? = null
    var date_Picker1: DatePicker? = null
    var date_Picker2: DatePicker? = null
    private var btnCustReset: Button? = null
    private var btnCustSubmit: Button? = null
    private var imCustclose: ImageView? = null
    private var imDateclose: ImageView? = null
    private var imFollowDateclose: ImageView? = null
    private var imProdclose: ImageView? = null
    private var CUSTOMER_SEARCH: Int? = 101
    private var SELECT_PRODUCT: Int? = 102
    private var SELECT_LOCATION: Int? = 103
    lateinit var leadThroughViewModel: LeadThroughViewModel
    lateinit var leadFromViewModel: LeadFromViewModel
    lateinit var leadByViewModel: LeadByViewModel
    lateinit var mediaTypeViewModel: MediaTypeViewModel
    var recyLeadFrom: RecyclerView? = null
    var recyLeadThrough: RecyclerView? = null
    var recyLeadby: RecyclerView? = null
    var recyMediaType: RecyclerView? = null
    private var imgvupload1: ImageView? = null
    private var imgvupload2: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private val PERMISSION_REQUEST_CODE = 200
    private var strImage: String? = null
    private var destination: File? = null
    private var image1 = ""
    private var image2 = ""
    private val MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1
    lateinit var leadFromArrayList : JSONArray
    lateinit var leadThroughArrayList : JSONArray
    lateinit var leadByArrayList : JSONArray
    lateinit var mediaTypeArrayList : JSONArray
    var dialogLeadFrom : Dialog? = null
    var dialogLeadThrough : Dialog? = null
    var dialogLeadBy : Dialog? = null
    var dialogMediaType : Dialog? = null
    var dialogCustSearch : Dialog? = null
     lateinit var customersearchViewModel: CustomerSearchViewModel
     lateinit var customerAddViewModel: CustomerAddViewModel
     lateinit var customerArrayList : JSONArray
     private var llfollowup: LinearLayout? = null
     private var llMoreCommInfo: LinearLayout? = null

    lateinit var pinCodeSearchViewModel: PinCodeSearchViewModel
    lateinit var pinCodeArrayList : JSONArray
    private var dialogPinCode : Dialog? = null
    var recyPinCode: RecyclerView? = null

    lateinit var countryViewModel: CountryViewModel
    lateinit var countryArrayList : JSONArray
    private var dialogCountry : Dialog? = null
    var recyCountry: RecyclerView? = null

    lateinit var stateViewModel: StateViewModel
    lateinit var stateArrayList : JSONArray
    private var dialogState : Dialog? = null
    var recyState: RecyclerView? = null

    lateinit var districtViewModel: DistrictViewModel
    lateinit var districtArrayList : JSONArray
    private var dialogDistrict : Dialog? = null
    var recyDistrict: RecyclerView? = null

    lateinit var postViewModel: PostViewModel
    lateinit var postArrayList : JSONArray
    private var dialogPost : Dialog? = null
    var recyPost: RecyclerView? = null

    private var edtPincode: EditText? = null
    private var edtCountry: EditText? = null
    private var edtState: EditText? = null
    private var edtDistrict: EditText? = null
    private var edtPost: EditText? = null
    private var edtLandLine: EditText? = null
    private var imgPinSearch: ImageView? = null

     private var edtProdcategory: EditText? = null
     private var edtProdproduct: EditText? = null
     private var edtProdpriority: EditText? = null
     private var edtProdstatus: EditText? = null
     private var edtFollowaction: EditText? = null
     private var edtFollowtype: EditText? = null
     private var edtFollowdate: EditText? = null
     var switchTransfer: Switch? = null
     private var edtbarnchtype: EditText? = null
     private var edtbranch: EditText? = null
     private var edtdepartment: EditText? = null
     private var edtEmployee: EditText? = null
     lateinit var productCategoryViewModel: ProductCategoryViewModel
     lateinit var productDetailViewModel: ProductDetailViewModel
     lateinit var productStatusViewModel: ProductStatusViewModel
     lateinit var productPriorityViewModel: ProductPriorityViewModel
     lateinit var followUpActionViewModel: FollowUpActionViewModel
     lateinit var followUpTypeViewModel: FollowUpTypeViewModel
     lateinit var branchTypeViewModel: BranchTypeViewModel
     lateinit var branchViewModel: BranchViewModel
     lateinit var departmentViewModel: DepartmentViewModel
     lateinit var employeeViewModel: EmployeeViewModel
     lateinit var prodCategoryArrayList : JSONArray
     lateinit var prodDetailArrayList : JSONArray
     lateinit var prodStatusArrayList : JSONArray
     lateinit var prodPriorityArrayList : JSONArray
     lateinit var followUpActionArrayList : JSONArray
     lateinit var followUpTypeArrayList : JSONArray
     lateinit var branchTypeArrayList : JSONArray
     lateinit var branchArrayList : JSONArray
     lateinit var departmentArrayList : JSONArray
     lateinit var employeeArrayList : JSONArray
     private var dialogProdCat : Dialog? = null
     private var dialogProdDet : Dialog? = null
     private var dialogProdStatus : Dialog? = null
     private var dialogProdPriority : Dialog? = null
     private var dialogFollowupAction : Dialog? = null
     private var dialogFollowupType : Dialog? = null
     private var dialogBranchType : Dialog? = null
     private var dialogBranch : Dialog? = null
     private var dialogDepartment : Dialog? = null
     private var dialogEmployee : Dialog? = null
     var recyProdCategory: RecyclerView? = null
     var recyProdDetail: RecyclerView? = null
     var recyProdStatus: RecyclerView? = null
     var recyProdPriority: RecyclerView? = null
     var recyFollowupAction: RecyclerView? = null
     var recyFollowupType: RecyclerView? = null
     var recyBranchType: RecyclerView? = null
     var recyBranch: RecyclerView? = null
     var recyDeaprtment: RecyclerView? = null
     var recyEmployee: RecyclerView? = null

    private var tv_CustClick: TextView? = null
    private var tv_ProductClick: TextView? = null
    private var tv_LocationClick: TextView? = null
    private var tv_DateClick: TextView? = null
    private var tv_LeadFromClick: TextView? = null
    private var tv_LeadThroughClick: TextView? = null
    private var tv_LeadByClick: TextView? = null
    private var tv_MediaTypeClick: TextView? = null
    private var tv_UploadImage: TextView? = null
    private var tv_MoreCommInfoClick: TextView? = null

    companion object {
        var ID_LeadFrom : String?= ""
        var ID_LeadThrough : String?= ""
        var ID_CollectedBy : String?= ""
        var ID_MediaMaster : String?= ""
        var custDetailMode : String?= "1"
        var moreCommInfoMode : String?= "1"
        var Customer_Mode : String?= ""
        var ID_Customer : String?= ""
        var Customer_Name : String?= ""
        var Customer_Mobile : String?= ""
        var Customer_Email : String?= ""
        var Customer_Address : String?= ""
        var strCustomer = ""
        var strPincode = ""
        var locAddress : String?= ""
        var locCity : String?= ""
        var locState : String?= ""
        var locCountry : String?= ""
        var locpostalCode : String?= ""
        var locKnownName : String?= ""
        var strLatitude : String?= ""
        var strLongitue : String?= ""
        var dateMode : String?= "1"  // GONE
        var dateFollowMode : String?= "1"  // GONE
        var custProdlMode : String?= "1" // GONE
        var locationMode : String?= "1" // GONE
        var leadfromMode : String?= "1" // GONE
        var leadThroughMode : String?= "1" // GONE
        var leadByMode : String?= "1" // GONE
        var mediaTypeMode : String?= "1" // GONE
        var uploadImageMode : String?= "1" // GONE
        var ID_Category : String?= ""
        var ID_Product : String?= ""
        var ID_Status : String?= ""
        var ID_Priority : String?= ""
        var strProdName : String = ""
        var ID_NextAction : String = ""
        var ID_ActionType : String = ""
        var ID_BranchType : String = ""
        var ID_Branch : String = ""
        var ID_Department : String = ""
        var ID_Employee : String = ""
        var strQty : String = ""
        var strFeedback : String = ""
        var strFollowupdate : String = ""
        var strNeedCheck : String = "0"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_leadgeneration)
        context = this@LeadGenerationActivity
        leadFromViewModel = ViewModelProvider(this).get(LeadFromViewModel::class.java)
        leadThroughViewModel = ViewModelProvider(this).get(LeadThroughViewModel::class.java)
        leadByViewModel = ViewModelProvider(this).get(LeadByViewModel::class.java)
        mediaTypeViewModel = ViewModelProvider(this).get(MediaTypeViewModel::class.java)
        customersearchViewModel = ViewModelProvider(this).get(CustomerSearchViewModel::class.java)
        pinCodeSearchViewModel = ViewModelProvider(this).get(PinCodeSearchViewModel::class.java)
        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        stateViewModel = ViewModelProvider(this).get(StateViewModel::class.java)
        districtViewModel = ViewModelProvider(this).get(DistrictViewModel::class.java)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        customerAddViewModel = ViewModelProvider(this).get(CustomerAddViewModel::class.java)
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        productStatusViewModel = ViewModelProvider(this).get(ProductStatusViewModel::class.java)
        productPriorityViewModel = ViewModelProvider(this).get(ProductPriorityViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        branchTypeViewModel = ViewModelProvider(this).get(BranchTypeViewModel::class.java)
        branchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        departmentViewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        setRegViews()
        clearData()
        switchTransfer!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                llNeedTransfer!!.visibility = View.VISIBLE
                edtbarnchtype!!.setText("")
                edtbranch!!.setText("")
                edtdepartment!!.setText("")
                edtEmployee!!.setText("")
                ProductActivity.strNeedCheck = "1"
            } else {
                llNeedTransfer!!.visibility = View.GONE
                edtbarnchtype!!.setText("")
                edtbranch!!.setText("")
                edtdepartment!!.setText("")
                edtEmployee!!.setText("")
                strNeedCheck = "0"
            }
        }
    }

    private fun clearData() {
        ID_LeadFrom  = ""
        ID_LeadThrough = ""
        ID_CollectedBy = ""
        ID_MediaMaster = ""
        custDetailMode = "1"
        Customer_Mode = ""
        ID_Customer = ""
        Customer_Name = ""
        Customer_Mobile = ""
        Customer_Email = ""
        Customer_Address = ""
        strCustomer = ""
        strPincode = ""
        locAddress = ""
        locCity = ""
        locState = ""
        locCountry = ""
        locpostalCode = ""
        locKnownName = ""
        strLatitude = ""
        strLongitue = ""
        edtProdcategory!!.setText("")
        edtProdproduct!!.setText("")
        edtProdpriority!!.setText("")
        edtProdstatus!!.setText("")
        edtFollowaction!!.setText("")
        edtFollowtype!!.setText("")
        ID_Category = ""
        ID_Product = ""
        strProdName = ""
        strQty = ""
        ID_Priority = ""
        strFeedback = ""
        ID_Status = ""
        ID_NextAction = ""
        ID_ActionType = ""
        strFollowupdate = ""
        strNeedCheck = "0"
        ID_BranchType = ""
        ID_Branch = ""
        ID_Department = ""
        ID_Employee = ""
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        img_search = findViewById<ImageView>(R.id.img_search)
        imCustclose = findViewById<ImageView>(R.id.imCustclose)
        imDateclose = findViewById<ImageView>(R.id.imDateclose)
        imFollowDateclose = findViewById<ImageView>(R.id.imFollowDateclose)
        imProdclose = findViewById<ImageView>(R.id.imProdclose)
      //  llCustomer = findViewById<LinearLayout>(R.id.llCustomer)
        llCustomerDetail = findViewById<LinearLayout>(R.id.llCustomerDetail)
        llProdDetail = findViewById<LinearLayout>(R.id.llProdDetail)
        llLeadFrom = findViewById<LinearLayout>(R.id.llLeadFrom)
        llleadthrough = findViewById<LinearLayout>(R.id.llleadthrough)
        llleadby = findViewById<LinearLayout>(R.id.llleadby)
        llproduct = findViewById<LinearLayout>(R.id.llproduct)
        llmediatype = findViewById<LinearLayout>(R.id.llmediatype)
        llUploadImages = findViewById<LinearLayout>(R.id.llUploadImages)
        lldate = findViewById<LinearLayout>(R.id.lldate)
        lllocation = findViewById<LinearLayout>(R.id.lllocation)
        ll_Todate = findViewById<LinearLayout>(R.id.ll_Todate)
        llFollowdate = findViewById<LinearLayout>(R.id.llFollowdate)
        llfollowup = findViewById<LinearLayout>(R.id.llfollowup)
        llMoreCommInfo = findViewById<LinearLayout>(R.id.llMoreCommInfo)
        llNeedTransfer = findViewById<LinearLayout>(R.id.llNeedTransfer)
        llLocDetail = findViewById<LinearLayout>(R.id.llLocDetail)
        txtcustomer = findViewById<TextView>(R.id.txtcustomer)
        txtleadfrom = findViewById<TextView>(R.id.txtleadfrom)
        txtleadthrough = findViewById<TextView>(R.id.txtleadthrough)
        txtleadby = findViewById<TextView>(R.id.txtleadby)
        txtproduct = findViewById<TextView>(R.id.txtproduct)
        txtMediatype = findViewById<TextView>(R.id.txtMediatype)
        txtDate = findViewById<TextView>(R.id.txtDate)
        txtLocation = findViewById<TextView>(R.id.txtLocation)
        txtok1 = findViewById<TextView>(R.id.txtok1)
        txtok2 = findViewById<TextView>(R.id.txtok2)
        edt_customer = findViewById<EditText>(R.id.edt_customer)
        edtProdcategory = findViewById<EditText>(R.id.edtProdcategory)
        edtProdproduct = findViewById<EditText>(R.id.edtProdproduct)
        edtProdpriority = findViewById<EditText>(R.id.edtProdpriority)
        edtProdstatus = findViewById<EditText>(R.id.edtProdstatus)
        edtFollowaction = findViewById<EditText>(R.id.edtFollowaction)
        edtFollowtype = findViewById<EditText>(R.id.edtFollowtype)
        edtFollowdate = findViewById<EditText>(R.id.edtFollowdate)
        edtbarnchtype = findViewById<EditText>(R.id.edtbarnchtype)
        edtbranch = findViewById<EditText>(R.id.edtbranch)
        edtdepartment = findViewById<EditText>(R.id.edtdepartment)
        edtEmployee = findViewById<EditText>(R.id.edtEmployee)
        switchTransfer = findViewById<Switch>(R.id.switchTransfer)
        edtCustname= findViewById<EditText>(R.id.edtCustname)
        edtCustemail= findViewById<EditText>(R.id.edtCustemail)
        edtCustphone= findViewById<EditText>(R.id.edtCustphone)
        edtCustaddress= findViewById<EditText>(R.id.edtCustaddress)

        edtPincode= findViewById<EditText>(R.id.edtPincode)
        edtCountry= findViewById<EditText>(R.id.edtCountry)
        edtState= findViewById<EditText>(R.id.edtState)
        edtDistrict= findViewById<EditText>(R.id.edtDistrict)
        edtPost= findViewById<EditText>(R.id.edtPost)
        edtLandLine= findViewById<EditText>(R.id.edtLandLine)

        imgPinSearch= findViewById<ImageView>(R.id.imgPinSearch)

        btnCustReset = findViewById<Button>(R.id.btnCustReset)
        btnCustSubmit = findViewById<Button>(R.id.btnCustSubmit)

        tv_CustClick = findViewById<TextView>(R.id.tv_CustClick)
        tv_ProductClick = findViewById<TextView>(R.id.tv_ProductClick)
        tv_LocationClick = findViewById<TextView>(R.id.tv_LocationClick)
        tv_DateClick = findViewById<TextView>(R.id.tv_DateClick)
        tv_LeadFromClick = findViewById<TextView>(R.id.tv_LeadFromClick)
        tv_LeadThroughClick = findViewById<TextView>(R.id.tv_LeadThroughClick)
        tv_LeadByClick = findViewById<TextView>(R.id.tv_LeadByClick)
        tv_MediaTypeClick = findViewById<TextView>(R.id.tv_MediaTypeClick)
        tv_UploadImage = findViewById<TextView>(R.id.tv_UploadImage)
        tv_MoreCommInfoClick = findViewById<TextView>(R.id.tv_MoreCommInfoClick)


        imback!!.setOnClickListener(this)
        img_search!!.setOnClickListener(this)
        imCustclose!!.setOnClickListener(this)
        imDateclose!!.setOnClickListener(this)
        imFollowDateclose!!.setOnClickListener(this)
        imProdclose!!.setOnClickListener(this)
        btnCustReset!!.setOnClickListener(this)
        btnCustSubmit!!.setOnClickListener(this)
        txtok1!!.setOnClickListener(this)
        txtok2!!.setOnClickListener(this)
       // llCustomer!!.setOnClickListener(this)
        llLeadFrom!!.setOnClickListener(this)
        llleadthrough!!.setOnClickListener(this)
        llleadby!!.setOnClickListener(this)
        llproduct!!.setOnClickListener(this)
        llmediatype!!.setOnClickListener(this)
        lldate!!.setOnClickListener(this)
        lllocation!!.setOnClickListener(this)
        imgvupload1 = findViewById(R.id.imgv_upload1)
        imgvupload2 = findViewById(R.id.imgv_upload2)
        imgvupload1!!.setOnClickListener(this)
        imgvupload2!!.setOnClickListener(this)
        edtProdcategory!!.setOnClickListener(this)
        edtProdproduct!!.setOnClickListener(this)
        edtProdpriority!!.setOnClickListener(this)
        edtProdstatus!!.setOnClickListener(this)
        edtFollowaction!!.setOnClickListener(this)
        edtFollowtype!!.setOnClickListener(this)
        edtFollowdate!!.setOnClickListener(this)
        edtbarnchtype!!.setOnClickListener(this)
        edtbranch!!.setOnClickListener(this)
        edtdepartment!!.setOnClickListener(this)
        edtEmployee!!.setOnClickListener(this)
        tv_CustClick!!.setOnClickListener(this)
        tv_ProductClick!!.setOnClickListener(this)
        tv_LocationClick!!.setOnClickListener(this)
        tv_DateClick!!.setOnClickListener(this)
        tv_LeadFromClick!!.setOnClickListener(this)
        tv_LeadThroughClick!!.setOnClickListener(this)
        tv_LeadByClick!!.setOnClickListener(this)
        tv_MediaTypeClick!!.setOnClickListener(this)
        tv_UploadImage!!.setOnClickListener(this)
        tv_MoreCommInfoClick!!.setOnClickListener(this)
        txtleadfrom!!.setOnClickListener(this)
        txtleadthrough!!.setOnClickListener(this)
        txtleadby!!.setOnClickListener(this)
        txtMediatype!!.setOnClickListener(this)
        txtDate!!.setOnClickListener(this)
        txtLocation!!.setOnClickListener(this)

        edtCountry!!.setOnClickListener(this)
        edtState!!.setOnClickListener(this)
        edtDistrict!!.setOnClickListener(this)
        edtPost!!.setOnClickListener(this)
        imgPinSearch!!.setOnClickListener(this)


        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        txtDate!!.setText(currentDate)
        txtDate!!.setText(currentDate)
        date_Picker1 = findViewById<DatePicker>(R.id.date_Picker1)
        date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
        date_Picker2 = findViewById<DatePicker>(R.id.date_Picker2)
        date_Picker2!!.minDate = Calendar.getInstance().timeInMillis
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
//            R.id.llCustomer->{
//                if (custDetailMode.equals("0")){
//                    llCustomerDetail!!.visibility = View.GONE
//                    custDetailMode = "1"
//                }else{
//                    llCustomerDetail!!.visibility = View.VISIBLE
//                    custDetailMode = "0"
//                }
//            }
            R.id.tv_CustClick->{
                if (custDetailMode.equals("0")){
                    llCustomerDetail!!.visibility = View.GONE
                    custDetailMode = "1"
                }else{
                    llCustomerDetail!!.visibility = View.VISIBLE
                    //custDetailMode = "0"

                    custDetailMode = "0"
                    moreCommInfoMode = "1"
                    custProdlMode = "1"
                    locationMode = "1"
                    dateMode = "1"
                    leadfromMode = "1"
                    leadThroughMode = "1"
                    leadByMode = "1"
                    mediaTypeMode = "1"
                    uploadImageMode = "1"

                    hideViews()
                }
            }

            R.id.tv_MoreCommInfoClick->{
                if (moreCommInfoMode.equals("0")){
                    llMoreCommInfo!!.visibility = View.GONE
                    moreCommInfoMode = "1"
                }else{
                    llMoreCommInfo!!.visibility = View.VISIBLE
                    //custDetailMode = "0"

                    custDetailMode = "1"
                    moreCommInfoMode = "0"
                    custProdlMode = "1"
                    locationMode = "1"
                    dateMode = "1"
                    leadfromMode = "1"
                    leadThroughMode = "1"
                    leadByMode = "1"
                    mediaTypeMode = "1"
                    uploadImageMode = "1"

                    hideViews()
                }
            }

            R.id.imgPinSearch->{
                Config.Utils.hideSoftKeyBoard(this@LeadGenerationActivity,v)
                try {
                    strPincode = edtPincode!!.text.toString()
                    if (strPincode.equals("")){
                        val snackbar: Snackbar = Snackbar.make(v, "Enter Pincode", Snackbar.LENGTH_LONG)
                        snackbar.setActionTextColor(Color.WHITE)
                        snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                        snackbar.show()

                    }else{
                        getPinCodeSearch()
                    }
                }catch (e  :Exception){
                    Log.e("TAG","Exception  64   "+e.toString())
                }
            }

            R.id.edtCountry->{
                Log.e(TAG,"edtCountry  549  ")
                getCountry(v)
            }

            R.id.edtState->{
                getState(v)
            }

            R.id.edtDistrict->{
                getDistrict(v)
            }
            R.id.edtPost->{
                getPost(v)
            }

            R.id.llleadthrough->{
                if (ID_LeadFrom.equals("")){
                    val snackbar: Snackbar = Snackbar.make(v, "Select Lead From", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()
                }else{
                    getLeadThrough(v)
                }
            }
            R.id.llLeadFrom->{
                getLeadFrom(v)
            }

            R.id.imgv_upload1->{
                try
                {
                    Config.Utils.hideSoftKeyBoard(this@LeadGenerationActivity,v)
                    strImage="1"
                    showPictureDialog()
                }
                catch(e:java.lang.Exception)
                {
                    if (checkCamera()){} else{
                        requestPermission()
                    }
                }
            }
            R.id.imgv_upload2->{
                try {
                    Config.Utils.hideSoftKeyBoard(this@LeadGenerationActivity,v)
                    strImage="2"
                    showPictureDialog()
                }
                catch(e:java.lang.Exception)
                {
                    if (checkCamera()){} else{
                        requestPermission()
                    }
                }
            }

            R.id.llleadby->{


                getLeadBy(v)

            }

//            R.id.llproduct->{
//
//
////                val intent = Intent(this@LeadGenerationActivity, ProductActivity::class.java)
////                startActivityForResult(intent, SELECT_PRODUCT!!);
//
//                if (custProdlMode.equals("0")){
//                    llProdDetail!!.visibility = View.GONE
//                    custProdlMode = "1"
//                }else{
//                    llProdDetail!!.visibility = View.VISIBLE
//                    custProdlMode = "0"
//                }
//
//            }

            R.id.tv_ProductClick->{

                if (custProdlMode.equals("0")){
                    llProdDetail!!.visibility = View.GONE
                    custProdlMode = "1"
                }else{
                    llProdDetail!!.visibility = View.VISIBLE
                   // custProdlMode = "0"

                    custDetailMode = "1"
                    moreCommInfoMode = "1"
                    custProdlMode = "0"
                    locationMode = "1"
                    dateMode = "1"
                    leadfromMode = "1"
                    leadThroughMode = "1"
                    leadByMode = "1"
                    mediaTypeMode = "1"
                    uploadImageMode = "1"

                    hideViews()
                }

            }

               R.id.tv_LocationClick->{

                if (locationMode.equals("0")){
                    llLocDetail!!.visibility = View.GONE
                    locationMode = "1"
                }else{
                    llLocDetail!!.visibility = View.VISIBLE
                    //locationMode = "0"

                    custDetailMode = "1"
                    moreCommInfoMode = "1"
                    custProdlMode = "1"
                    locationMode = "0"
                    dateMode = "1"
                    leadfromMode = "1"
                    leadThroughMode = "1"
                    leadByMode = "1"
                    mediaTypeMode = "1"
                    uploadImageMode = "1"

                    hideViews()

                }

            }

            R.id.llmediatype->{
                getMediaType()
            }
//            R.id.lldate->{
//               // datePickerPopup()
//                if (dateMode.equals("0")){
//                    ll_Todate!!.visibility = View.GONE
//                    dateMode = "1"
//                }else{
//                    ll_Todate!!.visibility = View.VISIBLE
//                    dateMode = "0"
//                }
//            }
             R.id.tv_DateClick->{
               // datePickerPopup()
                if (dateMode.equals("0")){
                    ll_Todate!!.visibility = View.GONE
                    dateMode = "1"
                }else{
                    ll_Todate!!.visibility = View.VISIBLE
                   // dateMode = "0"

                    custDetailMode = "1"
                    moreCommInfoMode = "1"
                    custProdlMode = "1"
                    locationMode = "1"
                    dateMode = "0"
                    leadfromMode = "1"
                    leadThroughMode = "1"
                    leadByMode = "1"
                    mediaTypeMode = "1"
                    uploadImageMode = "1"
                    hideViews()
                }
            }
            R.id.txtDate->{
                datePickerPopup()
            }

             R.id.tv_LeadFromClick->{
                 if (leadfromMode.equals("0")){
                     llLeadFrom!!.visibility = View.GONE
                     leadfromMode = "1"
                 }else{
                     llLeadFrom!!.visibility = View.VISIBLE
                     //leadfromMode = "0"

                     custDetailMode = "1"
                     moreCommInfoMode = "1"
                     custProdlMode = "1"
                     locationMode = "1"
                     dateMode = "1"
                     leadfromMode = "0"
                     leadThroughMode = "1"
                     leadByMode = "1"
                     mediaTypeMode = "1"
                     uploadImageMode = "1"
                     hideViews()
                 }
            }



            R.id.txtleadfrom->{
                getLeadFrom(v)
            }

            R.id.tv_LeadThroughClick->{
                 if (leadThroughMode.equals("0")){
                     llleadthrough!!.visibility = View.GONE
                     leadThroughMode = "1"
                 }else{
                     llleadthrough!!.visibility = View.VISIBLE
                    // leadThroughMode = "0"

                     custDetailMode = "1"
                     moreCommInfoMode = "1"
                     custProdlMode = "1"
                     locationMode = "1"
                     dateMode = "1"
                     leadfromMode = "1"
                     leadThroughMode = "0"
                     leadByMode = "1"
                     mediaTypeMode = "1"
                     uploadImageMode = "1"
                     hideViews()
                 }
            }

            R.id.txtleadthrough->{
                getLeadThrough(v)
            }

            R.id.tv_LeadByClick->{
                 if (leadByMode.equals("0")){
                     llleadby!!.visibility = View.GONE
                     leadByMode = "1"
                 }else{
                     llleadby!!.visibility = View.VISIBLE
                    // leadByMode = "0"

                     custDetailMode = "1"
                     moreCommInfoMode = "1"
                     custProdlMode = "1"
                     locationMode = "1"
                     dateMode = "1"
                     leadfromMode = "1"
                     leadThroughMode = "1"
                     leadByMode = "0"
                     mediaTypeMode = "1"
                     uploadImageMode = "1"
                     hideViews()
                 }
            }

            R.id.txtleadby->{
                getLeadBy(v)
            }

            R.id.tv_MediaTypeClick->{
                if (mediaTypeMode.equals("0")){
                    llmediatype!!.visibility = View.GONE
                    mediaTypeMode = "1"
                }else{
                    llmediatype!!.visibility = View.VISIBLE
                  //  mediaTypeMode = "0"

                    custDetailMode = "1"
                    moreCommInfoMode = "1"
                    custProdlMode = "1"
                    locationMode = "1"
                    dateMode = "1"
                    leadfromMode = "1"
                    leadThroughMode = "1"
                    leadByMode = "1"
                    mediaTypeMode = "0"
                    uploadImageMode = "1"
                    hideViews()
                }
            }

            R.id.txtMediatype->{
                getMediaType()
            }

             R.id.tv_UploadImage->{
                if (uploadImageMode.equals("0")){
                    llUploadImages!!.visibility = View.GONE
                    uploadImageMode = "1"
                }else{
                    llUploadImages!!.visibility = View.VISIBLE
                   // uploadImageMode = "0"

                    custDetailMode = "1"
                    moreCommInfoMode = "1"
                    custProdlMode = "1"
                    locationMode = "1"
                    dateMode = "1"
                    leadfromMode = "1"
                    leadThroughMode = "1"
                    leadByMode = "1"
                    mediaTypeMode = "1"
                    uploadImageMode = "0"

                    hideViews()
                }
            }


            R.id.lllocation->{

                val intent = Intent(this@LeadGenerationActivity, LocationPickerActivity::class.java)
                startActivityForResult(intent, SELECT_LOCATION!!);
            }

            R.id.txtLocation->{

                val intent = Intent(this@LeadGenerationActivity, LocationPickerActivity::class.java)
                startActivityForResult(intent, SELECT_LOCATION!!);
            }


            R.id.img_search->{
                try {
                    strCustomer = edt_customer!!.text.toString()
                    if (strCustomer.equals("")){
                        val snackbar: Snackbar = Snackbar.make(v, "Enter Customer", Snackbar.LENGTH_LONG)
                        snackbar.setActionTextColor(Color.WHITE)
                        snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                        snackbar.show()

                    }else{
                        getCustomerSearch()
                    }
                }catch (e  :Exception){
                    Log.e("TAG","Exception  64   "+e.toString())
                }
            }

            R.id.btnCustSubmit->{
                validations(v)
            }
            R.id.btnCustReset->{

                edtCustname!!.setText("")
                edtCustphone!!.setText("")
                edtCustemail!!.setText("")
                edtCustaddress!!.setText("")
                custDetailMode = "1"
                moreCommInfoMode = "1"
                ID_Customer = ""
                edt_customer!!.setText("")
                moreCommInfoMode = "1"
                Customer_Mode = ""
                ID_Customer  = ""
                Customer_Name  = ""
                Customer_Mobile = ""
                Customer_Email = ""
                Customer_Address = ""
                strCustomer = ""
            }

            R.id.imCustclose->{
                llCustomerDetail!!.visibility = View.GONE
                custDetailMode = "1"
            }
            R.id.imDateclose->{
                ll_Todate!!.visibility = View.GONE
                dateMode = "1"
            }
            R.id.imFollowDateclose->{
                llFollowdate!!.visibility = View.GONE
                dateFollowMode = "1"
            }
              R.id.imProdclose->{

                  llProdDetail!!.visibility = View.GONE
                  custProdlMode = "1"
            }

            R.id.txtok1->{
                try {
                    date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                    val day: Int = date_Picker1!!.getDayOfMonth()
                    val mon: Int = date_Picker1!!.getMonth()
                    val month: Int = mon+1
                    val year: Int = date_Picker1!!.getYear()
                    var strDay = day.toString()
                    var strMonth = month.toString()
                    var strYear = year.toString()
                    if (strDay.length == 1){
                        strDay ="0"+day
                    }
                    if (strMonth.length == 1){
                        strMonth ="0"+strMonth
                    }
                    txtDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    ll_Todate!!.visibility=View.GONE
                    dateMode = "1"
                }
                catch (e: Exception){
                    Log.e(TAG,"Exception   428   "+e.toString())
                }

            }
              R.id.txtok2->{
                try {
                    date_Picker2!!.minDate = Calendar.getInstance().timeInMillis
                    val day: Int = date_Picker2!!.getDayOfMonth()
                    val mon: Int = date_Picker2!!.getMonth()
                    val month: Int = mon+1
                    val year: Int = date_Picker2!!.getYear()
                    var strDay = day.toString()
                    var strMonth = month.toString()
                    var strYear = year.toString()
                    if (strDay.length == 1){
                        strDay ="0"+day
                    }
                    if (strMonth.length == 1){
                        strMonth ="0"+strMonth
                    }
                    edtFollowdate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    llFollowdate!!.visibility=View.GONE
                    dateFollowMode = "1"
                }
                catch (e: Exception){
                    Log.e(TAG,"Exception   428   "+e.toString())
                }

            }

            R.id.edtProdcategory->{
                getCategory()
            }
            R.id.edtProdproduct->{
                strProdName = edtProdproduct!!.text.toString()

               if (ID_Category.equals("")){
                    val snackbar: Snackbar = Snackbar.make(v, "Select Category", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()

                }
                else{
                    getProductDetail(strProdName)
                }
            }

            R.id.edtProdpriority->{
                getProductPriority()
            }
            R.id.edtProdstatus->{
                getProductStatus()
            }
            R.id.edtFollowaction->{
                getFollowupAction()
            }
            R.id.edtFollowtype->{
                getFollowupType()
            }
            R.id.edtFollowdate->{
                if (dateFollowMode.equals("0")){
                    llFollowdate!!.visibility = View.GONE
                    dateFollowMode = "1"
                }else{
                    llFollowdate!!.visibility = View.VISIBLE
                    dateFollowMode = "0"
                }
            }

            R.id.edtbarnchtype->{
                getBranchType()
            }
            R.id.edtbranch->{
                if (ID_BranchType.equals("")){

                    val snackbar: Snackbar = Snackbar.make(v, "Select Branch type", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()

                }else{
                    getBranch()
                }

            }
            R.id.edtdepartment->{
                getDepartment()
            }
            R.id.edtEmployee->{

                if (ID_Department.equals("")){

                    val snackbar: Snackbar = Snackbar.make(v, "Select Department", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()

                }else{
                    getEmployee()
                }
            }

        }
    }




    private fun hideViews() {

        if (custDetailMode.equals("1")){
            llCustomerDetail!!.visibility = View.GONE
        }
        if (moreCommInfoMode.equals("1")){
            llMoreCommInfo!!.visibility = View.GONE
        }
        if (custProdlMode.equals("1")){
            llProdDetail!!.visibility = View.GONE
        }
        if (locationMode.equals("1")){
            llLocDetail!!.visibility = View.GONE
        }
        if (dateMode.equals("1")){
            ll_Todate!!.visibility = View.GONE
        }
        if (leadfromMode.equals("1")){
            llLeadFrom!!.visibility = View.GONE
        }
        if (leadThroughMode.equals("1")){
            llleadthrough!!.visibility = View.GONE
        }
        if (leadByMode.equals("1")){
            llleadby!!.visibility = View.GONE
        }

        if (mediaTypeMode.equals("1")){
            llmediatype!!.visibility = View.GONE
        }
        if (uploadImageMode.equals("1")){
            llUploadImages!!.visibility = View.GONE
        }


    }

    private fun datePickerPopup() {
        try {

            val dialogDate = Dialog(this)
            dialogDate!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDate!! .setContentView(R.layout.dialog_datepicker)
            dialogDate!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogDate.setCancelable(false)

            val date_Picker = dialogDate!! .findViewById(R.id.date_Picker) as DatePicker
            val txtcancel = dialogDate!! .findViewById(R.id.txtcancel) as TextView
            val txtok = dialogDate!! .findViewById(R.id.txtok) as TextView

            date_Picker.minDate = Calendar.getInstance().timeInMillis

            txtok.setOnClickListener {
                dialogDate.dismiss()
                val day: Int = date_Picker.getDayOfMonth()
                val mon: Int = date_Picker.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker.getYear()



                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()

                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }

                txtDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)


            }

            txtcancel.setOnClickListener {
                dialogDate.dismiss()
            }

            dialogDate!!.show()
            // dialogProdStatus!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun getLeadBy(v: View) {

        var countLeadBy = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadByViewModel.getLeadBy(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   228   "+msg.length)
                            Log.e(TAG,"msg   228   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("CollectedByUsersList")
                                leadByArrayList = jobjt.getJSONArray("CollectedByUsers")
                                if (leadByArrayList.length()>0){
                                    if (countLeadBy == 0){
                                        countLeadBy++
                                        leadByPopup(leadByArrayList)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun leadByPopup(leadByArrayList: JSONArray) {

        try {

            dialogLeadBy = Dialog(this)
            dialogLeadBy!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadBy!! .setContentView(R.layout.lead_by_popup)
            dialogLeadBy!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadby = dialogLeadBy!! .findViewById(R.id.recyLeadby) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recyLeadby!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = LeadByAdapter(this@LeadGenerationActivity, leadByArrayList)
            recyLeadby!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogLeadBy!!.show()
            dialogLeadBy!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getLeadFrom(v: View) {
        var countLeadFrom = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadFromViewModel.getLeadFrom(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {


                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   91   "+msg.length)
                            Log.e(TAG,"msg   91   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("LeadFromDetailsList")
                                leadFromArrayList = jobjt.getJSONArray("LeadFromDetails")
                                if (leadFromArrayList.length()>0){
                                    if (countLeadFrom == 0){
                                        countLeadFrom++
                                        leadFromPopup(leadFromArrayList)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun leadFromPopup(leadFromArrayList: JSONArray) {

        try {

            dialogLeadFrom = Dialog(this)
            dialogLeadFrom!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadFrom!! .setContentView(R.layout.lead_from_popup)
            dialogLeadFrom!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadFrom = dialogLeadFrom!! .findViewById(R.id.recyLeadFrom) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recyLeadFrom!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = LeadFromAdapter(this@LeadGenerationActivity, leadFromArrayList)
            recyLeadFrom!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogLeadFrom!!.show()
            dialogLeadFrom!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getLeadThrough(v: View) {
        var countLeadThrough = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadThroughViewModel.getLeadThrough(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   267   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("LeadThroughDetailsList")
                                leadThroughArrayList = jobjt.getJSONArray("LeadThroughDetails")
                                if (leadThroughArrayList.length()>0){
                                    if (countLeadThrough == 0){
                                        countLeadThrough++
                                        leadThroghPopup(leadThroughArrayList)
                                    }


                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun leadThroghPopup(leadThroughArrayList: JSONArray) {

        try {

            dialogLeadThrough = Dialog(this)
            dialogLeadThrough!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadThrough!! .setContentView(R.layout.lead_through_popup)
            dialogLeadThrough!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadThrough = dialogLeadThrough!! .findViewById(R.id.recyLeadThrough) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recyLeadThrough!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = LeadThroughAdapter(this@LeadGenerationActivity, leadThroughArrayList)
            recyLeadThrough!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogLeadThrough!!.show()
            dialogLeadThrough!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getMediaType() {
        var countMediatype = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                mediaTypeViewModel.getMediaType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   510   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("MediaTypeDetails")
                                mediaTypeArrayList = jobjt.getJSONArray("MediaTypeDetailsList")
                                if (mediaTypeArrayList.length()>0){
                                    if (countMediatype == 0){
                                        countMediatype++
                                        mediaTypePopup(mediaTypeArrayList)
                                    }


                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun mediaTypePopup(mediaTypeArrayList: JSONArray) {

        try {

            dialogMediaType = Dialog(this)
            dialogMediaType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMediaType!! .setContentView(R.layout.mediatype_popup)
            dialogMediaType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyMediaType = dialogMediaType!! .findViewById(R.id.recyMediaType) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recyMediaType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = MediaTypeAdapter(this@LeadGenerationActivity, mediaTypeArrayList)
            recyMediaType!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogMediaType!!.show()
            dialogMediaType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /*private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@LeadGenerationActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.reminder -> {
                      setReminder()
                    }
                    R.id.logout -> {
                        doLogout()
                    }
                    R.id.quit -> {
                        quit()
                    }
                }
            }
        })
    }

     private fun setReminder() {
         try
         {
             val builder = android.app.AlertDialog.Builder(this)
             val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
             val layout = inflater1.inflate(R.layout.reminder_setter_popup, null)
             val btncancel = layout.findViewById(R.id.btncancel) as Button
             val btnsubmit = layout.findViewById(R.id.btnsubmit) as Button
             etdate = layout.findViewById(R.id.etdate) as EditText
             ettime = layout.findViewById(R.id.ettime) as EditText
             etdis = layout.findViewById(R.id.etdis) as EditText
             *//* val ll_ok = layout.findViewById(R.id.ll_ok) as LinearLayout
              val ll_cancel = layout.findViewById(R.id.ll_cancel) as LinearLayout
              etdate = layout.findViewById(R.id.etdate) as TextView
              ettime = layout.findViewById(R.id.ettime) as TextView
              val etdis = layout.findViewById(R.id.etdis) as EditText*//*
             etdate!!.setKeyListener(null)
             ettime!!.setKeyListener(null)
             builder.setView(layout)
             val alertDialog = builder.create()
             val c = Calendar.getInstance()
             val sdf = SimpleDateFormat("dd-MM-yyyy")
             val sdf1 = SimpleDateFormat("hh:mm a")
             val sdf2 = SimpleDateFormat("hh:mm")
             yr = c.get(Calendar.YEAR)
             month = c.get(Calendar.MONTH)
             day = c.get(Calendar.DAY_OF_MONTH)
             etdate!!.setText(sdf.format(c.time))
             ettime!!.setText(sdf1.format(c.time))
             val s = sdf2.format(c.time)
             val split = s.split((":").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
             val strhr = split[0]
             val strmin = split[1]
             hr = Integer.parseInt(strhr)
             min = Integer.parseInt(strmin)
             ettime!!.setOnClickListener(View.OnClickListener { timeSelector() })
             etdate!!.setOnClickListener(View.OnClickListener { dateSelector() })
             btncancel.setOnClickListener {
                 Config.Utils.hideSoftKeyBoard(this, it)
                 alertDialog.dismiss() }
             btnsubmit.setOnClickListener {
                 Config.Utils.hideSoftKeyBoard(this, it)
                 addEvent(yr, month, day, hr, min, etdis!!.text.toString(), " Reminder")
                 alertDialog.dismiss()
             }
             alertDialog.show()
         }
         catch (e: Exception) {
             e.printStackTrace()
         }

     }

     fun addEvent(iyr: Int, imnth: Int, iday: Int, ihour: Int, imin: Int, descriptn: String, Title: String) {
         if (ActivityCompat.checkSelfPermission(
                         applicationContext,
                         Manifest.permission.WRITE_CALENDAR
                 ) != PackageManager.PERMISSION_GRANTED
         ) {
             ActivityCompat.requestPermissions(
                     this,
                     arrayOf(Manifest.permission.WRITE_CALENDAR),
                     1
             )
         }
         val cr = contentResolver
         val beginTime = Calendar.getInstance()
         beginTime.set(2022, 11 - 1, 28, 9, 30)
         val endTime = Calendar.getInstance()
         endTime.set(iyr, imnth, iday, ihour, imin)
         val values = ContentValues()
         values.put(CalendarContract.Events.DTSTART, endTime.timeInMillis)
         values.put(CalendarContract.Events.DTEND, endTime.timeInMillis)
         values.put(CalendarContract.Events.TITLE, Title)
         values.put(CalendarContract.Events.DESCRIPTION, "[ $descriptn ]")
         values.put(CalendarContract.Events.CALENDAR_ID, 1)
         val tz = TimeZone.getDefault()
         values.put(CalendarContract.Events.EVENT_TIMEZONE, tz.id)
         values.put(CalendarContract.Events.EVENT_LOCATION, "India")
         try {
             val uri = cr.insert(CalendarContract.Events.CONTENT_URI, values)
             val reminders = ContentValues()
             reminders.put(CalendarContract.Reminders.EVENT_ID, uri!!.lastPathSegment)
             reminders.put(
                     CalendarContract.Reminders.METHOD,
                     CalendarContract.Reminders.METHOD_ALERT
             )
             reminders.put(CalendarContract.Reminders.MINUTES, 10)
             cr.insert(CalendarContract.Reminders.CONTENT_URI, reminders)
         }catch (e: Exception){
             e.printStackTrace()
         }

         val builder = AlertDialog.Builder(this)
         builder.setMessage("Reminder set successfully.")
                 .setCancelable(false)
                 .setPositiveButton(
                         "OK"
                 ) { dialog, id -> dialog.dismiss()
                 }
         val alert = builder.create()
         alert.show()

     }

     fun timeSelector() {
         val c = Calendar.getInstance()
         mHour = c.get(Calendar.HOUR_OF_DAY)
         mMinute = c.get(Calendar.MINUTE)
         // Launch Time Picker Dialog
         val timePickerDialog = TimePickerDialog(this,
                 TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                     val strDate = String.format(
                             "%02d:%02d %s", if (hourOfDay == 0) 12 else hourOfDay,
                             minute, if (hourOfDay < 12) "am" else "pm"
                     )
                     ettime!!.setText(strDate)
                     hr = hourOfDay
                     min = minute
                 }, mHour, mMinute, false
         )
         timePickerDialog.show()
     }

     fun dateSelector() {
         try {
             val sdf = SimpleDateFormat("dd-MM-yyyy")
             val c = Calendar.getInstance()
             mYear = c.get(Calendar.YEAR)
             mMonth = c.get(Calendar.MONTH)
             mDay = c.get(Calendar.DAY_OF_MONTH)
             val datePickerDialog = DatePickerDialog(this,
                     DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                         yr = year
                         month = monthOfYear
                         day = dayOfMonth
                         etdate!!.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                     }, mYear, mMonth, mDay
             )
             datePickerDialog.datePicker.minDate = c.timeInMillis
             datePickerDialog.show()

         } catch (e: ParseException) {
             e.printStackTrace()
         }
     }

     private fun doLogout() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.logout_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btnYes) as Button
            val btn_No = dialog1 .findViewById(R.id.btnNo) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                dologoutchanges()
                startActivity(Intent(this@LeadGenerationActivity, WelcomeActivity::class.java))
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dologoutchanges() {
        val loginSP = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        val loginEditer = loginSP.edit()
        loginEditer.putString("loginsession", "No")
        loginEditer.commit()
        val loginmobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
        val loginmobileEditer = loginmobileSP.edit()
        loginmobileEditer.putString("Loginmobilenumber", "")
        loginmobileEditer.commit()
    }

    private fun quit() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.quit_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btn_Yes) as Button
            val btn_No = dialog1 .findViewById(R.id.btn_No) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                finish()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity()
                }

            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG","onActivityResult  256   "+requestCode+ "   "+resultCode+ "  "+data)
        if (requestCode == CUSTOMER_SEARCH){
            if (data!=null){
                txtcustomer!!.text = data!!.getStringExtra("Name")

                Customer_Mode     = data!!.getStringExtra("Customer_Mode")
                ID_Customer       = data!!.getStringExtra("ID_Customer")
                Customer_Name     = data!!.getStringExtra("Name")
                Customer_Mobile   = data!!.getStringExtra("MobileNumber")
                Customer_Email    = data!!.getStringExtra("Email")
                Customer_Address  = data!!.getStringExtra("Address")
            }

        }

        if (requestCode == SELECT_LOCATION){
            if (data!=null){
            //    txtcustomer!!.text = data!!.getStringExtra("Name")
                if (data.getStringExtra("address").equals("")){
                    txtLocation!!.setText(data.getStringExtra("address"))
                }else{
                    txtLocation!!.setText(data.getStringExtra("city"))
                }

                locAddress      = data.getStringExtra("address")
                locCity         = data.getStringExtra("city")
                locState        = data.getStringExtra("state")
                locCountry      = data.getStringExtra("country")
                locpostalCode   = data.getStringExtra("postalCode")
                locKnownName    = data.getStringExtra("knownName")
                strLatitude     = data.getStringExtra("strLatitude")
                strLongitue     = data.getStringExtra("strLongitue")
            }

        }
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data!!.data
                try {
                    var selectedImageUri: Uri = data.getData()!!
                    data.getData()
                    if(strImage.equals("1")) {

                        //   val img_image1 = findViewById(R.id.img_image1) as RoundedImageView
                        imgvupload1!!.setImageURI(contentURI)
                        image1 = getRealPathFromURI(selectedImageUri)
                        if (image1 != null) {
                        }
                    }
                    if(strImage.equals("2")) {

                        //  val img_image2 = findViewById(R.id.img_image2) as RoundedImageView
                        imgvupload2!!.setImageURI(contentURI)
                        image2 = getRealPathFromURI(selectedImageUri)
                        if (image2 != null) {
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@LeadGenerationActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }

        } else if (requestCode == CAMERA) {

            if (data != null){
                   try {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                        )
                    }
                }
                else {

                    val thumbnail = data!!.getExtras()!!.get("data") as Bitmap
                    val bytes = ByteArrayOutputStream()
                    thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                    destination = File(
                        (Environment.getExternalStorageDirectory()).toString() + "/" +
                                getString(R.string.app_name),
                        "IMG_" + System.currentTimeMillis() + ".jpg"
                    )
                    val fo: FileOutputStream

                    try {
                        if (!destination!!.getParentFile().exists()) {
                            destination!!.getParentFile().mkdirs()
                        }
                        if (!destination!!.exists()) {
                            destination!!.createNewFile()
                        }
                        fo = FileOutputStream(destination)
                        fo.write(bytes.toByteArray())
                        fo.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    if (strImage.equals("1")) {
                        image1 = destination!!.getAbsolutePath()
                        destination = File(image1)


                        val myBitmap = BitmapFactory.decodeFile(destination.toString())
                        //  val img_image1 = findViewById(R.id.img_image1) as RoundedImageView
                        if (imgvupload1 != null) {
                            imgvupload1!!.setImageBitmap(myBitmap)
                        }
                        imgvupload1!!.setImageBitmap(myBitmap)

                        if (image1 != null) {

                        }
                    }
                    if (strImage.equals("2")) {
                        image2 = destination!!.getAbsolutePath()
                        destination = File(image2)

                        val myBitmap = BitmapFactory.decodeFile(destination.toString())
                        //   val img_image2 = findViewById(R.id.img_image2) as RoundedImageView
                        if (imgvupload2 != null) {
                            imgvupload2!!.setImageBitmap(myBitmap)
                        }
                        imgvupload2!!.setImageBitmap(myBitmap)

                        if (image2 != null) {

                        }
                    }

                }
            }
            catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@LeadGenerationActivity, "Failed!", Toast.LENGTH_SHORT).show()
            }
            }

        }
    }
    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }
    private fun showPictureDialog() {

        try {
            val pm = packageManager
            val hasPerm = pm.checkPermission(Manifest.permission.CAMERA, packageName)
             if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) !== PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is not granted
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                        )
                    }
                } else {
                    val pictureDialog = AlertDialog.Builder(this)
                    pictureDialog.setTitle("Select From")
                    val pictureDialogItems = arrayOf("Gallery", "Camera")
                    pictureDialog.setItems(pictureDialogItems   ) { dialog, which ->
                        when (which) {
                            0 -> choosePhotoFromGallary()
                            1 -> takePhotoFromCamera()
                        }
                    }
                    pictureDialog.show()
                }
            } else ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                 CAMERA
            )
        }catch (e : Exception){

        }

    }

    private fun checkCamera(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;

    }

    private fun takePhotoFromCamera() {
        val photo =  Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(photo, CAMERA)
    }

    private fun requestPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE)
    }
    fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        if (getContentResolver() != null) {
            val cursor = getContentResolver().query(uri, null, null, null, null)
            if (cursor != null) {
                cursor!!.moveToFirst()
                val idx = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor!!.getString(idx)
                cursor!!.close()
            }
        }
        return path
    }



     private fun getCustomerSearch() {
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                 customersearchViewModel.getCustomer(this)!!.observe(
                     this,
                     Observer { serviceSetterGetter ->
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   105   "+msg)
                             if (jObject.getString("StatusCode") == "0") {
                                 val jobjt = jObject.getJSONObject("CustomerDetailsList")
                                 customerArrayList = jobjt.getJSONArray("CustomerDetails")
                                 if (customerArrayList.length()>0){
                                     Log.e(TAG,"msg   1052   "+msg)

                                     customerSearchPopup(customerArrayList)
                                 }
                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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

     private fun customerSearchPopup(customerArrayList: JSONArray) {
         try {

             dialogCustSearch = Dialog(this)
             dialogCustSearch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
             dialogCustSearch!! .setContentView(R.layout.customersearch_popup)
             dialogCustSearch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             val recyCustomer = dialogCustSearch!! .findViewById(R.id.recyCustomer) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyCustomer!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = CustomerAdapter(this@LeadGenerationActivity, customerArrayList)
             recyCustomer!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogCustSearch!!.show()
             dialogCustSearch!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
             dialogCustSearch!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         } catch (e: Exception) {
             e.printStackTrace()
         }
     }

    private fun getPinCodeSearch() {
        var pinCodeDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                pinCodeSearchViewModel.getPincode(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2028   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

//                                val jobjt = jObject.getJSONObject("CustomerDetailsList")
//                                customerArrayList = jobjt.getJSONArray("CustomerDetails")
//
//                                if (prodCategoryArrayList.length()>0){
//                                    if (pinCodeDet == 0){
//                                        pinCodeDet++
//                                        productCategoryPopup(prodCategoryArrayList)
//                                    }
//
//                                }
//

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun getCountry(v: View) {
        var countryDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                countryViewModel.getCountry(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2151   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

//                                val jobjt = jObject.getJSONObject("CustomerDetailsList")
//                                customerArrayList = jobjt.getJSONArray("CustomerDetails")
//
//                                if (prodCategoryArrayList.length()>0){
//                                    if (countryDet == 0){
//                                        countryDet++
//                                        productCategoryPopup(prodCategoryArrayList)
//                                    }
//
//                                }


                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun getState(v: View) {

        var stateDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                stateViewModel.getState(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2219   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

//                                val jobjt = jObject.getJSONObject("CustomerDetailsList")
//                                customerArrayList = jobjt.getJSONArray("CustomerDetails")
//
//                                if (prodCategoryArrayList.length()>0){
//                                    if (stateDet == 0){
//                                        stateDet++
//                                        productCategoryPopup(prodCategoryArrayList)
//                                    }
//
//                                }


                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun getDistrict(v: View) {
        var distDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                districtViewModel.getDistrict(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2286   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

//                                val jobjt = jObject.getJSONObject("CustomerDetailsList")
//                                customerArrayList = jobjt.getJSONArray("CustomerDetails")
//
//                                if (prodCategoryArrayList.length()>0){
//                                    if (distDet == 0){
//                                        distDet++
//                                        productCategoryPopup(prodCategoryArrayList)
//                                    }
//
//                                }


                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun getPost(v: View) {
        var distDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                postViewModel.getPost(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2353   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

//                                val jobjt = jObject.getJSONObject("CustomerDetailsList")
//                                customerArrayList = jobjt.getJSONArray("CustomerDetails")
//
//                                if (prodCategoryArrayList.length()>0){
//                                    if (distDet == 0){
//                                        distDet++
//                                        productCategoryPopup(prodCategoryArrayList)
//                                    }
//
//                                }


                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadGenerationActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

     private fun validations(v: View) {

         val Cust_Name =  edtCustname!!.text.toString()
         val Cust_Email =  edtCustemail!!.text.toString()
         val Cust_Mobile =  edtCustphone!!.text.toString()
         val Cust_Address =  edtCustaddress!!.text.toString()

         if (Cust_Name.equals("")){
             val snackbar: Snackbar = Snackbar.make(v, "Enter Name", Snackbar.LENGTH_LONG)
             snackbar.setActionTextColor(Color.WHITE)
             snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
             snackbar.show()
         }
         else if (Cust_Mobile.equals("") || Cust_Mobile!!.length != 10){
             val snackbar: Snackbar = Snackbar.make(v, "Enter Valid Mobile", Snackbar.LENGTH_LONG)
             snackbar.setActionTextColor(Color.WHITE)
             snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
             snackbar.show()
         }
         else if (Cust_Email.equals("")){
             val snackbar: Snackbar = Snackbar.make(v, "Enter Valid Email", Snackbar.LENGTH_LONG)
             snackbar.setActionTextColor(Color.WHITE)
             snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
             snackbar.show()
         }
         else if (Cust_Address.equals("")){
             val snackbar: Snackbar = Snackbar.make(v, "Enter Address", Snackbar.LENGTH_LONG)
             snackbar.setActionTextColor(Color.WHITE)
             snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
             snackbar.show()
         }
         else{


             custDetailMode = "1"
             ID_Customer = ""
             edt_customer!!.setText("")
             txtcustomer!!.text = Cust_Name

             Customer_Mode     = "0"  // ADD
             ID_Customer       = ""
             Customer_Name     = Cust_Name
             Customer_Mobile   = Cust_Mobile
             Customer_Email    = Cust_Email
             Customer_Address  = Cust_Address
             llCustomerDetail!!.visibility = View.GONE

         }

     }


     private fun getCategory() {
         var prodcategory = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 productCategoryViewModel.getProductCategory(this)!!.observe(
                     this,
                     Observer { serviceSetterGetter ->
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   82   "+msg)
                             if (jObject.getString("StatusCode") == "0") {
                                 val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                 prodCategoryArrayList = jobjt.getJSONArray("CategoryList")
                                 if (prodCategoryArrayList.length()>0){
                                     if (prodcategory == 0){
                                         prodcategory++
                                         productCategoryPopup(prodCategoryArrayList)
                                     }

                                 }
                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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

     private fun productCategoryPopup(prodCategoryArrayList: JSONArray) {
         try {

             dialogProdCat = Dialog(this)
             dialogProdCat!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
             dialogProdCat!! .setContentView(R.layout.product_category_popup)
             dialogProdCat!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             recyProdCategory = dialogProdCat!! .findViewById(R.id.recyProdCategory) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyProdCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = ProductCategoryAdapter(this@LeadGenerationActivity, prodCategoryArrayList)
             recyProdCategory!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogProdCat!!.show()
             dialogProdCat!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
         }
     }

     private fun getProductDetail(strProdName: String) {
         var proddetail = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 productDetailViewModel.getProductDetail(this)!!.observe(
                     this,
                     Observer { serviceSetterGetter ->
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   227   "+msg)
                             if (jObject.getString("StatusCode") == "0") {

                                 val jobjt = jObject.getJSONObject("ProductDetailsList")
                                 prodDetailArrayList = jobjt.getJSONArray("ProductList")
                                 if (prodDetailArrayList.length()>0){
                                     if (proddetail == 0){
                                         proddetail++
                                         productDetailPopup(prodDetailArrayList)
                                     }

                                 }

                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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

     private fun productDetailPopup(prodDetailArrayList: JSONArray) {

         try {

             dialogProdDet = Dialog(this)
             dialogProdDet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
             dialogProdDet!! .setContentView(R.layout.product_detail_popup)
             dialogProdDet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             recyProdDetail = dialogProdDet!! .findViewById(R.id.recyProdDetail) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = ProductDetailAdapter(this@LeadGenerationActivity, prodDetailArrayList)
             recyProdDetail!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogProdDet!!.show()
             dialogProdDet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
         }

     }

     private fun getProductPriority() {
         var prodpriority = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 productPriorityViewModel.getProductPriority(this)!!.observe(
                     this,
                     Observer { serviceSetterGetter ->
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   353   "+msg)
                             if (jObject.getString("StatusCode") == "0") {

                                 val jobjt = jObject.getJSONObject("PriorityDetailsList")
                                 prodPriorityArrayList = jobjt.getJSONArray("PriorityList")
                                 if (prodPriorityArrayList.length()>0){
                                     if (prodpriority == 0){
                                         prodpriority++
                                         productPriorityPopup(prodPriorityArrayList)
                                     }

                                 }

                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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

     private fun productPriorityPopup(prodPriorityArrayList: JSONArray) {

         try {

             dialogProdPriority = Dialog(this)
             dialogProdPriority!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
             dialogProdPriority!! .setContentView(R.layout.product_priority_popup)
             dialogProdPriority!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             recyProdPriority = dialogProdPriority!! .findViewById(R.id.recyProdPriority) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyProdPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = ProductPriorityAdapter(this@LeadGenerationActivity, prodPriorityArrayList)
             recyProdPriority!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogProdPriority!!.show()
             dialogProdPriority!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
         }

     }

     private fun getProductStatus() {
         var prodstatus = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 productStatusViewModel.getProductStatus(this)!!.observe(
                     this,
                     Observer { serviceSetterGetter ->
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   333   "+msg)
                             if (jObject.getString("StatusCode") == "0") {

                                 val jobjt = jObject.getJSONObject("StatusDetailsList")
                                 prodStatusArrayList = jobjt.getJSONArray("StatusList")
                                 if (prodStatusArrayList.length()>0){
                                     if (prodstatus == 0){
                                         prodstatus++
                                         productStatusPopup(prodStatusArrayList)
                                     }

                                 }

                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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

     private fun productStatusPopup(prodStatusArrayList: JSONArray) {

         try {

             dialogProdStatus = Dialog(this)
             dialogProdStatus!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
             dialogProdStatus!! .setContentView(R.layout.product_status_popup)
             dialogProdStatus!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             recyProdStatus = dialogProdStatus!! .findViewById(R.id.recyProdStatus) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyProdStatus!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = ProductStatusAdapter(this@LeadGenerationActivity, prodStatusArrayList)
             recyProdStatus!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogProdStatus!!.show()
             dialogProdStatus!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
         }
     }

     private fun getFollowupAction() {
         var followUpAction = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 followUpActionViewModel.getFollowupAction(this)!!.observe(
                     this,
                     Observer { serviceSetterGetter ->
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   82   "+msg)
                             if (jObject.getString("StatusCode") == "0") {
                                 val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                 followUpActionArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
                                 if (followUpActionArrayList.length()>0){
                                     if (followUpAction == 0){
                                         followUpAction++
                                         followUpActionPopup(followUpActionArrayList)
                                     }

                                 }
                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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

     private fun followUpActionPopup(followUpActionArrayList: JSONArray) {

         try {

             dialogFollowupAction = Dialog(this)
             dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
             dialogFollowupAction!! .setContentView(R.layout.followup_action)
             dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             recyFollowupAction = dialogFollowupAction!! .findViewById(R.id.recyFollowupAction) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = FollowupActionAdapter(this@LeadGenerationActivity, followUpActionArrayList)
             recyFollowupAction!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogFollowupAction!!.show()
             dialogFollowupAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
         }

     }


     private fun getFollowupType() {
         var followUpType = 0
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
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   82   "+msg)
                             if (jObject.getString("StatusCode") == "0") {
                                 val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                 followUpTypeArrayList = jobjt.getJSONArray("FollowUpTypeDetailsList")
                                 if (followUpTypeArrayList.length()>0){
                                     if (followUpType == 0){
                                         followUpType++
                                         followupTypePopup(followUpTypeArrayList)
                                     }

                                 }
                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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
             dialogFollowupType!! .setContentView(R.layout.followup_type_popup)
             dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             recyFollowupType = dialogFollowupType!! .findViewById(R.id.recyFollowupType) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = FollowupTypeAdapter(this@LeadGenerationActivity, followUpTypeArrayList)
             recyFollowupType!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogFollowupType!!.show()
             dialogFollowupType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
         }

     }

     private fun getBranchType() {
         var branchType = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 branchTypeViewModel.getBranchType(this)!!.observe(
                     this,
                     Observer { serviceSetterGetter ->
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   979   "+msg)
                             if (jObject.getString("StatusCode") == "0") {
                                 val jobjt = jObject.getJSONObject("BranchTypeDetails")
                                 branchTypeArrayList = jobjt.getJSONArray("BranchTypeDetailsList")
                                 if (branchTypeArrayList.length()>0){
                                     if (branchType == 0){
                                         branchType++
                                         branchTypePopup(branchTypeArrayList)
                                     }

                                 }
                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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

     private fun branchTypePopup(branchTypeArrayList: JSONArray) {

         try {

             dialogBranchType = Dialog(this)
             dialogBranchType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
             dialogBranchType!! .setContentView(R.layout.branchtype_popup)
             dialogBranchType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             recyBranchType = dialogBranchType!! .findViewById(R.id.recyBranchType) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyBranchType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = BranchTypeAdapter(this@LeadGenerationActivity, branchTypeArrayList)
             recyBranchType!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogBranchType!!.show()
             dialogBranchType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
         }
     }

     private fun getBranch() {
         var branch = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 branchViewModel.getBranch(this)!!.observe(
                     this,
                     Observer { serviceSetterGetter ->
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   1062   "+msg)
                             if (jObject.getString("StatusCode") == "0") {
                                 val jobjt = jObject.getJSONObject("BranchDetails")
                                 branchArrayList = jobjt.getJSONArray("BranchDetailsList")
                                 if (branchArrayList.length()>0){
                                     if (branch == 0){
                                         branch++
                                         branchPopup(branchArrayList)
                                     }

                                 }
                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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

     private fun branchPopup(branchArrayList: JSONArray) {

         try {

             dialogBranch = Dialog(this)
             dialogBranch!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
             dialogBranch!! .setContentView(R.layout.branch_popup)
             dialogBranch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             recyBranch = dialogBranch!! .findViewById(R.id.recyBranch) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyBranch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = BranchAdapter(this@LeadGenerationActivity, branchArrayList)
             recyBranch!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogBranch!!.show()
             dialogBranch!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
             Log.e(TAG,"Exception  1132   "+e.toString())
         }
     }

     private fun getDepartment() {
         var department = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 departmentViewModel.getDepartment(this)!!.observe(
                     this,
                     Observer { serviceSetterGetter ->
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   1142   "+msg)
                             if (jObject.getString("StatusCode") == "0") {
                                 val jobjt = jObject.getJSONObject("DepartmentDetails")
                                 departmentArrayList = jobjt.getJSONArray("DepartmentDetailsList")
                                 if (departmentArrayList.length()>0){
                                     if (department == 0){
                                         department++
                                         departmentPopup(departmentArrayList)
                                     }

                                 }
                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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

     private fun departmentPopup(departmentArrayList: JSONArray) {
         try {

             dialogDepartment = Dialog(this)
             dialogDepartment!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
             dialogDepartment!! .setContentView(R.layout.department_popup)
             dialogDepartment!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             recyDeaprtment = dialogDepartment!! .findViewById(R.id.recyDeaprtment) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyDeaprtment!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = DepartmentAdapter(this@LeadGenerationActivity, departmentArrayList)
             recyDeaprtment!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogDepartment!!.show()
             dialogDepartment!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
         }
     }

     private fun getEmployee() {
         var employee = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 employeeViewModel.getEmployee(this, ID_Department)!!.observe(
                     this,
                     Observer { serviceSetterGetter ->
                         val msg = serviceSetterGetter.message
                         if (msg!!.length > 0) {
                             val jObject = JSONObject(msg)
                             Log.e(TAG,"msg   1224   "+msg)
                             if (jObject.getString("StatusCode") == "0") {
                                 val jobjt = jObject.getJSONObject("EmployeeDetails")
                                 employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                 if (employeeArrayList.length()>0){
                                     if (employee == 0){
                                         employee++
                                         employeePopup(employeeArrayList)
                                     }

                                 }
                             } else {
                                 val builder = AlertDialog.Builder(
                                     this@LeadGenerationActivity,
                                     R.style.MyDialogTheme
                                 )
                                 builder.setMessage(jObject.getString("EXMessage"))
                                 builder.setPositiveButton("Ok") { dialogInterface, which ->
                                 }
                                 val alertDialog: AlertDialog = builder.create()
                                 alertDialog.setCancelable(false)
                                 alertDialog.show()
                             }
                         } else {
                             Toast.makeText(
                                 applicationContext,
                                 "Some Technical Issues.",
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
             dialogEmployee!! .setContentView(R.layout.employee_popup)
             dialogEmployee!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
             recyEmployee = dialogEmployee!! .findViewById(R.id.recyEmployee) as RecyclerView

             val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
             recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
             recyEmployee!!.adapter = adapter
             adapter.setClickListener(this@LeadGenerationActivity)

             dialogEmployee!!.show()
             dialogEmployee!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         } catch (e: Exception) {
             e.printStackTrace()
         }
     }


     override fun onClick(position: Int, data: String) {

        if (data.equals("leadfrom")){
            dialogLeadFrom!!.dismiss()
            val jsonObject = leadFromArrayList.getJSONObject(position)
            Log.e(TAG,"ID_LeadFrom   "+jsonObject.getString("ID_LeadFrom"))
            ID_LeadFrom = jsonObject.getString("ID_LeadFrom")
            txtleadfrom!!.text = jsonObject.getString("LeadFromName")
        }
        if (data.equals("leadthrough")){
            dialogLeadThrough!!.dismiss()
            val jsonObject = leadThroughArrayList.getJSONObject(position)
            Log.e(TAG,"ID_LeadThrough   "+jsonObject.getString("ID_LeadThrough"))
            ID_LeadThrough = jsonObject.getString("ID_LeadThrough")
            txtleadthrough!!.text = jsonObject.getString("LeadThroughName")

        }

        if (data.equals("leadby")){
            dialogLeadBy!!.dismiss()
            val jsonObject = leadByArrayList.getJSONObject(position)
            Log.e(TAG,"ID_CollectedBy   "+jsonObject.getString("ID_CollectedBy"))
            ID_CollectedBy = jsonObject.getString("ID_CollectedBy")
            txtleadby!!.text = jsonObject.getString("Name")

        }
        if (data.equals("mediatype")){
            dialogMediaType!!.dismiss()
            val jsonObject = mediaTypeArrayList.getJSONObject(position)
            Log.e(TAG,"ID_MediaMaster   "+jsonObject.getString("ID_MediaMaster"))
            ID_MediaMaster = jsonObject.getString("ID_MediaMaster")
            txtMediatype!!.text = jsonObject.getString("MdaName")

        }
        if (data.equals("customer")){
            dialogCustSearch!!.dismiss()
            val jsonObject = customerArrayList.getJSONObject(position)
            txtcustomer!!.text = jsonObject!!.getString("Name")
            edt_customer!!.setText(jsonObject!!.getString("Name"))

            custDetailMode = "1"
            Customer_Mode     = "1"  // SEARCH
            ID_Customer       = jsonObject.getString("ID_Customer")
            Customer_Name     = jsonObject.getString("Name")
            Customer_Mobile   = jsonObject.getString("MobileNumber")
            Customer_Email    = jsonObject.getString("Email")
            Customer_Address  = jsonObject.getString("Address")

            llCustomerDetail!!.visibility = View.GONE
            edtCustname!!.setText("")
            edtCustphone!!.setText("")
            edtCustemail!!.setText("")
            edtCustaddress!!.setText("")
        }

         if (data.equals("prodcategory")){
             dialogProdCat!!.dismiss()
             val jsonObject = prodCategoryArrayList.getJSONObject(position)
             Log.e(TAG,"ID_Category   "+jsonObject.getString("ID_Category"))
             ID_Category = jsonObject.getString("ID_Category")
             edtProdcategory!!.setText(jsonObject.getString("CategoryName"))
         }
         if (data.equals("proddetails")){
             dialogProdDet!!.dismiss()
             val jsonObject = prodDetailArrayList.getJSONObject(position)
             Log.e(TAG,"ID_Product   "+jsonObject.getString("ID_Product"))
             ID_Product = jsonObject.getString("ID_Product")
             edtProdproduct!!.setText(jsonObject.getString("ProductName"))
         }
         if (data.equals("prodpriority")){
             dialogProdPriority!!.dismiss()
             val jsonObject = prodPriorityArrayList.getJSONObject(position)
             Log.e(TAG,"ID_Priority   "+jsonObject.getString("ID_Priority"))
             ID_Priority = jsonObject.getString("ID_Priority")
             edtProdpriority!!.setText(jsonObject.getString("PriorityName"))


         }

         if (data.equals("prodstatus")){
             dialogProdStatus!!.dismiss()
             val jsonObject = prodStatusArrayList.getJSONObject(position)
             Log.e(TAG,"ID_Status   "+jsonObject.getString("ID_Status"))
             ProductActivity.ID_Status = jsonObject.getString("ID_Status")
             edtProdstatus!!.setText(jsonObject.getString("StatusName"))

             edtFollowaction!!.setText("")
             ID_NextAction =""
             edtFollowtype!!.setText("")
             ID_ActionType = ""

             if (jsonObject.getString("ID_Status").equals("1")){
                 llfollowup!!.visibility  =View.VISIBLE
                 val sdf = SimpleDateFormat("dd-MM-yyyy")
                 val currentDate = sdf.format(Date())
                 edtFollowdate!!.setText(currentDate)
                 switchTransfer!!.isChecked = false

             }else{
                 llfollowup!!.visibility  =View.GONE
                 switchTransfer!!.isChecked = false
             }
         }
         if (data.equals("followupaction")){
             dialogFollowupAction!!.dismiss()
             val jsonObject = followUpActionArrayList.getJSONObject(position)
             Log.e(TAG,"ID_NextAction   "+jsonObject.getString("ID_NextAction"))
             ID_NextAction = jsonObject.getString("ID_NextAction")
             edtFollowaction!!.setText(jsonObject.getString("NxtActnName"))


         }

         if (data.equals("followuptype")){
             dialogFollowupType!!.dismiss()
             val jsonObject = followUpTypeArrayList.getJSONObject(position)
             Log.e(TAG,"ID_ActionType   "+jsonObject.getString("ID_ActionType"))
             ID_ActionType = jsonObject.getString("ID_ActionType")
             edtFollowtype!!.setText(jsonObject.getString("ActnTypeName"))


         }

         if (data.equals("branchtype")){
             dialogBranchType!!.dismiss()
             val jsonObject = branchTypeArrayList.getJSONObject(position)
             Log.e(TAG,"ID_BranchType   "+jsonObject.getString("ID_BranchType"))
             ID_BranchType = jsonObject.getString("ID_BranchType")
             edtbarnchtype!!.setText(jsonObject.getString("BranchTypeName"))


         }

         if (data.equals("branch")){
             dialogBranch!!.dismiss()
             val jsonObject = branchArrayList.getJSONObject(position)
             Log.e(TAG,"ID_Branch   "+jsonObject.getString("ID_Branch"))
             ID_Branch = jsonObject.getString("ID_Branch")
             edtbranch!!.setText(jsonObject.getString("BranchName"))


         }

         if (data.equals("department")){
             dialogDepartment!!.dismiss()
             val jsonObject = departmentArrayList.getJSONObject(position)
             Log.e(TAG,"ID_Department   "+jsonObject.getString("ID_Department"))
             ID_Department = jsonObject.getString("ID_Department")
             edtdepartment!!.setText(jsonObject.getString("DeptName"))


         }

         if (data.equals("employee")){
             dialogEmployee!!.dismiss()
             val jsonObject = employeeArrayList.getJSONObject(position)
             Log.e(TAG,"ID_Employee   "+jsonObject.getString("ID_Employee"))
             ID_Employee = jsonObject.getString("ID_Employee")
             edtEmployee!!.setText(jsonObject.getString("EmpName"))


         }

     }
}
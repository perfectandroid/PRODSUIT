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
import android.os.Build
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
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
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
import java.text.DateFormat
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
    private var llCustSearch: LinearLayout? = null
    private var llCompanyName: LinearLayout? = null
    private var rltvPinCode: RelativeLayout? = null
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
    private var edtCompanyName: EditText? = null
    private var edtContactPerson: EditText? = null
    private var edtContactNumber: EditText? = null
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

    lateinit var leadGenerateSaveViewModel: LeadGenerateSaveViewModel
    lateinit var saveLeadGenArrayList : JSONArray

    lateinit var leadEditListViewModel: LeadEditListViewModel
    lateinit var leadEditArrayList : JSONArray

    lateinit var leadEditDetailViewModel: LeadEditDetailViewModel
    lateinit var leadEditDetArrayList : JSONArray


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
     private var edtProdqty: EditText? = null
     private var edtProdfeedback: EditText? = null
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
     lateinit var leadGenerateDefaultvalueViewModel: LeadGenerationDefaultvalueViewModel

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
     private var dialogLeadEdit : Dialog? = null

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
     var recyLeadDetail: RecyclerView? = null

    private var tv_CustClick: TextView? = null
    private var tv_CompanyNameClick: TextView? = null
    private var tv_ProductClick: TextView? = null
    private var tv_LocationClick: TextView? = null
    private var tv_DateClick: TextView? = null
    private var tv_LeadFromClick: TextView? = null
    private var tv_LeadThroughClick: TextView? = null
    private var tv_LeadByClick: TextView? = null
    private var tv_MediaTypeClick: TextView? = null
    private var tv_UploadImage: TextView? = null
    private var tv_MoreCommInfoClick: TextView? = null

    private var btnReset: Button? = null
    private var btnSubmit: Button? = null

    var saveUpdateMode : String?= ""
    var dateSelectMode : Int= 0


    companion object {
        var ID_LeadFrom : String?= ""
        var ID_LeadThrough : String?= ""
        var ID_CollectedBy : String?= ""
        var ID_MediaMaster : String?= ""
        var custDetailMode : String?= "1"
        var moreCommInfoMode : String?= "1"
        var companyNameMode : String?= "1"
        var Customer_Mode : String?= "0"
        var ID_Customer : String?= ""
        var Customer_Name : String?= ""
        var Customer_Mobile : String?= ""
        var Customer_Email : String?= ""
        var Customer_Address : String?= ""
        var strComapnyName : String?= ""
        var strContactPerson : String?= ""
        var strContactNumber : String?= ""
        var strCustomer = ""
        var strPincode = ""
        var FK_Country = ""
        var FK_States = ""
        var FK_District = ""
        var FK_Area = ""
        var FK_Place = ""
        var FK_Post = ""
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
        var strDate : String = ""
        var strFeedback : String = ""
        var strFollowupdate : String = ""
        var strNeedCheck : String = "0"
        var strMoreLandPhone : String = ""

        var encode1 : String = ""
        var encode2 : String = ""

        var ID_LeadGenerate : String = ""
        var ID_LeadGenerateProduct : String = ""


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
        leadGenerateSaveViewModel = ViewModelProvider(this).get(LeadGenerateSaveViewModel::class.java)
        leadEditListViewModel = ViewModelProvider(this).get(LeadEditListViewModel::class.java)
        leadEditDetailViewModel = ViewModelProvider(this).get(LeadEditDetailViewModel::class.java)
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
        leadGenerateDefaultvalueViewModel = ViewModelProvider(this).get(LeadGenerationDefaultvalueViewModel::class.java)


        setRegViews()
       // getCalendarId(context)

        clearData()
        switchTransfer!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                llNeedTransfer!!.visibility = View.VISIBLE
//                edtbarnchtype!!.setText("")
//                edtbranch!!.setText("")
//                edtdepartment!!.setText("")
//                edtEmployee!!.setText("")
                strNeedCheck = "1"

            } else {
                llNeedTransfer!!.visibility = View.GONE
//                edtbarnchtype!!.setText("")
//                edtbranch!!.setText("")
//                edtdepartment!!.setText("")
//                edtEmployee!!.setText("")
                strNeedCheck = "0"

            }

            getDefaultValueSettings()
        }

       // getDefaultValueSettings()
    }



    private fun clearData() {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())

        Customer_Mode = "0"
        ID_Customer = ""
        Customer_Name = ""
        Customer_Mobile = ""
        Customer_Email = ""
        Customer_Address = ""
        edt_customer!!.setText("")
        edtCustname!!.setText("")
        edtCustphone!!.setText("")
        edtCustemail!!.setText("")
        edtCustaddress!!.setText("")
        strCustomer = ""
        strComapnyName = ""
        strContactPerson = ""
        strContactNumber = ""

        edtCompanyName!!.setText("")
        edtContactPerson!!.setText("")
        edtContactNumber!!.setText("")

        FK_Country = ""
        FK_States = ""
        FK_District = ""
        FK_Area = ""
        FK_Place = ""
        FK_Post = ""
        edtPincode!!.setText("")
        edtCountry!!.setText("")
        edtState!!.setText("")
        edtDistrict!!.setText("")
        edtPost!!.setText("")
        edtLandLine!!.setText("")

        ID_Category = ""
        ID_Product = ""
        strProdName = ""
        strQty = ""
        strDate = ""
        ID_Priority = ""
        strFeedback = ""
        ID_Status = ""
        ID_NextAction = ""
        ID_ActionType = ""
        strFollowupdate = currentDate
        strNeedCheck = "0"
        ID_BranchType = ""
        ID_Branch = ""
        ID_Department = ""
        ID_Employee = ""

        edtProdcategory!!.setText("")
        edtProdproduct!!.setText("")
        edtProdqty!!.setText("")
        edtProdfeedback!!.setText("")
        edtProdpriority!!.setText("")
        edtProdstatus!!.setText("")
        edtFollowaction!!.setText("")
        edtFollowtype!!.setText("")
        edtFollowdate!!.setText(currentDate)
        switchTransfer!!.isChecked = false
        edtbarnchtype!!.setText("")
        edtbranch!!.setText("")
        edtdepartment!!.setText("")
        edtEmployee!!.setText("")

        strPincode = ""
        FK_Country = ""
        FK_States = ""
        FK_District = ""
        FK_Area = ""
        FK_Place = ""
        FK_Post = ""
        locAddress = ""
        locCity = ""
        locState = ""
        locCountry = ""
        locpostalCode = ""
        locKnownName = ""
        strLatitude = ""
        strLongitue = ""

        txtLocation!!.setText("")

        txtDate!!.setText(currentDate)
        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDateFormate = inputFormat.parse(currentDate)
        strDate = outputFormat.format(currentDateFormate)
      //  strDate =currentDate

        ID_LeadFrom  = ""
        ID_LeadThrough = ""
        ID_CollectedBy = ""
        ID_MediaMaster = ""

        txtleadfrom!!.setText("")
        txtleadthrough!!.setText("")
        txtleadby!!.setText("")
        txtMediatype!!.setText("")

        custDetailMode = "1"
        strMoreLandPhone = ""

        image1 = ""
        image2 = ""
        encode1 = ""
        encode2 = ""

        imgvupload1!!.setImageResource(R.drawable.uploadimg)
        imgvupload2!!.setImageResource(R.drawable.uploadimg)

        llfollowup!!.visibility = View.GONE
        llNeedTransfer!!.visibility = View.GONE

        custDetailMode = "1"
        companyNameMode="1"
        moreCommInfoMode = "1"
        custProdlMode = "1"
        locationMode = "1"
        dateMode = "1"
        leadfromMode = "1"
        leadThroughMode = "1"
        leadByMode = "1"
        mediaTypeMode = "1"
        uploadImageMode = "1"

        llCustSearch!!.visibility = View.VISIBLE
        btnSubmit!!.setText("Submit")
        saveUpdateMode = "1"  //SAVE
        rltvPinCode!!.visibility = View.VISIBLE

        ID_LeadGenerate = ""
        ID_LeadGenerateProduct = ""

        getDefaultValueSettings()

        hideViews()

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        val imLeadedit = findViewById<ImageView>(R.id.imLeadedit)
        img_search = findViewById<ImageView>(R.id.img_search)
        imCustclose = findViewById<ImageView>(R.id.imCustclose)
        imDateclose = findViewById<ImageView>(R.id.imDateclose)
        imFollowDateclose = findViewById<ImageView>(R.id.imFollowDateclose)
        imProdclose = findViewById<ImageView>(R.id.imProdclose)
      //  llCustomer = findViewById<LinearLayout>(R.id.llCustomer)
        llCustomerDetail = findViewById<LinearLayout>(R.id.llCustomerDetail)
        llCustSearch = findViewById<LinearLayout>(R.id.llCustSearch)
        llCompanyName = findViewById<LinearLayout>(R.id.llCompanyName)
        rltvPinCode = findViewById<RelativeLayout>(R.id.rltvPinCode)
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
        edtProdqty = findViewById<EditText>(R.id.edtProdqty)
        edtProdfeedback = findViewById<EditText>(R.id.edtProdfeedback)
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
        edtCompanyName= findViewById<EditText>(R.id.edtCompanyName)
        edtContactPerson= findViewById<EditText>(R.id.edtContactPerson)
        edtContactNumber= findViewById<EditText>(R.id.edtContactNumber)

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
        tv_CompanyNameClick = findViewById<TextView>(R.id.tv_CompanyNameClick)
        tv_ProductClick = findViewById<TextView>(R.id.tv_ProductClick)
        tv_LocationClick = findViewById<TextView>(R.id.tv_LocationClick)
        tv_DateClick = findViewById<TextView>(R.id.tv_DateClick)
        tv_LeadFromClick = findViewById<TextView>(R.id.tv_LeadFromClick)
        tv_LeadThroughClick = findViewById<TextView>(R.id.tv_LeadThroughClick)
        tv_LeadByClick = findViewById<TextView>(R.id.tv_LeadByClick)
        tv_MediaTypeClick = findViewById<TextView>(R.id.tv_MediaTypeClick)
        tv_UploadImage = findViewById<TextView>(R.id.tv_UploadImage)
        tv_MoreCommInfoClick = findViewById<TextView>(R.id.tv_MoreCommInfoClick)

        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)


        imback!!.setOnClickListener(this)
        imLeadedit!!.setOnClickListener(this)
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
        tv_CompanyNameClick!!.setOnClickListener(this)
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

        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)



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
            R.id.imLeadedit->{

                getLeadEditList(v)
             // Config.snackBars(context,v,"Lead Edit")

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
                    companyNameMode="1"
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

            R.id.tv_CompanyNameClick->{
                if (companyNameMode.equals("0")){
                    llCompanyName!!.visibility = View.GONE
                    companyNameMode = "1"
                }else{
                    llCompanyName!!.visibility = View.VISIBLE

                    custDetailMode = "1"
                    companyNameMode="0"
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
                    companyNameMode="1"
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
                        getPinCodeSearch(strPincode)
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
                    companyNameMode="1"
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
                    companyNameMode="1"
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
//                    dateSelectMode = 0
//                    openBottomSheet()

                    custDetailMode = "1"
                    companyNameMode="1"
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
               // datePickerPopup()
                dateSelectMode = 0
                openBottomSheet()
            }

             R.id.tv_LeadFromClick->{
                 if (leadfromMode.equals("0")){
                     llLeadFrom!!.visibility = View.GONE
                     leadfromMode = "1"
                 }else{
                     llLeadFrom!!.visibility = View.VISIBLE
                     //leadfromMode = "0"

                     custDetailMode = "1"
                     companyNameMode="1"
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
                     companyNameMode="1"
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
                     companyNameMode="1"
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
                    companyNameMode="1"
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
                    companyNameMode="1"
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
               // validations(v)
            }
            R.id.btnCustReset->{

//                edtCustname!!.setText("")
//                edtCustphone!!.setText("")
//                edtCustemail!!.setText("")
//                edtCustaddress!!.setText("")
//                custDetailMode = "1"
//                moreCommInfoMode = "1"
//                ID_Customer = ""
//                edt_customer!!.setText("")
//                moreCommInfoMode = "1"
//                Customer_Mode = "0"
//                ID_Customer  = ""
//                Customer_Name  = ""
//                Customer_Mobile = ""
//                Customer_Email = ""
//                Customer_Address = ""
//                strCustomer = ""
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
                    strDate = strDay+"-"+strMonth+"-"+strYear
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
                    strFollowupdate = strDay+"-"+strMonth+"-"+strYear
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
                    getProductDetail(ID_Category!!)
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
//                if (dateFollowMode.equals("0")){
//                    llFollowdate!!.visibility = View.GONE
//                    dateFollowMode = "1"
//                }else{
//                    llFollowdate!!.visibility = View.VISIBLE
//                    dateFollowMode = "0"
//
//
//                }

                dateSelectMode = 1
                openBottomSheet()
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

            R.id.btnReset->{

                clearData()
//                finish();
//                startActivity(getIntent());
//                overridePendingTransition(0, 0);
//                startActivity(getIntent());
//                overridePendingTransition(0, 0);
            }

            R.id.btnSubmit->{
                LeadValidations(v)


//                if(image1.equals(""))
//                {
//                    encode1 = ""
//                }
//                else
//                {
//                    val bitmap = BitmapFactory.decodeFile(image1)
//                    val stream =  ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        encode1 = Base64.getEncoder().encodeToString(stream.toByteArray());
//                    } else {
//                        encode1 = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
//                    }
//                }
//                if(image2.equals(""))
//                {
//                    encode2 = ""
//                }
//                else
//                {
//                    val bitmap = BitmapFactory.decodeFile(image2)
//                    val stream =  ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        encode2 = Base64.getEncoder().encodeToString(stream.toByteArray())
//                    } else {
//                        encode2 = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
//                    }
//                }
//
//                Log.e(TAG,"LocationValidation  encode1  373241   "+ encode1)
//                Log.e(TAG,"LocationValidation  encode2  373241   "+ encode2)
//
//                //   LeadConfirmationPopup()
//
//                saveLeadGeneration()


            }

        }
    }

    private fun LeadValids() {
        if(image1.equals(""))
        {
            encode1 = ""
        }
        else
        {
            val bitmap = BitmapFactory.decodeFile(image1)
            val stream =  ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encode1 = Base64.getEncoder().encodeToString(stream.toByteArray());
            } else {
                encode1 = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
            }
        }
        if(image2.equals(""))
        {
            encode2 = ""
        }
        else
        {
            val bitmap = BitmapFactory.decodeFile(image2)
            val stream =  ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encode2 = Base64.getEncoder().encodeToString(stream.toByteArray())
            } else {
                encode2 = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
            }
        }

        Log.e(TAG,"LocationValidation  encode1  373241   "+ encode1)
        Log.e(TAG,"LocationValidation  encode2  373241   "+ encode2)
    }


    private fun hideViews() {

        if (custDetailMode.equals("1")){
            llCustomerDetail!!.visibility = View.GONE
        }
        if (companyNameMode.equals("1")){
            llCompanyName!!.visibility = View.GONE
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
                strDate = strDay+"-"+strMonth+"-"+strYear


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


    private fun getDefaultValueSettings() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadGenerateDefaultvalueViewModel.getLeadGenerationDefaultvalue(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   14781   "+msg.length)
                            Log.e(TAG,"msg   14782   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("LeadGenerationDefaultvalueSettings")
                                Log.e(TAG,"msg   14783   "+jobjt.getString("EmpFName"))

                                ID_BranchType = jobjt.getString("ID_BranchType")
                                edtbarnchtype!!.setText(jobjt.getString("BranchType"))
                                ID_Branch = jobjt.getString("ID_Branch")
                                edtbranch!!.setText(jobjt.getString("Branch"))
                                ID_Department = jobjt.getString("FK_Department")
                                edtdepartment!!.setText(jobjt.getString("Department"))
                                ID_Employee = jobjt.getString("ID_Employee")
                                edtEmployee!!.setText(jobjt.getString("EmpFName"))


                            } else {
//                                val builder = AlertDialog.Builder(
//                                    this@LeadGenerationActivity,
//                                    R.style.MyDialogTheme
//                                )
//                                builder.setMessage(jObject.getString("EXMessage"))
//                                builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                }
//                                val alertDialog: AlertDialog = builder.create()
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()
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
//                txtcustomer!!.text = data!!.getStringExtra("Name")
//
//                Customer_Mode     = data!!.getStringExtra("Customer_Mode")
//                ID_Customer       = data!!.getStringExtra("ID_Customer")
//                Customer_Name     = data!!.getStringExtra("Name")
//                Customer_Mobile   = data!!.getStringExtra("MobileNumber")
//                Customer_Email    = data!!.getStringExtra("Email")
//                Customer_Address  = data!!.getStringExtra("Address")
            }

        }

        if (requestCode == SELECT_LOCATION){
            if (data!=null){
            //    txtcustomer!!.text = data!!.getStringExtra("Name")


//                if (data.getStringExtra("address").equals("")){
//                    txtLocation!!.setText(data.getStringExtra("address"))
//                }else{
//                    txtLocation!!.setText(data.getStringExtra("city"))
//                }

            try {
                locAddress      = data.getStringExtra("address")
                locCity         = data.getStringExtra("city")
                locState        = data.getStringExtra("state")
                locCountry      = data.getStringExtra("country")
                locpostalCode   = data.getStringExtra("postalCode")
                locKnownName    = data.getStringExtra("knownName")
                strLatitude     = data.getStringExtra("strLatitude")
                strLongitue     = data.getStringExtra("strLongitue")

                var locAddres = ""
                if (!locAddress.equals("")){
                    locAddres = locAddress!!
                }
                if (!locCity.equals("")){
                    locAddres = locAddres+","+locCity!!
                }
                if (!locState.equals("")){
                    locAddres = locAddres+","+locState!!
                }
                if (!locCountry.equals("")){
                    locAddres = locAddres+","+locCountry!!
                }
                if (!locpostalCode.equals("")){
                    locAddres = locAddres+","+locpostalCode!!
                }
                txtLocation!!.setText(locAddres)
            }
            catch (e : Exception){

            }

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
                        Log.e(TAG,"image1  2052    "+image1)
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
                        Log.e(TAG,"image1  20522    "+image1)
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
                        Log.e(TAG,"image2  20522    "+image2)
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
         var custDet = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                 customersearchViewModel.getCustomer(this,strCustomer)!!.observe(
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
                                     if (custDet == 0){
                                         custDet++
                                         customerSearchPopup(customerArrayList)
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

    private fun getPinCodeSearch(strPincode: String) {
        var pinCodeDet = 0
        clearCommunicationInfo()
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                pinCodeSearchViewModel.getPincode(this,strPincode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message





                        try {
//                            if (pinCodeDet == 0){
//                                pinCodeDet++
                                 if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   21081   "+msg)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("PincodeDetails")

                                    FK_Country  = jobjt.getString("FK_Country")
                                    FK_States   = jobjt.getString("FK_States")
                                    FK_District = jobjt.getString("FK_District")
                                    FK_Area     = jobjt.getString("FK_Area")
                                    FK_Place    = jobjt.getString("FK_Place")
                                    FK_Post     = jobjt.getString("FK_Post")

                                    edtCountry!!.setText(jobjt.getString("Country"))
                                    edtState!!.setText(jobjt.getString("States"))
                                    edtDistrict!!.setText(jobjt.getString("District"))
                                    edtPost!!.setText(jobjt.getString("Post"))

                                    Log.e(TAG,"Post  21082   "+jobjt.getString("Post"))
//                                if (pinCodeArrayList.length()>0){
//                                    if (pinCodeDet == 0){
//                                        pinCodeDet++
//                                        pincodeDetailPopup(pinCodeArrayList)
//                                    }
//
//                                }


                                } else {

                                    clearCommunicationInfo()

                                    clearCommunicationInfo()
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
                                clearCommunicationInfo()
                            }
                          //  }


                        }catch (e: Exception){


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

    private fun clearCommunicationInfo() {
        FK_Country  = ""
        FK_States   = ""
        FK_District = ""
        FK_Area     = ""
        FK_Place    = ""
        FK_Post     = ""

        edtCountry!!.setText("")
        edtState!!.setText("")
        edtDistrict!!.setText("")
        edtPost!!.setText("")
        edtLandLine!!.setText("")
    }

    private fun pincodeDetailPopup(pinCodeArrayList: JSONArray) {
        try {

//            dialogPinCode = Dialog(this)
//            dialogPinCode!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialogPinCode!! .setContentView(R.layout.pincodedetail_popup)
//            dialogPinCode!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
//            val recyPincodeDetails = dialogPinCode!! .findViewById(R.id.recyPincodeDetails) as RecyclerView
//
//            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
//            recyPincodeDetails!!.layoutManager = lLayout as RecyclerView.LayoutManager?
////            recyCustomer!!.setHasFixedSize(true)
//            val adapter = PicodeDetailAdapter(this@LeadGenerationActivity, pinCodeArrayList)
//            recyPincodeDetails!!.adapter = adapter
//            adapter.setClickListener(this@LeadGenerationActivity)
//
//            dialogPinCode!!.show()
//            dialogPinCode!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialogPinCode!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCountry(v: View) {
        var countryDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                countryViewModel.getCountry(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2252   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("CountryDetails")
                                countryArrayList = jobjt.getJSONArray("CountryDetailsList")

                                if (countryArrayList.length()>0){
                                    if (countryDet == 0){
                                        countryDet++
                                        countryListPopup(countryArrayList)
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
              //  progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun countryListPopup(countryArrayList: JSONArray) {
        try {

            dialogCountry = Dialog(this)
            dialogCountry!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCountry!! .setContentView(R.layout.country_list_popup)
            dialogCountry!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycCountry = dialogCountry!! .findViewById(R.id.recycCountry) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recycCountry!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = CountryDetailAdapter(this@LeadGenerationActivity, countryArrayList)
            recycCountry!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogCountry!!.show()
            dialogCountry!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogCountry!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
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
                stateViewModel.getState(this,FK_Country)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2338   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("StatesDetails")
                                stateArrayList = jobjt.getJSONArray("StatesDetailsList")

                                if (stateArrayList.length()>0){
                                    if (stateDet == 0){
                                        stateDet++
                                        stateDetailPopup(stateArrayList)
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

    private fun stateDetailPopup(stateArrayList: JSONArray) {

        try {

            dialogState = Dialog(this)
            dialogState!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogState!! .setContentView(R.layout.state_list_popup)
            dialogState!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycState = dialogState!! .findViewById(R.id.recycState) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recycState!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = StateDetailAdapter(this@LeadGenerationActivity, stateArrayList)
            recycState!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogState!!.show()
            dialogState!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogState!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
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
                districtViewModel.getDistrict(this, FK_States)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2286   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("DistrictDetails")
                                districtArrayList = jobjt.getJSONArray("DistrictDetailsList")

                                if (districtArrayList.length()>0){
                                    if (distDet == 0){
                                        distDet++
                                        districtDetailPopup(districtArrayList)
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

    private fun districtDetailPopup(districtArrayList: JSONArray) {

        try {

            dialogDistrict = Dialog(this)
            dialogDistrict!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDistrict!! .setContentView(R.layout.district_list_popup)
            dialogDistrict!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycDistrict = dialogDistrict!! .findViewById(R.id.recycDistrict) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recycDistrict!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = DistrictDetailAdapter(this@LeadGenerationActivity, districtArrayList)
            recycDistrict!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogDistrict!!.show()
            dialogDistrict!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogDistrict!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getPost(v: View) {
        var postDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                postViewModel.getPost(this, FK_District)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   2353   "+msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("PostDetails")
                                postArrayList = jobjt.getJSONArray("PostDetailsList")

                                if (postArrayList.length()>0){
                                    if (postDet == 0){
                                        postDet++
                                        postDetailPopup(postArrayList)
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

    private fun postDetailPopup(postArrayList: JSONArray) {

        try {

            dialogPost = Dialog(this)
            dialogPost!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPost!! .setContentView(R.layout.post_list_popup)
            dialogPost!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycPost = dialogPost!! .findViewById(R.id.recycPost) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recycPost!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = PostDetailAdapter(this@LeadGenerationActivity, postArrayList)
            recycPost!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogPost!!.show()
            dialogPost!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogPost!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
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

     private fun getProductDetail(ID_Category: String) {
         var proddetail = 0
         when (Config.ConnectivityUtils.isConnected(this)) {
             true -> {
                 progressDialog = ProgressDialog(context, R.style.Progress)
                 progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                 progressDialog!!.setCancelable(false)
                 progressDialog!!.setIndeterminate(true)
                 progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                 progressDialog!!.show()
                 productDetailViewModel.getProductDetail(this, ID_Category)!!.observe(
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

    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
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

              //  dateSelectMode = 0

                if (dateSelectMode == 0){
                    txtDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    strDate = strYear+"-"+strMonth+"-"+strDay
                }
                if (dateSelectMode == 1){
                    edtFollowdate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    strFollowupdate = strYear+"-"+strMonth+"-"+strDay
                }

//                if (DateType == 0){
//                    tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
//                }
//                if (DateType == 1){
//                    tie_NextFollowupDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
//                }


            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
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
                 branchViewModel.getBranch(this,ID_BranchType)!!.observe(
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

           // custDetailMode = "1"
            Customer_Mode     = "1"  // SEARCH
            ID_Customer       = jsonObject.getString("ID_Customer")
            Customer_Name     = jsonObject.getString("Name")
            Customer_Mobile   = jsonObject.getString("MobileNumber")
            Customer_Email    = jsonObject.getString("Email")
            Customer_Address  = jsonObject.getString("Address")

           // llCustomerDetail!!.visibility = View.GONE
            edtCustname!!.setText(jsonObject.getString("Name"))
            edtCustphone!!.setText(jsonObject.getString("MobileNumber"))
            edtCustemail!!.setText(jsonObject.getString("Email"))
            edtCustaddress!!.setText(jsonObject.getString("Address"))


            FK_Country = jsonObject.getString("FK_Country")
            FK_States = jsonObject.getString("FK_States")
            FK_District = jsonObject.getString("FK_District")
            FK_Post = jsonObject.getString("FK_Post")

            edtPincode!!.setText(jsonObject.getString("Pincode"))
            edtCountry!!.setText(jsonObject.getString("Country"))
            edtState!!.setText(jsonObject.getString("States"))
            edtDistrict!!.setText(jsonObject.getString("District"))
            edtPost!!.setText(jsonObject.getString("Post"))


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
             ID_Status = jsonObject.getString("ID_Status")
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
                 strFollowupdate = currentDate
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

         if (data.equals("countrydetail")){
             dialogCountry!!.dismiss()
             val jsonObject = countryArrayList.getJSONObject(position)
             Log.e(TAG,"FK_Country   "+jsonObject.getString("FK_Country"))
             FK_Country = jsonObject.getString("FK_Country")
             edtCountry!!.setText(jsonObject.getString("Country"))


             FK_States   = ""
             FK_District = ""
             FK_Area     = ""
             FK_Place    = ""
             FK_Post     = ""

             edtState!!.setText("")
             edtDistrict!!.setText("")
             edtPost!!.setText("")
             edtPincode!!.setText("")
             edtLandLine!!.setText("")


         }

         if (data.equals("statedetail")){
             dialogState!!.dismiss()
             val jsonObject = stateArrayList.getJSONObject(position)
             Log.e(TAG,"FK_States   "+jsonObject.getString("FK_States"))
             FK_States = jsonObject.getString("FK_States")
             edtState!!.setText(jsonObject.getString("States"))

             FK_District = ""
             FK_Area     = ""
             FK_Place    = ""
             FK_Post     = ""

             edtDistrict!!.setText("")
             edtPost!!.setText("")
             edtPincode!!.setText("")
             edtLandLine!!.setText("")


         }

         if (data.equals("districtdetail")){
             dialogDistrict!!.dismiss()
             val jsonObject = districtArrayList.getJSONObject(position)
             Log.e(TAG,"FK_District   "+jsonObject.getString("FK_District"))
             FK_District = jsonObject.getString("FK_District")
             edtDistrict!!.setText(jsonObject.getString("District"))

             FK_Area     = ""
             FK_Place    = ""
             FK_Post     = ""

             edtPost!!.setText("")
             edtPincode!!.setText("")
             edtLandLine!!.setText("")


         }

         if (data.equals("postdetail")){
             dialogPost!!.dismiss()
             val jsonObject = postArrayList.getJSONObject(position)
             Log.e(TAG,"FK_Post  3672    "+jsonObject.getString("FK_Post"))
             Log.e(TAG,"PinCode  3672    "+jsonObject.getString("PinCode"))
             FK_Post = jsonObject.getString("FK_Post")
             edtPost!!.setText(jsonObject.getString("PostName"))
             edtPincode!!.setText(jsonObject.getString("PinCode"))

             edtLandLine!!.setText("")


         }

         if (data.equals("leadedit")){

            // Toast.makeText(applicationContext,"Lead Edit selection",Toast.LENGTH_SHORT).show()
             dialogLeadEdit!!.dismiss()

//             llCustSearch!!.visibility = View.GONE
//             btnSubmit!!.setText("Update")
//             saveUpdateMode = "1"  //Update
//             rltvPinCode!!.visibility = View.VISIBLE

             val jsonObject = leadEditArrayList.getJSONObject(position)
             Log.e(TAG,"ID_LeadGenerate   "+jsonObject.getString("ID_LeadGenerate"))

             ID_LeadGenerate = jsonObject.getString("ID_LeadGenerate")
             ID_LeadGenerateProduct = jsonObject.getString("ID_LeadGenerateProduct")


             //FK_Country = jsonObject.getString("FK_Country")
//             edtCustname!!.setText(jsonObject.getString("CustomerName"))
//             edtFollowdate!!.setText(jsonObject.getString("NextActionDate"))
//             txtleadby!!.text = jsonObject.getString("CollectedBy")
//
//             edtProdproduct!!.setText(jsonObject.getString("product"))

             getLeadEditDetail(ID_LeadGenerate,ID_LeadGenerateProduct)


         }

     }



    private fun LeadValidations(v : View) {
        Log.e(TAG,"LeadValidations  3732   "+ ID_Customer+"  "+ID_Customer!!.length)
        strComapnyName = edtCompanyName!!.text.toString()
        strContactPerson = edtContactPerson!!.text.toString()
        strContactNumber = edtContactNumber!!.text.toString()

        if (ID_Customer!!.equals("")){
            Customer_Mode = "0"
            Customer_Name = edtCustname!!.text.toString()
            Customer_Mobile = edtCustphone!!.text.toString()
            Customer_Email = edtCustemail!!.text.toString()
            Customer_Address = edtCustaddress!!.text.toString()
          //  Config.snackBars(context,v,"Search OR Add Customer ")
            if (Customer_Name.equals("")){
                Config.snackBars(context,v,"Enter Customer Name")
            }
            else if (Customer_Mobile.equals("")){
                Config.snackBars(context,v,"Enter Customer Mobile")
            }
            else if (Customer_Email.equals("")){
                Config.snackBars(context,v,"Enter Customer Email")
            }
            else if (Customer_Address.equals("")){
                Config.snackBars(context,v,"Enter Customer Address")
            }
//            else if (strComapnyName.equals("")){
//                Config.snackBars(context,v,"Enter Company Name")
//            }
            else{
                MoreValidations(v)
            }
        }
        else{
            Customer_Mode = "1"
//            if (strComapnyName.equals("")){
//                Config.snackBars(context,v,"Enter Company Name")
//            }
//            else{
//                MoreValidations(v)
//            }


            MoreValidations(v)
        }

    }

    private fun MoreValidations(v: View) {

        Log.e(TAG,"LeadValidations  37321"
                +"\n"+"Customer_Mode     : "+Customer_Mode
                +"\n"+"ID_Customer       : "+ID_Customer
                +"\n"+"Customer_Name     : "+ Customer_Name
                +"\n"+"Customer_Mobile   : "+ Customer_Mobile
                +"\n"+"Customer_Email    : "+ Customer_Email
                +"\n"+"Customer_Address  : "+ Customer_Address)

        strMoreLandPhone = edtLandLine!!.text.toString()

        if (FK_Country.equals("")){
            Config.snackBars(context,v,"Select Country")
        }
        else if (FK_States.equals("")){
            Config.snackBars(context,v,"Select State")
        }
        else if (FK_District.equals("")){
            Config.snackBars(context,v,"Select District")
        }
        else if (FK_Post.equals("")){
            Config.snackBars(context,v,"Select Post")
        }
//        else if (strMoreLandPhone.equals("")){
//            Config.snackBars(context,v,"Enter LandLine")
//        }
        else{
            ProductValidations(v)
        }


    }

    private fun ProductValidations(v: View) {
        Log.e(TAG,"MoreValidations  37322"
                +"\n"+"FK_Country        : "+FK_Country
                +"\n"+"FK_States         : "+FK_States
                +"\n"+"FK_District       : "+ FK_District
                +"\n"+"FK_Post           : "+ FK_Post
                +"\n"+"strMoreLandPhone  : "+ strMoreLandPhone)
        strQty = edtProdqty!!.text.toString()
        strFeedback = edtProdfeedback!!.text.toString()
        if (ID_Category.equals("")){
            Config.snackBars(context,v,"Select Category")
        }
        else if (ID_Product.equals("")){
            Config.snackBars(context,v,"Select Product")
        }
        else if (strQty.equals("")){
            Config.snackBars(context,v,"Enter Quantity ")
        }
        else if (ID_Priority.equals("")){
            Config.snackBars(context,v,"Select Priority")
        }
//        else if (strFeedback.equals("")){
//            Config.snackBars(context,v,"Enter Feedback ")
//        }
        else if (ID_Status.equals("")){
            Config.snackBars(context,v,"Select Status")
        }
        else if (ID_Status.equals("1")){
            Log.e(TAG,"ProductValidations  373221   "+ ID_Status)
            if (ID_NextAction.equals("")){
                Config.snackBars(context,v,"Select Followup Action")
            }
            else if (ID_ActionType.equals("")){
                Config.snackBars(context,v,"Select Followup Action type")
            }
            else if (strFollowupdate.equals("")){
                Config.snackBars(context,v,"Select Followup Date")
            }
            else{
               if (strNeedCheck.equals("1")){
                   if (ID_Branch.equals("")){
                       Config.snackBars(context,v,"Select Branch")
                   }
                   else if (ID_BranchType.equals("")){
                       Config.snackBars(context,v,"Select Branch Type")
                   }
                   else if (ID_Department.equals("")){
                       Config.snackBars(context,v,"Select Department")
                   }
                   else if (ID_Employee.equals("")){
                       Config.snackBars(context,v,"Select Employee")
                   }
                   else{
                       LocationValidation(v)
                   }
               }
               else{
                    LocationValidation(v)
               }
            }
        }
        else{
            Log.e(TAG,"ProductValidations  373222   "+ ID_Status)
            LocationValidation(v)
        }
    }

    private fun LocationValidation(v: View) {
        Log.e(TAG,"ProductValidations  37323"
                +"\n"+"ID_Category        : "+ ID_Category
                +"\n"+"ID_Product         : "+ ID_Product
                +"\n"+"strQty             : "+ strQty
                +"\n"+"ID_Priority        : "+ ID_Priority
                +"\n"+"strFeedback        : "+ strFeedback
                +"\n"+"ID_Status          : "+ ID_Status
                +"\n"+"ID_NextAction      : "+ ID_NextAction
                +"\n"+"ID_ActionType      : "+ ID_ActionType
                +"\n"+"strFollowupdate    : "+ strFollowupdate
                +"\n"+"strNeedCheck       : "+ strNeedCheck
                +"\n"+"ID_Branch          : "+ ID_Branch
                +"\n"+"ID_BranchType      : "+ ID_BranchType
                +"\n"+"ID_Department      : "+ ID_Department
                +"\n"+"ID_Employee        : "+ ID_Employee)

        if (strLatitude.equals("") && strLongitue.equals("")){
            Config.snackBars(context,v,"Select Location")
        }
        else if (strDate.equals("")){
            Config.snackBars(context,v,"Select Date")
        }
        else if (ID_LeadFrom.equals("")){
            Config.snackBars(context,v,"Select Lead From")
        }
        else if (ID_LeadThrough.equals("")){
            Config.snackBars(context,v,"Select Lead Through")
        }
        else if (ID_CollectedBy.equals("")){
            Config.snackBars(context,v,"Select Collected By")
        }
//        else if (ID_MediaMaster.equals("")){
//            Config.snackBars(context,v,"Select Media Type")
//        }
        else{
            Log.e(TAG,"LocationValidation  37324"
                    +"\n"+"strLatitude        : "+ strLatitude
                    +"\n"+"strLongitue        : "+ strLongitue
                    +"\n"+"locAddress         : "+ locAddress+","+ locCity+","+ locState+","+ locCountry+","+ locpostalCode
                    +"\n"+"strDate            : "+ strDate
                    +"\n"+"ID_LeadFrom        : "+ ID_LeadFrom
                    +"\n"+"ID_LeadThrough     : "+ ID_LeadThrough
                    +"\n"+"ID_CollectedBy     : "+ ID_CollectedBy
                    +"\n"+"ID_MediaMaster     : "+ ID_MediaMaster)

            if(image1.equals(""))
            {
                encode1 = ""
            }
            else
            {
                val bitmap = BitmapFactory.decodeFile(image1)
                val stream =  ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    encode1 = Base64.getEncoder().encodeToString(stream.toByteArray());
                } else {
                    encode1 = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
                }
            }
            if(image2.equals(""))
            {
                encode2 = ""
            }
            else
            {
                val bitmap = BitmapFactory.decodeFile(image2)
                val stream =  ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    encode2 = Base64.getEncoder().encodeToString(stream.toByteArray())
                } else {
                    encode2 = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
                }
            }

            Log.e(TAG,"LocationValidation  encode1  373241   "+ encode1)
            Log.e(TAG,"LocationValidation  encode2  373241   "+ encode2)

            LeadConfirmationPopup()

          //  saveLeadGeneration()
        }
    }

    private fun LeadConfirmationPopup() {
        try {

            val dialogConfirmPop = Dialog(this)
            dialogConfirmPop!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogConfirmPop!! .setContentView(R.layout.lead_generation_confirmation_popup)
            dialogConfirmPop!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;



            val tvp_lead_date        = dialogConfirmPop!! .findViewById(R.id.tvp_lead_date) as TextView
            val tvp_lead_from        = dialogConfirmPop!! .findViewById(R.id.tvp_lead_from) as TextView
            val tvp_lead_through     = dialogConfirmPop!! .findViewById(R.id.tvp_lead_through) as TextView
            val tvp_collected_by     = dialogConfirmPop!! .findViewById(R.id.tvp_collected_by) as TextView

            val tvp_cust_name        = dialogConfirmPop!! .findViewById(R.id.tvp_cust_name) as TextView
            val tvp_cust_address     = dialogConfirmPop!! .findViewById(R.id.tvp_cust_address) as TextView
            val tvp_cust_email       = dialogConfirmPop!! .findViewById(R.id.tvp_cust_email) as TextView
            val tvp_cust_phone       = dialogConfirmPop!! .findViewById(R.id.tvp_cust_phone) as TextView
            val tvp_cust_coutry      = dialogConfirmPop!! .findViewById(R.id.tvp_cust_coutry) as TextView
            val tvp_cust_state       = dialogConfirmPop!! .findViewById(R.id.tvp_cust_state) as TextView
            val tvp_cust_district    = dialogConfirmPop!! .findViewById(R.id.tvp_cust_district) as TextView
            val tvp_cust_post        = dialogConfirmPop!! .findViewById(R.id.tvp_cust_post) as TextView
            val tvp_cust_pincode     = dialogConfirmPop!! .findViewById(R.id.tvp_cust_pincode) as TextView
            val tvp_cust_landline    = dialogConfirmPop!! .findViewById(R.id.tvp_cust_landline) as TextView

            val tvp_company_name     = dialogConfirmPop!! .findViewById(R.id.tvp_company_name) as TextView
            val tvp_contact_person   = dialogConfirmPop!! .findViewById(R.id.tvp_contact_person) as TextView
            val tvp_contact_number   = dialogConfirmPop!! .findViewById(R.id.tvp_contact_number) as TextView
            val tvp_media_type       = dialogConfirmPop!! .findViewById(R.id.tvp_media_type) as TextView

            val tvp_category         = dialogConfirmPop!! .findViewById(R.id.tvp_category) as TextView
            val tvp_product          = dialogConfirmPop!! .findViewById(R.id.tvp_product) as TextView
            val tvp_quantity         = dialogConfirmPop!! .findViewById(R.id.tvp_quantity) as TextView
            val tvp_priority         = dialogConfirmPop!! .findViewById(R.id.tvp_priority) as TextView
            val tvp_feedback         = dialogConfirmPop!! .findViewById(R.id.tvp_feedback) as TextView
            val tvp_status           = dialogConfirmPop!! .findViewById(R.id.tvp_status) as TextView
            val tvp_followup_action  = dialogConfirmPop!! .findViewById(R.id.tvp_followup_action) as TextView
            val tvp_followup_type    = dialogConfirmPop!! .findViewById(R.id.tvp_followup_type) as TextView
            val tvp_followup_date    = dialogConfirmPop!! .findViewById(R.id.tvp_followup_date) as TextView
            val tvp_branch_type      = dialogConfirmPop!! .findViewById(R.id.tvp_branch_type) as TextView
            val tvp_branch           = dialogConfirmPop!! .findViewById(R.id.tvp_branch) as TextView
            val tvp_department       = dialogConfirmPop!! .findViewById(R.id.tvp_department) as TextView
            val tvp_employee         = dialogConfirmPop!! .findViewById(R.id.tvp_employee) as TextView

            val tvp_location         = dialogConfirmPop!! .findViewById(R.id.tvp_location) as TextView

            val ll_cust_landline            = dialogConfirmPop!! .findViewById(R.id.ll_cust_landline) as LinearLayout
            val ll_MoreCommunicationInfo    = dialogConfirmPop!! .findViewById(R.id.ll_MoreCommunicationInfo) as LinearLayout
            val ll_company_name             = dialogConfirmPop!! .findViewById(R.id.ll_company_name) as LinearLayout
            val ll_contact_person           = dialogConfirmPop!! .findViewById(R.id.ll_contact_person) as LinearLayout
            val ll_contact_number           = dialogConfirmPop!! .findViewById(R.id.ll_contact_number) as LinearLayout
            val ll_media_type               = dialogConfirmPop!! .findViewById(R.id.ll_media_type) as LinearLayout

            val ll_prod_feedback            = dialogConfirmPop!! .findViewById(R.id.ll_prod_feedback) as LinearLayout
            val ll_followup_action          = dialogConfirmPop!! .findViewById(R.id.ll_followup_action) as LinearLayout
            val ll_followup_type            = dialogConfirmPop!! .findViewById(R.id.ll_followup_type) as LinearLayout
            val ll_followup_date            = dialogConfirmPop!! .findViewById(R.id.ll_followup_date) as LinearLayout
            val ll_branchtype               = dialogConfirmPop!! .findViewById(R.id.ll_branchtype) as LinearLayout
            val ll_branch                   = dialogConfirmPop!! .findViewById(R.id.ll_branch) as LinearLayout
            val ll_department               = dialogConfirmPop!! .findViewById(R.id.ll_department) as LinearLayout
            val ll_employee                 = dialogConfirmPop!! .findViewById(R.id.ll_employee) as LinearLayout

            val btnCancel                 = dialogConfirmPop!! .findViewById(R.id.btnCancel) as Button
            val btnOk                 = dialogConfirmPop!! .findViewById(R.id.btnOk) as Button

            Log.e(TAG,"")
            if (edtLandLine!!.text.toString().equals("")){
                ll_cust_landline!!.visibility = View.GONE
            }

            if (edtCompanyName!!.text.toString().equals("")){
                ll_company_name!!.visibility = View.GONE
            }

            if (edtContactPerson!!.text.toString().equals("")){
                ll_contact_person!!.visibility = View.GONE
            }

            if (edtContactNumber!!.text.toString().equals("")){
                ll_contact_number!!.visibility = View.GONE
            }
            if (txtMediatype!!.text.toString().equals("")){
                ll_media_type!!.visibility = View.GONE
            }

            if (edtCompanyName!!.text.toString().equals("") && edtContactPerson!!.text.toString().equals("") && edtContactNumber!!.text.toString().equals("") && txtMediatype!!.text.toString().equals("")){
                ll_MoreCommunicationInfo!!.visibility = View.GONE
            }


            if (edtProdfeedback!!.text.toString().equals("")){
                ll_prod_feedback!!.visibility = View.GONE
            }

            if (edtProdfeedback!!.text.toString().equals("")){
                ll_prod_feedback!!.visibility = View.GONE
            }

            if (!ID_Status.equals("1")){
                ll_followup_action!!.visibility = View.GONE
                ll_followup_type!!.visibility = View.GONE
                ll_followup_date!!.visibility = View.GONE
            }


            tvp_lead_date.text = txtDate!!.text.toString()
            tvp_lead_from.text = txtleadfrom!!.text.toString()
            tvp_lead_through.text = txtleadthrough!!.text.toString()
            tvp_collected_by.text = txtleadby!!.text.toString()

            tvp_cust_name.text = edtCustname!!.text.toString()
            tvp_cust_address.text = edtCustaddress!!.text.toString()
            tvp_cust_phone.text = edtCustphone!!.text.toString()
            tvp_cust_email.text = edtCustemail!!.text.toString()
            tvp_cust_coutry.text = edtCountry!!.text.toString()
            tvp_cust_state.text = edtState!!.text.toString()
            tvp_cust_district.text = edtDistrict!!.text.toString()
            tvp_cust_post.text = edtPost!!.text.toString()
            tvp_cust_pincode.text = edtPincode!!.text.toString()
            tvp_cust_landline.text = edtLandLine!!.text.toString()


            tvp_company_name.text = edtCompanyName!!.text.toString()
            tvp_contact_person.text = edtContactPerson!!.text.toString()
            tvp_contact_number.text = edtContactNumber!!.text.toString()
            tvp_media_type.text = txtMediatype!!.text.toString()

            tvp_category.text = edtProdcategory!!.text.toString()
            tvp_product.text = edtProdproduct!!.text.toString()
            tvp_quantity.text = edtProdqty!!.text.toString()
            tvp_priority.text = edtProdpriority!!.text.toString()
            tvp_feedback.text = edtProdfeedback!!.text.toString()
            tvp_status.text = edtProdstatus!!.text.toString()
            tvp_followup_action.text = edtFollowaction!!.text.toString()
            tvp_followup_type.text = edtFollowtype!!.text.toString()
            tvp_followup_date.text = edtFollowdate!!.text.toString()
            tvp_branch_type.text = edtbarnchtype!!.text.toString()
            tvp_branch.text = edtbranch!!.text.toString()
            tvp_department.text = edtdepartment!!.text.toString()
            tvp_employee.text = edtEmployee!!.text.toString()

            tvp_location.text = txtLocation!!.text.toString()

            btnCancel.setOnClickListener {
                dialogConfirmPop.dismiss()
            }

            btnOk.setOnClickListener {
                dialogConfirmPop.dismiss()
                saveLeadGeneration()
            }


            dialogConfirmPop!!.show()
            dialogConfirmPop!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveLeadGeneration() {
        var saveLeadGenDet = 0
        try {
            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(context, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                    leadGenerateSaveViewModel.saveLeadGenerate(this, strDate, ID_LeadFrom!!,
                        ID_LeadThrough!!, ID_CollectedBy!!, ID_Customer!!, Customer_Name!!, Customer_Address!!,
                        Customer_Mobile!!, Customer_Email!!,strComapnyName!!,"", ID_MediaMaster!!, FK_Country, FK_States,
                        FK_District, FK_Post, ID_Category!!, ID_Product!!,strProdName, strQty, ID_Priority!!,
                        strFeedback, ID_Status!!, ID_NextAction, ID_ActionType, strFollowupdate, ID_Branch,
                        ID_BranchType, ID_Department, ID_Employee, strLatitude!!, strLongitue!!, locAddress!!,
                        encode1, encode2,saveUpdateMode!!)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message


                            try {
//                            if (pinCodeDet == 0){
//                                pinCodeDet++
                                if (msg!!.length > 0) {
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   4120   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("UpdateLeadGeneration")
                                        val builder = AlertDialog.Builder(
                                            this@LeadGenerationActivity,
                                            R.style.MyDialogTheme
                                        )
//                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setMessage(jobjt.getString("ResponseMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->

                                            val i = Intent(this@LeadGenerationActivity, LeadActivity::class.java)
                                            startActivity(i)
                                            finish()
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()


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
                                //  }


                            }catch (e: Exception){

                                Log.e(TAG,"Exception  4133    "+e.toString())

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
        catch (e : Exception){
            Log.e(TAG,"Exception  226666    "+e.toString())
        }


    }


    private fun getLeadEditList(v: View) {
        var editLeadGenDet = 0
        try {
            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(context, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                    leadEditListViewModel.getLeadEditList(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message


                            try {
//                            if (pinCodeDet == 0){
//                                pinCodeDet++
                                if (msg!!.length > 0) {
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   4233   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("LeadGenerationDetails")
                                        leadEditArrayList = jobjt.getJSONArray("LeadGenerationDetailsList")
                                        if (leadEditArrayList.length()>0){
                                            if (editLeadGenDet == 0){
                                                editLeadGenDet++
                                                LeadEditDetailPopup(leadEditArrayList)
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
                                //  }


                            }catch (e: Exception){

                                Log.e(TAG,"Exception  4133    "+e.toString())

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
        catch (e : Exception){
            Log.e(TAG,"Exception  226666    "+e.toString())
        }

    }

    private fun LeadEditDetailPopup(leadEditArrayList: JSONArray) {

        try {

            dialogLeadEdit = Dialog(this)
            dialogLeadEdit!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadEdit!! .setContentView(R.layout.lead_editdetail_popup)
            dialogLeadEdit!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadDetail = dialogLeadEdit!! .findViewById(R.id.recyLeadDetail) as RecyclerView

            val lLayout = GridLayoutManager(this@LeadGenerationActivity, 1)
            recyLeadDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = LeadEditDetailAdapter(this@LeadGenerationActivity, leadEditArrayList)
            recyLeadDetail!!.adapter = adapter
            adapter.setClickListener(this@LeadGenerationActivity)

            dialogLeadEdit!!.show()
            dialogLeadEdit!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getLeadEditDetail(ID_LeadGenerate  :String,ID_LeadGenerateProduct : String) {
        var editLeadGenDet = 0
        try {
            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(context, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                    leadEditDetailViewModel.getLeadEditDetail(this, ID_LeadGenerate, ID_LeadGenerateProduct)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message


                            try {
//                            if (pinCodeDet == 0){
//                                pinCodeDet++
                                if (msg!!.length > 0) {
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   4362   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("LeadGenerationListDetails")

                                        Log.e(TAG,"ID_LeadGenerateProduct   4366   "+jobjt.getString("ID_LeadGenerateProduct"))

                                        llCustSearch!!.visibility = View.GONE
                                        btnSubmit!!.setText("Update")
                                        saveUpdateMode = "2"  //Update
                                        rltvPinCode!!.visibility = View.GONE

                                        ID_Customer = jobjt.getString("FK_Customer")
                                        edtCustname!!.setText(jobjt.getString("LgCusName"))
                                        edtCustphone!!.setText(jobjt.getString("LgCusMobile"))
                                        edtCustemail!!.setText(jobjt.getString("LgCusEmail"))
                                        edtCustaddress!!.setText(jobjt.getString("LgCusAddress"))

                                        edtCompanyName!!.setText(jobjt.getString("CusCompany"))
                                      //  edtContactPerson!!.setText(jobjt.getString("CusCompany"))
                                      //  edtContactNumber!!.setText(jobjt.getString("CusCompany"))

                                        FK_Country = jobjt.getString("FK_Country")
                                        FK_States = jobjt.getString("FK_States")
                                        FK_District = jobjt.getString("FK_District")
                                        FK_Post = jobjt.getString("FK_Post")

                                        edtCountry!!.setText(jobjt.getString("Country"))
                                        edtState!!.setText(jobjt.getString("States"))
                                        edtDistrict!!.setText(jobjt.getString("District"))
                                        edtPost!!.setText(jobjt.getString("Post"))
                                        edtLandLine!!.setText(jobjt.getString("CusPhone"))

                                        ID_Category = jobjt.getString("FK_Category")
                                        ID_Product = jobjt.getString("FK_Category")
                                        ID_Priority = jobjt.getString("FK_Category")
                                        ID_Status = jobjt.getString("FK_Category")

                                        edtProdcategory!!.setText(jobjt.getString("CategoryName"))
                                        edtProdproduct!!.setText(jobjt.getString("ProdName"))
                                        edtProdpriority!!.setText(jobjt.getString("PriorityName"))
                                        edtProdfeedback!!.setText(jobjt.getString("LgpDescription"))


                                        ///////////
                                        edtProdqty!!.setText(jobjt.getString("LgpPQuantity"))

                                        if (ID_Status.equals("1")){
                                            ID_NextAction = jobjt.getString("FK_NetAction")
                                            ID_ActionType = jobjt.getString("FK_ActionType")


                                            edtFollowaction!!.setText(jobjt.getString("ActionName"))
                                            edtFollowtype!!.setText(jobjt.getString("ActionTypeName"))
                                            edtFollowdate!!.setText(jobjt.getString("NextActionDate"))
                                            strFollowupdate = jobjt.getString("NextActionDate")

                                            val brName = jobjt.getString("BranchName")

                                            if (!brName.equals("")){

                                                strNeedCheck = "0"
                                                switchTransfer!!.isChecked = false
                                                ID_BranchType = ""
                                                ID_Branch = ""
                                                ID_Department=""
                                                ID_Employee=""

                                                edtbarnchtype!!.setText("")
                                                edtbranch!!.setText("")
                                                edtdepartment!!.setText("")
                                                edtEmployee!!.setText("")

                                            }
                                            else {
                                                strNeedCheck = "1"
                                                switchTransfer!!.isChecked = true
                                                ID_BranchType =  jobjt.getString("BranchTypeID")
                                                ID_Branch =  jobjt.getString("BranchID")
                                                ID_Department= jobjt.getString("FK_Departement")
                                                ID_Employee= jobjt.getString("FK_AssignEmp")

                                                edtbarnchtype!!.setText(jobjt.getString("BranchTypeName"))
                                                edtbranch!!.setText(jobjt.getString("BranchName"))
                                                edtdepartment!!.setText(jobjt.getString("DepartementName"))
                                                edtEmployee!!.setText(jobjt.getString("AssignEmp"))
                                            }

                                        }else{
                                            val sdf = SimpleDateFormat("dd-MM-yyyy")
                                            val currentDate = sdf.format(Date())
                                            ID_NextAction = ""
                                            ID_ActionType = ""
                                            edtFollowdate!!.setText(currentDate)
                                            strFollowupdate = currentDate

                                            strNeedCheck = "0"
                                            switchTransfer!!.isChecked = false
                                            ID_BranchType = ""
                                            ID_Branch = ""
                                            ID_Department=""
                                            ID_Employee=""

                                            edtbarnchtype!!.setText("")
                                            edtbranch!!.setText("")
                                            edtdepartment!!.setText("")
                                            edtEmployee!!.setText("")
                                        }

                                        strLatitude = jobjt.getString("LocLatitude")
                                        strLongitue = jobjt.getString("LocLongitude")
                                        txtLocation!!.setText(jobjt.getString("LocationAddress"))


//                                        txtDate!!.setText(jobjt.getString("LocLatitude"))
//                                        strDate = jobjt.getString("LocLatitude")

                                        ID_LeadFrom = jobjt.getString("FK_LeadFrom")
                                        txtleadfrom!!.setText(jobjt.getString("LeadFrom"))

                                        ID_LeadThrough = jobjt.getString("FK_LeadBy")
                                        txtleadthrough!!.setText(jobjt.getString("LeadBy"))

                                        ID_CollectedBy = jobjt.getString("LgCollectedBy")
                                        txtleadby!!.text= jobjt.getString("CollectedBy")

                                        ID_MediaMaster = jobjt.getString("FK_MediaMaster")
                                        txtMediatype!!.setText(jobjt.getString("MediaMaster"))

                                        val LandMark1=jobjt.getString("LocationLandMark1")


                                        if (!LandMark1!!.isEmpty()) {
                                            val decodedString = android.util.Base64.decode(LandMark1, android.util.Base64.DEFAULT)
                                            ByteArrayToBitmap(decodedString)
                                            val decodedByte = BitmapFactory.decodeByteArray(decodedString,0, decodedString.size)
                                            val stream = ByteArrayOutputStream()
                                            decodedByte.compress(Bitmap.CompressFormat.PNG,100, stream)
                                            Glide.with(this@LeadGenerationActivity) .load(stream.toByteArray()) .placeholder(R.drawable.uploadimg).error(R.drawable.uploadimg).into(imgvupload1!!)
                                        }
                                        val LandMark2=jobjt.getString("LocationLandMark2")
                                      //  Log.e(TAG,"LandMark21  4506   "+LandMark2)
                                        // WORKING
                                       // val LandMark2="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"

                                        if (!LandMark2!!.isEmpty()) {
                                            val decodedString = android.util.Base64.decode(LandMark2, android.util.Base64.DEFAULT)
                                            ByteArrayToBitmap(decodedString)
                                            val decodedByte = BitmapFactory.decodeByteArray(decodedString,0, decodedString.size)
                                            val stream = ByteArrayOutputStream()
                                            decodedByte.compress(Bitmap.CompressFormat.PNG,100, stream)
                                            Glide.with(this@LeadGenerationActivity) .load(stream.toByteArray()) .placeholder(R.drawable.uploadimg).error(R.drawable.uploadimg).into(imgvupload2!!)
                                        }



//                                        leadEditDetArrayList = jobjt.getJSONArray("LeadGenerationDetailsList")
//                                        if (leadEditDetArrayList.length()>0){
//                                            if (editLeadGenDet == 0){
////                                                editLeadGenDet++
////                                                LeadEditDetailPopup(leadEditArrayList)
//                                            }
//
//                                        }



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
                                //  }


                            }catch (e: Exception){

                                Log.e(TAG,"Exception  4133    "+e.toString())

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
        catch (e : Exception){
            Log.e(TAG,"Exception  226666    "+e.toString())
        }
    }

    private fun ByteArrayToBitmap(byteArray: ByteArray): Bitmap{
        val arrayInputStream = ByteArrayInputStream(byteArray)
        return BitmapFactory.decodeStream(arrayInputStream)
    }


}
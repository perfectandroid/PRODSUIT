package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.UriUtil
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*

import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Intimation : AppCompatActivity(), View.OnClickListener, ItemClickListener {
    lateinit var saveDocumentViewModel: SaveDocumentViewModel

    var tie_Attachments1: TextInputEditText? = null
    private var inputStreamImg1: InputStream? = null
    private var destination1: File? = null
    private var imgPath1: String? = null
    var documentPath1: String = ""
    private var bitmap1: Bitmap? = null
    var strImageName1: String = ""
    var encodeDoc1 : String = ""




    var shedulemode = 0

    var ID_first: String? = ""
    var ID_LeadFirst: String? = ""
    var ID_sec: String? = ""
    var ID_LeadSec: String? = ""
    private var source_lead          : RecyclerView? = null

    var ID_SearchBy: String? = "0"
    var Transmode: String? = ""

    private var dialog1: Dialog? = null

    private var lead_sourceName: String = ""
    var leadSource                 = 0
    lateinit var leadSourceViewModel : LeadSourceViewModel
    lateinit var leadSourceList: JSONArray
    private var dialogeLeadSource           : Dialog? = null
    private var recyclerLeadSource          : RecyclerView? = null
    lateinit var leadSorceSort         : JSONArray
    private var ID_LeadSource: String = ""
    private var tie_LeadSource  : TextInputEditText? = null
    private var tie_FromDate1  : TextInputEditText? = null
    var selectedFromDate: String = ""
    private var tie_ToDate  : TextInputEditText? = null
    var selectedToDate: String = ""
    private var tie_Category  : TextInputEditText? = null

    var category                 = 0
    lateinit var productCategoryViewModel: ProductCategoryViewModel
    lateinit var prodCategoryArrayList: JSONArray
    private var dialogProdCat: Dialog? = null
    var recyProdCategory: RecyclerView? = null
    var ID_Category: String? = ""
    lateinit var prodCatSort : JSONArray
    //..........reset
    private var txtReset  : TextView? = null
    private var txtSearch  : TextView? = null

    //..........lead from

    var LeadFromInfocount = 0
    lateinit var leadfromInfoViewModel: LeadFromInfoViewModel
    lateinit var leadfromInfoArrayList: JSONArray
    lateinit var leadfromInfosort: JSONArray
    var recy_comm: RecyclerView? = null
    private var dialogLeadinfo: Dialog? = null
    var ID_LeadInfo: String = ""

    private var tie_LeadFrom: TextInputEditText? = null
    private var til_LeadFrom: TextInputLayout? = null
    private var ID_FIELD: String = ""
    private var TransMode: String = ""
    private var ID_LeadFrom: String = "2"
    //......projectproduct
    private var tie_productproject  : TextInputEditText? = null
    private var til_productproject  : TextInputLayout? = null

    //......product
    private var tie_product  : TextInputEditText? = null
    private var til_product  : TextInputLayout? = null
    var proddetail = 0
    lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var prodDetailArrayList: JSONArray
    private var dialogProdDet: Dialog? = null
    var recyProdDetail: RecyclerView? = null
    lateinit var prodDetailSort: JSONArray

    var ID_Product: String = ""
    //............product type
    private var tie_ProductType  : TextInputEditText? = null
    var productTypeCount = 0
    lateinit var productTypeViewModel: ProductTypeViewModel
    lateinit var productTypeArrayList : JSONArray
    lateinit var productTypeSort : JSONArray
    private var dialogProductType : Dialog? = null
    var recyProdType: RecyclerView? = null
    var ID_ProductType: String? = ""
    private var ll_product  : LinearLayout? = null
    private var ll_project  : LinearLayout? = null
    //........
    private var tie_employeee  : TextInputEditText? = null
    var employee = 0
    var ID_Department: String = ""
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null
    lateinit var employeeSort: JSONArray
    var ID_Employee: String = ""
    //..........collected by
    private var tie_CollectedBy  : TextInputEditText? = null
    var countLeadBy = 0
    lateinit var leadByViewModel: LeadByViewModel
    lateinit var leadByArrayList: JSONArray

    var dialogLeadBy: Dialog? = null
    lateinit var leadBySort: JSONArray
    var recyLeadby: RecyclerView? = null
    var ID_CollectedBy: String? = ""
    //.....area
    private var tie_areaa  : TextInputEditText? = null
    var areaDet = 0
    lateinit var areaViewModel: AreaViewModel

    lateinit var areaArrayList: JSONArray
    private var dialogArea: Dialog? = null
    lateinit var areaSort: JSONArray
    var ID_Area: String? = ""
    //...........follow up action
    private var tie_FollowUpAction  : TextInputEditText? = null
    var followUpAction = 0
    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList: JSONArray
    private var dialogFollowupAction: Dialog? = null
    var recyFollowupAction: RecyclerView? = null
    lateinit var followUpActionSort: JSONArray
    var ID_NextAction: String = ""
    //.......follow up action type
    private var tie_FollowUpType  : TextInputEditText? = null
    var followUpType = 0
    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    lateinit var followUpTypeArrayList: JSONArray
    private var dialogFollowupType: Dialog? = null
    lateinit var followUpTypeSort: JSONArray
    var recyFollowupType: RecyclerView? = null

    //............priority
    private var tie_Priority  : TextInputEditText? = null
    var prodpriority = 0
    lateinit var productPriorityViewModel: ProductPriorityViewModel
    lateinit var prodPriorityArrayList: JSONArray
    private var dialogProdPriority: Dialog? = null
    var recyProdPriority: RecyclerView? = null
    lateinit var prodPrioritySort: JSONArray
    var ID_Priority: String? = ""
    //............lead details


    private var tie_LeadDetails  : TextInputEditText? = null
    var leadDetails = 0
    lateinit var leadDetailViewModel: LeadDetailViewModel
    lateinit var leadDetailArrayList : JSONArray
    private var dialogleadDetail : Dialog? = null
    var recyleadDetail: RecyclerView? = null
    var recyleadDetail1: RecyclerView? = null
    lateinit var leadDetailSort : JSONArray
    private var ID_Lead_Details = "";
    //........lead details
    private var ll_leadENTRY  : LinearLayout? = null
    private var til_Lead_entry  : TextInputLayout? = null
    private var tie_Lead_entry  : TextInputEditText? = null
    var leadEntry: String = ""
    var ID_ActionType: String = ""
    //..........grid
    private var ll_gridData  : LinearLayout? = null
    //.......multiple lead
    lateinit var leadHistSort: JSONArray
    var recyLeadHist: RecyclerView? = null
    private var dialogHist: Dialog? = null
    lateinit var leadHistArrayList : JSONArray
    lateinit var leadHistViewModel: LeadHistViewModel
    private var add_multiple  : LinearLayout? = null
    var addMulti = 0
    private var Cust_Wise_ListArray = JSONArray()
    private var dialogCusMiseLead : Dialog? = null
    var recyleCusWise: RecyclerView? = null
    lateinit var cusLeadSort : JSONArray
    lateinit var gridListarray : JSONArray
    var gridSize: String = ""
    var fulllengthrcy: RecyclerView? = null
    //...................
    var ll_sheduled: LinearLayout? = null
    var tie_ScheduledDate: TextInputEditText? = null
    var ScheduledDate: String = ""
    var tie_ScheduledTime: TextInputEditText? = null
    var ScheduledTime: String = ""
    var img_filter: ImageView? = null

    //..................

    var tie_module: TextInputEditText? = null
    var tie_Branch: TextInputEditText? = null
    var tie_Channel: TextInputEditText? = null
    var tie_shedule: TextInputEditText? = null
    var tie_message: TextInputEditText? = null
    var tie_FromDate: TextInputEditText? = null
    var tie_Attachments: TextInputEditText? = null

    var til_shedule: TextInputLayout? = null
    var til_Channel: TextInputLayout? = null
    var til_module: TextInputLayout? = null
    var til_Branch: TextInputLayout? = null
    var imback: ImageView? = null
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var intimationModuleViewModel: IntimationModuleViewModel
    val TAG: String = "IntimationActivity"
    lateinit var intimationModuleArrayList: JSONArray
    private var dialogGrouping: Dialog? = null
    var recyGrouping: RecyclerView? = null
    lateinit var moduleSort: JSONArray
    lateinit var channelSort: JSONArray
    private var ID_module: String = ""
    private var ID_Branch: String = ""
    private var ID_Channel: String = ""
    private var ID_Shedule: String = ""
    var imagemodecount = 0
    var btnSubmit: Button? = null
    var btnReset: Button? = null
    lateinit var channelViewModel: ChannelViewModel
    lateinit var sheduleViewModel: SheduleViewModel
    lateinit var branchViewModel: BranchViewModel
    lateinit var sentIntimationViewModel: SentIntimationViewModel
    lateinit var branchArrayList: JSONArray
    lateinit var channelArrayList: JSONArray
    lateinit var sheduleArrayList: JSONArray
    lateinit var branchsort: JSONArray
    lateinit var shedulesort: JSONArray
    private var dialogBranch: Dialog? = null
    var recyBranch: RecyclerView? = null
    private var MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1
    private var PICK_IMAGE_CAMERA = 1
    private var PICK_IMAGE_GALLERY = 2
    private var PICK_DOCUMRNT_GALLERY = 3
    private var PERMISSION_CAMERA = 1
    private val PICK_DOCUMENT_REQUEST_CODE = 123
    private var inputStreamImg: InputStream? = null
    private var imgPath: String? = null
    private var bitmap: Bitmap? = null
    var documentPath: String = ""
    private val GALLERY = 1
    private val CAMERA = 2
    private var image = ""
    private var destination: File? = null
    private val PERMISSION_REQUEST_CODE = 200
    var selectedDate: String = ""
    var strImageName: String = ""
    var encodeDoc : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_intimation)
        context = this@Intimation
        intimationModuleViewModel =
            ViewModelProvider(this).get(IntimationModuleViewModel::class.java)
        branchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        channelViewModel = ViewModelProvider(this).get(ChannelViewModel::class.java)
        sheduleViewModel = ViewModelProvider(this).get(SheduleViewModel::class.java)
        sentIntimationViewModel = ViewModelProvider(this).get(SentIntimationViewModel::class.java)
        leadSourceViewModel = ViewModelProvider(this).get(LeadSourceViewModel::class.java)
        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        leadfromInfoViewModel = ViewModelProvider(this).get(LeadFromInfoViewModel::class.java)
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        leadByViewModel = ViewModelProvider(this).get(LeadByViewModel::class.java)
        areaViewModel = ViewModelProvider(this).get(AreaViewModel::class.java)
        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        productPriorityViewModel = ViewModelProvider(this).get(ProductPriorityViewModel::class.java)
        leadDetailViewModel = ViewModelProvider(this).get(LeadDetailViewModel::class.java)
        productTypeViewModel = ViewModelProvider(this).get(ProductTypeViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        leadHistViewModel = ViewModelProvider(this).get(LeadHistViewModel::class.java)
        saveDocumentViewModel = ViewModelProvider(this).get(SaveDocumentViewModel::class.java)
        setReg()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_FromDate!!.setText(currentDate)
     //   tie_ScheduledDate!!.setText(currentDate)



//        val sdf1 = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
//        val currentDate1 = sdf1.format(Date())
//        val newDate: Date = sdf1.parse(currentDate1)
//        val sdfTime1 = SimpleDateFormat("hh:mm aa")
//        tie_ScheduledTime!!.setText(""+sdfTime1.format(newDate))
        getShedule()

        val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val BranchSP = context.getSharedPreferences(Config.SHARED_PREF45, 0)
        ID_Branch = FK_BranchSP.getString("FK_Branch", null).toString()
        tie_Branch!!.setText(BranchSP.getString("BranchName", null))

    }

    private fun setReg() {

        tie_Attachments1       = findViewById(R.id.tie_Attachments1) as TextInputEditText

        tie_Attachments1!!.setOnClickListener(this)

        gridListarray = JSONArray()
        ll_gridData          = findViewById(R.id.ll_gridData)          as LinearLayout
        fulllengthrcy          = findViewById(R.id.fulllengthrcy)          as RecyclerView

        ll_sheduled = findViewById(R.id.ll_sheduled)
        tie_ScheduledDate = findViewById(R.id.tie_ScheduledDate)
        tie_ScheduledTime = findViewById(R.id.tie_ScheduledTime)
        img_filter = findViewById(R.id.img_filter)
        //.................
        til_Branch = findViewById(R.id.til_Branch)
        tie_Branch = findViewById(R.id.tie_Branch)
        tie_module = findViewById(R.id.tie_module)
        til_module = findViewById(R.id.til_module)
        til_Channel = findViewById(R.id.til_Channel)
        tie_Channel = findViewById(R.id.tie_Channel)
        tie_shedule = findViewById(R.id.tie_shedule)
        tie_FromDate = findViewById(R.id.tie_FromDate)
        tie_message = findViewById(R.id.tie_message)
        tie_Attachments = findViewById(R.id.tie_Attachments)
        til_shedule = findViewById(R.id.til_shedule)
        imback = findViewById(R.id.imback)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)
        tie_module!!.setOnClickListener(this)
        tie_Branch!!.setOnClickListener(this)
        tie_Channel!!.setOnClickListener(this)
        tie_shedule!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        tie_Attachments!!.setOnClickListener(this)
        tie_FromDate!!.setOnClickListener(this)

        //............
        tie_ScheduledDate!!.setOnClickListener(this)
        tie_ScheduledTime!!.setOnClickListener(this)
        img_filter!!.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tie_Attachments -> {
                    imagemodecount = 0
                    selectImage()
                }
                R.id.imback -> {
                    finish()
                }
                R.id.tie_module -> {
                    Config.disableClick(v)
                    getModule()
                }
                R.id.tie_Branch -> {
                    Config.disableClick(v)
                    getBranch()
                }
                R.id.tie_Channel -> {
                    Config.disableClick(v)
                    getChannel()
                }
                R.id.tie_shedule -> {
                    Config.disableClick(v)
                    shedulemode=1
                    getShedule()
                }
                R.id.btnSubmit -> {
                    Config.disableClick(v)
                    validateData(v)
                }
                R.id.btnReset -> {
                    Config.disableClick(v)
                    reset()
                }
                R.id.tie_FromDate -> {
                    datePicker()
                }
                R.id.tie_ScheduledDate -> {
                  //  datePicker(tie_ScheduledDate)

                    datePickerSheduleDate()
                }
                R.id.tie_ScheduledTime -> {
                    Config.disableClick(v)

                    openBottomTime()
                }R.id.img_filter -> {


                filterBottomData()
                }

                R.id.tie_Attachments1->{
                    selectImage1()
                }

            }
        }
    }

    private fun browseDocuments1() {


        val mimetypes = arrayOf(
            "application/*",  //"audio/*",
            "font/*",  //"image/*",
            "message/*",
            "model/*",
            "multipart/*",
            "text/*"
        )

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        //   val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        //   intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes) //Important part here
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, PICK_DOCUMRNT_GALLERY)
    }

    private fun selectImage1() {
        try {
            val pm = packageManager
            val hasPerm = pm.checkPermission(Manifest.permission.CAMERA, packageName)
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {


                if (Build.VERSION.SDK_INT >= 33) {
                    //ActivityCompat.requestPermissions(this,String[]{readMediaAudio},PERMISSION_CODE)
                    Log.e(TAG, "222399912   ")
                    if (Config.check13Permission(context)) {
                        Log.e(TAG, "222399913   ")

                        val options = arrayOf<CharSequence>(
                            "Take Photo",
                            "Choose From Gallery",
                            "Choose Document",
                            "Cancel"
                        )
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("Select Option")
                        builder.setItems(options,
                            DialogInterface.OnClickListener { dialog, item ->
                                if (options[item] == "Take Photo") {
                                    dialog.dismiss()
                                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    startActivityForResult(intent, PICK_IMAGE_CAMERA)
                                } else if (options[item] == "Choose From Gallery") {
                                    dialog.dismiss()
                                    val pickPhoto = Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    )
                                    startActivityForResult(pickPhoto,  PICK_IMAGE_GALLERY)
                                } else if (options[item] == "Choose Document") {
                                    browseDocuments1()
                                } else if (options[item] == "Cancel") {
                                    dialog.dismiss()
                                }
                                dialog.dismiss()
                            })
                        builder.show()
                    }
//                    ActivityCompat.requestPermissions(this, arrayOf(readMediaAudio,readMediaImages,readMediaVideo), PERMISSION_CODE)


                } else {
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
                        val options = arrayOf<CharSequence>(
                            "Take Photo",
                            "Choose From Gallery",
                            "Choose Document",
                            "Cancel"
                        )
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("Select Option")
                        builder.setItems(options,
                            DialogInterface.OnClickListener { dialog, item ->
                                if (options[item] == "Take Photo") {
                                    dialog.dismiss()
                                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    startActivityForResult(intent, PICK_IMAGE_CAMERA)
                                } else if (options[item] == "Choose From Gallery") {
                                    dialog.dismiss()
                                    val pickPhoto = Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    )
                                    startActivityForResult(pickPhoto,  PICK_IMAGE_GALLERY)
                                } else if (options[item] == "Choose Document") {
                                    browseDocuments1()
                                } else if (options[item] == "Cancel") {
                                    dialog.dismiss()
                                }
                                dialog.dismiss()
                            })
                        builder.show()
                    }
                }

            } else ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA
            )
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }



    private fun openBottomTime() {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_timer, null)

        Log.e(TAG,"openBottomTime 2246  ")
        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)

        val time_Picker1 = view.findViewById<TimePicker>(R.id.time_Picker1)




        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
//            dialog.dismiss()
            try {


                val hr = time_Picker1!!.hour
                val min = time_Picker1!!.minute
                val input = ""+hr+":"+min
                val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
                val outputDateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
                val date: Date = inputDateFormat.parse(input)
                val output = outputDateFormat.format(date)

                tie_ScheduledTime!!.setText(output)
                ScheduledTime=output
                dialog.dismiss()

            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun validateData(v: View) {
        if (tie_FromDate!!.text.toString().equals("")) {
            Config.snackBars(context, v, "Select Date")
        } else if (ID_module.equals("")) {
            Config.snackBars(context, v, "Select Module")
        } else if (ID_Branch.equals("")) {
            Config.snackBars(context, v, "Select Branch")
        } else if (ID_Channel.equals("")) {
            Config.snackBars(context, v, "Select Channel")
        } else if (ID_Shedule.equals("")) {
            Config.snackBars(context, v, "Select Shedule Type")

        }

            else  if (ID_Shedule.equals("2"))
            {
//                ScheduledTime=""
//                ScheduledDate=""
                if (tie_ScheduledDate!!.text.toString().equals(""))
                {
                    Config.snackBars(context, v, "Select Sheduled Date")
                }
                else if (tie_ScheduledTime!!.text.toString().equals(""))
                {
                    Config.snackBars(context, v, "Select Sheduled Time")
                }
                else
                {
                    Log.e(TAG,"tyfhtfhgfghfg first")
                    docUploadValidation(v)
                }

            }

        else {
            Log.e(TAG,"tyfhtfhgfghfg sec")
           docUploadValidation(v)
        }

    }

    private fun docUploadValidation(v: View) {
        val message = tie_message!!.text.toString()
        strImageName = tie_Attachments!!.text.toString()
      //  strImageName1 = tie_Attachments1!!.text.toString()
        var extension=""



        if (strImageName.equals("") && message.equals("")) {
            Config.snackBars(context, v, "Select Documents Or Add Message")
        } else if (documentPath.equals("") && message.equals("")) {
            Config.snackBars(context, v, "Pick Documents Or Add Message")
        } else
        {
            try {
                val inputStream: InputStream = FileInputStream(documentPath)
                val bos = ByteArrayOutputStream()
                val b = ByteArray(1024 * 8)
                var bytesRead = 0
                while (inputStream.read(b).also { bytesRead = it } != -1) {
                    bos.write(b, 0, bytesRead)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    encodeDoc = Base64.getEncoder().encodeToString(stream.toByteArray());
                    encodeDoc = Base64.getEncoder().encodeToString(bos.toByteArray());
                } else {
                    //  encodeDoc = android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.DEFAULT)
                    encodeDoc = android.util.Base64.encodeToString(
                        bos.toByteArray(),
                        android.util.Base64.DEFAULT
                    )
                }
                extension= documentPath.substring(documentPath.lastIndexOf("."))
                Log.e(TAG, "encodeDoc_intimation   5083333   " + encodeDoc)
                Log.e(TAG, "encodeDoc_intimation   5083333   " + extension)

            } catch (e: Exception) {

            }


            sentIntimation(tie_FromDate!!.text.toString(),ID_module,ID_Branch,ID_Channel,ID_Shedule,
                encodeDoc ,extension,message)


       //     saveDocuments("01-03-2024", documentPath, "description", encodeDoc,extension)
        }





    }




    private fun selectImage() {
        try {
            val pm = packageManager
            val hasPerm = pm.checkPermission(Manifest.permission.CAMERA, packageName)
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= 33) {
                    //ActivityCompat.requestPermissions(this,String[]{readMediaAudio},PERMISSION_CODE)
                    Log.e(TAG, "222399912   ")
                    if (Config.check13Permission(context)) {
                        Log.e(TAG, "222399913   ")

                        val options = arrayOf<CharSequence>(
                            "Take Photo",
                            "Choose From Gallery",
                            "Choose Document",
                            "Cancel"
                        )
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("Select Option")
                        builder.setItems(options,
                            DialogInterface.OnClickListener { dialog, item ->
                                if (options[item] == "Take Photo") {
                                    dialog.dismiss()
                                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    startActivityForResult(intent, PICK_IMAGE_CAMERA)
                                } else if (options[item] == "Choose From Gallery") {
                                    dialog.dismiss()
                                    val pickPhoto = Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    )
                                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
                                } else if (options[item] == "Choose Document") {
                                    // browseDocuments()
                                    //  browseFolders()

                                    pickDocument()
                                } else if (options[item] == "Cancel") {
                                    dialog.dismiss()
                                }
                                dialog.dismiss()
                            })
                        builder.show()
                    }
//                    ActivityCompat.requestPermissions(this, arrayOf(readMediaAudio,readMediaImages,readMediaVideo), PERMISSION_CODE)


                } else {
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
                        val options = arrayOf<CharSequence>(
                            "Take Photo",
                            "Choose From Gallery",
                            "Choose Document",
                            "Cancel"
                        )
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("Select Option")
                        builder.setItems(options,
                            DialogInterface.OnClickListener { dialog, item ->
                                if (options[item] == "Take Photo") {
                                    dialog.dismiss()
                                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    startActivityForResult(intent, PICK_IMAGE_CAMERA)
                                } else if (options[item] == "Choose From Gallery") {
                                    dialog.dismiss()
                                    val pickPhoto = Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    )
                                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
                                } else if (options[item] == "Choose Document") {
                                    // browseDocuments()
                                    //  browseFolders()

                                    pickDocument()
                                } else if (options[item] == "Cancel") {
                                    dialog.dismiss()
                                }
                                dialog.dismiss()
                            })
                        builder.show()
                    }
                }


            } else ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA
            )
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }


    private fun datePicker() {
        val builder = android.app.AlertDialog.Builder(this)
        val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater1.inflate(R.layout.alert_date_chooser, null)
        val txtCancel = layout.findViewById(R.id.txtCancel) as TextView
        val txtSubmit = layout.findViewById(R.id.txtSubmit) as TextView
        val date_Picker1 = layout.findViewById<DatePicker>(R.id.date_Picker1)
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        // Set the maximum date to the current date to prevent selecting future dates
        date_Picker1.maxDate = currentDate.timeInMillis
        builder.setView(layout)
        val alertDialog = builder.create()
        txtCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            alertDialog.dismiss()

            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }
                tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                selectedDate = strDay + "-" + strMonth + "-" + strYear
                tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        alertDialog.show()

    }
    private fun datePickerToDate() {
        val builder = android.app.AlertDialog.Builder(this)
        val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater1.inflate(R.layout.alert_date_chooser, null)
        val txtCancel = layout.findViewById(R.id.txtCancel) as TextView
        val txtSubmit = layout.findViewById(R.id.txtSubmit) as TextView
        val date_Picker1 = layout.findViewById<DatePicker>(R.id.date_Picker1)
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        // Set the maximum date to the current date to prevent selecting future dates
    //    date_Picker1.minDate = currentDate.timeInMillis
        builder.setView(layout)
        val alertDialog = builder.create()
        txtCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            alertDialog.dismiss()

            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }
//                this.tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
//                selectedDate = strDay + "-" + strMonth + "-" + strYear
//                this.tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)

                tie_ToDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                selectedToDate = strDay + "-" + strMonth + "-" + strYear
                tie_ToDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        alertDialog.show()
    }

    private fun datePickerFromDate() {
        val builder = android.app.AlertDialog.Builder(this)
        val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater1.inflate(R.layout.alert_date_chooser, null)
        val txtCancel = layout.findViewById(R.id.txtCancel) as TextView
        val txtSubmit = layout.findViewById(R.id.txtSubmit) as TextView
        val date_Picker1 = layout.findViewById<DatePicker>(R.id.date_Picker1)
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        // Set the maximum date to the current date to prevent selecting future dates
      //  date_Picker1.minDate = currentDate.timeInMillis
        builder.setView(layout)
        val alertDialog = builder.create()
        txtCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            alertDialog.dismiss()

            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }
//                this.tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
//                selectedDate = strDay + "-" + strMonth + "-" + strYear
//                this.tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)

                tie_FromDate1!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                selectedFromDate = strDay + "-" + strMonth + "-" + strYear
                tie_FromDate1!!.setText("" + strDay + "-" + strMonth + "-" + strYear)


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        alertDialog.show()
    }

    private fun datePickerSheduleDate() {
        val builder = android.app.AlertDialog.Builder(this)
        val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater1.inflate(R.layout.alert_date_chooser, null)
        val txtCancel = layout.findViewById(R.id.txtCancel) as TextView
        val txtSubmit = layout.findViewById(R.id.txtSubmit) as TextView
        val date_Picker1 = layout.findViewById<DatePicker>(R.id.date_Picker1)
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        // Set the maximum date to the current date to prevent selecting future dates
        date_Picker1.minDate = currentDate.timeInMillis
        builder.setView(layout)
        val alertDialog = builder.create()
        txtCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            alertDialog.dismiss()

            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }
//                this.tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
//                selectedDate = strDay + "-" + strMonth + "-" + strYear
//                this.tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)

                tie_ScheduledDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                ScheduledDate = strDay + "-" + strMonth + "-" + strYear
                tie_ScheduledDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        alertDialog.show()
    }
    private fun datePicker(tie_FromDate: TextInputEditText?) {
        val builder = android.app.AlertDialog.Builder(this)
        val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater1.inflate(R.layout.alert_date_chooser, null)
        val txtCancel = layout.findViewById(R.id.txtCancel) as TextView
        val txtSubmit = layout.findViewById(R.id.txtSubmit) as TextView
        val date_Picker1 = layout.findViewById<DatePicker>(R.id.date_Picker1)
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        // Set the maximum date to the current date to prevent selecting future dates
        date_Picker1.minDate = currentDate.timeInMillis
        builder.setView(layout)
        val alertDialog = builder.create()
        txtCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            alertDialog.dismiss()

            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }
//                this.tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
//                selectedDate = strDay + "-" + strMonth + "-" + strYear
//                this.tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)

                tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                selectedDate = strDay + "-" + strMonth + "-" + strYear
                tie_FromDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)


            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        alertDialog.show()

    }

    private fun pickDocument() {

//         val mimetypes = arrayOf(
//             "jpeg/*",
//             "jpg/*",
//             "png/*",
//             "docx/*",
//             "doc/*",
//             "xlsx/*",
//             "xls/*",
//             "pdf/*"
//         )

        val mimetypes = arrayOf(
            "image/*",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/pdf"
        )

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"  // All file types
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)

        startActivityForResult(intent, PICK_DOCUMENT_REQUEST_CODE)
    }

    private fun filterBottomData() {

        try {
            Log.e(TAG,"2323222323   ")
             dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.intemation_popup, null)
            (dialog1 as BottomSheetDialog).requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = (dialog1 as BottomSheetDialog).getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(true)


             txtReset          = view.findViewById(R.id.txtReset)          as TextView
             txtSearch         = view.findViewById(R.id.txtSearch)         as TextView
             tie_LeadSource    = view.findViewById(R.id.tie_LeadSource)    as TextInputEditText
             tie_LeadFrom      = view.findViewById(R.id.tie_LeadFrom)      as TextInputEditText
            til_LeadFrom      = view.findViewById(R.id.til_LeadFrom)      as TextInputLayout
             tie_FromDate1      = view.findViewById(R.id.tie_FromDate1)      as TextInputEditText
             tie_ToDate        = view.findViewById(R.id.tie_ToDate)        as TextInputEditText
             tie_Category      = view.findViewById(R.id.tie_Category)      as TextInputEditText

             tie_ProductType       = view.findViewById(R.id.tie_ProductType)       as TextInputEditText
             tie_productproject    = view.findViewById(R.id.tie_productproject)    as TextInputEditText
            ll_product          = view.findViewById(R.id.ll_product)          as LinearLayout
            ll_project          = view.findViewById(R.id.ll_project)          as LinearLayout

            til_productproject        = view.findViewById(R.id.til_productproject)        as TextInputLayout
             tie_employeee         = view.findViewById(R.id.tie_employeee)         as TextInputEditText
             tie_CollectedBy       = view.findViewById(R.id.tie_CollectedBy)       as TextInputEditText
             tie_areaa             = view.findViewById(R.id.tie_areaa)             as TextInputEditText
             tie_FollowUpAction    = view.findViewById(R.id.tie_FollowUpAction)    as TextInputEditText
             tie_FollowUpType      = view.findViewById(R.id.tie_FollowUpType)      as TextInputEditText
             tie_Priority          = view.findViewById(R.id.tie_Priority)          as TextInputEditText
             tie_LeadDetails       = view.findViewById(R.id.tie_Lea)               as TextInputEditText
             tie_Lead_entry        = view.findViewById(R.id.tie_Lead_entry)        as TextInputEditText
             til_Lead_entry        = view.findViewById(R.id.til_Lead_entry)        as TextInputLayout

             add_multiple          = view.findViewById(R.id.add_multiple)          as LinearLayout
        //    fulllengthrcy          = view.findViewById(R.id.fulllengthrcy)          as RecyclerView

            til_product          = view.findViewById(R.id.til_product)          as TextInputLayout
            tie_product        = view.findViewById(R.id.tie_product)        as TextInputEditText
            ll_leadENTRY          = view.findViewById(R.id.ll_leadENTRY)          as LinearLayout
        //    ll_gridData          = view.findViewById(R.id.ll_gridData)          as LinearLayout

            tie_LeadSource!!.setText(lead_sourceName)
            tie_Lead_entry!!.visibility=View.GONE
            ll_leadENTRY!!.visibility=View.INVISIBLE

//            if (ID_ProductType.equals("")||ID_ProductType.equals("2"))
//            {
//                til_productproject!!.setOnClickListener(View.OnClickListener {
//                    productTypeCount = 0
//                    getProductType()
//
//                })
//            }
//            else
//            {
//
//            }

            txtSearch!!.setOnClickListener(View.OnClickListener {

                leadEntry= tie_Lead_entry!!.text.toString()
                gridSize= gridListarray.length().toString()
                dialog1!!.dismiss()

            })
            txtReset!!.setOnClickListener(View.OnClickListener {
                resetDialog()

            })
            tie_product!!.setOnClickListener(View.OnClickListener {
                proddetail = 0
                getProductDetail(ID_Category!!)

            })

            tie_ProductType!!.setOnClickListener(View.OnClickListener {

             //   tie_productproject!!.setText("")
//                ID_Product=""
                productTypeCount = 0
                getProductType()

            })

            add_multiple!!.setOnClickListener(View.OnClickListener {
            /*    val jObject = JSONObject()
                val startingLength: Int = Cust_Wise_ListArray.length()

                for (i in startingLength - 1 downTo 0) {
                    Cust_Wise_ListArray.remove(i)
                }

                jObject.put("LeadNo", "LD-004")
                jObject.put("LeadDate", "01/04/2024")
                jObject.put("CusName", "Ragav")
                jObject.put("Mobile", "9895554")
                jObject.put("Email", "fourb4334@gmail,com")

                Cust_Wise_ListArray!!.put(jObject)
                val jObject1 = JSONObject()
                jObject1.put("LeadNo", "LD-00554")
                jObject1.put("LeadDate", "07/04/2024")
                jObject1.put("CusName", "gopu")
                jObject1.put("Mobile", "98955656554")
                jObject1.put("Email", "fouryty@gmail,com")
                Cust_Wise_ListArray!!.put(jObject1)
                Log.e(TAG,"cus list array 67676="+Cust_Wise_ListArray)



                val jObject3 = JSONObject()
                jObject3.put("LeadNo", "LD-00554")
                jObject3.put("LeadDate", "09/04/2024")
                jObject3.put("CusName", "gopqq")
                jObject3.put("Mobile", "656554")
                jObject3.put("Email", "fouryty@gmail,com")
                Cust_Wise_ListArray!!.put(jObject3)
                Log.e(TAG,"cus list array 67676="+Cust_Wise_ListArray)

                CusWiseListPopUp(Cust_Wise_ListArray) */

                addMulti=0
                getLeadHistory()
            })

            tie_LeadDetails!!.setOnClickListener(View.OnClickListener {
                leadDetails = 0
                getLeadDetails()
            })

            tie_Priority!!.setOnClickListener(View.OnClickListener {
                prodpriority = 0
                getProductPriority()
            })

            tie_FollowUpType!!.setOnClickListener(View.OnClickListener {

                followUpType = 0
                getFollowupType()

            })

            tie_FollowUpAction!!.setOnClickListener(View.OnClickListener {
                followUpAction = 0
                getFollowupAction()


            })

            tie_areaa!!.setOnClickListener(View.OnClickListener {

                areaDet = 0
                getArea()

            })

            tie_CollectedBy!!.setOnClickListener(View.OnClickListener {


                countLeadBy = 0
                getLeadBy()
            })

            tie_LeadSource!!.setOnClickListener(View.OnClickListener {
                leadSource = 0
                getLeadSource()
             //   getLeadSource1()

            })
            tie_FromDate1!!.setOnClickListener(View.OnClickListener {

            //    datePicker(tie_FromDate1)
                datePickerFromDate()
            })

            tie_ToDate!!.setOnClickListener(View.OnClickListener {

           //     datePicker(tie_ToDate)
                datePickerToDate()
            })

            tie_Category!!.setOnClickListener(View.OnClickListener {

                if (ID_ProductType.equals(""))
                {
                    Toast.makeText(this, "Select Product Type", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    category = 0
                    getCategory()
                }



            })

            tie_LeadFrom!!.setOnClickListener {

                if (ID_LeadSource.equals(""))
                {
             //       Config.snackBars(context, view, "Select Lead Source")
                    Toast.makeText(this, "Select Lead Source", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    LeadFromInfocount = 0
                    getLeadFromInfo()
                }



            }

            tie_employeee!!.setOnClickListener {

                employee = 0
                getEmployee()

            }



            dialog1!!.setContentView(view)
            dialog1!!.show()

           // dialog1.show()
        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }



    }



    private fun getLeadHistory() {
        Log.v("sfsdfdsfdsddd","ID_LeadSource  "+ID_LeadSource)
        Log.v("sfsdfdsfdsddd","ID_LeadInfo  "+ID_LeadInfo)
        Log.v("sfsdfdsfdsddd","selectedFromDate  "+selectedFromDate)
        Log.v("sfsdfdsfdsddd","selectedToDate  "+selectedToDate)

//        Log.v("sfsdfdsfdsddd","ID_Category  "+ID_Category)
//        Log.v("sfsdfdsfdsddd","ID_ProductType  "+ID_ProductType)
//        Log.v("sfsdfdsfdsddd","ID_Product  "+ID_Product)
//        Log.v("sfsdfdsfdsddd","ID_Employee  "+ID_Employee)
//        Log.v("sfsdfdsfdsddd","ID_CollectedBy  "+ID_CollectedBy)
//        Log.v("sfsdfdsfdsddd","ID_Area  "+ID_Area)
//
//
//        Log.v("sfsdfdsfdsddd","ID_NextAction  "+ID_NextAction)
//        Log.v("sfsdfdsfdsddd","ID_ActionType  "+ID_ActionType)
//        Log.v("sfsdfdsfdsddd","ID_Priority  "+ID_Priority)
//        Log.v("sfsdfdsfdsddd","ID_Lead_Details  "+ID_Lead_Details)
//        Log.v("sfsdfdsfdsddd","lead Entry  "+ tie_Lead_entry!!.text.toString())
//        Log.v("sfsdfdsfdsddd","Transmode  "+ Transmode)
//        Log.v("sfsdfdsfdsddd","product project  "+ tie_productproject!!.text.toString())

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadHistViewModel.getLeadHist(this,
                    ID_LeadSource,
                    ID_LeadInfo,
                    selectedFromDate,
                    selectedToDate,
                    ID_Category,
                    ID_ProductType,
                    ID_Product,

                    ID_Employee,
                    ID_CollectedBy,
                    ID_Area!!,
                    ID_NextAction,
                    ID_ActionType,
                    ID_Priority,
                    ID_Lead_Details,
                    tie_Lead_entry!!.text.toString(),
                    Transmode!!,
                    tie_productproject!!.text.toString()





                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            Log.e(TAG,"msgggfg   1224rrt   "+msg)
                            if (msg!!.length > 0) {
                                Log.e(TAG,"msgggfg   1224rrt   "+msg)
                                if (addMulti == 0){
                                    addMulti++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("LeadHistory")
                                        leadHistArrayList = jobjt.getJSONArray("LeadHistoryList")
                                        Log.e(TAG,"656565 array="+leadHistArrayList)
                                        if (leadHistArrayList.length()>0){

                                            leadHistPopup(leadHistArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@Intimation,
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
                        }catch (e :Exception){
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

    private fun leadHistPopup(leadHistArrayList: JSONArray) {


        try {

            dialogHist = Dialog(this)
            dialogHist!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogHist!!.setContentView(R.layout.leadhist_popup)
            dialogHist!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadHist = dialogHist!!.findViewById(R.id.recyLeadHist) as RecyclerView
            val etsearch = dialogHist!!.findViewById(R.id.etsearch) as EditText

            leadHistSort = JSONArray()
            for (k in 0 until leadHistArrayList.length()) {
                val jsonObject = leadHistArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                leadHistSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyLeadHist!!.layoutManager = lLayout as RecyclerView.LayoutManager?

            val adapter = LeadHistCusAdapter(this@Intimation, leadHistSort)
            recyLeadHist!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    leadHistSort = JSONArray()

                    for (k in 0 until leadHistArrayList.length()) {
                        val jsonObject = leadHistArrayList.getJSONObject(k)
                        Log.e(TAG,"object 343434"+jsonObject)
                        if (textlength <= jsonObject.getString("LeadNo").length) {
                            if (jsonObject.getString("LeadNo")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                                ||
                                jsonObject.getString("CustomerName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                                ||
                                jsonObject.getString("Mobile")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())

                            ) {
                                leadHistSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "employeeSort               7103    " + leadHistSort)
                    val adapter = LeadHistCusAdapter(this@Intimation, leadHistSort)
                    recyLeadHist!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogHist!!.show()
            dialogHist!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun resetDialog() {
        selectedToDate=""
        selectedFromDate=""

        ID_LeadSource=""
        tie_LeadSource!!.setText("")
        ID_LeadInfo=""
        tie_LeadFrom!!.setText("")
        tie_FromDate1!!.setText("")
        tie_ToDate!!.setText("")
        tie_ProductType!!.setText("")
        ID_ProductType=""
        tie_Category!!.setText("")
        ID_Category=""
        tie_product!!.setText("")
        tie_productproject!!.setText("")
        ID_Product=""

        tie_employeee!!.setText("")
        ID_Employee=""
        tie_CollectedBy!!.setText("")
        ID_CollectedBy=""
        tie_areaa!!.setText("")
        ID_Area=""
        tie_FollowUpAction!!.setText("")
        ID_NextAction=""
        tie_FollowUpType!!.setText("")
        ID_ActionType=""
        tie_Priority!!.setText("")
        ID_Priority=""
        tie_LeadDetails!!.setText("")
        ID_Lead_Details=""
        tie_Lead_entry!!.visibility=View.INVISIBLE
        ll_leadENTRY!!.visibility=View.INVISIBLE
        til_LeadFrom!!.visibility=View.VISIBLE
        ll_project!!.visibility=View.GONE


    }

    private fun getProductDetail(ID_Category: String) {
//         var proddetail = 0

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

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (proddetail == 0) {
                                    proddetail++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   227   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ProductDetailsList")
                                        prodDetailArrayList = jobjt.getJSONArray("ProductList")
                                        Log.e(TAG, "list   2274545   " + msg)
                                        if (prodDetailArrayList.length() > 0) {

                                            productDetailPopup(prodDetailArrayList)


                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@Intimation,
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

    private fun productDetailPopup(prodDetailArrayList: JSONArray) {

        try {

            dialogProdDet = Dialog(this)
            dialogProdDet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdDet!!.setContentView(R.layout.product_detail_popup)
            dialogProdDet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdDetail = dialogProdDet!!.findViewById(R.id.recyProdDetail) as RecyclerView
            val etsearch = dialogProdDet!!.findViewById(R.id.etsearch) as EditText

            prodDetailSort = JSONArray()
            for (k in 0 until prodDetailArrayList.length()) {
                val jsonObject = prodDetailArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodDetailSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?

            val adapter = ProductDetailAdapter(this@Intimation, prodDetailSort)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodDetailSort = JSONArray()

                    for (k in 0 until prodDetailArrayList.length()) {
                        val jsonObject = prodDetailArrayList.getJSONObject(k)
                        //if (textlength <= jsonObject.getString("ProductName").length) {
                        if (textlength > 0) {
                            if (jsonObject.getString("ProductName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim()) ||
                                jsonObject.getString("ProdBarcode")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())) {
                                prodDetailSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodDetailSort               7103    " + prodDetailSort)
                    val adapter = ProductDetailAdapter(this@Intimation, prodDetailSort)
                    recyProdDetail!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogProdDet!!.show()
            dialogProdDet!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    private fun getProductType() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productTypeViewModel.getProductType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (productTypeCount == 0){
                                    productTypeCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProductType")
                                        productTypeArrayList = jobjt.getJSONArray("ProductTypeList")
                                        Log.e(TAG,"656565 array="+productTypeArrayList)
                                        if (productTypeArrayList.length()>0){

                                            productTypePopup(productTypeArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@Intimation,
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
                        }catch (e :Exception){
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

    private fun productTypePopup(productTypeArrayList: JSONArray) {

        try {

            dialogProductType = Dialog(this)
            dialogProductType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProductType!! .setContentView(R.layout.product_category_popup)
            dialogProductType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdType = dialogProductType!!.findViewById(R.id.recyProdCategory) as RecyclerView
            val llsearch = dialogProductType!!.findViewById(R.id.llsearch) as LinearLayout
            val txt_head = dialogProductType!!.findViewById(R.id.txt_head) as TextView
            txt_head.text="Product Type"
            llsearch!!.visibility=View.GONE


            productTypeSort = JSONArray()
            for (k in 0 until productTypeArrayList.length()) {
                val jsonObject = productTypeArrayList.getJSONObject(k)
                productTypeSort.put(jsonObject)
            }


            try {
                val lLayout = GridLayoutManager(this@Intimation, 1)
                recyProdType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                val adapter = ProductTypeAdapter(this@Intimation, productTypeSort)
                recyProdType!!.adapter = adapter
                adapter.setClickListener(this@Intimation)
            }catch (e: Exception){
                Log.e(TAG,"Exception  1275   "+e.toString())
            }




            dialogProductType!!.show()
            dialogProductType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun CusWiseListPopUp(custWiseListarray: JSONArray) {

        try {

            dialogCusMiseLead = Dialog(this)
            dialogCusMiseLead!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogCusMiseLead!! .setContentView(R.layout.cus_wise_lead_popup)
            dialogCusMiseLead!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

            val etsearch = dialogCusMiseLead!! .findViewById(R.id.etsearch) as EditText
             recyleCusWise = dialogCusMiseLead!! .findViewById(R.id.recyleCusWise) as RecyclerView


            cusLeadSort = JSONArray()
            for (k in 0 until custWiseListarray.length()) {
                val jsonObject = custWiseListarray.getJSONObject(k)
                cusLeadSort.put(jsonObject)
            }


            try {
                val lLayout = GridLayoutManager(this@Intimation, 1)
                recyleCusWise!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                val adapter = CusLeadAdapter(this@Intimation, cusLeadSort)
                recyleCusWise!!.adapter = adapter
                adapter.setClickListener(this@Intimation)
            }catch (e: Exception){
                Log.e(TAG,"Exception  1275   "+e.toString())
            }


//            etsearch!!.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(p0: Editable?) {
//                }
//
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                }
//
//                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                    //  list_view!!.setVisibility(View.VISIBLE)
//                    val textlength = etsearch!!.text.length
//                    leadDetailSort = JSONArray()
//
//                    for (k in 0 until leadDetailArrayList.length()) {
//                        val jsonObject = leadDetailArrayList.getJSONObject(k)
//                        if (textlength <= jsonObject.getString("TodoListLeadDetailsName").length) {
//                            if (jsonObject.getString("TodoListLeadDetailsName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
//                                leadDetailSort.put(jsonObject)
//                            }
//
//                        }
//                    }
//
//                    Log.e(TAG,"leadDetailSort               7103    "+leadDetailSort)
//                    val adapter = LeadDetailAdapter(this@Intimation, leadDetailSort)
//                    recyleadDetail!!.adapter = adapter
//                    adapter.setClickListener(this@Intimation)
//                }
//            })

            dialogCusMiseLead!!.show()
            dialogCusMiseLead!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getLeadDetails() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadDetailViewModel.getLeadDetail(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (leadDetails == 0){
                                    leadDetails++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("TodoListLeadDetails")
                                        leadDetailArrayList = jobjt.getJSONArray("TodoListLeadDetailsList")
                                        if (leadDetailArrayList.length()>0){

                                            leadDetailPopup(leadDetailArrayList)
                                            //   employeeAllPopup(leadDetailArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@Intimation,
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
                        }catch (e :Exception){
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

    private fun leadDetailPopup(leadDetailArrayList: JSONArray) {
        try {

            dialogleadDetail = Dialog(this)
            dialogleadDetail!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogleadDetail!! .setContentView(R.layout.lead_pop_up)
            dialogleadDetail!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyleadDetail1 = dialogleadDetail!! .findViewById(R.id.detail_recy) as RecyclerView
            val etsearch = dialogleadDetail!! .findViewById(R.id.etsearch1) as EditText


            leadDetailSort = JSONArray()
            for (k in 0 until leadDetailArrayList.length()) {
                val jsonObject = leadDetailArrayList.getJSONObject(k)
                leadDetailSort.put(jsonObject)
            }


            try {
                val lLayout = GridLayoutManager(this@Intimation, 1)
                recyleadDetail1!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                val adapter = LeadDetailAdapter(this@Intimation, leadDetailSort)
                recyleadDetail1!!.adapter = adapter
                adapter.setClickListener(this@Intimation)
            }catch (e: Exception){
                Log.e(TAG,"Exception  1275   "+e.toString())
            }


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    leadDetailSort = JSONArray()

                    for (k in 0 until leadDetailArrayList.length()) {
                        val jsonObject = leadDetailArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("TodoListLeadDetailsName").length) {
                            if (jsonObject.getString("TodoListLeadDetailsName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                leadDetailSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"leadDetailSort               7103    "+leadDetailSort)
                    val adapter = LeadDetailAdapter(this@Intimation, leadDetailSort)
                    recyleadDetail1!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogleadDetail!!.show()
            dialogleadDetail!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getProductPriority() {
//         var prodpriority = 0
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
                            Log.e(TAG, "msg   353   " + msg)

                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("PriorityDetailsList")
                                prodPriorityArrayList = jobjt.getJSONArray("PriorityList")
                                if (prodPriorityArrayList.length() > 0) {
                                    if (prodpriority == 0) {
                                        prodpriority++


                                        productPriorityPopup(prodPriorityArrayList)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@Intimation,
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
//                             Toast.makeText(
//                                 applicationContext,
//                                 "Some Technical Issues.",
//                                 Toast.LENGTH_LONG
//                             ).show()
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
            dialogProdPriority!!.setContentView(R.layout.product_priority_popup)
            dialogProdPriority!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdPriority =
                dialogProdPriority!!.findViewById(R.id.recyProdPriority) as RecyclerView
            val etsearch = dialogProdPriority!!.findViewById(R.id.etsearch) as EditText

            prodPrioritySort = JSONArray()
            for (k in 0 until prodPriorityArrayList.length()) {
                val jsonObject = prodPriorityArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodPrioritySort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyProdPriority!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductPriorityAdapter(this@LeadGenerationActivity, prodPriorityArrayList)
            val adapter = ProductPriorityAdapter(this@Intimation, prodPrioritySort)
            recyProdPriority!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodPrioritySort = JSONArray()

                    for (k in 0 until prodPriorityArrayList.length()) {
                        val jsonObject = prodPriorityArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("PriorityName").length) {
                            if (jsonObject.getString("PriorityName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodPrioritySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "reportNamesort               7103    " + prodPrioritySort)
                    val adapter =
                        ProductPriorityAdapter(this@Intimation, prodPrioritySort)
                    recyProdPriority!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogProdPriority!!.show()
            dialogProdPriority!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
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

                                            followupTypePopup(followUpTypeArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@Intimation,
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

            followUpTypeSort = JSONArray()
            for (k in 0 until followUpTypeArrayList.length()) {
                val jsonObject = followUpTypeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpTypeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = FollowupTypeAdapter(this@LeadGenerationActivity, followUpTypeArrayList)
            val adapter = FollowupTypeAdapter(this@Intimation, followUpTypeSort)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

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

                    Log.e(TAG, "followUpTypeSort               7103    " + followUpTypeSort)
                    val adapter = FollowupTypeAdapter(this@Intimation, followUpTypeSort)
                    recyFollowupType!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
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
    private fun getFollowupAction() {
//         var followUpAction = 0
        var SubMode = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpActionViewModel.getFollowupAction(this, SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (followUpAction == 0) {
                                    followUpAction++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                        followUpActionArrayList =
                                            jobjt.getJSONArray("FollowUpActionDetailsList")
                                        if (followUpActionArrayList.length() > 0) {

                                            followUpActionPopup(followUpActionArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@Intimation,
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
    private fun followUpActionPopup(followUpActionArrayList: JSONArray) {

        try {

            dialogFollowupAction = Dialog(this)
            dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupAction!!.setContentView(R.layout.followup_action)
            dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupAction =
                dialogFollowupAction!!.findViewById(R.id.recyFollowupAction) as RecyclerView
            val etsearch = dialogFollowupAction!!.findViewById(R.id.etsearch) as EditText
            val txt_follwAction = dialogFollowupAction!!.findViewById(R.id.txt_follwAction) as TextView
            txt_follwAction.text="Follow Up Action"

            followUpActionSort = JSONArray()
            for (k in 0 until followUpActionArrayList.length()) {
                val jsonObject = followUpActionArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                followUpActionSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = FollowupActionAdapter(this@LeadGenerationActivity, followUpActionArrayList)
            val adapter = FollowupActionAdapter(this@Intimation, followUpActionSort)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    followUpActionSort = JSONArray()

                    for (k in 0 until followUpActionArrayList.length()) {
                        val jsonObject = followUpActionArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("NxtActnName").length) {
                            if (jsonObject.getString("NxtActnName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                followUpActionSort.put(jsonObject)
                            }

                        }
                    }


                    val adapter =
                        FollowupActionAdapter(this@Intimation, followUpActionSort)
                    recyFollowupAction!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getArea() {
//        var areaDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                areaViewModel.getArea(this, "")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (areaDet == 0) {
                                    areaDet++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   2353   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AreaDetails")
                                        areaArrayList = jobjt.getJSONArray("AreaDetailsList")

                                        if (areaArrayList.length() > 0) {

                                            areaDetailPopup(areaArrayList)


                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@Intimation,
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
                        } catch (e: Exception) {

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

    private fun areaDetailPopup(areaArrayList: JSONArray) {

        try {

            dialogArea = Dialog(this)
            dialogArea!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogArea!!.setContentView(R.layout.area_list_popup)
            dialogArea!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycArea = dialogArea!!.findViewById(R.id.recycArea) as RecyclerView
            val etsearch = dialogArea!!.findViewById(R.id.etsearch) as EditText

            areaSort = JSONArray()
            for (k in 0 until areaArrayList.length()) {
                val jsonObject = areaArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                areaSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@Intimation, 1)
            recycArea!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = PostDetailAdapter(this@LeadGenerationActivity, areaArrayList)
            val adapter = AreaDetailAdapter(this@Intimation, areaSort)
            recycArea!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    areaSort = JSONArray()

                    for (k in 0 until areaArrayList.length()) {
                        val jsonObject = areaArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Area").length) {
                            if (jsonObject.getString("Area")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                areaSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "areaSort               7103    " + areaSort)
                    val adapter = AreaDetailAdapter(this@Intimation, areaSort)
                    recycArea!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogArea!!.show()
            dialogArea!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialogArea!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    private fun getLeadBy() {

//        var countLeadBy = 0
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
                            Log.e(TAG, "msg   228   " + msg.length)
                            Log.e(TAG, "msg   228   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("CollectedByUsersList")
                                leadByArrayList = jobjt.getJSONArray("CollectedByUsers")
                                if (leadByArrayList.length() > 0) {
                                    if (countLeadBy == 0) {
                                        countLeadBy++
                                        leadByPopup(leadByArrayList)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@Intimation,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
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
            dialogLeadBy!!.setContentView(R.layout.lead_by_popup)
            dialogLeadBy!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyLeadby = dialogLeadBy!!.findViewById(R.id.recyLeadby) as RecyclerView
            val etsearch = dialogLeadBy!!.findViewById(R.id.etsearch) as EditText

            leadBySort = JSONArray()
            for (k in 0 until leadByArrayList.length()) {
                val jsonObject = leadByArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                leadBySort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyLeadby!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = LeadByAdapter(this@LeadGenerationActivity, leadByArrayList)
            val adapter = LeadByAdapter(this@Intimation, leadBySort)
            recyLeadby!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    leadBySort = JSONArray()

                    for (k in 0 until leadByArrayList.length()) {
                        val jsonObject = leadByArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Name").length) {
                            if (jsonObject.getString("Name")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                leadBySort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "leadBySort               7103    " + leadBySort)
                    val adapter = LeadByAdapter(this@Intimation, leadBySort)
                    recyLeadby!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogLeadBy!!.show()
            dialogLeadBy!!.getWindow()!!.setLayout(
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
                employeeViewModel.getEmployee(this, ID_Department)!!.observe(
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
                                            this@Intimation,
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

            employeeSort = JSONArray()
            for (k in 0 until employeeArrayList.length()) {
                val jsonObject = employeeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@Intimation, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

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

                    Log.e(TAG, "employeeSort               7103    " + employeeSort)
                    val adapter = EmployeeAdapter(this@Intimation, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
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

    private fun getLeadFromInfo() {
//        var branch = 0
     //   ID_LeadFrom=ID_LeadSource
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                leadfromInfoViewModel.getLeadFromInfo(this,ID_LeadSource,TransMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   639   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("LeadFromInfo")
                                    leadfromInfoArrayList = jobjt.getJSONArray("LeadFromList")
                                    if (leadfromInfoArrayList.length() > 0) {
                                        if (LeadFromInfocount == 0) {
                                            LeadFromInfocount++
                                            LeadinfoPopup(leadfromInfoArrayList)
                                        }

                                    }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@Intimation,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
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

    private fun LeadinfoPopup(leadfromInfoArrayList: JSONArray) {
        try {

            Log.e(TAG,"inside   ")
            dialogLeadinfo = Dialog(this)
            dialogLeadinfo!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogLeadinfo!!.setContentView(R.layout.commen_popup)
            dialogLeadinfo!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

            recy_comm = dialogLeadinfo!!.findViewById(R.id.recy_comm) as RecyclerView
            val etsearch = dialogLeadinfo!!.findViewById(R.id.etsearch) as EditText
            val txt_header = dialogLeadinfo!!.findViewById(R.id.txt_header) as TextView

            txt_header.setText("Lead From Info")

            leadfromInfosort = JSONArray()
            for (k in 0 until leadfromInfoArrayList.length()) {
                val jsonObject = leadfromInfoArrayList.getJSONObject(k)
                leadfromInfosort.put(jsonObject)
            }
            val lLayout = GridLayoutManager(this@Intimation, 1)
            recy_comm!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            //  val adapter = BranchAdapter(this@TicketReportActivity, leadfromInfoArrayList)
            val adapter = LeadFromInfoAdapter(this@Intimation, leadfromInfosort)
            recy_comm!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    leadfromInfosort = JSONArray()

                    for (k in 0 until leadfromInfoArrayList.length()) {
                        val jsonObject = leadfromInfoArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Name").length) {
                            if (jsonObject.getString("Name")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                leadfromInfosort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "leadfromInfosort               7103    " + leadfromInfosort)
                    val adapter = LeadFromInfoAdapter(this@Intimation, leadfromInfosort)
                    recy_comm!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogLeadinfo!!.show()
            dialogLeadinfo!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    private fun getCategory() {
//         var prodcategory = 0
        var ReqMode = "13"
        var SubMode = "0"

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                productCategoryViewModel.getProductCategory(this, ReqMode!!, SubMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (category == 0) {
                                    category++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   8255  " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                        prodCategoryArrayList = jobjt.getJSONArray("CategoryList")
                                        Log.e(TAG, "prodCategoryArrayList   825566   " + prodCategoryArrayList)
                                        if (prodCategoryArrayList.length() > 0) {

                                            productCategoryPopup(prodCategoryArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@Intimation,
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

    private fun productCategoryPopup(prodCategoryArrayList: JSONArray) {
        try {

            dialogProdCat = Dialog(this)
            dialogProdCat!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdCat!!.setContentView(R.layout.product_category_popup)
            dialogProdCat!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyProdCategory = dialogProdCat!!.findViewById(R.id.recyProdCategory) as RecyclerView
            val etsearch = dialogProdCat!!.findViewById(R.id.etsearch) as EditText
            val llsearch = dialogProdCat!!.findViewById(R.id.llsearch) as LinearLayout
//            llsearch!!.visibility=View.GONE


            prodCatSort = JSONArray()
            for (k in 0 until prodCategoryArrayList.length()) {
                val jsonObject = prodCategoryArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                prodCatSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyProdCategory!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductCategoryAdapter(this@LeadGenerationActivity, prodCategoryArrayList)
            val adapter = ProductCategoryAdapter1(this@Intimation, prodCatSort)
            recyProdCategory!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    prodCatSort = JSONArray()

                    for (k in 0 until prodCategoryArrayList.length()) {
                        val jsonObject = prodCategoryArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("CategoryName").length) {
                            if (jsonObject.getString("CategoryName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodCatSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodCategorySort               7103    " + prodCatSort)
                    val adapter =
                        ProductCategoryAdapter1(this@Intimation, prodCatSort)
                    recyProdCategory!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })



            dialogProdCat!!.show()
            dialogProdCat!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getLeadSource() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                leadSourceViewModel.getLeadSource(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            Log.e(TAG,"767676 msg="+msg)

                            if (leadSource == 0) {
                                leadSource++
                                progressDialog!!.dismiss()

                                val jObject = JSONObject(msg)

                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("LeadSourceDetails")
                                    leadSourceList=jobjt.getJSONArray("LeadSourceDetailsList")

                                    if (leadSourceList.length() > 0)
                                    {
                                    //    leadSourcePopup(leadSourceList)
                                        leadSourcePopup1(leadSourceList)
                                    }


                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@Intimation,
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

    private fun leadSourcePopup(leadSourceList: JSONArray) {
        try {

            dialogeLeadSource = Dialog(this)
            dialogeLeadSource!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogeLeadSource!!.setContentView(R.layout.lead_detail_popup)
            dialogeLeadSource!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyclerLeadSource = dialogeLeadSource!!.findViewById(R.id.recyclerLeadSource) as RecyclerView


//            prodDetailSort = JSONArray()
//            for (k in 0 until leadSourceList.length()) {
//                val jsonObject = leadSourceList.getJSONObject(k)
//                // reportNamesort.put(k,jsonObject)
//                prodDetailSort.put(jsonObject)
//            }

            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyclerLeadSource!!.layoutManager = lLayout as RecyclerView.LayoutManager?

            val adapter = LeadSourceAdapter(this@Intimation, leadSourceList)
            recyclerLeadSource!!.adapter = adapter
            adapter.setClickListener(this@Intimation)



            dialogeLeadSource!!.show()
            dialogeLeadSource!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun leadSourcePopup1(leadSourceList: JSONArray) {
        try {

            dialogeLeadSource = Dialog(this)
            dialogeLeadSource!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogeLeadSource!!.setContentView(R.layout.source_lead)
            dialogeLeadSource!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            source_lead = dialogeLeadSource!!.findViewById(R.id.source_lead) as RecyclerView
            val etsearch = dialogeLeadSource!!.findViewById(R.id.etsearch) as EditText

            leadSorceSort = JSONArray()
            for (k in 0 until leadSourceList.length()) {
                val jsonObject = leadSourceList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                leadSorceSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@Intimation, 1)
            source_lead!!.layoutManager = lLayout as RecyclerView.LayoutManager?

            val adapter = LeadSourceAdapter(this@Intimation, leadSorceSort)
            source_lead!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    leadSorceSort = JSONArray()

                    for (k in 0 until leadSourceList.length()) {
                        val jsonObject = leadSourceList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("LeadFromName").length) {
                            if (jsonObject.getString("LeadFromName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                leadSorceSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "leadfromInfosort               7103    " + leadSorceSort)
                    val adapter = LeadSourceAdapter(this@Intimation, leadSorceSort)
                    source_lead!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogeLeadSource!!.show()
            dialogeLeadSource!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



    private fun reset() {

        tie_Attachments1!!.setText("")
        shedulemode = 0
        getShedule()



        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_FromDate!!.setText(currentDate)
//        tie_Lead_entry!!.visibility=View.GONE
//        ll_leadENTRY!!.visibility=View.INVISIBLE
//        ll_gridData!!.visibility=View.GONE

        gridListarray = JSONArray()
        ll_gridData!!.visibility=View.GONE

//        val startingLength: Int = gridListarray.length()
//
//        for (i in startingLength - 1 downTo 0) {
//            gridListarray.remove(i)
//        }

        //................
//        tie_ScheduledDate!!.setText(currentDate)
        ll_sheduled!!.visibility=View.GONE
        img_filter!!.visibility=View.INVISIBLE

        //...............
        ScheduledTime=""
        ScheduledDate=""
        tie_ScheduledTime!!.setText("")

        tie_ScheduledDate!!.setText("")
        tie_module!!.setText("")
        tie_Branch!!.setText("")
        tie_Channel!!.setText("")
        tie_shedule!!.setText("")
        tie_message!!.setText("")
        tie_Attachments!!.setText("")

        ID_module = ""
        ID_Branch = ""
        ID_Channel = ""
        ID_Shedule = ""

        val FK_BranchSP = context.getSharedPreferences(Config.SHARED_PREF37, 0)
        val BranchSP = context.getSharedPreferences(Config.SHARED_PREF45, 0)
        ID_Branch = FK_BranchSP.getString("FK_Branch", null).toString()
        tie_Branch!!.setText(BranchSP.getString("BranchName", null))
    }

    private fun saveDocuments(strDate: String, strSubject: String, strDescription: String, encodeDoc: String?,extension : String) {

        var saveDoc = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                saveDocumentViewModel.saveDocuments(this,"4",strDate,strSubject,strDescription,encodeDoc!!,extension)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG,"msg   82   "+msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("AddDocument")

                                    val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/" + "."+context.getResources().getString(R.string.app_name))
                                    if (dir.isDirectory) {
                                        val children = dir.list()
                                        for (i in children.indices) {
                                            File(dir, children[i]).delete()
                                        }
                                    }


                                    val builder = AlertDialog.Builder(
                                        this@Intimation,
                                        R.style.MyDialogTheme
                                    )
                                    builder.setMessage(jobjt.getString("ResponseMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                    val i = Intent(this@AddDocumentActivity, AccountDetailsActivity::class.java)
//                                    startActivity(i)
//                                    finish()

                                        onBackPressed()

                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@Intimation,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }

                        }catch (e : Exception){
                            Toast.makeText(applicationContext, ""+e.toString(), Toast.LENGTH_SHORT).show()
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

    private fun sentIntimation(
        dated: String,
        ID_module: String,
        ID_Branch: String,
        ID_Channel: String,
        ID_Shedule: String,
        encodeDoc: String,
        extension: String,
        message: String
    ) {
        Log.v("sfsdfdsfdsddd","dated  "+dated)
        Log.v("sfsdfdsfdsddd","ID_module  "+ID_module)
        Log.v("sfsdfdsfdsddd","ID_Branch  "+ID_Branch)
        Log.v("sfsdfdsfdsddd","ID_Channel  "+ID_Channel)
        Log.v("sfsdfdsfdsddd","ID_Shedule  "+ID_Shedule)
        Log.v("sfsdfdsfdsddd","encodeDoc  "+encodeDoc)
        Log.v("sfsdfdsfdsddd","extension  "+extension)
        Log.v("sfsdfdsfdsddd","message  "+message)
        Log.v("sfsdfdsfdsddd","ID_LeadSource  "+ID_LeadSource)
        Log.v("sfsdfdsfdsddd","ID_LeadInfo  "+ID_LeadInfo)
        Log.v("sfsdfdsfdsddd","ID_Category  "+ID_Category)
        Log.v("sfsdfdsfdsddd","ID_Employee  "+ID_Employee)
        Log.v("sfsdfdsfdsddd","selectedFromDate  "+selectedFromDate)
        Log.v("sfsdfdsfdsddd","selectedToDate  "+selectedToDate)
        Log.v("sfsdfdsfdsddd","leadEntry  "+leadEntry)
        Log.v("sfsdfdsfdsddd","gridSize  "+gridSize)
        Log.v("sfsdfdsfdsddd","ScheduledTime  "+ScheduledTime)
        Log.v("sfsdfdsfdsddd","ScheduleDate  "+ScheduledDate)

        var branch = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                sentIntimationViewModel.sentIntimation(this, dated,ID_module,ID_Branch,ID_Channel,ID_Shedule,encodeDoc,extension,
                    message,
                    ScheduledDate,
                    ScheduledTime,
                    ID_LeadSource,
                    ID_LeadInfo,
                    selectedFromDate,
                    selectedToDate,
                    ID_Category,
                    ID_ProductType,
                    ID_Product,
                    ID_Employee,
                    ID_CollectedBy,
                    ID_Area!!,
                    ID_NextAction,
                    ID_ActionType,
                    ID_Priority,
                    ID_Lead_Details,
                    leadEntry,
                    gridSize,
                    gridListarray

                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   1062   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    successPopup()
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@Intimation,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
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

    private fun successPopup()
    {
        val suceessDialog = Dialog(this)
        suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        suceessDialog!!.setCancelable(false)
        suceessDialog!!.setContentView(R.layout.success_popup)
        suceessDialog!!.window!!.attributes.gravity =
            Gravity.CENTER_VERTICAL;

        val tv_succesmsg =
            suceessDialog!!.findViewById(R.id.tv_succesmsg) as TextView
        val ll_leadnumber =
            suceessDialog!!.findViewById(R.id.ll_leadnumber) as LinearLayout
        val tv_leadid =
            suceessDialog!!.findViewById(R.id.tv_leadid) as TextView
        val tv_succesok =
            suceessDialog!!.findViewById(R.id.tv_succesok) as TextView
        tv_succesmsg!!.setText("Successfully completed")
        tv_leadid!!.visibility=View.GONE
        ll_leadnumber!!.visibility=View.GONE

        tv_succesok!!.setOnClickListener {
            suceessDialog!!.dismiss()
            finish()
        }

        suceessDialog!!.show()
        suceessDialog!!.getWindow()!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
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
                branchViewModel.getBranch(this, "0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   1062   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("BranchDetails")
                                    branchArrayList = jobjt.getJSONArray("BranchDetailsList")
                                    if (branchArrayList.length() > 0) {
                                        if (branch == 0) {
                                            branch++
                                            branchPopup(branchArrayList)
                                        }

                                    }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@Intimation,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
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

    private fun getChannel() {
        var branch = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                channelViewModel.getChannel(this, "0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   106654   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("Channel")
                                    channelArrayList = jobjt.getJSONArray("ChannelList")
                                    if (channelArrayList.length() > 0) {
                                        if (branch == 0) {
                                            branch++
                                            channelPopup(channelArrayList)
                                        }

                                    }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@Intimation,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
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

    private fun getShedule() {
        var branch = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                sheduleViewModel.getShedule(this, "0")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   106654   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("ScheduleType")
                                    sheduleArrayList = jobjt.getJSONArray("ScheduleTypeList")
                                    if (sheduleArrayList.length() > 0) {
                                        if (branch == 0) {
                                            branch++


                                            if(shedulemode == 0)
                                            {
                                                var jsonObject1 = sheduleArrayList.getJSONObject(0)
                                                tie_shedule!!.setText(jsonObject1.getString("SheduledTypeName"))
                                                ID_Shedule = jsonObject1.getString("ID_SheduledType")
                                            }
                                            else
                                            {
                                                shedulePopup(sheduleArrayList)
                                            }

//                                            var jsonObject1 = sheduleArrayList.getJSONObject(1)
//                                            tie_shedule!!.setText(jsonObject1.getString("SheduledTypeName"))
//                                            ID_Shedule = jsonObject1.getString("ID_SheduledType")
//                                            shedulePopup(sheduleArrayList)
                                        }

                                    }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@Intimation,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + e.toString(),
                                Toast.LENGTH_SHORT
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
            dialogBranch!!.setContentView(R.layout.branch_popup)
            dialogBranch!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBranch = dialogBranch!!.findViewById(R.id.recyBranch) as RecyclerView
            val etsearch = dialogBranch!!.findViewById(R.id.etsearch) as EditText
            branchsort = JSONArray()
            for (k in 0 until branchArrayList.length()) {
                val jsonObject = branchArrayList.getJSONObject(k)
                branchsort.put(jsonObject)
            }
            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyBranch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            //  val adapter = BranchAdapter(this@TicketReportActivity, branchArrayList)
            val adapter = BranchAdapter(this@Intimation, branchsort)
            recyBranch!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    branchsort = JSONArray()

                    for (k in 0 until branchArrayList.length()) {
                        val jsonObject = branchArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("BranchName").length) {
                            if (jsonObject.getString("BranchName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                branchsort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "branchsort               7103    " + branchsort)
                    val adapter = BranchAdapter(this@Intimation, branchsort)
                    recyBranch!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogBranch!!.show()
            dialogBranch!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    private fun getModule() {
        var reportName = 0
        var SubMode = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                intimationModuleViewModel.getModule(this, SubMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   1062   " + msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val jobjt = jObject.getJSONObject("SendIntimationModule")
                                intimationModuleArrayList =
                                    jobjt.getJSONArray("IntimationModuleList")
                                if (intimationModuleArrayList.length() > 0) {
                                    if (reportName == 0) {
                                        reportName++
                                        modulePopup(intimationModuleArrayList)
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@Intimation,
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
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
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

    private fun hasUser(gridListarray: JSONArray, idFirst: String, idLeadfirst: String): Boolean {

        for (i in 0 until gridListarray.length()) {
            Log.e(TAG,"rrrrrrrrrrrrrrrrrrrrrrrrr  2 "+gridListarray)
            if (gridListarray.getJSONObject(i).getString("SlNo") == (idFirst) && gridListarray.getJSONObject(i).getString("LeadNo") == idLeadfirst)
                return false
        }
        return true

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onClick(position: Int, data: String) {
        if (data.equals("module")) {
            dialogGrouping!!.dismiss()
            val jsonObject = moduleSort.getJSONObject(position)
            ID_module = jsonObject.getString("ID_Mode")
            tie_module!!.setText(jsonObject.getString("ModeName"))

            if (ID_module.equals("2"))
            {
                img_filter!!.visibility=View.VISIBLE
            }
            else
            {
                img_filter!!.visibility=View.INVISIBLE
            }
        }
        if (data.equals("branch")) {
            dialogBranch!!.dismiss()
            val jsonObject = branchsort.getJSONObject(position)
            ID_Branch = jsonObject.getString("ID_Branch")
            tie_Branch!!.setText(jsonObject.getString("BranchName"))

        }
        if (data.equals("channel")) {
            dialogGrouping!!.dismiss()
            val jsonObject = channelSort.getJSONObject(position)
            ID_Channel = jsonObject.getString("ID_Channel")
            tie_Channel!!.setText(jsonObject.getString("ChannelName"))
        }
        if (data.equals("shedule")) {
            dialogGrouping!!.dismiss()
            val jsonObject = shedulesort.getJSONObject(position)
            ID_Shedule = jsonObject.getString("ID_SheduledType")
            tie_shedule!!.setText(jsonObject.getString("SheduledTypeName"))

            if (ID_Shedule.equals("2"))
            {
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val currentDate = sdf.format(Date())
                ScheduledDate=currentDate
                tie_ScheduledDate!!.setText(ScheduledDate)
                ll_sheduled!!.visibility=View.VISIBLE


            }
            else
            {
                ScheduledTime=""
                ScheduledDate=""
                tie_ScheduledTime!!.setText("")

                tie_ScheduledDate!!.setText("")
                ll_sheduled!!.visibility=View.GONE
            }
        }
        if (data.equals("leadSource")) {
            dialogeLeadSource!!.dismiss()
            val jsonObject = leadSourceList.getJSONObject(position)
            ID_LeadSource = jsonObject.getString("ID_LeadFrom")
            tie_LeadSource!!.setText(jsonObject.getString("LeadFromName"))

            Log.e(TAG, "ID_LeadSource   3453454 " + ID_LeadSource)

            if (ID_LeadSource.equals("11") || ID_LeadSource.equals("10"))
            {
                til_LeadFrom!!.visibility=View.GONE
                ID_LeadInfo=""

            }
            else
            {
                til_LeadFrom!!.visibility=View.VISIBLE
            }


        }

        if (data.equals("employee")) {
            dialogEmployee!!.dismiss()
//             val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))
            ID_Employee = jsonObject.getString("ID_Employee")
            tie_employeee!!.setText(jsonObject.getString("EmpName"))
        }

        if (data.equals("leadby")) {
            dialogLeadBy!!.dismiss()
//            val jsonObject = leadByArrayList.getJSONObject(position)
            val jsonObject = leadBySort.getJSONObject(position)
            Log.e(TAG, "ID_CollectedBy   " + jsonObject.getString("ID_CollectedBy"))
         ID_CollectedBy = jsonObject.getString("ID_CollectedBy")

            tie_CollectedBy!!.setText(jsonObject.getString("Name"))

        }
        if (data.equals("areadetail")) {
            dialogArea!!.dismiss()
            val jsonObject = areaSort.getJSONObject(position)
            Log.e(TAG, "jsonObject  5465    " + jsonObject)


            ID_Area = jsonObject.getString("FK_Area")

            tie_areaa!!.setText(jsonObject.getString("Area"))



        }

        if (data.equals("followupaction")) {
            dialogFollowupAction!!.dismiss()
//             val jsonObject = followUpActionArrayList.getJSONObject(position)
            val jsonObject = followUpActionSort.getJSONObject(position)
            Log.e(TAG, "ID_NextAction   " + jsonObject.getString("ID_NextAction"))
            ID_NextAction = jsonObject.getString("ID_NextAction")
            tie_FollowUpAction!!.setText(jsonObject.getString("NxtActnName"))

        //    ID_Status = jsonObject.getString("Status")
            Log.e(TAG, "Status   " + jsonObject.getString("Status"))

        }

        if (data.equals("followuptype")) {
            dialogFollowupType!!.dismiss()
//             val jsonObject = followUpTypeArrayList.getJSONObject(position)
            val jsonObject = followUpTypeSort.getJSONObject(position)
            Log.e(TAG, "ID_ActionType   " + jsonObject.getString("ID_ActionType"))
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tie_FollowUpType!!.setText(jsonObject.getString("ActnTypeName"))


        }
        if (data.equals("prodpriority")) {
            dialogProdPriority!!.dismiss()
//             val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = prodPrioritySort.getJSONObject(position)
            Log.e(TAG, "ID_Priority   " + jsonObject.getString("ID_Priority"))
            ID_Priority = jsonObject.getString("ID_Priority")
            tie_Priority!!.setText(jsonObject.getString("PriorityName"))


        }

        if (data.equals("LeadDetail1")){
            dialogleadDetail!!.dismiss()
            val jsonObject = leadDetailSort.getJSONObject(position)
            Log.e(TAG,"ID_TodoListLeadDetails   "+jsonObject.getString("ID_TodoListLeadDetails"))
            ID_Lead_Details = jsonObject.getString("ID_TodoListLeadDetails")
            tie_LeadDetails!!.setText(jsonObject.getString("TodoListLeadDetailsName"))
            til_Lead_entry!!.setHint(jsonObject.getString("TodoListLeadDetailsName"))
            tie_Lead_entry!!.visibility=View.VISIBLE
            ll_leadENTRY!!.visibility=View.VISIBLE

            if (ID_Lead_Details.equals("1"))
            {
                tie_Lead_entry!!.setText("")
                tie_Lead_entry!!.inputType=InputType.TYPE_CLASS_TEXT
            }
            if (ID_Lead_Details.equals("2"))
            {
                tie_Lead_entry!!.setText("")
                tie_Lead_entry!!.inputType=InputType.TYPE_CLASS_PHONE
            }
            if (ID_Lead_Details.equals("3"))
            {
                tie_Lead_entry!!.setText("")
                tie_Lead_entry!!.inputType=InputType.TYPE_CLASS_TEXT
            }


        }

//        if (data.equals("cus_wise_lead")){
//            dialogCusMiseLead!!.dismiss()
//            val jsonObject = cusLeadSort.getJSONObject(position)
//            val jObject = JSONObject()
////            jObject.put("CategoryName", edtProdcategory!!.text.toString())
////            jObject.put("ProdName", edtProdproduct!!.text.toString())
//            Log.e(TAG,"jsonObject  4543543 "+jsonObject)
//            gridListarray = JSONArray()
//            gridListarray!!.put(jsonObject)
//            Log.e(TAG,"gridListarray  4543543 "+jsonObject)
//
//            try {
//                val lLayout = GridLayoutManager(this@Intimation, 1)
//                fulllengthrcy!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                val adapter = CusLeadAdapter(this@Intimation, gridListarray)
//                fulllengthrcy!!.adapter = adapter
//                adapter.setClickListener(this@Intimation)
//            }catch (e: Exception){
//                Log.e(TAG,"Exception  1275   "+e.toString())
//            }
//
//
//
//        }

        if (data.equals("cus_wise_lead_hist")){

            val jsonObject = leadHistSort.getJSONObject(position)
            val jObject = JSONObject()
//            jObject.put("CategoryName", edtProdcategory!!.text.toString())
//            jObject.put("ProdName", edtProdproduct!!.text.toString())
            Log.e(TAG,"jsonObject  4543543 "+jsonObject)
     //       Log.e(TAG,"gridListarray_first  454354321 "+gridListarray)


     //       gridListarray = JSONArray()


            ID_first = jsonObject.getString("SlNo")
            ID_LeadFirst = jsonObject.getString("LeadNo")

            Log.e(TAG,"ID_first  4543543   "+ID_first)
            Log.e(TAG,"ID_LeadFirst  4543543   "+ID_first)
            Log.e(TAG,"ID_LeadFirst  4543543   "+ID_first)

            var hasId = hasUser(gridListarray, ID_first!!, ID_LeadFirst!!)



            if (hasId)
            {
                dialogHist!!.dismiss()
                dialog1!!.dismiss()
                gridListarray!!.put(jsonObject)

                Log.e(TAG,"gridListarray_second  454354322 "+gridListarray)
                Log.e(TAG,"gridListarray  4543543 "+jsonObject)

                Log.e(TAG,"alresdytrt   65656565 ")

                viewGridList(gridListarray)

            }
            else
            {
                dialogHist!!.dismiss()
                dialog1!!.dismiss()
                Log.e(TAG,"alresdy   65656565 ")
             //   Toast.makeText(context,"Already exist",Toast.LENGTH_SHORT).show()
   //             Config.snackBars(context, , "Select Documents Or Add Message")
          //      Snackbar.make(findViewById(android.R.id.content), "Already exist", Snackbar.LENGTH_SHORT).show()


                val snackbar = Snackbar.make(findViewById(android.R.id.content), "Already exist", Snackbar.LENGTH_SHORT)
                val sbView = snackbar.view
                sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                val textView: TextView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
                textView.setTextColor(Color.WHITE)
                val typeface = ResourcesCompat.getFont(context, R.font.myfont)
                textView.setTypeface(typeface)
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15f)
                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                snackbar.show()
            }









        }


        if (data.equals("deletearray_list"))
        {

            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.alert_delete, null)

            val btnNo = view.findViewById<Button>(R.id.btn_No)
            val btnYes = view.findViewById<Button>(R.id.btn_Yes)
            val textid1 = view.findViewById<TextView>(R.id.textid1)

            textid1!!.setText("Do you want to delete this product?")

            btnNo.setOnClickListener {
                dialog .dismiss()

            }
            btnYes.setOnClickListener {

                gridListarray.remove(position)
                viewGridList(gridListarray)
                dialog.dismiss()

            }
            dialog.setCancelable(true)
            dialog!!.setContentView(view)

            dialog.show()




            //...........old

//           gridListarray.remove(position)
//            viewGridList(gridListarray)



        }

        if (data.equals("prodcategory")){
            dialogProdCat!!.dismiss()
//             val jsonObject = followUpTypeArrayList.getJSONObject(position)
            val jsonObject = prodCatSort.getJSONObject(position)


            ID_Category = jsonObject.getString("ID_Category")
            tie_Category!!.setText(jsonObject.getString("CategoryName"))


        }

        if (data.equals("proddetails"))
        {
            dialogProdDet!!.dismiss()
//             val jsonObject = prodDetailArrayList.getJSONObject(position)
            val jsonObject = prodDetailSort.getJSONObject(position)


            ID_Product = jsonObject.getString("ID_Product")
            tie_product!!.setText(jsonObject.getString("ProductName"))
        }

        if (data.equals("prodcatType")) {
          //     tie_productproject!!.setText("")
                ID_Product=""
            dialogProductType!!.dismiss()
//             val jsonObject = prodPriorityArrayList.getJSONObject(position)
            val jsonObject = productTypeSort.getJSONObject(position)

            ID_ProductType = jsonObject.getString("ID_ProductType")
            tie_ProductType!!.setText(jsonObject.getString("ProductTypeName"))
            til_productproject!!.setHint(jsonObject.getString("ProductTypeName"))
            if (jsonObject.getString("ID_ProductType").equals("2"))
            {
                ll_product!!.visibility=View.VISIBLE
                ll_project!!.visibility=View.GONE
                til_product!!.setHint(jsonObject.getString("ProductTypeName"))

                //                ll_project!!.visibility=View.GONE
//
                tie_productproject!!.setText("")
//                ll_product!!.visibility=View.GONE
//                ID_Product=""

          //      til_productproject!!.setEndIconDrawable(ContextCompat.getDrawable(this,R.drawable.ic_search))
            }
            else
            {

                ll_product!!.visibility=View.GONE
                ll_project!!.visibility=View.VISIBLE
                ID_Product=""
                til_productproject!!.setHint(jsonObject.getString("ProductTypeName"))
           //     til_productproject!!.setEndIconDrawable(null)
            }


        }

        if (data.equals("LeadFromInfo"))
        {
            dialogLeadinfo!!.dismiss()
            val jsonObject = leadfromInfosort.getJSONObject(position)
            ID_LeadInfo = jsonObject.getString("ID_FIELD")
            tie_LeadFrom!!.setText(jsonObject.getString("Name"))


        }

    }

    private fun viewGridList(gridListarray: JSONArray) {
        try {
            if (gridListarray.length()>0)
            {
                ll_gridData!!.visibility=View.VISIBLE
                val lLayout = GridLayoutManager(this@Intimation, 1)
                fulllengthrcy!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                val adapter = LeadCusGridAdapter(this@Intimation, gridListarray)
                fulllengthrcy!!.adapter = adapter
                adapter.setClickListener(this@Intimation)
            }
            else
            {
                ll_gridData!!.visibility=View.GONE
            }


        }catch (e: Exception){
            Log.e(TAG,"Exception  1275   "+e.toString())
        }

    }

    private fun shedulePopup(sheduleArrayList: JSONArray) {
        try {
            dialogGrouping = Dialog(this)
            dialogGrouping!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogGrouping!!.setContentView(R.layout.intimation_popup)
            dialogGrouping!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyGrouping = dialogGrouping!!.findViewById(R.id.recyGrouping) as RecyclerView
            val etsearch = dialogGrouping!!.findViewById(R.id.etsearch) as EditText
            val heading = dialogGrouping!!.findViewById(R.id.heading) as TextView
            heading.setText("Channel")
            shedulesort = JSONArray()
            for (k in 0 until sheduleArrayList.length()) {
                val jsonObject = sheduleArrayList.getJSONObject(k)
                shedulesort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyGrouping!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = GroupingAdapter(this@TicketReportActivity, groupingArrayList)
            val adapter = IntimationSheduleAdapter(this@Intimation, shedulesort)
            recyGrouping!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val textlength = etsearch!!.text.length
                    shedulesort = JSONArray()
                    for (k in 0 until intimationModuleArrayList.length()) {
                        val jsonObject = intimationModuleArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("SheduledTypeName").length) {
                            if (jsonObject.getString("SheduledTypeName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                shedulesort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "groupingSort               7103    " + shedulesort)
                    val adapter = IntimationSheduleAdapter(this@Intimation, shedulesort)
                    recyGrouping!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogGrouping!!.show()
            dialogGrouping!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    private fun channelPopup(channelArrayList: JSONArray) {
        try {
            dialogGrouping = Dialog(this)
            dialogGrouping!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogGrouping!!.setContentView(R.layout.intimation_popup)
            dialogGrouping!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyGrouping = dialogGrouping!!.findViewById(R.id.recyGrouping) as RecyclerView
            val etsearch = dialogGrouping!!.findViewById(R.id.etsearch) as EditText
            val heading = dialogGrouping!!.findViewById(R.id.heading) as TextView
            heading.setText("Channel")
            channelSort = JSONArray()
            for (k in 0 until channelArrayList.length()) {
                val jsonObject = channelArrayList.getJSONObject(k)
                channelSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyGrouping!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = GroupingAdapter(this@TicketReportActivity, groupingArrayList)
            val adapter = IntimationChannelAdapter(this@Intimation, channelSort)
            recyGrouping!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val textlength = etsearch!!.text.length
                    channelSort = JSONArray()
                    for (k in 0 until intimationModuleArrayList.length()) {
                        val jsonObject = intimationModuleArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ChannelName").length) {
                            if (jsonObject.getString("ChannelName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                channelSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "groupingSort               7103    " + channelSort)
                    val adapter = IntimationChannelAdapter(this@Intimation, channelSort)
                    recyGrouping!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogGrouping!!.show()
            dialogGrouping!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    private fun modulePopup(intimationModuleArrayList: JSONArray) {
        try {
            dialogGrouping = Dialog(this)
            dialogGrouping!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogGrouping!!.setContentView(R.layout.intimation_popup)
            dialogGrouping!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyGrouping = dialogGrouping!!.findViewById(R.id.recyGrouping) as RecyclerView
            val etsearch = dialogGrouping!!.findViewById(R.id.etsearch) as EditText
            val heading = dialogGrouping!!.findViewById(R.id.heading) as TextView
            heading.setText("Module")
            moduleSort = JSONArray()
            for (k in 0 until intimationModuleArrayList.length()) {
                val jsonObject = intimationModuleArrayList.getJSONObject(k)
                moduleSort.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@Intimation, 1)
            recyGrouping!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = GroupingAdapter(this@TicketReportActivity, groupingArrayList)
            val adapter = IntimationModuleAdapter(this@Intimation, moduleSort)
            recyGrouping!!.adapter = adapter
            adapter.setClickListener(this@Intimation)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val textlength = etsearch!!.text.length
                    moduleSort = JSONArray()
                    for (k in 0 until intimationModuleArrayList.length()) {
                        val jsonObject = intimationModuleArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ModeName").length) {
                            if (jsonObject.getString("ModeName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                moduleSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "groupingSort               7103    " + moduleSort)
                    val adapter = IntimationModuleAdapter(this@Intimation, moduleSort)
                    recyGrouping!!.adapter = adapter
                    adapter.setClickListener(this@Intimation)
                }
            })

            dialogGrouping!!.show()
            dialogGrouping!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception  1132   " + e.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inputStreamImg = null
        if (requestCode == PICK_IMAGE_CAMERA) {

            try {
                if (data != null) {
                    try {
//                        if (ContextCompat.checkSelfPermission(
//                                this,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                            ) != PackageManager.PERMISSION_GRANTED
//                        ) {
//                            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                                    this,
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                                )
//                            ) {
//                                // Show an explanation to the user *asynchronously* -- don't block
//                                // this thread waiting for the user's response! After the user
//                                // sees the explanation, try again to request the permission.
//
//                            } else {
//                                // No explanation needed; request the permission
//                                ActivityCompat.requestPermissions(
//                                    this,
//                                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
//                                )
//                            }
//                        } else {

                        Log.e(TAG, "3961   " + data)
                        Log.e(TAG, "3962   " + data!!.getExtras()!!.get("data"))

                        val thumbnail = data!!.getExtras()!!.get("data") as Bitmap
                        val bytes = ByteArrayOutputStream()
                        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                        val fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
                        try {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                destination = File(
                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath,
                                    ""
                                )
                                // destination = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)  +"/" +  getString(R.string.app_name));
                            } else {
                                destination = File(
                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath,
                                    ""
                                )
                            }

                            if (!destination!!.exists()) {
                                destination!!.createNewFile()
                            }


                            destination = File(
                                (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)).absolutePath + "/" +
                                        "",
                                fileName
                            )
                            val fo: FileOutputStream


                            fo = FileOutputStream(destination)
                            fo.write(bytes.toByteArray())
                            fo.close()
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                            Log.e(TAG, "FileNotFoundException   23671    " + e.toString())

                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.e(TAG, "FileNotFoundException   23672    " + e.toString())
                        }


                        imgPath = destination!!.getAbsolutePath()
                        Log.e(TAG, "imgPath  20522    " + imgPath)
                        destination = File(imgPath)
                        documentPath = imgPath!!
                        //txtAttachmentPath!!.setText(imgPath)
                        //  tie_Attachment!!.setText(imgPath)
                        tie_Attachments!!.setText(fileName)


                        //  }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this@Intimation, "Failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {

            }

        } else if (requestCode == PICK_IMAGE_GALLERY) {
            if (data != null) {
                val selectedImage = data.data
                try {
                    val fileName = UriUtil.getFileName(this, data!!.data!!)
                    Log.e(TAG, "561 fileName :   " + fileName)
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                    val bytes = ByteArrayOutputStream()
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, bytes)

                    imgPath = getRealPathFromURI(selectedImage!!)
                    destination = File(imgPath.toString())
                    documentPath = imgPath!!
                    // txtAttachmentPath!!.setText(imgPath)
                    tie_Attachments!!.setText(fileName)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "No image selected from gallery", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == PICK_DOCUMRNT_GALLERY) {
            try {
                if (data != null) {
                    val uri = data.data
                    Log.e(TAG, "561  uri " + uri)
                    Log.e(TAG, "561 data  " + data)

//                    OLD
                    //    selectedFilePath = UriUtil.fileFromContentUri(this, uri!!).toString()
                    //   selectedFilePath = UriUtil.getPath(this, uri!!).toString()
                    //   selectedFilePath = getRealPathFromURI(uri)


//                    selectedFilePath = getRealPathFromURI(uri)
////                    selectedFilePath = getRealPathUri(uri)
//                    Log.e(TAG,"561  selectedFilePath   "+selectedFilePath)
//                    destination = File(selectedFilePath.toString())
//                    Log.e(TAG,"561 destination  "+destination)
//                    documentPath = selectedFilePath!!
//                    Log.e(TAG,"561 documentPath   "+documentPath)
//                    txtAttachmentPath!!.setText(selectedFilePath)
//                    tie_Attachment!!.setText(selectedFilePath)
//
//                    22.02.2023

                    val fileName = UriUtil.getFileName(this, data!!.data!!)
                    val filepath = UriUtil.fileFromContentUri(
                        this,
                        data!!.data!!,
                        fileName!!
                    )  // WORKING 22.02.2023
                    tie_Attachments!!.setText(fileName)
                    Log.e(TAG, "561 filepath :   " + filepath)
                    documentPath = filepath.toString()

//                    val map: MimeTypeMap = MimeTypeMap.getSingleton()
//                    val ext: String = MimeTypeMap.getFileExtensionFromUrl(filepath.name)
//                    var type: String? = map.getMimeTypeFromExtension(ext)
//                    if (type == null)
//                        type = "*/*";
//
//                    val intent = Intent(Intent.ACTION_VIEW)
//                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                    val data: Uri = Uri.fromFile(filepath)
//                    intent.setDataAndType(data, type)
//                    startActivity(intent)

                } else {
                    Toast.makeText(this, "No Document selected", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "561   " + e.toString())
            }

        } else if (requestCode == PICK_DOCUMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { documentUri ->
                // Perform the upload operation with the selected document URI

                val fileName = UriUtil.getFileName(this, data!!.data!!)
                val filepath = UriUtil.fileFromContentUri(
                    this,
                    data!!.data!!,
                    fileName!!
                )  // WORKING 22.02.2023
                tie_Attachments!!.setText(fileName)
                Log.e(TAG, "561 filepath :   " + filepath)
                documentPath = filepath.toString()

                uploadDocument(documentUri)
            }
        }
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
//    fun getRealPathFromURI(contentUri: Uri?): String? {
//        val proj = arrayOf(MediaStore.Audio.Media.DATA)
//        val cursor = managedQuery(contentUri, proj, null, null, null)
//        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
//        cursor.moveToFirst()
//        return cursor.getString(column_index)
//    }

    private fun uploadDocument(documentUri: Uri) {
        val documentFile = DocumentFile.fromSingleUri(this, documentUri)

        if (documentFile != null && documentFile.exists()) {
            // Get information about the selected document
            val documentName = documentFile.name
            val documentSize = documentFile.length()

            Log.d("Document Info", "Name: $documentName, Size: $documentSize bytes")

            // Perform your upload logic here
            // Example: Upload the document to a server, etc.
        } else {
            Log.e("Error", "Unable to access the selected document.")
        }
    }
}
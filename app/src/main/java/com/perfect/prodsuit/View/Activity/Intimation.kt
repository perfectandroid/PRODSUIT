package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import java.text.SimpleDateFormat
import java.util.*

class Intimation : AppCompatActivity(), View.OnClickListener, ItemClickListener {
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
        setReg()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_FromDate!!.setText(currentDate)
    }

    private fun setReg() {
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
                    getModule()
                }
                R.id.tie_Branch -> {
                    getBranch()
                }
                R.id.tie_Channel -> {
                    getChannel()
                }
                R.id.tie_shedule -> {
                    getShedule()
                }
                R.id.btnSubmit -> {
                    validateData(v)
                }
                R.id.btnReset -> {
                    reset()
                }
                R.id.tie_FromDate -> {
                    datePicker()
                }

            }
        }
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
        } else {
            docUploadValidation(v)
        }

    }

    private fun docUploadValidation(v: View) {
        val message = tie_message!!.text.toString()
        strImageName = tie_Attachments!!.text.toString()
        var extension=""

        if (strImageName.equals("") && message.equals("")) {
            Config.snackBars(context, v, "Select Documents Or Add Message")
        } else if (documentPath.equals("") && message.equals("")) {
            Config.snackBars(context, v, "Pick Documents Or Add Message")
        } else {
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
                Log.e(TAG, "encodeDoc   508   " + encodeDoc)

            } catch (e: Exception) {

            }
            sentIntimation(tie_FromDate!!.text.toString(),ID_module,ID_Branch,ID_Channel,ID_Shedule,encodeDoc,extension,message)
//            saveDocuments(strDescription, strImageName, encodeDoc, extension)
        }

    }

    private fun selectImage() {
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

    private fun reset() {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        tie_FromDate!!.setText(currentDate)

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
        var branch = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                sentIntimationViewModel.sentIntimation(this, dated,ID_module,ID_Branch,ID_Channel,ID_Shedule,encodeDoc,extension,message)!!.observe(
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
                                            shedulePopup(sheduleArrayList)
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

    override fun onClick(position: Int, data: String) {
        if (data.equals("module")) {
            dialogGrouping!!.dismiss()
            val jsonObject = moduleSort.getJSONObject(position)
            ID_module = jsonObject.getString("ID_Mode")
            tie_module!!.setText(jsonObject.getString("ModeName"))
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
                        } else {

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


                        }
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
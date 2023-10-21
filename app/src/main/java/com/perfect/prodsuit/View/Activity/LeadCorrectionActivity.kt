package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelLeadCorrectionDetails
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.CorrectionProductAdapter
import com.perfect.prodsuit.Viewmodel.CorrectionLeadViewModel
import com.perfect.prodsuit.Viewmodel.LeadGenerateSaveViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class LeadCorrectionActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    var TAG = "LeadCorrectionActivity"
    var recycle_productlist: RecyclerView? = null
    private var imback: ImageView? = null
    private var tvv_leadno_1: TextView? = null
    private var tvv_leadNo_2: TextView? = null
    private var tvv_leadname_1: TextView? = null
    private var tvv_leadName_2: TextView? = null
    private var tvv_leadremark_1: TextView? = null
    private var tvv_leadremark_2: TextView? = null
    private var tvv_correctionPerson_1: TextView? = null
    private var tvv_correctionPerson_2: TextView? = null
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var correctionLeadViewModel: CorrectionLeadViewModel

    lateinit var leadGenerateSaveViewModel: LeadGenerateSaveViewModel
    lateinit var array_product_lead: JSONArray
    var array_product_lead_final: JSONArray? = null
    lateinit var correctionsenderArrayList: JSONArray

    //    private var array_product_lead = JSONArray()
    var saveUpdateMode: String? = "2"

    var ID_LeadGenerate: String = "0"
    var strDate: String = ""
    var ID_Customer: String = ""
    var ID_MediaSubMaster: String = ""
    var CusNameTitle: String = ""
    var Customer_Name: String = ""
    var Customer_Address1: String = ""
    var Customer_Address2: String = ""
    var Customer_Mobile: String = ""
    var Customer_Email: String = ""
    var strCompanyContact: String = ""
    var FK_Country: String = ""
    var FK_States: String = ""
    var FK_District: String = ""
    var FK_Post: String = ""
    var strPincode: String = ""
    var FK_Area: String = ""
    var ID_LeadFrom: String = ""
    var ID_LeadThrough: String = ""
    var strLeadThrough: String = ""
    var strWhatsAppNo: String = ""
    var strLatitude: String = ""
    var strLongitue: String = ""
    var encode1: String = ""
    var encode2: String = ""
    var Customer_Mode: String = ""
    var Customer_Type: String = ""
    var ID_CustomerAssignment: String = ""
    var ID_CollectedBy: String = ""
    var LgActDate: String = ""
    var LgpExpectDate: String = ""
    var check_save: String = ""


    var saveLeadGenDet = 0
    val modelLeadCorrectionDetails = ArrayList<ModelLeadCorrectionDetails>()
    var modelLeadCorrectionDetailsfinal = ArrayList<ModelLeadCorrectionDetails>()
    var correctionProductAdapter: CorrectionProductAdapter? = null

    private var btnCancel: Button? = null
    private var btnSubmit: Button? = null

    var correctioncount = 0
    var jsonObj: JSONObject? = null
    var TransMode = ""
    private var FK_TransMaster: String? = ""
    private var Module_sub: String? = ""
    private var ID_AuthorizationData: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lead_correction)
        context = this@LeadCorrectionActivity


        correctionLeadViewModel = ViewModelProvider(this).get(CorrectionLeadViewModel::class.java)
        leadGenerateSaveViewModel =
            ViewModelProvider(this).get(LeadGenerateSaveViewModel::class.java)

        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)
        getIntent().hasExtra("Module_sub")
        Module_sub = intent.getStringExtra("Module_sub")
        FK_TransMaster = jsonObj!!.getString("FK_TransMaster")
        ID_AuthorizationData = jsonObj!!.getString("ID_AuthorizationData")

        Log.e(TAG, "FK_TransMaster 445   " + FK_TransMaster)
        Log.e(TAG, "Module 445   " + Module_sub)
        Log.e(TAG, " ID_AuthorizationData   " + ID_AuthorizationData)

        if (Module_sub.equals("1")) {

            TransMode = jsonObj!!.getString("Module")
        } else {
            TransMode = jsonObj!!.getString("TransMode")
            Log.e(TAG, "TransMode 445   " + jsonObj!!.getString("TransMode"))
        }
        setRegViews()

        correctioncount = 0
        getCorrectionDetails(TransMode, FK_TransMaster!!, ID_AuthorizationData!!)

    }

    private fun setRegViews() {

        imback = findViewById(R.id.imback)
        tvv_leadno_1 = findViewById(R.id.tvv_leadno_1)
        tvv_leadNo_2 = findViewById(R.id.tvv_leadNo_2)
        tvv_leadname_1 = findViewById(R.id.tvv_leadname_1)
        tvv_leadName_2 = findViewById(R.id.tvv_leadName_2)
        tvv_leadremark_1 = findViewById(R.id.tvv_leadremark_1)
        tvv_leadremark_2 = findViewById(R.id.tvv_leadremark_2)
        tvv_correctionPerson_1 = findViewById(R.id.tvv_correctionPerson_1)
        tvv_correctionPerson_2 = findViewById(R.id.tvv_correctionPerson_2)
        recycle_productlist = findViewById(R.id.recycle_productlist)

        btnCancel = findViewById<Button>(R.id.btnCancel)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)

        imback!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
        btnCancel!!.setOnClickListener(this)

    }

    private fun getCorrectionDetails(
        TransMode: String,
        FK_TransMaster: String,
        ID_AuthorizationData: String
    ) {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                correctionLeadViewModel.getCorrectionLead(
                    this,
                    TransMode,
                    FK_TransMaster,
                    ID_AuthorizationData
                )!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG, "msg   353   " + msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt =
                                    jObject.getJSONObject("AuthorizationCorrectionLeadDetails")
                                array_product_lead = jobjt.getJSONArray("ProductDetails")

                                Log.e(TAG, "array_product_lead  889 " + array_product_lead)

                                correctionsenderArrayList = jobjt.getJSONArray("SenderDetails")
                                if (array_product_lead.length() > 0) {
                                    if (correctioncount == 0) {
                                        correctioncount++

                                        modelLeadCorrectionDetails.clear()
                                        for (i in 0 until array_product_lead.length()) {
                                            var jsonObject = array_product_lead.getJSONObject(i)

                                            tvv_leadNo_2!!.setText(jobjt.getString("LeadNo"))
                                            tvv_leadName_2!!.setText(jobjt.getString("CusName"))

                                            ID_Customer = jobjt.getString("ID_Customer")
                                            ID_LeadGenerate = jobjt.getString("LeadGenerateID")
                                            CusNameTitle = jobjt.getString("CusNameTitle")
                                            Customer_Name = jobjt.getString("CusName")
                                            strDate = jobjt.getString("LeadDate")
                                            Customer_Address1 = jobjt.getString("CusAddress")
                                            Customer_Address2 = jobjt.getString("CusAddress2")
                                            Customer_Mobile = jobjt.getString("CusMobile")
                                            ID_MediaSubMaster = jobjt.getString("ID_MediaMaster")
                                            Customer_Email = jobjt.getString("CusEmail")
                                            FK_Country = jobjt.getString("CountryID")
                                            FK_States = jobjt.getString("StatesID")
                                            FK_District = jobjt.getString("DistrictID")
                                            FK_Post = jobjt.getString("PostID")
                                            FK_Area = jobjt.getString("AreaID")
                                            strLeadThrough = jobjt.getString("LeadByName")
                                            strWhatsAppNo = jobjt.getString("CusMobileAlternate")
                                            ID_CollectedBy = jobjt.getString("CollectedBy")

//                                            LgActDate = jobjt.getString("NextActionDate")
//                                            LgpExpectDate = jobjt.getString("LgpExpectDate")
//
//
//                                            Log.e(TAG,"NextActionDate     "+LgActDate)
//                                            Log.e(TAG,"LgpExpectDate     "+LgpExpectDate)

                                            modelLeadCorrectionDetails!!.add(
                                                ModelLeadCorrectionDetails(
                                                    jsonObject.getString("FK_Category"),
                                                    jsonObject.getString("CategoryName"),
                                                    jsonObject.getString("ID_Product"),
                                                    jsonObject.getString("ProdName"),
                                                    jsonObject.getString("LgpMRP"),
                                                    jsonObject.getString("LgpSalesPrice")
                                                )
                                            )
                                        }

                                        for (i in 0 until correctionsenderArrayList.length()) {
                                            var jsonObject =
                                                correctionsenderArrayList.getJSONObject(i)
                                            tvv_leadremark_2!!.setText(jsonObject.getString("Remark"))
                                            tvv_correctionPerson_2!!.setText(jsonObject.getString("Sender"))
                                        }

                                        if (modelLeadCorrectionDetails.size > 0) {
                                            recycle_productlist!!.setLayoutManager(
                                                LinearLayoutManager(
                                                    this,
                                                    LinearLayoutManager.VERTICAL,
                                                    false
                                                )
                                            )
                                            correctionProductAdapter = CorrectionProductAdapter(
                                                this@LeadCorrectionActivity,
                                                modelLeadCorrectionDetails
                                            )
                                            recycle_productlist!!.adapter = correctionProductAdapter
                                            correctionProductAdapter!!.setClickListener(this@LeadCorrectionActivity)
                                        }
//                                        productPriorityPopup(prodPriorityArrayList)

//                                        val lLayout = GridLayoutManager(this@LeadCorrectionActivity, 1)
//                                        recycle_productlist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                        val adapterHome = CorrectionProductAdapter(this@LeadCorrectionActivity,correctionleadArrayList)
//                                        recycle_productlist!!.adapter = adapterHome
//                                        adapterHome.setClickListener(this@LeadCorrectionActivity)
                                    }

                                }

                            } else {
                                val builder = AlertDialog.Builder(
                                    this@LeadCorrectionActivity,
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


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imback -> {
                finish()
            }

            R.id.btnCancel -> {
                finish()
            }

            R.id.btnSubmit -> {
                Config.disableClick(v)
                Log.i("respCorrection", "validation ")
                validation()
            }
        }
    }

    private fun validation() {
        Log.e(TAG, "modelLeadCorrectionDetails     " + modelLeadCorrectionDetails.size)
//        if (modelLeadCorrectionDetails.size > 0){
//            for (i in 0 until modelLeadCorrectionDetails.size) {
//                var MRRP = modelLeadCorrectionDetails[i].LgpMRP
//                var offer = modelLeadCorrectionDetails[i].LgpSalesPrice
//
//                if (MRRP!!.equals("")) {
//                    MRRP = "0"
//                }
//
//                if (offer!!.equals("")) {
//                    offer = "0"
//                }
//
//                Log.e(TAG,"209990  MRRP      "+MRRP+"  :  "+MRRP.toFloat())
//                Log.e(TAG,"209990  offer     "+offer)
//
//
//                if ((MRRP.toFloat() != "0".toFloat()) && (offer.toFloat() > MRRP.toFloat())) {
//                    Log.e(TAG,"209991   "+modelLeadCorrectionDetails[i].ProdName)
//                    Config.showCustomToast1("Offer Price Should be less than or Equal to MRP",context)
//                    break
//                }
//                else{
//                    Log.e(TAG,"209992   "+modelLeadCorrectionDetails[i].ProdName)
//                }
//
//
//            }
        check_save = "1"

        modelLeadCorrectionDetailsfinal = correctionProductAdapter!!.returnlist()
        if (modelLeadCorrectionDetailsfinal.size > 0) {
            for (i in 0 until modelLeadCorrectionDetailsfinal.size) {
                var MRRP = modelLeadCorrectionDetailsfinal[i].LgpMRP
                var offer = modelLeadCorrectionDetailsfinal[i].LgpSalesPrice

                if (MRRP!!.equals("")) {
                    MRRP = "0"
                }

                if (offer!!.equals("")) {
                    offer = "0"
                }

                Log.e(TAG, "209990  MRRP      " + MRRP + "  :  " + MRRP.toFloat())
                Log.e(TAG, "209990  offer     " + offer)


                if ((MRRP.toFloat() != "0".toFloat()) && (offer.toFloat() > MRRP.toFloat())) {
                    Log.e(TAG, "209991   " + modelLeadCorrectionDetailsfinal[i].ProdName)
                    Config.showCustomToast1(
                        "Offer Price Should be less than or Equal to MRP",
                        context
                    )
                    check_save = "0"
                    break
                } else {
                    Log.e(TAG, "209992   " + modelLeadCorrectionDetailsfinal[i].ProdName)
                }


            }


            if (check_save.equals("1")) {
                Log.e(TAG, "11222233  1 ")

                var i = 0
                val jsonproduct = JSONArray()
                for (myObject in modelLeadCorrectionDetailsfinal) {


                    val jsonObject2 = JSONObject()
                    var jobjt = array_product_lead.getJSONObject(i)

//                    val strrfollowup = jsonObject.getString("NextActionDate")
//                    var folloupdate = ""
//                    if (!strrfollowup.equals("")){
//                        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
//                        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
//                        val currentDateFormate = inputFormat.parse(strrfollowup)
//                        folloupdate = outputFormat.format(currentDateFormate)
//                    }

                    val Next_action_date = jobjt.getString("NextActionDate")
                    val str_ExpectDate = jobjt.getString("LgpExpectDate")
                    //   val Next_action_date = "01-12-2200"
                    //  val str_ExpectDate  = "01-05-1900"
                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    Log.i("respdate", "next act date ==  " + Next_action_date)
                    Log.i("respdate", "expect date ==  " + str_ExpectDate)
                    var NxtActiondate = ""
                    var ExpDate = ""
                    if (!Next_action_date.equals("null") && !Next_action_date.equals("")) {
                        Log.i(
                            "respdate",
                            "inside...next...................................... === "
                        )
                        val currentDateFormate = inputFormat.parse(Next_action_date)
                        NxtActiondate = outputFormat.format(currentDateFormate)
                    }

                    if (!str_ExpectDate.equals("") && !str_ExpectDate.equals("null")) {
                        Log.i(
                            "respdate",
                            "inside...except...................................... === "
                        )
                        val currentDateFormate_1 = inputFormat.parse(str_ExpectDate)
                        ExpDate = outputFormat.format(currentDateFormate_1)
                    }
                    Log.i("respdate", "NxtActiondate=" + NxtActiondate)
                    Log.i("respdate", "ExpDate=" + ExpDate)

//                    val currentDateFormate = inputFormat.parse(Next_action_date)
                    //      val currentDateFormate_1 = inputFormat.parse(str_ExpectDate)

                    //        val Next_actionDate = outputFormat.format(currentDateFormate)
                    //     val ExpectDate = outputFormat.format(currentDateFormate_1)


//                    Log.e(TAG,"eeee  "+Next_actionDate)
//                    Log.e(TAG,"eeee  "+ExpectDate)

//                Log.e(TAG,"eeee112  "+Next_actionDate)

                    jsonObject2.put("ID_Product", jobjt.getString("ID_Product"))
                    jsonObject2.put("FK_Category", jobjt.getString("FK_Category"))
                    jsonObject2.put("ProdName", jobjt.getString("ProdName"))
                    jsonObject2.put("ProjectName", jobjt.getString("ProjectName"))
                    jsonObject2.put("LgpPQuantity", jobjt.getString("LgpPQuantity"))
                    jsonObject2.put("LgpDescription", jobjt.getString("LgpDescription"))
                    jsonObject2.put("ActStatus", jobjt.getString("ActStatus"))
                    jsonObject2.put("FK_NetAction", jobjt.getString("FK_NetAction"))
                    jsonObject2.put("BranchID", jobjt.getString("BranchID"))
                    jsonObject2.put("BranchTypeID", jobjt.getString("BranchTypeID"))
                    jsonObject2.put("FK_ActionType", jobjt.getString("FK_ActionType"))
                    jsonObject2.put("NextActionDate", NxtActiondate)
                    jsonObject2.put("FK_Departement", jobjt.getString("FK_Departement"))
                    jsonObject2.put("FK_Employee", jobjt.getString("FK_Employee"))
                    jsonObject2.put("FK_Priority", jobjt.getString("FK_Priority"))
                    jsonObject2.put("LgpExpectDate", ExpDate)
                    jsonObject2.put("LgpMRP", jobjt.getString("LgpMRP"))
                    jsonObject2.put("LgpSalesPrice", myObject.LgpSalesPrice)
                    jsonObject2.put("FK_ProductLocation", jobjt.getString("FK_ProductLocation"))

                    jsonproduct.put(jsonObject2)
                    Log.v("sfdsfds", "LgpSalesPrice " + myObject.LgpSalesPrice)
                    Log.v("sfdsfds", "FK_ProductLocation " + jobjt.getString("FK_ProductLocation"))
//                    Log.v("sfdsfds","Next_actionDate "+Next_actionDate)
//                    Log.v("sfdsfds","ExpectDate "+ExpectDate)
                    i = i + 1
                }
                Log.v("sfdsfds", "dsfdsfd " + jsonproduct)
                  saveLeadGeneration(jsonproduct)

            } else {
//                Log.e(TAG,"11222233  2 ")
            }
//            array_product_lead = JSONArray(modelLeadCorrectionDetailsfinal)
//
            //  array_product_lead = JSONArray()
            //  array_product_lead.


        }


    }

    private fun saveLeadGeneration(jsonproduct: JSONArray) {
//        var saveLeadGenDet = 0
        try {

            Log.e(TAG, "encode1   4759   " + LeadGenerationActivity.encode1)
            Log.e(
                TAG,
                "strDate   4759   " + LeadGenerationActivity.strDate + "   " + LeadGenerationActivity.strFollowupdate
            )


//            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val currentDate = strDate

            Log.e(TAG, "strDate   4759   " + strDate)

            val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val currentDateFormate = inputFormat.parse(currentDate)
            val strDate = outputFormat.format(currentDateFormate)


//            >

            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(context, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()

                    leadGenerateSaveViewModel.saveLeadGenerate(
                        this,
                        saveUpdateMode!!,
                        ID_LeadGenerate!!,
                        strDate,
                        ID_Customer!!,
                        ID_MediaSubMaster!!,
                        CusNameTitle!!,
                        Customer_Name!!,
                        Customer_Address1!!,
                        Customer_Address2!!,
                        Customer_Mobile!!,
                        Customer_Email!!,
                        strCompanyContact,
                        FK_Country,
                        FK_States,
                        FK_District,
                        FK_Post,
                        strPincode,
                        FK_Area,
                        ID_LeadFrom!!,
                        ID_LeadThrough!!,
                        strLeadThrough,
                        strWhatsAppNo,
//                        ID_Category!!,
//                        ID_Product!!,
//                        strProduct,
//                        strProject,
//                        strQty,
//                        ID_Priority!!,
//                        strFeedback,
//                        ID_Status!!,
//                        ID_NextAction,
//                        ID_ActionType,
//                        strFollowupdate,
//                        ID_Branch,
//                        ID_BranchType,
//                        ID_Department,
//                        ID_Employee,
                        strLatitude!!,
                        strLongitue!!,
                        encode1,
                        encode2,
                        Customer_Mode!!,
                        Customer_Type!!,
//                        strExpecteddate,
                        ID_CustomerAssignment!!,
                        ID_CollectedBy!!,
                        ID_AuthorizationData!!,
                        jsonproduct

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
                                                jObject.getJSONObject("UpdateLeadGeneration")
                                            try {

                                                val suceessDialog = Dialog(this)
                                                suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                suceessDialog!!.setCancelable(false)
                                                suceessDialog!!.setContentView(R.layout.success_popup)
                                                suceessDialog!!.window!!.attributes.gravity =
                                                    Gravity.CENTER_VERTICAL;

                                                val tv_succesmsg =
                                                    suceessDialog!!.findViewById(R.id.tv_succesmsg) as TextView
                                                val tv_leadid =
                                                    suceessDialog!!.findViewById(R.id.tv_leadid) as TextView
                                                val tv_succesok =
                                                    suceessDialog!!.findViewById(R.id.tv_succesok) as TextView
                                                //LeadNumber
                                                tv_succesmsg!!.setText(jobjt.getString("ResponseMessage"))
                                                tv_leadid!!.setText(jobjt.getString("LeadNumber"))

                                                tv_succesok!!.setOnClickListener {
                                                    suceessDialog!!.dismiss()
//                                                    val i = Intent(this@LeadGenerationActivity, LeadActivity::class.java)
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
                                                    this@LeadCorrectionActivity,
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


//                                        val jobjt = jObject.getJSONObject("UpdateLeadGeneration")
//                                        val builder = AlertDialog.Builder(
//                                            this@LeadGenerationActivity,
//                                            R.style.MyDialogTheme
//                                        )
////                                        builder.setMessage(jObject.getString("EXMessage"))
//                                        builder.setMessage(jobjt.getString("ResponseMessage"))
//                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
//
//                                            val i = Intent(this@LeadGenerationActivity, LeadActivity::class.java)
//                                            startActivity(i)
//                                            finish()

//
//                                        }
//                                        val alertDialog: AlertDialog = builder.create()
//                                        alertDialog.setCancelable(false)
//                                        alertDialog.show()


                                        } else {
                                            val builder = AlertDialog.Builder(
                                                this@LeadCorrectionActivity,
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
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                    ).show()
                                }


                            } catch (e: Exception) {

                                Log.e(TAG, "Exception  4133    " + e.toString())
                                val builder = AlertDialog.Builder(
                                    this@LeadCorrectionActivity,
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

    override fun onClick(position: Int, data: String) {
        TODO("Not yet implemented")
    }
}
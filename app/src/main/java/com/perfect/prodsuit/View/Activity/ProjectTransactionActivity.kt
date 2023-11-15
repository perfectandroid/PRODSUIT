package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ProjectTransactionActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {

    val TAG : String = "ProjectTransactionActivity"
    lateinit var context: Context
    private var progressDialog    : ProgressDialog?    = null

    private var tie_Project       : TextInputEditText? = null
    private var tie_Stage       : TextInputEditText? = null
    private var tie_Date       : TextInputEditText? = null
    private var tie_OtherCharges       : TextInputEditText? = null

    private var til_Project       : TextInputLayout?   = null
    private var til_Stage       : TextInputLayout?   = null
    private var til_Date       : TextInputLayout?   = null
    private var til_OtherCharges       : TextInputLayout?   = null

    lateinit var materialusageProjectViewModel: MaterialUsageProjectViewModel
    lateinit var materialusageStageViewModel  : MaterialUsageStageViewModel
    lateinit var otherChargesViewModel  : OtherChargesViewModel
    lateinit var otherChargeTaxCalculationViewModel  : OtherChargeTaxCalculationViewModel

    var modeTest    = 0
    var usageMode: String? = "1"
    var managementMode: String? = "0"

    var projectcount = 0
    var stagecount   = 0
    var otherchargecount   = 0
    var otherchargetaxcount   = 0
    var otherchargetaxMode   = 0  // 0 = Popup, 1 = change amount
    var otherChargeCalcPosition   = 0
    var otherChargeClickPosition   = 0

    lateinit var projectArrayList  : JSONArray
    lateinit var stageArrayList    : JSONArray
    lateinit var otherChargeArrayList : JSONArray
    lateinit var otherChargeCalcArrayList : JSONArray


    lateinit var projectSort       : JSONArray
    lateinit var stageSort         : JSONArray

    val modelOtherCharges = ArrayList<ModelOtherCharges>()

    private var dialogProject     : Dialog?            = null
    private var dialogStage       : Dialog?            = null

    private var recylist          : RecyclerView?      = null
    private var recyOtherCalc     : RecyclerView?      = null

    private var tvv_list_name     : TextView?          = null

    var ID_Project   = ""
    var ID_Stage   = ""
    var FK_TaxGroup   = ""
    var IncludeTaxCalc   = ""
    var AmountTaxCalc   = ""
    private var strDate                           = ""

    var jsonObj: JSONObject? = null
    private var dialogOtherChargesSheet : Dialog? = null
    val modelOtherChargesCalculation = ArrayList<ModelOtherChargesCalculation>()
    private var dialogOtherChargesCalcSheet : Dialog? = null
    private var otherChargeAdapter : OtherChargeAdapter? = null
    private var taxDetailAdapter : TaxDetailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_project_transaction)
        context = this@ProjectTransactionActivity

        materialusageProjectViewModel   = ViewModelProvider(this).get(MaterialUsageProjectViewModel::class.java)
        materialusageStageViewModel     = ViewModelProvider(this).get(MaterialUsageStageViewModel::class.java)
        otherChargesViewModel     = ViewModelProvider(this).get(OtherChargesViewModel::class.java)
        otherChargeTaxCalculationViewModel     = ViewModelProvider(this).get(
            OtherChargeTaxCalculationViewModel::class.java)

        setRegViews()
        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)

        ID_Project = jsonObj!!.getString("ID_FIELD")
        tie_Project!!.setText(jsonObj!!.getString("ProjName"))


    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tie_Project      = findViewById(R.id.tie_Project)
        tie_Stage    = findViewById(R.id.tie_Stage)
        tie_Date    = findViewById(R.id.tie_Date)
        tie_OtherCharges    = findViewById(R.id.tie_OtherCharges)

        til_Project      = findViewById(R.id.til_Project)
        til_Stage    = findViewById(R.id.til_Stage)
        til_Date    = findViewById(R.id.til_Stage)

        tie_Project!!.setOnClickListener(this)
        tie_Stage!!.setOnClickListener(this)
        tie_Date!!.setOnClickListener(this)
        tie_OtherCharges!!.setOnClickListener(this)

        DecimelFormatters.setDecimelPlace(tie_OtherCharges!!)

        til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Date!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)

        onTextChangedValues()
    }



    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tie_Project->{
               Config.disableClick(v)
                projectcount = 0
                getProject()
            }
            R.id.tie_Stage->{
                if (!ID_Project.equals("")){
                    Config.disableClick(v)
                    stagecount = 0
                    getStage()
                }
                else{
                    til_Project!!.setError("Select Project")
                    til_Project!!.setErrorIconDrawable(null)
                    usageMode = "1"
                    managementMode = "0"

                }
            }
            R.id.tie_Date->{
                Config.disableClick(v)
                openBottomSheet()
            }

            R.id.tie_OtherCharges->{
                Config.disableClick(v)
                otherchargecount = 0
                getOtherCharges()
            }
        }
    }

    private fun getOtherCharges() {
        var ReqMode = "117"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                otherChargesViewModel.getOthercharge(this,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (otherchargecount == 0){
                                    otherchargecount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProjectOtherChargeDetails")
                                        otherChargeArrayList = jobjt.getJSONArray("ProjectOtherChargeDetailsList")
                                        if (otherChargeArrayList.length()>0){
                                            var gridList = Config.getTransType(this@ProjectTransactionActivity)
                                            val jObject1 = JSONObject(gridList)
                                            Log.e(TAG,"gridList  2111   "+gridList)
                                            val jobjt1 = jObject1.getJSONObject("TransType")
                                            var typeArrayList = jobjt1.getJSONArray("TransTypeDetails")

                                            for (i in 0 until otherChargeArrayList.length()) {
                                                var jsonObject = otherChargeArrayList.getJSONObject(i)

                                                var ID_TransType = ""
                                                var TransType_Name = ""

                                                if (jsonObject.getString("OctyTransTypeActive").equals("0")){
                                                    val jsonObject = typeArrayList.getJSONObject(0)
                                                    ID_TransType = jsonObject.getString("ID_TransType")
                                                    TransType_Name = jsonObject.getString("TransType_Name")
                                                }
                                                else if (jsonObject.getString("OctyTransTypeActive").equals("1")){
                                                    val jsonObject = typeArrayList.getJSONObject(1)
                                                    ID_TransType = jsonObject.getString("ID_TransType")
                                                    TransType_Name = jsonObject.getString("TransType_Name")
                                                }
                                                else if (jsonObject.getString("OctyTransTypeActive").equals("2")){
                                                    val jsonObject = typeArrayList.getJSONObject(0)
                                                    ID_TransType = jsonObject.getString("ID_TransType")
                                                    TransType_Name = jsonObject.getString("TransType_Name")
                                                }

                                                modelOtherCharges!!.add(ModelOtherCharges(jsonObject.getString("ID_OtherChargeType"),jsonObject.getString("OctyName"),
                                                    jsonObject.getString("OctyTransTypeActive"),jsonObject.getString("OctyTransType"),jsonObject.getString("FK_TaxGroup"),
                                                    jsonObject.getString("OctyAmount"), jsonObject.getString("OctyTaxAmount"),false,
                                                    ID_TransType, TransType_Name,false))
                                            }
                                        }

                                             otherChargesPopup(modelOtherCharges)
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectTransactionActivity,
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

    private fun otherChargesPopup(modelOtherCharges : ArrayList<ModelOtherCharges>) {
        try {

            dialogOtherChargesSheet = Dialog(this)
            dialogOtherChargesSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogOtherChargesSheet!! .setContentView(R.layout.other_charges_popup)
            dialogOtherChargesSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
            dialogOtherChargesSheet!!.setCancelable(true)

            var recycOtherCharges = dialogOtherChargesSheet!! .findViewById(R.id.recycOtherCharges) as RecyclerView
            var txt_close = dialogOtherChargesSheet!! .findViewById(R.id.txt_close) as TextView
            var txt_apply = dialogOtherChargesSheet!! .findViewById(R.id.txt_apply) as TextView


            val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
            recycOtherCharges!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            otherChargeAdapter = OtherChargeAdapter(this@ProjectTransactionActivity, modelOtherCharges)
            recycOtherCharges!!.adapter = otherChargeAdapter
            otherChargeAdapter!!.setClickListener(this@ProjectTransactionActivity)
            otherChargeAdapter!!.setClickListener(this@ProjectTransactionActivity)

            txt_close!!.setOnClickListener {
                dialogOtherChargesSheet!!.dismiss()
            }

            txt_apply!!.setOnClickListener {

                var otherCharge = 0F
                for (i in 0 until modelOtherCharges.size) {
                    if (modelOtherCharges[i].isCalculate){
                        if (modelOtherCharges[i].OctyIncludeTaxAmount){
                            if (modelOtherCharges[i].ID_TransType.equals("1")){
                                // Debit
                                otherCharge = otherCharge - (modelOtherCharges[i].OctyAmount).toFloat()
                            }else if (modelOtherCharges[i].ID_TransType.equals("2")){
                                // Credit
                                otherCharge = otherCharge + (modelOtherCharges[i].OctyAmount).toFloat()
                            }
                        }else{
                            if (modelOtherCharges[i].ID_TransType.equals("1")){
                                // Debit
                                otherCharge = otherCharge - ((modelOtherCharges[i].OctyAmount).toFloat()+(modelOtherCharges[i].OctyTaxAmount).toFloat())
                            }else if (modelOtherCharges[i].ID_TransType.equals("2")){
                                // Credit
                                otherCharge = otherCharge + ((modelOtherCharges[i].OctyAmount).toFloat()+(modelOtherCharges[i].OctyTaxAmount).toFloat())
                            }
                        }
                    }
                }

                tie_OtherCharges!!.setText(Config.changeTwoDecimel(otherCharge.toString()))
                dialogOtherChargesSheet!!.dismiss()

            }


            dialogOtherChargesSheet!!.show()
            val window: Window? = dialogOtherChargesSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.white);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        }catch (e : Exception){

            Log.e(TAG,"3856  "+e)
        }
    }

    private fun getOtherChargeTax() {
        var ReqMode = "118"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                otherChargeTaxCalculationViewModel.getOtherChargeTaxCalculation(this,ReqMode!!,FK_TaxGroup,IncludeTaxCalc,AmountTaxCalc)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (otherchargetaxcount == 0){
                                    otherchargetaxcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   6733332   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("OtherChargeTaxCalculationDetails")
                                        otherChargeCalcArrayList = jobjt.getJSONArray("OtherChargeTaxCalculationDetailsList")

                                        if (otherChargeCalcArrayList.length() > 0){
                                            if (otherchargetaxMode == 0){
                                                otherChargesCalcPopup(otherChargeCalcArrayList)
                                            }else{
                                                Log.e(TAG,"38777  ")
                                                var sumOfTax = 0F
                                                for (i in 0 until otherChargeCalcArrayList.length()) {
                                                    val jsonObject2 = otherChargeCalcArrayList.getJSONObject(i)


                                                        Log.e(TAG,"40551  "+jsonObject2.getString("Amount"))
                                                        sumOfTax = sumOfTax +(jsonObject2.getString("Amount")).toFloat()

                                                }

                                                modelOtherCharges[otherChargeClickPosition].OctyTaxAmount = sumOfTax.toString()
                                                otherChargeAdapter!!.notifyItemChanged(otherChargeClickPosition)

                                                Log.e(TAG,"40552     "+sumOfTax)
//                                                modelOtherCharges[otherChargeClickPosition].OctyTaxAmount = sumOfTax.toString()
//
//                                                otherChargeAdapter!!.notifyItemChanged(otherChargeClickPosition)
//                                                dialogOtherChargesCalcSheet!!.dismiss()
                                            }

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectTransactionActivity,
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
                                ""+ Config.SOME_TECHNICAL_ISSUES,
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

    private fun otherChargesCalcPopup(otherChargeCalcArrayList: JSONArray) {
        try {


            dialogOtherChargesCalcSheet = Dialog(this)
            dialogOtherChargesCalcSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogOtherChargesCalcSheet!! .setContentView(R.layout.list_other_charge_popup)
            dialogOtherChargesCalcSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            dialogOtherChargesCalcSheet!!.setCancelable(false)
            recyOtherCalc = dialogOtherChargesCalcSheet!! .findViewById(R.id.recyOtherCalc) as RecyclerView
            var btnOk = dialogOtherChargesCalcSheet!! .findViewById(R.id.btnOk) as Button

            var hasId1 =  hasOtherChecked(modelOtherChargesCalculation!!,FK_TaxGroup)
            Log.e(TAG,"3199   "+hasId1)
            if (hasId1){
                Log.e(TAG,"31991   "+hasId1)

                for (i in 0 until otherChargeCalcArrayList.length()) {

                    val jsonObject = otherChargeCalcArrayList.getJSONObject(i)
                    var empModel = modelOtherChargesCalculation[otherChargeCalcPosition]
                    empModel.FK_TaxGroup = FK_TaxGroup
                    empModel.ID_TaxSettings = jsonObject.getString("ID_TaxSettings")
                    empModel.FK_TaxType = jsonObject.getString("FK_TaxType")
                    empModel.TaxTyName = jsonObject.getString("TaxTyName")
                    empModel.TaxPercentage = jsonObject.getString("TaxPercentage")
                    empModel.TaxtyInterstate = jsonObject.getBoolean("TaxtyInterstate")
                    empModel.TaxUpto = jsonObject.getString("TaxUpto")
                    empModel.TaxUptoPercentage = jsonObject.getBoolean("TaxUptoPercentage")
                    empModel.Amount = jsonObject.getString("Amount")

                }
//                val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
//                recyOtherCalc!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                val adapter = TaxDetailAdapter(this@ProjectTransactionActivity, modelOtherChargesCalculation)
//                recyOtherCalc!!.adapter = adapter
//                adapter.setClickListener(this@ProjectTransactionActivity)

            }else{
                Log.e(TAG,"31992   "+hasId1)
                for (i in 0 until otherChargeCalcArrayList.length()) {
                    val jsonObject = otherChargeCalcArrayList.getJSONObject(i)
                    modelOtherChargesCalculation!!.add(
                        ModelOtherChargesCalculation(FK_TaxGroup,jsonObject.getString("ID_TaxSettings"),jsonObject.getString("FK_TaxType"),
                            jsonObject.getString("TaxTyName"),jsonObject.getString("TaxPercentage"),jsonObject.getBoolean("TaxtyInterstate"),
                            jsonObject.getString("TaxUpto"),jsonObject.getBoolean("TaxUptoPercentage"),jsonObject.getString("Amount"))
                    )
                }




            }

            val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
            recyOtherCalc!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            taxDetailAdapter = TaxDetailAdapter(this@ProjectTransactionActivity, modelOtherChargesCalculation)
            recyOtherCalc!!.adapter = taxDetailAdapter
            taxDetailAdapter!!.setClickListener(this@ProjectTransactionActivity)

            btnOk.setOnClickListener {
                var sumOfTax = 0F
                for (i in 0 until modelOtherChargesCalculation.size) {
                    if (modelOtherChargesCalculation[i].FK_TaxGroup.equals(FK_TaxGroup)){

                        Log.e(TAG,"40551  "+modelOtherChargesCalculation[i].Amount)
                        sumOfTax = sumOfTax +(modelOtherChargesCalculation[i].Amount).toFloat()
                    }
                }

                Log.e(TAG,"40552     "+sumOfTax)
                modelOtherCharges[otherChargeClickPosition].OctyTaxAmount = sumOfTax.toString()

                otherChargeAdapter!!.notifyItemChanged(otherChargeClickPosition)
                dialogOtherChargesCalcSheet!!.dismiss()
            }

            dialogOtherChargesCalcSheet!!.show()
            dialogOtherChargesCalcSheet!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onTextChangedValues() {
        tie_Project!!.addTextChangedListener(watcher)
    }

    var watcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //YOUR CODE
            val outputedText = s.toString()
            Log.e(TAG,"28301    "+outputedText)

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //YOUR CODE
        }

        override fun afterTextChanged(editable: Editable) {
            Log.e(TAG,"28302    ")

            when {
                editable === tie_Project!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Project!!.text.toString().equals("")){
                        til_Project!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Project!!.isErrorEnabled = false
                        til_Project!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }
                editable === tie_Date!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Date!!.text.toString().equals("")){
                        til_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Date!!.isErrorEnabled = false
                        til_Date!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }

            }

        }
    }

    private fun getProject() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialusageProjectViewModel.getMaterialUsageProjectModel(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (projectcount == 0){
                                    projectcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProjectList")
                                        projectArrayList = jobjt.getJSONArray("ProjectListDetails")
                                        if (projectArrayList.length()>0){

                                            projectPopup(projectArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectTransactionActivity,
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
                                ""+ Config.SOME_TECHNICAL_ISSUES,
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

    private fun projectPopup(projectArraylist: JSONArray) {
        try {
            modeTest = 1
            dialogProject = Dialog(this)
            dialogProject!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProject!! .setContentView(R.layout.list_popup)
            dialogProject!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogProject!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogProject!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogProject!! .findViewById(R.id.etsearch) as EditText
            tvv_list_name!!.setText("PROJECT LIST")

            projectSort = JSONArray()
            for (k in 0 until projectArraylist.length()) {
                val jsonObject = projectArraylist.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                projectSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectAdapter(this@ProjectTransactionActivity, projectSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@ProjectTransactionActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    projectSort = JSONArray()

                    for (k in 0 until projectArraylist.length()) {
                        val jsonObject = projectArraylist.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ProjName").length) {
                            if (jsonObject.getString("ProjName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                projectSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"projectSort               7103    "+projectSort)
                    val adapter = ProjectAdapter(this@ProjectTransactionActivity, projectSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@ProjectTransactionActivity)
                }
            })

            dialogProject!!.show()
            dialogProject!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getStage() {
        // var leadInfo = 0
        var ReqMode = "17"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialusageStageViewModel.getMaterialUsageStageModel(this,ID_Project!!,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (stagecount == 0){
                                    stagecount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProjectStagesList")
                                        stageArrayList = jobjt.getJSONArray("ProjectStagesListDetails")
                                        if (stageArrayList.length()>0){

                                            stagePopup(stageArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ProjectTransactionActivity,
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
                                ""+ Config.SOME_TECHNICAL_ISSUES,
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

    private fun stagePopup(stageArrayList: JSONArray) {
        try {
            modeTest = 2
            dialogStage = Dialog(this)
            dialogStage!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogStage!! .setContentView(R.layout.list_popup)
            dialogStage!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogStage!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogStage!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogStage!! .findViewById(R.id.etsearch) as EditText
            tvv_list_name!!.setText("STAGE LIST")

            stageSort = JSONArray()
            for (k in 0 until stageArrayList.length()) {
                val jsonObject = stageArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                stageSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = StageAdapter(this@ProjectTransactionActivity, stageSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@ProjectTransactionActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    stageSort = JSONArray()

                    for (k in 0 until stageArrayList.length()) {
                        val jsonObject = stageArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("StageName").length) {
                            if (jsonObject.getString("StageName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                stageSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"stageSort               7103    "+stageSort)
                    val adapter = StageAdapter(this@ProjectTransactionActivity, stageSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@ProjectTransactionActivity)
                }
            })

            dialogStage!!.show()
            dialogStage!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openBottomSheet() {


        val dialog = BottomSheetDialog(this)
        val view   = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel    = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit    = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.MONTH, 1)
        date_Picker1.maxDate = maxDate.timeInMillis

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                val day: Int   = date_Picker1!!.getDayOfMonth()
                val mon: Int   = date_Picker1!!.getMonth()
                val month: Int = mon + 1
                val year: Int  = date_Picker1!!.getYear()
                var strDay     = day.toString()
                var strMonth   = month.toString()
                var strYear    = year.toString()
                if (strDay.length == 1) {
                    strDay     = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }

                tie_Date!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                strDate = strYear + "-" + strMonth + "-" + strDay

            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }




    private fun hasOtherChecked(modelOtherChargesCalculation: ArrayList<ModelOtherChargesCalculation>, FK_TaxGroup: String): Boolean {
        var isChecked = false
        for (i in 0 until modelOtherChargesCalculation.size) {
            if (modelOtherChargesCalculation.get(i).FK_TaxGroup.equals(FK_TaxGroup)){
                isChecked = true
                otherChargeCalcPosition = i
                break
            }
        }
        return isChecked
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("projectClick")){

            dialogProject!!.dismiss()
            val jsonObject = projectSort.getJSONObject(position)

            ID_Project = jsonObject.getString("ID_FIELD")
            tie_Project!!.setText(jsonObject.getString("ProjName"))

            ID_Stage = ""
            tie_Stage!!.setText("")

        }
        if (data.equals("stageCliik")){

            dialogStage!!.dismiss()
            val jsonObject = stageSort.getJSONObject(position)
            ID_Stage = jsonObject.getString("ProjectStagesID")
            tie_Stage!!.setText(jsonObject.getString("StageName"))


        }

        if (data.equals("IncludeTaxClick")){

            Log.e(TAG,"IncludeTaxClick  6733   "+ modelOtherCharges[position].OctyIncludeTaxAmount)
//            if (modelOtherCharges[position].OctyIncludeTaxAmount){
////                FK_TaxGroup = modelOtherCharges[position].FK_TaxGroup
////                otherchargetaxcount = 0
////                getOtherChargeTax()
//            }else{
//
//            }
            otherChargeClickPosition = position
            FK_TaxGroup = modelOtherCharges[position].FK_TaxGroup
            AmountTaxCalc = modelOtherCharges[position].OctyAmount
            if (modelOtherCharges[position].OctyIncludeTaxAmount){
                IncludeTaxCalc = "1"
            }else{
                IncludeTaxCalc = "0"
            }

            otherchargetaxcount = 0
            otherchargetaxMode = 0
            getOtherChargeTax()

        }

        if (data.equals("IncludeTaxAmountClick")){

            Log.e(TAG,"IncludeTaxAmountClick  8288   "+ modelOtherCharges[position].OctyIncludeTaxAmount)
            otherChargeClickPosition = position
            FK_TaxGroup = modelOtherCharges[position].FK_TaxGroup
            AmountTaxCalc = modelOtherCharges[position].OctyAmount
            if (modelOtherCharges[position].OctyIncludeTaxAmount){
                IncludeTaxCalc = "1"
            }else{
                IncludeTaxCalc = "0"
            }
            FK_TaxGroup = modelOtherCharges[position].FK_TaxGroup
            otherchargetaxcount = 0
            otherchargetaxMode = 0
            getOtherChargeTax()
        }

        if (data.equals("TaxAmountClaculateClick")){

            Log.e(TAG,"TaxAmountClaculateClick  8288   "+ modelOtherCharges[position].OctyIncludeTaxAmount)
            otherChargeClickPosition = position
            FK_TaxGroup = modelOtherCharges[position].FK_TaxGroup
            AmountTaxCalc = modelOtherCharges[position].OctyAmount
            if (modelOtherCharges[position].OctyIncludeTaxAmount){
                IncludeTaxCalc = "1"
            }else{
                IncludeTaxCalc = "0"
            }
            FK_TaxGroup = modelOtherCharges[position].FK_TaxGroup
            otherchargetaxcount = 0
            otherchargetaxMode = 1
            getOtherChargeTax()
        }

    }




}
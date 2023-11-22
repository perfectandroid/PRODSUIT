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
import androidx.lifecycle.Observer
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelUsageProduct
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MaterialUsageActivity : AppCompatActivity(),  View.OnClickListener, ItemClickListener {


    val TAG: String = "MaterialUsageActivity"
    lateinit var context          : Context
    private var tie_Project       : TextInputEditText? = null
    private var tie_UsageDate     : TextInputEditText? = null
    private var tie_Stage         : TextInputEditText? = null
    private var tie_Team          : TextInputEditText? = null
    private var tie_Employee      : TextInputEditText? = null
    private var tie_Mode          : TextInputEditText? = null
    private var tie_Quantity      : TextInputEditText? = null
    private var tie_Product       : TextInputEditText? = null
    private var til_Project       : TextInputLayout?   = null
    private var til_UsageDate     : TextInputLayout?   = null
    private var til_Stage         : TextInputLayout?   = null
    private var til_Team          : TextInputLayout?   = null
    private var til_Product       : TextInputLayout?   = null
    private var til_Quantity      : TextInputLayout?   = null
    private var til_Mode          : TextInputLayout?   = null
    private var img_ProductAdd    : ImageView?         = null
    private var imback            : ImageView?         = null
    private var img_ProductRefresh: ImageView?         = null
    private var tv_Material       : TextView?          = null
    private var tv_MaterialRqst   : TextView?          = null
    private var tvv_list_name     : TextView?          = null
    private var ll_MaterialUsage  : LinearLayout?      = null
    private var ll_MaterialRqst   : LinearLayout?      = null
    private var progressDialog    : ProgressDialog?    = null
    private var dialogProject     : Dialog?            = null
    private var dialogStage       : Dialog?            = null
    private var dialogTeam        : Dialog?            = null
    private var dialogTeamEmp     : Dialog?            = null
    private var dialogProduct     : Dialog?            = null
    private var dialogqty         : Dialog?            = null
    private var dialogMode        : Dialog?            = null
    private var recylist          : RecyclerView?      = null
    private var recyEmpDetails    : RecyclerView?      = null
    private var ll_usage_product  : LinearLayout?      = null
    private var btnReset          : Button?            = null
    private var btnSubmit         : Button?            = null
    private var strUsagedate                           = ""
    lateinit var materialusageProjectViewModel: MaterialUsageProjectViewModel
    lateinit var materialusageStageViewModel  : MaterialUsageStageViewModel
    lateinit var materialusageTeamViewModel   : MaterialUsageTeamViewModel
    lateinit var materialusageProductViewModel: MaterialUsageProductViewModel
    lateinit var materialModeViewModel: MaterialModeViewModel
    lateinit var teamEmployeeViewModel: TeamEmployeeViewModel

    lateinit var materialUsageSaveViewModel: MaterialUsageSaveViewModel

    lateinit var projectArrayList  : JSONArray
    lateinit var stageArrayList    : JSONArray
    lateinit var teamArrayList     : JSONArray
    lateinit var teamEmployeeArrayList     : JSONArray
    lateinit var productArrayList  : JSONArray
    lateinit var modeArrayList     : JSONArray
    lateinit var projectSort       : JSONArray
    lateinit var stageSort         : JSONArray
    lateinit var teamSort          : JSONArray
    lateinit var teamEmpSort       : JSONArray
    lateinit var productSort       : JSONArray
    lateinit var modeSort          : JSONArray

    var visibility   = "1"
    var projectcount = 0
    var stagecount   = 0
    var teamcount    = 0
    var employeecount    = 0
    var productcount = 0
    var qtycount     = 0
    var modecount    = 0
    var saveCount    = 0

    var ID_Project   = ""
    var ID_Stage   = ""
    var ID_Team   = ""
    var ID_Employee   = "0"

    var ID_Product   = ""
    var ID_Stock   = ""
    var productTotQty   = ""
    var productQty   = ""
    var ID_Mode   = ""
    val modelUsageProduct = ArrayList<ModelUsageProduct>()
    var materialUsageAdapter : MaterialUsageAdapter? = null


    var modeTest    = 0
    var editMode = 0 // 1 = Edit ,0 = New
    var editIndex = 0
    var validateMessage   = ""

    var usageMode: String? = "1"
    var managementMode: String? = "0"

    var saveDetailArray :JSONArray? = null
    private var dialogConfirm : Dialog? = null

    var jsonObj: JSONObject? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_material_usage)

        context = this@MaterialUsageActivity
        materialusageProjectViewModel   = ViewModelProvider(this).get(MaterialUsageProjectViewModel::class.java)
        materialusageProductViewModel   = ViewModelProvider(this).get(MaterialUsageProductViewModel::class.java)
        materialusageStageViewModel     = ViewModelProvider(this).get(MaterialUsageStageViewModel::class.java)
        materialusageTeamViewModel      = ViewModelProvider(this).get(MaterialUsageTeamViewModel::class.java)
        teamEmployeeViewModel      = ViewModelProvider(this).get(TeamEmployeeViewModel::class.java)
        materialModeViewModel      = ViewModelProvider(this).get(MaterialModeViewModel::class.java)
        materialUsageSaveViewModel      = ViewModelProvider(this).get(MaterialUsageSaveViewModel::class.java)

        setRegViews()
        var jsonObject: String? = intent.getStringExtra("jsonObject")
        jsonObj = JSONObject(jsonObject)
        ID_Project = jsonObj!!.getString("ID_Project")
        tie_Project!!.setText(jsonObj!!.getString("ProjName"))

        usageMode = "1"
        managementMode = "0"

        hideViews()
    }



    private fun setRegViews() {

        tie_Project      = findViewById(R.id.tie_Project)
        tie_UsageDate    = findViewById(R.id.tie_UsageDate)
        tie_Stage        = findViewById(R.id.tie_Stage)
        tie_Team         = findViewById(R.id.tie_Team)
        tie_Employee     = findViewById(R.id.tie_Employee)
        tie_Mode         = findViewById(R.id.tie_Mode)
        tie_Quantity     = findViewById(R.id.tie_Quantity)
        tie_Product      = findViewById(R.id.tie_Product)
        img_ProductAdd   = findViewById(R.id.img_ProductAdd)
        img_ProductRefresh = findViewById(R.id.img_ProductRefresh)
        imback           = findViewById(R.id.imback)
        tv_Material      = findViewById(R.id.tv_Material)
        ll_MaterialUsage = findViewById(R.id.ll_MaterialUsage)
        tv_MaterialRqst  = findViewById(R.id.tv_MaterialRqst)
        ll_MaterialRqst  = findViewById(R.id.ll_MaterialRqst)
        til_Project      = findViewById(R.id.til_Project)
        til_UsageDate    = findViewById(R.id.til_UsageDate)
        til_Stage        = findViewById(R.id.til_Stage)
        til_Team         = findViewById(R.id.til_Team)
        til_Product      = findViewById(R.id.til_Product)
        til_Quantity     = findViewById(R.id.til_Quantity)
        til_Mode         = findViewById(R.id.til_Mode)
        tvv_list_name    = findViewById(R.id.tvv_list_name)
        recyEmpDetails   = findViewById(R.id.recyEmpDetails)
        ll_usage_product = findViewById(R.id.ll_usage_product)
        btnReset         = findViewById(R.id.btnReset)
        btnSubmit        = findViewById(R.id.btnSubmit)


        tie_Project!!.setOnClickListener(this)
        tie_UsageDate!!.setOnClickListener(this)
        tie_Stage!!.setOnClickListener(this)
        tie_Team!!.setOnClickListener(this)
        tie_Employee!!.setOnClickListener(this)
        tie_Mode!!.setOnClickListener(this)
        tie_Quantity!!.setOnClickListener(this)
        tie_Product!!.setOnClickListener(this)
        img_ProductAdd!!.setOnClickListener(this)
        img_ProductRefresh!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        tv_Material!!.setOnClickListener(this)
        tv_MaterialRqst!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
//        ll_MaterialUsage!!.setOnClickListener(this)



        til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_UsageDate!!.defaultHintTextColor = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Stage!!.defaultHintTextColor     = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Team!!.defaultHintTextColor      = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Product!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Quantity!!.defaultHintTextColor  = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Mode!!.defaultHintTextColor      = ContextCompat.getColorStateList(this,R.color.color_mandatory)

        DecimelFormatters.setDecimelPlace(tie_Quantity!!)

        onTextChangedValues()

        getCurrentDate()
    }

    private fun hideViews() {

        ll_MaterialUsage!!.visibility = View.GONE
        ll_MaterialRqst!!.visibility = View.GONE

        if (usageMode.equals("1")) {
            ll_MaterialUsage!!.visibility = View.VISIBLE
        }
        if (managementMode.equals("1")) {
            ll_MaterialRqst!!.visibility = View.VISIBLE
        }
    }

    private fun onTextChangedValues() {
        tie_Project!!.addTextChangedListener(watcher)
        tie_UsageDate!!.addTextChangedListener(watcher)
        tie_Stage!!.addTextChangedListener(watcher)
        tie_Team!!.addTextChangedListener(watcher)

        tie_Product!!.addTextChangedListener(watcher)
        tie_Quantity!!.addTextChangedListener(watcher)
        tie_Mode!!.addTextChangedListener(watcher)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imback -> {
                finish()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.tv_Material -> {

                if (usageMode.equals("1")){
                    ll_MaterialUsage!!.visibility = View.GONE
                    usageMode = "0"
                }
                else{
                    usageMode = "1"
                    managementMode = "0"
                    hideViews()
                }



            }
            R.id.tv_MaterialRqst -> {

                if (managementMode.equals("1")){
                    ll_MaterialRqst!!.visibility = View.GONE
                    managementMode = "0"
                }
                else{
                    usageMode = "0"
                    managementMode = "1"
                    hideViews()
                }
            }
            R.id.tie_Project -> {
                Config.disableClick(v)
                projectcount = 0
                getProject()
            }
            R.id.tie_Stage -> {
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

                    hideViews()
                }

            }
            R.id.tie_UsageDate -> {
                Config.disableClick(v)
                openBottomSheet()
            }
            R.id.tie_Team -> {

                if (!ID_Stage.equals("")){
                    Config.disableClick(v)
                    teamcount = 0
                    getTeam()
                }
                else{
                    til_Stage!!.setError("Select Stage")
                    til_Stage!!.setErrorIconDrawable(null)
                    usageMode = "1"
                    managementMode = "0"

                    hideViews()
                }

            }

            R.id.tie_Employee -> {

                if (!ID_Team.equals("")){
                    Config.disableClick(v)
                    employeecount = 0
                    getEmployeeFromTeam()
                }
                else{
                    til_Team!!.setError("Select Team")
                    til_Team!!.setErrorIconDrawable(null)
                    usageMode = "1"
                    managementMode = "0"

                    hideViews()
                }

            }
            R.id.tie_Product -> {
                if (!ID_Team.equals("")){
                    Config.disableClick(v)
                    productcount = 0
                    getProduct()

                }else{
                    til_Team!!.setError("Select Team")
                    til_Team!!.setErrorIconDrawable(null)
                    usageMode = "1"
                    managementMode = "0"

                    hideViews()

                }



            }
            R.id.tie_Mode -> {
                Config.disableClick(v)
                modecount = 0
                getMode()
            }

            R.id.img_ProductAdd -> {
                Config.Utils.hideSoftKeyBoard(context,v)
                managementValidation(v)
            }

            R.id.img_ProductRefresh -> {
                clearProducts()
            }



            R.id.btnReset -> {

              resetData()
            }
            R.id.btnSubmit -> {

                Log.e(TAG,"369    "+Config.convertDate(tie_UsageDate!!.text.toString()))
                Config.disableClick(v)
                validation(v)
            }
        }
    }

    private fun resetData() {

//        ID_Project = ""
        ID_Stage = ""
        ID_Team = ""
        ID_Employee = "0"

//        tie_Project!!.setText("")
        tie_Stage!!.setText("")
        tie_Team!!.setText("")
        tie_Employee!!.setText("")

        ID_Product = ""
        ID_Stock = ""
        ID_Mode = ""

        tie_Product!!.setText("")
        tie_Quantity!!.setText("")
        tie_Mode!!.setText("")

        ll_usage_product!!.visibility = View.GONE
        modelUsageProduct.clear()

        getCurrentDate()

        usageMode = "1"
        managementMode = "0"

        hideViews()



    }

    private fun managementValidation(v : View) {

        productQty = tie_Quantity!!.text.toString()
        if (productQty.equals("") || productQty.equals(".")){
            productQty ="0"
        }
        if (ID_Product.equals("")){
            til_Product!!.setError("Select Product")
            til_Product!!.setErrorIconDrawable(null)
        }
        else if (productQty.toFloat() <= 0){
            til_Quantity!!.setError("Enter Quantity")
            til_Quantity!!.setErrorIconDrawable(null)
        }
        else if (ID_Mode.equals("")){
            til_Mode!!.setError("Select Mode")
            til_Mode!!.setErrorIconDrawable(null)
        }
        else{

            var hasId =  hasAll(modelUsageProduct!!,ID_Product!!,productQty!!,ID_Mode!!)

            if (hasId){

                if (editMode == 1){
                    modelUsageProduct[editIndex].ID_Product = ID_Product
                    modelUsageProduct[editIndex].ID_Mode = ID_Mode
                    modelUsageProduct[editIndex].Quantity = productQty
                    modelUsageProduct[editIndex].Product = tie_Product!!.text.toString()
                    modelUsageProduct[editIndex].Mode = tie_Mode!!.text.toString()
                    modelUsageProduct[editIndex].StockId = ID_Stock

                    materialUsageAdapter!!.notifyItemChanged(editIndex)
                }else{
                    modelUsageProduct!!.add(ModelUsageProduct(ID_Product,tie_Product!!.text.toString(),productQty,ID_Mode,tie_Mode!!.text.toString(),ID_Stock))
                    if (modelUsageProduct.size>0){
                        ll_usage_product!!.visibility = View.VISIBLE
                        val lLayout = GridLayoutManager(this@MaterialUsageActivity, 1)
                        recyEmpDetails!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                        materialUsageAdapter = MaterialUsageAdapter(this@MaterialUsageActivity,modelUsageProduct)
                        recyEmpDetails!!.adapter = materialUsageAdapter
                        materialUsageAdapter!!.setClickListener(this@MaterialUsageActivity)
                    }
                    else{
                        ll_usage_product!!.visibility = View.GONE
                    }
                }

                clearProducts()
            }
            else{
                Config.snackBars(context,v,""+validateMessage)
            }




        }
    }

    private fun clearProducts() {

        editMode = 0
        editIndex = 0
        ID_Product = ""
        ID_Stock = ""
        ID_Mode = ""
        tie_Product!!.setText("")
        tie_Quantity!!.setText("")
        tie_Mode!!.setText("")
    }

    private fun hasAll(modelUsageProduct: ArrayList<ModelUsageProduct>, ID_Product: String, productQty: String, ID_Mode : String): Boolean {
        var result = true
        var sumQty : Float = 0.0F


        if (editMode == 1){
            // Edit
            for (i in 0 until modelUsageProduct.size) {
                Log.e(TAG,"3800 Product "+modelUsageProduct.get(i).ID_Product)
                Log.e(TAG,"3800 editIndex "+modelUsageProduct.get(editIndex).ID_Product)
                Log.e(TAG,"3800 ID_Product "+ID_Product)

                if ((modelUsageProduct.get(i).ID_Product).equals(ID_Product) && i != editIndex){
                    sumQty = sumQty + (modelUsageProduct.get(i).Quantity).toFloat()
                }
            }
            sumQty = sumQty + productQty.toFloat()
            Log.e(TAG,"sumQty  3941   "+sumQty)

            for (i in 0 until modelUsageProduct.size) {

                if (i != editIndex){
                    if (modelUsageProduct.get(i).ID_Product == ID_Product && modelUsageProduct.get(i).ID_Mode == ID_Mode) {

                        validateMessage = "Product Already exits"
                        result = false
                    }
                    else if (productTotQty.toFloat() >= sumQty){
                        result = true
                    }else{
                        validateMessage = "Quantity Cannot be Greater than Current Stock"
                        result = false
                    }
                }
            }
//            if (productTotQty.toFloat() >= sumQty){
//                result = true
//            }else{
//                validateMessage = "Quantity Cannot be Greater than Currennt Stock"
//                result = false
//            }

        }else{
            // Save

            for (i in 0 until modelUsageProduct.size) {
                if ((modelUsageProduct.get(i).ID_Product).equals(ID_Product)){
                    sumQty = sumQty + (modelUsageProduct.get(i).Quantity).toFloat()
                }
            }

            sumQty = sumQty + productQty.toFloat()
            Log.e(TAG,"sumQty  3942   "+sumQty+"  :  "+productTotQty)
            if(modelUsageProduct.size > 0){
                for (i in 0 until modelUsageProduct.size) {  // iterate through the JsonArray

                    if (modelUsageProduct.get(i).ID_Product == ID_Product && modelUsageProduct.get(i).ID_Mode == ID_Mode) {

                        validateMessage = "Product Already exits"
                        result = false
                    }
                    else if (productTotQty.toFloat() < sumQty){
                        validateMessage = "Quantity Cannot be Greater than Currennt Stock"
                        result = false
                    }

                }
            }
            else{
                if (productTotQty.toFloat() < sumQty){
                    validateMessage = "Quantity Cannot be Greater than Currennt Stock"
                    result = false
                }
            }

        }


        return result
    }

    private fun validation(v : View) {

//        Validate date
        if (ID_Project.equals("")){
            til_Project!!.setError("Select Project")
            til_Project!!.setErrorIconDrawable(null)
            usageMode = "1"
            managementMode = "0"

            hideViews()
        }
        else if (ID_Stage.equals("")){
            til_Stage!!.setError("Select Stage")
            til_Stage!!.setErrorIconDrawable(null)
            usageMode = "1"
            managementMode = "0"

            hideViews()
        }
        else if (ID_Team.equals("")){
            til_Team!!.setError("Select Team")
            til_Team!!.setErrorIconDrawable(null)
            usageMode = "1"
            managementMode = "0"

            hideViews()
        }
        else if (modelUsageProduct.size <= 0){
            usageMode = "0"
            managementMode = "1"

            hideViews()
            Config.snackBars(context,v,"Enter Atleast One Material")
        }
        else{
            Config.disableClick(v)
//            saveCount = 0
//            saveMaterialUsage()

//            confirmationScreen(v)

            createDetailArray(v)
        }
    }

    private fun getCurrentDate() {


        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            Log.e(TAG, "DATE TIME  196  " + currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG, "newDate  196  " + newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)


            tie_UsageDate!!.setText("" + sdfDate1.format(newDate))
            strUsagedate = "" + sdfDate1.format(newDate)

            Log.e(TAG, "strUsagedate 4582  " + strUsagedate)

        } catch (e: Exception) {
            Log.e(TAG, "strUsagedate 4582   " + e.toString())
        }
    }

    private fun openBottomSheet() {


        val dialog = BottomSheetDialog(this)
        val view   = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel    = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit    = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

//        date_Picker1.setMinDate(System.currentTimeMillis())
        date_Picker1.maxDate = System.currentTimeMillis()
//        date_Picker1.minDate = System.currentTimeMillis()


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

                    tie_UsageDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                    strUsagedate = strYear + "-" + strMonth + "-" + strDay

            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
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
                                            this@MaterialUsageActivity,
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

            val lLayout = GridLayoutManager(this@MaterialUsageActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectAdapter(this@MaterialUsageActivity, projectSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@MaterialUsageActivity)

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
                    val adapter = ProjectAdapter(this@MaterialUsageActivity, projectSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@MaterialUsageActivity)
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
        var ReqMode = "3"
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
                                            this@MaterialUsageActivity,
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

            val lLayout = GridLayoutManager(this@MaterialUsageActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = StageAdapter(this@MaterialUsageActivity, stageSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@MaterialUsageActivity)

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
                    val adapter = StageAdapter(this@MaterialUsageActivity, stageSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@MaterialUsageActivity)
                }
            })

            dialogStage!!.show()
            dialogStage!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getTeam() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialusageTeamViewModel.getMaterialUsageTeamModel(this,ID_Project!!,ID_Stage!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (teamcount == 0){
                                    teamcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProjectTeamList")
                                        teamArrayList = jobjt.getJSONArray("ProjectTeamListDetails")
                                        if (teamArrayList.length()>0){

                                            teamPopup(teamArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MaterialUsageActivity,
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

    private fun teamPopup(teamArrayList: JSONArray) {
        try {

            modeTest = 3
            dialogTeam = Dialog(this)
            dialogTeam!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogTeam!! .setContentView(R.layout.list_popup)
            dialogTeam!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogTeam!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogTeam!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogTeam!! .findViewById(R.id.etsearch) as EditText
            tvv_list_name!!.setText("TEAM LIST")

            teamSort = JSONArray()
            for (k in 0 until teamArrayList.length()) {
                val jsonObject = teamArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                teamSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@MaterialUsageActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = TeamAdapter(this@MaterialUsageActivity, teamSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@MaterialUsageActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    teamSort = JSONArray()

                    for (k in 0 until teamArrayList.length()) {
                        val jsonObject = teamArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("TeamName").length) {
                            if (jsonObject.getString("TeamName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                teamSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"teamSort               7103    "+teamSort)
                    val adapter = TeamAdapter(this@MaterialUsageActivity, teamSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@MaterialUsageActivity)
                }
            })

            dialogTeam!!.show()
            dialogTeam!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getEmployeeFromTeam() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                teamEmployeeViewModel.getTeamEmployee(this,ID_Team!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (employeecount == 0){
                                    employeecount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeListforProject")
                                        teamEmployeeArrayList = jobjt.getJSONArray("EmployeeListforProjectDetails")
                                        if (teamEmployeeArrayList.length()>0){

                                            teamEmployeePopup(teamEmployeeArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MaterialUsageActivity,
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

    private fun teamEmployeePopup(teamEmployeeArrayList: JSONArray) {
        try {

            dialogTeamEmp = Dialog(this)
            dialogTeamEmp!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogTeamEmp!! .setContentView(R.layout.list_popup)
            dialogTeamEmp!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogTeamEmp!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogTeamEmp!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogTeamEmp!! .findViewById(R.id.etsearch) as EditText
            tvv_list_name!!.setText("Employee")

            teamEmpSort = JSONArray()
            for (k in 0 until teamEmployeeArrayList.length()) {
                val jsonObject = teamEmployeeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                teamEmpSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@MaterialUsageActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = TeamEmployeeAdapter(this@MaterialUsageActivity, teamEmpSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@MaterialUsageActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    teamEmpSort = JSONArray()

                    for (k in 0 until teamEmployeeArrayList.length()) {
                        val jsonObject = teamEmployeeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Name").length) {
                            if (jsonObject.getString("Name")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                teamEmpSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"teamEmpSort               7103    "+teamEmpSort)
                    val adapter = TeamEmployeeAdapter(this@MaterialUsageActivity, teamEmpSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@MaterialUsageActivity)
                }
            })

            dialogTeamEmp!!.show()
            dialogTeamEmp!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun getProduct() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialusageProductViewModel.getMaterialProduct(this,ID_Project!!,ID_Stage!!,ID_Team!!,ID_Employee)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (productcount == 0){
                                    productcount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("MatProductDetails")
                                        productArrayList = jobjt.getJSONArray("MatProductListDetails")
                                        if (productArrayList.length()>0){

                                            productPopup(productArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MaterialUsageActivity,
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

    private fun productPopup(productArrayList: JSONArray) {
        try {

            dialogProduct = Dialog(this)
            dialogProduct!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProduct!! .setContentView(R.layout.list_popup)
            dialogProduct!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogProduct!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogProduct!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogProduct!! .findViewById(R.id.etsearch) as EditText
            tvv_list_name!!.setText("PRODUCT LIST")

            productSort = JSONArray()
            for (k in 0 until productArrayList.length()) {
                val jsonObject = productArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                productSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@MaterialUsageActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectProductAdapter(this@MaterialUsageActivity, productSort,"0")
            recylist!!.adapter = adapter
            adapter.setClickListener(this@MaterialUsageActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    productSort = JSONArray()

                    for (k in 0 until productArrayList.length()) {
                        val jsonObject = productArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Name").length) {
                            if (jsonObject.getString("Name")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                productSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"productSort               7103    "+productSort)
                    val adapter = ProjectProductAdapter(this@MaterialUsageActivity, productSort,"0")
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@MaterialUsageActivity)
                }
            })

            dialogProduct!!.show()
            dialogProduct!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getMode() {
        // var leadInfo = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialModeViewModel.getMaterialMode(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (modecount == 0){
                                    modecount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ModeList")
                                        modeArrayList = jobjt.getJSONArray("ModeListDetails")
                                        if (modeArrayList.length()>0){

                                            modePopup(modeArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MaterialUsageActivity,
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

    private fun modePopup(modeArrayList: JSONArray) {
        try {

            dialogMode = Dialog(this)
            dialogMode!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogMode!! .setContentView(R.layout.list_popup)
            dialogMode!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogMode!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogMode!! .findViewById(R.id.tvv_list_name) as TextView
            val etsearch = dialogMode!! .findViewById(R.id.etsearch) as EditText
            tvv_list_name!!.setText("Mode")

            modeSort = JSONArray()
            for (k in 0 until modeArrayList.length()) {
                val jsonObject = modeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                modeSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@MaterialUsageActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectModeAdapter(this@MaterialUsageActivity, modeSort)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@MaterialUsageActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    modeSort = JSONArray()

                    for (k in 0 until modeArrayList.length()) {
                        val jsonObject = modeArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("ModeName").length) {
                            if (jsonObject.getString("ModeName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                modeSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"modeSort               7103    "+modeSort)
                    val adapter = ProjectModeAdapter(this@MaterialUsageActivity, modeSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@MaterialUsageActivity)
                }
            })

            dialogMode!!.show()
            dialogMode!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createDetailArray(v : View) {
        try {
            saveDetailArray = JSONArray()
//            {"ProductID":"1548","Quantity":"23","StockId":"23","Mode":"23"},
//           var obj = JSONObject()
            for (i in 0 until modelUsageProduct.size) {
                var empModel = modelUsageProduct[i]
                var obj = JSONObject()
                obj.put("ProductID", empModel.ID_Product)
                obj.put("Quantity", empModel.Quantity)
                obj.put("StockId", empModel.StockId)
                obj.put("Mode", empModel.ID_Mode)
                saveDetailArray!!.put(obj)
            }
            Log.e(TAG,"70000  jsonObject  "+saveDetailArray)
            confirmationScreen(v)

        }catch (e : Exception){
            Log.e(TAG,"70000  Exception  "+e.toString())
        }
    }

    private fun confirmationScreen(v : View) {
        try {


            dialogConfirm = Dialog(this)
            dialogConfirm!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogConfirm!! .setContentView(R.layout.confirm_material_usage)
            dialogConfirm!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

            var ll_Employee = dialogConfirm!! .findViewById(R.id.ll_Employee) as LinearLayout

            var tvc_Project = dialogConfirm!! .findViewById(R.id.tvc_Project) as TextView
            var tvc_Date = dialogConfirm!! .findViewById(R.id.tvc_Date) as TextView
            var tvc_Stage = dialogConfirm!! .findViewById(R.id.tvc_Stage) as TextView
            var tvc_Team = dialogConfirm!! .findViewById(R.id.tvc_Team) as TextView
            var tvc_Employee = dialogConfirm!! .findViewById(R.id.tvc_Employee) as TextView


            var recyConfirmUsage = dialogConfirm!! .findViewById(R.id.recyConfirmUsage) as RecyclerView



            var btnCancel = dialogConfirm!! .findViewById(R.id.btnCancel) as Button
            var btnOk = dialogConfirm!! .findViewById(R.id.btnOk) as Button

            tvc_Project!!.setText(tie_Project!!.text.toString())
            tvc_Date!!.setText(tie_UsageDate!!.text.toString())
            tvc_Stage!!.setText(tie_Stage!!.text.toString())
            tvc_Team!!.setText(tie_Team!!.text.toString())
            tvc_Employee!!.setText(tie_Employee!!.text.toString())


            if (ID_Employee.equals("0")){
                ll_Employee!!.visibility  =View.GONE
            }


            if (modelUsageProduct.size>0){

                val lLayout = GridLayoutManager(this@MaterialUsageActivity, 1)
                recyConfirmUsage!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                var confirmAdapter = MaterialUsageConfirmAdapter(this@MaterialUsageActivity,modelUsageProduct,"0")
                recyConfirmUsage!!.adapter = confirmAdapter

            }

            btnCancel!!.setOnClickListener {
                dialogConfirm!!.dismiss()
            }

            btnOk!!.setOnClickListener {
                dialogConfirm!!.dismiss()
                saveCount = 0
                saveMaterialUsage()
            }

            val window: Window? = dialogConfirm!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            dialogConfirm!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveMaterialUsage() {
         var UserAction = "1"
         var TransMode = "PRMU"
         var ID_ProjectMaterialUsage = "0"

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                materialUsageSaveViewModel.saveMaterialUsage(this,UserAction!!,TransMode!!,ID_ProjectMaterialUsage!!,ID_Stage,ID_Project,
                    strUsagedate,ID_Employee,ID_Team,saveDetailArray!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (saveCount == 0){
                                    saveCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        //val jobjt = jObject.getJSONObject("UpdateMaterialUsage")
                                        successBottomSheet(jObject)
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MaterialUsageActivity,
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

    private fun successBottomSheet(jObject : JSONObject) {
        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.succes_bottomsheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCancelable(false)
            dialog1!!.setCanceledOnTouchOutside(false)
            dialog1!!.getBehavior().setDraggable(false);

            var tv_succesmsg = view.findViewById<TextView>(R.id.tv_succesmsg)
            var tv_gotit = view.findViewById<TextView>(R.id.tv_gotit)

            tv_succesmsg!!.setText(jObject.getString("EXMessage"))
           // tv_succesmsg!!.setText("Success")
            tv_gotit!!.setOnClickListener {
                dialog1.dismiss()
                finish()
            }


            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }
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
                editable === tie_UsageDate!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_UsageDate!!.text.toString().equals("")){
                        til_UsageDate!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_UsageDate!!.isErrorEnabled = false
                        til_UsageDate!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }
                editable === tie_Stage!!.editableText -> {
                    if (tie_Stage!!.text.toString().equals("")){
                        til_Stage!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Stage!!.isErrorEnabled = false
                        til_Stage!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }
                }
                editable === tie_Team!!.editableText -> {
                    if (tie_Team!!.text.toString().equals("")){
                        til_Team!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Team!!.isErrorEnabled = false
                        til_Team!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }


                }

                editable === tie_Product!!.editableText -> {
                    if (tie_Product!!.text.toString().equals("")){
                        til_Product!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Product!!.isErrorEnabled = false
                        til_Product!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }


                }

                editable === tie_Quantity!!.editableText -> {
                    if (tie_Quantity!!.text.toString().equals("")){
                        til_Quantity!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Quantity!!.isErrorEnabled = false
                        til_Quantity!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }


                editable === tie_Mode!!.editableText -> {
                    if (tie_Mode!!.text.toString().equals("")){
                        til_Mode!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Mode!!.isErrorEnabled = false
                        til_Mode!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }

            }

        }
    }


    override fun onClick(position: Int, data: String) {

        if (data.equals("projectClick")){

            dialogProject!!.dismiss()
            val jsonObject = projectSort.getJSONObject(position)

            ID_Project = jsonObject.getString("ID_FIELD")
            tie_Project!!.setText(jsonObject.getString("ProjName"))

            ID_Stage = ""
            tie_Stage!!.setText("")
            ID_Team = ""
            tie_Team!!.setText("")
            ID_Employee = "0"
            tie_Employee!!.setText("")

            ID_Product = ""
            ID_Stock = ""
            ID_Mode = ""

            tie_Product!!.setText("")
            tie_Quantity!!.setText("")
            tie_Mode!!.setText("")

            ll_usage_product!!.visibility = View.GONE
            modelUsageProduct.clear()

        }
        if (data.equals("stageCliik")){

            dialogStage!!.dismiss()
            val jsonObject = stageSort.getJSONObject(position)
            ID_Stage = jsonObject.getString("ProjectStagesID")
            tie_Stage!!.setText(jsonObject.getString("StageName"))

            ID_Team = ""
            tie_Team!!.setText("")
            ID_Employee = "0"
            tie_Employee!!.setText("")

            ID_Product = ""
            ID_Stock = ""
            ID_Mode = ""

            tie_Product!!.setText("")
            tie_Quantity!!.setText("")
            tie_Mode!!.setText("")

            ll_usage_product!!.visibility = View.GONE
            modelUsageProduct.clear()

        }

        if (data.equals("teamClick")){

            dialogTeam!!.dismiss()
            val jsonObject = teamSort.getJSONObject(position)
            ID_Team = jsonObject.getString("ID_ProjectTeam")
            tie_Team!!.setText(jsonObject.getString("TeamName"))

            ID_Employee = "0"
            tie_Employee!!.setText("")

            ID_Product = ""
            ID_Stock = ""
            ID_Mode = ""

            tie_Product!!.setText("")
            tie_Quantity!!.setText("")
            tie_Mode!!.setText("")

            ll_usage_product!!.visibility = View.GONE
            modelUsageProduct.clear()

        }

        if (data.equals("teamEmpClick")){
            dialogTeamEmp!!.dismiss()
            val jsonObject = teamEmpSort.getJSONObject(position)

            ID_Employee = jsonObject.getString("ID_FIELD")
            tie_Employee!!.setText(jsonObject.getString("Name"))

        }

        if (data.equals("productClick")){
            dialogProduct!!.dismiss()
            val jsonObject = productSort.getJSONObject(position)

            ID_Product = jsonObject.getString("ProductID")
            ID_Stock = jsonObject.getString("StockId")
            tie_Product!!.setText(jsonObject.getString("Name"))
            productTotQty = jsonObject.getString("AvailableStock")

        }

        if (data.equals("modeClick")){
            dialogMode!!.dismiss()
            val jsonObject = modeSort.getJSONObject(position)

            ID_Mode = jsonObject.getString("ID_Mode")
            tie_Mode!!.setText(jsonObject.getString("ModeName"))

        }

        if (data.equals("deleteStocks")){

            if (editMode == 1){
                ID_Product = "="
                ID_Stock = ""
                tie_Product!!.setText("")
                tie_Quantity!!.setText("")
                productTotQty = ""
                ID_Mode = ""
                tie_Mode!!.setText("")
            }

            editMode = 0
            Log.e(TAG,"14644  "+position)
            modelUsageProduct!!.removeAt(position)
            materialUsageAdapter!!.notifyItemRemoved(position)
            if (modelUsageProduct.size == 0){
                ll_usage_product!!.visibility = View.GONE
            }

        }

        if (data.equals("editStocks")){

            editMode = 1
            editIndex = position

            ID_Product = modelUsageProduct[position].ID_Product
            ID_Stock = modelUsageProduct[position].StockId
            tie_Product!!.setText(modelUsageProduct[position].Product)
            ID_Mode = modelUsageProduct[position].ID_Mode
            tie_Mode!!.setText(modelUsageProduct[position].Mode)
            tie_Quantity!!.setText(modelUsageProduct[position].Quantity)

        }

    }

}
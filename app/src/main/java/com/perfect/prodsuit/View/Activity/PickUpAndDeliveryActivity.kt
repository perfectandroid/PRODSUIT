package com.perfect.prodsuit.View.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PickUpAndDeliveryActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {


    var TAG  ="PickUpAndDeliveryActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    private var ll_pickup          : LinearLayout? = null
    private var ll_delivery        : LinearLayout? = null
    private var imgv_filter        : ImageView? = null
    private var tie_Fromdate       : TextInputEditText? = null
    private var tie_Todate         : TextInputEditText? = null
    private var tie_Selectarea     : TextInputEditText? = null
    private var tie_Selectemployee : TextInputEditText? = null
    private var tie_Entercustomer  : TextInputEditText? = null
    private var tie_Customemobile  : TextInputEditText? = null
    private var tie_Selectproduct  : TextInputEditText? = null
    lateinit var tie_Selectstatus   : AutoCompleteTextView
    private var tie_Ticketumber     : TextInputEditText? = null
    private var dialogareaList          : Dialog? = null
    private var dialogEmployeeAll       : Dialog? = null
    private var dialogProdDet           : Dialog? = null
    private var recyareaList            : RecyclerView? = null
    private var recyProdDetail          : RecyclerView? = null
    private var recyEmployeeAll         : RecyclerView? = null
    private var datatype                : Int= 0
    lateinit var areaArrayList          : JSONArray
    lateinit var prodDetailArrayList    : JSONArray
    lateinit var employeeAllArrayList   : JSONArray
    lateinit var areaListSort           : JSONArray
    lateinit var prodDetailSort         : JSONArray
    lateinit var employeeAllSort        : JSONArray
//    lateinit var arealistviewmodel    : AreaListViewModel
    lateinit var areaviewmodel          : AreaViewModel
    lateinit var empByBranchViewModel   : EmpByBranchViewModel
    lateinit var productDetailViewModel : ProductDetailViewModel
    lateinit var pickupdeliverycounts   : PickupDeliveryCountsViewModel
    private var pick_up_count      : TextView? = null
    private var delivery_count     : TextView? = null
    private var status_check       : String?   = null
    private var status_id          = "1"
    private var ID_Employee        = ""
    private var emp_name2          = ""
    private var area_name          = ""
    private var product            = ""
    var status                     = ""
    var areaList                   = 0
    var employeeCount              = 0
    var proddetail                 = 0
    var FK_District                = "0"
    var ID_Category                = "0"
    var getCounts                  = 0
    var statusCount                = 0


    private var temp_ID_Branch: String = "0"
    private var temp_Product: String = ""
    private var temp_ID_Employee: String = "0"
    private var temp_Employee: String = ""
    private var temp_FromDate: String = ""
    private var temp_ToDate: String = ""
    private var temp_FK_Area: String = "0"
    private var temp_Area: String = ""
    private var temp_Customer: String = ""
    private var temp_Mobile: String = ""
    private var temp_TicketNo: String = ""
    private var temp_Status: String = ""

    private var ID_Branch: String = "0"
//    private var ID_Employee: String = "0"
    private var FK_Area: String = "0"
    private var strFromDate: String = ""
    private var strToDate: String = ""
    private var strCustomer: String = ""
    private var strMobile: String = ""
    private var strTicketNo: String = ""
    private var strarea: String = ""
    private var strProduct: String = ""
    private var strstatus: String = ""
    private var stProduct: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pick_up_and_delivery)

        context = this@PickUpAndDeliveryActivity
        areaviewmodel = ViewModelProvider(this).get(AreaViewModel::class.java)
        empByBranchViewModel = ViewModelProvider(this).get(EmpByBranchViewModel::class.java)
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        pickupdeliverycounts = ViewModelProvider(this).get(PickupDeliveryCountsViewModel::class.java)
        setRegViews()
        getCounts = 0
        getCounts()
        loadLoginEmpDetails("0")
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        ll_pickup      = findViewById(R.id.ll_pickup)
        ll_delivery    = findViewById(R.id.ll_delivery)
        imgv_filter    = findViewById(R.id.imgv_filter)
        delivery_count = findViewById(R.id.delivery_count)
        pick_up_count  = findViewById(R.id.pick_up_count)

        ll_pickup!!.setOnClickListener(this)
        ll_delivery!!.setOnClickListener(this)
        imgv_filter!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.ll_pickup->{
                val i = Intent(this@PickUpAndDeliveryActivity, PickUpAndDeliveryListActivity::class.java)
                i.putExtra("SubMode","1")
                i.putExtra("ID_Employee",ID_Employee)
                i.putExtra("FK_Area",FK_Area)
                i.putExtra("strFromDate",strFromDate)
                i.putExtra("strToDate",strToDate)
                i.putExtra("strCustomer",strCustomer)
                i.putExtra("strMobile",strMobile)
                i.putExtra("strProduct",strProduct)
                i.putExtra("strTicketNo",strTicketNo)
                i.putExtra("status_id",status_id)
                startActivity(i)
            }
            R.id.ll_delivery->{
                val i = Intent(this@PickUpAndDeliveryActivity, PickUpAndDeliveryListActivity::class.java)
                i.putExtra("SubMode","2")
                i.putExtra("ID_Employee",ID_Employee)
                i.putExtra("FK_Area",FK_Area)
                i.putExtra("strFromDate",strFromDate)
                i.putExtra("strToDate",strToDate)
                i.putExtra("strCustomer",strCustomer)
                i.putExtra("strMobile",strMobile)
                i.putExtra("strProduct",strProduct)
                i.putExtra("strTicketNo",strTicketNo)
                i.putExtra("status_id",status_id)
                startActivity(i)
            }
            R.id.imgv_filter->{
                filterBottomData()
            }
        }
    }




    private fun getCounts(){
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()

                pickupdeliverycounts.getPickupDeliveryCounts(this,ID_Employee,FK_Area,strFromDate,strToDate,strarea,strCustomer,strMobile,stProduct,strTicketNo,status_id)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {

                            if (getCounts == 0) {
                                getCounts++
                                progressDialog!!.dismiss()

                                Log.e(TAG, "msg   0001122   " + strFromDate)
                                Log.e(TAG, "msg   167   " + FK_Area)
                                Log.e(TAG, "msg   5566248   " + strarea)
                                Log.e(TAG, "msg   185467   " + strProduct)


                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   167   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    val jobjt = jObject.getJSONObject("PickupandDeliveryCount")

                                    Log.e(TAG, "dddddd   185467   " )
                                    pick_up_count!!.setText(jobjt.getString("PickUp"))
                                    delivery_count!!.setText(jobjt.getString("Delivery"))

                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@PickUpAndDeliveryActivity,
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

    @SuppressLint("SuspiciousIndentation")
    private fun filterBottomData() {

        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.picup_and_delivery_filter, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(true)

//            val ll_admin_staff = layout1.findViewById(R.id.ll_admin_staff) as LinearLayout

            val txtCancel       = view.findViewById(R.id.txtReset) as TextView
            val txtSubmit       = view.findViewById(R.id.txtSearch) as TextView
            tie_Fromdate        = view.findViewById(R.id.tie_Fromdate)
            tie_Todate          = view.findViewById(R.id.tie_Todate)
            tie_Selectarea      = view.findViewById(R.id.tie_Selectarea)
            tie_Selectemployee  = view.findViewById(R.id.tie_Selectemployee)
            tie_Entercustomer   = view.findViewById(R.id.tie_Entercustomer)
            tie_Customemobile   = view.findViewById(R.id.tie_Customemobile)
            tie_Selectproduct   = view.findViewById(R.id.tie_Selectproduct)
            tie_Selectstatus    = view.findViewById(R.id.tie_Selectstatus)
            tie_Ticketumber     = view.findViewById(R.id.tie_Ticketumber)


//            val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
//            val BranchNameSP        = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
//            val FK_EmployeeSP       = context.getSharedPreferences(Config.SHARED_PREF1, 0)
//            val UserNameSP          = context.getSharedPreferences(Config.SHARED_PREF2, 0)




//                ID_Employee = temp_ID_Employee
                tie_Selectemployee!!.setText(temp_Employee)
                tie_Selectproduct!!.setText(temp_Product)
                tie_Fromdate!!.setText(temp_FromDate)
                tie_Todate!!.setText(temp_ToDate)
                tie_Entercustomer!!.setText(strCustomer)
                tie_Selectarea!!.setText(temp_FK_Area)
                tie_Customemobile!!.setText(strMobile)
                tie_Selectstatus!!.setText(status_check)
                tie_Ticketumber!!.setText(strTicketNo)




            val IsAdminSP = context.getSharedPreferences(Config.SHARED_PREF43, 0)
            var isAdmin   = IsAdminSP.getString("IsAdmin", null)
            Log.e(TAG,"isAdmin 796  "+isAdmin)


            tie_Fromdate!!.setOnClickListener(View.OnClickListener {
                datatype = 0
                openBottomSheet()
                Config.disableClick(it)
                Log.e(TAG," 796   fromdate"+datatype)
            })

            tie_Todate!!.setOnClickListener(View.OnClickListener {
                datatype = 1
                openBottomSheet()
                Config.disableClick(it)
                Log.e(TAG," 796   todate"+datatype)
            })

            tie_Selectarea!!.setOnClickListener(View.OnClickListener {
                areaList = 0
                getArea(view)
                Log.e(TAG," 796   area"+areaList)
            })

            tie_Selectemployee!!.setOnClickListener(View.OnClickListener {
                employeeCount = 0
                getEmpByBranch()
                Log.e(TAG," 796   employee"+employeeCount)
            })

            tie_Selectproduct!!.setOnClickListener(View.OnClickListener {
                proddetail = 0
                getProductDetail()
                Log.e(TAG," 796   product"+proddetail)
            })

            tie_Selectstatus!!.setOnClickListener(View.OnClickListener {
                statusCount = 0
                showMethod(tie_Selectstatus)
                Log.e(TAG,"785666666644  Exception   "+status_check)
            })

            txtCancel.setOnClickListener {
                loadLoginEmpDetails("1")
            }

            txtSubmit.setOnClickListener {

                validateData(dialog1)

            }


            dialog1!!.setContentView(view)
            dialog1.show()

            dialog1.show()
        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }

    }


    private fun showMethod(tie_Selectstatus: AutoCompleteTextView?) {

        val searchType = arrayOf<String>(
            "Select Status",
            "Pending",
            "Complete",
        )

        if (statusCount == 0) {
            statusCount++

            val adapter = ArrayAdapter(this, R.layout.filter_status_spinner, searchType)
            tie_Selectstatus!!.setAdapter(adapter)
            //   tie_Selectstatus!!.setText(searchType.get(0), false)
//            tie_Selectstatus!!.setOnClickListener {
                statusCount = 0
                tie_Selectstatus!!.showDropDown()
                Log.e(TAG, "7778889999   " + statusCount)
//            }
            tie_Selectstatus!!.setOnItemClickListener { parent, view, position, id ->
                status_check = searchType[position]

                if (status_check.equals("Pending")) {
                    status_id = "1"
//                showMethod(tie_Selectstatus)
                }
                if (status_check.equals("Complete"))
                    status_id = "2"
//                showMethod(tie_Selectstatus)
                Log.e(TAG, "5555444   " + status_id)
            }
        }
    }


    private fun validateData(dialog1: BottomSheetDialog) {


        strFromDate = tie_Fromdate!!.text.toString()
        strToDate   = tie_Todate!!.text.toString()
        strCustomer = tie_Entercustomer!!.text.toString()
        strMobile   = tie_Customemobile!!.text.toString()
        strTicketNo = tie_Ticketumber!!.text.toString()
        strProduct  = tie_Selectproduct!!.text.toString()
        status_check = tie_Selectstatus!!.text.toString()

//        strarea  = tie_Selectarea!!.text.toString()

        Log.e(TAG,"tie_Ticketumber   5241663   "+strTicketNo)

        if (FK_Area.equals("")){
            FK_Area = "0"
        }


        if (ID_Branch.equals("")){
            ID_Branch = "0"
        }
        if (ID_Employee.equals("")){
            ID_Employee = "0"
        }

        if (!strFromDate.equals("") || !strToDate.equals("")){

            if (strFromDate.equals("")){
                tie_Fromdate!!.setError("Select From Date");
//                tie_Fromdate!!.setErrorIconDrawable(null)
            }
            else if (strToDate.equals("")){
                tie_Todate!!.setError("Select To Date");
//                til_ToDate!!.setErrorIconDrawable(null)
            }else{
                val sdfDate = SimpleDateFormat("dd-MM-yyyy")
                val strFromDate1 = sdfDate.parse(strFromDate)
                val strToDate1 = sdfDate.parse(strToDate)

                if(strFromDate1.equals(strToDate1) || strFromDate1.before(strToDate1)){
                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    if (strFromDate!!.equals("")){
//                    strDate = "0000-00-00 00:00:00"
                        strFromDate = ""
                    }else{
                        var date: Date? = null
                        date = inputFormat.parse(strFromDate)
                        strFromDate = outputFormat.format(date)
                        Log.e(TAG,"DATE   1302   "+strFromDate)
                    }

                    if (strToDate!!.equals("")){
//                    strDate = "0000-00-00 00:00:00"
                        strToDate = ""
                    }else{
                        var date: Date? = null
                        date = inputFormat.parse(strToDate)
                        strToDate = outputFormat.format(date)
                        Log.e(TAG,"DATE   1302   "+strToDate)
                    }

                    dialog1.dismiss()
                    getCounts = 0
                    Log.e(TAG,"111165555  ")
                    getCounts()
                }
                else{
//                    til_ToDate!!.setError("To Date should be greater than or equal to From date");
//                    til_ToDate!!.setErrorIconDrawable(null)
                }

            }
        }
        else{
            dialog1.dismiss()
//            serviceCount = 0
            getCounts = 0
            getCounts()
        }


    }

    private fun loadLoginEmpDetails(mode : String) {


        ID_Employee ="0"
        ID_Branch = "0"
        status_id = "1"


        temp_ID_Branch = "0"
        temp_Product = ""
        temp_Employee =""

        if (mode.equals("1")){

            tie_Todate!!.setText("")
            tie_Fromdate!!.setText("")
            tie_Customemobile!!.setText("")
            tie_Selectarea!!.setText("")
            tie_Entercustomer!!.setText("")
            tie_Selectproduct!!.setText("")
            tie_Selectemployee!!.setText("")
            tie_Selectstatus!!.setText("")
            tie_Ticketumber!!.setText("")
        }

        FK_Area = "0"
        temp_FromDate = ""
        temp_ToDate = ""
        temp_Area = ""
        temp_FK_Area = ""
        temp_Customer = ""
        temp_Mobile = ""
        temp_TicketNo = ""
        temp_Status = ""
    }

    private fun openBottomSheet(){

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)


        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker = view.findViewById<DatePicker>(R.id.date_Picker1)


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()


            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker!!.getDayOfMonth()
                val mon: Int = date_Picker!!.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }


                if (datatype == 0){
                    tie_Fromdate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    temp_FromDate = ""+strDay+"-"+strMonth+"-"+strYear
                }
                if (datatype == 1){
                    tie_Todate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    temp_ToDate = ""+strDay+"-"+strMonth+"-"+strYear
                }


            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(true)
        dialog!!.setContentView(view)

        dialog.show()

    }

    private fun getArea(v: View) {
//        var areaDet = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                areaviewmodel.getArea(this, FK_District)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (areaList == 0) {
                                    areaList++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   2353   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("AreaDetails")
                                        areaArrayList = jobjt.getJSONArray("AreaDetailsList")

                                        if (areaArrayList.length() > 0) {
//                                            if (postDet == 0){
//                                                postDet++
                                            areaDetailPopup(areaArrayList)
//                                            }

                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@PickUpAndDeliveryActivity,
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

            dialogareaList = Dialog(this)
            dialogareaList!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogareaList!!.setContentView(R.layout.area_list_popup)
            dialogareaList!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            val recycArea = dialogareaList!!.findViewById(R.id.recycArea) as RecyclerView
            val etsearch = dialogareaList!!.findViewById(R.id.etsearch) as EditText

            areaListSort = JSONArray()
            for (k in 0 until areaArrayList.length()) {
                val jsonObject = areaArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                areaListSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@PickUpAndDeliveryActivity, 1)
            recycArea!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = PostDetailAdapter(this@LeadGenerationActivity, areaArrayList)
            val adapter = AreaDetailAdapter(this@PickUpAndDeliveryActivity, areaListSort)
            recycArea!!.adapter = adapter
            adapter.setClickListener(this@PickUpAndDeliveryActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    areaListSort = JSONArray()

                    for (k in 0 until areaArrayList.length()) {
                        val jsonObject = areaArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("Area").length) {
                            if (jsonObject.getString("Area")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                areaListSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "areaSort               7103    " + areaListSort)
                    val adapter = AreaDetailAdapter(this@PickUpAndDeliveryActivity, areaListSort)
                    recycArea!!.adapter = adapter
                    adapter.setClickListener(this@PickUpAndDeliveryActivity)
                }
            })

            dialogareaList!!.show()
            dialogareaList!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialogareaList!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getEmpByBranch() {
//         var branch = 0
        Log.v("sfsdfsdfdf","branch"+ID_Branch)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                empByBranchViewModel.getEmpByBranch(this, ID_Branch)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (employeeCount == 0){
                                    employeeCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeAllArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeAllArrayList.length()>0){

                                            employeeAllPopup(employeeAllArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@PickUpAndDeliveryActivity,
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

    private fun employeeAllPopup(employeeAllArrayList: JSONArray) {
        try {

            dialogEmployeeAll = Dialog(this)
            dialogEmployeeAll!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogEmployeeAll!! .setContentView(R.layout.employeeall_popup)
            dialogEmployeeAll!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyEmployeeAll = dialogEmployeeAll!! .findViewById(R.id.recyEmployeeAll) as RecyclerView
            val etsearch = dialogEmployeeAll!! .findViewById(R.id.etsearch) as EditText


            employeeAllSort = JSONArray()
            for (k in 0 until employeeAllArrayList.length()) {
                val jsonObject = employeeAllArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                employeeAllSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@PickUpAndDeliveryActivity, 1)
            recyEmployeeAll!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//            val adapter = EmployeeAllAdapter(this@FollowUpActivity, employeeAllArrayList)
            val adapter = EmployeeAllAdapter(this@PickUpAndDeliveryActivity, employeeAllSort)
            recyEmployeeAll!!.adapter = adapter
            adapter.setClickListener(this@PickUpAndDeliveryActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    employeeAllSort = JSONArray()

                    for (k in 0 until employeeAllArrayList.length()) {
                        val jsonObject = employeeAllArrayList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                employeeAllSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"employeeAllSort               7103    "+employeeAllSort)
                    val adapter = EmployeeAllAdapter(this@PickUpAndDeliveryActivity, employeeAllSort)
                    recyEmployeeAll!!.adapter = adapter
                    adapter.setClickListener(this@PickUpAndDeliveryActivity)
                }
            })

            dialogEmployeeAll!!.show()
            dialogEmployeeAll!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProductDetail() {
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
                                        if (prodDetailArrayList.length() > 0) {
//                                             if (proddetail == 0){
//                                                 proddetail++
                                            productDetailPopup(prodDetailArrayList)
//                                             }

                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@PickUpAndDeliveryActivity,
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

            val lLayout = GridLayoutManager(this@PickUpAndDeliveryActivity, 1)
            recyProdDetail!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = ProductDetailAdapter(this@LeadGenerationActivity, prodDetailArrayList)
            val adapter = ProductDetailAdapter(this@PickUpAndDeliveryActivity, prodDetailSort)
            recyProdDetail!!.adapter = adapter
            adapter.setClickListener(this@PickUpAndDeliveryActivity)

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
                        if (textlength <= jsonObject.getString("ProductName").length) {
                            if (jsonObject.getString("ProductName")!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                prodDetailSort.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG, "prodDetailSort               7103" + prodDetailSort)
                    val adapter = ProductDetailAdapter(this@PickUpAndDeliveryActivity, prodDetailSort)
                    recyProdDetail!!.adapter = adapter
                    adapter.setClickListener(this@PickUpAndDeliveryActivity)
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

    override fun onClick(position: Int, data: String) {
        if (data.equals("areadetail")){
            dialogareaList!!.dismiss()
            val jsonObject = areaListSort.getJSONObject(position)
            tie_Selectarea!!.setText(jsonObject.getString("Area"))
            FK_Area = jsonObject.getString("FK_Area")

            temp_FK_Area =  jsonObject.getString("Area")
            dialogareaList!!.dismiss()
        }

        if (data.equals("employeeAll")){
            dialogEmployeeAll!!.dismiss()
            val jsonObject = employeeAllSort.getJSONObject(position)
            tie_Selectemployee!!.setText(jsonObject.getString("EmpName"))
            temp_Employee = jsonObject.getString("EmpName")
            ID_Employee = jsonObject.getString("ID_Employee")
//            Log.e(TAG,"iddddd "+jsonObject.getString("ID_Employee"))
        }

        if (data.equals("proddetails")){
            dialogProdDet!!.dismiss()
            val jsonObject = prodDetailSort.getJSONObject(position)
            tie_Selectproduct!!.setText(jsonObject.getString("ProductName"))
            temp_Product = jsonObject.getString("ProductName")
            stProduct = jsonObject.getString("ID_Product")
            Log.e(TAG,"strProoduct   185467   "+strProduct)
//            Log.e(TAG,"iddddd "+jsonObject.getString("ID_Employee"))
        }


    }

//    private fun lofinTempDetails(mode: String){
//        ID_Employee ="0"
//        ID_Branch = "0"
//
//
//        temp_ID_Branch = "0"
//        temp_Branch = ""
//        temp_ID_Employee = "0"
//        temp_Employee =""
//
//        if (mode.equals("1")){
//
//            tie_Fromdate!!.setText("")
//            tie_Todate!!.setText("")
//            tie_Selectarea!!.setText("")
//            tie_Selectemployee!!.setText("")
//            tie_Entercustomer!!.setText("")
//            tie_Customemobile!!.setText("")
//            tie_Selectproduct!!.setText("")
//            tie_Selectstatus!!.setText("")
//            te_Ticketumber!!.setText("")
//        }
//
//        FK_Area = "0"
//
//        temp_FromDate = ""
//        temp_ToDate = ""
//        temp_Area = ""
//        temp_FK_Area = "0"
//        temp_Customer = ""
//        temp_Mobile = ""
//        temp_TicketNo = ""
//        temp_DueDays = ""
//    }

}
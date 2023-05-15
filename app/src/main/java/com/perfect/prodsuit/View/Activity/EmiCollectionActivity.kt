package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.EmployeeAdapter
import com.perfect.prodsuit.View.Adapter.PayMethodAdapter
import com.perfect.prodsuit.View.Adapter.PaymentListAdapter
import com.perfect.prodsuit.Viewmodel.EMIAccountDetailsViewModel
import com.perfect.prodsuit.Viewmodel.EmployeeViewModel
import com.perfect.prodsuit.Viewmodel.PaymentMethodViewModel
import com.perfect.prodsuit.Viewmodel.UpdateEMICollectionViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class EmiCollectionActivity : AppCompatActivity(), View.OnClickListener , ItemClickListener {

    val TAG: String = "EmiCollectionActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null



    private var tv_Customer: TextView? = null
    private var tv_Mobile: TextView? = null
    private var tv_Addess: TextView? = null


    private var tv_Info_AccountNo: TextView? = null
    private var tv_Info_Product: TextView? = null
    private var tv_Info_FinancePlan: TextView? = null
    private var tv_Info_DueAmount: TextView? = null
    private var tv_Info_FineAmount: TextView? = null
    private var tv_Info_Balance: TextView? = null
    private var tv_Info_DueDate: TextView? = null
    private var tv_Info_NextEMIDate: TextView? = null
    private var tv_Info_InstNo: TextView? = null

    private var tv_Total: TextView? = null
    private var tv_NetAmount: TextView? = null
    private var tv_Balance: TextView? = null


    private var txt_pay_method: TextView? = null
    private var txt_pay_Amount: TextView? = null
    private var txt_bal_Amount: TextView? = null

    private var img_call: ImageView? = null
    private var img_location: ImageView? = null
    private var img_payment: ImageView? = null
    private var tie_Payment_Method: TextInputEditText? = null

    private var edtTransDate: EditText? = null
    private var edtCollectionDate: EditText? = null
    private var edtCollectedBy: EditText? = null

    private var edtInsAmount: EditText? = null
    private var edtFine: EditText? = null
    private var chkClosing: CheckBox? = null

//    private var edtTotalAmount: EditText? = null
//    private var edtTotalFineAmount: EditText? = null
//    private var edtNetAmount: EditText? = null


    private var btnReset: Button? = null
    private var btnSubmit: Button? = null

    private var edtPayMethod: EditText? = null
    private var edtPayRefNo: EditText? = null
    private var edtPayAmount: EditText? = null

    private var txtPayBalAmount: TextView? = null
    private var img_PayAdd: ImageView? = null
    private var img_PayRefresh: ImageView? = null
    private var btnApply: Button? = null

    var arrAddUpdate: String? = "0"
    var strBalance: String? = "0.00"
    var strBalanceTemp: String? = "0.00"
    var strInsAmount: String? = "0.00"
    var strInsAmountTemp: String? = "0.00"
    var strFine: String? = "0.00"
    var arrPosition: Int? = 0
    var applyMode = 0

    var ID_CollectedBy: String? = ""
    var ID_PaymentMethod: String? = ""

    private var dialogPaymentSheet : Dialog? = null

    var arrPayment = JSONArray()
    internal var recyPaymentList: RecyclerView? = null
    internal var ll_paymentlist: LinearLayout? = null
    var adapterPaymentList : PaymentListAdapter? = null

    private var tabLayout : TabLayout? = null
    private var ll_information : LinearLayout? = null
    private var ll_collection : RelativeLayout? = null

    var dateMode = 0 // 0 = TransDAte , 1 = Collection Date

    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    lateinit var employeeSort: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null
    var empMediaDet = 0

    var paymentCount = 0
    lateinit var paymentMethodeViewModel: PaymentMethodViewModel
    lateinit var paymentMethodeArrayList: JSONArray
    private var dialogPaymentMethod: Dialog? = null
    var recyPaymentMethod: RecyclerView? = null

    private var ID_CustomerWiseEMI:String?=""
    private var strCurrentDate:String?=""
    private var jsonObject: JSONObject? = null

    lateinit var emiAccountDetailsViewModel: EMIAccountDetailsViewModel
    lateinit var customerInformationArrayList: JSONArray
    lateinit var emiAccountDetailsArrayList: JSONArray
    var emiCollectnDet = 0


    var informationMode: String? = "1"
    var collectionMode: String? = "0"
    private var tv_tab_information: TextView? = null
    private var tv_tab_collection: TextView? = null


    var strSaveTrnsDate  = ""
    var strID_CustomerWiseEMI = "0.00"
    var strSaveCollectDate = ""
    var strSaveInstllAmount = "0.00"
    var strSaveTotalAmount = "0.00"
    var strSaveFineAmount = "0.00"
    var strSaveNetAmount = "0.00"
    var strSaveBalance = "0"
    var strSaveClosing = "0"

    var saveEmiDetailsArray = JSONArray()
    var savePaymentDetailArray = JSONArray()
    var totPayAmount = 0.0f

    lateinit var updateEMICollectionViewModel: UpdateEMICollectionViewModel
    var saveEmiCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_emi_collection)
        context = this@EmiCollectionActivity

        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        paymentMethodeViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)
        emiAccountDetailsViewModel = ViewModelProvider(this).get(EMIAccountDetailsViewModel::class.java)
        updateEMICollectionViewModel = ViewModelProvider(this).get(UpdateEMICollectionViewModel::class.java)

        if (getIntent().hasExtra("ID_CustomerWiseEMI")) {
            ID_CustomerWiseEMI = intent.getStringExtra("ID_CustomerWiseEMI")
        }
        if (getIntent().hasExtra("jsonObject")) {
            jsonObject = JSONObject(getIntent().getStringExtra("jsonObject"));
        }
        Log.e(TAG,"ID_CustomerWiseEMI  147  "+ID_CustomerWiseEMI)
        Log.e(TAG,"ID_CustomerWiseEMI  147  "+jsonObject)

        setRegViews()
        getCurrentDate()
        hideViews()
        emiCollectnDet = 0
        getEmiCollectionDetails()


    }




    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_Customer = findViewById<TextView>(R.id.tv_Customer)
        tv_Mobile = findViewById<TextView>(R.id.tv_Mobile)
        tv_Addess = findViewById<TextView>(R.id.tv_Addess)

        tv_Info_AccountNo = findViewById<TextView>(R.id.tv_Info_AccountNo)
        tv_Info_Product = findViewById<TextView>(R.id.tv_Info_Product)
        tv_Info_FinancePlan = findViewById<TextView>(R.id.tv_Info_FinancePlan)
        tv_Info_DueAmount = findViewById<TextView>(R.id.tv_Info_DueAmount)
        tv_Info_FineAmount = findViewById<TextView>(R.id.tv_Info_FineAmount)
        tv_Info_Balance = findViewById<TextView>(R.id.tv_Info_Balance)
        tv_Info_DueDate = findViewById<TextView>(R.id.tv_Info_DueDate)
        tv_Info_NextEMIDate = findViewById<TextView>(R.id.tv_Info_NextEMIDate)
        tv_Info_InstNo = findViewById<TextView>(R.id.tv_Info_InstNo)

        tv_Total = findViewById<TextView>(R.id.tv_Total)
        tv_NetAmount = findViewById<TextView>(R.id.tv_NetAmount)
        tv_Balance = findViewById<TextView>(R.id.tv_Balance)

        tv_tab_information = findViewById<TextView>(R.id.tv_tab_information)
        tv_tab_collection = findViewById<TextView>(R.id.tv_tab_collection)

        tie_Payment_Method = findViewById<TextInputEditText>(R.id.tie_Payment_Method)
        img_payment = findViewById<ImageView>(R.id.img_payment)
        img_call = findViewById<ImageView>(R.id.img_call)
        img_location = findViewById<ImageView>(R.id.img_location)

        edtInsAmount = findViewById<EditText>(R.id.edtInsAmount)
        edtFine = findViewById<EditText>(R.id.edtFine)

        chkClosing = findViewById<CheckBox>(R.id.chkClosing)

        edtTransDate = findViewById<EditText>(R.id.edtTransDate)
        edtCollectionDate = findViewById<EditText>(R.id.edtCollectionDate)
        edtCollectedBy = findViewById<EditText>(R.id.edtCollectedBy)


//        edtTotalAmount = findViewById<EditText>(R.id.edtTotalAmount)
//        edtTotalFineAmount = findViewById<EditText>(R.id.edtTotalFineAmount)
//        edtNetAmount = findViewById<EditText>(R.id.edtNetAmount)

        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        ll_information = findViewById<LinearLayout>(R.id.ll_information)
        ll_collection = findViewById<RelativeLayout>(R.id.ll_collection)



        btnReset = findViewById<Button>(R.id.btnReset)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)

        tv_tab_information!!.setOnClickListener(this)
        tv_tab_collection!!.setOnClickListener(this)

        edtTransDate!!.setOnClickListener(this)
        edtCollectionDate!!.setOnClickListener(this)
        edtCollectedBy!!.setOnClickListener(this)
        img_payment!!.setOnClickListener(this)
        tie_Payment_Method!!.setOnClickListener(this)
        img_call!!.setOnClickListener(this)
        img_location!!.setOnClickListener(this)

        chkClosing!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)

        changeListner()
        setValues()
     //   addTabItem()
        getDefaultValueSettings()

    }

    private fun getCurrentDate() {

        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("yyyy-MM-dd")
            strCurrentDate = sdfDate1.format(newDate)

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun hideViews() {
        ll_information!!.visibility = View.GONE
        ll_collection!!.visibility = View.GONE


        if (informationMode.equals("1")){
            ll_information!!.visibility = View.VISIBLE
            tv_tab_information!!.setBackgroundResource(R.drawable.shape_rectangle_bg)
            tv_tab_collection!!.setBackgroundResource(R.drawable.shape_rectangle_trans)
            tv_tab_information!!.setTextColor(Color.parseColor("#FFFFFF"))
            tv_tab_collection!!.setTextColor(Color.parseColor("#000000"))
        }
        if (collectionMode.equals("1")){
            ll_collection!!.visibility = View.VISIBLE
            tv_tab_information!!.setBackgroundResource(R.drawable.shape_rectangle_trans)
            tv_tab_collection!!.setBackgroundResource(R.drawable.shape_rectangle_bg)
            tv_tab_information!!.setTextColor(Color.parseColor("#000000"))
            tv_tab_collection!!.setTextColor(Color.parseColor("#FFFFFF"))
        }

        tv_tab_information!!.setPadding(20,10,20,10);
        tv_tab_collection!!.setPadding(20,10,20,10);
    }

    private fun setValues() {
//        tv_Customer!!.text = "Ranjith"
//        tv_Mobile!!.text = "8075283549"
//        tv_Addess!!.text = "Perfect Software Solutions (Clt)"

        tv_Info_AccountNo!!.text = jsonObject!!.getString("EMINo")
        tv_Info_Product!!.text = jsonObject!!.getString("Product")

        tv_Info_FinancePlan!!.text = jsonObject!!.getString("FinancePlan")
        tv_Info_DueAmount!!.text = jsonObject!!.getString("DueAmount")
        tv_Info_FineAmount!!.text = jsonObject!!.getString("FineAmount")
        tv_Info_Balance!!.text = jsonObject!!.getString("Balance")
        tv_Info_DueDate!!.text = jsonObject!!.getString("DueDate")
        tv_Info_NextEMIDate!!.text = jsonObject!!.getString("NextEMIDate")
        tv_Info_InstNo!!.text = jsonObject!!.getString("InstNo")


//        tv_Balance!!.text = "27412.25"
//
//        edtInsAmount!!.setText("2712.25")
//        edtFine!!.setText("27.15")
    }

    private fun addTabItem() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Information"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Collection"))
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE

        ll_information!!.visibility = View.VISIBLE

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabSelected  113  "+tab.position)
                if (tab.position == 0){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    ll_information!!.visibility = View.VISIBLE
                    ll_collection!!.visibility = View.GONE


                }
                if (tab.position == 1){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    ll_information!!.visibility = View.GONE
                    ll_collection!!.visibility = View.VISIBLE

                }


            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabUnselected  162  "+tab.position)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabReselected  165  "+tab.position)
            }
        })


    }

    private fun getDefaultValueSettings() {

        val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
        val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
        Log.e(TAG, " UserName  143     " + UserNameSP.getString("UserName", null))

        ID_CollectedBy = FK_EmployeeSP.getString("FK_Employee", null)
        edtCollectedBy!!.setText(""+UserNameSP.getString("UserName", null))

        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {

            Log.e(TAG,"DATE TIME  196  "+currentDate)
            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)


            edtTransDate!!.setText(""+sdfDate1.format(newDate))
            edtCollectionDate!!.setText(""+sdfDate1.format(newDate))


        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun changeListner() {

       // edtInsAmount!!.addDecimalLimiter(2)
        DecimelFormatters.setDecimelPlace(edtInsAmount!!)
        DecimelFormatters.setDecimelPlace(edtFine!!)
        edtInsAmount!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edtInsAmount!!.text.toString().equals(".")){
                    strInsAmount = "0.00"
                }
                else if (edtInsAmount!!.text.toString().equals("")){
                    strInsAmount = "0.00"
                }
                else{
                    strInsAmount =  edtInsAmount!!.text.toString()
                }




                Log.e(TAG,"41900     "+strBalance+"   :   "+strInsAmount)
                if ((strInsAmount!!.toFloat()) <= (strBalance!!.toFloat())){
                    val bal = DecimelFormatters.set2DecimelPlace(((strBalance!!.toFloat()) - (strInsAmount!!.toFloat())))
//                    tv_Balance!!.text = ((strBalance!!.toFloat()) - (strInsAmount!!.toFloat())).toString()
                    tv_Balance!!.text = bal
                }
                else{
                    edtInsAmount!!.setText(strInsAmountTemp)
                   // snackBarWarnings()
//                    val snackbar = Snackbar.make(this@EmiCollectionActivity, "", Snackbar.LENGTH_SHORT)
//                    val sbView = snackbar.view
//                    sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
//                    val textView: TextView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
//                    textView.setTextColor(Color.WHITE)
//                    val typeface = ResourcesCompat.getFont(context, R.font.myfont)
//                    textView.setTypeface(typeface)
//                    textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15f)
//                    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
//                    snackbar.show()

                }



         //       Log.e(TAG,"tv_Total  "+((strInsAmount!!.toFloat())+(strFine!!.toFloat())).toString())
                val tot = DecimelFormatters.set2DecimelPlace((strInsAmount!!.toFloat())+(strFine!!.toFloat()))
                tv_Total!!.text = tot

                val totAmnt = DecimelFormatters.set2DecimelPlace(strInsAmount!!.toFloat())
              //  edtTotalAmount!!.setText(totAmnt)
                tv_NetAmount!!.setText(tot)
             //   edtNetAmount!!.setText(tot)

            }

        })

        edtFine!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (edtFine!!.text.toString().equals(".")){
                    strFine = "0.00"
                }
                else if (edtFine!!.text.toString().equals("")){
                    strFine = "0.00"
                }
                else{
                    strFine =  edtFine!!.text.toString()
                }
                Log.e(TAG,"tv_Total  "+((strInsAmount!!.toFloat())+(strFine!!.toFloat())).toString())
              //  tv_Total!!.text = ((strInsAmount!!.toFloat())+(strFine!!.toFloat())).toString()

                val tot = DecimelFormatters.set2DecimelPlace((strInsAmount!!.toFloat())+(strFine!!.toFloat()))
                tv_Total!!.text = tot

                val totFine = DecimelFormatters.set2DecimelPlace(strFine!!.toFloat())
             //   edtTotalFineAmount!!.setText(totFine)
              //  edtNetAmount!!.setText(tot)
                tv_NetAmount!!.setText(tot)
            }

        })
    }


    private fun getEmiCollectionDetails() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                emiAccountDetailsViewModel.getEmiAccountDetailst(this, ID_CustomerWiseEMI!!,strCurrentDate!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (emiCollectnDet == 0) {
                                    emiCollectnDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EMIAccountDetails")
                                        customerInformationArrayList = jobjt.getJSONArray("CustomerInformationList")
                                        if (customerInformationArrayList.length() > 0) {
                                            val jsonObject = customerInformationArrayList.getJSONObject(0)

                                            tv_Customer!!.text = jsonObject.getString("CusName")
                                            tv_Mobile!!.text = jsonObject.getString("Mobile")
                                            tv_Addess!!.text = jsonObject.getString("Address")
                                        }

                                        emiAccountDetailsArrayList = jobjt.getJSONArray("EMIAccountDetailsList")
                                        if (emiAccountDetailsArrayList.length() > 0) {
                                            val jsonObject1 = emiAccountDetailsArrayList.getJSONObject(0)

                                            strBalance = jsonObject1.getString("Balance")
                                            strBalanceTemp = jsonObject1.getString("Balance")
                                            strInsAmountTemp = jsonObject1.getString("Amount")
                                            edtInsAmount!!.setText(jsonObject1.getString("Amount"))
                                            edtFine!!.setText(jsonObject1.getString("Fine"))
                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiCollectionActivity,
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


    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.img_call->{
                callFunction(v)
            }
            R.id.img_location->{

                checkLocationPermission(v)
//                val i = Intent(this@EmiCollectionActivity, LocationCollectionActivity::class.java)
//                startActivity(i)
            }
            R.id.edtTransDate->{
                dateMode = 0
                openBottomDate()
            }
            R.id.edtCollectionDate->{
                dateMode = 1
                openBottomDate()
            }
            R.id.edtCollectedBy->{
                empMediaDet = 0
                getChannelEmp()
            }

            R.id.chkClosing->{
               if (chkClosing!!.isChecked){
                   strSaveClosing = "1"
                   edtInsAmount!!.setText(strBalanceTemp)
               }else{
                   edtInsAmount!!.setText(strInsAmountTemp)
                   strSaveClosing = "0"
               }
            }

            R.id.img_payment->{
                arrAddUpdate = "0"
                paymentMethodPopup()
            }

            R.id.tie_Payment_Method->{
                arrAddUpdate = "0"
                paymentMethodPopup()
            }

            R.id.btnSubmit->{

                saveValidation(v)
//                if (applyMode == 1){
//
//                }else{
//                    Config.snackBarWarning(context,v,"In payment method ,Apply method first")
//                }

            }

            R.id.tv_tab_information->{
                informationMode = "1"
                collectionMode  = "0"

                hideViews()
            }

            R.id.tv_tab_collection->{
                informationMode = "0"
                collectionMode  = "1"

                hideViews()

            }

        }
    }

    private fun checkLocationPermission(v: View) {
        val ALL_PERMISSIONS = 102

        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        if (ContextCompat.checkSelfPermission(
                this@EmiCollectionActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) + ContextCompat.checkSelfPermission(
                this@EmiCollectionActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {
            val i = Intent(this@EmiCollectionActivity, LocationCollectionActivity::class.java)
            startActivity(i)

        }
    }

    private fun callFunction(v: View) {
        val ALL_PERMISSIONS = 101

        val permissions = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE
        )
        if (ContextCompat.checkSelfPermission(
                this@EmiCollectionActivity,
                Manifest.permission.CALL_PHONE
            ) + ContextCompat.checkSelfPermission(
                this@EmiCollectionActivity,
                Manifest.permission.RECORD_AUDIO
            )
            + ContextCompat.checkSelfPermission(
                this@EmiCollectionActivity,
                Manifest.permission.READ_PHONE_STATE
            )
            + ContextCompat.checkSelfPermission(
                this@EmiCollectionActivity,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
        } else {

            var mobileno = tv_Mobile!!.text.toString()
            if (mobileno.equals("")){
                Config.snackBarWarning(context,v,"Invalid mobile number")
            }else{
                //intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + "8075283549"))
                intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91" + mobileno))
                startActivity(intent)
            }

        }
    }


    private fun openBottomDate() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

//        if (dateMode == 0){
//            //date_Picker1.maxDate = System.currentTimeMillis()
//        }else if (dateMode == 1){
//
//           // date_Picker1.minDate = System.currentTimeMillis()
//        }
//

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

                if (dateMode == 0){
                    edtTransDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }else if (dateMode == 1){

                    edtCollectionDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }



            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun paymentMethodPopup() {
        try {

            dialogPaymentSheet = Dialog(this)
            dialogPaymentSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPaymentSheet!! .setContentView(R.layout.emi_payment_bottom_sheet)
            dialogPaymentSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogPaymentSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
          //  dialogPaymentSheet!!.setCancelable(false)

            edtPayMethod = dialogPaymentSheet!! .findViewById(R.id.edtPayMethod) as EditText
            edtPayRefNo = dialogPaymentSheet!! .findViewById(R.id.edtPayRefNo) as EditText
            edtPayAmount = dialogPaymentSheet!! .findViewById(R.id.edtPayAmount) as EditText

            txtPayBalAmount = dialogPaymentSheet!! .findViewById(R.id.txtPayBalAmount) as TextView

            txt_pay_method = dialogPaymentSheet!! .findViewById(R.id.txt_pay_method) as TextView
            txt_pay_Amount = dialogPaymentSheet!! .findViewById(R.id.txt_pay_Amount) as TextView
            txt_bal_Amount = dialogPaymentSheet!! .findViewById(R.id.txt_bal_Amount) as TextView

            edtPayMethod!!.addTextChangedListener(watcher)
            edtPayAmount!!.addTextChangedListener(watcher)
            txtPayBalAmount!!.addTextChangedListener(watcher)

//            edtPayMethod = dialogPaymentSheet!! .findViewById(R.id.edtPayMethod) as EditText
//            edtPayRefNo = dialogPaymentSheet!! .findViewById(R.id.edtPayRefNo) as EditText
//            edtPayAmount = dialogPaymentSheet!! .findViewById(R.id.edtPayAmount) as EditText


            DecimelFormatters.setDecimelPlace(edtPayAmount!!)



//            txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
//            txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))


            img_PayAdd = dialogPaymentSheet!! .findViewById(R.id.img_PayAdd) as ImageView
            img_PayRefresh = dialogPaymentSheet!! .findViewById(R.id.img_PayRefresh) as ImageView
            btnApply = dialogPaymentSheet!! .findViewById(R.id.btnApply) as Button

            ll_paymentlist = dialogPaymentSheet!! .findViewById(R.id.ll_paymentlist) as LinearLayout
            recyPaymentList = dialogPaymentSheet!! .findViewById(R.id.recyPaymentList) as RecyclerView



           // txtPayBalAmount!!.setText(""+tv_NetAmount!!.text.toString())

            if (arrPayment.length() > 0){
                ll_paymentlist!!.visibility = View.VISIBLE
                viewList(arrPayment)
                var payAmnt = 0.00
                for (i in 0 until arrPayment.length()) {
                    //apply your logic
                    val jsonObject = arrPayment.getJSONObject(i)
                    payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                }
               val pay = DecimelFormatters.set2DecimelPlace(payAmnt.toFloat())
              //  payAmnt = (DecimelFormatters.set2DecimelPlace(payAmnt.toFloat()))
                Log.e(TAG,"payAmnt         475    "+payAmnt)
                Log.e(TAG,"tv_NetAmount    475    "+tv_NetAmount!!.text.toString())
                txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - pay.toFloat()))
            }else{
                ll_paymentlist!!.visibility = View.GONE
                recyPaymentList!!.adapter = null
                txtPayBalAmount!!.setText(""+tv_NetAmount!!.text.toString())
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
                edtPayMethod!!.setText("")
                edtPayRefNo!!.setText("")
                edtPayAmount!!.setText("")

                if (arrPayment.length() > 0){
                    var payAmnt = 0.00
                    for (i in 0 until arrPayment.length()) {
                        //apply your logic
                        val jsonObject = arrPayment.getJSONObject(i)
                        payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                    }
                    txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - payAmnt.toFloat()))
                }else{
                    txtPayBalAmount!!.setText(""+tv_NetAmount!!.text.toString())
                }
            }

            btnApply!!.setOnClickListener {
               val payAmnt = DecimelFormatters.set2DecimelPlace(txtPayBalAmount!!.text.toString().toFloat())
                if ((payAmnt.toFloat()).equals("0.00".toFloat())){
                    Log.e(TAG,"801 payAmnt  0.00  "+payAmnt.toFloat())
                    applyMode = 1
                    dialogPaymentSheet!!.dismiss()
                }
                else{
                    Log.e(TAG,"801 payAmnt  0.0clhghfoij    "+payAmnt.toFloat())
                    Config.snackBarWarning(context,it,"Balance Amount should be zero")
                }
            }


            dialogPaymentSheet!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"801 Exception  "+e.toString())
        }
    }

    private fun validateAddPayment(view: View) {
        var balAmount = (txtPayBalAmount!!.text.toString()).toFloat()
        var payAmount = edtPayAmount!!.text.toString()

        Log.e(TAG,"110   balAmount   : "+balAmount)
        Log.e(TAG,"110   payAmount   : "+payAmount)
        var hasId = hasPayMethod(arrPayment,"MethodID",ID_PaymentMethod!!)

        if (ID_PaymentMethod.equals("")){
            Log.e(TAG,"110   Valid   : Select Payment Method")
            edtPayMethod!!.setError("Select Payment Method",null)
            txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
            Config.snackBarWarning(context,view,"Select Payment Method")
        }
        else if (hasId == false && arrAddUpdate.equals("0")){
            txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
            Config.snackBarWarning(context,view,"PaymentMethod Already exits")
        }
        else if (edtPayAmount!!.text.toString().equals("")){
            txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
            Log.e(TAG,"110   Valid   : Enter Amount")
            Config.snackBarWarning(context,view,"Enter Amount")

        }else if (balAmount < payAmount.toFloat() ){
            Log.e(TAG,"110   Valid   : Enter Amount DD")
            txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
            Config.snackBarWarning(context,view,"Amount should be less than or equal to Bal. Amount")
        }else{

            if (arrAddUpdate.equals("0")){

                val payAmnt = DecimelFormatters.set2DecimelPlace(balAmount- payAmount.toFloat())
                // txtPayBalAmount!!.text = (balAmount- payAmount.toFloat()).toString()
                txtPayBalAmount!!.text = payAmnt
                val jObject = JSONObject()
                jObject.put("MethodID",ID_PaymentMethod)
                jObject.put("Method",edtPayMethod!!.text.toString())
                jObject.put("RefNo",edtPayRefNo!!.text.toString())
                jObject.put("Amount",DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))
                arrPayment!!.put(jObject)
            }
            if (arrAddUpdate.equals("1")){

                val payAmnt = DecimelFormatters.set2DecimelPlace(balAmount- payAmount.toFloat())
                // txtPayBalAmount!!.text = (balAmount- payAmount.toFloat()).toString()
                txtPayBalAmount!!.text = payAmnt

                val jsonObject = arrPayment.getJSONObject(arrPosition!!)
                jsonObject.put("MethodID",ID_PaymentMethod)
                jsonObject.put("Method",edtPayMethod!!.text.toString())
                jsonObject.put("RefNo",edtPayRefNo!!.text.toString())
                jsonObject.put("Amount",DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))

                arrAddUpdate = "0"



            }

            applyMode = 0
            ID_PaymentMethod = ""
            edtPayMethod!!.setText("")
            edtPayRefNo!!.setText("")
            edtPayAmount!!.setText("")

            if (arrPayment.length() > 0){
                ll_paymentlist!!.visibility = View.VISIBLE
                viewList(arrPayment)
            }else{
                ll_paymentlist!!.visibility = View.GONE
                recyPaymentList!!.adapter = null
            }




        }

        Log.e(TAG,"110  arrPayment  :  "+arrPayment)
    }

    private fun viewList(arrPayment: JSONArray) {

        val lLayout = GridLayoutManager(this@EmiCollectionActivity, 1)
        recyPaymentList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
        adapterPaymentList = PaymentListAdapter(this@EmiCollectionActivity, arrPayment)
        recyPaymentList!!.adapter = adapterPaymentList
        adapterPaymentList!!.setClickListener(this@EmiCollectionActivity)
    }

    override fun onClick(position: Int, data: String) {

        if (data.equals("deleteArrayList")){


            val jsonObject = arrPayment.getJSONObject(position)
            var balAmount = (txtPayBalAmount!!.text.toString()).toFloat()
            var Amount = (jsonObject!!.getString("Amount")).toFloat()

            arrPayment.remove(position)
            adapterPaymentList!!.notifyItemRemoved(position)

            if (arrPayment.length() > 0){
                ll_paymentlist!!.visibility = View.VISIBLE
            }else{
                ll_paymentlist!!.visibility =View.GONE
            }
            applyMode = 0
            txtPayBalAmount!!.text = (balAmount + Amount).toString()
        }

        if (data.equals("editArrayList")){
            try {
                arrAddUpdate = "1"
                arrPosition = position
                val jsonObject = arrPayment.getJSONObject(position)
                ID_PaymentMethod = jsonObject!!.getString("MethodID")
                edtPayMethod!!.setText(""+jsonObject!!.getString("Method"))
                edtPayRefNo!!.setText(""+jsonObject!!.getString("RefNo"))
                edtPayAmount!!.setText(""+jsonObject!!.getString("Amount"))


                var payAmnt = 0.00
                for (i in 0 until arrPayment.length()) {
                    //apply your logic
                    val jsonObject = arrPayment.getJSONObject(i)
                    payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                }
                val pay = DecimelFormatters.set2DecimelPlace(payAmnt.toFloat())
                //  payAmnt = (DecimelFormatters.set2DecimelPlace(payAmnt.toFloat()))
                Log.e(TAG,"payAmnt         475    "+payAmnt)
                Log.e(TAG,"tv_NetAmount    475    "+tv_NetAmount!!.text.toString())
                txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - pay.toFloat() + (jsonObject!!.getString("Amount").toFloat())))


//                Log.e(TAG,"605   "+txtPayBalAmount!!.text.toString().toFloat())
//                var payAmnt = ((txtPayBalAmount!!.text.toString().toFloat()) + (jsonObject!!.getString("Amount").toFloat()))
//                txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace(payAmnt))

            }catch (e : Exception){

            }
        }

        if (data.equals("employee")) {
            dialogEmployee!!.dismiss()
//             val jsonObject = employeeArrayList.getJSONObject(position)
            val jsonObject = employeeSort.getJSONObject(position)
            Log.e(TAG, "ID_Employee   " + jsonObject.getString("ID_Employee"))
            ID_CollectedBy = jsonObject.getString("ID_Employee")
            edtCollectedBy!!.setText(jsonObject.getString("EmpName"))


        }

        if (data.equals("paymentMethod")){
            dialogPaymentMethod!!.dismiss()
            val jsonObject = paymentMethodeArrayList.getJSONObject(position)
            ID_PaymentMethod= jsonObject.getString("ID_PaymentMethod")
            edtPayMethod!!.setText(jsonObject.getString("PaymentName"))
        }
    }


    private fun getChannelEmp() {
        var ID_Department = "0"
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
                                if (empMediaDet == 0) {
                                    empMediaDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1224   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeDetails")
                                        employeeArrayList = jobjt.getJSONArray("EmployeeDetailsList")
                                        if (employeeArrayList.length() > 0) {

                                            employeePopup(employeeArrayList)


                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiCollectionActivity,
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

            val lLayout = GridLayoutManager(this@EmiCollectionActivity, 1)
            recyEmployee!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
            val adapter = EmployeeAdapter(this@EmiCollectionActivity, employeeSort)
            recyEmployee!!.adapter = adapter
            adapter.setClickListener(this@EmiCollectionActivity)

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
                    val adapter = EmployeeAdapter(this@EmiCollectionActivity, employeeSort)
                    recyEmployee!!.adapter = adapter
                    adapter.setClickListener(this@EmiCollectionActivity)
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
                                        paymentMethodeArrayList = jobjt.getJSONArray("FollowUpPaymentMethodList")
                                        if (paymentMethodeArrayList.length() > 0) {

                                            payMethodPopup(paymentMethodeArrayList)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiCollectionActivity,
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
            recyPaymentMethod = dialogPaymentMethod!!.findViewById(R.id.recyPaymentMethod) as RecyclerView

            val lLayout = GridLayoutManager(this@EmiCollectionActivity, 1)
            recyPaymentMethod!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
            val adapter = PayMethodAdapter(this@EmiCollectionActivity, paymentMethodeArrayList)
            recyPaymentMethod!!.adapter = adapter
            adapter.setClickListener(this@EmiCollectionActivity)



            dialogPaymentMethod!!.show()
            dialogPaymentMethod!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
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
                editable === edtPayMethod!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (edtPayMethod!!.text.toString().equals("")){
//                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
                    }else{
                     //   til_DeliveryDate!!.isErrorEnabled = false
                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.black))
                    }

                }

                editable === edtPayAmount!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (edtPayAmount!!.text.toString().equals("")){
//                        txt_pay_method!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))
                    }else{
                        //   til_DeliveryDate!!.isErrorEnabled = false
                        txt_pay_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.black))
                    }

                }

                editable === txtPayBalAmount!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    val payAmnt = DecimelFormatters.set2DecimelPlace(txtPayBalAmount!!.text.toString().toFloat())
                    if ((payAmnt.toFloat()).equals("0.00".toFloat())){
                        Log.e(TAG,"801 payAmnt  0.00  "+payAmnt.toFloat())
                        txt_bal_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.black))
                    }
                    else{
                        Log.e(TAG,"801 payAmnt  0.0clhghfoij    "+payAmnt.toFloat())
                        txt_bal_Amount!!.setTextColor(ContextCompat.getColorStateList(context,R.color.color_mandatory))

                    }

                }





            }

        }
    }

    fun hasPayMethod(json: JSONArray, key: String, value: String): Boolean {
        for (i in 0 until json.length()) {  // iterate through the JsonArray
            // first I get the 'i' JsonElement as a JsonObject, then I get the key as a string and I compare it with the value
            if (json.getJSONObject(i).getString(key) == value) return false
        }
        return true
    }

    private fun saveValidation(v: View) {

        strSaveTrnsDate = edtTransDate!!.text.toString()
//        strID_CustomerWiseEMI = ID_CustomerWiseEMI
        strSaveCollectDate = edtCollectionDate!!.text.toString()

        strSaveInstllAmount = Config.changeTwoDecimel(edtInsAmount!!.text.toString())
        strSaveTotalAmount = Config.changeTwoDecimel(edtInsAmount!!.text.toString())
        strSaveBalance = Config.changeTwoDecimel(tv_Balance!!.text.toString())
        strSaveFineAmount = Config.changeTwoDecimel(edtFine!!.text.toString())
        strSaveNetAmount = Config.changeTwoDecimel(tv_NetAmount!!.text.toString())
        Log.e(TAG,"VALUES  1411    "+strSaveFineAmount)

        if (strSaveNetAmount!!.toFloat() > 0){


            try {
                saveEmiDetailsArray =  JSONArray()
                val jsonObject1 = JSONObject()
                jsonObject1.put("FK_CustomerWiseEMI",ID_CustomerWiseEMI)
                jsonObject1.put("CusTrDetPayAmount",strSaveInstllAmount)
                jsonObject1.put("CusTrDetFineAmount",strSaveFineAmount)
                jsonObject1.put("Total",strSaveTotalAmount)
                jsonObject1.put("Balance",strSaveBalance)
                jsonObject1.put("FK_Closed",strSaveClosing)

                saveEmiDetailsArray.put(jsonObject1)


                /////
                totPayAmount = 0.0f
                savePaymentDetailArray = JSONArray()
                for (i in 0 until arrPayment.length()) {
                    var jsonObject = arrPayment.getJSONObject(i)
                    val jsonObject2 = JSONObject()
                    jsonObject2.put("PaymentMethod",jsonObject.getString("MethodID"))
                    jsonObject2.put("PAmount",jsonObject.getString("Amount"))
                    jsonObject2.put("Refno",jsonObject.getString("RefNo"))


                    savePaymentDetailArray.put(jsonObject2)
                    totPayAmount = totPayAmount + jsonObject.getString("Amount").toFloat()
                }


                validatePaymentMethod(v)

            }catch (e : Exception){

            }


        }else{

            Config.snackBarWarning(context,v,"Net Amount should be greater than zero")
        }









//        var payAmnt = ""
//        if (arrPayment.length() > 0){
//            payAmnt = DecimelFormatters.set2DecimelPlace(txtPayBalAmount!!.text.toString().toFloat())
//        }
//
//        if (arrPayment.length() == 0){
//            Config.snackBarWarning(context,v,"Select payment method")
//        }
//        else  if (!(payAmnt.toFloat()).equals("0.00".toFloat())){
//            Config.snackBarWarning(context,v,"In payment method Bal. Amount should be zero")
//        }else{
//            Config.snackBarWarning(context,v,"Success "+payAmnt)
//        }


    }

    private fun validatePaymentMethod(v: View) {
        Log.e(TAG,"VALUES  1411   "+totPayAmount+"  ==  "+strSaveNetAmount.toFloat())

        if (totPayAmount == strSaveNetAmount.toFloat()){

            Log.e(TAG,"VALUES  1411"
                    +"\n  TrnsDate      "+strSaveTrnsDate
                    +"\n ID_CustomerWiseEMI      "+ID_CustomerWiseEMI
                    +"\n CollectDate      "+strSaveCollectDate
                    +"\n TotalAmount     "+strSaveTotalAmount
                    +"\n  FineAmount    "+strSaveFineAmount
                    +"\n  NetAmount    "+strSaveNetAmount
                    +"\n  FK_Employee    "+ID_CollectedBy
                    +"\n  FK_Employee    "+ID_CollectedBy


                    +"\n  saveEmiDetailsArray    "+saveEmiDetailsArray
                    +"\n  savePaymentDetailArray    "+savePaymentDetailArray
            )

            updateEmiCollection()

        }else{
            Config.snackBars(context,v,"In Payment Method Balance Amt. Should be Zero")

        }


    }

    private fun updateEmiCollection() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                updateEMICollectionViewModel.setUpdateEMICollection(this,strSaveTrnsDate,ID_CustomerWiseEMI!!,strSaveCollectDate,strSaveTotalAmount,strSaveFineAmount,
                    strSaveNetAmount,ID_CollectedBy!!,saveEmiDetailsArray,savePaymentDetailArray)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (empMediaDet == 0) {
                                    empMediaDet++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1525   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                   //     {"UpdateEMICollection":{"Message":"Update Successfully","ResponseCode":"0","ResponseMessage":"Transaction Verified"},"StatusCode":0,"EXMessage":"Transaction Verified"}
                                        try {
                                            val jobjt = jObject.getJSONObject("UpdateEMICollection")
                                            val suceessDialog = Dialog(this)
                                            suceessDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                            suceessDialog!!.setCancelable(false)
                                            suceessDialog!! .setContentView(R.layout.success_service_popup)
                                            suceessDialog!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL
                                            suceessDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                                            suceessDialog!!.setCancelable(false)

                                            val tv_succesmsg = suceessDialog!! .findViewById(R.id.tv_succesmsg) as TextView
                                            val tv_succesok = suceessDialog!! .findViewById(R.id.tv_succesok) as TextView

                                            tv_succesmsg!!.setText(jobjt.getString("Message"))

                                            tv_succesok!!.setOnClickListener {
                                                suceessDialog!!.dismiss()
                                                onBackPressed()

                                            }

                                            suceessDialog!!.show()
                                            suceessDialog!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        }catch (e: Exception){
                                            val builder = AlertDialog.Builder(
                                                this@EmiCollectionActivity,
                                                R.style.MyDialogTheme
                                            )
                                            builder.setMessage(e.toString())
                                            builder.setPositiveButton("Ok") { dialogInterface, which ->

                                            }
                                            val alertDialog: AlertDialog = builder.create()
                                            alertDialog.setCancelable(false)
                                            alertDialog.show()
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiCollectionActivity,
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


}
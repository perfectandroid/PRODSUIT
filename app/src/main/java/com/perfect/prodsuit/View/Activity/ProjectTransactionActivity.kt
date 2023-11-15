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
import com.perfect.prodsuit.Helper.DecimalToWordsConverter
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.*
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ProjectTransactionActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {

    val TAG : String = "ProjectTransactionActivity"
    lateinit var context: Context
    private var progressDialog    : ProgressDialog?    = null

    private var tie_Project       : TextInputEditText? = null
    private var tie_Stage       : TextInputEditText? = null
    private var tie_Date       : TextInputEditText? = null
    private var tie_OtherCharges       : TextInputEditText? = null
    private var tie_PaymentMethod       : TextInputEditText? = null
    private var tie_Remarks       : TextInputEditText? = null

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
    private var strOtherAmount                    = ""
    private var strRemark                    = ""

    var jsonObj: JSONObject? = null
    private var dialogOtherChargesSheet : Dialog? = null
    val modelOtherChargesCalculation = ArrayList<ModelOtherChargesCalculation>()
    private var dialogOtherChargesCalcSheet : Dialog? = null
    private var otherChargeAdapter : OtherChargeAdapter? = null
    private var taxDetailAdapter : TaxDetailAdapter? = null

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
    internal var recyPaymentList: RecyclerView? = null
    internal var ll_paymentlist: LinearLayout? = null
    var arrPayment = JSONArray()
    var adapterPaymentList: PaymentListAdapter? = null
    var arrAddUpdate: String? = "0"
    var strSumPayMethode = "0"

    var paymentCount = 0
    lateinit var paymentMethodeViewModel: PaymentMethodViewModel
    lateinit var paymentMethodeArrayList: JSONArray
    private var dialogPaymentMethod: Dialog? = null
    var recyPaymentMethod: RecyclerView? = null
    var applyMode = 0
    var arrPosition: Int? = 0
    var ID_PaymentMethod: String? = ""

    private var btnSubmit: Button? = null
    private var btnReset: Button? = null
    var savePaymentDetailArray = JSONArray()
    var totPayAmount = 0.0f

    var updateCount = 0
    lateinit var updateProjectTransactionViewModel: UpdateProjectTransactionViewModel
    var pssOtherCharge= JSONArray()
    var pssOtherChargeTax= JSONArray()


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
        paymentMethodeViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)
        updateProjectTransactionViewModel = ViewModelProvider(this).get(UpdateProjectTransactionViewModel::class.java)

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
        tie_PaymentMethod    = findViewById(R.id.tie_PaymentMethod)
        tie_Remarks    = findViewById(R.id.tie_Remarks)

        til_Project      = findViewById(R.id.til_Project)
        til_Stage    = findViewById(R.id.til_Stage)
        til_Date    = findViewById(R.id.til_Date)

        btnReset = findViewById(R.id.btnReset)
        btnSubmit = findViewById(R.id.btnSubmit)

        tie_Project!!.setOnClickListener(this)
        tie_Stage!!.setOnClickListener(this)
        tie_Date!!.setOnClickListener(this)
        tie_OtherCharges!!.setOnClickListener(this)
        tie_PaymentMethod!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)





        DecimelFormatters.setDecimelPlace(tie_OtherCharges!!)

        til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Date!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)

        onTextChangedValues()

        getCurrentDate()
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


            tie_Date!!.setText("" + sdfDate1.format(newDate))


        } catch (e: Exception) {

            Log.e(TAG, "Exception 456  " + e.toString())
        }
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
            R.id.tie_PaymentMethod->{
                Config.disableClick(v)
                paymentMethodPopup()
            }

            R.id.btnSubmit->{
                Config.disableClick(v)
                validateProjectTransaction(v)

            }
            R.id.btnReset->{
                Config.disableClick(v)
                clearData()
            }
        }
    }

    private fun clearData() {

        ID_Stage = ""

        tie_Stage!!.setText("")
        tie_OtherCharges!!.setText("")
        tie_Remarks!!.setText("")

        modelOtherCharges.clear()
        otherChargeAdapter = null

        modelOtherChargesCalculation.clear()
        taxDetailAdapter = null

        arrPayment = JSONArray()
        adapterPaymentList = null

        getCurrentDate()
    }

    private fun validateProjectTransaction(v: View) {

        strDate = tie_Date!!.text.toString()
        strOtherAmount = tie_OtherCharges!!.text.toString()
        strRemark = tie_Remarks!!.text.toString()
        if (ID_Project.equals("")){
            Config.snackBars(context, v, "Select Project")
        }else if(strDate.equals("")){
            Config.snackBars(context, v, "Select Date")
        }
        else if(strOtherAmount.equals("")){
            Config.snackBars(context, v, "Other Amount cannot be zero")
        }
        else{

            validateSaveOtherCharges(v)


        }




    }

    private fun validateSaveOtherCharges(v: View) {

        pssOtherCharge = JSONArray()
        pssOtherChargeTax = JSONArray()
        for (obj in modelOtherCharges) {

            if (obj.isCalculate){
                val jsonObject = JSONObject()
                jsonObject.put("ID_OtherChargeType", obj.ID_OtherChargeType)
                jsonObject.put("OctyTransType", obj.OctyTransType)
                jsonObject.put("FK_TaxGroup", obj.FK_TaxGroup)
                jsonObject.put("OctyAmount", obj.OctyAmount)
                jsonObject.put("OctyTaxAmount", obj.OctyTaxAmount)
                jsonObject.put("OctyIncludeTaxAmount", obj.OctyIncludeTaxAmount)

                pssOtherCharge.put(jsonObject)
                for (objSub in modelOtherChargesCalculation) {
                    val jsonObject1 = JSONObject()
                    if (obj.FK_TaxGroup.equals(objSub.FK_TaxGroup)){
                        jsonObject1.put("ID_OtherChargeType", obj.ID_OtherChargeType)
                        jsonObject1.put("ID_TaxSettings", objSub.ID_TaxSettings)
                        jsonObject1.put("Amount", objSub.Amount)
                        jsonObject1.put("TaxPercentage", objSub.TaxPercentage)
                        jsonObject1.put("TaxGrpID", objSub.FK_TaxGroup)
                        jsonObject1.put("FK_TaxType", objSub.FK_TaxType)
                        jsonObject1.put("TaxTyName", objSub.TaxTyName)

                        pssOtherChargeTax.put(jsonObject)
                    }
                }

            }

        }


        Log.e(TAG," 117884 pssOtherCharge          :  "+pssOtherCharge)
        Log.e(TAG," 117885 pssOtherChargeTax       :  "+pssOtherChargeTax)

        validatePay(v)

    }

    private fun validatePay(v: View) {
        try {
            totPayAmount = 0.0f
            savePaymentDetailArray = JSONArray()
            for (i in 0 until arrPayment.length()) {
                var jsonObject = arrPayment.getJSONObject(i)
                val jsonObject2 = JSONObject()
                jsonObject2.put("PaymentMethod",jsonObject.getString("MethodID"))
                jsonObject2.put("Refno",jsonObject.getString("RefNo"))
                jsonObject2.put("PAmount",jsonObject.getString("Amount"))



                savePaymentDetailArray.put(jsonObject2)
                totPayAmount = totPayAmount + jsonObject.getString("Amount").toFloat()
            }

            validatePaymentMethod(v)
        }
        catch (e : Exception){

        }

    }

    private fun validatePaymentMethod(v: View) {

        var otheAmount = tie_OtherCharges!!.text.toString()
        if (otheAmount.equals("") || otheAmount.equals(".")){
            otheAmount = "0.00"
        }

        if (otheAmount.toFloat() < 0){
            otheAmount = (Math.abs(otheAmount.toFloat())).toString()
        }
        Log.e(TAG,"VALUES  1411   "+totPayAmount+"  ==  "+otheAmount.toFloat())

        if (totPayAmount == otheAmount.toFloat()){
            updateCount = 0
            updateProjectTransaction()
        }else{
            Config.snackBars(context,v,"In Payment Method Balance Amt. Should be Zero")

        }


    }

    private fun updateProjectTransaction() {

        var UserAction = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                updateProjectTransactionViewModel.SaveUpdateProjectTransaction(this,UserAction,strDate,ID_Project,ID_Stage,
                    totPayAmount!!.toString(),strOtherAmount,strRemark,pssOtherCharge,pssOtherChargeTax,savePaymentDetailArray)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (updateCount == 0) {
                                    updateCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   421111   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("UpdateProjectTransaction")
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
                                            val tv_succesok = suceessDialog!!.findViewById(R.id.tv_succesok) as TextView
                                            val ll_leadnumber = suceessDialog!!.findViewById(R.id.ll_leadnumber) as LinearLayout
                                            //LeadNumber
                                            ll_leadnumber.visibility = View.GONE
                                            tv_succesmsg!!.setText(jobjt.getString("ResponseMessage"))


                                            tv_succesok!!.setOnClickListener {
                                                suceessDialog!!.dismiss()
                                                finish()

                                            }

                                            suceessDialog!!.show()
                                            suceessDialog!!.getWindow()!!.setLayout(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                            );
                                        }catch (e : Exception){

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


            var otheAmount = tie_OtherCharges!!.text.toString()
            if (otheAmount.equals("") || otheAmount.equals(".")){
                otheAmount = "0.00"
            }

            if (otheAmount.toFloat() < 0){
                otheAmount = (Math.abs(otheAmount.toFloat())).toString()
            }

            // txtPayBalAmount!!.setText(""+tv_NetAmount!!.text.toString())

            if (arrPayment.length() > 0){
                ll_paymentlist!!.visibility = View.VISIBLE
                viewList(arrPayment)
                var payAmnt = 0.00
                for (i in 0 until arrPayment.length()) {
                    //apply your logic
                    val jsonObject = arrPayment.getJSONObject(i)
                    Log.e(TAG,"9451  ")
                    payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                }
                Log.e(TAG,"9452  ")
                val pay = DecimelFormatters.set2DecimelPlace(payAmnt.toFloat())
                //  payAmnt = (DecimelFormatters.set2DecimelPlace(payAmnt.toFloat()))
                Log.e(TAG,"9453  ")
                Log.e(TAG,"payAmnt         475    "+payAmnt)
                Log.e(TAG,"otheAmount    475    "+otheAmount)
                //  txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - pay.toFloat()))
                txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((DecimalToWordsConverter.commaRemover(otheAmount).toFloat()) - pay.toFloat()))
                Log.e(TAG,"9454  ")
            }else{
                Log.e(TAG,"9455  ")
                ll_paymentlist!!.visibility = View.GONE
                recyPaymentList!!.adapter = null
                Log.e(TAG,"94551  "+otheAmount)
                txtPayBalAmount!!.setText(""+otheAmount)
                Log.e(TAG,"9456  ")
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
                    txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((otheAmount.toFloat()) - payAmnt.toFloat()))
                }else{
                    txtPayBalAmount!!.setText(""+otheAmount)
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
            Log.e(TAG,"1012 Exception  "+e.toString())
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

            val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
            recyPaymentMethod!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
//             val adapter = EmployeeAdapter(this@LeadGenerationActivity, employeeArrayList)
            val adapter = PayMethodAdapter(this@ProjectTransactionActivity, paymentMethodeArrayList)
            recyPaymentMethod!!.adapter = adapter
            adapter.setClickListener(this@ProjectTransactionActivity)



            dialogPaymentMethod!!.show()
            dialogPaymentMethod!!.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun validateAddPayment(view: View) {
        var balAmount = (DecimalToWordsConverter.commaRemover(txtPayBalAmount!!.text.toString())).toFloat()
        //  var payAmount = edtPayAmount!!.text.toString()
        var payAmount = DecimalToWordsConverter.commaRemover(edtPayAmount!!.text.toString())


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
        else if (payAmount.equals("")){
//            else if (edtPayAmount!!.text.toString().equals("")){
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
                // jObject.put("Amount",DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))
                jObject.put("Amount",DecimelFormatters.set2DecimelPlace((payAmount).toFloat()))
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
                //   jsonObject.put("Amount",DecimelFormatters.set2DecimelPlace((edtPayAmount!!.text.toString()).toFloat()))
                jsonObject.put("Amount",DecimelFormatters.set2DecimelPlace((payAmount).toFloat()))

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

    fun hasPayMethod(json: JSONArray, key: String, value: String): Boolean {
        for (i in 0 until json.length()) {  // iterate through the JsonArray
            // first I get the 'i' JsonElement as a JsonObject, then I get the key as a string and I compare it with the value
            if (json.getJSONObject(i).getString(key) == value) return false
        }
        return true
    }

    private fun viewList(arrPayment: JSONArray) {

        val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
        recyPaymentList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
        adapterPaymentList = PaymentListAdapter(this@ProjectTransactionActivity, arrPayment)
        recyPaymentList!!.adapter = adapterPaymentList
        adapterPaymentList!!.setClickListener(this@ProjectTransactionActivity)
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
        tie_Date!!.addTextChangedListener(watcher)
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

        date_Picker1.maxDate = System.currentTimeMillis()

//        val maxDate = Calendar.getInstance()
//        maxDate.add(Calendar.MONTH, 1)
//        date_Picker1.maxDate = maxDate.timeInMillis

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

        if (data.equals("deleteArrayList")){


            val jsonObject = arrPayment.getJSONObject(position)
            var balAmount = (txtPayBalAmount!!.text.toString()).toFloat()
            var Amount = (jsonObject!!.getString("Amount")).toFloat()

            ID_PaymentMethod = ""
            edtPayMethod!!.setText("")
            edtPayRefNo!!.setText("")
            edtPayAmount!!.setText("")

            arrAddUpdate = "0"

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

                var otheAmount = tie_OtherCharges!!.text.toString()
                if (otheAmount.equals("") || otheAmount.equals(".")){
                    otheAmount = "0.00"
                }

                if (otheAmount.toFloat() < 0){
                    otheAmount = (Math.abs(otheAmount.toFloat())).toString()
                }

                var payAmnt = 0.00
                for (i in 0 until arrPayment.length()) {
                    //apply your logic
                    val jsonObject = arrPayment.getJSONObject(i)
                    payAmnt = (payAmnt + (jsonObject.getString("Amount")).toFloat())
                }
                val pay = DecimelFormatters.set2DecimelPlace(payAmnt.toFloat())
                //  payAmnt = (DecimelFormatters.set2DecimelPlace(payAmnt.toFloat()))
                Log.e(TAG,"payAmnt         475    "+payAmnt)
                Log.e(TAG,"otheAmount    475    "+otheAmount)
                txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((otheAmount.toFloat()) - pay.toFloat() + (jsonObject!!.getString("Amount").toFloat())))


//                Log.e(TAG,"605   "+txtPayBalAmount!!.text.toString().toFloat())
//                var payAmnt = ((txtPayBalAmount!!.text.toString().toFloat()) + (jsonObject!!.getString("Amount").toFloat()))
//                txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace(payAmnt))

            }catch (e : Exception){

            }
        }

        if (data.equals("paymentMethod")){
            dialogPaymentMethod!!.dismiss()
            val jsonObject = paymentMethodeArrayList.getJSONObject(position)
            ID_PaymentMethod= jsonObject.getString("ID_PaymentMethod")
            edtPayMethod!!.setText(jsonObject.getString("PaymentName"))
        }

    }




}
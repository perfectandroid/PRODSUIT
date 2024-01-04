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
import com.perfect.prodsuit.Helper.Common
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
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class ProjectTransactionActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {

    val TAG : String = "ProjectTransactionActivity"
    lateinit var context: Context
    private var progressDialog    : ProgressDialog?    = null

    private var tie_TransactionType       : TextInputEditText? = null
    private var tie_Project       : TextInputEditText? = null
    private var tie_Stage       : TextInputEditText? = null
    private var tie_Date       : TextInputEditText? = null
    private var tie_OtherCharges       : TextInputEditText? = null
    private var tie_PaymentMethod       : TextInputEditText? = null
    private var tie_Remarks       : TextInputEditText? = null
    private var tie_NetAmount       : TextInputEditText? = null

    private var tie_Bill_Type       : TextInputEditText? = null
    private var tie_Petty_Cashier       : TextInputEditText? = null
    private var tie_Employee       : TextInputEditText? = null
    private var tie_RoundOff       : TextInputEditText? = null

    private var til_TransactionType       : TextInputLayout?   = null
    private var til_Bill_Type       : TextInputLayout?   = null
    private var til_Petty_Cashier       : TextInputLayout?   = null
    private var til_PaymentMethod       : TextInputLayout?   = null
    private var til_Employee       : TextInputLayout?   = null
    private var til_RoundOff       : TextInputLayout?   = null
    private var til_NetAmount       : TextInputLayout?   = null

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

    var transtypecount = 0
    var projectcount = 0
    var stagecount   = 0
    var otherchargecount   = 0
    var otherchargetaxcount   = 0
    var otherchargetaxMode   = 0  // 0 = Popup, 1 = change amount
    var otherChargeCalcPosition   = 0
    var otherChargeClickPosition   = 0

    lateinit var transTypeArrayList  : JSONArray
    lateinit var projectArrayList  : JSONArray
    lateinit var stageArrayList    : JSONArray
    lateinit var otherChargeArrayList : JSONArray
    lateinit var otherChargeCalcArrayList : JSONArray


    lateinit var projectSort       : JSONArray
    lateinit var stageSort         : JSONArray

    val modelOtherCharges = ArrayList<ModelOtherCharges>()
    val modelOtherChargesTemp = ArrayList<ModelOtherChargesTemp>()

    private var dialogTransType     : Dialog?            = null
    private var dialogProject     : Dialog?            = null
    private var dialogStage       : Dialog?            = null

    private var recylist          : RecyclerView?      = null
    private var recyOtherCalc     : RecyclerView?      = null

    private var tvv_list_name     : TextView?          = null

    var ID_TransactionType   = ""
    var ID_Project   = ""
    var ID_Stage   = ""
    var ID_BillType   = ""
    var ID_PettyCashier   = ""
    var ID_Employee   = ""
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

    var CreatedDate                                       = ""

    lateinit var projectTransactionTypeViewModel: ProjectTransactionTypeViewModel

    var billtypeCount = 0
    lateinit var billtTypeViewModel: BilltTypeViewModel
    lateinit var billtTypeArrayList: JSONArray
    private var dialogBilltType: Dialog? = null
    //var recyPaymentMethod: RecyclerView? = null

    var pettCashierCount = 0
    lateinit var pettyCashierViewModel: PettyCashierViewModel
    lateinit var pettyCashierArrayList: JSONArray
    private var dialogPettyCashier: Dialog? = null

    var employeeCount = 0
    lateinit var projectWiseEmployeeViewModel: ProjectWiseEmployeeViewModel
    lateinit var projectWiseEmployeeArrayList: JSONArray
    lateinit var projectWiseEmployeeSort: JSONArray
    private var dialogProjectWiseEmployee: Dialog? = null

    private var imLeadedit: ImageView? = null
    private var ll_paymentInfo: LinearLayout? = null

    private var recyle_paymentInfo          : RecyclerView?      = null
    lateinit var paymentInfoViewModel: PaymentInfoViewModel
    var payCount = 0
    lateinit var paymentInfoList: JSONArray

    private var paymentAdapter : PaymentInfoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_project_transactionnew)
        context = this@ProjectTransactionActivity

        materialusageProjectViewModel   = ViewModelProvider(this).get(MaterialUsageProjectViewModel::class.java)
        materialusageStageViewModel     = ViewModelProvider(this).get(MaterialUsageStageViewModel::class.java)
        otherChargesViewModel     = ViewModelProvider(this).get(OtherChargesViewModel::class.java)
        otherChargeTaxCalculationViewModel     = ViewModelProvider(this).get(
            OtherChargeTaxCalculationViewModel::class.java)
        paymentMethodeViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)
        updateProjectTransactionViewModel = ViewModelProvider(this).get(UpdateProjectTransactionViewModel::class.java)
        projectTransactionTypeViewModel = ViewModelProvider(this).get(ProjectTransactionTypeViewModel::class.java)
        billtTypeViewModel = ViewModelProvider(this).get(BilltTypeViewModel::class.java)
        pettyCashierViewModel = ViewModelProvider(this).get(PettyCashierViewModel::class.java)
        projectWiseEmployeeViewModel = ViewModelProvider(this).get(ProjectWiseEmployeeViewModel::class.java)
        paymentInfoViewModel = ViewModelProvider(this).get(PaymentInfoViewModel::class.java)

        setRegViews()
//        var jsonObject: String? = intent.getStringExtra("jsonObject")
//        jsonObj = JSONObject(jsonObject)
//
//        CreatedDate = jsonObj!!.getString("CreateDate")
//        ID_Project = jsonObj!!.getString("ID_Project")
//        tie_Project!!.setText(jsonObj!!.getString("ProjName"))

        // Create Model Class
//        ProjectPayInfoViewModel
//        ProjectTransactionTypeViewModel
//        BilltTypeViewModel
//        PettyCashierViewModel

        hideShow("0")
        setMandatoryField("0")


    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tie_TransactionType      = findViewById(R.id.tie_TransactionType)
        tie_Project      = findViewById(R.id.tie_Project)
        tie_Stage    = findViewById(R.id.tie_Stage)
        tie_Date    = findViewById(R.id.tie_Date)
        tie_OtherCharges    = findViewById(R.id.tie_OtherCharges)
        tie_PaymentMethod    = findViewById(R.id.tie_PaymentMethod)
        tie_Remarks    = findViewById(R.id.tie_Remarks)
        tie_NetAmount    = findViewById(R.id.tie_NetAmount)

        tie_Bill_Type    = findViewById(R.id.tie_Bill_Type)
        tie_Petty_Cashier    = findViewById(R.id.tie_Petty_Cashier)
        tie_Employee    = findViewById(R.id.tie_Employee)
        tie_RoundOff    = findViewById(R.id.tie_RoundOff)


        til_TransactionType      = findViewById(R.id.til_TransactionType)
        til_Bill_Type      = findViewById(R.id.til_Bill_Type)
        til_Petty_Cashier      = findViewById(R.id.til_Petty_Cashier)
        til_PaymentMethod      = findViewById(R.id.til_PaymentMethod)
        til_Employee      = findViewById(R.id.til_Employee)
        til_RoundOff      = findViewById(R.id.til_RoundOff)
        til_NetAmount      = findViewById(R.id.til_NetAmount)

        til_Project      = findViewById(R.id.til_Project)
        til_Stage    = findViewById(R.id.til_Stage)
        til_Date    = findViewById(R.id.til_Date)

        btnReset = findViewById(R.id.btnReset)
        btnSubmit = findViewById(R.id.btnSubmit)

        recyle_paymentInfo    = findViewById(R.id.recyle_paymentInfo)
        imLeadedit    = findViewById(R.id.imLeadedit)
        ll_paymentInfo    = findViewById(R.id.ll_paymentInfo)

        tie_TransactionType!!.setOnClickListener(this)
        tie_Project!!.setOnClickListener(this)
        tie_Stage!!.setOnClickListener(this)
        tie_Date!!.setOnClickListener(this)
        tie_Bill_Type!!.setOnClickListener(this)
        tie_Petty_Cashier!!.setOnClickListener(this)
        tie_Employee!!.setOnClickListener(this)
        tie_OtherCharges!!.setOnClickListener(this)
        tie_PaymentMethod!!.setOnClickListener(this)
        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)





        DecimelFormatters.setDecimelPlace(tie_OtherCharges!!)

        til_TransactionType!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Date!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Bill_Type!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Petty_Cashier!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
        til_Employee!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)

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
            R.id.tie_TransactionType->{
                Config.disableClick(v)
                transtypecount = 0
                getTransactionType()

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

            R.id.tie_Bill_Type->{

                Config.disableClick(v)
                billtypeCount = 0
                getBillType()
            }

            R.id.tie_Petty_Cashier->{

                Config.disableClick(v)
                pettCashierCount = 0
                getPettyCashier()

            }

            R.id.tie_Employee->{

                if (ID_Project.equals("") && !ID_TransactionType.equals("1")){
                    til_Project!!.setError("Select Project")
                    til_Project!!.setErrorIconDrawable(null)
                }else{
                    Config.disableClick(v)
                    employeeCount = 0
                    getProjectWiseEmployee()
                }
            }


            R.id.tie_Date->{
                Config.disableClick(v)
                openBottomSheet()
            }

            R.id.tie_OtherCharges->{

                Log.e(TAG,"3777   "+ID_TransactionType)
                if (ID_TransactionType.equals("2") || ID_TransactionType.equals("4")){

                    if (modelOtherCharges.size == 0){
                        Config.disableClick(v)
                        otherchargecount = 0
                        getOtherCharges()
                    }else{
                        Log.e(TAG,"1491112   "+modelOtherCharges.size)
//                        modelOtherChargesTemp = modelOtherCharges :  ArrayList<ModelOtherCharges>
//                        modelOtherCharges!!.add(ModelOtherCharges(modelOtherCharges : ArrayList<ModelOtherCharges>))
                          //  modelOtherChargesTemp.add(ModelOtherChargesTemp(modelOtherCharges))
//                        modelOtherChargesTemp = modelOtherCharges : ArrayList<ModelOtherCharges>
                       // modelOtherChargesTemp.add(modelOtherCharges)
                     //   modelOtherChargesTemp.clear()
                        for (i in 0 until modelOtherCharges.size) {
//                            modelOtherChargesTemp.addAll(i,modelOtherCharges :  ArrayList<ModelOtherCharges>)
                            modelOtherChargesTemp[i].ID_OtherChargeType = modelOtherCharges[i].ID_OtherChargeType
                            modelOtherChargesTemp[i].OctyName = modelOtherCharges[i].OctyName
                            modelOtherChargesTemp[i].OctyTransTypeActive = modelOtherCharges[i].OctyTransTypeActive
                            modelOtherChargesTemp[i].OctyTransType = modelOtherCharges[i].OctyTransType
                            modelOtherChargesTemp[i].FK_TaxGroup = modelOtherCharges[i].FK_TaxGroup
                            modelOtherChargesTemp[i].OctyAmount = modelOtherCharges[i].OctyAmount
                            modelOtherChargesTemp[i].OctyTaxAmount = modelOtherCharges[i].OctyTaxAmount
                            modelOtherChargesTemp[i].OctyIncludeTaxAmount = modelOtherCharges[i].OctyIncludeTaxAmount
                            modelOtherChargesTemp[i].ID_TransType = modelOtherCharges[i].ID_TransType
                            modelOtherChargesTemp[i].TransType_Name = modelOtherCharges[i].TransType_Name
                            modelOtherChargesTemp[i].isCalculate = modelOtherCharges[i].isCalculate
                            modelOtherChargesTemp[i].isTaxCalculate = modelOtherCharges[i].isTaxCalculate

                        }
                       // otherChargesPopup(modelOtherCharges)
                        otherChargesPopup(modelOtherChargesTemp)
                    }


                }

            }
            R.id.tie_PaymentMethod->{

                var othAmnt = tie_OtherCharges!!.text.toString()
                if (othAmnt.equals("") || othAmnt.equals(".")){
                    othAmnt = "0.00"
                }

                if (ID_TransactionType.equals("1") && othAmnt.toFloat() <= 0){
                    Config.snackBarWarning(context,v,"Amount should be greter than zero")
                }else{
                    Config.disableClick(v)
                    paymentMethodPopup()
                }

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

        ID_TransactionType = ""
        ID_Project = ""
        ID_Stage = ""
        ID_Employee = ""
        ID_BillType  = ""
        ID_PettyCashier  = ""

        tie_TransactionType!!.setText("")
        tie_Project!!.setText("")
        tie_Stage!!.setText("")
        tie_Employee!!.setText("")
        tie_Bill_Type!!.setText("")
        tie_Petty_Cashier!!.setText("")
        tie_OtherCharges!!.setText("")
        tie_Remarks!!.setText("")

        modelOtherCharges.clear()
        otherChargeAdapter = null

        modelOtherChargesCalculation.clear()
        taxDetailAdapter = null

        arrPayment = JSONArray()
        adapterPaymentList = null

        getCurrentDate()
        hideShow("0")

    }

    private fun validateProjectTransaction(v: View) {

        strDate = tie_Date!!.text.toString()
        strOtherAmount = tie_OtherCharges!!.text.toString()
        strRemark = tie_Remarks!!.text.toString()

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        Log.e(TAG, "DATE TIME  196  " + currentDate)
        Log.e(TAG, "DATE TIME  375  " + ID_BillType)

       // val date1 = Config.convertDate(CreatedDate)
        val date1 = Config.convertDate(currentDate)
        val date2 = Config.convertDate(tie_Date!!.text.toString())

        var isValid1 = Config.convertTimemills(date1,date2)

        if (ID_TransactionType.equals("")){
            til_TransactionType!!.setError("Select Transaction Type")
            til_TransactionType!!.setErrorIconDrawable(null)
        }
        else if(strDate.equals("")){
            Config.snackBars(context, v, "Date should be less than or equal to Current date")
        }
        else if (ID_Project.equals("") && (ID_TransactionType.equals("2") || ID_TransactionType.equals("3") || ID_TransactionType.equals("4"))){
           // Config.snackBars(context, v, "Select Project")
            til_Project!!.setError("Select Project")
            til_Project!!.setErrorIconDrawable(null)
        }

        else if (ID_Employee.equals("") && (ID_TransactionType.equals("1") || ID_TransactionType.equals("2") || ID_TransactionType.equals("3"))){
            // Config.snackBars(context, v, "Select Project")
            til_Employee!!.setError("Select Employee")
            til_Employee!!.setErrorIconDrawable(null)
        }

//        else if(strDate.equals("") || !isValid1){
//            Config.snackBars(context, v, "Date should be greater than or equal to Created date and less than or equal to Current date")
//        }
        else if((ID_BillType.equals("")) && (ID_TransactionType.equals("1") || ID_TransactionType.equals("2") || ID_TransactionType.equals("3") || ID_TransactionType.equals("4"))){
           // Config.snackBars(context, v, "Select Bill Type")
            til_Bill_Type!!.setError("Select Bill Type")
            til_Bill_Type!!.setErrorIconDrawable(null)
        }
        else if((ID_PettyCashier.equals("")) && (ID_TransactionType.equals("1") || ID_TransactionType.equals("3") || ID_TransactionType.equals("5") || ID_TransactionType.equals("6"))){
            // Config.snackBars(context, v, "Select Bill Type")
            til_Petty_Cashier!!.setError("Select Petty cashier")
            til_Petty_Cashier!!.setErrorIconDrawable(null)
        }
        else if((ID_TransactionType.equals("1") && strOtherAmount.equals("")) || (ID_TransactionType.equals("2") && strOtherAmount.equals(""))
            || (ID_TransactionType.equals("3") && strOtherAmount.equals(""))  || (ID_TransactionType.equals("4") && strOtherAmount.equals(""))
            || (ID_TransactionType.equals("5") && strOtherAmount.equals("")) || (ID_TransactionType.equals("6") && strOtherAmount.equals("")) ){
            Config.snackBars(context, v, "Amount cannot be zero")
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

        if (ID_TransactionType.equals("1") || ID_TransactionType.equals("3") || ID_TransactionType.equals("4")
            || ID_TransactionType.equals("5") || ID_TransactionType.equals("6")){
            validatePay(v)
        }else{

            Log.e(TAG,"5491  updateProjectTransaction")
//            savePaymentDetailArray = JSONArray()
//            updateCount = 0
//            updateProjectTransaction()
        }



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
        var otheNetAmount = tie_NetAmount!!.text.toString()
        if (otheAmount.equals("") || otheAmount.equals(".")){
            otheAmount = "0.00"
        }

        if (otheAmount.toFloat() < 0){
            otheAmount = (Math.abs(otheAmount.toFloat())).toString()
        }


        ////
        if (otheNetAmount.equals("") || otheNetAmount.equals(".")){
            otheNetAmount = "0.00"
        }

        if (otheNetAmount.toFloat() < 0){
            otheNetAmount = (Math.abs(otheNetAmount.toFloat())).toString()
        }


        Log.e(TAG,"VALUES  581011   "+totPayAmount+"  ==  "+otheAmount.toFloat())
        Log.e(TAG,"VALUES  581012   "+totPayAmount+"  ==  "+otheNetAmount.toFloat())

        if((totPayAmount == otheAmount.toFloat() && ID_TransactionType.equals("1")) || (totPayAmount == otheAmount.toFloat() && ID_TransactionType.equals("3"))
            || (totPayAmount == otheAmount.toFloat() && ID_TransactionType.equals("5"))|| (totPayAmount == otheAmount.toFloat() && ID_TransactionType.equals("6"))){

            Log.e(TAG,"5492  updateProjectTransaction")
//            updateCount = 0
//            updateProjectTransaction()

            Log.e(TAG,"581021    Success")
        }
        else if (totPayAmount == otheNetAmount.toFloat() && ID_TransactionType.equals("4")){

            Log.e(TAG,"5493  updateProjectTransaction")

//            updateCount = 0
//            updateProjectTransaction()

            Log.e(TAG,"581022    Success")
        }
        else{
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
            var otheAmount = ""
            if (ID_TransactionType.equals("4")){
                otheAmount = tie_NetAmount!!.text.toString()
            }else{
                otheAmount = tie_OtherCharges!!.text.toString()
            }


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

    private fun getBillType() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                billtTypeViewModel.getBilltType(this,ID_Project,ID_Employee)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (billtypeCount == 0){
                                    billtypeCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   9788   "+msg)

                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("BillTypeDetails")
                                        billtTypeArrayList = jobjt.getJSONArray("BillTypeDetailsList")

                                        if (billtTypeArrayList.length() > 0){
                                            billtypePopup(billtTypeArrayList)
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

    private fun billtypePopup(billtTypeArrayList: JSONArray) {

        try {
            dialogBilltType = Dialog(this)
            dialogBilltType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBilltType!! .setContentView(R.layout.list_popup)
            dialogBilltType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogBilltType!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogBilltType!! .findViewById(R.id.tvv_list_name) as TextView
            var llsearch = dialogBilltType!! .findViewById(R.id.llsearch) as LinearLayout
            val etsearch = dialogBilltType!! .findViewById(R.id.etsearch) as EditText

            llsearch.visibility = View.GONE
            tvv_list_name!!.setText("Bill Type")



            val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectBillTypeAdapter(this@ProjectTransactionActivity, billtTypeArrayList)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@ProjectTransactionActivity)


            dialogBilltType!!.show()
            dialogBilltType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getPettyCashier() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                pettyCashierViewModel.getPettyCashierData(this,ID_Project,ID_Employee)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (pettCashierCount == 0){
                                    pettCashierCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1095   "+msg)

                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("PettyCashieDetails")
                                        pettyCashierArrayList = jobjt.getJSONArray("PettyCashieList")

                                        if (pettyCashierArrayList.length() > 0){
                                            pettyCashierPopup(pettyCashierArrayList)
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

    private fun pettyCashierPopup(pettyCashierArrayList: JSONArray) {

        try {
            dialogPettyCashier = Dialog(this)
            dialogPettyCashier!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPettyCashier!! .setContentView(R.layout.list_popup)
            dialogPettyCashier!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogPettyCashier!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogPettyCashier!! .findViewById(R.id.tvv_list_name) as TextView
            var llsearch = dialogPettyCashier!! .findViewById(R.id.llsearch) as LinearLayout
            val etsearch = dialogPettyCashier!! .findViewById(R.id.etsearch) as EditText

            llsearch.visibility = View.GONE
            tvv_list_name!!.setText("Petty cashier")

            val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = PettyCashierAdapter(this@ProjectTransactionActivity, pettyCashierArrayList)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@ProjectTransactionActivity)


            dialogPettyCashier!!.show()
            dialogPettyCashier!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getProjectWiseEmployee() {

        var ReqMode = "159"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                projectWiseEmployeeViewModel.getProjectWiseEmployee(this,ID_Project,ID_Stage,"0",ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (employeeCount == 0){
                                    employeeCount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1095   "+msg)

                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("ProjectTransactionEmployeeDetails")
                                        projectWiseEmployeeArrayList = jobjt.getJSONArray("ProjectTransactionEmployeeDetailsList")

                                        if (projectWiseEmployeeArrayList.length() > 0){
                                            projectWiseEmployeePopup(projectWiseEmployeeArrayList)
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

    private fun projectWiseEmployeePopup(projectWiseEmployeeArrayList: JSONArray) {
        try {
            dialogProjectWiseEmployee = Dialog(this)
            dialogProjectWiseEmployee!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProjectWiseEmployee!! .setContentView(R.layout.list_popup)
            dialogProjectWiseEmployee!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogProjectWiseEmployee!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogProjectWiseEmployee!! .findViewById(R.id.tvv_list_name) as TextView
            var llsearch = dialogProjectWiseEmployee!! .findViewById(R.id.llsearch) as LinearLayout
            val etsearch = dialogProjectWiseEmployee!! .findViewById(R.id.etsearch) as EditText

          //  llsearch.visibility = View.GONE
            tvv_list_name!!.setText("Employee")

            projectWiseEmployeeSort = JSONArray()
            for (k in 0 until projectWiseEmployeeArrayList.length()) {
                val jsonObject = projectWiseEmployeeArrayList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                projectWiseEmployeeSort.put(jsonObject)
            }
//
            val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectWiseEmployeeAdapter(this@ProjectTransactionActivity, projectWiseEmployeeSort)
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
                    projectWiseEmployeeSort = JSONArray()

                    for (k in 0 until projectWiseEmployeeArrayList.length()) {
                        val jsonObject = projectWiseEmployeeArrayList.getJSONObject(k)
//                        if (textlength <= jsonObject.getString("ProjName").length) {
//
//
//                        }

//                        if (jsonObject.getString("EmployeeName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
//                            projectWiseEmployeeSort.put(jsonObject)
//                        }

                        if (jsonObject.getString("EmployeeName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())
                            || jsonObject.getString("Department")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())
                            || jsonObject.getString("Designation")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                            projectWiseEmployeeSort.put(jsonObject)
                        }
                    }

                    Log.e(TAG,"projectWiseEmployeeSort               1399    "+projectWiseEmployeeSort)
                    val adapter = ProjectWiseEmployeeAdapter(this@ProjectTransactionActivity, projectWiseEmployeeSort)
                    recylist!!.adapter = adapter
                    adapter.setClickListener(this@ProjectTransactionActivity)
                }
            })


            dialogProjectWiseEmployee!!.show()
            dialogProjectWiseEmployee!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
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
                                            modelOtherChargesTemp.clear()
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
                                                    ID_TransType, TransType_Name,false,false))

                                                modelOtherChargesTemp!!.add(ModelOtherChargesTemp(jsonObject.getString("ID_OtherChargeType"),jsonObject.getString("OctyName"),
                                                    jsonObject.getString("OctyTransTypeActive"),jsonObject.getString("OctyTransType"),jsonObject.getString("FK_TaxGroup"),
                                                    jsonObject.getString("OctyAmount"), jsonObject.getString("OctyTaxAmount"),false,
                                                    ID_TransType,
                                                    TransType_Name,false,false))
                                            }
                                        }

                                        Log.e(TAG,"149111   "+modelOtherCharges.size)
//                                             otherChargesPopup(modelOtherCharges)
                                             otherChargesPopup(modelOtherChargesTemp)
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

    private fun otherChargesPopup(modelOtherChargesTemp : ArrayList<ModelOtherChargesTemp>) {
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
            otherChargeAdapter = OtherChargeAdapter(this@ProjectTransactionActivity, modelOtherChargesTemp)
            recycOtherCharges!!.adapter = otherChargeAdapter
            otherChargeAdapter!!.setClickListener(this@ProjectTransactionActivity)
            otherChargeAdapter!!.setClickListener(this@ProjectTransactionActivity)

            txt_close!!.setOnClickListener {
                dialogOtherChargesSheet!!.dismiss()
            }

            txt_apply!!.setOnClickListener {

                var hasCalulate = hasCalulateMethod(modelOtherChargesTemp)
                if (hasCalulate){

                    var otherCharge = 0F
                    for (i in 0 until modelOtherChargesTemp.size) {

                        Log.e(TAG,"548712   "+modelOtherChargesTemp.size+"  :  "+modelOtherCharges.size)
                        try {

                            if ( modelOtherChargesTemp[i].isCalculate){
                                modelOtherCharges[i].ID_OtherChargeType = modelOtherChargesTemp[i].ID_OtherChargeType
                                modelOtherCharges[i].OctyName = modelOtherChargesTemp[i].OctyName
                                modelOtherCharges[i].OctyTransTypeActive = modelOtherChargesTemp[i].OctyTransTypeActive
                                modelOtherCharges[i].OctyTransType = modelOtherChargesTemp[i].OctyTransType
                                modelOtherCharges[i].FK_TaxGroup = modelOtherChargesTemp[i].FK_TaxGroup
                                modelOtherCharges[i].OctyAmount = modelOtherChargesTemp[i].OctyAmount
                                modelOtherCharges[i].OctyTaxAmount = modelOtherChargesTemp[i].OctyTaxAmount
                                modelOtherCharges[i].OctyIncludeTaxAmount = modelOtherChargesTemp[i].OctyIncludeTaxAmount
                                modelOtherCharges[i].ID_TransType = modelOtherChargesTemp[i].ID_TransType
                                modelOtherCharges[i].TransType_Name = modelOtherChargesTemp[i].TransType_Name
                                modelOtherCharges[i].isCalculate = modelOtherChargesTemp[i].isCalculate
                                modelOtherCharges[i].isTaxCalculate = modelOtherChargesTemp[i].isTaxCalculate
                            }

                        }catch (e: Exception){
                            Log.e(TAG,"12548  "+e.toString())
                        }


                        if (modelOtherChargesTemp[i].isCalculate){
                            if (modelOtherChargesTemp[i].OctyIncludeTaxAmount){
                                if (modelOtherChargesTemp[i].ID_TransType.equals("2")){
                                    // Debit
                                    otherCharge = otherCharge - (modelOtherChargesTemp[i].OctyAmount).toFloat()
                                }else if (modelOtherChargesTemp[i].ID_TransType.equals("1")){
                                    // Credit
                                    otherCharge = otherCharge + (modelOtherChargesTemp[i].OctyAmount).toFloat()
                                }
                            }else{
                                if (modelOtherChargesTemp[i].ID_TransType.equals("2")){
                                    // Debit
                                    otherCharge = otherCharge - ((modelOtherChargesTemp[i].OctyAmount).toFloat()+(modelOtherChargesTemp[i].OctyTaxAmount).toFloat())
                                }else if (modelOtherChargesTemp[i].ID_TransType.equals("1")){
                                    // Credit
                                    otherCharge = otherCharge + ((modelOtherChargesTemp[i].OctyAmount).toFloat()+(modelOtherChargesTemp[i].OctyTaxAmount).toFloat())
                                }
                            }
                        }
                    }

                    tie_OtherCharges!!.setText(Config.changeTwoDecimel(otherCharge.toString()))
                    dialogOtherChargesSheet!!.dismiss()

                    if (ID_TransactionType.equals("2") || ID_TransactionType.equals("4")){

                        var othCharge = Config.changeTwoDecimel(otherCharge.toString())
                        var othChargeAfterDot = othCharge.substringAfter(".")
                        var othNetAmont = ""
                        var roundOffAmont = ""
                        othChargeAfterDot = (DecimalToWordsConverter.getDecimelFormate("."+othChargeAfterDot)).toString()
                        Log.e(TAG,"147331   "+othChargeAfterDot)
                        roundOffAmont  = (1-othChargeAfterDot.toDouble()).toString()
                        Log.e(TAG,"147332   "+roundOffAmont)


                        ////
                        Log.e(TAG,"14831   "+otherCharge)
                        val decimalPlaces = 0

                        if (othChargeAfterDot.toFloat() < .50){
                            roundOffAmont = othChargeAfterDot

                            if (roundOffAmont.toDouble() == 0.0){
                                tie_RoundOff!!.setText(""+DecimalToWordsConverter.getDecimelFormate(roundOffAmont))
                                othNetAmont = (BigDecimal(otherCharge.toDouble()).setScale(decimalPlaces, RoundingMode.DOWN)).toString()
                            }else{
                                tie_RoundOff!!.setText("-"+DecimalToWordsConverter.getDecimelFormate(roundOffAmont))
                                othNetAmont = (BigDecimal(otherCharge.toDouble()).setScale(decimalPlaces, RoundingMode.DOWN)).toString()
                            }

                            Log.e(TAG,"14832   "+othNetAmont)
                            Log.e(TAG,"14833   "+roundOffAmont)
                            // Log.e(TAG,"147332  -"+DecimalToWordsConverter.getDecimelFormate("."+othChargeAfterDot))
                            //othNetAmont = Config.changeTwoDecimel((othCharge.toFloat() - othChargeAfterDot.toFloat()).toString())
                        }else{
                            tie_RoundOff!!.setText(DecimalToWordsConverter.getDecimelFormate(roundOffAmont))
                            othNetAmont = (BigDecimal(otherCharge.toDouble()).setScale(decimalPlaces, RoundingMode.HALF_UP)).toString()
                            Log.e(TAG,"14834   "+othNetAmont)
                            // Log.e(TAG,"147333  +"+DecimalToWordsConverter.getDecimelFormate("."+othChargeAfterDot))
                            // othNetAmont = Config.changeTwoDecimel((othCharge.toFloat() + othChargeAfterDot.toFloat()).toString())
                        }
                        // othNetAmont = Config.changeTwoDecimel((othCharge.toFloat() + roundOffAmont.toFloat()).toString())
                        Log.e(TAG,"14835   "+othCharge+"  :  "+othChargeAfterDot+"  :  "+othNetAmont)

                        tie_NetAmount!!.setText(DecimalToWordsConverter.getDecimelFormate(othNetAmont))
                    }
                }else{
                    Config.snackBars(context,it,"Enter atleast one transactions")
                    Log.e(TAG,"163111   Enter atleast one transactions")
                }

            }


            dialogOtherChargesSheet!!.show()
            val window: Window? = dialogOtherChargesSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.white);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        }catch (e : Exception){

            Log.e(TAG,"3856  "+e)
        }
    }

    private fun hasCalulateMethod(modelOtherChargesTemp: ArrayList<ModelOtherChargesTemp>): Boolean {

        var result = false
        for (i in 0 until modelOtherChargesTemp.size) {  // iterate through the JsonArray
            if (modelOtherChargesTemp[i].isCalculate){
                result = true
            }
        }
        return result
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
                                           // modelOtherChargesTemp[otherChargeCalcPosition].isTaxCalculate = false
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

                                                modelOtherChargesTemp[otherChargeClickPosition].OctyTaxAmount = sumOfTax.toString()
                                                otherChargeAdapter!!.notifyItemChanged(otherChargeClickPosition)

                                                Log.e(TAG,"405521     "+sumOfTax)
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

                Log.e(TAG,"405522     "+sumOfTax)
                modelOtherChargesTemp[otherChargeClickPosition].OctyTaxAmount = sumOfTax.toString()
                modelOtherChargesTemp[otherChargeClickPosition].isTaxCalculate = true

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
        tie_TransactionType!!.addTextChangedListener(watcher)
        tie_Project!!.addTextChangedListener(watcher)
        tie_Date!!.addTextChangedListener(watcher)
        tie_Bill_Type!!.addTextChangedListener(watcher)
        tie_Petty_Cashier!!.addTextChangedListener(watcher)
        tie_Employee!!.addTextChangedListener(watcher)
        tie_RoundOff!!.addTextChangedListener(watcher)

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

                editable === tie_TransactionType!!.editableText -> {
                    Log.e(TAG,"283020    ")
                    if (tie_TransactionType!!.text.toString().equals("")){
                        til_TransactionType!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_TransactionType!!.isErrorEnabled = false
                        til_TransactionType!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }

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

                editable === tie_Bill_Type!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Bill_Type!!.text.toString().equals("")){
                        til_Bill_Type!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Bill_Type!!.isErrorEnabled = false
                        til_Bill_Type!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }

                editable === tie_Petty_Cashier!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Petty_Cashier!!.text.toString().equals("")){
                        til_Petty_Cashier!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Petty_Cashier!!.isErrorEnabled = false
                        til_Petty_Cashier!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }

                editable === tie_Employee!!.editableText -> {
                    Log.e(TAG,"283021    ")
                    if (tie_Employee!!.text.toString().equals("")){
                        til_Employee!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.color_mandatory)
                    }else{
                        til_Employee!!.isErrorEnabled = false
                        til_Employee!!.defaultHintTextColor = ContextCompat.getColorStateList(context,R.color.grey_dark)
                    }

                }

                editable === tie_RoundOff!!.editableText -> {
                    Log.e(TAG,"17891  tie_RoundOff  ")
                    if (ID_TransactionType.equals("2") || ID_TransactionType.equals("4")){

                        Log.e(TAG,"179444   "+tie_RoundOff!!.text.toString())

                        var othCharge = tie_OtherCharges!!.text.toString()
                        var roundCharge = tie_RoundOff!!.text.toString()

                        if (othCharge.equals("") || othCharge.equals(".")){
                            othCharge = "0.0"
                        }
                        if (roundCharge.equals("") || roundCharge.equals(".")){
                            roundCharge = "0.0"
                        }

                        tie_NetAmount!!.setText(DecimalToWordsConverter.getDecimelFormate((othCharge.toFloat()+roundCharge.toFloat()).toString()))
                    }else{
                        tie_NetAmount!!.setText("0.00")
                    }
                }

            }

        }
    }

    private fun getTransactionType() {

        var ReqMode = "127"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                projectTransactionTypeViewModel.getProjectTransactionType(this,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (transtypecount == 0){
                                    transtypecount++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   114455   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("TransactionTypeDetails")
                                        transTypeArrayList = jobjt.getJSONArray("TransactionTypeList")
                                        if (transTypeArrayList.length()>0){

                                            projectTransTypePopup(transTypeArrayList)

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

    private fun projectTransTypePopup(transTypeArrayList: JSONArray) {
        try {

            dialogTransType = Dialog(this)
            dialogTransType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogTransType!! .setContentView(R.layout.list_popup)
            dialogTransType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recylist = dialogTransType!! .findViewById(R.id.recylist) as RecyclerView
            tvv_list_name = dialogTransType!! .findViewById(R.id.tvv_list_name) as TextView
            val llsearch = dialogTransType!! .findViewById(R.id.llsearch) as LinearLayout
            val etsearch = dialogTransType!! .findViewById(R.id.etsearch) as EditText

            llsearch!!.visibility = View.GONE
            tvv_list_name!!.setText("Transaction Type")


            val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
            recylist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ProjectTransactionTypeAdapter(this@ProjectTransactionActivity, transTypeArrayList)
            recylist!!.adapter = adapter
            adapter.setClickListener(this@ProjectTransactionActivity)


            dialogTransType!!.show()
            dialogTransType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
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

//        val sdf = SimpleDateFormat("yyyy-MM-dd")
//        val mDate1 = sdf.parse(CreatedDate)
//        val timeInMilliseconds1 = mDate1.time
//        date_Picker1.minDate = timeInMilliseconds1
        date_Picker1.maxDate = System.currentTimeMillis()

//        val date = "2023-10-12"
//        val (year, month, day) = Config.getDateComponents(CreatedDate)
//        Log.e(TAG,"1528   "+"Year: $year, Month: $month, Day: $day")
//
//        val minDate = Calendar.getInstance()
//        minDate.set(2023, 10, 27)
//        date_Picker1.minDate = minDate.timeInMillis

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

        if (data.equals("TransTypeClick")){

            dialogTransType!!.dismiss()
            val jsonObject = transTypeArrayList.getJSONObject(position)

            ID_TransactionType = jsonObject.getString("FK_TransactionType")
            tie_TransactionType!!.setText(jsonObject.getString("TransactionTypename"))

            ID_Project = ""
            tie_Project!!.setText("")

            ID_Stage = ""
            tie_Stage!!.setText("")

            ID_BillType = ""
            tie_Bill_Type!!.setText("")

            ID_PettyCashier = ""
            tie_Petty_Cashier!!.setText("")

            ID_Employee = ""
            tie_Employee!!.setText("")

            tie_Remarks!!.setText("")
            tie_OtherCharges!!.setText("")

            hideShow(ID_TransactionType)
            setMandatoryField(ID_TransactionType)

            if (ID_TransactionType.equals("4") || ID_TransactionType.equals("0")) {
                hideShow("0")
            }
            else
            {
                payCount = 0
                getpaymentInfo2(ID_TransactionType,ID_Project,ID_Stage,ID_Employee,ID_BillType,ID_PaymentMethod,ID_PettyCashier)
            }

        }


        if (data.equals("projectClick")){

            dialogProject!!.dismiss()
            val jsonObject = projectSort.getJSONObject(position)

            ID_Project = jsonObject.getString("ID_Project")
            tie_Project!!.setText(jsonObject.getString("ProjName"))

            ID_Stage = ""
            tie_Stage!!.setText("")

            ID_PettyCashier = ""
            tie_Petty_Cashier!!.setText("")

            ID_Employee = ""
            tie_Employee!!.setText("")

            if (ID_TransactionType.equals("4") || ID_TransactionType.equals("0")) {
                hideShow("0")
            }
            else
            {
                payCount = 0
                getpaymentInfo2(ID_TransactionType,ID_Project,ID_Stage,ID_Employee,ID_BillType,ID_PaymentMethod,ID_PettyCashier)
            }

        }
        if (data.equals("stageCliik")){

            dialogStage!!.dismiss()
            val jsonObject = stageSort.getJSONObject(position)
            ID_Stage = jsonObject.getString("ProjectStagesID")
            tie_Stage!!.setText(jsonObject.getString("StageName"))


            ID_PettyCashier = ""
            tie_Petty_Cashier!!.setText("")

            ID_Employee = ""
            tie_Employee!!.setText("")

            if (ID_TransactionType.equals("4") || ID_TransactionType.equals("0")) {
                hideShow("0")
            }
            else
            {
                payCount = 0
                getpaymentInfo2(ID_TransactionType,ID_Project,ID_Stage,ID_Employee,ID_BillType,ID_PaymentMethod,ID_PettyCashier)
            }


        }

        if (data.equals("projectBillTypeClick")){

            dialogBilltType!!.dismiss()
            val jsonObject = billtTypeArrayList.getJSONObject(position)
            ID_BillType = jsonObject.getString("ID_BillType")
            tie_Bill_Type!!.setText(jsonObject.getString("BTName"))

            if (ID_TransactionType.equals("4") || ID_TransactionType.equals("0")) {
                hideShow("0")
            }
            else
            {
                payCount = 0
                getpaymentInfo2(ID_TransactionType,ID_Project,ID_Stage,ID_Employee,ID_BillType,ID_PaymentMethod,ID_PettyCashier)
            }


        }

        if (data.equals("PettycashierClick")){

            dialogPettyCashier!!.dismiss()
            val jsonObject = pettyCashierArrayList.getJSONObject(position)
            ID_PettyCashier = jsonObject.getString("ID_PettyCashier")
            tie_Petty_Cashier!!.setText(jsonObject.getString("PtyCshrName"))

            if (ID_TransactionType.equals("4") || ID_TransactionType.equals("0")) {
                hideShow("0")
            }
            else
            {
                payCount = 0
                getpaymentInfo2(ID_TransactionType,ID_Project,ID_Stage,ID_Employee,ID_BillType,ID_PaymentMethod,ID_PettyCashier)
            }


        }

        if (data.equals("ProjectEmployeeClick")){

            dialogProjectWiseEmployee!!.dismiss()
            val jsonObject = projectWiseEmployeeSort.getJSONObject(position)
            ID_Employee = jsonObject.getString("FK_Employee")
            tie_Employee!!.setText(jsonObject.getString("EmployeeName"))

            if (ID_TransactionType.equals("4") || ID_TransactionType.equals("0")) {
                hideShow("0")
            }
            else
            {
                payCount = 0
                getpaymentInfo2(ID_TransactionType,ID_Project,ID_Stage,ID_Employee,ID_BillType,ID_PaymentMethod,ID_PettyCashier)
            }

        }

        if (data.equals("IncludeTaxClick")){

            Log.e(TAG,"IncludeTaxClick  6733   "+ modelOtherChargesTemp[position].OctyIncludeTaxAmount)
//            if (modelOtherCharges[position].OctyIncludeTaxAmount){
////                FK_TaxGroup = modelOtherCharges[position].FK_TaxGroup
////                otherchargetaxcount = 0
////                getOtherChargeTax()
//            }else{
//
//            }
            otherChargeClickPosition = position
            FK_TaxGroup = modelOtherChargesTemp[position].FK_TaxGroup
            AmountTaxCalc = modelOtherChargesTemp[position].OctyAmount
            if (modelOtherChargesTemp[position].OctyIncludeTaxAmount){
                IncludeTaxCalc = "1"
            }else{
                IncludeTaxCalc = "0"
            }

            otherchargetaxcount = 0
            otherchargetaxMode = 1
            getOtherChargeTax()

        }

        if (data.equals("IncludeTaxAmountClick")){

            Log.e(TAG,"IncludeTaxAmountClick  8288   "+ modelOtherChargesTemp[position].OctyIncludeTaxAmount)
            otherChargeClickPosition = position
            FK_TaxGroup = modelOtherChargesTemp[position].FK_TaxGroup
            AmountTaxCalc = modelOtherChargesTemp[position].OctyAmount
            if (modelOtherChargesTemp[position].OctyIncludeTaxAmount){
                IncludeTaxCalc = "1"
            }else{
                IncludeTaxCalc = "0"
            }
            FK_TaxGroup = modelOtherChargesTemp[position].FK_TaxGroup
            otherchargetaxcount = 0
            otherchargetaxMode = 0
            getOtherChargeTax()
        }

        if (data.equals("TaxAmountClaculateClick")){

            Log.e(TAG,"TaxAmountClaculateClick  82881   "+ modelOtherChargesTemp[position].OctyIncludeTaxAmount)
            otherChargeClickPosition = position
            FK_TaxGroup = modelOtherChargesTemp[position].FK_TaxGroup
            AmountTaxCalc = modelOtherChargesTemp[position].OctyAmount
            if (modelOtherChargesTemp[position].OctyIncludeTaxAmount){
                IncludeTaxCalc = "1"
            }else{
                IncludeTaxCalc = "0"
            }
            FK_TaxGroup = modelOtherChargesTemp[position].FK_TaxGroup

            if (modelOtherChargesTemp[position].OctyIncludeTaxAmount || modelOtherChargesTemp[position].isTaxCalculate){
//                modelOtherChargesTemp[otherChargeClickPosition].OctyTaxAmount = sumOfTax.toString()
             //   modelOtherChargesTemp[position].isTaxCalculate = false
                otherChargeAdapter!!.notifyItemChanged(otherChargeClickPosition)
            }else{
                otherchargetaxcount = 0
                otherchargetaxMode = 1

                getOtherChargeTax()
            }


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

    private fun getpaymentInfo2(
        ID_TransactionType: String,
        ID_Project: String,
        ID_Stage: String,
        ID_Employee: String,
        ID_BillType: String,
        ID_PaymentMethod: String?,
        ID_PettyCashier: String?
    ) {

        var asOnDate = Common.getCurrentDateNTime("1")
        var ReqMode = "128"

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                paymentInfoViewModel.getPaymentInfo(this,ID_TransactionType,ID_Project!!,ID_Stage!!,ID_Employee!!,ID_BillType!!,
                    ID_PaymentMethod!!,ID_PettyCashier!!,asOnDate,ReqMode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (payCount == 0) {
                                    payCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   28022   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("PaymentInformation")
                                        paymentInfoList = jobjt.getJSONArray("PaymentInformationList")
                                        try {

                                            if (paymentInfoList.length()>0)
                                            {
                                                ll_paymentInfo!!.visibility=View.VISIBLE
                                                Log.e(TAG,"info 323232=="+paymentInfoList)

                                                val lLayout = GridLayoutManager(this@ProjectTransactionActivity, 1)
                                                recyle_paymentInfo!!.layoutManager = lLayout as RecyclerView.LayoutManager?

                                                paymentAdapter = PaymentInfoAdapter(this@ProjectTransactionActivity, paymentInfoList)
                                                recyle_paymentInfo!!.adapter = paymentAdapter

                                            }
                                            else
                                            {
                                                val builder = AlertDialog.Builder(
                                                    this@ProjectTransactionActivity,
                                                    R.style.MyDialogTheme
                                                )
                                                builder.setMessage("No Data Found")
                                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                                }
                                                val alertDialog: AlertDialog = builder.create()
                                                alertDialog.setCancelable(false)
                                                alertDialog.show()
                                            }

                                        }
                                        catch (e : Exception){

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


    private fun setMandatoryField(ID_TransactionType: String) {
        til_Project!!.isErrorEnabled = false
        if (ID_TransactionType.equals("0")){
            Log.e(TAG,"19522 0  ")
            til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.grey_dark)
            til_Project!!.setHint("Project")
        }

        else if (ID_TransactionType.equals("1")){
            Log.e(TAG,"19522 1  ")
            til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.grey_dark)
            til_Project!!.setHint("Project")
        }
        else if (ID_TransactionType.equals("2")){
            Log.e(TAG,"19522 2  ")
            til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
            til_Project!!.setHint("Project *")
        }
        else if (ID_TransactionType.equals("3")){
            Log.e(TAG,"19522  3 ")
            til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
            til_Project!!.setHint("Project *")
        }

        else if (ID_TransactionType.equals("4")){
            Log.e(TAG,"19522 4  ")
            til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.color_mandatory)
            til_Project!!.setHint("Project *")
        }

        else if (ID_TransactionType.equals("5")){
            Log.e(TAG,"19522 5  ")
            til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.grey_dark)
            til_Project!!.setHint("Project")
        }

        else if (ID_TransactionType.equals("6")){
            Log.e(TAG,"19522 6 ")
            til_Project!!.defaultHintTextColor   = ContextCompat.getColorStateList(this,R.color.grey_dark)
            til_Project!!.setHint("Project")
        }
    }

    private fun hideShow(ID_TransactionType: String) {

        til_Project!!.visibility = View.VISIBLE
        til_Stage!!.visibility = View.VISIBLE
        til_Employee!!.visibility = View.VISIBLE
        til_Bill_Type!!.visibility = View.VISIBLE
        til_RoundOff!!.visibility = View.VISIBLE
        til_NetAmount!!.visibility = View.VISIBLE
        til_Petty_Cashier!!.visibility = View.VISIBLE
        til_PaymentMethod!!.visibility = View.VISIBLE
       // ll_paymentInfo!!.visibility=View.VISIBLE

        if (ID_TransactionType.equals("0")){
            Log.e(TAG,"19533 0  ")
            til_NetAmount!!.visibility = View.GONE
            ll_paymentInfo!!.visibility=View.GONE
        }

        else if (ID_TransactionType.equals("1")){
            Log.e(TAG,"19533 1  ")
            til_RoundOff!!.visibility = View.GONE
            til_NetAmount!!.visibility = View.GONE

            tie_OtherCharges!!.isFocusableInTouchMode = true
            tie_OtherCharges!!.isEnabled = true
        }
        else if (ID_TransactionType.equals("2")){
            Log.e(TAG,"19533 2  ")
            til_Petty_Cashier!!.visibility = View.GONE
            til_PaymentMethod!!.visibility = View.GONE
            tie_OtherCharges!!.isFocusableInTouchMode = false
            tie_OtherCharges!!.isEnabled = true
        }
        else if (ID_TransactionType.equals("3")){
            Log.e(TAG,"19533  3 ")
            til_RoundOff!!.visibility = View.GONE
            til_NetAmount!!.visibility = View.GONE
            tie_OtherCharges!!.isFocusableInTouchMode = true
            tie_OtherCharges!!.isEnabled = true

        }

        else if (ID_TransactionType.equals("4")){
            Log.e(TAG,"19533 4  ")
            til_Petty_Cashier!!.visibility = View.GONE
            til_Employee!!.visibility = View.GONE
            tie_OtherCharges!!.isFocusableInTouchMode = false
            tie_OtherCharges!!.isEnabled = true
        }

        else if (ID_TransactionType.equals("5")){
            Log.e(TAG,"19533 5  ")
            til_Project!!.visibility = View.GONE
            til_Stage!!.visibility = View.GONE
            til_Employee!!.visibility = View.GONE
            til_Bill_Type!!.visibility = View.GONE
            til_RoundOff!!.visibility = View.GONE
            til_NetAmount!!.visibility = View.GONE
            tie_OtherCharges!!.isFocusableInTouchMode = true
            tie_OtherCharges!!.isEnabled = true
        }

        else if (ID_TransactionType.equals("6")){
            Log.e(TAG,"19533 6 ")
            til_Project!!.visibility = View.GONE
            til_Stage!!.visibility = View.GONE
            til_Employee!!.visibility = View.GONE
            til_Bill_Type!!.visibility = View.GONE
            til_RoundOff!!.visibility = View.GONE
            til_NetAmount!!.visibility = View.GONE
            tie_OtherCharges!!.isFocusableInTouchMode = true
            tie_OtherCharges!!.isEnabled = true
        }


    }


}
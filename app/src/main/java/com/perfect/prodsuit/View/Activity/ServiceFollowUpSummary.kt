package com.perfect.prodsuit.View.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.PaymentMethodModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.PaymentMethodAdapter
import org.json.JSONArray
import java.text.DecimalFormat


class ServiceFollowUpSummary : AppCompatActivity(), View.OnClickListener, ItemClickListener {
    lateinit var actv_action: AutoCompleteTextView
    lateinit var actv_bankType: AutoCompleteTextView
    lateinit var at_method: AutoCompleteTextView
    private var tv_alert_diss: TextView? = null
    private var tv_alert: TextView? = null
    private var tv_netAmount: TextView? = null
    private var tv_netAmount2: TextView? = null
    private var tv_productAmount: TextView? = null
    private var tv_totalAmount: TextView? = null
    private var tv_serviceAmount: TextView? = null
    private var tv_buyBackAmount: TextView? = null
    private var tv_attended: TextView? = null
    private var tv_DateClick: TextInputEditText? = null
    private var act_reff: TextInputEditText? = null
    private var act_amount: TextInputEditText? = null
    private var text_payment_method: TextView? = null
    private var lin_recycler: LinearLayout? = null
    private var edtServiceAmount: EditText? = null
    private var edtCustomerNote: EditText? = null
    private var edtBuyBackAmount: EditText? = null
    private var edtEmployeeNote: EditText? = null
    private var edtProductAmount: EditText? = null
    private var recycler: RecyclerView? = null
    private var edtDiscountAmount: TextInputEditText? = null
    private var imback: ImageView? = null
    var jsonArrayServiceCost: JSONArray? = null
    var jsonArrayPaymentMethod: JSONArray = JSONArray()
    var jsonArrayreplacedProductCost: JSONArray? = null
    var jsonArrayAttendance: JSONArray? = null
    var strDate: String = ""
    var paymentMethod: String = ""
    var netAmount = 0f
    val paymentMethodArray = ArrayList<PaymentMethodModel>()
    var isEdit:Boolean=false
    var editPosition:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_service_follow_up_summary)
        val intent: Intent = intent
        val arrayServiceCost: String? = intent.getStringExtra("jsonArrayServiceCost")
        val arrayreplacedProductCost: String? =
            intent.getStringExtra("jsonArrayreplacedProductCost")
        val arrayAttendance: String? = intent.getStringExtra("jsonArrayAttendance")
        jsonArrayServiceCost = JSONArray(arrayServiceCost)
        jsonArrayreplacedProductCost = JSONArray(arrayreplacedProductCost)
        jsonArrayAttendance = JSONArray(arrayAttendance)
        setId()
        setListners()
        LoadData()
    }

    private fun LoadData() {
        var name=""
        for (k in 0 until jsonArrayAttendance!!.length()) {
            val jsonObject = jsonArrayAttendance!!.getJSONObject(k)
            val name1 = jsonObject.getString("EmployeeName")
            name+=","+name1
        }
        tv_attended!!.setText(name.substring(1,name.length))
        val df = DecimalFormat("#.##")
        var serviceCostSum: Float = 0F
        for (k in 0 until jsonArrayServiceCost!!.length()) {
            val jsonObject = jsonArrayServiceCost!!.getJSONObject(k)
            val serviceCost = jsonObject.getString("serviceCost").toFloat()
            Log.v("sdfsdffd33fdf", "serviceCost " + serviceCost)
            serviceCostSum += serviceCost
        }
        tv_serviceAmount?.text = "\u20b9" + df.format(serviceCostSum)

        var buyBackAmountSum: Float = 0F
        for (k in 0 until jsonArrayreplacedProductCost!!.length()) {
            val jsonObject = jsonArrayreplacedProductCost!!.getJSONObject(k)
            val buyBackAmount = jsonObject.getString("buyBackAmount").toFloat()
            Log.v("sdfsdffd33fdf", "serviceCost " + buyBackAmount)
            buyBackAmountSum += buyBackAmount
        }
        tv_buyBackAmount?.text = "\u20b9" + df.format(buyBackAmountSum)

        var amountSum: Float = 0F
        for (k in 0 until jsonArrayreplacedProductCost!!.length()) {
            val jsonObject = jsonArrayreplacedProductCost!!.getJSONObject(k)
            val amount = jsonObject.getString("amount").toFloat()
            val quantity = jsonObject.getString("quantity").toFloat()
            var totalAmount = amount * quantity
            Log.v("sdfsdffd33fdf", "serviceCost " + amount)
            amountSum += totalAmount
        }
        tv_productAmount?.text = "\u20b9" + df.format(amountSum)
        netAmount = (serviceCostSum + amountSum) - buyBackAmountSum
        tv_netAmount?.text = "\u20b9" + df.format(buyBackAmountSum)
        tv_totalAmount?.text = df.format(buyBackAmountSum)
    }

    private fun setId() {
        tv_attended = findViewById(R.id.tv_attended)
        tv_totalAmount = findViewById(R.id.tv_totalAmount)
        tv_netAmount = findViewById(R.id.tv_netAmount)
        tv_productAmount = findViewById(R.id.tv_productAmount)
        tv_buyBackAmount = findViewById(R.id.tv_buyBackAmount)
        tv_serviceAmount = findViewById(R.id.tv_serviceAmount)
        imback = findViewById(R.id.imback)
        actv_action = findViewById(R.id.actv_action)
        actv_bankType = findViewById(R.id.actv_bankType)
        tv_alert_diss = findViewById<TextView>(R.id.tv_alert_diss)
        edtDiscountAmount = findViewById(R.id.edtDiscountAmount)
        tv_DateClick = findViewById(R.id.tv_DateClick)
        text_payment_method = findViewById(R.id.text_payment_method)
        detailsShowingStatus(actv_action)
        showBankType(actv_bankType)
    }

    private fun setListners() {
        text_payment_method!!.setOnClickListener(this)
        tv_DateClick!!.setOnClickListener(this)
        imback!!.setOnClickListener(this)
        edtDiscountAmount!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                try {
                    val df = DecimalFormat("#.##")
                    var dis = s.toString().toFloat()
                    var total = tv_totalAmount?.text.toString().toFloat()
                    netAmount = total - dis
                    tv_netAmount?.setText("\u20b9" + df.format(netAmount))
                    if (netAmount.toString().toFloat() < 0F) {
                        tv_alert_diss?.visibility = View.VISIBLE
                    } else {
                        tv_alert_diss?.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    Log.v("dsfsdfddd", "err " + e)
                }
            }
        })
    }

    private fun detailsShowingStatus(actvAction: AutoCompleteTextView?) {
        val searchType = arrayOf<String>(
            "Please Choose Action",
            "On-Hold with Stand By",
            "On-Hold",
            "Pick Up Request",
            "Completed",
            "Replace Request"
        )
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, searchType)
        actvAction!!.setAdapter(adapter)
        actvAction!!.setText(searchType.get(0), false)
        actvAction!!.setOnClickListener {
            actvAction!!.showDropDown()
        }
        actvAction!!.setOnItemClickListener { parent, view, position, id ->
            //type = position

        }
    }

    private fun showBankType(actvBank: AutoCompleteTextView?) {
        val searchType = arrayOf<String>(
            "Please Select",
            "Service Charges"
        )
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, searchType)
        actvBank!!.setAdapter(adapter)
        actvBank!!.setText(searchType.get(0), false)
        actvBank!!.setOnClickListener {
            actvBank!!.showDropDown()
        }
        actvBank!!.setOnItemClickListener { parent, view, position, id ->
            //type = position

        }
    }

    private fun showMethod(actvBank: AutoCompleteTextView?) {
        paymentMethod = ""
        val searchType = arrayOf<String>(
            "Please Select",
            "Credit",
            "Phonepe",
            "Debit Card",
            "Cash",
            "Net Banking",
            "Pay"
        )
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, searchType)
        actvBank!!.setAdapter(adapter)
        actvBank!!.setText(searchType.get(0), false)
        actvBank!!.setOnClickListener {
            actvBank!!.showDropDown()
        }
        actvBank!!.setOnItemClickListener { parent, view, position, id ->
            paymentMethod = searchType[position]

        }
    }

    private fun validateData() {
        val date = tv_DateClick?.text.toString()
        val serviceAmount = edtServiceAmount?.text.toString()
        val action = actv_action?.text.toString()
        val customerNote = edtCustomerNote?.text.toString()
        val buyBackAmount = edtBuyBackAmount?.text.toString()
        val discountAmount = edtDiscountAmount?.text.toString()
        val employeeNote = edtEmployeeNote?.text.toString()
        val productAmount = edtProductAmount?.text.toString()
        if (action == "Please Choose Action") {
            // Config.snackBars(this, v, "Please Choose Action")
        } else {

        }
    }

    override fun onClick(p0: View) {
        when (p0?.id) {
            R.id.tv_DateClick -> {
                openBottomSheet()
            }
            R.id.imback -> {
                onBackPressed()
            }
            R.id.text_payment_method -> {
                openBottomSheetPaymentMethod(p0)
            }

        }
    }

    private fun openBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_date, null)
        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)
        date_Picker1.maxDate = System.currentTimeMillis()
        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
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
                tv_DateClick!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                strDate = strYear + "-" + strMonth + "-" + strDay
            } catch (e: Exception) {

            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun openBottomSheetPaymentMethod(p0: View) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_payment_method, null)
        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        recycler = view.findViewById<RecyclerView>(R.id.recycler)
        act_reff = view.findViewById<TextInputEditText>(R.id.act_reff)
        act_amount = view.findViewById<TextInputEditText>(R.id.act_amount)
        lin_recycler = view.findViewById<LinearLayout>(R.id.lin_recycler)
        val img_add = view.findViewById<ImageView>(R.id.img_add)
        tv_netAmount2 = view.findViewById<TextView>(R.id.tv_netAmount)
        val img_refresh = view.findViewById<ImageView>(R.id.img_refresh)
        tv_alert = view.findViewById<TextView>(R.id.tv_alert)
        at_method = view.findViewById<AutoCompleteTextView>(R.id.at_method)
        showMethod(at_method)
        tv_netAmount2!!.text = "" + netAmount
        loadRecycler()
        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            var netAmount=tv_netAmount2!!.text.toString().toFloat()
            if (netAmount!= 0F) {
                tv_alert!!.startAnimation(shakeError());
            } else {

            }
        }
        img_add.setOnClickListener(View.OnClickListener {
            Config.Utils.hideSoftKeyBoard(this, it)
            if (paymentMethod == "" || paymentMethod == "Please Select") {
                Toast.makeText(this, "Please choose payment method", Toast.LENGTH_SHORT).show()
            } else if (act_amount!!.text.toString() == "") {
                Toast.makeText(this, "Please add amount", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    var present = 0
                    var position=0
                    for (k in 0 until jsonArrayPaymentMethod!!.length()) {
                        val jsonObject = jsonArrayPaymentMethod!!.getJSONObject(k)
                        var method = jsonObject.getString("method")
                        if (method == paymentMethod) {
                            present = 1
                            position=k
                            break
                        } else {
                            present = 0
                        }
                    }
                    if(present==0||(isEdit&&present==1&&editPosition==position))
                    {
                        if(isEdit)
                        {
                            isEdit=false
                            var size = paymentMethodArray.size.toInt() + 1
                            val a1 =
                                PaymentMethodModel(
                                    "" + size,
                                    paymentMethod,
                                    "" + act_reff!!.text,
                                    "" + act_amount!!.text
                                )
                            paymentMethodArray.removeAt(editPosition)
                            jsonArrayPaymentMethod.remove(editPosition)
                            paymentMethodArray.add(editPosition,a1)
                        }
                        else {
                            var size = paymentMethodArray.size.toInt() + 1
                            val a1 =
                                PaymentMethodModel(
                                    "" + size,
                                    paymentMethod,
                                    "" + act_reff!!.text,
                                    "" + act_amount!!.text
                                )
                            paymentMethodArray.add(a1)
                        }

                        val gson1 = Gson()
                        val listString1 = gson1.toJson(
                            paymentMethodArray,
                            object : TypeToken<ArrayList<PaymentMethodModel?>?>() {}.type
                        )
                        jsonArrayPaymentMethod = JSONArray(listString1)
                        showMethod(at_method)
                        act_reff!!.setText("")
                        act_amount!!.setText("")
                        Log.v(
                            "adsdss33dd",
                            "jsonArrayPaymentMethod " + jsonArrayPaymentMethod.toString()
                        )
                        lin_recycler!!.visibility = View.VISIBLE
                        recycler!!.setLayoutManager(
                            LinearLayoutManager(
                                this,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        )
                        var totalAmount = 0f
                        for (k in 0 until jsonArrayPaymentMethod!!.length()) {
                            val jsonObject = jsonArrayPaymentMethod!!.getJSONObject(k)
                            var amount = jsonObject.getString("amount").toFloat()
                            totalAmount = totalAmount + amount
                        }
                        var finalAmount = netAmount - totalAmount
                        tv_netAmount2!!.text = "" + finalAmount
                        if (finalAmount.toString().toFloat() != 0F) {
                            tv_alert!!.visibility = View.VISIBLE
                        } else {
                            tv_alert!!.visibility = View.GONE
                        }
                        var adapterReplacedProductCost =
                            PaymentMethodAdapter(this, jsonArrayPaymentMethod!!)
                        recycler!!.adapter = adapterReplacedProductCost
                        adapterReplacedProductCost.addItemClickListener(this)
                    }
                    else
                    {
                        Toast.makeText(this, "Payment method already exits", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                }

            }
        })
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun loadRecycler() {
        if (jsonArrayPaymentMethod.length() != 0) {
            Log.v("dfdsf43rff","size  "+jsonArrayPaymentMethod.length())
            lin_recycler!!.visibility = View.VISIBLE
            var totalAmount = 0f
            for (k in 0 until jsonArrayPaymentMethod!!.length()) {
                val jsonObject = jsonArrayPaymentMethod!!.getJSONObject(k)
                var amount = jsonObject.getString("amount").toFloat()
                totalAmount = totalAmount + amount
            }
            var finalAmount = netAmount - totalAmount
            tv_netAmount2!!.text = "" + finalAmount
            if (finalAmount.toString().toFloat() != 0F) {
                tv_alert!!.visibility = View.VISIBLE
            } else {
                tv_alert!!.visibility = View.GONE
            }
            Log.v("dfdfdfd33fd", "amount " + totalAmount)
            recycler!!.setLayoutManager(
                LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            )
            var adapterReplacedProductCost =
                PaymentMethodAdapter(this, jsonArrayPaymentMethod!!)
            recycler!!.adapter = adapterReplacedProductCost
            adapterReplacedProductCost.addItemClickListener(this)
        }
        else
        {
            lin_recycler!!.visibility = View.GONE
            tv_netAmount2!!.text = "" + netAmount
            if (netAmount.toString().toFloat() != 0F) {
                tv_alert!!.visibility = View.VISIBLE
            } else {
                tv_alert!!.visibility = View.GONE
            }
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("delete")) {
            Log.v("dfdsf43rff","pos "+position)
            jsonArrayPaymentMethod.remove(position)
            paymentMethodArray.removeAt(position)
            showMethod(at_method)
            act_reff!!.setText("")
            act_amount!!.setText("")
            loadRecycler()
        }
        if (data.equals("edit")) {
            isEdit=true
            editPosition=position
            Log.v("dfdsf43rff","pos "+position)
            var jsonObject=jsonArrayPaymentMethod.getJSONObject(position)
            var method=jsonObject.get("method")
            var amount=jsonObject.get("amount").toString()
            var reff=jsonObject.get("reff").toString()
            act_amount!!.setText(amount)
            act_reff!!.setText(reff)
            Log.v("dfdsf43rff","method "+method)
            Log.v("dfdsf43rff","amount "+amount)
            Log.v("dfdsf43rff","reff "+reff)
            paymentMethod=method.toString()
            val searchType = arrayOf<String>(
                "Please Select",
                "Credit",
                "Phonepe",
                "Debit Card",
                "Cash",
                "Net Banking",
                "Pay"
            )
            val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, searchType)
            at_method!!.setAdapter(adapter)
            at_method!!.setText(method.toString(), false)
            at_method!!.setOnClickListener {
                at_method!!.showDropDown()
            }
            at_method!!.setOnItemClickListener { parent, view, position, id ->
                paymentMethod = searchType[position]

            }
        }
    }

    fun shakeError(): TranslateAnimation? {
        val shake = TranslateAnimation(0F, 10F, 0F, 0F)
        shake.setDuration(500)
        shake.setInterpolator(CycleInterpolator(7F))
        return shake
    }
}
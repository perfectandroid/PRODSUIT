package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.ClickListener
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.HomeGridAdapter
import com.perfect.prodsuit.View.Adapter.ProdInformationAdapter
import com.perfect.prodsuit.Viewmodel.PaymentMethodViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PickUpAndDeliveryUpdateActivity : AppCompatActivity(), View.OnClickListener,
    ItemClickListener {

    var TAG  ="PickUpAndDeliveryUpdateActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    private var tv_header: TextView? = null
    private var SubMode:String?=""

    private var tv_TicketDetailsClick: TextView? = null
    private var tv_CustomerDetailsClick: TextView? = null
    private var tv_ProductDetailsClick: TextView? = null
    private var tv_PickDeliveryInformationClick: TextView? = null
    private var tv_ProductInformationClick: TextView? = null

    private var llTicketDetails: LinearLayout? = null
    private var llCustomerDetails: LinearLayout? = null
    private var llProductDetails: LinearLayout? = null
    private var llPickDeliveryInformation: LinearLayout? = null

    var TicketDetailsMode: String? = "1"
    var CustomerDetailsMode: String? = "1"
    var ProductDetailsMode: String? = "1"
    var PickDeliveryInformationMode: String? = "1"
    var ProductInformationMode: String? = "1"


    private var til_PickDeliveryDate: TextInputLayout? = null
    private var til_PickDeliveryTime: TextInputLayout? = null

    private var tie_PickDeliveryDate: TextInputEditText? = null
    private var tie_PickDeliveryTime: TextInputEditText? = null
    private var tie_Remark: TextInputEditText? = null

    private var PRODUCT_INFORM: Int? = 100

    var prodInformationCount = 0
    lateinit var ProdInformationViewModel: PaymentMethodViewModel
    lateinit var prodInformationArrayList: JSONArray
    private var dialogProdInformation: Dialog? = null
    var recyProdInformation: RecyclerView? = null
    var tv_Pop_StandByTotal: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pick_up_and_delivery_update)
        context = this@PickUpAndDeliveryUpdateActivity

        if (getIntent().hasExtra("SubMode")) {
            SubMode = intent.getStringExtra("SubMode")
        }
        setRegViews()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_header = findViewById(R.id.tv_header)
        tv_TicketDetailsClick = findViewById<TextView>(R.id.tv_TicketDetailsClick)
        tv_CustomerDetailsClick = findViewById<TextView>(R.id.tv_CustomerDetailsClick)
        tv_ProductDetailsClick = findViewById<TextView>(R.id.tv_ProductDetailsClick)
        tv_PickDeliveryInformationClick = findViewById<TextView>(R.id.tv_PickDeliveryInformationClick)
        tv_ProductInformationClick = findViewById<TextView>(R.id.tv_ProductInformationClick)

        llTicketDetails = findViewById<LinearLayout>(R.id.llTicketDetails)
        llCustomerDetails = findViewById<LinearLayout>(R.id.llCustomerDetails)
        llProductDetails = findViewById<LinearLayout>(R.id.llProductDetails)
        llPickDeliveryInformation = findViewById<LinearLayout>(R.id.llPickDeliveryInformation)

        til_PickDeliveryDate = findViewById<TextInputLayout>(R.id.til_PickDeliveryDate)
        til_PickDeliveryTime = findViewById<TextInputLayout>(R.id.til_PickDeliveryTime)

        tie_PickDeliveryDate = findViewById<TextInputEditText>(R.id.tie_PickDeliveryDate)
        tie_PickDeliveryTime = findViewById<TextInputEditText>(R.id.tie_PickDeliveryTime)
        tie_Remark = findViewById<TextInputEditText>(R.id.tie_Remark)

        tv_TicketDetailsClick!!.setOnClickListener(this)
        tv_CustomerDetailsClick!!.setOnClickListener(this)
        tv_ProductDetailsClick!!.setOnClickListener(this)
        tv_PickDeliveryInformationClick!!.setOnClickListener(this)
        tv_ProductInformationClick!!.setOnClickListener(this)

        tie_PickDeliveryDate!!.setOnClickListener(this)
        tie_PickDeliveryTime!!.setOnClickListener(this)

        setLabel()
       // onTextChangedValues()
        getCurrentDate()
        getDetailList()

    }



    private fun setLabel() {

        if (SubMode.equals("1")){
            tv_header!!.text = "Pick up"
            tv_PickDeliveryInformationClick!!.text = "Pickup Information"
            til_PickDeliveryDate!!.hint = "Pick Up Date *"
            til_PickDeliveryTime!!.hint = "Pick up Time *"

        }
        if (SubMode.equals("2")){
            tv_header!!.text = "Delivery"
            tv_PickDeliveryInformationClick!!.text = "Delivery Information"
            til_PickDeliveryDate!!.hint = "Delivery Date *"
            til_PickDeliveryTime!!.hint = "Delivery Time *"
        }
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tv_TicketDetailsClick->{
                llTicketDetails!!.visibility = View.VISIBLE
                TicketDetailsMode       = "0"
                CustomerDetailsMode     = "1"
                ProductDetailsMode      = "1"
                PickDeliveryInformationMode      = "1"
                ProductInformationMode  = "1"
                hideViews()
            }
            R.id.tv_CustomerDetailsClick->{
                llCustomerDetails!!.visibility = View.VISIBLE
                TicketDetailsMode       = "1"
                CustomerDetailsMode     = "0"
                ProductDetailsMode      = "1"
                PickDeliveryInformationMode      = "1"
                ProductInformationMode  = "1"
                hideViews()
            }
            R.id.tv_ProductDetailsClick->{
                llProductDetails!!.visibility = View.VISIBLE
                TicketDetailsMode       = "1"
                CustomerDetailsMode     = "1"
                ProductDetailsMode      = "0"
                PickDeliveryInformationMode      = "1"
                ProductInformationMode  = "1"
                hideViews()
            }
            R.id.tv_PickDeliveryInformationClick->{
                llPickDeliveryInformation!!.visibility = View.VISIBLE
                TicketDetailsMode       = "1"
                CustomerDetailsMode     = "1"
                ProductDetailsMode      = "1"
                PickDeliveryInformationMode      = "0"
                ProductInformationMode  = "1"
                hideViews()
            }
            R.id.tv_ProductInformationClick->{
                TicketDetailsMode       = "1"
                CustomerDetailsMode     = "1"
                ProductDetailsMode      = "1"
                PickDeliveryInformationMode      = "1"
                ProductInformationMode  = "0"
                hideViews()

//                val intent = Intent(this@PickUpAndDeliveryUpdateActivity, ProductInformationActivity::class.java)
//                startActivityForResult(intent, PRODUCT_INFORM!!);

                ProductInformationBottom(v)

            }

            R.id.tie_PickDeliveryDate->{
                Config.disableClick(v)
               // dateMode = 0
                openBottomDate()
            }
            R.id.tie_PickDeliveryTime->{
                Config.disableClick(v)
               // timeMode = 0
                openBottomTime()
            }

        }
    }


    private fun hideViews() {
        if (TicketDetailsMode.equals("1")) {
            llTicketDetails!!.visibility = View.GONE
        }
        if (CustomerDetailsMode.equals("1")) {
            llCustomerDetails!!.visibility = View.GONE
        }
        if (ProductDetailsMode.equals("1")) {
            llProductDetails!!.visibility = View.GONE
        }
        if (PickDeliveryInformationMode.equals("1")) {
            llPickDeliveryInformation!!.visibility = View.GONE
        }
    }

    private fun getCurrentDate() {


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


            tie_PickDeliveryDate!!.setText(""+sdfDate1.format(newDate))
            tie_PickDeliveryTime!!.setText(""+sdfTime1.format(newDate))

        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }

    private fun getDetailList() {
        var gridList = Config.getHomeGrid(this@PickUpAndDeliveryUpdateActivity)
        Log.e(TAG,"ActionType   44  "+gridList)
        val jObject = JSONObject(gridList)
        val jobjt = jObject.getJSONObject("homeGridType")
        val tempArrayList = jobjt.getJSONArray("homeGridDetails")

        prodInformationArrayList  = JSONArray()
        for (i in 0 until tempArrayList.length()) {
            val jsonObject = tempArrayList.getJSONObject(i)
            val jObject = JSONObject()
            jObject.put("isSelected","0")
            jObject.put("prodName",jsonObject.getString("grid_name"))
            jObject.put("prodQuantity","")
            jObject.put("isStatndBy","0")
            jObject.put("standByProduct","")
            jObject.put("standByQuantity","")
            jObject.put("standByAmount","")
            jObject.put("remarks","")
            prodInformationArrayList!!.put(jObject)
        }

      //  prodInformationArrayList = jobjt.getJSONArray("homeGridDetails")
    }

    private fun openBottomDate() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

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

                tie_PickDeliveryDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)


            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun openBottomTime() {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_timer, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val time_Picker1 = view.findViewById<TimePicker>(R.id.time_Picker1)
        //   time_Picker1!!.currentMinute = System.currentTimeMillis() - 1000


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {


                val hr = time_Picker1!!.hour
                val min = time_Picker1!!.minute
                val input = ""+hr+":"+min
                val inputDateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
                val outputDateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
                val date: Date = inputDateFormat.parse(input)
                val output = outputDateFormat.format(date)

                tie_PickDeliveryTime!!.setText(output)

            }
            catch (e: Exception){
                //   Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun ProductInformationBottom(v : View) {
        try {

            dialogProdInformation = Dialog(this)
            dialogProdInformation!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogProdInformation!! .setContentView(R.layout.product_information_sheet)
            dialogProdInformation!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogProdInformation!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            recyProdInformation = dialogProdInformation!! .findViewById(R.id.recyProdInformation) as RecyclerView
            tv_Pop_StandByTotal = dialogProdInformation!! .findViewById(R.id.tv_Pop_StandByTotal) as TextView


//            var gridList = Config.getHomeGrid(this@PickUpAndDeliveryUpdateActivity)
//            Log.e(TAG,"ActionType   44  "+gridList)
//            val jObject = JSONObject(gridList)
//            val jobjt = jObject.getJSONObject("homeGridType")
//            val tempArrayList = jobjt.getJSONArray("homeGridDetails")
//
//
//            prodInformationArrayList = jobjt.getJSONArray("homeGridDetails")

            Log.e(TAG,"prodInformationArrayList   396   "+prodInformationArrayList)

            if (prodInformationArrayList.length()>0){
                val lLayout = GridLayoutManager(this@PickUpAndDeliveryUpdateActivity, 1)
                recyProdInformation!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                val adapterHome = ProdInformationAdapter(this@PickUpAndDeliveryUpdateActivity, prodInformationArrayList)
                recyProdInformation!!.adapter = adapterHome
                adapterHome.setClickListener(this@PickUpAndDeliveryUpdateActivity)


                setAmount()
            }


            dialogProdInformation!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onClick(position: Int, data: String) {
        Log.e(TAG,"123456  "+data)
        if (data.equals("changeAmount")){

            setAmount()

        }
    }

    private fun setAmount() {
        Log.e(TAG,"1234561 prodInformationArrayList  "+prodInformationArrayList)
        var standbyTotal ="0.00"
        for (i in 0 until prodInformationArrayList.length()) {
            val jsonObject = prodInformationArrayList.getJSONObject(i)
            if (jsonObject.getString("isSelected").equals("1")){
                // Selected
                Log.e(TAG,"42101 prodInformationArrayList  "+i)
                var standbyAmount = "0.00"
                if (jsonObject.getString("standByAmount").equals(".")){
                    standbyAmount = "0.00"
                }
                else if (!jsonObject.getString("standByAmount").equals("")){
                    standbyAmount = jsonObject.getString("standByAmount")
                }
//                    Log.e(TAG,"standbyTotal  42102   "+standbyTotal+"   :   "+standbyAmount +"  :  "+jsonObject.getString("prodName"))
//                    Log.e(TAG,"standbyTotal  42103   "+DecimelFormatters.set2DecimelPlace(standbyTotal.toFloat())+"   :   "+DecimelFormatters.set2DecimelPlace(standbyAmount.toFloat()))
                standbyTotal = (DecimelFormatters.set2DecimelPlace(standbyTotal.toFloat()+standbyAmount.toFloat()))

                tv_Pop_StandByTotal!!.text = standbyTotal

            }

        }
        Log.e(TAG,"standbyTotal  4313   "+standbyTotal)


//            txtPayBalAmount!!.setText(""+DecimelFormatters.set2DecimelPlace((tv_NetAmount!!.text.toString().toFloat()) - pay.toFloat()))

//                    && jsonObject.getString("ID_BranchType").equals("")
    }

}
package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.EmiListAdapter
import com.perfect.prodsuit.Viewmodel.EmiListViewModel
import com.perfect.prodsuit.Viewmodel.ProductCategoryViewModel
import org.json.JSONArray
import org.json.JSONObject

class EmiToDoListActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val TAG: String = "EmiToDoListActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var emiListViewModel: EmiListViewModel
    lateinit var emiListArrayList: JSONArray
    lateinit var emiListSort: JSONArray
    var recyEmiList: RecyclerView? = null
    var ll_nodata: LinearLayout? = null

    private var imgv_filter: ImageView? = null
    private var tv_header: TextView? = null
    private var tv_listCount: TextView?          = null
    private var txtReset: TextView? = null
    private var txtSearch: TextView? = null
    private var tie_Customer: TextInputEditText? = null
    private var tie_Mobile: TextInputEditText? = null
    private var tie_Area: TextInputEditText? = null
    private var tie_Due_Amount: TextInputEditText? = null

    var strCustomer = ""
    var strMobile = ""
    var strArea = ""
    var strDueAmount = ""
    var emiCount = 0
    var onRestartCount = 0
    var filterCount = 0

    private var ID_FinancePlanType:String?=""
    private var AsOnDate:String?=""
    private var ID_Category:String?=""
    private var ID_Area:String?=""
    private var Demand:String?="30"

    private var ReqMode:String?=""
    private var SubMode:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_emi_to_do_list)
        context = this@EmiToDoListActivity
        emiListViewModel = ViewModelProvider(this).get(EmiListViewModel::class.java)
        emiListViewModel = ViewModelProvider(this).get(EmiListViewModel::class.java)


        setRegViews()

        if (getIntent().hasExtra("SubMode")) {
            SubMode = intent.getStringExtra("SubMode")
        }

        if (getIntent().hasExtra("ID_FinancePlanType")) {
            ID_FinancePlanType = intent.getStringExtra("ID_FinancePlanType")
        }
        if (getIntent().hasExtra("AsOnDate")) {
            AsOnDate = intent.getStringExtra("AsOnDate")
            Log.e(TAG,"7777777    "+AsOnDate)
        }
        if (getIntent().hasExtra("ID_Category")) {
            ID_Category = intent.getStringExtra("ID_Category")
            Log.e(TAG,"7777777    "+ID_Category)
        }
        if (getIntent().hasExtra("ID_Area")) {
            ID_Area = intent.getStringExtra("ID_Area")
            Log.e(TAG,"7777777    "+ID_Area)
        }

        if (getIntent().hasExtra("Demand")) {
            Demand = intent.getStringExtra("Demand")
            Log.e(TAG,"7777777    "+Demand)
        }

        setHeader()
        emiCount = 0
        getEmiList()
    }

    private fun setHeader() {
        if (SubMode.equals("1")){
            tv_header!!.text = "To-Do"
        }
        if (SubMode.equals("2")){
            tv_header!!.text = "Over Due"
        }
        if (SubMode.equals("3")){
            tv_header!!.text = "Demand"
        }
    }


    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tv_header = findViewById<TextView>(R.id.tv_header)
        recyEmiList = findViewById<RecyclerView>(R.id.recyEmiList)
        ll_nodata = findViewById<LinearLayout>(R.id.ll_nodata)
        tv_listCount = findViewById(R.id.tv_listCount)

        imgv_filter = findViewById<ImageView>(R.id.imgv_filter)
        imgv_filter!!.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.imgv_filter->{
                Config.disableClick(v)
                filterBottomSheet()
            }

        }
    }

    private fun getEmiList() {
        ReqMode = "100"

        recyEmiList!!.adapter = null
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                emiListViewModel.getEmiList(this,ReqMode!!,SubMode!!,ID_FinancePlanType!!,AsOnDate!!,ID_Category!!,ID_Area!!,Demand!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (emiCount == 0) {
                                    emiCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   1530   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EMICollectionReport")
                                        emiListArrayList = jobjt.getJSONArray("EMICollectionReportList")
                                        if (emiListArrayList.length() > 0) {

                                            emiListSort = JSONArray()
                                            for (k in 0 until emiListArrayList.length()) {
                                                val jsonObject = emiListArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                emiListSort.put(jsonObject)
                                            }

                                            tv_listCount!!.setText(""+emiListSort.length())

                                            if (filterCount == 1 && onRestartCount == 1){

                                                strCustomer = tie_Customer!!.text.toString().toLowerCase().trim()
                                                strMobile = tie_Mobile!!.text.toString().toLowerCase().trim()
                                                strArea = tie_Area!!.text.toString().toLowerCase().trim()
                                                strDueAmount = tie_Due_Amount!!.text.toString().toLowerCase().trim()



                                                emiListSort = JSONArray()

                                                for (k in 0 until emiListArrayList.length()) {
                                                    val jsonObject = emiListArrayList.getJSONObject(k)
                                                    //  if (textlength <= jsonObject.getString("TicketNo").length) {
                                                    if ((jsonObject.getString("Customer")!!.toLowerCase().trim().contains(strCustomer!!))
                                                        && (jsonObject.getString("Mobile")!!.toLowerCase().trim().contains(strMobile!!))
                                                        && (jsonObject.getString("Customer")!!.toLowerCase().trim().contains(strArea!!))
                                                        && (jsonObject.getString("DueAmount")!!.toLowerCase().trim().contains(strDueAmount!!))){
                                                        //   Log.e(TAG,"2161    "+strTicketNumber+"   "+strCustomer)
                                                        emiListSort!!.put(jsonObject)
                                                    }else{
                                                        //  Log.e(TAG,"2162    "+strTicketNumber+"   "+strCustomer)
                                                    }

                                                    // }
                                                }

                                            }


                                            ///

                                            if (emiListSort.length() == 0){
                                                ll_nodata!!.visibility  =View.VISIBLE
                                                recyEmiList!!.visibility  =View.GONE

                                                Log.e(TAG,"3542  ")

                                            }else{
                                                ll_nodata!!.visibility  =View.GONE
                                                recyEmiList!!.visibility  =View.VISIBLE
                                                val lLayout = GridLayoutManager(this@EmiToDoListActivity, 1)
                                                recyEmiList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                                val adapter = EmiListAdapter(this@EmiToDoListActivity, emiListSort!!,SubMode!!)
                                                recyEmiList!!.adapter = adapter
                                                adapter.setClickListener(this@EmiToDoListActivity)
                                            }




                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiToDoListActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("OK") { dialogInterface, which ->
                                            finish()
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

    private fun filterBottomSheet() {
        try {

            filterCount = 1
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.emi_list_filter_sheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(false)



            txtReset = view.findViewById<TextView>(R.id.txtReset)
            txtSearch = view.findViewById<TextView>(R.id.txtSearch)

            tie_Customer = view.findViewById<TextInputEditText>(R.id.tie_Customer)
            tie_Mobile = view.findViewById<TextInputEditText>(R.id.tie_Mobile)
            tie_Area = view.findViewById<TextInputEditText>(R.id.tie_Area)
            tie_Due_Amount = view.findViewById<TextInputEditText>(R.id.tie_Due_Amount)

            tie_Customer!!.setText(""+strCustomer)
            tie_Mobile!!.setText(""+strMobile)
            tie_Area!!.setText(""+strArea)
            tie_Due_Amount!!.setText(""+strDueAmount)

            txtReset!!.setOnClickListener {
                tie_Customer!!.setText("")
                tie_Mobile!!.setText("")
                tie_Area!!.setText("")
                tie_Due_Amount!!.setText("")
            }
            txtSearch!!.setOnClickListener {

                strCustomer = tie_Customer!!.text.toString().toLowerCase().trim()
                strMobile = tie_Mobile!!.text.toString().toLowerCase().trim()
                strArea = tie_Area!!.text.toString().toLowerCase().trim()
                strDueAmount = tie_Due_Amount!!.text.toString().toLowerCase().trim()



                emiListSort = JSONArray()

                for (k in 0 until emiListArrayList.length()) {
                    val jsonObject = emiListArrayList.getJSONObject(k)
                    //  if (textlength <= jsonObject.getString("TicketNo").length) {
                    if ((jsonObject.getString("Customer")!!.toLowerCase().trim().contains(strCustomer!!))
                        && (jsonObject.getString("Mobile")!!.toLowerCase().trim().contains(strMobile!!))
                        && (jsonObject.getString("Area")!!.toLowerCase().trim().contains(strArea!!))
                        && (jsonObject.getString("DueAmount")!!.toLowerCase().trim().contains(strDueAmount!!))){
                        //   Log.e(TAG,"2161    "+strTicketNumber+"   "+strCustomer)
                        emiListSort!!.put(jsonObject)
                    }else{
                        //  Log.e(TAG,"2162    "+strTicketNumber+"   "+strCustomer)
                    }

                    // }
                }

                dialog1.dismiss()

                if (emiListSort.length() == 0){
                    ll_nodata!!.visibility  =View.VISIBLE
                    recyEmiList!!.visibility  =View.GONE
                    tv_listCount!!.setText(""+emiListSort.length())

                    Log.e(TAG,"3541  ")

                }else{
                    ll_nodata!!.visibility  =View.GONE
                    recyEmiList!!.visibility  =View.VISIBLE
                    val lLayout = GridLayoutManager(this@EmiToDoListActivity, 1)
                    recyEmiList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                    val adapter = EmiListAdapter(this@EmiToDoListActivity, emiListSort!!,SubMode!!)
                    recyEmiList!!.adapter = adapter
                    adapter.setClickListener(this@EmiToDoListActivity)
                }


            }


            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){

        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("EmiList")) {

            Log.e(TAG,"EmiList  148")
            val jsonObject = emiListSort.getJSONObject(position)
//            val ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
            val ID_CustomerWiseEMI = jsonObject.getString("ID_CustomerWiseEMI")
            val i = Intent(this@EmiToDoListActivity, EmiCollectionActivity::class.java)
            i.putExtra("jsonObject", jsonObject.toString());
            i.putExtra("ID_CustomerWiseEMI",ID_CustomerWiseEMI)
            startActivity(i)
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG,"741  onRestart ")

        onRestartCount = 1
        emiCount = 0
        getEmiList()
    }

}
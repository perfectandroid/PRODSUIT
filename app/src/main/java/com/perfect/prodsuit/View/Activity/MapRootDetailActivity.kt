package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.MapRootDetailAdapter
import com.perfect.prodsuit.Viewmodel.EmployeeWiseLocationListViewModel
import org.json.JSONArray
import org.json.JSONObject

class MapRootDetailActivity : AppCompatActivity() , View.OnClickListener{

    var TAG = "MapRootDetailActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    private var dialogascdesc : Dialog? = null
    private var FK_Employee:String?=""
    private var strDate:String? = ""

    var EmployeeLocation = 0
    var submode = "1"
    lateinit var employeeWiseLocationListViewModel: EmployeeWiseLocationListViewModel
    lateinit var locationList : JSONArray
    private var checkbox_asc: CheckBox? = null
    private var checkbox_dsc: CheckBox? = null
    private var btnsubmit: Button? = null
    private var txtSubmit: TextView? = null


    private var tv_employeee: TextView? = null
    private var tv_EnteredDate: TextView? = null
    private var imgv_filter: ImageView? = null

    var recyMapRoot: RecyclerView? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_map_root_detail)

        context = this@MapRootDetailActivity
        employeeWiseLocationListViewModel = ViewModelProvider(this).get(EmployeeWiseLocationListViewModel::class.java)

        setRegViews()

        if (getIntent().hasExtra("FK_Employee")) {
            FK_Employee = intent.getStringExtra("FK_Employee")
        }
        if (getIntent().hasExtra("strDate")) {
            strDate = intent.getStringExtra("strDate")
        }

        if (!FK_Employee.equals("") && !strDate.equals("")){
            Log.e(TAG,"620   "+FK_Employee+" : "+strDate)
            EmployeeLocation = 0
            getEmployeeWiseList(submode)
        }

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        tv_employeee = findViewById<TextView>(R.id.tv_employeee)
        tv_EnteredDate = findViewById<TextView>(R.id.tv_EnteredDate)
        recyMapRoot = findViewById<RecyclerView>(R.id.recyMapRoot)
        imgv_filter= findViewById<ImageView>(R.id.imgv_filter)
        imback!!.setOnClickListener(this)
        imgv_filter!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.imgv_filter->{
                filterBottomSheet()
            }
        }
    }

    private fun getEmployeeWiseList(submode: String) {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                employeeWiseLocationListViewModel.getEmployeeWiseLocationList(this, FK_Employee!!,strDate,submode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (EmployeeLocation == 0){
                                    EmployeeLocation++

                                    val jObject = JSONObject(msg)
                                    Log.e(TAG,"msg   1224   "+msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("EmployeeWiseLocationList")
                                        locationList = jobjt.getJSONArray("EmployeeWiseLocationListData")

                                        if (locationList.length()>0){
                                            tv_employeee!!.setText(locationList.getJSONObject(0).getString("EmployeeName"))
                                            tv_EnteredDate!!.setText("Showing location details as on "+locationList.getJSONObject(0).getString("EnteredDate"))

                                            val lLayout = GridLayoutManager(this@MapRootDetailActivity, 1)
                                            recyMapRoot!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = MapRootDetailAdapter(this@MapRootDetailActivity, locationList)
                                            recyMapRoot!!.adapter = adapter
                                        }





                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@MapRootDetailActivity,
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
                        }catch (e:Exception){
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun filterBottomSheet() {
        try {


            val dialog1 = BottomSheetDialog(this, R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.ascdesc, null)
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog1!!.setCanceledOnTouchOutside(false)


            checkbox_asc = view.findViewById<CheckBox>(R.id.checkbox_asc)
            checkbox_dsc = view.findViewById<CheckBox>(R.id.checkbox_dsc)
            txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)


            txtSubmit!!.setOnClickListener(this)
            checkbox_asc!!.setOnClickListener(this)
            checkbox_dsc!!.setOnClickListener(this)

            checkbox_asc!!.setOnClickListener {
                // loadLoginEmpDetails("1")
                checkbox_asc!!.isChecked=true
                checkbox_dsc!!.isChecked=false
                checkbox_asc!!.setButtonDrawable(R.drawable.ic_radiosort)
                checkbox_dsc!!.setButtonDrawable(R.drawable.ic_radio1)
            }
            checkbox_dsc!!.setOnClickListener {
                // loadLoginEmpDetails("1")
                checkbox_asc!!.isChecked=false
                checkbox_dsc!!.isChecked=true
                checkbox_dsc!!.setButtonDrawable(R.drawable.ic_radiosort)
                checkbox_asc!!.setButtonDrawable(R.drawable.ic_radio1)
            }
            txtSubmit!!.setOnClickListener {
                // loadLoginEmpDetails("1")
                if(checkbox_asc!!.isChecked)
                {
                    submode="1"
                }
                if(checkbox_dsc!!.isChecked)
                {
                    submode="2"
                }
                Log.e(TAG,"SUBMODE"+submode)
                getEmployeeWiseList(submode)
                dialog1.dismiss()
            }
//            onTextChangedValues()







//            dialog1!!.setCancelable(false)
            //   view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;


            //      tabLayout = view.findViewById(R.id.tabLayout)


            dialog1!!.setContentView(view)
            dialog1.show()

        } catch (e: Exception) {

        }
    }
}
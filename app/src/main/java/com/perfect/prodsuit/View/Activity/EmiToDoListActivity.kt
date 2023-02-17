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
import com.perfect.prodsuit.View.Adapter.ServiceListAdapter
import com.perfect.prodsuit.Viewmodel.EmiListViewModel
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

    private var imgv_filter: ImageView? = null
    private var txtReset: TextView? = null
    private var txtSearch: TextView? = null
    private var tie_Customer: TextInputEditText? = null
    private var tie_Mobile: TextInputEditText? = null
    private var tie_Address: TextInputEditText? = null

    var strCustomer = ""
    var strMobile = ""
    var strAddress = ""


    var emiCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_emi_to_do_list)
        context = this@EmiToDoListActivity
        emiListViewModel = ViewModelProvider(this).get(EmiListViewModel::class.java)

        setRegViews()
        emiCount = 0
        getEmiList()
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyEmiList = findViewById<RecyclerView>(R.id.recyEmiList)

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
        recyEmiList!!.adapter = null
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                emiListViewModel.getEmiList(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (emiCount == 0) {
                                    emiCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   82   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("CategoryDetailsList")
                                        emiListArrayList = jobjt.getJSONArray("CategoryList")
                                        if (emiListArrayList.length() > 0) {

                                            emiListSort = JSONArray()
                                            for (k in 0 until emiListArrayList.length()) {
                                                val jsonObject = emiListArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                emiListSort.put(jsonObject)
                                            }

                                            val lLayout = GridLayoutManager(this@EmiToDoListActivity, 1)
                                            recyEmiList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = EmiListAdapter(this@EmiToDoListActivity, emiListSort!!)
                                            recyEmiList!!.adapter = adapter
                                            adapter.setClickListener(this@EmiToDoListActivity)

                                        }
                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@EmiToDoListActivity,
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

    private fun filterBottomSheet() {
        try {


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
            tie_Address = view.findViewById<TextInputEditText>(R.id.tie_Address)

            tie_Customer!!.setText(""+strCustomer)
            tie_Mobile!!.setText(""+strMobile)
            tie_Address!!.setText(""+strAddress)

            txtReset!!.setOnClickListener {
                tie_Customer!!.setText("")
                tie_Mobile!!.setText("")
                tie_Address!!.setText("")
            }
            txtSearch!!.setOnClickListener {

                strCustomer = tie_Customer!!.text.toString().toLowerCase().trim()
                strMobile = tie_Mobile!!.text.toString().toLowerCase().trim()
                strAddress = tie_Address!!.text.toString().toLowerCase().trim()

//                emiListSort = JSONArray()
//                for (k in 0 until emiListArrayList.length()) {
//                    val jsonObject = emiListArrayList.getJSONObject(k)
//                    if ((jsonObject.getString("TicketNo")!!.toLowerCase().trim().contains(strCustomer!!))
//                        && (jsonObject.getString("Branch")!!.toLowerCase().trim().contains(strMobile!!))
//                        && (jsonObject.getString("Customer")!!.toLowerCase().trim().contains(strAddress!!))){
//
//                        emiListSort.put(jsonObject)
//                    }
//                }
//
                dialog1.dismiss()
//
//                val lLayout = GridLayoutManager(this@EmiToDoListActivity, 1)
//                recyEmiList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                val adapter = EmiListAdapter(this@EmiToDoListActivity, emiListSort!!)
//                recyEmiList!!.adapter = adapter
//                adapter.setClickListener(this@EmiToDoListActivity)

            }


            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){

        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("EmiList")) {

            Log.e(TAG,"EmiList  148")
//            val jsonObject = serviceListArrayList.getJSONObject(position)
//            val ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
            val i = Intent(this@EmiToDoListActivity, EmiCollectionActivity::class.java)
            startActivity(i)
        }
    }


}
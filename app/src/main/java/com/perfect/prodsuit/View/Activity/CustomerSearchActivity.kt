package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Adapter.CustomerAdapter
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.CustomerSearchViewModel
import org.json.JSONArray
import org.json.JSONObject

class CustomerSearchActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {

    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    private var chipNavigationBar: ChipNavigationBar? = null
    var edt_customer: EditText? = null
    var img_search: ImageView? = null
    var recyCustomer: RecyclerView? = null
    private var CUSTOMER_SEARCH: Int? = 101
    lateinit var customersearchViewModel: CustomerSearchViewModel
    val TAG: String = "CustomerSearchRepository"
    lateinit var customerArrayList : JSONArray
    companion object {
        var strCustomer = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_search)
        context = this@CustomerSearchActivity
        customersearchViewModel = ViewModelProvider(this).get(CustomerSearchViewModel::class.java)
        setRegViews()
        bottombarnav()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        edt_customer = findViewById<EditText>(R.id.edt_customer)
        img_search = findViewById<ImageView>(R.id.img_search)
        recyCustomer = findViewById<RecyclerView>(R.id.recyCustomer)
        imback!!.setOnClickListener(this)
        img_search!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.img_search->{
                try {
                    strCustomer = edt_customer!!.text.toString()
                    if (strCustomer.equals("")){
                    val snackbar: Snackbar = Snackbar.make(v, "Enter Customer", Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.colorPrimary))
                    snackbar.show()

                    }else{
                        getCustomerSearch()
                    }
                }catch (e  :Exception){
                    Log.e("TAG","Exception  64   "+e.toString())
                }
           }
        }
    }

    private fun getCustomerSearch() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                Config.Utils.hideSoftKeyBoard(this, edt_customer!!)
                customersearchViewModel.getCustomer(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   105   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("CustomerDetailsList")
                                customerArrayList = jobjt.getJSONArray("CustomerDetails")
                                if (customerArrayList.length()>0){
                                    Log.e(TAG,"msg   1052   "+msg)
                                    val lLayout = GridLayoutManager(this@CustomerSearchActivity, 1)
                                    recyCustomer!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                    recyCustomer!!.setHasFixedSize(true)
                                    val adapter = CustomerAdapter(this@CustomerSearchActivity, customerArrayList)
                                    recyCustomer!!.adapter = adapter
                                    adapter.setClickListener(this@CustomerSearchActivity)
                                    Log.e(TAG,"msg   10522   "+msg)
                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@CustomerSearchActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@CustomerSearchActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.profile -> {
                        val i = Intent(this@CustomerSearchActivity, ProfileActivity::class.java)
                        startActivity(i)
                    }
                    R.id.logout -> {
                        doLogout()
                    }
                    R.id.quit -> {
                        quit()
                    }
                }
            }
        })
    }

    private fun doLogout() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.logout_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btnYes) as Button
            val btn_No = dialog1 .findViewById(R.id.btnNo) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                dologoutchanges()
                startActivity(Intent(this@CustomerSearchActivity, WelcomeActivity::class.java))
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dologoutchanges() {
        val loginSP = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        val loginEditer = loginSP.edit()
        loginEditer.putString("loginsession", "No")
        loginEditer.commit()
        val loginmobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
        val loginmobileEditer = loginmobileSP.edit()
        loginmobileEditer.putString("Loginmobilenumber", "")
        loginmobileEditer.commit()
    }

    private fun quit() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.quit_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btn_Yes) as Button
            val btn_No = dialog1 .findViewById(R.id.btn_No) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                finish()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity()
                }
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("customer")){
            val jsonObject = customerArrayList.getJSONObject(position)
            val intent = Intent()
            intent.putExtra("ID_Customer", jsonObject.getString("ID_Customer"))
            intent.putExtra("Name", jsonObject.getString("Name"))
            intent.putExtra("Address", jsonObject.getString("Address"))
            intent.putExtra("Email", jsonObject.getString("Email"))
            intent.putExtra("MobileNumber", jsonObject.getString("MobileNumber"))
            setResult(CUSTOMER_SEARCH!!, intent)
            finish()
        }
    }

}
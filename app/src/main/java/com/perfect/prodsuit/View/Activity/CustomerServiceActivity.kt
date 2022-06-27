package com.perfect.prodsuit.View.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.R

class CustomerServiceActivity : AppCompatActivity()  , View.OnClickListener {

    val TAG : String = "CustomerServiceActivity"
    lateinit var context: Context

    var til_CustomerNo: TextInputLayout? = null

    var tie_CustomerNo: TextInputEditText? = null

    private var tabLayout : TabLayout? = null
    var llMainDetail: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_customer_service)
        context = this@CustomerServiceActivity

        setRegViews()
        addTabItem()

        til_CustomerNo!!.setEndIconOnClickListener {
            finish()
        }
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        til_CustomerNo      = findViewById(R.id.til_CustomerNo)
        tie_CustomerNo      = findViewById(R.id.tie_CustomerNo)



        tabLayout      = findViewById(R.id.tabLayout)
        llMainDetail = findViewById<LinearLayout>(R.id.llMainDetail)

        til_CustomerNo!!.setOnClickListener(this)
    }


    private fun addTabItem() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Complaint"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Warranty"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Product"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Sales"))
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        getComplaints()
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabSelected  113  "+tab.position)
                if (tab.position == 0){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    llMainDetail!!.removeAllViews()

                  getComplaints()

                }
                if (tab.position == 1){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    llMainDetail!!.removeAllViews()

                    getWarranty()

                }
                if (tab.position == 2){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    llMainDetail!!.removeAllViews()
                    getProduct()

                }
                if (tab.position == 3){
                    Log.e(TAG,"onTabSelected  1131  "+tab.position)
                    llMainDetail!!.removeAllViews()

                    getSales()
                }
                /*  if (tab.position == 4){
                      Log.e(TAG,"onTabSelected  1131  "+tab.position)
                      llMainDetail!!.removeAllViews()
                   //   tv_actionType!!.visibility=View.GONE
                      recyAgendaDetail!!.visibility=View.GONE
                      getQuotationtails()
                  }*/
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabUnselected  162  "+tab.position)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.e(TAG,"onTabReselected  165  "+tab.position)
            }
        })
    }

    private fun getComplaints() {

        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_complaints, null, false)
        llMainDetail!!.addView(inflatedLayout);

    }

    private fun getWarranty() {

        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_warranty, null, false)
        llMainDetail!!.addView(inflatedLayout);

    }

    private fun getProduct() {

        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_product, null, false)
        llMainDetail!!.addView(inflatedLayout);

    }

    private fun getSales() {

        val inflater = LayoutInflater.from(this@CustomerServiceActivity)
        val inflatedLayout: View = inflater.inflate(R.layout.activity_customer_service_sales, null, false)
        llMainDetail!!.addView(inflatedLayout);

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

        }
    }
}
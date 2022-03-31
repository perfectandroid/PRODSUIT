package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.LoginActivityViewModel
import org.json.JSONObject

class LeadGenerationActivity : AppCompatActivity() , View.OnClickListener {

    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    private var chipNavigationBar: ChipNavigationBar? = null

    private var llTerminal: LinearLayout? = null
    private var llCustomer: LinearLayout? = null
    private var txtcustomer: TextView? = null
   // lateinit var leadterminalViewModel: LeadTerminalViewModel

    private var CUSTOMER_SEARCH: Int? = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_leadgeneration)
        context = this@LeadGenerationActivity
        setRegViews()
        bottombarnav()
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        llTerminal = findViewById<LinearLayout>(R.id.llTerminal)
        llCustomer = findViewById<LinearLayout>(R.id.llCustomer)

        txtcustomer = findViewById<TextView>(R.id.txtcustomer)

        imback!!.setOnClickListener(this)
        llTerminal!!.setOnClickListener(this)
        llCustomer!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.llTerminal->{
               // finish()
                //getTerminal()
            }
            R.id.llCustomer->{
                val intent = Intent(this@LeadGenerationActivity, CustomerSearchActivity::class.java)
                CUSTOMER_SEARCH?.let { startActivityForResult(intent, it) } // Activity is started with requestCode 2
               // overridePendingTransition(R.anim.exit_on_left, R.anim.enter_from_right);

            }
        }
    }

//    private fun getTerminal() {
//        when (Config.ConnectivityUtils.isConnected(this)) {
//            true -> {
//                progressDialog = ProgressDialog(context, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
//
//                loginActivityViewModel.getUser(this)!!.observe(
//                    this,
//                    Observer { serviceSetterGetter ->
//                        val msg = serviceSetterGetter.message
//                        if (msg!!.length > 0) {
//                            val jObject = JSONObject(msg)
//                            if (jObject.getString("StatusCode") == "0") {
//                                var jobj = jObject.getJSONObject("UserLoginDetails")
//                                val FK_EmployeeSP = applicationContext.getSharedPreferences(
//                                    Config.SHARED_PREF1,
//                                    0
//                                )
//                                val FK_EmployeeEditer = FK_EmployeeSP.edit()
//                                FK_EmployeeEditer.putString(
//                                    "FK_Employee",
//                                    jobj.getString("FK_Employee")
//                                )
//                                FK_EmployeeEditer.commit()
//                                val UserNameSP = applicationContext.getSharedPreferences(
//                                    Config.SHARED_PREF2,
//                                    0
//                                )
//                                val UserNameEditer = UserNameSP.edit()
//                                UserNameEditer.putString("UserName", jobj.getString("UserName"))
//                                UserNameEditer.commit()
//                                val AddressSP = applicationContext.getSharedPreferences(
//                                    Config.SHARED_PREF3,
//                                    0
//                                )
//                                val AddressEditer = AddressSP.edit()
//                                AddressEditer.putString("Address", jobj.getString("Address"))
//                                AddressEditer.commit()
//                                val MobileNumberSP = applicationContext.getSharedPreferences(
//                                    Config.SHARED_PREF4,
//                                    0
//                                )
//                                val MobileNumberEditer = MobileNumberSP.edit()
//                                MobileNumberEditer.putString(
//                                    "MobileNumber",
//                                    jobj.getString("MobileNumber")
//                                )
//                                MobileNumberEditer.commit()
//                                val TokenSP = applicationContext.getSharedPreferences(
//                                    Config.SHARED_PREF5,
//                                    0
//                                )
//                                val TokenEditer = TokenSP.edit()
//                                TokenEditer.putString("Token", jobj.getString("Token"))
//                                TokenEditer.commit()
//                                val EmailSP = applicationContext.getSharedPreferences(
//                                    Config.SHARED_PREF6,
//                                    0
//                                )
//                                val EmailEditer = EmailSP.edit()
//                                EmailEditer.putString("Email", jobj.getString("Email"))
//                                EmailEditer.commit()
//                                val i = Intent(this@LoginActivity, OTPActivity::class.java)
//                                startActivity(i)
//                                finish()
//                            } else {
//                                val builder = AlertDialog.Builder(
//                                    this@LoginActivity,
//                                    R.style.MyDialogTheme
//                                )
//                                builder.setMessage(jObject.getString("EXMessage"))
//                                builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                }
//                                val alertDialog: AlertDialog = builder.create()
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()
//                            }
//                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    })
//                progressDialog!!.dismiss()
//            }
//            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
//            }
//        }
//    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@LeadGenerationActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.profile -> {
                        val i = Intent(this@LeadGenerationActivity, ProfileActivity::class.java)
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
                startActivity(Intent(this@LeadGenerationActivity, WelcomeActivity::class.java))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG","onActivityResult  256   "+requestCode+ "   "+resultCode+ "  "+data)
        if (requestCode == CUSTOMER_SEARCH){
            txtcustomer!!.text = data!!.getStringExtra("Name")
        }
    }
}
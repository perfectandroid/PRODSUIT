package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.OTPActivityViewModel
import org.json.JSONObject

class OTPActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var context: Context
    lateinit var otpActivityViewModel: OTPActivityViewModel
    var show : Boolean = false
    private var progressDialog: ProgressDialog? = null
    private var one: TextView? = null
    private var two: TextView? = null
    private var three: TextView? = null
    private var four: TextView? = null
    private var five: TextView? = null
    private var six: TextView? = null
    private var seven: TextView? = null
    private var eight: TextView? = null
    private var nine: TextView? = null
    private var zero: TextView? = null
    private var et_1: EditText? = null
    private var et_2: EditText? = null
    private var et_3: EditText? = null
    private var et_4: EditText? = null
    private var et_5: EditText? = null
    private var et_6: EditText? = null
    private var imgShowPin: ImageView? = null
    private var showPin: LinearLayout? = null
    private var clear: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_otp)
        setRegViews()
        context = this@OTPActivity
        otpActivityViewModel = ViewModelProvider(this).get(OTPActivityViewModel::class.java)
    }

    private fun setRegViews() {
        var tvdata = findViewById<TextView>(R.id.tvdata)
        one = findViewById<TextView>(R.id.one)
        two = findViewById<TextView>(R.id.two)
        three = findViewById<TextView>(R.id.three)
        four = findViewById<TextView>(R.id.four)
        five = findViewById<TextView>(R.id.five)
        six = findViewById<TextView>(R.id.six)
        seven = findViewById<TextView>(R.id.seven)
        eight = findViewById<TextView>(R.id.eight)
        nine = findViewById<TextView>(R.id.nine)
        zero = findViewById<TextView>(R.id.zero)
        et_1 = findViewById<EditText>(R.id.et_1)
        et_2 = findViewById<EditText>(R.id.et_2)
        et_3 = findViewById<EditText>(R.id.et_3)
        et_4 = findViewById<EditText>(R.id.et_4)
        et_5 = findViewById<EditText>(R.id.et_5)
        et_6 = findViewById<EditText>(R.id.et_6)
        showPin = findViewById<LinearLayout>(R.id.showPin)
        clear = findViewById<LinearLayout>(R.id.clear)
        imgShowPin = findViewById<ImageView>(R.id.imgShowPin)
        one!!.setOnClickListener(this)
        two!!.setOnClickListener(this)
        three!!.setOnClickListener(this)
        four!!.setOnClickListener(this)
        five!!.setOnClickListener(this)
        six!!.setOnClickListener(this)
        seven!!.setOnClickListener(this)
        eight!!.setOnClickListener(this)
        nine!!.setOnClickListener(this)
        zero!!.setOnClickListener(this)
        clear!!.setOnClickListener(this)
        showPin!!.setOnClickListener(this)
        et_1!!.isEnabled = false
        et_2!!.isEnabled = false
        et_3!!.isEnabled = false
        et_4!!.isEnabled = false
        et_5!!.isEnabled = false
        et_6!!.isEnabled = false
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.one -> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("1")
                                    OTPVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                }
                                else {
                                    et_5!!.setText("1")
                                }
                            }
                            else {
                                et_4!!.setText("1")
                            }
                        }
                        else {
                            et_3!!.setText("1")
                        }
                    }
                    else {
                        et_2!!.setText("1")
                    }
                }
                else {
                    et_1!!.setText("1")
                }
            }
            R.id.two-> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("2")
                                    OTPVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                }
                                else {
                                    et_5!!.setText("2")
                                }
                            }
                            else {
                                et_4!!.setText("2")
                            }
                        }
                        else {
                            et_3!!.setText("2")
                        }
                    }
                    else {
                        et_2!!.setText("2")
                    }
                }
                else {
                    et_1!!.setText("2");
                }
            }
            R.id.three-> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("3")
                                    OTPVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                }
                                else {
                                    et_5!!.setText("3")
                                }
                            }
                            else {
                                et_4!!.setText("3")
                            }
                        }
                        else {
                            et_3!!.setText("3")
                        }
                    }
                    else {
                        et_2!!.setText("3")
                    }
                }
                else {
                    et_1!!.setText("3")
                }
            }
            R.id.four-> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("4")
                                    OTPVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                }
                                else {
                                    et_5!!.setText("4")
                                }
                            }
                            else {
                                et_4!!.setText("4")
                            }
                        }
                        else {
                            et_3!!.setText("4")
                        }
                    }
                    else {
                        et_2!!.setText("4")
                    }
                }
                else {
                    et_1!!.setText("4")
                }
            }
            R.id.five-> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("5")
                                    OTPVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                }
                                else {
                                    et_5!!.setText("5")
                                }
                            }
                            else {
                                et_4!!.setText("5")
                            }
                        }
                        else {
                            et_3!!.setText("5")
                        }
                    }
                    else {
                        et_2!!.setText("5")
                    }
                }
                else {
                    et_1!!.setText("5")
                }
            }
            R.id.six-> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("6")
                                    OTPVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                }
                                else {
                                    et_5!!.setText("6")
                                }
                            }
                            else {
                                et_4!!.setText("6")
                            }
                        }
                        else {
                            et_3!!.setText("6")
                        }
                    }
                    else {
                        et_2!!.setText("6")
                    }
                }
                else {
                    et_1!!.setText("6")
                }
            }
            R.id.seven-> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("7")
                                    OTPVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                }
                                else {
                                    et_5!!.setText("7")
                                }
                            }
                            else {
                                et_4!!.setText("7")
                            }
                        }
                        else {
                            et_3!!.setText("7")
                        }
                    }
                    else {
                        et_2!!.setText("7")
                    }
                }
                else {
                    et_1!!.setText("7")
                }
            }
            R.id.eight-> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("8")
                                    OTPVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                }
                                else {
                                    et_5!!.setText("8")
                                }
                            }
                            else {
                                et_4!!.setText("8")
                            }
                        }
                        else {
                            et_3!!.setText("8")
                        }
                    }
                    else {
                        et_2!!.setText("8")
                    }
                }
                else {
                    et_1!!.setText("8")
                }
            }
            R.id.nine-> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("9")
                                    OTPVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                }
                                else {
                                    et_5!!.setText("9")
                                }
                            }
                            else {
                                et_4!!.setText("9")
                            }
                        }
                        else {
                            et_3!!.setText("9")
                        }
                    }
                    else {
                        et_2!!.setText("9")
                    }
                }
                else {
                    et_1!!.setText("9")
                }
            }
            R.id.zero-> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("0")
                                    OTPVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                }
                                else {
                                    et_5!!.setText("0")
                                }
                            }
                            else {
                                et_4!!.setText("0")
                            }
                        }
                        else {
                            et_3!!.setText("0")
                        }
                    }
                    else {
                        et_2!!.setText("0")
                    }
                }
                else {
                    et_1!!.setText("0")
                }
            }
            R.id.showPin-> {
                if (show == false){
                    et_1!!.transformationMethod = null
                    et_2!!.transformationMethod = null
                    et_3!!.transformationMethod = null
                    et_4!!.transformationMethod = null
                    et_5!!.transformationMethod = null
                    et_6!!.transformationMethod = null
                    show = true
                    imgShowPin!!.setBackgroundResource(R.drawable.ic_visibility)
                }
                else{
                    et_1!!.transformationMethod = PasswordTransformationMethod()
                    et_2!!.transformationMethod = PasswordTransformationMethod()
                    et_3!!.transformationMethod = PasswordTransformationMethod()
                    et_4!!.transformationMethod = PasswordTransformationMethod()
                    et_5!!.transformationMethod = PasswordTransformationMethod()
                    et_6!!.transformationMethod = PasswordTransformationMethod()
                    show = false
                    imgShowPin!!.setBackgroundResource(R.drawable.ic_visibility_off)
                }
            }
            R.id.clear->{
                if(et_6!!.length()==1) {
                    et_6!!.setText("")
                }
                else{
                    if(et_5!!.length()==1) {
                        et_5!!.setText("")
                    }
                    else{
                        if(et_4!!.length()==1) {
                            et_4!!.setText("")
                        }
                        else{
                            if(et_3!!.length()==1) {
                                et_3!!.setText("")
                            }
                            else{
                                if(et_2!!.length()==1) {
                                    et_2!!.setText("")
                                }
                                else{
                                    if(et_1!!.length()==1) {
                                        et_1!!.setText("")
                                    }
                                    else{
                                        et_1!!.text.clear()
                                        et_2!!.text.clear()
                                        et_3!!.text.clear()
                                        et_4!!.text.clear()
                                        et_5!!.text.clear()
                                        et_6!!.text.clear()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        var strMOTP= ""
    }

    private fun OTPVerification(Mpin:String) {
        strMOTP = et_1!!.text.toString()+et_2!!.text.toString()+et_3!!.text.toString()+et_4!!.text.toString()+et_5!!.text.toString()+et_6!!.text.toString()
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                otpActivityViewModel.getOTP(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {
                                var jobj = jObject.getJSONObject("UserLoginDetails")
                                val FK_EmployeeSP = applicationContext.getSharedPreferences(
                                    Config.SHARED_PREF1,
                                    0
                                )
                                val FK_EmployeeEditer = FK_EmployeeSP.edit()
                                FK_EmployeeEditer.putString(
                                    "FK_Employee",
                                    jobj.getString("FK_Employee")
                                )
                                FK_EmployeeEditer.commit()
                                val UserNameSP = applicationContext.getSharedPreferences(
                                    Config.SHARED_PREF2,
                                    0
                                )
                                val UserNameEditer = UserNameSP.edit()
                                UserNameEditer.putString("UserName", jobj.getString("UserName"))
                                UserNameEditer.commit()
                                val AddressSP = applicationContext.getSharedPreferences(
                                    Config.SHARED_PREF3,
                                    0
                                )
                                val AddressEditer = AddressSP.edit()
                                AddressEditer.putString("Address", jobj.getString("Address"))
                                AddressEditer.commit()
                                val MobileNumberSP = applicationContext.getSharedPreferences(
                                    Config.SHARED_PREF4,
                                    0
                                )
                                val MobileNumberEditer = MobileNumberSP.edit()
                                MobileNumberEditer.putString(
                                    "MobileNumber",
                                    jobj.getString("MobileNumber")
                                )
                                MobileNumberEditer.commit()
                                val TokenSP = applicationContext.getSharedPreferences(
                                    Config.SHARED_PREF5,
                                    0
                                )
                                val TokenEditer = TokenSP.edit()
                                TokenEditer.putString("Token", jobj.getString("Token"))
                                TokenEditer.commit()
                                val EmailSP = applicationContext.getSharedPreferences(
                                    Config.SHARED_PREF6,
                                    0
                                )
                                val EmailEditer = EmailSP.edit()
                                EmailEditer.putString("Email", jobj.getString("Email"))
                                EmailEditer.commit()
                                val i = Intent(this@OTPActivity, SetMpinActivity::class.java)
                                startActivity(i)
                                finish()
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@OTPActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                                clearAll()
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

    private fun clearAll() {
        et_1!!.text.clear()
        et_2!!.text.clear()
        et_3!!.text.clear()
        et_4!!.text.clear()
        et_5!!.text.clear()
        et_6!!.text.clear()
    }

}



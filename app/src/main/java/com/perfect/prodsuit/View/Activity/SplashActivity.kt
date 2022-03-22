package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.SplashresellerActivityViewModel
import org.json.JSONObject

class SplashActivity : AppCompatActivity() {

    val CERT_NAME = "static-vm.pem"
    val BASE_URL = "https://202.164.150.65:14262/ProdSuitAPI/api/"
    val BANK_KEY = "-500"
    lateinit var splashresellerActivityViewModel: SplashresellerActivityViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        context = this@SplashActivity
        splashresellerActivityViewModel = ViewModelProvider(this).get(SplashresellerActivityViewModel::class.java)
        getResellerData()

        val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
        val BASE_URLEditer = BASE_URLSP.edit()
        BASE_URLEditer.putString("BASE_URL", BASE_URL)
        BASE_URLEditer.commit()

        val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
        val CERT_NAMEEditer = CERT_NAMESP.edit()
        CERT_NAMEEditer.putString("CERT_NAME", CERT_NAME)
        CERT_NAMEEditer.commit()

        val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
        val BANK_KEYEditer = BANK_KEYESP.edit()
        BANK_KEYEditer.putString("BANK_KEY", BANK_KEY)
        BANK_KEYEditer.commit()
    }

    private fun getResellerData() {
        when(Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                splashresellerActivityViewModel.getReseller(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    //var jobj = jObject.getJSONObject("UserLoginInfodet")
                                    //var jsonArray = jobj.getJSONArray("LoanApplicationListDetails")
                                    //tvdata.text = jobj.getString("User_ID")
                                        doSplash()
                                } else {
                                    val builder = AlertDialog.Builder(
                                            this@SplashActivity,
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
                }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun doSplash() {
        val background = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep((4 * 1000).toLong())
                    val Loginpref = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
                    if (Loginpref.getString("loginsession", null) == null) {
                        val i = Intent(this@SplashActivity, WelcomeSliderActivity::class.java)
                        startActivity(i)
                        finish()
                    } else if (Loginpref.getString("loginsession", null) != null && !Loginpref.getString(
                            "loginsession",
                            null
                        )!!.isEmpty() && Loginpref.getString("loginsession", null) == "Yes") {
                        val i = Intent(this@SplashActivity, MpinActivity::class.java)
                        startActivity(i)
                        finish()
                    } else if (Loginpref.getString("loginsession", null) != null && !Loginpref.getString(
                            "loginsession",
                            null
                        )!!.isEmpty() && Loginpref.getString("loginsession", null) == "No") {
                        val i = Intent(this@SplashActivity, WelcomeActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        val i = Intent(this@SplashActivity, WelcomeSliderActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }

}
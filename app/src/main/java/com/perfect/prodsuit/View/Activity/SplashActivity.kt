package com.perfect.prodsuit.View.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.LoginActivityViewModel
import com.perfect.prodsuit.Viewmodel.SplashresellerActivityViewModel
import org.json.JSONObject

class SplashActivity : AppCompatActivity() {

    lateinit var splashresellerActivityViewModel: SplashresellerActivityViewModel
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        doSplash()
        context = this@SplashActivity
        splashresellerActivityViewModel = ViewModelProvider(this).get(SplashresellerActivityViewModel::class.java)

        getResellerData()
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
                              /*  if (jObject.getString("StatusCode") == "0") {
                                    var jobj = jObject.getJSONObject("UserLoginInfodet")
                                    //var jsonArray = jobj.getJSONArray("LoanApplicationListDetails")
                                    tvdata.text = jobj.getString("User_ID")
                                } else {
                                    tvdata.text = jObject.getString("EXMessage")
                                }*/
                            } else {
                                Toast.makeText(
                                        applicationContext,
                                        "Some Technical Issues.",
                                        Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                val i = Intent(this@SplashActivity, OTPActivity::class.java)
                startActivity(i)
                finish()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG).show()
            }
        }    }

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
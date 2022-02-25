package com.perfect.prodsuit.View.Activity

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.LoginActivityViewModel
import org.json.JSONObject
import java.util.*

class LoginActivity : AppCompatActivity() {

    lateinit var context: Context
    lateinit var loginActivityViewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        context = this@LoginActivity
        loginActivityViewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)


        var tvdata = findViewById(R.id.tvdata) as TextView
        var btlogin = findViewById(R.id.btlogin) as Button

        btlogin.setOnClickListener {
            when(Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                     loginActivityViewModel.getUser(this)!!.observe(this, Observer { serviceSetterGetter ->
                       val msg = serviceSetterGetter.message
                       if(msg!!.length>0) {
                           val jObject = JSONObject(msg)
                           if (jObject.getString("StatusCode") == "0") {
                               var jobj = jObject.getJSONObject("UserLoginInfodet")
                               //var jsonArray = jobj.getJSONArray("LoanApplicationListDetails")
                               tvdata.text = jobj.getString("User_ID")
                           } else {
                               tvdata.text = jObject.getString("EXMessage")

                           }
                       }else{
                           Toast.makeText(applicationContext, "Some Technical Issues.", Toast.LENGTH_LONG).show()
                       }


            })
                }
                false -> {
                    Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG).show()
                }
            }
        }

    }


}
package com.perfect.prodsuit.View.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.LoginActivityViewModel
import org.json.JSONObject
import java.util.*

class LoginActivity : AppCompatActivity() , GoogleApiClient.OnConnectionFailedListener {

    lateinit var context: Context
    lateinit var loginActivityViewModel: LoginActivityViewModel
    var signInButton: SignInButton? = null
    var progress: ImageView? = null
    private var googleApiClient: GoogleApiClient? = null
    private val RC_SIGN_IN = 1
    var strName: String? = null
    var strEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this@LoginActivity
        loginActivityViewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
        var tvdata = findViewById(R.id.tvdata) as TextView
        var btlogin = findViewById(R.id.btlogin) as Button
         progress = findViewById(R.id.progress)
        Glide.with(this).load(R.drawable.progressgif).into(progress!!);
        btlogin.setOnClickListener {
            when(Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progress!!.visibility=View.VISIBLE
                    loginActivityViewModel.getUser(this)!!.observe(
                        this,
                        Observer { serviceSetterGetter ->
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                    var jobj = jObject.getJSONObject("UserLoginInfodet")
                                    //var jsonArray = jobj.getJSONArray("LoanApplicationListDetails")
                                    tvdata.text = jobj.getString("User_ID")
                                } else {
                                    tvdata.text = jObject.getString("EXMessage")
                                }
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Some Technical Issues.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    val i = Intent(this@LoginActivity, OTPActivity::class.java)
                    startActivity(i)
                    finish()
                    progress!!.visibility=View.INVISIBLE
                }
                false -> {
                   Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG).show()
                }
            }
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        signInButton = findViewById<View>(R.id.sign_in_button) as SignInButton
        signInButton!!.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            strName=account!!.displayName
            strEmail=account.email
          //  login(account!!.displayName, account.email)
        } else {
            Toast.makeText(applicationContext, "Sign in cancel", Toast.LENGTH_LONG).show()
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

}
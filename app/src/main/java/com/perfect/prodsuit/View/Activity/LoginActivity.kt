package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var loginActivityViewModel: LoginActivityViewModel
    var signInButton: SignInButton? = null
    var progress: ImageView? = null
    var etxt_mob: EditText? = null
    private var googleApiClient: GoogleApiClient? = null
    private val RC_SIGN_IN = 1
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1
    var strName: String? = null
    var strEmail: String? = null
    companion object {
        var strEPhone = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this@LoginActivity
        loginActivityViewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
        var tvdata = findViewById<TextView>(R.id.tvdata)
        var btlogin = findViewById<Button>(R.id.btlogin)
        etxt_mob = findViewById<EditText>(R.id.etxt_mob)
        progress = findViewById(R.id.progress)
        checkLocationPermission()

        btlogin.setOnClickListener {
            validation()
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

    private fun validation(){
        if (etxt_mob!!.text.toString() == null || etxt_mob!!.text.toString().isEmpty()) {
            etxt_mob!!.setError("Please Enter Mobile Number")
        }
        else if (etxt_mob!!.text.toString().isNotEmpty() && etxt_mob!!.text.toString().length!=10) {
            etxt_mob!!.setError("Please Enter Valid Mobile Number")
        }else{
            strEPhone = etxt_mob!!.text.toString()
            when (Config.ConnectivityUtils.isConnected(this)) {
                true -> {
                    progressDialog = ProgressDialog(context, R.style.Progress)
                    progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.setIndeterminate(true)
                    progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                    progressDialog!!.show()
                    Config.Utils.hideSoftKeyBoard(this, etxt_mob!!)
                    loginActivityViewModel.getUser(this,strEPhone)!!.observe(
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


                                    val EmailSP = applicationContext.getSharedPreferences(Config.SHARED_PREF6, 0)
                                    val EmailEditer = EmailSP.edit()
                                    EmailEditer.putString("Email", jobj.getString("Email"))
                                    EmailEditer.commit()


                                    val UserCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF36, 0)
                                    val UserCodeEditer = UserCodeSP.edit()
                                    UserCodeEditer.putString("UserCode", jobj.getString("UserCode"))
                                    UserCodeEditer.commit()

                                    val FK_BranchSP = applicationContext.getSharedPreferences(Config.SHARED_PREF37, 0)
                                    val FK_BranchEditer = FK_BranchSP.edit()
                                    FK_BranchEditer.putString("FK_Branch", jobj.getString("FK_Branch"))
                                    FK_BranchEditer.commit()

                                    val FK_BranchTypeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF38, 0)
                                    val FK_BranchTypeEditer = FK_BranchTypeSP.edit()
                                    FK_BranchTypeEditer.putString("FK_BranchType", jobj.getString("FK_BranchType"))
                                    FK_BranchTypeEditer.commit()

                                    val FK_CompanySP = applicationContext.getSharedPreferences(Config.SHARED_PREF39, 0)
                                    val FK_CompanyEditer = FK_CompanySP.edit()
                                    FK_CompanyEditer.putString("FK_Company", jobj.getString("FK_Company"))
                                    FK_CompanyEditer.commit()

                                    val FK_BranchCodeUserSP = applicationContext.getSharedPreferences(Config.SHARED_PREF40, 0)
                                    val FK_BranchCodeUserEditer = FK_BranchCodeUserSP.edit()
                                    FK_BranchCodeUserEditer.putString("FK_BranchCodeUser", jobj.getString("FK_BranchCodeUser"))
                                    FK_BranchCodeUserEditer.commit()

                                    val FK_UserRoleSP = applicationContext.getSharedPreferences(Config.SHARED_PREF41, 0)
                                    val FK_UserRoleEditer = FK_UserRoleSP.edit()
                                    FK_UserRoleEditer.putString("FK_UserRole", jobj.getString("FK_UserRole"))
                                    FK_UserRoleEditer.commit()

                                    val UserRoleSP = applicationContext.getSharedPreferences(Config.SHARED_PREF42, 0)
                                    val UserRoleEditer = UserRoleSP.edit()
                                    UserRoleEditer.putString("UserRole", jobj.getString("UserRole"))
                                    UserRoleEditer.commit()

                                    val IsAdminSP = applicationContext.getSharedPreferences(Config.SHARED_PREF43, 0)
                                    val IsAdminEditer = IsAdminSP.edit()
                                    IsAdminEditer.putString("IsAdmin", jobj.getString("IsAdmin"))
                                    IsAdminEditer.commit()

                                    val ID_UserSP = applicationContext.getSharedPreferences(Config.SHARED_PREF44, 0)
                                    val ID_UserEditer = ID_UserSP.edit()
                                    ID_UserEditer.putString("ID_User", jobj.getString("ID_User"))
                                    ID_UserEditer.commit()






                                    val i = Intent(this@LoginActivity, OTPActivity::class.java)
                                    startActivity(i)
                                    finish()
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@LoginActivity,
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
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Some Technical Issues.",
//                                    Toast.LENGTH_LONG
//                                ).show()
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
        } else {
            Toast.makeText(applicationContext, "Sign in cancel", Toast.LENGTH_LONG).show()
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}
    private fun checkLocationPermission():Boolean  {



        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {


                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Allow application to access location services")
                    //  .setMessage("This app wants to change your app permissions.")
                    .setPositiveButton("Location") { paramDialogInterface, paramInt ->
                        //  val myIntent = Intent(Settings.Intent.ACTION_CALL)
                        // val callIntent =  Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        //startActivity(callIntent)
                        startActivity( Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.perfect.scoreconnect")));
                    }
                    .setNegativeButton("Deny") { paramDialogInterface, paramInt ->

                        //  val intent = Intent(applicationContext, HomeActivity::class.java)
                        //   startActivity(intent)
                    }
                dialog.show()

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            return false
        } else {
            return true
        }


    }
}
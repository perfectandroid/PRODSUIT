package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
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
import com.google.android.material.snackbar.Snackbar
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DBHelper
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.LoginActivityViewModel
import org.json.JSONObject

class LoginActivity : AppCompatActivity() , GoogleApiClient.OnConnectionFailedListener {

    val TAG ="LoginActivity"
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
    var countLogin=0
    var img_logo: ImageView? = null
    var img_technology: ImageView? = null

    var db : DBHelper? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this@LoginActivity
        loginActivityViewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
        var tvdata = findViewById<TextView>(R.id.tvdata)
        var btlogin = findViewById<Button>(R.id.btlogin)
        etxt_mob = findViewById<EditText>(R.id.etxt_mob)
        progress = findViewById(R.id.progress)
        img_logo = findViewById(R.id.img_logo)
        img_technology = findViewById(R.id.img_technology)
        setTechnologyPartner()
        checkLocationPermission()
        db = DBHelper(this, null)

        deleteChatData()

        Log.e(TAG,"99991   Start")
        Config.deleteFcmToken(context)

        btlogin.setOnClickListener {
            Config.disableClick(it)
            countLogin=0
            if (etxt_mob!!.text.toString() == null || etxt_mob!!.text.toString().isEmpty()) {
                etxt_mob!!.setError("Please Enter Mobile Number")
            }
            else if (etxt_mob!!.text.toString().isNotEmpty() && etxt_mob!!.text.toString().length!=10) {
                etxt_mob!!.setError("Please Enter Valid Mobile Number")
            }else{
                strEPhone = etxt_mob!!.text.toString()
                CheckTestingMobile()
            }


          //  validation()
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

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun deleteChatData() {

        try {
            db!!.deleteChatdb()

            val Fbase_NameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF68, 0)
            val Fbase_NameEditer = Fbase_NameSP.edit()
            Fbase_NameEditer.putString("Fbase_Name","")
            Fbase_NameEditer.commit()

            val Fbase_MobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF69, 0)
            val Fbase_MobileEditer = Fbase_MobileSP.edit()
            Fbase_MobileEditer.putString("Fbase_Mobile","")
            Fbase_MobileEditer.commit()

            var timestampSP = applicationContext.getSharedPreferences(Config.SHARED_PREF70, 0)
            var timestampEditer = timestampSP.edit()
            timestampEditer.putString("timestamp","")
            timestampEditer.commit()

        }catch (e: Exception){

        }

    }

    private fun setTechnologyPartner() {

        try {
            val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
            val TechnologyPartnerImageSP = applicationContext.getSharedPreferences(Config.SHARED_PREF20, 0)
            val AppIconImageCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF19, 0)
            var IMAGEURL = IMAGE_URLSP.getString("IMAGE_URL","")

            val TechnologyPartnerImage  = IMAGEURL + TechnologyPartnerImageSP.getString("TechnologyPartnerImage", "")
            PicassoTrustAll.getInstance(this@LoginActivity)!!.load(TechnologyPartnerImage).error(R.drawable.svg_trans).into(img_technology)

            val AppIconImageCode  = IMAGEURL + AppIconImageCodeSP.getString("AppIconImageCode", "")
            PicassoTrustAll.getInstance(this@LoginActivity)!!.load(AppIconImageCode).error(R.drawable.svg_trans).into(img_logo)
        }catch (e : Exception){

        }



    }

    private fun CheckTestingMobile() {
        val BANK_KEYSP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
        val TestingURLpref = context.getSharedPreferences(Config.SHARED_PREF10, 0)
        val TestingImageURLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF15, 0)
        val TestingMobileNopref = context.getSharedPreferences(Config.SHARED_PREF11, 0)
        val TestingBankKeypref = context.getSharedPreferences(Config.SHARED_PREF12, 0)
        val Testingsslcertificatepref = context.getSharedPreferences(Config.SHARED_PREF13, 0)

        Log.e(TAG,"1021     Mobile     "+TestingMobileNopref.getString("TestingMobileNo", null))
        if(TestingMobileNopref.getString("TestingMobileNo", null)!=null
            && strEPhone.equals(TestingMobileNopref.getString("TestingMobileNo", null))){

                Log.e(TAG,"1022   Testing")

            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            val BASE_URLEditer = BASE_URLSP.edit()
            BASE_URLEditer.putString("BASE_URL", TestingURLpref.getString("TestingURL", null))
            BASE_URLEditer.commit()

            val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
            val IMAGE_URLEditer = IMAGE_URLSP.edit()
            IMAGE_URLEditer.putString("IMAGE_URL", TestingImageURLSP.getString("TestingImageURL", null))
            IMAGE_URLEditer.commit()

            val CERT_NAMESP = context.getSharedPreferences(Config.SHARED_PREF8, 0)
            val CERT_NAMEEditer = CERT_NAMESP.edit()
            CERT_NAMEEditer.putString("CERT_NAME",Testingsslcertificatepref.getString("Testingsslcertificate", null))
            CERT_NAMEEditer.commit()

            val BANK_KEYESP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
            val BANK_KEYEditer = BANK_KEYESP.edit()
            BANK_KEYEditer.putString("BANK_KEY", TestingBankKeypref.getString("TestingBankKey", null))
            BANK_KEYEditer.commit()

            validation()

        }else{
            Log.e(TAG,"1023   Live")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            val BASE_URLEditer = BASE_URLSP.edit()
            BASE_URLEditer.putString("BASE_URL", BASE_URLSP.getString("BASE_URL", null))
            BASE_URLEditer.commit()

            val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
            val IMAGE_URLEditer = IMAGE_URLSP.edit()
            IMAGE_URLEditer.putString("IMAGE_URL", IMAGE_URLSP.getString("IMAGE_URL", null))
            IMAGE_URLEditer.commit()

            val CERT_NAMESP = context.getSharedPreferences(Config.SHARED_PREF8, 0)
            val CERT_NAMEEditer = CERT_NAMESP.edit()
            CERT_NAMEEditer.putString("CERT_NAME",CERT_NAMESP.getString("CERT_NAME", null))
            CERT_NAMEEditer.commit()

            val BANK_KEYESP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
            val BANK_KEYEditer = BANK_KEYESP.edit()
            BANK_KEYEditer.putString("BANK_KEY", BANK_KEYSP.getString("BANK_KEY", null))
            BANK_KEYEditer.commit()

            validation()
        }
    }

    private fun validation(){

        try {
            Log.e(TAG,"1024  validation ")
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
                                    if (countLogin == 0) {
                                        countLogin++
                                        Log.e(TAG, "msg   login   " + msg)
                                        val jObject = JSONObject(msg)
                                        if (jObject.getString("StatusCode") == "0") {

                                            Log.e(TAG,"5007771    ")
                                            var jobj = jObject.getJSONObject("UserLoginDetails")



                                            try {

                                                Log.e(TAG,"50077721    "+jobj)
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
                                                UserNameEditer.putString(
                                                    "UserName",
                                                    jobj.getString("UserName")
                                                )
                                                UserNameEditer.commit()
                                                val AddressSP = applicationContext.getSharedPreferences(
                                                    Config.SHARED_PREF3,
                                                    0
                                                )
                                                val AddressEditer = AddressSP.edit()
                                                AddressEditer.putString(
                                                    "Address",
                                                    jobj.getString("Address")
                                                )
                                                AddressEditer.commit()
                                                val MobileNumberSP =
                                                    applicationContext.getSharedPreferences(
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


                                                val UserCodeSP = applicationContext.getSharedPreferences(
                                                    Config.SHARED_PREF36,
                                                    0
                                                )
                                                val UserCodeEditer = UserCodeSP.edit()
                                                UserCodeEditer.putString(
                                                    "UserCode",
                                                    jobj.getString("UserCode")
                                                )
                                                UserCodeEditer.commit()

                                                val FK_BranchSP = applicationContext.getSharedPreferences(
                                                    Config.SHARED_PREF37,
                                                    0
                                                )
                                                val FK_BranchEditer = FK_BranchSP.edit()
                                                FK_BranchEditer.putString(
                                                    "FK_Branch",
                                                    jobj.getString("FK_Branch")
                                                )
                                                FK_BranchEditer.commit()

                                                val FK_BranchTypeSP =
                                                    applicationContext.getSharedPreferences(
                                                        Config.SHARED_PREF38,
                                                        0
                                                    )
                                                val FK_BranchTypeEditer = FK_BranchTypeSP.edit()
                                                FK_BranchTypeEditer.putString(
                                                    "FK_BranchType",
                                                    jobj.getString("FK_BranchType")
                                                )
                                                FK_BranchTypeEditer.commit()

                                                val FK_CompanySP = applicationContext.getSharedPreferences(
                                                    Config.SHARED_PREF39,
                                                    0
                                                )
                                                val FK_CompanyEditer = FK_CompanySP.edit()
                                                FK_CompanyEditer.putString(
                                                    "FK_Company",
                                                    jobj.getString("FK_Company")
                                                )
                                                FK_CompanyEditer.commit()

                                                val FK_BranchCodeUserSP =
                                                    applicationContext.getSharedPreferences(
                                                        Config.SHARED_PREF40,
                                                        0
                                                    )
                                                val FK_BranchCodeUserEditer = FK_BranchCodeUserSP.edit()
                                                FK_BranchCodeUserEditer.putString(
                                                    "FK_BranchCodeUser",
                                                    jobj.getString("FK_BranchCodeUser")
                                                )
                                                FK_BranchCodeUserEditer.commit()

                                                val FK_UserRoleSP = applicationContext.getSharedPreferences(
                                                    Config.SHARED_PREF41,
                                                    0
                                                )
                                                val FK_UserRoleEditer = FK_UserRoleSP.edit()
                                                FK_UserRoleEditer.putString(
                                                    "FK_UserRole",
                                                    jobj.getString("FK_UserRole")
                                                )
                                                FK_UserRoleEditer.commit()

                                                val UserRoleSP = applicationContext.getSharedPreferences(
                                                    Config.SHARED_PREF42,
                                                    0
                                                )
                                                val UserRoleEditer = UserRoleSP.edit()
                                                UserRoleEditer.putString(
                                                    "UserRole",
                                                    jobj.getString("UserRole")
                                                )
                                                UserRoleEditer.commit()

                                                val IsAdminSP = applicationContext.getSharedPreferences(
                                                    Config.SHARED_PREF43,
                                                    0
                                                )
                                                val IsAdminEditer = IsAdminSP.edit()
                                                IsAdminEditer.putString(
                                                    "IsAdmin",
                                                    jobj.getString("IsAdmin")
                                                )
                                                IsAdminEditer.commit()

                                                val IsManagerSP = applicationContext.getSharedPreferences(Config.SHARED_PREF75, 0)
                                                val IsManagerEditer = IsManagerSP.edit()
                                                IsManagerEditer.putString("IsManager",jobj.getString("IsManager"))
                                                IsManagerEditer.commit()

                                                val ID_UserSP = applicationContext.getSharedPreferences(
                                                    Config.SHARED_PREF44,
                                                    0
                                                )
                                                val ID_UserEditer = ID_UserSP.edit()
                                                ID_UserEditer.putString(
                                                    "ID_User",
                                                    jobj.getString("ID_User")
                                                )
                                                ID_UserEditer.commit()

                                                val BranchNameSP = applicationContext.getSharedPreferences(
                                                    Config.SHARED_PREF45,
                                                    0
                                                )
                                                val BranchNameEditer = BranchNameSP.edit()
                                                BranchNameEditer.putString(
                                                    "BranchName",
                                                    jobj.getString("BranchName")
                                                )
                                                BranchNameEditer.commit()


                                                Log.e(TAG,"5007773    ")
                                                val CompanyCategorySP = applicationContext.getSharedPreferences(Config.SHARED_PREF46, 0)
                                                val CompanyCategoryEditer = CompanyCategorySP.edit()
                                                CompanyCategoryEditer.putString("CompanyCategory", jobj.getString("CompanyCategory"))
                                                CompanyCategoryEditer.commit()
                                                Log.e(TAG,"5007774    ")
                                                val ModuleListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF54, 0)
                                                val ModuleListEditer = ModuleListSP.edit()
                                                ModuleListEditer.putString("ModuleList", jobj.getString("ModuleList"))
                                                ModuleListEditer.commit()
                                                Log.e(TAG,"5007775    ")
                                                val FK_DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF55, 0)
                                                val FK_DepartmentEditer = FK_DepartmentSP.edit()
                                                FK_DepartmentEditer.putString("FK_Department", jobj.getString("FK_Department"))
                                                FK_DepartmentEditer.commit()
                                                Log.e(TAG,"5007776    ")
                                                val DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF56, 0)
                                                val DepartmentEditer = DepartmentSP.edit()
                                                DepartmentEditer.putString("Department", jobj.getString("Department"))
                                                DepartmentEditer.commit()
                                                Log.e(TAG,"5007777    ")
                                                val UtilityListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF57, 0)
                                                val UtilityListEditer = UtilityListSP.edit()
                                                UtilityListEditer.putString("UtilityList", jobj.getString("UtilityList"))
                                                UtilityListEditer.commit()
                                                Log.e(TAG,"5007778    ")
                                                val LocLongitudeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF58, 0)
                                                val LocLongitudeEditer = LocLongitudeSP.edit()
                                                LocLongitudeEditer.putString("LocLongitude", jobj.getString("LocLongitude"))
                                                LocLongitudeEditer.commit()
                                                Log.e(TAG,"5007779    ")
                                                val LocLattitudeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF59, 0)
                                                val LocLattitudeEditer = LocLattitudeSP.edit()
                                                LocLattitudeEditer.putString("LocLattitude", jobj.getString("LocLattitude"))
                                                LocLattitudeEditer.commit()
                                                Log.e(TAG,"50077710    ")

                                                val LocLocationNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF60, 0)
                                                val LocLocationNameEditer = LocLocationNameSP.edit()
                                                LocLocationNameEditer.putString("LocLocationName", jobj.getString("LocLocationName"))
                                                LocLocationNameEditer.commit()
                                                Log.e(TAG,"50077711    ")
                                                val EnteredDateSP = applicationContext.getSharedPreferences(Config.SHARED_PREF61, 0)
                                                val EnteredDateEditer = EnteredDateSP.edit()
                                                EnteredDateEditer.putString("EnteredDate", jobj.getString("EnteredDate"))
                                                EnteredDateEditer.commit()
                                                Log.e(TAG,"50077712    ")
                                                val EnteredTimeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF62, 0)
                                                val EnteredTimeEditer = EnteredTimeSP.edit()
                                                EnteredTimeEditer.putString("EnteredTime", jobj.getString("EnteredTime"))
                                                EnteredTimeEditer.commit()
                                                Log.e(TAG,"50077713    ")
                                                val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)
                                                val StatusEditer = StatusSP.edit()
                                                StatusEditer.putString("Status", jobj.getString("Status"))
                                                StatusEditer.commit()
                                                Log.e(TAG,"50077714    ")
                                                val CRMDetailsSP = applicationContext.getSharedPreferences(Config.SHARED_PREF79, 0)
                                                val CRMDetailsEditer = CRMDetailsSP.edit()
                                                CRMDetailsEditer.putString("CRMDetails", jobj.getString("CRMDetails"))
                                                CRMDetailsEditer.commit()
                                                Log.e(TAG,"50077715    ")
                                                val FollowUpDetailsSP = applicationContext.getSharedPreferences(Config.SHARED_PREF80, 0)
                                                val FollowUpDetailsEditer = FollowUpDetailsSP.edit()
                                                FollowUpDetailsEditer.putString("FollowUpDetails", jobj.getString("FollowUpDetails"))
                                                FollowUpDetailsEditer.commit()
                                                Log.e(TAG,"50077716    ")
                                                val PSValueSP = applicationContext.getSharedPreferences(Config.SHARED_PREF81, 0)
                                                val PSValueEditer = PSValueSP.edit()
                                                PSValueEditer.putString("PSValue", jobj.getString("PSValue"))
                                                PSValueEditer.commit()
                                                Log.e(TAG,"50077717    ")
                                                val PnotificationIDSP = applicationContext.getSharedPreferences(Config.SHARED_PREF82, 0)
                                                val PnotificationIDEditer = PnotificationIDSP.edit()
                                                PnotificationIDEditer.putString("PnotificationID", "0")
                                                PnotificationIDEditer.commit()

                                                val ID_TokenUserSP = applicationContext.getSharedPreferences(Config.SHARED_PREF85, 0)
                                                val ID_TokenUserEditer = ID_TokenUserSP.edit()
                                                ID_TokenUserEditer.putString("ID_TokenUser", jobj.getString("ID_TokenUser"))
                                                ID_TokenUserEditer.commit()

                                                Log.e(TAG,"50077718    ")

                                                val i = Intent(this@LoginActivity, OTPActivity::class.java)
                                                startActivity(i)
                                                finish()

                                            }catch (e: Exception){

                                                Log.e(TAG,"50077719    "+e.toString())
                                            }




                                        } else {


                                            try {

                                                val dialog1 = Dialog(this)
                                                dialog1!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                                dialog1!!.setCancelable(false)
                                                dialog1!!.setContentView(R.layout.alert_invalid_user)
                                                dialog1!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

                                                val tv_invalid = dialog1!!.findViewById(R.id.tv_invalid) as TextView
                                                val btn_Gotit = dialog1!!.findViewById(R.id.btn_Gotit) as Button

                                                tv_invalid.setText(jObject.getString("EXMessage"))

                                                btn_Gotit!!.setOnClickListener {
                                                    dialog1.dismiss()

                                                }

                                                dialog1!!.show()
                                                dialog1!!.getWindow()!!.setLayout(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                                );


//
//                                                val dialog1 = Dialog(this)
//                                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
//                                                dialog1!!.getWindow()!!.setLayout(
//                                                    ViewGroup.LayoutParams.MATCH_PARENT,
//                                                    ViewGroup.LayoutParams.WRAP_CONTENT
//                                                );
//                                                dialog1.setCancelable(false)
//                                                dialog1.setContentView(R.layout.alert_invalid_user)
//                                                val btn_Gotit = dialog1.findViewById(R.id.btn_Gotit) as Button
//                                                val tv_invalid = dialog1.findViewById(R.id.tv_invalid) as TextView
//                                                tv_invalid.setText(jObject.getString("EXMessage"))
//
//                                                btn_Gotit.setOnClickListener {
//                                                    dialog1.dismiss()
//                                                }
//
//                                                dialog1.show()
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }



//                                            val builder = AlertDialog.Builder(
//                                                this@LoginActivity,
//                                                R.style.MyDialogTheme
//                                            )
//                                            builder.setMessage(jObject.getString("EXMessage"))
//                                            builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                            }
//                                            val alertDialog: AlertDialog = builder.create()
//                                            alertDialog.setCancelable(false)
//                                            alertDialog.show()
                                        }
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
//                        Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                            .show()
//                        val snackbar = Snackbar.make(Config.rootView, "Offline", Snackbar.LENGTH_INDEFINITE)
//                        snackbar.setBackgroundTint(Color.parseColor("#FF4848"))
//                        snackbar.show()
                    }
                }
            }
        }catch (e : Exception){

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result!!)
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

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
}
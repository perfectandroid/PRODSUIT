package com.perfect.prodsuit.Helper

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Service.NotificationLocationService
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.*


object Config {

    var CODE_STOCK_LIST: Int? = 1001

    const val SHARED_PREF = "loginsession"
    const val SHARED_PREF1 = "FK_Employee"
    const val SHARED_PREF2 = "UserName"
    const val SHARED_PREF3 = "Address"
    const val SHARED_PREF4 = "MobileNumber"
    const val SHARED_PREF5 = "Token"
    const val SHARED_PREF6 = "Email"
    const val SHARED_PREF7 = "BASE_URL"
    const val SHARED_PREF8 = "CERT_NAME"
    const val SHARED_PREF9 = "BANK_KEY"
    const val SHARED_PREF10 = "TestingURL"
    const val SHARED_PREF11 = "TestingMobileNo"
    const val SHARED_PREF12 = "TestingBankKey"
    const val SHARED_PREF13 = "Testingsslcertificate"
    const val SHARED_PREF14 = "Loginmobilenumber"
    const val SHARED_PREF15 = "TestingImageURL"
    const val SHARED_PREF16 = "BroadCall"

    const val SHARED_PREF17 ="companyCode"
    const val SHARED_PREF18 ="commonApp"
    const val SHARED_PREF19 ="AppIconImageCode"
    const val SHARED_PREF20 ="TechnologyPartnerImage"
    const val SHARED_PREF21 ="ProductName"
    const val SHARED_PREF22 ="PlayStoreLink"
    const val SHARED_PREF23 ="mpinStatus"


//    const val SHARED_PREF17 = "LS_LocLatitude"
//    const val SHARED_PREF18 = "LS_LocLongitude"
//    const val SHARED_PREF19 = "LS_LocationName"
//    const val SHARED_PREF20 = "LS_FK_Employee"
//    const val SHARED_PREF21 = "LS_Name"
//    const val SHARED_PREF22 = "LS_Address"
//    const val SHARED_PREF23 = "LS_LoginDate"
//    const val SHARED_PREF24 = "LS_LoginTime"
//    const val SHARED_PREF25 = "LS_LoginMode"
//    const val SHARED_PREF26 = "LS_LoginStauats"
//    const val SHARED_PREF27 = "LS_DutyStatus"

    const val SHARED_PREF28 = "Notifctn_Id"
    const val SHARED_PREF29 = "IMAGE_URL"
    const val SHARED_PREF30 = "LOGIN_DATETIME"
    const val SHARED_PREF31 = "ABOUTUS"
    const val SHARED_PREF32 = "RESELLER_NAME"
    const val SHARED_PREF33 = "CONTACT_NUMBER"
    const val SHARED_PREF34 = "CONTACT_EMAIL"
    const val SHARED_PREF35 = "CONTACT_ADDRESS"

    const val SHARED_PREF36 = "UserCode"  // EntrBy
    const val SHARED_PREF37 = "FK_Branch"
    const val SHARED_PREF38 = "FK_BranchType"
    const val SHARED_PREF39 = "FK_Company"
    const val SHARED_PREF40 = "FK_BranchCodeUser"
    const val SHARED_PREF41 = "FK_UserRole"  // UserGroup
    const val SHARED_PREF42 = "UserRole"
    const val SHARED_PREF43 = "IsAdmin"
    const val SHARED_PREF44 = "ID_User"  // FK_User
    const val SHARED_PREF45 = "BranchName"
    const val SHARED_PREF46 = "CompanyCategory"

    const val SHARED_PREF47 = "Customerid"
    const val SHARED_PREF48 = "CusMode"
    const val SHARED_PREF49 = "Productid"

    const val SHARED_PREF50 = "WarrantyCount"
    const val SHARED_PREF51 = "ServiceHistoryCount"
    const val SHARED_PREF52 = "SalesCount"
    const val SHARED_PREF53 = "CustomerDueCount"

    const val SHARED_PREF54 = "ModuleList" // Rights
    const val SHARED_PREF55 = "FK_Department"
    const val SHARED_PREF56 = "Department"

    const val SHARED_PREF57 = "UtilityList"
    const val SHARED_PREF58 = "LocLongitude"
    const val SHARED_PREF59 = "LocLattitude"
    const val SHARED_PREF60 = "LocLocationName"
    const val SHARED_PREF61 = "EnteredDate"
    const val SHARED_PREF62 = "EnteredTime"
    const val SHARED_PREF63 = "Status"  // String False / True
    const val SHARED_PREF64 = "isNotification"  //NotificationBack Preesed
    const val SHARED_PREF65 = "fireBaseToken"  //FireBase User Token
    const val SHARED_PREF66 = "deviceID"  //FireBase User Token
    const val SHARED_PREF67 = "AUDIO"  //FireBase User Token

    const val SHARED_PREF68 = "Fbase_Name"
    const val SHARED_PREF69 = "Fbase_Mobile"
    const val SHARED_PREF70 = "timestamp"
    const val SHARED_PREF71 = "custbalid"
    const val SHARED_PREF72 = "idcustsrvceregist"
    const val SHARED_PREF73 = "idcustsrvceregistproductdetail"
    const val SHARED_PREF74 = "mpin"


    var width = 0
    var height = 0


    const val SCANNER_CODE = 501
    const val SOME_TECHNICAL_ISSUES = "Some Technical Issues."
    const val PLEASE_TRY_AGAIN = "Some Technical Issues, Please try again in sometime"
    const val notificationBack = false // String False / True
    const val INVALID_MOBILE = "Invalid Mobile Number"

    fun getHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { hostname, session -> true }
    }

    fun getWrappedTrustManagers(trustManagers: Array<TrustManager>): Array<TrustManager> {
        val originalTrustManager = trustManagers[0] as X509TrustManager
        return arrayOf(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return originalTrustManager.acceptedIssuers
            }
            override fun checkClientTrusted(certs: Array<X509Certificate>?, authType: String) {
                try {
                    if (certs != null && certs.size > 0) {
                        certs[0].checkValidity()
                    } else {
                        originalTrustManager.checkClientTrusted(certs, authType)
                    }
                } catch (e: CertificateException) {
                    Log.w("checkClientTrusted", e.toString())
                }
            }
            override fun checkServerTrusted(certs: Array<X509Certificate>?, authType: String) {
                try {
                    if (certs != null && certs.size > 0) {
                        certs[0].checkValidity()
                    } else {
                        originalTrustManager.checkServerTrusted(certs, authType)
                    }
                } catch (e: CertificateException) {
                    Log.w("checkServerTrusted", e.toString())
                }
            }
        })
    }

    @Throws(CertificateException::class,  KeyStoreException::class,IOException::class, NoSuchAlgorithmException::class, KeyManagementException::class )
    fun getSSLSocketFactory(context: Context): SSLSocketFactory {
        val cf = CertificateFactory.getInstance("X.509")
        val CERT_NAMESP = context.getSharedPreferences(SHARED_PREF8, 0)
        val caInput = context!!.assets.open(CERT_NAMESP.getString("CERT_NAME", null)!!)
        val ca = cf.generateCertificate(caInput)
        caInput.close()
        val keyStore = KeyStore.getInstance("BKS")
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)
        val wrappedTrustManagers = getWrappedTrustManagers(tmf.trustManagers)
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, wrappedTrustManagers, null)
        return sslContext.socketFactory
    }


    fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder =  OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
            builder
        } catch (e: java.lang.Exception) {
            throw RuntimeException(e)
        }
    }

    object Utils {
        fun hideSoftKeyBoard(context: Context, view: View) {
            try {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    object ConnectivityUtils {
        @SuppressLint("MissingPermission")
        fun isConnected(context: Context): Boolean {
            val connectivityManager = context.applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    fun showCustomToast(message: String, context: Context)
    {

//        val customSnackView: View = LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null)
//        val layout = LayoutInflater.from(context).inflate (
//            R.layout.custom_snackbar,
//            context.findViewById(R.id.toast_container)
//        )
//
//        // set the text of the TextView of the message
////        val textView = layout.findViewById<TextView>(R.id.toast_text)
////        textView.text = message
//
//        // use the application extension function
//        this.apply {
//            setGravity(Gravity.BOTTOM, 0, 40)
//            duration = Toast.LENGTH_LONG
//            view = layout
//            show()
//        }

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)

        val textView1 = layout.findViewById<TextView>(R.id.textView1)
        textView1.text = message

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()


    }


    fun showCustomToast1(message: String, context: Context)
    {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.custome_toast1, null)

        val textView1 = layout.findViewById<TextView>(R.id.textView1)
        textView1.text = message

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()


    }

    @SuppressLint("ResourceAsColor")
    fun snackBars(context: Context, view: View, message: String) {
//        val snackbar: Snackbar = Snackbar.make(view, ""+message, Snackbar.LENGTH_LONG)
//        snackbar.setActionTextColor(Color.WHITE)
//        snackbar.setBackgroundTint(context.resources.getColor(R.color.black))
//        snackbar.show()


        val snackbar = Snackbar.make(view, ""+message, Snackbar.LENGTH_SHORT)
        val sbView = snackbar.view
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        val textView: TextView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        val typeface = ResourcesCompat.getFont(context, R.font.myfont)
        textView.setTypeface(typeface)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15f)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackbar.show()

    }

    fun snackBarWarning(context: Context, view: View, message: String) {
//        val snackbar: Snackbar = Snackbar.make(view, ""+message, Snackbar.LENGTH_LONG)
//        snackbar.setActionTextColor(Color.WHITE)
//        snackbar.setBackgroundTint(context.resources.getColor(R.color.black))
//        snackbar.show()


        val snackbar = Snackbar.make(view, ""+message, Snackbar.LENGTH_SHORT)
        val sbView = snackbar.view
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        val textView: TextView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        val typeface = ResourcesCompat.getFont(context, R.font.myfont)
        textView.setTypeface(typeface)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15f)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackbar.show()



    }

    fun addSpaceBetweenLowerAndUpper(text: String): String {
        val regex = "(?<=\\p{Ll})(?=\\p{Lu})".toRegex()
        return text.replace(regex, " ")
    }

    fun getActionTypes(): String {

        var result =""
        try {

            val jsonObject1 = JSONObject()
            val jsonObject = JSONObject()
            val array = JSONArray()


            var obj = JSONObject()
            obj.put("action_id", "1")
            obj.put("action", "Add Remark")
            array.put(obj)

            obj = JSONObject()
            obj.put("action_id", "2")
            obj.put("action", "Site Visit")
            array.put(obj)

            obj = JSONObject()
            obj.put("action_id", "3")
            obj.put("action", "Message")
            array.put(obj)

            obj = JSONObject()
            obj.put("action_id", "4")
            obj.put("action", "Quotation")
            array.put(obj)

            jsonObject.put("actionTypeDetails", array)
            jsonObject1.put("actionType", jsonObject)
            Log.e("JsonObject", jsonObject.toString())
            result = jsonObject1.toString()

        } catch (e: JSONException) {
            e.printStackTrace()
            result= ""
        }

        return result

    }

    fun createLocation(): String{

        var result =""
        try {

            val jsonObject1 = JSONObject()
            val jsonObject = JSONObject()
            val array = JSONArray()


            var obj = JSONObject()
            obj.put("User", "Ranjith")
            obj.put("LocLattitude", "11.2590")
            obj.put("LocLongitude", "75.7863")
            obj.put("LocLocationName", "CALICUT ")
            array.put(obj)

            obj = JSONObject()
            obj.put("User", "Aneesh")
            obj.put("LocLattitude", "11.887509")
            obj.put("LocLongitude", "75.371029")
            obj.put("LocLocationName", "KANNUR")
            array.put(obj)

            obj = JSONObject()
            obj.put("User", "Aswathi")
            obj.put("LocLattitude", "15.2993")
            obj.put("LocLongitude", "74.1240")
            obj.put("LocLocationName", "GOA")
            array.put(obj)

            obj = JSONObject()
            obj.put("User", "Akshay")
            obj.put("LocLattitude", "19.9975")
            obj.put("LocLongitude", "73.7898")
            obj.put("LocLocationName", "NASIK")
            array.put(obj)

            obj = JSONObject()
            obj.put("User", "Jithin")
            obj.put("LocLattitude", "17.3850")
            obj.put("LocLongitude", "78.4867")
            obj.put("LocLocationName", "Hyderabad")
            array.put(obj)

            obj = JSONObject()
            obj.put("User", "Salil")
            obj.put("LocLattitude", "28.7041")
            obj.put("LocLongitude", "77.1025")
            obj.put("LocLocationName", "Delhi")
            array.put(obj)

//            obj = JSONObject()
//            obj.put("User", "Anvin")
//            obj.put("LocLattitude", "11.887509")
//            obj.put("LocLongitude", "75.371029")
//            obj.put("LocLocationName", "KANNUR MOFUSIAL BUS STAND")
//            array.put(obj)
//
//            obj = JSONObject()
//            obj.put("User", "Dilsha")
//            obj.put("LocLattitude", "11.887509")
//            obj.put("LocLongitude", "75.371029")
//            obj.put("LocLocationName", "KANNUR MOFUSIAL BUS STAND")
//            array.put(obj)
//
//
//            obj = JSONObject()
//            obj.put("User", "Nihal")
//            obj.put("LocLattitude", "11.887509")
//            obj.put("LocLongitude", "75.371029")
//            obj.put("LocLocationName", "KANNUR MOFUSIAL BUS STAND")
//            array.put(obj)
//
//            obj = JSONObject()
//            obj.put("User", "Bhaghyesh")
//            obj.put("LocLattitude", "11.887509")
//            obj.put("LocLongitude", "75.371029")
//            obj.put("LocLocationName", "KANNUR MOFUSIAL BUS STAND")
//            array.put(obj)
//
//            obj = JSONObject()
//            obj.put("User", "Riyas")
//            obj.put("LocLattitude", "11.887509")
//            obj.put("LocLongitude", "75.371029")
//            obj.put("LocLocationName", "KANNUR MOFUSIAL BUS STAND")
//            array.put(obj)
//
//            obj = JSONObject()
//            obj.put("User", "Sona")
//            obj.put("LocLattitude", "11.887509")
//            obj.put("LocLongitude", "75.371029")
//            obj.put("LocLocationName", "KANNUR MOFUSIAL BUS STAND")
//            array.put(obj)
//
//            obj = JSONObject()
//            obj.put("User", "Shilpa")
//            obj.put("LocLattitude", "11.887509")
//            obj.put("LocLongitude", "75.371029")
//            obj.put("LocLocationName", "KANNUR MOFUSIAL BUS STAND")
//            array.put(obj)
//
//            obj = JSONObject()
//            obj.put("User", "Levi")
//            obj.put("LocLattitude", "11.887509")
//            obj.put("LocLongitude", "75.371029")
//            obj.put("LocLocationName", "KANNUR MOFUSIAL BUS STAND")
//            array.put(obj)

            jsonObject.put("LocationDetails", array)
            jsonObject1.put("LocationType", jsonObject)
            Log.e("JsonObject 311   ", jsonObject.toString())
            result = jsonObject1.toString()

        } catch (e: JSONException) {
            e.printStackTrace()
            result= ""
        }

        return result
    }

    fun disableClick(v : View) {
        v.isEnabled = false
        v.postDelayed({
            v.isEnabled = true
        },3000)
    }

    fun disableClickOnSec(v : View) {
        v.isEnabled = false
        v.postDelayed({
            v.isEnabled = true
        },1000)
    }

    fun getHeight(context: Context) : Int{
        height = context.resources.displayMetrics.heightPixels
        return height.toInt()
    }

    fun getWidth(context: Context) : Int {
        width = context.resources.displayMetrics.widthPixels
        return width.toInt()

    }

    fun changeTwoDecimel(toString: String) : String {
        var value = "0.00"
        if (toString.equals("")){
            value = "0.00"
        }else{
            var valu = toString.toFloat()
            value = String.format("%.2f",valu)

        }

        return value
    }

    fun logOut(context : Context) {


        try {

            deleteFcmToken(context)

            val mpinSP = context.getSharedPreferences(
                Config.SHARED_PREF74,
                0
            )
            val mpinEditer = mpinSP.edit()
            mpinEditer.putString("mpin", "")
            mpinEditer.commit()

            val loginSP = context.getSharedPreferences(SHARED_PREF, 0)
            val loginEditer = loginSP.edit()
            loginEditer.putString("loginsession", "No")
            loginEditer.commit()

            val FK_EmployeeSP = context.getSharedPreferences(SHARED_PREF1, 0)
            val FK_EmployeeEditer = FK_EmployeeSP.edit()
            FK_EmployeeEditer.putString("FK_Employee", "")
            FK_EmployeeEditer.commit()

            val UserNameSP = context.getSharedPreferences(SHARED_PREF2, 0)
            val UserNameEditer = UserNameSP.edit()
            UserNameEditer.putString("UserName", "")
            UserNameEditer.commit()

            val AddressSP = context.getSharedPreferences(SHARED_PREF3, 0)
            val AddressEditer = AddressSP.edit()
            AddressEditer.putString("Address", "")
            AddressEditer.commit()

            val MobileNumberSP = context.getSharedPreferences(SHARED_PREF4, 0)
            val MobileNumberEditer = MobileNumberSP.edit()
            MobileNumberEditer.putString("MobileNumber", "")
            MobileNumberEditer.commit()

            val TokenSP = context.getSharedPreferences(SHARED_PREF5, 0)
            val TokenEditer = TokenSP.edit()
            TokenEditer.putString("Token", "")
            TokenEditer.commit()

            val EmailSP = context.getSharedPreferences(SHARED_PREF6, 0)
            val EmailEditer = EmailSP.edit()
            EmailEditer.putString("Email", "")
            EmailEditer.commit()

            val BASE_URLSP = context.getSharedPreferences(SHARED_PREF7, 0)
            val BASE_URLEditer = BASE_URLSP.edit()
            BASE_URLEditer.putString("BASE_URL", "")
            BASE_URLEditer.commit()

            /////////

            val IMAGE_URLSP = context.getSharedPreferences(SHARED_PREF29, 0)
            val IMAGE_URLEditer = IMAGE_URLSP.edit()
            IMAGE_URLEditer.putString("IMAGE_URL", "")
            IMAGE_URLEditer.commit()

            ///////////////


            val CERT_NAMESP = context.getSharedPreferences(SHARED_PREF8, 0)
            val CERT_NAMEEditer = CERT_NAMESP.edit()
            CERT_NAMEEditer.putString("CERT_NAME", "")
            CERT_NAMEEditer.commit()

            val BANK_KEYESP = context.getSharedPreferences(SHARED_PREF9, 0)
            val BANK_KEYEditer = BANK_KEYESP.edit()
            BANK_KEYEditer.putString("BANK_KEY", "")
            BANK_KEYEditer.commit()


            val TestingURLSP = context.getSharedPreferences(SHARED_PREF10, 0)
            val TestingURLEditer = TestingURLSP.edit()
            TestingURLEditer.putString("TestingURL", "")
            TestingURLEditer.commit()


            val TestingMobileNoSP = context.getSharedPreferences(SHARED_PREF11, 0)
            val TestingMobileNoEditer = TestingMobileNoSP.edit()
            TestingMobileNoEditer.putString("TestingMobileNo", "")
            TestingMobileNoEditer.commit()

            val TestingBankKeySP = context.getSharedPreferences(SHARED_PREF12, 0)
            val TestingBankKeyEditer = TestingBankKeySP.edit()
            TestingBankKeyEditer.putString("TestingBankKey", "")
            TestingBankKeyEditer.commit()

            val TestingsslcertificateSP = context.getSharedPreferences(SHARED_PREF13, 0)
            val TestingsslcertificateEditer = TestingsslcertificateSP.edit()
            TestingsslcertificateEditer.putString("Testingsslcertificate", "")
            TestingsslcertificateEditer.commit()

            val loginmobileSP = context.getSharedPreferences(SHARED_PREF14, 0)
            val loginmobileEditer = loginmobileSP.edit()
            loginmobileEditer.putString("Loginmobilenumber", "")
            loginmobileEditer.commit()


            val TestingImageURLSP = context.getSharedPreferences(SHARED_PREF15, 0)
            val TestingImageURLEditer = TestingImageURLSP.edit()
            TestingImageURLEditer.putString("TestingImageURL", "")
            TestingImageURLEditer.commit()

            val BroadCallSP = context.getSharedPreferences(SHARED_PREF16, 0)
            val BroadCallEditer = BroadCallSP.edit()
            BroadCallEditer.putString("BroadCall", "")
            BroadCallEditer.putString("ID_LeadGenerate", "")
            BroadCallEditer.putString("ID_LeadGenerateProduct", "")
            BroadCallEditer.putString("FK_Employee", "")
            BroadCallEditer.putString("AssignedTo", "")
            BroadCallEditer.commit()


            val companyCodeSP = context.getSharedPreferences(SHARED_PREF17, 0)
            val companyCodeEditer = companyCodeSP.edit()
            companyCodeEditer.putString("companyCode", "")
            companyCodeEditer.commit()

            val commonAppSP = context.getSharedPreferences(SHARED_PREF18, 0)
            val commonAppEditer = commonAppSP.edit()
            commonAppEditer.putString("commonApp", "")
            commonAppEditer.commit()


            val AppIconImageCodeSP = context.getSharedPreferences(SHARED_PREF19, 0)
            val AppIconImageCodeEditer = AppIconImageCodeSP.edit()
            AppIconImageCodeEditer.putString("AppIconImageCode", "")
            AppIconImageCodeEditer.commit()

            val CompanyLogoImageCodeSP = context.getSharedPreferences(SHARED_PREF20, 0)
            val CompanyLogoImageCodeEditer = CompanyLogoImageCodeSP.edit()
            CompanyLogoImageCodeEditer.putString("CompanyLogoImageCode", "")
            CompanyLogoImageCodeEditer.commit()

            val ProductNameSP = context.getSharedPreferences(SHARED_PREF21, 0)
            val ProductNameEditer = ProductNameSP.edit()
            ProductNameEditer.putString("ProductName", "")
            ProductNameEditer.commit()

            val PlayStoreLinkSP = context.getSharedPreferences(SHARED_PREF22, 0)
            val PlayStoreLinkEditer = PlayStoreLinkSP.edit()
            PlayStoreLinkEditer.putString("PlayStoreLink", "")
            PlayStoreLinkEditer.commit()

            val mpinStatusSP = context.getSharedPreferences(SHARED_PREF23, 0)
            val mpinStatusEditer = mpinStatusSP.edit()
            mpinStatusEditer.putString("mpinStatus", "")
            mpinStatusEditer.commit()


            /////////////////////////


            val LOGIN_DATETIMESP = context.getSharedPreferences(SHARED_PREF30, 0)
            val LOGIN_DATETIMEEditer = LOGIN_DATETIMESP.edit()
            LOGIN_DATETIMEEditer.putString("LOGIN_DATETIME", "")
            LOGIN_DATETIMEEditer.commit()


            val ABOUTUSSP = context.getSharedPreferences(SHARED_PREF31, 0)
            val ABOUTUSEditer = ABOUTUSSP.edit()
            ABOUTUSEditer.putString("ABOUTUS", "")
            ABOUTUSEditer.commit()


            val ResellerNameSP = context.getSharedPreferences(SHARED_PREF32, 0)
            val ResellerNameEditer = ResellerNameSP.edit()
            ResellerNameEditer.putString("ResellerName","")
            ResellerNameEditer.commit()


            val ContactNumberSP = context.getSharedPreferences(SHARED_PREF33, 0)
            val ContactNumberEditer = ContactNumberSP.edit()
            ContactNumberEditer.putString("ContactNumber","")
            ContactNumberEditer.commit()


            val ContactEmailSP = context.getSharedPreferences(SHARED_PREF34, 0)
            val ContactEmailEditer = ContactEmailSP.edit()
            ContactEmailEditer.putString("ContactEmail","")
            ContactEmailEditer.commit()


            val ContactAddressSP = context.getSharedPreferences(SHARED_PREF35, 0)
            val ContactAddressEditer = ContactAddressSP.edit()
            ContactAddressEditer.putString("ContactAddress","")
            ContactAddressEditer.commit()


            val UserCodeSP = context.getSharedPreferences(SHARED_PREF36, 0)
            val UserCodeEditer = UserCodeSP.edit()
            UserCodeEditer.putString("UserCode", "")
            UserCodeEditer.commit()

            val FK_BranchSP = context.getSharedPreferences(SHARED_PREF37, 0)
            val FK_BranchEditer = FK_BranchSP.edit()
            FK_BranchEditer.putString("FK_Branch", "")
            FK_BranchEditer.commit()

            val FK_BranchTypeSP = context.getSharedPreferences(SHARED_PREF38, 0)
            val FK_BranchTypeEditer = FK_BranchTypeSP.edit()
            FK_BranchTypeEditer.putString("FK_BranchType","")
            FK_BranchTypeEditer.commit()

            val FK_CompanySP = context.getSharedPreferences(SHARED_PREF39, 0)
            val FK_CompanyEditer = FK_CompanySP.edit()
            FK_CompanyEditer.putString("FK_Company", "")
            FK_CompanyEditer.commit()

            val FK_BranchCodeUserSP = context.getSharedPreferences(SHARED_PREF40, 0)
            val FK_BranchCodeUserEditer = FK_BranchCodeUserSP.edit()
            FK_BranchCodeUserEditer.putString("FK_BranchCodeUser", "")
            FK_BranchCodeUserEditer.commit()





            val FK_UserRoleSP = context.getSharedPreferences(SHARED_PREF41, 0)
            val FK_UserRoleEditer = FK_UserRoleSP.edit()
            FK_UserRoleEditer.putString("FK_UserRole", "")
            FK_UserRoleEditer.commit()

            val UserRoleSP = context.getSharedPreferences(SHARED_PREF42, 0)
            val UserRoleEditer = UserRoleSP.edit()
            UserRoleEditer.putString("UserRole", "")
            UserRoleEditer.commit()

            val IsAdminSP = context.getSharedPreferences(SHARED_PREF43, 0)
            val IsAdminEditer = IsAdminSP.edit()
            IsAdminEditer.putString("IsAdmin", "")
            IsAdminEditer.commit()

            val ID_UserSP = context.getSharedPreferences(SHARED_PREF44, 0)
            val ID_UserEditer = ID_UserSP.edit()
            ID_UserEditer.putString("ID_User", "")
            ID_UserEditer.commit()

            val BranchNameSP = context.getSharedPreferences(SHARED_PREF45, 0)
            val BranchNameEditer = BranchNameSP.edit()
            BranchNameEditer.putString("BranchName", "")
            BranchNameEditer.commit()


            val CompanyCategorySP = context.getSharedPreferences(SHARED_PREF46, 0)
            val CompanyCategoryEditer = CompanyCategorySP.edit()
            CompanyCategoryEditer.putString("CompanyCategory", "")
            CompanyCategoryEditer.commit()

            val ModuleListSP = context.getSharedPreferences(Config.SHARED_PREF54, 0)
            val ModuleListEditer = ModuleListSP.edit()
            ModuleListEditer.putString("ModuleList", "")
            ModuleListEditer.commit()


            val FK_DepartmentSP = context.getSharedPreferences(Config.SHARED_PREF55, 0)
            val FK_DepartmentEditer = FK_DepartmentSP.edit()
            FK_DepartmentEditer.putString("FK_Department", "")
            FK_DepartmentEditer.commit()

            val DepartmentSP = context.getSharedPreferences(Config.SHARED_PREF56, 0)
            val DepartmentEditer = DepartmentSP.edit()
            DepartmentEditer.putString("Department", "")
            DepartmentEditer.commit()

            val UtilityListSP = context.getSharedPreferences(Config.SHARED_PREF57, 0)
            val UtilityListEditer = UtilityListSP.edit()
            UtilityListEditer.putString("UtilityList", "")
            UtilityListEditer.commit()

            val LocLongitudeSP = context.getSharedPreferences(Config.SHARED_PREF58, 0)
            val LocLongitudeEditer = LocLongitudeSP.edit()
            LocLongitudeEditer.putString("LocLongitude", "")
            LocLongitudeEditer.commit()

            val LocLattitudeSP = context.getSharedPreferences(Config.SHARED_PREF59, 0)
            val LocLattitudeEditer = LocLattitudeSP.edit()
            LocLattitudeEditer.putString("LocLattitude", "")
            LocLattitudeEditer.commit()


            val LocLocationNameSP = context.getSharedPreferences(Config.SHARED_PREF60, 0)
            val LocLocationNameEditer = LocLocationNameSP.edit()
            LocLocationNameEditer.putString("LocLocationName", "")
            LocLocationNameEditer.commit()

            val EnteredDateSP = context.getSharedPreferences(Config.SHARED_PREF61, 0)
            val EnteredDateEditer = EnteredDateSP.edit()
            EnteredDateEditer.putString("EnteredDate", "")
            EnteredDateEditer.commit()

            val EnteredTimeSP = context.getSharedPreferences(Config.SHARED_PREF62, 0)
            val EnteredTimeEditer = EnteredTimeSP.edit()
            EnteredTimeEditer.putString("EnteredTime", "")
            EnteredTimeEditer.commit()

            val StatusSP = context.getSharedPreferences(Config.SHARED_PREF63, 0)
            val StatusEditer = StatusSP.edit()
            StatusEditer.putString("Status", "")
            StatusEditer.commit()

            val isNotificationSP = context.getSharedPreferences(Config.SHARED_PREF64, 0)
            val isNotificationEditer = isNotificationSP.edit()
            isNotificationEditer.putString("isNotification", "")
            isNotificationEditer.commit()

            val fireBaseTokenSP = context.getSharedPreferences(Config.SHARED_PREF65, 0)
            val fireBaseTokenEditer = fireBaseTokenSP.edit()
            fireBaseTokenEditer.putString("fireBaseToken", "")
            fireBaseTokenEditer.commit()

            val deviceIDSP = context.getSharedPreferences(Config.SHARED_PREF66, 0)
            val deviceIDEditer = deviceIDSP.edit()
            deviceIDEditer.putString("deviceID", "")
            deviceIDEditer.commit()


            val isMyServiceRunning = isServiceRunning(context, NotificationLocationService::class.java)
            if (isMyServiceRunning){
                context.stopService(Intent(context, NotificationLocationService::class.java))
            }
        }catch (e : Exception){

        }



    }

    fun deleteFcmToken(context : Context) {
        try {
            FirebaseMessaging.getInstance().isAutoInitEnabled = false
            FirebaseMessaging.getInstance().deleteToken()
                .addOnCompleteListener { task: Task<Void?> ->
                    if (task.isSuccessful) {
                        // Token deleted successfully
                        Log.e("TAG  99991", "FCM Token deleted")
                    } else {
                        // Token deletion failed
                        Log.e("TAG  99991 ", "FCM Token deletion failed: " + task.exception!!.message)
                    }
                }
        }catch (e : Exception){

        }
    }

    fun getStockMode(context : Context): String {
        var result =""
        try {

            val jsonObject1 = JSONObject()
            val jsonObject = JSONObject()
            val array = JSONArray()

            var obj = JSONObject()
            obj.put("FK_StockMode", "1")
            obj.put("StockMode", "Actual Stock")
            array.put(obj)

            obj = JSONObject()
            obj.put("FK_StockMode", "2")
            obj.put("StockMode", "Return Stock")
            array.put(obj)

            jsonObject.put("stockModeDetailsList", array)
            jsonObject1.put("stockModeDetails", jsonObject)
            Log.e("JsonObject", jsonObject.toString())
            result = jsonObject1.toString()

        }catch (e :  Exception){
            result = ""
        }

        return result
    }

    fun getHomeGrid(context : Context): String {

        var result =""
        try {

            val UtilityListSP = context.getSharedPreferences(Config.SHARED_PREF57, 0)
            val jsonObj1 = JSONObject(UtilityListSP.getString("UtilityList", ""))
            var bTracker = jsonObj1!!.getString("LOCATION_TRACKING").toBoolean()
            val loginSP = context.getSharedPreferences(SHARED_PREF, 0)

            Log.e("TAG","537    "+loginSP.getString("loginsession",""));


            val ModuleListSP = context.getSharedPreferences(Config.SHARED_PREF54, 0)
          //  Log.e("TAG","547    "+ModuleListSP.getString("ModuleList",""));
            val jsonObj = JSONObject(ModuleListSP.getString("ModuleList",""))
            Log.e("TAG","5471    "+jsonObj!!.getString("LEAD"));

//            var iLead = 1
//            var iService = 1
//            var iCollection = 1
//            var iPickUp = 1

            var iLead = jsonObj!!.getString("LEAD")
            var iService = jsonObj!!.getString("SERVICE")
            var iCollection = jsonObj!!.getString("ACCOUNTS")
            var iPickUp = jsonObj!!.getString("DELIVERY")
            var iProject = jsonObj!!.getString("PROJECT")

            val jsonObject1 = JSONObject()
            val jsonObject = JSONObject()
            val array = JSONArray()

            var obj = JSONObject()
            obj.put("grid_id", "1")
            obj.put("grid_name", "Today's List")
          //  obj.put("image",context.resources.getDrawable(R.drawable.addrs) )
            obj.put("image","todays_list_home")
            obj.put("count","0")
            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "2")
            obj.put("grid_name", "DashBoard")
         //   obj.put("image",context.resources.getDrawable(R.drawable.agenda) )
            obj.put("image","dashboard_home")
            obj.put("count","0")
            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "3")
            obj.put("grid_name", "Authorization")
            // obj.put("image",context.resources.getDrawable(R.drawable.agntremrk) )
            obj.put("image","athorization_home")
            obj.put("count","0")
            array.put(obj)

//            obj = JSONObject()
//            obj.put("grid_id", "3")
//            obj.put("grid_name", "Notification")
//           // obj.put("image",context.resources.getDrawable(R.drawable.agntremrk) )
//            obj.put("image","home_notification")
//            obj.put("count","0")
//            array.put(obj)

//            obj = JSONObject()
//            obj.put("grid_id", "3")
//            obj.put("grid_name", "Inventory")
//            // obj.put("image",context.resources.getDrawable(R.drawable.agntremrk) )
//            obj.put("image","home_inventory")
//            obj.put("count","0")
//            array.put(obj)


            if(iLead.equals("true")){
                obj = JSONObject()
                obj.put("grid_id", "4")
                obj.put("grid_name", "Leads")
                //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
                obj.put("image","lead_home")
                obj.put("count","0")
                array.put(obj)
            }

            if(iService.equals("true")){
                obj = JSONObject()
                obj.put("grid_id", "5")
                obj.put("grid_name", "Services")
                //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
                obj.put("image","service_home")
                obj.put("count","0")
                array.put(obj)
            }

//            if(iCollection.equals("true")){
//                obj = JSONObject()
//                obj.put("grid_id", "6")
//                obj.put("grid_name", "Collection")
//                //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
//                obj.put("image","collection_home")
//                obj.put("count","0")
//                array.put(obj)
//            }

            if(iProject.equals("true")){
                obj = JSONObject()
                obj.put("grid_id", "6")
                obj.put("grid_name", "Project")
                obj.put("image","project_home")
                obj.put("count","0")
                array.put(obj)
            }

            if(iPickUp.equals("true")){

                obj = JSONObject()
                obj.put("grid_id", "7")
                obj.put("grid_name", "Pickup& Delivery")
                //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
                obj.put("image","pick_deli_home")
                obj.put("count","0")
                array.put(obj)
            }
            if(bTracker){
                obj = JSONObject()
                obj.put("grid_id", "8")
                obj.put("grid_name", "Employee Location")
                //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
                obj.put("image","location_home")
                obj.put("count","0")
                array.put(obj)
            }


            obj = JSONObject()
            obj.put("grid_id", "9")
            obj.put("grid_name", "Report")
            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
            obj.put("image","report_home")
            obj.put("count","0")
            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "10")
            obj.put("grid_name", "Reminder")
            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
            obj.put("image","reminder_home_1")
            obj.put("count","0")
            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "11")
            obj.put("grid_name", "Profile")
            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
            obj.put("image","profile_home_1")
            obj.put("count","0")
            array.put(obj)

//            obj = JSONObject()
//            obj.put("grid_id", "11")
//            obj.put("grid_name", "Expense")
//            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
//            obj.put("image","home_expense")
//            obj.put("count","0")
//            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "12")
            obj.put("grid_name", "Contact Us")
            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
            obj.put("image","contactus_home_1")
            obj.put("count","0")
            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "13")
            obj.put("grid_name", "About Us")
            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
            obj.put("image","aboutus_home_1")
            obj.put("count","0")
            array.put(obj)



            jsonObject.put("homeGridDetails", array)
            jsonObject1.put("homeGridType", jsonObject)
            Log.e("JsonObject", jsonObject.toString())
            result = jsonObject1.toString()


        }catch (e : Exception){
            result = ""
        }
        return result
    }

    fun getTransType(context : Context): String {

        var result =""

        try {
            val array = JSONArray()
            val jsonObject = JSONObject()
            val jsonObject1 = JSONObject()

            var obj = JSONObject()
            obj.put("ID_TransType", "1")
            obj.put("TransType_Name", "Debit")

            array.put(obj)

            obj = JSONObject()
            obj.put("ID_TransType", "2")
            obj.put("TransType_Name", "Credit")

            array.put(obj)

            jsonObject.put("TransTypeDetails", array)
            jsonObject1.put("TransType", jsonObject)

            result = jsonObject1.toString()

        }catch (e : Exception){

        }


        return result
    }

    fun getCompliantOrService(context : Context): String {
        var result =""
        try {


            val jsonObject1 = JSONObject()
            val jsonObject = JSONObject()
            val array = JSONArray()

            var obj = JSONObject()
            obj.put("compService_id", "1")
            obj.put("compService_name", "Complaint")
            array.put(obj)

            obj = JSONObject()
            obj.put("compService_id", "2")
            obj.put("compService_name", "Service")
            array.put(obj)

            jsonObject.put("compServiceDetails", array)
            jsonObject1.put("compServiceType", jsonObject)
            result = jsonObject1.toString()

        }catch (e : Exception){
            result = ""
        }
        return result
    }

    fun getMilliSeconds(context : Context): Int {
        var result =30000
        try {
            val UtilityListSP = context.getSharedPreferences(Config.SHARED_PREF57, 0)
            val jsonObj = JSONObject(UtilityListSP.getString("UtilityList", ""))
            var iSecond = jsonObj!!.getString("LOCATION_INTERVAL")
            result = (iSecond.toLong() * 1000).toInt()
        }catch (e : Exception){

        }
        return result
    }

    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for (serviceInfo in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == serviceInfo.service.className) {
                // Service is running
                return true
            }
        }
        // Service is not running
        return false
    }

    fun isAppInForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        val packageName = context.packageName

        val foregroundTaskInfo = activityManager?.getRunningTasks(1)?.firstOrNull()

        return foregroundTaskInfo?.topActivity?.packageName == packageName
    }

    fun setRedirection(context : Context,redirection: String) {

        val isNotificationSP = context.getSharedPreferences(Config.SHARED_PREF64, 0)
        val isNotificationEditer = isNotificationSP.edit()
        isNotificationEditer.putString("isNotification", redirection)
        isNotificationEditer.commit()

    }

    fun convertDate(strDate : String): String {
        var result =""
        try {

            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val newDate: Date = sdf.parse(strDate)
//            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfDate2 = SimpleDateFormat("yyyy-MM-dd")

            result = sdfDate2.format(newDate)

        }catch (e :  Exception){
            result = ""
        }

        return result
    }

    fun convertTimemills(date1: String , date2: String): Boolean {

        var result = false
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val mDate1 = sdf.parse(date1)
            val timeInMilliseconds1 = mDate1.time

            val mDate2 = sdf.parse(date2)
            val timeInMilliseconds2 = mDate2.time

            if (timeInMilliseconds1 <= timeInMilliseconds2){
                Log.e("TAG","13141    "+date1+"  <=  "+date2)
                result = true
            }else{
                Log.e("TAG","13142    "+date1+"  >  "+date2)
                result = false
            }








        }catch (e:Exception){
            Log.e("TAG","123456   "+e)
        }
       return  result
    }

    fun convert12HourTo24Hour(time12: String): String {
        val inputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val date = inputFormat.parse(time12)
        return outputFormat.format(date)
    }

    fun getDateComponents(dateString: String): Triple<Int, Int, Int> {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = dateFormatter.parse(dateString) ?: Date()

        val calendar = Calendar.getInstance().apply {
            time = date
        }

        return Triple(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1, // Months are zero-based, so add 1
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }



}

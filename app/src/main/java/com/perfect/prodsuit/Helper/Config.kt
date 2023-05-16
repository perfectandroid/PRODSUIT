package com.perfect.prodsuit.Helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*


object Config {

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
    const val SHARED_PREF41 = "FK_UserRole"
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

    var width = 0
    var height = 0



    const val SOME_TECHNICAL_ISSUES = "Some Technical Issues."
    const val PLEASE_TRY_AGAIN = "Some Technical Issues, Please try again in sometime"

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

    }

    fun getHomeGrid(context : Context): String {

        var result =""
        try {

            val loginSP = context.getSharedPreferences(SHARED_PREF, 0)

            Log.e("TAG","537    "+loginSP.getString("loginsession",""));

            val ModuleListSP = context.getSharedPreferences(Config.SHARED_PREF54, 0)
            Log.e("TAG","547    "+ModuleListSP.getString("ModuleList",""));
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

            val jsonObject1 = JSONObject()
            val jsonObject = JSONObject()
            val array = JSONArray()

            var obj = JSONObject()
            obj.put("grid_id", "1")
            obj.put("grid_name", "Agenda")
          //  obj.put("image",context.resources.getDrawable(R.drawable.addrs) )
            obj.put("image","home_agenda")
            obj.put("count","0")
            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "2")
            obj.put("grid_name", "DashBoard")
         //   obj.put("image",context.resources.getDrawable(R.drawable.agenda) )
            obj.put("image","home_dashboard")
            obj.put("count","0")
            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "3")
            obj.put("grid_name", "Notification")
           // obj.put("image",context.resources.getDrawable(R.drawable.agntremrk) )
            obj.put("image","home_notification")
            obj.put("count","0")
            array.put(obj)

            if(iLead.equals("true")){
                obj = JSONObject()
                obj.put("grid_id", "4")
                obj.put("grid_name", "Leads")
                //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
                obj.put("image","home_leads")
                obj.put("count","0")
                array.put(obj)
            }

            if(iService.equals("true")){
                obj = JSONObject()
                obj.put("grid_id", "5")
                obj.put("grid_name", "Services")
                //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
                obj.put("image","home_service")
                obj.put("count","0")
                array.put(obj)
            }

            if(iCollection.equals("true")){
                obj = JSONObject()
                obj.put("grid_id", "6")
                obj.put("grid_name", "Collection")
                //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
                obj.put("image","home_collection")
                obj.put("count","0")
                array.put(obj)
            }

            if(iPickUp.equals("true")){

                obj = JSONObject()
                obj.put("grid_id", "7")
                obj.put("grid_name", "Pickup& Delivery")
                //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
                obj.put("image","home_pickupdelivery")
                obj.put("count","0")
                array.put(obj)
            }

            obj = JSONObject()
            obj.put("grid_id", "8")
            obj.put("grid_name", "Reminder")
            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
            obj.put("image","home_reminder")
            obj.put("count","0")
            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "9")
            obj.put("grid_name", "Report")
            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
            obj.put("image","home_report")
            obj.put("count","0")
            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "10")
            obj.put("grid_name", "Profile")
            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
            obj.put("image","home_profile")
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
            obj.put("grid_id", "11")
            obj.put("grid_name", "Contact Us")
            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
            obj.put("image","home_contactus")
            obj.put("count","0")
            array.put(obj)

            obj = JSONObject()
            obj.put("grid_id", "12")
            obj.put("grid_name", "About Us")
            //   obj.put("image",context.resources.getDrawable(R.drawable.applogo) )
            obj.put("image","home_aboutus")
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




}

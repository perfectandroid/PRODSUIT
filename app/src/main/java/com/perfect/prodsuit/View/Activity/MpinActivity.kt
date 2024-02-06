package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DBHelper
import com.perfect.prodsuit.Model.ModelServiceAttendedTemp
import com.perfect.prodsuit.Model.MpinUserModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.CustomSpinnerAdapter
import com.perfect.prodsuit.Viewmodel.ForgotMpinViewModel
import com.perfect.prodsuit.Viewmodel.MpinActivityViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.*


class MpinActivity : AppCompatActivity(), View.OnClickListener {

    var TAG = "MpinActivity"
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
    private var tvLogout: TextView? = null
    private var tvForgotMpin: TextView? = null
    private var tvRegister: TextView? = null
    private var et_1: EditText? = null
    private var et_2: EditText? = null
    private var et_3: EditText? = null
    private var et_4: EditText? = null
    private var et_5: EditText? = null
    private var et_6: EditText? = null
    private var imgShowPin: ImageView? = null
    private var finger_print: ImageView? = null
    private var img_logo: ImageView? = null
    private var autoTextView: AutoCompleteTextView? = null
    private var spinCompany: Spinner? = null
    private var showPin: LinearLayout? = null
    private var clear: LinearLayout? = null
    private var lin_fingerprint: LinearLayout? = null
    private var ll_companyName: LinearLayout? = null
    lateinit var context: Context
    lateinit var mpinActivityViewModel: MpinActivityViewModel
    lateinit var forgotMpinViewModel: ForgotMpinViewModel
    private var activity: String? = null
    private var cancellationSignal: CancellationSignal? = null

    var db : DBHelper? = null
    var mpinUserModel = ArrayList<MpinUserModel>()
    var tableCount = 0
    var logoutMode = 1

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_mpin)
        setRegViews()
        context = this@MpinActivity
        mpinActivityViewModel = ViewModelProvider(this).get(MpinActivityViewModel::class.java)
        forgotMpinViewModel = ViewModelProvider(this).get(ForgotMpinViewModel::class.java)

        activity = intent.getStringExtra("activity")
        db = DBHelper(this, null)

        if(checkBiometricFeatureAvailability(context)) {
            loadBiometric()
        }
        else
        {
            lin_fingerprint!!.visibility=View.GONE
        }

        setLogoHeight()


    }

    private fun setLogoHeight() {
        var userList = JSONArray()
        userList = db!!.getRegisteredUserList()

        Log.e(TAG,"10881  "+userList.length())
        Log.e(TAG,"10882  "+userList)
        Log.e(TAG,"10882  "+userList)

        if (userList.length() > 1){
            val desiredHeightInPixels = 300
            // Get the current layout parameters of the ImageView
            val layoutParams = img_logo!!.layoutParams
            // Set the height of the ImageView
            layoutParams.height = desiredHeightInPixels
            // Apply the updated layout parameters to the ImageView
            img_logo!!.layoutParams = layoutParams
            ll_companyName!!.visibility = View.VISIBLE
//            autoTextView.setCustomAdapter(dataList)
//            val dataList = parseJsonArray(userList)

            val gson = Gson()
            mpinUserModel.clear()
            mpinUserModel = gson.fromJson(userList.toString(),Array<MpinUserModel>::class.java).toList() as ArrayList<MpinUserModel>

            val adapter = CustomSpinnerAdapter(this@MpinActivity, android.R.layout.simple_spinner_item, mpinUserModel)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinCompany!!.setAdapter(adapter) // Set the custom ada

            for (k in 0 until mpinUserModel.size) {
                if (mpinUserModel[k].IP_Default.equals("1") || mpinUserModel[k].IP_Default.equals("true")){
                    spinCompany!!.setSelection(k)
                    val selectedItem = mpinUserModel[k]

                    addToSession(selectedItem)

                }
            }


            spinCompany!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedItem = mpinUserModel[position]
                    // Handle the selected item
                  //  Log.d("Spinner", "Selected Item: ${selectedItem.name}")

                    addToSession(selectedItem)

//                    val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
//                    val BASE_URLEditer = BASE_URLSP.edit()
//                    BASE_URLEditer.putString("BASE_URL", selectedItem.Base_Url)
//                    BASE_URLEditer.commit()
//
//                    val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
//                    val IMAGE_URLEditer = IMAGE_URLSP.edit()
//                    IMAGE_URLEditer.putString("IMAGE_URL", selectedItem.Image_Url)
//                    IMAGE_URLEditer.commit()
//
//
//                    val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
//                    val CERT_NAMEEditer = CERT_NAMESP.edit()
//                    CERT_NAMEEditer.putString("CERT_NAME", selectedItem.Cert_Name)
//                    CERT_NAMEEditer.commit()
//
//                    val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
//                    val BANK_KEYEditer = BANK_KEYESP.edit()
//                    BANK_KEYEditer.putString("BANK_KEY", selectedItem.Bank_key)
//                    BANK_KEYEditer.commit()
//
//
//                    ////
//
//
//                    val ResellerNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF32, 0)
//                    val ResellerNameEditer = ResellerNameSP.edit()
//                    ResellerNameEditer.putString("ResellerName",selectedItem.ResellerName)
//                    ResellerNameEditer.commit()
//
//                    val AppIconImageCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF19, 0)
//                    val AppIconImageCodeEditer = AppIconImageCodeSP.edit()
//                    AppIconImageCodeEditer.putString("AppIconImageCode", selectedItem.AppIconImageCode)
//                    AppIconImageCodeEditer.commit()
//
//                    val TechnologyPartnerImageSP = applicationContext.getSharedPreferences(Config.SHARED_PREF20, 0)
//                    val TechnologyPartnerImageEditer = TechnologyPartnerImageSP.edit()
//                    TechnologyPartnerImageEditer.putString("TechnologyPartnerImage", selectedItem.TechnologyPartnerImage)
//                    TechnologyPartnerImageEditer.commit()
//
//                    val ProductNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF21, 0)
//                    val ProductNameEditer = ProductNameSP.edit()
//                    ProductNameEditer.putString("ProductName", selectedItem.ProductName)
//                    ProductNameEditer.commit()
//
//                    val PlayStoreLinkSP = applicationContext.getSharedPreferences(Config.SHARED_PREF22, 0)
//                    val PlayStoreLinkEditer = PlayStoreLinkSP.edit()
//                    PlayStoreLinkEditer.putString("PlayStoreLink", selectedItem.PlayStoreLink)
//                    PlayStoreLinkEditer.commit()
//
//
//
//                    val ContactNumberSP = applicationContext.getSharedPreferences(Config.SHARED_PREF33, 0)
//                    val ContactNumberEditer = ContactNumberSP.edit()
//                    ContactNumberEditer.putString("ContactNumber",selectedItem.ContactNumber)
//                    ContactNumberEditer.commit()
//
//
//                    val ContactEmailSP = applicationContext.getSharedPreferences(Config.SHARED_PREF34, 0)
//                    val ContactEmailEditer = ContactEmailSP.edit()
//                    ContactEmailEditer.putString("ContactEmail",selectedItem.ContactEmail)
//                    ContactEmailEditer.commit()
//
//
//                    val ContactAddressSP = applicationContext.getSharedPreferences(Config.SHARED_PREF35, 0)
//                    val ContactAddressEditer = ContactAddressSP.edit()
//                    ContactAddressEditer.putString("ContactAddress",selectedItem.ContactAddress)
//                    ContactAddressEditer.commit()
//
//                    val TestingsslcertificateSP = applicationContext.getSharedPreferences(Config.SHARED_PREF13, 0)
//                    val TestingsslcertificateEditer = TestingsslcertificateSP.edit()
//                    TestingsslcertificateEditer.putString("Testingsslcertificate", selectedItem.CertificateName)
//                    TestingsslcertificateEditer.commit()
//
//
//                    val TestingURLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF10, 0)
//                    val TestingURLEditer = TestingURLSP.edit()
//                    TestingURLEditer.putString("TestingURL", selectedItem.TestingURL+"/")
//                    TestingURLEditer.commit()
//
//                    val TestingImageURLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF15, 0)
//                    val TestingImageURLEditer = TestingImageURLSP.edit()
//                    TestingImageURLEditer.putString("TestingImageURL", selectedItem.TestingImageURL+"/")
//                    TestingImageURLEditer.commit()
//
//                    val TestingMobileNoSP = applicationContext.getSharedPreferences(Config.SHARED_PREF11, 0)
//                    val TestingMobileNoEditer = TestingMobileNoSP.edit()
//                    TestingMobileNoEditer.putString("TestingMobileNo", selectedItem.TestingMobileNo)
//                    TestingMobileNoEditer.commit()
//
//                    val TestingBankKeySP = applicationContext.getSharedPreferences(Config.SHARED_PREF12, 0)
//                    val TestingBankKeyEditer = TestingBankKeySP.edit()
//                    TestingBankKeyEditer.putString("TestingBankKey", selectedItem.TestingBankKey)
//                    TestingBankKeyEditer.commit()
//
//                    val ABOUTUSSP = applicationContext.getSharedPreferences(Config.SHARED_PREF31, 0)
//                    val ABOUTUSEditer = ABOUTUSSP.edit()
//                    ABOUTUSEditer.putString("ABOUTUS", selectedItem.AboutUs)
//                    ABOUTUSEditer.commit()
//
//                    val AudioClipEnabledSP = applicationContext.getSharedPreferences(Config.SHARED_PREF76, 0)
//                    val AudioClipEnabledEditer = AudioClipEnabledSP.edit()
//                    AudioClipEnabledEditer.putString("AudioClipEnabled", selectedItem.AudioClipEnabled)
//                    AudioClipEnabledEditer.commit()
//
//                    val IsLocationDistanceShowingSP = applicationContext.getSharedPreferences(Config.SHARED_PREF77, 0)
//                    val IsLocationDistanceShowingEditer = IsLocationDistanceShowingSP.edit()
//                    IsLocationDistanceShowingEditer.putString("IsLocationDistanceShowing", selectedItem.IsLocationDistanceShowing)
//                    IsLocationDistanceShowingEditer.commit()
//
//                    val EditMRPLeadSP = applicationContext.getSharedPreferences(Config.SHARED_PREF78, 0)
//                    val EditMRPLeadEditer = EditMRPLeadSP.edit()
//                    EditMRPLeadEditer.putString("EditMRPLead", selectedItem.EditMRPLead)
//                    EditMRPLeadEditer.commit()
//
//                   // Login User
//
//                    val FK_EmployeeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF1, 0)
//                    val FK_EmployeeEditer = FK_EmployeeSP.edit()
//                    FK_EmployeeEditer.putString("FK_Employee", selectedItem.FK_Employee)
//                    FK_EmployeeEditer.commit()
//
//                    val UserNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF2, 0)
//                    val UserNameEditer = UserNameSP.edit()
//                    UserNameEditer.putString("UserName", selectedItem.UserName)
//                    UserNameEditer.commit()
//
//                    val AddressSP = applicationContext.getSharedPreferences(Config.SHARED_PREF3, 0)
//                    val AddressEditer = AddressSP.edit()
//                    AddressEditer.putString("Address", selectedItem.Address)
//                    AddressEditer.commit()
//
//                    val MobileNumberSP = applicationContext.getSharedPreferences(Config.SHARED_PREF4, 0)
//                    val MobileNumberEditer = MobileNumberSP.edit()
//                    MobileNumberEditer.putString("MobileNumber", selectedItem.MobileNumber)
//                    MobileNumberEditer.commit()
//
//                    val TokenSP = applicationContext.getSharedPreferences(Config.SHARED_PREF5, 0)
//                    val TokenEditer = TokenSP.edit()
//                    TokenEditer.putString("Token", selectedItem.Token)
//                    TokenEditer.commit()
//
//
//                    val UserCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF36, 0)
//                    val UserCodeEditer = UserCodeSP.edit()
//                    UserCodeEditer.putString("UserCode", selectedItem.UserCode)
//                    UserCodeEditer.commit()
//
//                    val FK_BranchSP = applicationContext.getSharedPreferences(Config.SHARED_PREF37, 0)
//                    val FK_BranchEditer = FK_BranchSP.edit()
//                    FK_BranchEditer.putString("FK_Branch", selectedItem.FK_Branch)
//                    FK_BranchEditer.commit()
//
//                    val FK_BranchTypeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF38, 0)
//                    val FK_BranchTypeEditer = FK_BranchTypeSP.edit()
//                    FK_BranchTypeEditer.putString("FK_BranchType", selectedItem.FK_BranchType)
//                    FK_BranchTypeEditer.commit()
//
//                    val FK_CompanySP = applicationContext.getSharedPreferences(Config.SHARED_PREF39, 0)
//                    val FK_CompanyEditer = FK_CompanySP.edit()
//                    FK_CompanyEditer.putString("FK_Company", selectedItem.FK_Company)
//                    FK_CompanyEditer.commit()
//
//                    val FK_BranchCodeUserSP =
//                        applicationContext.getSharedPreferences(Config.SHARED_PREF40, 0)
//                    val FK_BranchCodeUserEditer = FK_BranchCodeUserSP.edit()
//                    FK_BranchCodeUserEditer.putString("FK_BranchCodeUser", selectedItem.FK_BranchCodeUser)
//                    FK_BranchCodeUserEditer.commit()
//
//                    val FK_UserRoleSP = applicationContext.getSharedPreferences(Config.SHARED_PREF41, 0)
//                    val FK_UserRoleEditer = FK_UserRoleSP.edit()
//                    FK_UserRoleEditer.putString("FK_UserRole", selectedItem.FK_UserRole)
//                    FK_UserRoleEditer.commit()
//
//                    val UserRoleSP = applicationContext.getSharedPreferences(Config.SHARED_PREF42, 0)
//                    val UserRoleEditer = UserRoleSP.edit()
//                    UserRoleEditer.putString("UserRole", selectedItem.UserRole)
//                    UserRoleEditer.commit()
//
//                    val IsAdminSP = applicationContext.getSharedPreferences(Config.SHARED_PREF43, 0)
//                    val IsAdminEditer = IsAdminSP.edit()
//                    IsAdminEditer.putString("IsAdmin", selectedItem.IsAdmin)
//                    IsAdminEditer.commit()
//
//                    val IsManagerSP = applicationContext.getSharedPreferences(Config.SHARED_PREF75, 0)
//                    val IsManagerEditer = IsManagerSP.edit()
//                    IsManagerEditer.putString("IsManager",selectedItem.IsManager)
//                    IsManagerEditer.commit()
//
//
//                    val ID_UserSP = applicationContext.getSharedPreferences(Config.SHARED_PREF44, 0)
//                    val ID_UserEditer = ID_UserSP.edit()
//                    ID_UserEditer.putString("ID_User", selectedItem.ID_User)
//                    ID_UserEditer.commit()
//
//                    val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
//                    val BranchNameEditer = BranchNameSP.edit()
//                    BranchNameEditer.putString("BranchName", selectedItem.BranchName)
//                    BranchNameEditer.commit()
//
//
//                    val FK_DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF55, 0)
//                    val FK_DepartmentEditer = FK_DepartmentSP.edit()
//                    FK_DepartmentEditer.putString("FK_Department", selectedItem.FK_Department)
//                    FK_DepartmentEditer.commit()
//
//                    val DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF56, 0)
//                    val DepartmentEditer = DepartmentSP.edit()
//                    DepartmentEditer.putString("Department", selectedItem.Department)
//                    DepartmentEditer.commit()

                    db!!.updateStatusDefaultIp(selectedItem.ID_Company,true,true,"0")


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }

//            val adapter = CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, dataList!!)
//
//
//            spinCompany!!.adapter = adapter

        }else{
            val desiredHeightInPixels = 400
            // Get the current layout parameters of the ImageView
            val layoutParams = img_logo!!.layoutParams
            // Set the height of the ImageView
            layoutParams.height = desiredHeightInPixels
            // Apply the updated layout parameters to the ImageView
            img_logo!!.layoutParams = layoutParams
            ll_companyName!!.visibility = View.GONE



            try {
                mpinUserModel.clear()
                val gson = Gson()

                if (userList.length() > 0){
                    for (i in 0 until userList.length()) {

                        var jsonObject = userList.getJSONObject(i)

                        mpinUserModel!!.add(MpinUserModel(jsonObject.getString("ID_Company"),jsonObject.getString("Base_Url"),jsonObject.getString("Image_Url"),
                            jsonObject.getString("Bank_key"),jsonObject.getString("Cert_Name"),jsonObject.getString("Company_Code"),
                            jsonObject.getString("Company_Status"), jsonObject.getString("IP_Default"),jsonObject.getString("ID_Reseller"),
                            jsonObject.getString("ResellerName"),jsonObject.getString("AppIconImageCode"),
                            jsonObject.getString("TechnologyPartnerImage"),jsonObject.getString("ProductName"),jsonObject.getString("PlayStoreLink"),
                            jsonObject.getString("AppStoreLink"), jsonObject.getString("ContactNumber"),jsonObject.getString("ContactEmail"),
                            jsonObject.getString("ContactAddress"), jsonObject.getString("CertificateName"), jsonObject.getString("TestingURL"),
                            jsonObject.getString("TestingMachineId"),jsonObject.getString("TestingImageURL"),jsonObject.getString("TestingMobileNo"),
                            jsonObject.getString("TestingBankKey"),jsonObject.getString("TestingBankHeader"),jsonObject.getString("AboutUs"),
                            jsonObject.getString("AudioClipEnabled"), jsonObject.getString("IsLocationDistanceShowing"),jsonObject.getString("EditMRPLead"),
                            jsonObject.getString("ID_LoginUser"),jsonObject.getString("FK_Employee"), jsonObject.getString("UserName"),
                            jsonObject.getString("Address"),jsonObject.getString("MobileNumber"),jsonObject.getString("Token"),
                            jsonObject.getString("UserCode"),jsonObject.getString("FK_Branch"),jsonObject.getString("BranchName"),
                            jsonObject.getString("FK_BranchType"),jsonObject.getString("FK_Company"),jsonObject.getString("FK_BranchCodeUser"),
                            jsonObject.getString("FK_UserRole"),jsonObject.getString("UserRole"),jsonObject.getString("IsAdmin"),
                            jsonObject.getString("IsManager"),jsonObject.getString("ID_User"),jsonObject.getString("FK_Department"),
                            jsonObject.getString("Department"),jsonObject.getString("userMpin"))
                        )
                    }

                  //  mpinUserModel = gson.fromJson(userList.toString(),Array<MpinUserModel>::class.java).toList() as ArrayList<MpinUserModel>
                    Log.e(TAG,"39331 Base_Url :   "+mpinUserModel.size)
                    Log.e(TAG,"39331 Base_Url :   "+mpinUserModel.size)
                }

                val selectedItem = mpinUserModel[0]
                Log.e(TAG,"39332 Base_Url :   "+selectedItem.Base_Url)
                addToSession(selectedItem)


            }catch (e : Exception){
                Log.e(TAG,"39331 Exception :   "+e.toString())
            }

        }


    }

    private fun addToSession(selectedItem: MpinUserModel) {
        val BASE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF7, 0)
        val BASE_URLEditer = BASE_URLSP.edit()
        BASE_URLEditer.putString("BASE_URL", selectedItem.Base_Url)
        BASE_URLEditer.commit()

        val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
        val IMAGE_URLEditer = IMAGE_URLSP.edit()
        IMAGE_URLEditer.putString("IMAGE_URL", selectedItem.Image_Url)
        IMAGE_URLEditer.commit()


        val CERT_NAMESP = applicationContext.getSharedPreferences(Config.SHARED_PREF8, 0)
        val CERT_NAMEEditer = CERT_NAMESP.edit()
        CERT_NAMEEditer.putString("CERT_NAME", selectedItem.Cert_Name)
        CERT_NAMEEditer.commit()

        val BANK_KEYESP = applicationContext.getSharedPreferences(Config.SHARED_PREF9, 0)
        val BANK_KEYEditer = BANK_KEYESP.edit()
        BANK_KEYEditer.putString("BANK_KEY", selectedItem.Bank_key)
        BANK_KEYEditer.commit()


        ////


        val ResellerNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF32, 0)
        val ResellerNameEditer = ResellerNameSP.edit()
        ResellerNameEditer.putString("ResellerName",selectedItem.ResellerName)
        ResellerNameEditer.commit()

        val AppIconImageCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF19, 0)
        val AppIconImageCodeEditer = AppIconImageCodeSP.edit()
        AppIconImageCodeEditer.putString("AppIconImageCode", selectedItem.AppIconImageCode)
        AppIconImageCodeEditer.commit()

        val TechnologyPartnerImageSP = applicationContext.getSharedPreferences(Config.SHARED_PREF20, 0)
        val TechnologyPartnerImageEditer = TechnologyPartnerImageSP.edit()
        TechnologyPartnerImageEditer.putString("TechnologyPartnerImage", selectedItem.TechnologyPartnerImage)
        TechnologyPartnerImageEditer.commit()

        val ProductNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF21, 0)
        val ProductNameEditer = ProductNameSP.edit()
        ProductNameEditer.putString("ProductName", selectedItem.ProductName)
        ProductNameEditer.commit()

        val PlayStoreLinkSP = applicationContext.getSharedPreferences(Config.SHARED_PREF22, 0)
        val PlayStoreLinkEditer = PlayStoreLinkSP.edit()
        PlayStoreLinkEditer.putString("PlayStoreLink", selectedItem.PlayStoreLink)
        PlayStoreLinkEditer.commit()



        val ContactNumberSP = applicationContext.getSharedPreferences(Config.SHARED_PREF33, 0)
        val ContactNumberEditer = ContactNumberSP.edit()
        ContactNumberEditer.putString("ContactNumber",selectedItem.ContactNumber)
        ContactNumberEditer.commit()


        val ContactEmailSP = applicationContext.getSharedPreferences(Config.SHARED_PREF34, 0)
        val ContactEmailEditer = ContactEmailSP.edit()
        ContactEmailEditer.putString("ContactEmail",selectedItem.ContactEmail)
        ContactEmailEditer.commit()


        val ContactAddressSP = applicationContext.getSharedPreferences(Config.SHARED_PREF35, 0)
        val ContactAddressEditer = ContactAddressSP.edit()
        ContactAddressEditer.putString("ContactAddress",selectedItem.ContactAddress)
        ContactAddressEditer.commit()

        val TestingsslcertificateSP = applicationContext.getSharedPreferences(Config.SHARED_PREF13, 0)
        val TestingsslcertificateEditer = TestingsslcertificateSP.edit()
        TestingsslcertificateEditer.putString("Testingsslcertificate", selectedItem.CertificateName)
        TestingsslcertificateEditer.commit()


        val TestingURLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF10, 0)
        val TestingURLEditer = TestingURLSP.edit()
        TestingURLEditer.putString("TestingURL", selectedItem.TestingURL+"/")
        TestingURLEditer.commit()

        val TestingImageURLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF15, 0)
        val TestingImageURLEditer = TestingImageURLSP.edit()
        TestingImageURLEditer.putString("TestingImageURL", selectedItem.TestingImageURL+"/")
        TestingImageURLEditer.commit()

        val TestingMobileNoSP = applicationContext.getSharedPreferences(Config.SHARED_PREF11, 0)
        val TestingMobileNoEditer = TestingMobileNoSP.edit()
        TestingMobileNoEditer.putString("TestingMobileNo", selectedItem.TestingMobileNo)
        TestingMobileNoEditer.commit()

        val TestingBankKeySP = applicationContext.getSharedPreferences(Config.SHARED_PREF12, 0)
        val TestingBankKeyEditer = TestingBankKeySP.edit()
        TestingBankKeyEditer.putString("TestingBankKey", selectedItem.TestingBankKey)
        TestingBankKeyEditer.commit()

        val ABOUTUSSP = applicationContext.getSharedPreferences(Config.SHARED_PREF31, 0)
        val ABOUTUSEditer = ABOUTUSSP.edit()
        ABOUTUSEditer.putString("ABOUTUS", selectedItem.AboutUs)
        ABOUTUSEditer.commit()

        val AudioClipEnabledSP = applicationContext.getSharedPreferences(Config.SHARED_PREF76, 0)
        val AudioClipEnabledEditer = AudioClipEnabledSP.edit()
        AudioClipEnabledEditer.putString("AudioClipEnabled", selectedItem.AudioClipEnabled)
        AudioClipEnabledEditer.commit()

        val IsLocationDistanceShowingSP = applicationContext.getSharedPreferences(Config.SHARED_PREF77, 0)
        val IsLocationDistanceShowingEditer = IsLocationDistanceShowingSP.edit()
        IsLocationDistanceShowingEditer.putString("IsLocationDistanceShowing", selectedItem.IsLocationDistanceShowing)
        IsLocationDistanceShowingEditer.commit()

        val EditMRPLeadSP = applicationContext.getSharedPreferences(Config.SHARED_PREF78, 0)
        val EditMRPLeadEditer = EditMRPLeadSP.edit()
        EditMRPLeadEditer.putString("EditMRPLead", selectedItem.EditMRPLead)
        EditMRPLeadEditer.commit()

        // Login User

        val FK_EmployeeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF1, 0)
        val FK_EmployeeEditer = FK_EmployeeSP.edit()
        FK_EmployeeEditer.putString("FK_Employee", selectedItem.FK_Employee)
        FK_EmployeeEditer.commit()

        val UserNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF2, 0)
        val UserNameEditer = UserNameSP.edit()
        UserNameEditer.putString("UserName", selectedItem.UserName)
        UserNameEditer.commit()

        val AddressSP = applicationContext.getSharedPreferences(Config.SHARED_PREF3, 0)
        val AddressEditer = AddressSP.edit()
        AddressEditer.putString("Address", selectedItem.Address)
        AddressEditer.commit()

        val MobileNumberSP = applicationContext.getSharedPreferences(Config.SHARED_PREF4, 0)
        val MobileNumberEditer = MobileNumberSP.edit()
        MobileNumberEditer.putString("MobileNumber", selectedItem.MobileNumber)
        MobileNumberEditer.commit()

        val TokenSP = applicationContext.getSharedPreferences(Config.SHARED_PREF5, 0)
        val TokenEditer = TokenSP.edit()
        TokenEditer.putString("Token", selectedItem.Token)
        TokenEditer.commit()


        val UserCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF36, 0)
        val UserCodeEditer = UserCodeSP.edit()
        UserCodeEditer.putString("UserCode", selectedItem.UserCode)
        UserCodeEditer.commit()

        val FK_BranchSP = applicationContext.getSharedPreferences(Config.SHARED_PREF37, 0)
        val FK_BranchEditer = FK_BranchSP.edit()
        FK_BranchEditer.putString("FK_Branch", selectedItem.FK_Branch)
        FK_BranchEditer.commit()

        val FK_BranchTypeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF38, 0)
        val FK_BranchTypeEditer = FK_BranchTypeSP.edit()
        FK_BranchTypeEditer.putString("FK_BranchType", selectedItem.FK_BranchType)
        FK_BranchTypeEditer.commit()

        val FK_CompanySP = applicationContext.getSharedPreferences(Config.SHARED_PREF39, 0)
        val FK_CompanyEditer = FK_CompanySP.edit()
        FK_CompanyEditer.putString("FK_Company", selectedItem.FK_Company)
        FK_CompanyEditer.commit()

        val FK_BranchCodeUserSP =
            applicationContext.getSharedPreferences(Config.SHARED_PREF40, 0)
        val FK_BranchCodeUserEditer = FK_BranchCodeUserSP.edit()
        FK_BranchCodeUserEditer.putString("FK_BranchCodeUser", selectedItem.FK_BranchCodeUser)
        FK_BranchCodeUserEditer.commit()

        val FK_UserRoleSP = applicationContext.getSharedPreferences(Config.SHARED_PREF41, 0)
        val FK_UserRoleEditer = FK_UserRoleSP.edit()
        FK_UserRoleEditer.putString("FK_UserRole", selectedItem.FK_UserRole)
        FK_UserRoleEditer.commit()

        val UserRoleSP = applicationContext.getSharedPreferences(Config.SHARED_PREF42, 0)
        val UserRoleEditer = UserRoleSP.edit()
        UserRoleEditer.putString("UserRole", selectedItem.UserRole)
        UserRoleEditer.commit()

        val IsAdminSP = applicationContext.getSharedPreferences(Config.SHARED_PREF43, 0)
        val IsAdminEditer = IsAdminSP.edit()
        IsAdminEditer.putString("IsAdmin", selectedItem.IsAdmin)
        IsAdminEditer.commit()

        val IsManagerSP = applicationContext.getSharedPreferences(Config.SHARED_PREF75, 0)
        val IsManagerEditer = IsManagerSP.edit()
        IsManagerEditer.putString("IsManager",selectedItem.IsManager)
        IsManagerEditer.commit()


        val ID_UserSP = applicationContext.getSharedPreferences(Config.SHARED_PREF44, 0)
        val ID_UserEditer = ID_UserSP.edit()
        ID_UserEditer.putString("ID_User", selectedItem.ID_User)
        ID_UserEditer.commit()

        val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
        val BranchNameEditer = BranchNameSP.edit()
        BranchNameEditer.putString("BranchName", selectedItem.BranchName)
        BranchNameEditer.commit()


        val FK_DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF55, 0)
        val FK_DepartmentEditer = FK_DepartmentSP.edit()
        FK_DepartmentEditer.putString("FK_Department", selectedItem.FK_Department)
        FK_DepartmentEditer.commit()

        val DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF56, 0)
        val DepartmentEditer = DepartmentSP.edit()
        DepartmentEditer.putString("Department", selectedItem.Department)
        DepartmentEditer.commit()

        val mpinSP = applicationContext.getSharedPreferences(Config.SHARED_PREF74, 0)
        val mpinEditer = mpinSP.edit()
        mpinEditer.putString("mpin", selectedItem.userMpin)
        mpinEditer.commit()


    }


    private fun setRegViews() {
        var tvdata = findViewById<TextView>(R.id.tvdata)
        tvLogout = findViewById<TextView>(R.id.tvLogout)
        tvForgotMpin = findViewById<TextView>(R.id.tvForgotMpin)
        tvRegister = findViewById<TextView>(R.id.tvRegister)
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
        lin_fingerprint = findViewById<LinearLayout>(R.id.lin_fingerprint)
        ll_companyName = findViewById<LinearLayout>(R.id.ll_companyName)
        clear = findViewById<LinearLayout>(R.id.clear)
        imgShowPin = findViewById<ImageView>(R.id.imgShowPin)
        finger_print = findViewById<ImageView>(R.id.finger_print)
        img_logo = findViewById<ImageView>(R.id.img_logo)
        autoTextView = findViewById<AutoCompleteTextView>(R.id.autoTextView)
        spinCompany = findViewById<Spinner>(R.id.spinCompany)
        tvLogout!!.setOnClickListener(this)
        tvForgotMpin!!.setOnClickListener(this)
        tvRegister!!.setOnClickListener(this)
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
        finger_print!!.setOnClickListener(this)
        et_1!!.isEnabled = false
        et_2!!.isEnabled = false
        et_3!!.isEnabled = false
        et_4!!.isEnabled = false
        et_5!!.isEnabled = false
        et_6!!.isEnabled = false
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.finger_print->
            {
                if(checkBiometricFeatureAvailability(context)) {
                    loadBiometric()
                }
            }
            R.id.tvLogout->{
                try {
                  //  doLogout()
                    LogoutBottomSheet()

               //     QuitBottomSheet()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.tvForgotMpin->{
                try {
                   forgotMpinDialog()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.tvRegister->{
                try {
                    registerDialog()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


            R.id.one -> {
                if(et_1!!.length()==1) {
                    if (et_2!!.length() == 1) {
                        if (et_3!!.length() == 1) {
                            if (et_4!!.length() == 1) {
                                if (et_5!!.length() == 1) {
                                    et_6!!.setText("1")
                                    MpinVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                    clearAll()
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
                                    MpinVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                    clearAll()
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
                                    MpinVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                    clearAll()
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
                                    MpinVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                    clearAll()
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
                                    MpinVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                    clearAll()
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
                                    MpinVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                    clearAll()
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
                                    MpinVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                    clearAll()
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
                                    MpinVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                    clearAll()
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
                                    MpinVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                    clearAll()
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
                                    MpinVerification(et_1!!.getText().toString()+et_2!!.getText().toString()+et_3!!.getText().toString() +
                                            et_4!!.getText().toString()+et_5!!.getText().toString()+et_6!!.getText().toString())
                                    clearAll()
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




    fun checkBiometricFeatureAvailability(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                return true
                Log.v("Biometric Status ","success")
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                // No biometric features available on this device
                Log.v("Biometric Status ","No biometric features available on this device")
                return false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                // Biometric features are currently unavailable
                Log.v("Biometric Status ","Biometric features are currently unavailable")
                return false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // No biometric features enrolled on this device
                Log.v("Biometric Status ","No biometric features enrolled on this device")
                notifyUser("No biometric features enrolled on this device")
                return false
            }
        }
        return false
    }

    private fun loadBiometric() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock "+getString(R.string.app_name))
            .setSubtitle("Log in using biometric credential or with MPIN")
            .setNegativeButtonText("Use MPIN")
            .setConfirmationRequired(false)
            .build()

        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Handle authentication error
                  //  notifyUser("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val mpinSP = context.getSharedPreferences(Config.SHARED_PREF74, 0)
                    val mpin=mpinSP.getString("mpin", null)
                    MpinVerification(""+mpin)
                    // Handle authentication success
                   //notifyUser("Authentication succeeded!")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Handle authentication failure
                 //   notifyUser("Authentication failed.")
                }
            })

        biometricPrompt.authenticate(promptInfo)
    }

    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun forgotMpinDialog() {
       try {
               val builder = android.app.AlertDialog.Builder(this)
               val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
               val layout = inflater1.inflate(R.layout.forgot_mpin, null)
               val btnVerify = layout.findViewById(R.id.btnVerify) as Button
               val tie_Mobile = layout.findViewById(R.id.tie_Mobile) as TextInputEditText


               builder.setView(layout)
               val alertDialog = builder.create()

               btnVerify.setOnClickListener {

                   if (tie_Mobile.text.toString().length == 10){
                       Config.Utils.hideSoftKeyBoard(this@MpinActivity,it)
                       alertDialog.dismiss()
                       forgotMpin(tie_Mobile.text.toString())
                   }
                   else{
                       Config.snackBars(context,it,"Enter Valid Mobile Number")
                   }

               }
               alertDialog.show()
           }
           catch (e: Exception){

           }


    }

    private fun registerDialog() {
        try {

            var ContDeleteMode = "0"  // 0 = Continue , 1 = Delete
            val builder = android.app.AlertDialog.Builder(this)
            val inflater1 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater1.inflate(R.layout.re_register_pop, null)
            val btnNo = layout.findViewById(R.id.btnNo) as Button
            val btnYes = layout.findViewById(R.id.btnYes) as Button

            val chk_box_Continue = layout.findViewById(R.id.chk_box_Continue) as CheckBox
            val chk_box_Delete = layout.findViewById(R.id.chk_box_Delete) as CheckBox

            chk_box_Continue.isChecked = true

            builder.setView(layout)
            val alertDialog = builder.create()

            chk_box_Continue.setOnClickListener {
                chk_box_Continue.isChecked = true
                chk_box_Delete.isChecked = false
            }

            chk_box_Delete.setOnClickListener {
                chk_box_Continue.isChecked = false
                chk_box_Delete.isChecked = true
            }

            btnNo.setOnClickListener {
                alertDialog.dismiss()
            }

            btnYes.setOnClickListener {

                if (chk_box_Continue.isChecked){
                    ContDeleteMode = "0"
                    val ContDeleteModeSP = context.getSharedPreferences(Config.SHARED_PREF84, 0)
                    val ContDeleteModeEditer = ContDeleteModeSP.edit()
                    ContDeleteModeEditer.putString("ContDeleteMode", "0")
                    ContDeleteModeEditer.commit()
                    startActivity(Intent(this@MpinActivity, SplashActivity::class.java))
                }
                if (chk_box_Delete.isChecked){
                    ContDeleteMode = "1"
                    alertDialog.dismiss()
                    db!!.deleteIPReseller()
                    Config.logOut(context,1)
                    startActivity(Intent(this@MpinActivity, SplashActivity::class.java))
                }

                Log.e(TAG,"6922  ContDeleteMode   "+ContDeleteMode)
//                if (ContDeleteMode.equals("1")){
//
//                }
            }
            alertDialog.show()
        }
        catch (e: Exception){

        }
    }



    companion object {
        var strMPIN= ""
    }

    private fun MpinVerification(Mpin:String) {
        Log.v("sdfsdfdsfdf33","called mpin")
        var cMpin = 0
        strMPIN = Mpin
//            et_1!!.text.toString()+et_2!!.text.toString()+et_3!!.text.toString()+et_4!!.text.toString()+et_5!!.text.toString()+et_6!!.text.toString()
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                mpinActivityViewModel.veryfyMpin(this,strMPIN)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        Log.e("TAG","message   :   183910   "+serviceSetterGetter.message)
                       val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (cMpin == 0) {
                                Log.e("dsfsdfdsddddd", "in1")
                                cMpin++
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {

                                val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss a")
                                val currentDate = sdf.format(Date())
                                Log.e("currentDate", "503   " + currentDate)

                                val LOGIN_DATETIMESP =
                                    applicationContext.getSharedPreferences(Config.SHARED_PREF30, 0)
                                val LOGIN_DATETIMEEditer = LOGIN_DATETIMESP.edit()
                                LOGIN_DATETIMEEditer.putString("LOGIN_DATETIME", currentDate)
                                LOGIN_DATETIMEEditer.commit()

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

                                val UserCodeSP =
                                    applicationContext.getSharedPreferences(Config.SHARED_PREF36, 0)
                                val UserCodeEditer = UserCodeSP.edit()
                                UserCodeEditer.putString("UserCode", jobj.getString("UserCode"))
                                UserCodeEditer.commit()

                                val FK_BranchSP =
                                    applicationContext.getSharedPreferences(Config.SHARED_PREF37, 0)
                                val FK_BranchEditer = FK_BranchSP.edit()
                                FK_BranchEditer.putString("FK_Branch", jobj.getString("FK_Branch"))
                                FK_BranchEditer.commit()

                                val FK_BranchTypeSP =
                                    applicationContext.getSharedPreferences(Config.SHARED_PREF38, 0)
                                val FK_BranchTypeEditer = FK_BranchTypeSP.edit()
                                FK_BranchTypeEditer.putString(
                                    "FK_BranchType",
                                    jobj.getString("FK_BranchType")
                                )
                                FK_BranchTypeEditer.commit()

                                val FK_CompanySP =
                                    applicationContext.getSharedPreferences(Config.SHARED_PREF39, 0)
                                val FK_CompanyEditer = FK_CompanySP.edit()
                                FK_CompanyEditer.putString(
                                    "FK_Company",
                                    jobj.getString("FK_Company")
                                )
                                FK_CompanyEditer.commit()

                                val FK_BranchCodeUserSP =
                                    applicationContext.getSharedPreferences(Config.SHARED_PREF40, 0)
                                val FK_BranchCodeUserEditer = FK_BranchCodeUserSP.edit()
                                FK_BranchCodeUserEditer.putString(
                                    "FK_BranchCodeUser",
                                    jobj.getString("FK_BranchCodeUser")
                                )
                                FK_BranchCodeUserEditer.commit()

                                val FK_UserRoleSP =
                                    applicationContext.getSharedPreferences(Config.SHARED_PREF41, 0)
                                val FK_UserRoleEditer = FK_UserRoleSP.edit()
                                FK_UserRoleEditer.putString(
                                    "FK_UserRole",
                                    jobj.getString("FK_UserRole")
                                )
                                FK_UserRoleEditer.commit()

                                val UserRoleSP =
                                    applicationContext.getSharedPreferences(Config.SHARED_PREF42, 0)
                                val UserRoleEditer = UserRoleSP.edit()
                                UserRoleEditer.putString("UserRole", jobj.getString("UserRole"))
                                UserRoleEditer.commit()

                                val IsAdminSP =
                                    applicationContext.getSharedPreferences(Config.SHARED_PREF43, 0)
                                val IsAdminEditer = IsAdminSP.edit()
                                IsAdminEditer.putString("IsAdmin", jobj.getString("IsAdmin"))
                                IsAdminEditer.commit()

                                val IsManagerSP = applicationContext.getSharedPreferences(Config.SHARED_PREF75, 0)
                                val IsManagerEditer = IsManagerSP.edit()
                                IsManagerEditer.putString("IsManager",jobj.getString("IsManager"))
                                IsManagerEditer.commit()


                                val ID_UserSP =
                                    applicationContext.getSharedPreferences(Config.SHARED_PREF44, 0)
                                val ID_UserEditer = ID_UserSP.edit()
                                ID_UserEditer.putString("ID_User", jobj.getString("ID_User"))
                                ID_UserEditer.commit()

                                val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
                                val BranchNameEditer = BranchNameSP.edit()
                                BranchNameEditer.putString("BranchName", jobj.getString("BranchName"))
                                BranchNameEditer.commit()

                                val CompanyCategorySP = applicationContext.getSharedPreferences(Config.SHARED_PREF46, 0)
                                val CompanyCategoryEditer = CompanyCategorySP.edit()
                                CompanyCategoryEditer.putString("CompanyCategory", jobj.getString("CompanyCategory"))
                                CompanyCategoryEditer.commit()

                                val ModuleListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF54, 0)
                                val ModuleListEditer = ModuleListSP.edit()
                                ModuleListEditer.putString("ModuleList", jobj.getString("ModuleList"))
                                Log.i("werwrreer",ModuleListEditer.toString())
                                ModuleListEditer.commit()

                                val FK_DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF55, 0)
                                val FK_DepartmentEditer = FK_DepartmentSP.edit()
                                FK_DepartmentEditer.putString("FK_Department", jobj.getString("FK_Department"))
                                FK_DepartmentEditer.commit()

                                val DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF56, 0)
                                val DepartmentEditer = DepartmentSP.edit()
                                DepartmentEditer.putString("Department", jobj.getString("Department"))
                                DepartmentEditer.commit()

                                val UtilityListSP = applicationContext.getSharedPreferences(Config.SHARED_PREF57, 0)
                                val UtilityListEditer = UtilityListSP.edit()
                                UtilityListEditer.putString("UtilityList", jobj.getString("UtilityList"))
                                UtilityListEditer.commit()

                                val LocLongitudeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF58, 0)
                                val LocLongitudeEditer = LocLongitudeSP.edit()
                                LocLongitudeEditer.putString("LocLongitude", jobj.getString("LocLongitude"))
                                LocLongitudeEditer.commit()

                                val LocLattitudeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF59, 0)
                                val LocLattitudeEditer = LocLattitudeSP.edit()
                                LocLattitudeEditer.putString("LocLattitude", jobj.getString("LocLattitude"))
                                LocLattitudeEditer.commit()


                                val LocLocationNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF60, 0)
                                val LocLocationNameEditer = LocLocationNameSP.edit()
                                LocLocationNameEditer.putString("LocLocationName", jobj.getString("LocLocationName"))
                                LocLocationNameEditer.commit()

                                val EnteredDateSP = applicationContext.getSharedPreferences(Config.SHARED_PREF61, 0)
                                val EnteredDateEditer = EnteredDateSP.edit()
                                EnteredDateEditer.putString("EnteredDate", jobj.getString("EnteredDate"))
                                EnteredDateEditer.commit()

                                val EnteredTimeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF62, 0)
                                val EnteredTimeEditer = EnteredTimeSP.edit()
                                EnteredTimeEditer.putString("EnteredTime", jobj.getString("EnteredTime"))
                                EnteredTimeEditer.commit()

                                val StatusSP = applicationContext.getSharedPreferences(Config.SHARED_PREF63, 0)
                                val StatusEditer = StatusSP.edit()
                                StatusEditer.putString("Status", jobj.getString("Status"))
                                StatusEditer.commit()

                                val CRMDetailsSP = applicationContext.getSharedPreferences(Config.SHARED_PREF79, 0)
                                val CRMDetailsEditer = CRMDetailsSP.edit()
                                CRMDetailsEditer.putString("CRMDetails", jobj.getString("CRMDetails"))
                                CRMDetailsEditer.commit()

                                val FollowUpDetailsSP = applicationContext.getSharedPreferences(Config.SHARED_PREF80, 0)
                                val FollowUpDetailsEditer = FollowUpDetailsSP.edit()
                                FollowUpDetailsEditer.putString("FollowUpDetails", jobj.getString("FollowUpDetails"))
                                FollowUpDetailsEditer.commit()

                                val PSValueSP = applicationContext.getSharedPreferences(Config.SHARED_PREF81, 0)
                                val PSValueEditer = PSValueSP.edit()
                                PSValueEditer.putString("PSValue", jobj.getString("PSValue"))
                                PSValueEditer.commit()


                                val i = Intent(this@MpinActivity, HomeActivity::class.java)
                                startActivity(i)
                                finish()


//                                val isNotificationSP = applicationContext.getSharedPreferences(Config.SHARED_PREF64, 0)
//                                var isNotification = isNotificationSP.getString("isNotification","")
//                                Log.e(TAG,"177770021   isNotification   "+isNotification)
//                                if (!isNotification.equals("")){
//                                    val i = Intent(this@MpinActivity, NotificationActivity::class.java)
//                                    startActivity(i)
//                                    finish()
//                                }else{
//                                    val i = Intent(this@MpinActivity, HomeActivity::class.java)
//                                    startActivity(i)
//                                    finish()
//                                }





                            } else {
                                val builder = AlertDialog.Builder(
                                    this@MpinActivity,
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
                        }
                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
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
               // dologoutchanges()
                Config.logOut(context,1)
                startActivity(Intent(this@MpinActivity, SplashActivity::class.java))
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
        val loginmobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
        val loginmobileEditer = loginmobileSP.edit()
        loginmobileEditer.putString("Loginmobilenumber", "")
        loginmobileEditer.commit()

        val companyCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF17, 0)
        val companyCodeEditer = companyCodeSP.edit()
        companyCodeEditer.putString("companyCode", "")
        companyCodeEditer.commit()

        val commonAppSP = applicationContext.getSharedPreferences(Config.SHARED_PREF18, 0)
        val commonAppEditer = commonAppSP.edit()
        commonAppEditer.putString("commonApp", "")
        commonAppEditer.commit()


    }

    private fun clearAll() {
        et_1!!.text.clear()
        et_2!!.text.clear()
        et_3!!.text.clear()
        et_4!!.text.clear()
        et_5!!.text.clear()
        et_6!!.text.clear()
    }


    private fun forgotMpin(mobileNumber : String) {
        var mpinAttemptCount = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                forgotMpinViewModel.forgotMpin(this,mobileNumber)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        try {
                            if (msg!!.length > 0) {
                                if (mpinAttemptCount == 0) {
                                    mpinAttemptCount++
                                    val jObject = JSONObject(msg)
                                    Log.e("TAG", "jObject   648   " + jObject)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val builder = AlertDialog.Builder(
                                            this@MpinActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                        clearAll()

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@MpinActivity,
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
                                }
                            } else {
//                            Toast.makeText(
//                                applicationContext,
//                                "Some Technical Issues.",
//                                Toast.LENGTH_LONG
//                            ).show()
                            }
                        }catch (e: Exception){
                            Toast.makeText(applicationContext,""+e.toString(),Toast.LENGTH_SHORT).show()
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

    private fun QuitBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.quit_popup, null)

        val btnNo = view.findViewById<Button>(R.id.btn_No)
        val btnYes = view.findViewById<Button>(R.id.btn_Yes)

        btnNo.setOnClickListener {
            dialog .dismiss()
        }
        btnYes.setOnClickListener {
            dialog.dismiss()
            finish()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity()
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun LogoutBottomSheet() {
        // BottomSheet

//        val dialog = BottomSheetDialog(this)
//        val view = layoutInflater.inflate(R.layout.logout_popup, null)
//
//        val btnNo = view.findViewById<Button>(R.id.btnNo)
//        val btnYes = view.findViewById<Button>(R.id.btnYes)
//
//        btnNo.setOnClickListener {
//            dialog .dismiss()
//
//        }
//        btnYes.setOnClickListener {
//            dialog.dismiss()
//            // dologoutchanges()
//            Config.logOut(context,1)
//            startActivity(Intent(this@MpinActivity, SplashActivity::class.java))
//        }
//        dialog.setCancelable(false)
//        dialog!!.setContentView(view)
//
//        dialog.show()


        val db = DBHelper(this, null)
        var userList = JSONArray()
        userList = db!!.getRegisteredUserList()

        tableCount = userList.length()
        logoutMode = 1

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.logout_popup, null)

        val btnNo = view.findViewById<Button>(R.id.btnNo)
        val btnYes = view.findViewById<Button>(R.id.btnYes)
        val btnSingleNo = view.findViewById<Button>(R.id.btnSingleNo)
        val tv_message = view.findViewById<TextView>(R.id.tv_message)

        if (tableCount > 1){
            tv_message.text = "Do you want to logout Multiple Account?"
            btnSingleNo!!.visibility = View.VISIBLE
            btnNo!!.visibility = View.GONE
        }else{
            tv_message.text = "Do you want to logout?"
        }

        btnNo.setOnClickListener {
            dialog .dismiss()

        }
        btnSingleNo.setOnClickListener {
            btnSingleNo!!.visibility = View.GONE
            btnNo!!.visibility = View.VISIBLE
            val ResellerNameSP = context.getSharedPreferences(Config.SHARED_PREF32, 0)
            var resellerName = ResellerNameSP.getString("ResellerName", "")
            tv_message.text = "Do you want to logout "+resellerName+" Account?"

            logoutMode = 0

        }
        btnYes.setOnClickListener {
            dialog.dismiss()
            // dologoutchanges()
            Config.logOut(context,logoutMode)
            startActivity(Intent(this@MpinActivity, SplashActivity::class.java))
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

}



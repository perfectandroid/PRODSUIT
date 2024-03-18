package com.perfect.prodsuit.Repository

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.Model.LeadGenerateSaveModel
import com.perfect.prodsuit.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

object LeadGenerateSaveRepository {

    private var progressDialog: ProgressDialog? = null
    val leadGenSaveSetterGetter = MutableLiveData<LeadGenerateSaveModel>()
    val TAG: String = "LeadGenerateSaveRepository"

    fun getServicesApiCall(
        context: Context,
        saveUpdateMode: String,
        ID_LeadGenerate: String,
        strDate: String,
        ID_Customer: String,
        ID_MediaSubMaster: String,
        CusNameTitle: String,
        Customer_Name: String,
        Customer_Address1: String,
        Customer_Address2: String,
        Customer_Mobile: String,
        Customer_Email: String,
        strCompanyContact: String,
        FK_Country: String,
        FK_States: String,
        FK_District: String,
        FK_Post: String,
        strPincode: String,
        FK_Area: String,
        ID_LeadFrom: String,
        ID_LeadThrough: String,
        strLeadThrough: String,
        strWhatsAppNo: String,
        strLatitude: String,
        strLongitue: String,
        encode1: String,
        encode2: String,
        Customer_Mode: String,
        Customer_Type: String,
        ID_CustomerAssignment: String,
        ID_CollectedBy: String,
        ID_AuthorizationData: String,
        array_product_lead: JSONArray,
        customerMobile2: String
    ): MutableLiveData<LeadGenerateSaveModel> {
        saveLeadGenerate(context, saveUpdateMode!!, ID_LeadGenerate!!, strDate, ID_Customer, ID_MediaSubMaster, CusNameTitle,
            Customer_Name, Customer_Address1, Customer_Address2, Customer_Mobile, Customer_Email, strCompanyContact, FK_Country, FK_States, FK_District, FK_Post, strPincode,
            FK_Area, ID_LeadFrom, ID_LeadThrough, strLeadThrough, strWhatsAppNo, strLatitude, strLongitue, encode1, encode2,Customer_Mode,Customer_Type,ID_CustomerAssignment,ID_CollectedBy,ID_AuthorizationData,array_product_lead,customerMobile2)
        Log.e("LeadGenerateSaveRepository"," 226666    ")
        return leadGenSaveSetterGetter
    }

    private fun saveLeadGenerate(
        context: Context,
        saveUpdateMode: String,
        ID_LeadGenerate: String,
        strDate: String,
        ID_Customer: String,
        ID_MediaSubMaster: String,
        CusNameTitle: String,
        Customer_Name: String,
        Customer_Address1: String,
        Customer_Address2: String,
        Customer_Mobile: String,
        Customer_Email: String,
        strCompanyContact: String,
        FK_Country: String,
        FK_States: String,
        FK_District: String,
        FK_Post: String,
        strPincode: String,
        FK_Area: String,
        ID_LeadFrom: String,
        ID_LeadThrough: String,
        strLeadThrough: String,
        strWhatsAppNo: String,
        strLatitude: String,
        strLongitue: String,
        encode1: String,
        encode2: String,
        Customer_Mode: String,
        Customer_Type: String,
        ID_CustomerAssignment: String,
        ID_CollectedBy: String,
        ID_AuthorizationData: String,
        array_product_lead: JSONArray,
        customerMobile2: String
    ) {


    //   var Customer_Mobile2 ="9895314400"
        Log.e("TAG","saveLeadGenerate  ")
        Log.e(TAG,"LocationValidation  6421232"
                +"\n"+"ID_LeadGenerate    : "+ ID_LeadGenerate
                +"\n"+"Enquiry date       : "+ strDate
//                +"\n"+"Attended by        : "+ ID_CollectedBy
                +"\n"+"Lead Source        : "+ ID_LeadFrom
                +"\n"+"Lead From          : "+ ID_LeadThrough
                +"\n"+"ID_MediaSubMaster  : "+ ID_MediaSubMaster
                +"\n"+"strLeadThrough     : "+ strLeadThrough
                +"\n"
                +"\n"+"CusNameTitle       : "+ CusNameTitle +"@"
                +"\n"+"CusNameTitle       : "+ CusNameTitle!!.length
                +"\n"+"ID_Customer        : "+ ID_Customer
                +"\n"+"ID_CustomerAssignment        : "+ ID_CustomerAssignment
                +"\n"+"Customer_Name      : "+ Customer_Name
                +"\n"+"Customer_Mobile    : "+ Customer_Mobile
                +"\n"+"customerMobile2    : "+ customerMobile2
                +"\n"+"WhatsApp No        : "+ strWhatsAppNo
                +"\n"+"Company Contact    : "+ strCompanyContact
                +"\n"+"Customer_Email     : "+ Customer_Email
                +"\n"+"Customer_Address1  : "+ Customer_Address1
                +"\n"+"Customer_Address2  : "+ Customer_Address2
                +"\n"+"Address 3          : "+ FK_Area
                +"\n"
                +"\n"+"FK_Country        : "+ FK_Country
                +"\n"+"FK_States         : "+ FK_States
                +"\n"+"FK_District       : "+ FK_District
                +"\n"+"FK_Area           : "+ FK_Area
                +"\n"+"FK_Post           : "+ FK_Post
                +"\n"+"strPincode        : "+ strPincode
                +"\n"
//                +"\n"+"ID_Category        : "+ ID_Category
//                +"\n"+"ID_Product         : "+ ID_Product
//                +"\n"+"strQty             : "+ strQty
//                +"\n"+"strProduct         : "+ strProduct
//                +"\n"+"strProject         : "+ strProject
//                +"\n"+"ID_Priority        : "+ ID_Priority
//                +"\n"+"strFeedback        : "+ strFeedback
//                +"\n"+"ID_Status          : "+ ID_Status
//                +"\n"+"ID_NextAction      : "+ ID_NextAction
//                +"\n"+"ID_ActionType      : "+ ID_ActionType
//                +"\n"+"strFollowupdate    : "+ strFollowupdate
//                +"\n"+"ID_Branch          : "+ ID_Branch
//                +"\n"+"ID_BranchType      : "+ ID_BranchType
//                +"\n"+"ID_Department      : "+ ID_Department
//                +"\n"+"ID_Employee        : "+ ID_Employee
                +"\n"
                +"\n"+"strLatitude        : "+ strLatitude
                +"\n"+"strLongitue        : "+ strLongitue
                +"\n"+"Customer_Mode      : "+ Customer_Mode
                +"\n"+"Customer_Type      : "+ Customer_Type)

        try {
            leadGenSaveSetterGetter.value =LeadGenerateSaveModel("")
            val BASE_URLSP = context.getSharedPreferences(Config.SHARED_PREF7, 0)
            progressDialog = ProgressDialog(context, R.style.Progress)
            progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setIndeterminate(true)
            progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(
                R.drawable.progress))
            progressDialog!!.show()
            val client = OkHttpClient.Builder()
                .sslSocketFactory(Config.getSSLSocketFactory(context))
                .hostnameVerifier(Config.getHostnameVerifier())
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URLSP.getString("BASE_URL", null))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            val apiService = retrofit.create(ApiInterface::class.java!!)
            val requestObject1 = JSONObject()
            try {

//                "ReqMode":"33",
//                "BankKey":"-500",
//                "FK_Employee":123,
//                "Token":sfdsgdgdg,
//                "Pincode":"641231"

                val TokenSP = context.getSharedPreferences(Config.SHARED_PREF5, 0)
                val FK_EmployeeSP = context.getSharedPreferences(Config.SHARED_PREF1, 0)
                val UserNameSP = context.getSharedPreferences(Config.SHARED_PREF2, 0)
                val BankKeySP = context.getSharedPreferences(Config.SHARED_PREF9, 0)
                val FK_CompanySP = context.getSharedPreferences(Config.SHARED_PREF39, 0)
                val FK_BranchCodeUserSP = context.getSharedPreferences(Config.SHARED_PREF40, 0)
                val UserCodeSP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
                val FK_ID_UserSP = context.getSharedPreferences(Config.SHARED_PREF44, 0)
                val ID_TokenUserSP = context.getSharedPreferences(Config.SHARED_PREF85, 0)


//                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
//                requestObject1.put("UserName", ProdsuitApplication.encryptStart(UserNameSP.getString("UserName", null))) //New
//                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
//                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(saveUpdateMode))
//
//                requestObject1.put("TransMode", ProdsuitApplication.encryptStart("LFLG")) // HARD //New
//                requestObject1.put("ID_LeadGenerate", ProdsuitApplication.encryptStart("0"))  //New
//                requestObject1.put("LgCusNameTitle", ProdsuitApplication.encryptStart("Mr. ")) //New
//                requestObject1.put("CusMobileAlternate", ProdsuitApplication.encryptStart(strContactNumber)) //New
//                requestObject1.put("FK_LeadByName", ProdsuitApplication.encryptStart("Lead NAme")) //New
//                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart("1"))  //New
//                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart("2"))  //New
//                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart("test"))  //New
//                requestObject1.put("PreviousID", ProdsuitApplication.encryptStart("0")) // HARD //New
//                requestObject1.put("LastID", ProdsuitApplication.encryptStart("0")) //New
//
//                requestObject1.put("LgLeadDate", ProdsuitApplication.encryptStart(strDate))
//                requestObject1.put("FK_LeadFrom", ProdsuitApplication.encryptStart(ID_LeadFrom))
//                requestObject1.put("FK_LeadBy", ProdsuitApplication.encryptStart(ID_LeadThrough))
//                requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(ID_Customer))
//                requestObject1.put("LgCusName", ProdsuitApplication.encryptStart(Customer_Name))
//                requestObject1.put("LgCusAddress", ProdsuitApplication.encryptStart(Customer_Address))
//                requestObject1.put("LgCusMobile", ProdsuitApplication.encryptStart(Customer_Mobile))
//                requestObject1.put("LgCusEmail", ProdsuitApplication.encryptStart(Customer_Email))
//                requestObject1.put("CusCompany", ProdsuitApplication.encryptStart(CompanyNme))
//                requestObject1.put("CusPerson", ProdsuitApplication.encryptStart(strContactPerson))
//                requestObject1.put("CusPhone", ProdsuitApplication.encryptStart(strContactNumber))
//                requestObject1.put("FK_MediaMaster", ProdsuitApplication.encryptStart(ID_MediaMaster))
//                requestObject1.put("FK_SubMedia", ProdsuitApplication.encryptStart("0")) //New
//
//                requestObject1.put("FK_Country", ProdsuitApplication.encryptStart(FK_Country))
//                requestObject1.put("FK_State", ProdsuitApplication.encryptStart(FK_States))
//                requestObject1.put("FK_District", ProdsuitApplication.encryptStart(FK_District))
//                requestObject1.put("FK_Area", ProdsuitApplication.encryptStart(FK_Area))
//                requestObject1.put("FK_Post", ProdsuitApplication.encryptStart(FK_Post))
//
//                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart(ID_Category))
//                requestObject1.put("ID_Product", ProdsuitApplication.encryptStart(ID_Product))
//                requestObject1.put("ProdName", ProdsuitApplication.encryptStart(strProdName))
//                requestObject1.put("ProjectName", ProdsuitApplication.encryptStart(strProdName))
//                requestObject1.put("LgpPQuantity", ProdsuitApplication.encryptStart(strQty))
//                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))
//                requestObject1.put("LgpDescription", ProdsuitApplication.encryptStart(strFeedback))
//                requestObject1.put("ActStatus", ProdsuitApplication.encryptStart(ID_Status))
//
//                requestObject1.put("FK_NetAction", ProdsuitApplication.encryptStart(ID_NextAction))
//                requestObject1.put("FK_ActionType", ProdsuitApplication.encryptStart(ID_ActionType))
//                requestObject1.put("NextActionDate", ProdsuitApplication.encryptStart(strFollowupdate))
//                requestObject1.put("BranchID", ProdsuitApplication.encryptStart(ID_Branch))
//                requestObject1.put("BranchTypeID", ProdsuitApplication.encryptStart(ID_BranchType))
//                requestObject1.put("FK_Departement", ProdsuitApplication.encryptStart(ID_Department))
//                requestObject1.put("AssignEmp", ProdsuitApplication.encryptStart(ID_Employee))
//
//                requestObject1.put("LocLatitude", ProdsuitApplication.encryptStart(strLatitude))
//                requestObject1.put("LocLongitude", ProdsuitApplication.encryptStart(strLongitue))
//                requestObject1.put("LocationAddress", ProdsuitApplication.encryptStart(locAddress))
////                requestObject1.put("LocationLandMark1", ProdsuitApplication.encryptStart(encode1))
////                requestObject1.put("LocationLandMark2", ProdsuitApplication.encryptStart(encode2))
//                requestObject1.put("LocationLandMark1", encode1)
//                requestObject1.put("LocationLandMark2", encode2)


                requestObject1.put("BankKey", ProdsuitApplication.encryptStart(BankKeySP.getString("BANK_KEY", null)))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(FK_EmployeeSP.getString("FK_Employee", null)))
                requestObject1.put("UserName", ProdsuitApplication.encryptStart(UserNameSP.getString("UserName", null))) //New
                requestObject1.put("Token", ProdsuitApplication.encryptStart(TokenSP.getString("Token", null)))
                requestObject1.put("UserAction", ProdsuitApplication.encryptStart(saveUpdateMode))
                requestObject1.put("ID_User", ProdsuitApplication.encryptStart(FK_ID_UserSP.getString("ID_User", null)))


                requestObject1.put("TransMode", ProdsuitApplication.encryptStart("LFLG")) // HARD //New
                requestObject1.put("ID_LeadGenerate", ProdsuitApplication.encryptStart(ID_LeadGenerate))  //New
                requestObject1.put("LgLeadDate", ProdsuitApplication.encryptStart(strDate))
            //    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(ID_Customer))
                requestObject1.put("FK_SubMedia", ProdsuitApplication.encryptStart(ID_MediaSubMaster))
                requestObject1.put("LgCusNameTitle", ProdsuitApplication.encryptStart(CusNameTitle)) //New
                requestObject1.put("LgCusName", ProdsuitApplication.encryptStart(Customer_Name))
                requestObject1.put("LgCusAddress", ProdsuitApplication.encryptStart(Customer_Address1))
                requestObject1.put("LgCusAddress2", ProdsuitApplication.encryptStart(Customer_Address2))
                requestObject1.put("LgCusMobile", ProdsuitApplication.encryptStart(Customer_Mobile))
                requestObject1.put("LandNumber", ProdsuitApplication.encryptStart(customerMobile2))
                requestObject1.put("LgCusEmail", ProdsuitApplication.encryptStart(Customer_Email))
                requestObject1.put("CusCompany", ProdsuitApplication.encryptStart(strCompanyContact))
                requestObject1.put("CusPerson", ProdsuitApplication.encryptStart(""))  //Removed
                requestObject1.put("CusPhone", ProdsuitApplication.encryptStart("")) //Removed


                requestObject1.put("FK_Country", ProdsuitApplication.encryptStart(FK_Country))
                requestObject1.put("FK_States", ProdsuitApplication.encryptStart(FK_States))
                requestObject1.put("FK_District", ProdsuitApplication.encryptStart(FK_District))
                requestObject1.put("FK_Area", ProdsuitApplication.encryptStart(FK_Area))
                requestObject1.put("FK_Post", ProdsuitApplication.encryptStart(FK_Post))

                requestObject1.put("LastID", ProdsuitApplication.encryptStart("0")) //New
                requestObject1.put("FK_LeadFrom", ProdsuitApplication.encryptStart(ID_LeadFrom))
                requestObject1.put("FK_LeadBy", ProdsuitApplication.encryptStart(ID_LeadThrough))
                requestObject1.put("LeadByName", ProdsuitApplication.encryptStart(strLeadThrough))
                requestObject1.put("FK_MediaMaster", ProdsuitApplication.encryptStart(""))  // Removed

//                requestObject1.put("LgCollectedBy", ProdsuitApplication.encryptStart(ID_CollectedBy))
                requestObject1.put("FK_Company", ProdsuitApplication.encryptStart(FK_CompanySP.getString("FK_Company", null)))
                requestObject1.put("FK_BranchCodeUser", ProdsuitApplication.encryptStart(FK_BranchCodeUserSP.getString("FK_BranchCodeUser", null)))
                requestObject1.put("EntrBy", ProdsuitApplication.encryptStart(UserCodeSP.getString("UserCode", null)))
                requestObject1.put("PreviousID", ProdsuitApplication.encryptStart("0")) // HARD //New
                requestObject1.put("CusMobileAlternate", ProdsuitApplication.encryptStart(strWhatsAppNo)) //New
                requestObject1.put("FK_AuthorizationData", ProdsuitApplication.encryptStart(ID_AuthorizationData)) //New



//                requestObject1.put("FK_Category", ProdsuitApplication.encryptStart(ID_Category))
//                requestObject1.put("ID_Product", ProdsuitApplication.encryptStart(ID_Product))
//                requestObject1.put("ProdName", ProdsuitApplication.encryptStart(strProduct))
//                requestObject1.put("ProjectName", ProdsuitApplication.encryptStart(strProject))
//                requestObject1.put("LgpPQuantity", ProdsuitApplication.encryptStart(strQty))
//                requestObject1.put("FK_Priority", ProdsuitApplication.encryptStart(ID_Priority))
//                requestObject1.put("LgpDescription", ProdsuitApplication.encryptStart(strFeedback))
//                requestObject1.put("ActStatus", ProdsuitApplication.encryptStart(ID_Status))
//
//                requestObject1.put("FK_NetAction", ProdsuitApplication.encryptStart(ID_NextAction))
//                requestObject1.put("FK_ActionType", ProdsuitApplication.encryptStart(ID_ActionType))
//                requestObject1.put("NextActionDate", ProdsuitApplication.encryptStart(strFollowupdate))
//                requestObject1.put("BranchID", ProdsuitApplication.encryptStart(ID_Branch))
//                requestObject1.put("BranchTypeID", ProdsuitApplication.encryptStart(ID_BranchType))
//                requestObject1.put("FK_Departement", ProdsuitApplication.encryptStart(ID_Department))
//                requestObject1.put("FK_Employee", ProdsuitApplication.encryptStart(ID_Employee))
                requestObject1.put("LgCollectedBy", ProdsuitApplication.encryptStart(ID_CollectedBy))
                requestObject1.put("LocLatitude", ProdsuitApplication.encryptStart(strLatitude))
                requestObject1.put("LocLongitude", ProdsuitApplication.encryptStart(strLongitue))
//                requestObject1.put("LgpExpectDate", ProdsuitApplication.encryptStart(strExpecteddate))
              //  requestObject1.put("LocationAddress", ProdsuitApplication.encryptStart(locAddress))
//                requestObject1.put("LocationLandMark1", ProdsuitApplication.encryptStart(encode1))
//                requestObject1.put("LocationLandMark2", ProdsuitApplication.encryptStart(encode2))
                requestObject1.put("LocationLandMark1", encode1)
                requestObject1.put("LocationLandMark2", encode2)
                if (Customer_Type.equals("0")){
                    Log.e(TAG,"642121   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                    requestObject1.put("FK_CustomerOthers", ProdsuitApplication.encryptStart(ID_Customer))
                }else if (Customer_Type.equals("1")){
                    Log.e(TAG,"642122   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart(ID_Customer))
                    requestObject1.put("FK_CustomerOthers", ProdsuitApplication.encryptStart("0"))
                }else{
                    Log.e(TAG,"642123   "+ID_Customer)
                    requestObject1.put("FK_Customer", ProdsuitApplication.encryptStart("0"))
                    requestObject1.put("FK_CustomerOthers", ProdsuitApplication.encryptStart("0"))
                }
                requestObject1.put("ID_CustomerAssignment", ProdsuitApplication.encryptStart(ID_CustomerAssignment))
                requestObject1.put("ProductDetails", (array_product_lead))
                requestObject1.put("ID_TokenUser", ProdsuitApplication.encryptStart(ID_TokenUserSP.getString("ID_TokenUser", null)))


                Log.e(TAG,"FK_Area   1360   "+FK_Area+"   "+UserNameSP.getString("UserName", null))
                Log.e(TAG,"array_product_lead    330022   "+array_product_lead)
//                Log.e(TAG,"requestObject1   1361   "+strDate+"   "+strFollowupdate)
                Log.e(TAG,"Customer_Type   1362   "+Customer_Type)
                Log.e(TAG,"requestObject1   1362   "+encode1)
                Log.e(TAG,"requestObject1   1363   "+requestObject1)


            } catch (e: Exception) {
                Log.v("hjhvbhk","gghg"+e)
                e.printStackTrace()
            }
            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                requestObject1.toString()
            )
            val call = apiService.saveUpdateLeadGeneration(body)
            call.enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(
                    call: retrofit2.Call<String>, response:
                    Response<String>
                ) {
                    try {
                        progressDialog!!.dismiss()
                        Log.e(TAG,"response  95   "+response.body())
                        val jObject = JSONObject(response.body())
                        val leadSave = ArrayList<LeadGenerateSaveModel>()
                        Log.e(TAG,"pincode  95   "+leadSave)
                        leadSave.add(LeadGenerateSaveModel(response.body()))
                        val msg = leadSave[0].message
                        leadGenSaveSetterGetter.value = LeadGenerateSaveModel(msg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show()
                        progressDialog!!.dismiss()
                    }
                }
                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    progressDialog!!.dismiss()
                    Toast.makeText(context, ""+Config.SOME_TECHNICAL_ISSUES, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
            progressDialog!!.dismiss()
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show()
        }

    }

}
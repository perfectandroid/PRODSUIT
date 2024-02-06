package com.perfect.prodsuit.Helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.android.libraries.places.internal.db
import org.json.JSONArray
import org.json.JSONObject


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    var TAG = "DBHelper"

    override fun onCreate(db: SQLiteDatabase) {
        // TODO Auto-generated method stub
        db.execSQL("create table travel_location " + "(id integer primary key, date text,time text,battery text, address text)")
        db.execSQL("create table chat_all_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text)")
        db.execSQL("create table chat_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text,senderID text,userToken text)")  // ,userToken text


        db.execSQL(
            "create table servicedetailmainlist " + "(id integer primary key, MasterProduct text, FK_Product text, Product text,Mode text," +
                    "BindProduct text,ComplaintProduct text,Warranty text,ServiceWarrantyExpireDate text,ReplacementWarrantyExpireDate text,ID_CustomerWiseProductDetails text,ServiceWarrantyExpired text,ReplacementWarrantyExpired text)"
        )

        db.execSQL(
            "create table servicedetailsublist " + "(id integer primary key,FK_Product_parent text,FK_Category text, MasterProduct text,FK_Product text, FK_Product_sub text, SLNo text, BindProduct text," +
                    "ComplaintProduct text,Warranty text,ServiceWarrantyExpireDate text,ReplacementWarrantyExpireDate text,ID_CustomerWiseProductDetails text,ServiceWarrantyExpired text,ReplacementWarrantyExpired text)"
        )

        db.execSQL(
            "create table Company " + "(ID_Company integer primary key,Base_Url text,Image_Url text, Bank_key text,Cert_Name text,Company_Code text, Company_Status Boolean, IP_Default Boolean)"
        )

        db.execSQL(
            "create table ResellerDetails " + "(ID_Reseller integer primary key,ID_Company text,ResellerName text,AppIconImageCode text, TechnologyPartnerImage text," +
                    "ProductName text,PlayStoreLink text, AppStoreLink text, ContactNumber text, ContactEmail text, ContactAddress text, CertificateName text, TestingURL text," +
                    " TestingMachineId text, TestingImageURL text, TestingMobileNo text, TestingBankKey text, TestingBankHeader text, AboutUs text, AudioClipEnabled text," +
                    " IsLocationDistanceShowing text, EditMRPLead text)"
        )

        db.execSQL(
            "create table LoginUser " + "(ID_LoginUser integer primary key,ID_Company text,FK_Employee text, UserName text,Address text,MobileNumber text," +
                    " Token text, UserCode text, FK_Branch text, BranchName text, FK_BranchType text, FK_Company text, FK_BranchCodeUser text, FK_UserRole text," +
                    " UserRole text, IsAdmin text, IsManager text, ID_User text, FK_Department text, Department text, CompanyCategory text, userMpin text)"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO Auto-generated method stub

        if (oldVersion < 2) {
            db.execSQL("create table chat_all_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text)")
            db.execSQL("create table chat_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text,senderID text)") //,userToken text
        } else if (oldVersion < 3) {

            db.execSQL(
                "create table servicedetailmainlist " + "(id integer primary key, MasterProduct text, FK_Product text, Product text,Mode text," +
                        "BindProduct text,ComplaintProduct text,Warranty text,ServiceWarrantyExpireDate text,ReplacementWarrantyExpireDate text,ID_CustomerWiseProductDetails text,ServiceWarrantyExpired text,ReplacementWarrantyExpired text)"
            )

//            db.execSQL("create table servicedetailsublist " + "(id integer primary key,FK_Product text, FK_Category text,MasterProduct text, FK_Product_sub text, Product text, SLNo text," +
//                    "BindProduct text,Warranty text,ServiceWarrantyExpireDate text,ReplacementWarrantyExpireDate text,ID_CustomerWiseProductDetails text,ServiceWarrantyExpired text,ReplacementWarrantyExpired text)")

            db.execSQL(
                "create table servicedetailsublist " + "(id integer primary key,FK_Category text, MasterProduct text,FK_Product text, FK_Product_sub text, SLNo text, BindProduct text," +
                        "ComplaintProduct text,Warranty text,ServiceWarrantyExpireDate text,ReplacementWarrantyExpireDate text,ID_CustomerWiseProductDetails text,ServiceWarrantyExpired text,ReplacementWarrantyExpired text)"
            )
        }
        else if (oldVersion < 4) {
            Log.e(TAG,"58888   "+oldVersion +   " : "+newVersion)
            db.execSQL("ALTER TABLE chat_user ADD COLUMN userToken text")
           // db.execSQL("create table chat_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text,senderID text,userToken text)")
        }
        else if (oldVersion < 5) {
            Log.e(TAG,"58888   "+oldVersion +   " : "+newVersion)
            db.execSQL(
                "create table Company " + "(ID_Company integer primary key,Base_Url text,Image_Url text, Bank_key text,Cert_Name text,Company_Code text, Company_Status Boolean, IP_Default Boolean)"
            )

            db.execSQL(
                "create table ResellerDetails " + "(ID_Reseller integer primary key,ID_Company text,ResellerName text,AppIconImageCode text, TechnologyPartnerImage text," +
                        "ProductName text,PlayStoreLink text, AppStoreLink text, ContactNumber text, ContactEmail text, ContactAddress text, CertificateName text, TestingURL text," +
                        " TestingMachineId text, TestingImageURL text, TestingMobileNo text, TestingBankKey text, TestingBankHeader text, AboutUs text, AudioClipEnabled text," +
                        " IsLocationDistanceShowing text, EditMRPLead text)"
            )




        }
        if (oldVersion < 6){
            db.execSQL(
                "create table LoginUser " + "(ID_LoginUser integer primary key,ID_Company text,FK_Employee text, UserName text,Address text,MobileNumber text," +
                        " Token text, UserCode text, FK_Branch text, BranchName text, FK_BranchType text, FK_Company text, FK_BranchCodeUser text, FK_UserRole text," +
                        " UserRole text, IsAdmin text, IsManager text, ID_User text, FK_Department text, Department text, CompanyCategory text)"
            )
        }
        if (oldVersion < 7){
            db.execSQL("ALTER TABLE LoginUser ADD COLUMN userMpin text")
        }

        // onCreate(db)
    }

    fun addFirebaseUser(
        name: String,
        BranchName: String,
        user_sender: String,
        user_receiver: String,
        chatkey1: String
    ) {

        try {
            val dbRead = this.readableDatabase
            val dbWrite = writableDatabase
            val cursor = dbRead.rawQuery(
                "SELECT * FROM " + "chat_all_user WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )",
                null
            )
            if (cursor.count == 0) {

                dbWrite.execSQL("INSERT INTO chat_all_user (name,BranchName,user_1,user_2,chatkey) VALUES ('$name','$BranchName','$user_sender','$user_receiver','$chatkey1')")

            } else {
                if (!chatkey1.equals("")) {
                    dbWrite.execSQL("UPDATE chat_all_user SET chatkey = '$chatkey1' WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "49999  " + e.toString())
        }


    }

    fun addFirebaseChatUser(
        name: String,
        BranchName: String,
        user_sender: String,
        user_receiver: String,
        chatkey1: String,
        senderID: String,
        userToken : String
    ) {
        try {

            Log.e(TAG,"10771  addFirebaseChatUser   "+name+"  :  "+BranchName+"  :  "+user_sender+"  :  "+user_receiver+"  :  "+chatkey1+"  :  "+senderID+"  :  "+userToken)
            val dbRead = this.readableDatabase
            val dbWrite = writableDatabase
            val cursor = dbRead.rawQuery(
                "SELECT * FROM " + "chat_user WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )",
                null
            )
            Log.e(TAG,"1005001    "+cursor.count)
            Log.e(TAG,"1005002    "+chatkey1)

            if (cursor.count == 0) {
                dbWrite.execSQL("INSERT INTO chat_user (name,BranchName,user_1,user_2,chatkey,senderID,userToken) VALUES ('$name','$BranchName','$user_sender','$user_receiver','$chatkey1','$senderID','$userToken')")
            } else {
                Log.e(TAG,"1005003   Count   "+cursor.count)
                Log.e(TAG,"1005004   Count   "+chatkey1)
                if (!chatkey1.equals("")) {
                    dbWrite.execSQL("UPDATE chat_user SET chatkey = '$chatkey1' , userToken = '$userToken' WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG,"1005006   Exe   "+e.toString())
            Log.e(TAG, "49999  " + e.toString())
        }
    }

    fun deleteFirebaseUser() {
        try {
            val dbWrite = writableDatabase
            dbWrite.execSQL("DELETE FROM chat_all_user")
        } catch (e: Exception) {
            Log.e(TAG, "5555  " + e.toString())
        }

    }

    @SuppressLint("Range")
    fun getChatKeyFromUser(user_sender: String, user_receiver: String): String {

        Log.e(TAG, "66661   " + user_sender + "  :  " + user_receiver)
        var chatKey = ""
        val dbRead = this.readableDatabase
        val cursor = dbRead.rawQuery(
            "SELECT * FROM " + "chat_all_user WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )",
            null
        )
        Log.e(TAG, "66662   " + cursor.count)
        if (cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    chatKey = cursor.getString(cursor.getColumnIndex("chatkey"))
                } while (cursor.moveToNext())
            }
        }

        return chatKey
    }

    fun addName(date: String, time: String, battery: String, address: String) {

        try {
            val values = ContentValues()

            // we are inserting our values
            // in the form of key-value pair
            values.put("date", date)
            values.put("time", time)
            values.put("battery", battery)
            values.put("address", address)
            val db = this.writableDatabase
            db.insert("travel_location", null, values)
            Log.e(TAG, "  40000235  Success " + address + "   " + battery)
        } catch (e: Exception) {
            Log.e(TAG, "Exception  40000236   " + e.toString())
        }

    }

    @SuppressLint("Range")
    fun getLocations() {
        // as we want to read value from it
        val db = this.readableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM " + "travel_location WHERE date = '01-07-2023' ", null)
        //   val cursor = db.rawQuery("SELECT * FROM " + "travel_location where address='NotificationService' ", null)
        Log.e(TAG, "  400002351  cursor " + cursor.count)
        cursor!!.moveToFirst()
        while (cursor.moveToNext()) {
            Log.e(
                TAG,
                "5222   " + cursor.getString(cursor.getColumnIndex("date")) + "  :  " + cursor.getString(
                    cursor.getColumnIndex("time")
                )
            )
        }
    }

    fun delete() {

        val dbs = this.writableDatabase
        // val db = this.writableDatabase

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        //   dbs.execSQL("delete from "+ "travel_location");

    }

    fun getChatUserData(): JSONArray {
        val jsonArray = JSONArray()

        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("select * from chat_user", null)
        if (cursor.moveToFirst()) {
            do {
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("id", cursor.getString(0))
                    jsonObject.put("name", cursor.getString(1))
                    jsonObject.put("BranchName", cursor.getString(2))
                    jsonObject.put("user_1", cursor.getString(3))
                    jsonObject.put("user_2", cursor.getString(4))
                    jsonObject.put("chatkey", cursor.getString(5))
                    jsonObject.put("senderID", cursor.getString(6))
                    jsonObject.put("userToken", cursor.getString(7))

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                jsonArray.put(jsonObject)
            } while (cursor.moveToNext())
        }
        return jsonArray

    }

    fun deleteChatdb() {
        try {
            val dbWrite = writableDatabase
            dbWrite.execSQL("DELETE FROM chat_all_user")
            dbWrite.execSQL("DELETE FROM chat_user")
        } catch (e: Exception) {
            Log.e(TAG, "5555  " + e.toString())
        }
    }

    fun insertServiceDetails(jsonArrayServiceType: JSONArray) {
        try {
            val dbRead = this.readableDatabase
            val dbWrite = writableDatabase
            for (i in 0 until jsonArrayServiceType.length()) {
                var jsonObject = jsonArrayServiceType.getJSONObject(i)
                var MasterProduct = jsonObject.getString("MasterProduct")
                var FK_Product = jsonObject.getString("FK_Product")
                var Product = jsonObject.getString("Product")
                var Mode = jsonObject.getString("Mode")
                var BindProduct = jsonObject.getString("BindProduct")
                var ComplaintProduct = jsonObject.getString("ComplaintProduct")
                var Warranty = jsonObject.getString("Warranty")
                var ServiceWarrantyExpireDate = jsonObject.getString("ServiceWarrantyExpireDate")
                var ReplacementWarrantyExpireDate =
                    jsonObject.getString("ReplacementWarrantyExpireDate")
                var ID_CustomerWiseProductDetails =
                    jsonObject.getString("ID_CustomerWiseProductDetails")
                var ServiceWarrantyExpired = jsonObject.getString("ServiceWarrantyExpired")
                var ReplacementWarrantyExpired = jsonObject.getString("ReplacementWarrantyExpired")
                var write =
                    dbWrite.execSQL("INSERT INTO servicedetailmainlist (MasterProduct,FK_Product,Product,Mode,BindProduct,ComplaintProduct,Warranty,ServiceWarrantyExpireDate,ReplacementWarrantyExpireDate,ID_CustomerWiseProductDetails,ServiceWarrantyExpired,ReplacementWarrantyExpired) VALUES ('$MasterProduct','$FK_Product','$Product','$Mode','$BindProduct','$ComplaintProduct','$Warranty','$ServiceWarrantyExpireDate','$ReplacementWarrantyExpireDate','$ID_CustomerWiseProductDetails','$ServiceWarrantyExpired','$ReplacementWarrantyExpired')")
            }
        } catch (e: Exception) {
            Log.e(TAG, "ggggghjfgtyr  " + e)
        }
    }

    fun insertServiceDetailsdd(jsonArrayServiceType: JSONArray) {
        try {
            Log.e(TAG, "sdfsdfsdfsdfsdddd " + jsonArrayServiceType)

            val dbRead = this.readableDatabase
            val dbWrite = writableDatabase

            for (i in 0 until jsonArrayServiceType.length()) {
                var jsonObject = jsonArrayServiceType.getJSONObject(i)
                var FK_Product = jsonObject.getString("FK_Product")
                var ServiceAttendedListDet = jsonObject.getJSONArray("ServiceAttendedListDet")
                for (j in 0 until ServiceAttendedListDet.length()) {
                    var jsonObjectSub = ServiceAttendedListDet.getJSONObject(j)
                    var FK_Category_sub = jsonObjectSub.getString("FK_Category")
                    var MasterProduct_sub = jsonObjectSub.getString("MasterProduct")
                    var ID_Product = jsonObjectSub.getString("FK_Product")
                    var FK_Product_sub = jsonObjectSub.getString("Product")
                    var SLNo_sub = jsonObjectSub.getString("SLNo")
                    var BindProduct_sub = jsonObjectSub.getString("BindProduct")
                    var ComplaintProduct_sub = jsonObjectSub.getString("ComplaintProduct")
                    var Warranty_sub = jsonObjectSub.getString("Warranty")
                    var ServiceWarrantyExpireDate_sub =
                        jsonObjectSub.getString("ServiceWarrantyExpireDate")
                    var ReplacementWarrantyExpireDate_sub =
                        jsonObjectSub.getString("ReplacementWarrantyExpireDate")
                    var ID_CustomerWiseProductDetails_sub =
                        jsonObjectSub.getString("ID_CustomerWiseProductDetails")
                    var ServiceWarrantyExpired_sub =
                        jsonObjectSub.getString("ServiceWarrantyExpired")
                    var ReplacementWarrantyExpired_sub =
                        jsonObjectSub.getString("ReplacementWarrantyExpired")

                    var writeSub =
                        dbWrite.execSQL("INSERT INTO servicedetailsublist (FK_Product_parent,FK_Category,MasterProduct,FK_Product,FK_Product_sub,Product,SLNo,BindProduct,ComplaintProduct,Warranty,ServiceWarrantyExpireDate,ReplacementWarrantyExpireDate,ID_CustomerWiseProductDetails,ServiceWarrantyExpired,ReplacementWarrantyExpired) VALUES ('$FK_Product','$FK_Category_sub','$MasterProduct_sub','$ID_Product','$FK_Product_sub','$SLNo_sub','$BindProduct_sub','$ComplaintProduct_sub','$Warranty_sub','$ServiceWarrantyExpireDate_sub','$ReplacementWarrantyExpireDate_sub','$ID_CustomerWiseProductDetails_sub','$ServiceWarrantyExpired_sub','$ReplacementWarrantyExpired_sub')")
                    Log.e(TAG, "writeSub  " + writeSub)

                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "sdfsdfsdfsdfsdddd  " + e)
        }

    }

    fun insertDataCompany(BaseUrl : String,ImageUrl : String,BankKey : String,CertName : String,CompanyCode : String,Status : Boolean,IpDefault : Boolean) : String{
        var primaryKey = ""
        try {
            val dbWrite = writableDatabase
            val dbRead = readableDatabase
          //  dbWrite.execSQL("DELETE FROM Company")
//            var BaseUrl = BaseUrl+"2"
            val cursor: Cursor = dbRead.rawQuery("select * from Company WHERE Base_Url= '$BaseUrl' AND Image_Url= '$ImageUrl' AND Bank_key= '$BankKey'", null)
            Log.e(TAG, "cursor 350001   " + cursor.count)
            if (cursor.count == 0) {
            //    var writeSub = dbWrite.execSQL("INSERT INTO Company (Base_Url,Image_Url, Bank_key,Cert_Name,Company_Code,Company_Status,IP_Default) VALUES ('$BaseUrl','$ImageUrl','$BankKey','$CertName','$CompanyCode','$Status','$IpDefault')")

                val db = writableDatabase

                val values = ContentValues()
                values.put("Base_Url", BaseUrl)
                values.put("Image_Url", ImageUrl)
                values.put("Bank_key", BankKey)
                values.put("Cert_Name", CertName)
                values.put("Company_Code", CompanyCode)
                values.put("Company_Status", Status)
                values.put("IP_Default", IpDefault)

                // Insert data into the table and get the primary key
                primaryKey = db.insert("Company", null, values).toString()
                Log.e(TAG, "writeSub 350002   " + primaryKey)
            }else{
                if (cursor.moveToFirst()) {
                    primaryKey =  cursor.getString(0)
                }
                Log.e(TAG, "cursor 350003   " + cursor.count)
            }
            return primaryKey
        } catch (e: Exception) {
            Log.e(TAG, "5555  " + e.toString())
            return primaryKey
        }
    }

    fun insertUpdateReseller(ID_PKey : String,ResellerName : String,AppIconImageCode : String,TechnologyPartnerImage : String,ProductName : String,PlayStoreLink : String,AppStoreLink : String,
    ContactNumber : String,ContactEmail : String,ContactAddress : String,CertificateName : String,TestingURL : String,TestingMachineId : String,TestingImageURL : String,TestingMobileNo : String,
    TestingBankKey : String,TestingBankHeader : String,AboutUs : String,AudioClipEnabled : String,IsLocationDistanceShowing : String,EditMRPLead : String){
        val primaryKey = ""
        try {
            Log.e(TAG, "cursor 350004   "+ID_PKey)
            val dbRead = readableDatabase
            val db = writableDatabase
            val cursor: Cursor = dbRead.rawQuery("select * from ResellerDetails WHERE ID_Company= '$ID_PKey'", null)
            Log.e(TAG, "cursor 350005   " + cursor.count)
            val values = ContentValues()

            if (cursor.count == 0) {

                values.put("ID_Company", ID_PKey)
                values.put("ResellerName", ResellerName)
                values.put("AppIconImageCode", AppIconImageCode)
                values.put("TechnologyPartnerImage", TechnologyPartnerImage)
                values.put("ProductName", ProductName)
                values.put("PlayStoreLink", PlayStoreLink)
                values.put("AppStoreLink", AppStoreLink)
                values.put("ContactNumber", ContactNumber)
                values.put("ContactEmail", ContactEmail)
                values.put("ContactAddress", ContactAddress)
                values.put("CertificateName", CertificateName)
                values.put("TestingURL", TestingURL)
                values.put("TestingMachineId", TestingMachineId)
                values.put("TestingImageURL", TestingImageURL)
                values.put("TestingMobileNo", TestingMobileNo)
                values.put("TestingBankKey", TestingBankKey)
                values.put("TestingBankHeader", TestingBankHeader)
                values.put("AboutUs", AboutUs)
                values.put("AudioClipEnabled", AudioClipEnabled)
                values.put("IsLocationDistanceShowing", IsLocationDistanceShowing)
                values.put("EditMRPLead", EditMRPLead)

                // Insert data into the table and get the primary key
               var primaryKey = db.insert("ResellerDetails", null, values)
                Log.e(TAG, "cursor 350006   " + cursor.count)
            }else{
                Log.e(TAG, "cursor 350007   " + cursor.count)
                values.put("ResellerName", ResellerName)
                values.put("AppIconImageCode", AppIconImageCode)
                values.put("TechnologyPartnerImage", TechnologyPartnerImage)
                values.put("ProductName", ProductName)
                values.put("PlayStoreLink", PlayStoreLink)
                values.put("AppStoreLink", AppStoreLink)
                values.put("ContactNumber", ContactNumber)
                values.put("ContactEmail", ContactEmail)
                values.put("ContactAddress", ContactAddress)
                values.put("CertificateName", CertificateName)
                values.put("TestingURL", TestingURL)
                values.put("TestingMachineId", TestingMachineId)
                values.put("TestingImageURL", TestingImageURL)
                values.put("TestingMobileNo", TestingMobileNo)
                values.put("TestingBankKey", TestingBankKey)
                values.put("TestingBankHeader", TestingBankHeader)
                values.put("AboutUs", AboutUs)
                values.put("AudioClipEnabled", AudioClipEnabled)
                values.put("IsLocationDistanceShowing", IsLocationDistanceShowing)
                values.put("EditMRPLead", EditMRPLead)

                val rowsAffected = db.update("ResellerDetails", values, "ID_Company = ?", arrayOf(ID_PKey))
                Log.e(TAG, "cursor 3500058  " + cursor.count)
            }
        }catch (e: Exception){
            Log.e(TAG, "cursor 350009   " + e.toString())
        }

    }

    fun getDefaultIP() : JSONArray{
        var primaryKey = ""
        val jsonArray = JSONArray()
        try {
            var IP_Default="1"
            Log.e(TAG, "cursor 47001   ")
            val dbRead = readableDatabase
            val cursor: Cursor = dbRead.rawQuery("select * from Company WHERE IP_Default= '$IP_Default' OR IP_Default= 'true'", null)
            Log.e(TAG, "count 47002   "+cursor.count)

            if (cursor.count > 0) {
                if (cursor.moveToFirst()) {
                    val jsonObject = JSONObject()
                    jsonObject.put("ID_Company", cursor.getString(0))
                    jsonObject.put("Base_Url", cursor.getString(1))
                    jsonObject.put("Image_Url", cursor.getString(2))
                    jsonObject.put("Bank_key", cursor.getString(3))
                    jsonObject.put("Cert_Name", cursor.getString(4))
                    jsonObject.put("Company_Code", cursor.getString(5))
                    jsonObject.put("Company_Status", cursor.getString(6))
                    jsonObject.put("IP_Default", cursor.getString(7))
                    primaryKey =  cursor.getString(0)
                    jsonArray.put(jsonObject)

                }
                Log.e(TAG, "primaryKey 47003   "+primaryKey)
            }
        }catch (e: Exception){
            Log.e(TAG, "Exception 47004   "+e.toString())
        }
        return jsonArray
    }

    fun getDefaultCompanyID() : String{
        var Company_ID = ""

        try {

            Log.e(TAG, "cursor 52444   ")
            val dbRead = readableDatabase
            val cursor: Cursor = dbRead.rawQuery("select * from Company WHERE IP_Default= '1' OR IP_Default= 'true'", null)
            Log.e(TAG, "count 47002   "+cursor.count)

            if (cursor.count > 0) {
                if (cursor.moveToFirst()) {
                    Company_ID =  cursor.getString(0)
                }
                Log.e(TAG, "Company_ID 47003   "+Company_ID)
            }
        }catch (e: Exception){
            Log.e(TAG, "Exception 47004   "+e.toString())
        }
        return Company_ID
    }

    fun getLastCompanyKey() : String {

        var primaryKey = ""
        try {
            val dbRead = readableDatabase
            val cursor: Cursor = dbRead.rawQuery("select * from Company", null)
            if (cursor.count > 0) {
                if (cursor.moveToLast()) {
                    primaryKey = cursor.getString(0)
                }
            }
        }catch (e: Exception){

        }
       return primaryKey
    }

    fun updateStatusDefaultIp(primaryKey : String,Status : Boolean,IpDefault : Boolean,mode : String) : String {

        var result = ""
        try {

            val dbRead = readableDatabase
            val dbWrite = writableDatabase

            dbWrite.execSQL("UPDATE Company SET Company_Status = '$Status' , IP_Default = 'true' WHERE ID_Company= '$primaryKey'")
            dbWrite.execSQL("UPDATE Company SET Company_Status = '$Status' , IP_Default = 'false' WHERE ID_Company!= '$primaryKey'")

            result = "Update Successfully"


//            val cursor: Cursor = dbRead.rawQuery("select * from Company", null)
//            if (cursor.count > 0) {
//
//            }
//
//            val values = ContentValues()
//            values.put("Company_Status", Status)
//            values.put("IP_Default", IpDefault)
//
//            val rowsAffected = dbWrite.update("Company", values, "ID_Company = ?", arrayOf(primaryKey))
//            result = rowsAffected.toString()

        }catch (e: Exception){
            result = e.toString()
        }
        return result
    }

    @SuppressLint("Range")
    fun getRegisteredUserList() : JSONArray {
        val jsonArray = JSONArray()
        try {
            val dbRead = readableDatabase
       //     val cursor: Cursor = dbRead.rawQuery("Select * from Company c join ResellerDetails r on c.ID_Company = r.ID_Company where c.Company_Status = '1' or c.Company_Status='true'", null)
            val cursor: Cursor = dbRead.rawQuery("Select * from Company c join ResellerDetails r join LoginUser l  on c.ID_Company = r.ID_Company and c.ID_Company = l.ID_Company where c.Company_Status = '1' or c.Company_Status='true'", null)
            if (cursor.count > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        val jsonObject = JSONObject()
//                        jsonObject.put("ID_Company", cursor.getString(0))
//                        jsonObject.put("Base_Url", cursor.getString(1))
//                        jsonObject.put("Image_Url", cursor.getString(2))
//                        jsonObject.put("Bank_key", cursor.getString(3))
//                        jsonObject.put("Cert_Name", cursor.getString(4))
//                        jsonObject.put("Company_Code", cursor.getString(5))
//                        jsonObject.put("Company_Status", cursor.getString(6))
//                        jsonObject.put("IP_Default", cursor.getString(7))
//                        jsonObject.put("ID_Reseller", cursor.getString(8))
//                        jsonObject.put("ID_Company", cursor.getString(9))
//                        jsonObject.put("ResellerName", cursor.getString(10))
//                        jsonObject.put("AppIconImageCode", cursor.getString(11))
//                        jsonObject.put("TechnologyPartnerImage", cursor.getString(12))
//                        jsonObject.put("ProductName", cursor.getString(13))
//                        jsonObject.put("PlayStoreLink", cursor.getString(14))
//                        jsonObject.put("AppStoreLink", cursor.getString(15))
//                        jsonObject.put("ContactNumber", cursor.getString(16))
//                        jsonObject.put("ContactEmail", cursor.getString(17))
//                        jsonObject.put("ContactAddress", cursor.getString(18))
//                        jsonObject.put("CertificateName", cursor.getString(19))
//                        jsonObject.put("TestingURL", cursor.getString(20))
//                        jsonObject.put("TestingMachineId", cursor.getString(21))
//                        jsonObject.put("TestingImageURL", cursor.getString(22))
//                        jsonObject.put("TestingMobileNo", cursor.getString(23))
//                        jsonObject.put("TestingBankKey", cursor.getString(24))
//                        jsonObject.put("TestingBankHeader", cursor.getString(25))
//                        jsonObject.put("AboutUs", cursor.getString(26))
//                        jsonObject.put("AudioClipEnabled", cursor.getString(27))
//                        jsonObject.put("IsLocationDistanceShowing", cursor.getString(28))
//                        jsonObject.put("EditMRPLead", cursor.getString(29))
//
////                        Login User
//                        jsonObject.put("ID_LoginUser", cursor.getString(31))
//                        jsonObject.put("ID_Company", cursor.getString(32))
//                        jsonObject.put("FK_Employee", cursor.getString(33))
//                        jsonObject.put("UserName", cursor.getString(34))
//                        jsonObject.put("Address", cursor.getString(35))
//                        jsonObject.put("MobileNumber", cursor.getString(36))
//                        jsonObject.put("Token", cursor.getString(37))
//                        jsonObject.put("UserCode", cursor.getString(38))
//                        jsonObject.put("FK_Branch", cursor.getString(39))
//                        jsonObject.put("BranchName", cursor.getString(40))
//
//                        jsonObject.put("FK_BranchType", cursor.getString(41))
//                        jsonObject.put("FK_Company", cursor.getString(42))
//                        jsonObject.put("FK_BranchCodeUser", cursor.getString(43))
//                        jsonObject.put("FK_UserRole", cursor.getString(44))
//                        jsonObject.put("UserRole", cursor.getString(45))
//                        jsonObject.put("IsAdmin", cursor.getString(46))
//                        jsonObject.put("IsManager", cursor.getString(47))
//                        jsonObject.put("ID_User", cursor.getString(48))
//                        jsonObject.put("FK_Department", cursor.getString(49))
//                        jsonObject.put("Department", cursor.getString(50))


                    //    jsonObject.put("CompanyCategory", cursor.getString(51))


                        jsonObject.put("ID_Company", cursor.getString(cursor.getColumnIndex("ID_Company")))
                        jsonObject.put("Base_Url", cursor.getString(cursor.getColumnIndex("Base_Url")))
                        jsonObject.put("Image_Url", cursor.getString(cursor.getColumnIndex("Image_Url")))
                        jsonObject.put("Bank_key", cursor.getString(cursor.getColumnIndex("Bank_key")))
                        jsonObject.put("Cert_Name", cursor.getString(cursor.getColumnIndex("Cert_Name")))
                        jsonObject.put("Company_Code", cursor.getString(cursor.getColumnIndex("Company_Code")))
                        jsonObject.put("Company_Status", cursor.getString(cursor.getColumnIndex("Company_Status")))
                        jsonObject.put("IP_Default", cursor.getString(cursor.getColumnIndex("IP_Default")))


                        //                        Reseller
                        jsonObject.put("ID_Reseller", cursor.getString(cursor.getColumnIndex("ID_Reseller")))
//                        jsonObject.put("ID_Company", cursor.getString(cursor.getColumnIndex("ID_Company")))
                        jsonObject.put("ResellerName", cursor.getString(cursor.getColumnIndex("ResellerName")))
                        jsonObject.put("AppIconImageCode", cursor.getString(cursor.getColumnIndex("AppIconImageCode")))
                        jsonObject.put("TechnologyPartnerImage", cursor.getString(cursor.getColumnIndex("TechnologyPartnerImage")))
                        jsonObject.put("ProductName", cursor.getString(cursor.getColumnIndex("ProductName")))
                        jsonObject.put("PlayStoreLink", cursor.getString(cursor.getColumnIndex("PlayStoreLink")))
                        jsonObject.put("AppStoreLink", cursor.getString(cursor.getColumnIndex("AppStoreLink")))
                        jsonObject.put("ContactNumber", cursor.getString(cursor.getColumnIndex("ContactNumber")))
                        jsonObject.put("ContactEmail", cursor.getString(cursor.getColumnIndex("ContactEmail")))
                        jsonObject.put("ContactAddress", cursor.getString(cursor.getColumnIndex("ContactAddress")))
                        jsonObject.put("CertificateName", cursor.getString(cursor.getColumnIndex("CertificateName")))
                        jsonObject.put("TestingURL", cursor.getString(cursor.getColumnIndex("TestingURL")))
                        jsonObject.put("TestingMachineId", cursor.getString(cursor.getColumnIndex("TestingMachineId")))
                        jsonObject.put("TestingImageURL", cursor.getString(cursor.getColumnIndex("TestingImageURL")))
                        jsonObject.put("TestingMobileNo", cursor.getString(cursor.getColumnIndex("TestingMobileNo")))
                        jsonObject.put("TestingBankKey", cursor.getString(cursor.getColumnIndex("TestingBankKey")))
                        jsonObject.put("TestingBankHeader", cursor.getString(cursor.getColumnIndex("TestingBankHeader")))
                        jsonObject.put("AboutUs", cursor.getString(cursor.getColumnIndex("AboutUs")))
                        jsonObject.put("AudioClipEnabled", cursor.getString(cursor.getColumnIndex("AudioClipEnabled")))
                        jsonObject.put("IsLocationDistanceShowing", cursor.getString(cursor.getColumnIndex("IsLocationDistanceShowing")))
                        jsonObject.put("EditMRPLead", cursor.getString(cursor.getColumnIndex("EditMRPLead")))

//                        Login User
                        jsonObject.put("ID_LoginUser", cursor.getString(cursor.getColumnIndex("ID_LoginUser")))
                        jsonObject.put("FK_Employee", cursor.getString(cursor.getColumnIndex("FK_Employee")))
                        jsonObject.put("UserName", cursor.getString(cursor.getColumnIndex("UserName")))
                        jsonObject.put("Address", cursor.getString(cursor.getColumnIndex("Address")))
                        jsonObject.put("MobileNumber", cursor.getString(cursor.getColumnIndex("MobileNumber")))
                        jsonObject.put("Token", cursor.getString(cursor.getColumnIndex("Token")))
                        jsonObject.put("UserCode", cursor.getString(cursor.getColumnIndex("UserCode")))
                        jsonObject.put("FK_Branch", cursor.getString(cursor.getColumnIndex("FK_Branch")))
                        jsonObject.put("BranchName", cursor.getString(cursor.getColumnIndex("BranchName")))

                        jsonObject.put("FK_BranchType", cursor.getString(cursor.getColumnIndex("FK_BranchType")))
                        jsonObject.put("FK_Company", cursor.getString(cursor.getColumnIndex("FK_Company")))
                        jsonObject.put("FK_BranchCodeUser", cursor.getString(cursor.getColumnIndex("FK_BranchCodeUser")))
                        jsonObject.put("FK_UserRole", cursor.getString(cursor.getColumnIndex("FK_UserRole")))
                        jsonObject.put("UserRole", cursor.getString(cursor.getColumnIndex("UserRole")))
                        jsonObject.put("IsAdmin", cursor.getString(cursor.getColumnIndex("IsAdmin")))
                        jsonObject.put("IsManager", cursor.getString(cursor.getColumnIndex("IsManager")))
                        jsonObject.put("ID_User", cursor.getString(cursor.getColumnIndex("ID_User")))
                        jsonObject.put("FK_Department", cursor.getString(cursor.getColumnIndex("FK_Department")))
                        jsonObject.put("Department", cursor.getString(cursor.getColumnIndex("Department")))
                        jsonObject.put("Department", cursor.getString(cursor.getColumnIndex("Department")))
                        jsonObject.put("userMpin", cursor.getString(cursor.getColumnIndex("userMpin")))

                        jsonArray.put(jsonObject)

                    } while (cursor.moveToNext())
                }
            }
        }catch (e: Exception){
            Log.e(TAG,"58551 Exception  "+e.toString())
        }
        Log.e(TAG,"58552   "+jsonArray.length())
        return jsonArray
    }

    fun deleteCompanyData() {
        val dbWrite = writableDatabase

        dbWrite.execSQL("delete from LoginUser where ID_Company in (select ID_Company from Company where Company_Status != '1' and Company_Status != 'true' ) ")
        dbWrite.execSQL("delete from ResellerDetails where ID_Company in (select ID_Company from Company where Company_Status != '1' and Company_Status != 'true' ) ")
        dbWrite.execSQL("delete from Company where ID_Company in (select ID_Company from Company where Company_Status != '1' and Company_Status != 'true' ) ")

    }

    fun deleteCompanyDefaultIP() {
        val dbWrite = writableDatabase

        dbWrite.execSQL("delete from LoginUser where ID_Company in (select ID_Company from Company where IP_Default != '1' and IP_Default != 'true' ) ")
        dbWrite.execSQL("delete from ResellerDetails where ID_Company in (select ID_Company from Company where IP_Default != '1' and IP_Default != 'true' ) ")
        dbWrite.execSQL("delete from Company where ID_Company in (select ID_Company from Company where IP_Default != '1' and IP_Default != 'true' ) ")
    }

    fun deleteIPReseller() {
        val dbWrite = writableDatabase
        dbWrite.execSQL("DELETE FROM Company")
        dbWrite.execSQL("DELETE FROM ResellerDetails")
        dbWrite.execSQL("DELETE FROM LoginUser")
    }

    fun getLastInsertCompanyID() : String {

        var ID_Company = ""
        val dbRead = readableDatabase
        val cursor: Cursor = dbRead.rawQuery("select ID_Company from Company  order by ID_Company DESC Limit 1", null)
        if (cursor.count > 0) {
            if (cursor.moveToFirst()){
                ID_Company = cursor.getString(0)
            }
        }

        return ID_Company


    }



    fun ChekCompanyExist(Code: String?): Boolean {

        var result = false
        try {
            Log.e(TAG,"isExist  657771   "+Code)
            val dbRead = readableDatabase
            val cursor: Cursor = dbRead.rawQuery("select * from Company where Company_Code = '$Code'", null)
            if (cursor.count == 0){
                Log.e(TAG,"isExist  657772   "+cursor.count)
                result = false
            }else{
                Log.e(TAG,"isExist  657773   "+cursor.count)
                result = true
            }

        }catch (e: Exception){
            Log.e(TAG,"isExist  657774   "+e.toString())
        }

        return result
    }

    fun insertUpdateLoginUser(ID_Company: String, FK_Employee: String, UserName: String, Address: String, MobileNumber: String, Token: String,
        Email: String, UserCode: String, FK_Branch: String, FK_BranchType: String, FK_Company: String, FK_BranchCodeUser: String,
        FK_UserRole: String, UserRole: String, IsAdmin: String, IsManager: String, ID_User: String, BranchName: String,FK_Department: String,
        Department: String, CompanyCategory: String) {

        try {
            Log.e(TAG, "cursor 6744    "+ID_Company)
            val dbRead = readableDatabase
            val db = writableDatabase
            val cursor: Cursor = dbRead.rawQuery("select * from LoginUser WHERE ID_Company= '$ID_Company'", null)
            Log.e(TAG, "cursor 6744   " + cursor.count)
            if (cursor.count == 0) {

                val db = writableDatabase

                val values = ContentValues()
                values.put("ID_Company", ID_Company)
                values.put("FK_Employee", FK_Employee)
                values.put("UserName", UserName)
                values.put("Address", Address)
                values.put("MobileNumber", MobileNumber)
                values.put("Token", Token)
                values.put("UserCode", UserCode)

                values.put("UserRole", UserRole)
                values.put("FK_Branch", FK_Branch)
                values.put("BranchName", BranchName)
                values.put("FK_BranchType", FK_BranchType)
                values.put("FK_Company", FK_Company)
                values.put("FK_BranchCodeUser", FK_BranchCodeUser)
                values.put("FK_UserRole", FK_UserRole)
                values.put("IsAdmin", IsAdmin)
                values.put("IsManager", IsManager)
                values.put("ID_User", ID_User)
                values.put("FK_Department", FK_Department)
                values.put("Department", Department)
                values.put("CompanyCategory", CompanyCategory)
                values.put("userMpin", "")

                db.insert("LoginUser", null, values).toString()

            }else{

                val values = ContentValues()

                values.put("FK_Employee", FK_Employee)
                values.put("UserName", UserName)
                values.put("Address", Address)
                values.put("MobileNumber", MobileNumber)
                values.put("Token", Token)
                values.put("UserCode", UserCode)

                values.put("UserRole", UserRole)
                values.put("FK_Branch", FK_Branch)
                values.put("BranchName", BranchName)
                values.put("FK_BranchType", FK_BranchType)
                values.put("FK_Company", FK_Company)
                values.put("FK_BranchCodeUser", FK_BranchCodeUser)
                values.put("FK_UserRole", FK_UserRole)
                values.put("IsAdmin", IsAdmin)
                values.put("IsManager", IsManager)
                values.put("ID_User", ID_User)
                values.put("FK_Department", FK_Department)
                values.put("Department", Department)
                values.put("CompanyCategory", CompanyCategory)

                db.update("LoginUser", values, "ID_Company = ?", arrayOf(ID_Company))
            }
        }catch (e : Exception){

        }

    }

    fun updateUserMpin(ID_Company: String, Mpin: String, Mode: String) {
        try {
            val db = writableDatabase
            val dbRead = readableDatabase
            val values = ContentValues()
            values.put("userMpin", Mpin)
            if (Mode.equals("0")){
                db.update("LoginUser", values, "ID_Company = ?", arrayOf(ID_Company))
            }
            else if (Mode.equals("1")){
                var ID_Company1 = ""
                val cursor: Cursor = dbRead.rawQuery("select ID_Company from Company where IP_Default='1' or IP_Default='true'", null)
                if (cursor.count > 0) {
                    if (cursor.moveToFirst()){
                        ID_Company1 = cursor.getString(0)
                    }
                }
                db.update("LoginUser", values, "ID_Company = ?", arrayOf(ID_Company1))
            }

        }catch (e : Exception){

        }
    }


    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "prodsuite"

        // below is the variable for database version
        private val DATABASE_VERSION = 7

        // DATABASE_VERSION = 4 , table chat_user , add new colum 'userToken'
        // DATABASE_VERSION = 5 , create table company & ResellerDetails

    }
}
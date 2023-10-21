package com.perfect.prodsuit.Helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    var TAG = "DBHelper"

    override fun onCreate(db: SQLiteDatabase) {
        // TODO Auto-generated method stub
        db.execSQL("create table travel_location " + "(id integer primary key, date text,time text,battery text, address text)")
        db.execSQL("create table chat_all_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text)")
        db.execSQL("create table chat_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text,senderID text)")


        db.execSQL(
            "create table servicedetailmainlist " + "(id integer primary key, MasterProduct text, FK_Product text, Product text,Mode text," +
                    "BindProduct text,ComplaintProduct text,Warranty text,ServiceWarrantyExpireDate text,ReplacementWarrantyExpireDate text,ID_CustomerWiseProductDetails text,ServiceWarrantyExpired text,ReplacementWarrantyExpired text)"
        )

        db.execSQL(
            "create table servicedetailsublist " + "(id integer primary key,FK_Product_parent text,FK_Category text, MasterProduct text,FK_Product text, FK_Product_sub text, SLNo text, BindProduct text," +
                    "ComplaintProduct text,Warranty text,ServiceWarrantyExpireDate text,ReplacementWarrantyExpireDate text,ID_CustomerWiseProductDetails text,ServiceWarrantyExpired text,ReplacementWarrantyExpired text)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO Auto-generated method stub

        if (oldVersion < 2) {
            db.execSQL("create table chat_all_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text)")
            db.execSQL("create table chat_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text,senderID text)")
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
        senderID: String
    ) {
        try {
            val dbRead = this.readableDatabase
            val dbWrite = writableDatabase
            val cursor = dbRead.rawQuery(
                "SELECT * FROM " + "chat_user WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )",
                null
            )
            if (cursor.count == 0) {

                dbWrite.execSQL("INSERT INTO chat_user (name,BranchName,user_1,user_2,chatkey,senderID) VALUES ('$name','$BranchName','$user_sender','$user_receiver','$chatkey1','$senderID')")

            } else {
                if (!chatkey1.equals("")) {
                    dbWrite.execSQL("UPDATE chat_user SET chatkey = '$chatkey1' WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )")
                }
            }
        } catch (e: Exception) {
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

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "prodsuite"

        // below is the variable for database version
        private val DATABASE_VERSION = 3

    }
}
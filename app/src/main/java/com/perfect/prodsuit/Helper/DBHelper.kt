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

class DBHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    var TAG = "DBHelper"

    override fun onCreate(db: SQLiteDatabase) {
        // TODO Auto-generated method stub
        db.execSQL("create table travel_location " + "(id integer primary key, date text,time text,battery text, address text)")
        db.execSQL("create table chat_all_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text)")
        db.execSQL("create table chat_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text,senderID text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO Auto-generated method stub

        if (oldVersion < 2){
            db.execSQL("create table chat_all_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text)")
            db.execSQL("create table chat_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text,senderID text)")
        }
       // onCreate(db)
    }

    fun addFirebaseUser(name: String,BranchName: String,user_sender: String, user_receiver: String, chatkey1: String) {

        try {
            val dbRead = this.readableDatabase
            val dbWrite = writableDatabase
            val cursor = dbRead.rawQuery("SELECT * FROM " + "chat_all_user WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )", null)
            if (cursor.count == 0){

                dbWrite.execSQL("INSERT INTO chat_all_user (name,BranchName,user_1,user_2,chatkey) VALUES ('$name','$BranchName','$user_sender','$user_receiver','$chatkey1')")

            }
            else{
                if (!chatkey1.equals("")){
                    dbWrite.execSQL("UPDATE chat_all_user SET chatkey = '$chatkey1' WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )")
                }
            }
        }catch (e: Exception){
            Log.e(TAG,"49999  "+e.toString())
        }


    }

    fun addFirebaseChatUser(name: String,BranchName: String,user_sender: String, user_receiver: String, chatkey1: String,senderID : String) {
        try {
            val dbRead = this.readableDatabase
            val dbWrite = writableDatabase
            val cursor = dbRead.rawQuery("SELECT * FROM " + "chat_user WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )", null)
            if (cursor.count == 0){

                dbWrite.execSQL("INSERT INTO chat_user (name,BranchName,user_1,user_2,chatkey,senderID) VALUES ('$name','$BranchName','$user_sender','$user_receiver','$chatkey1','$senderID')")

            }
            else{
                if (!chatkey1.equals("")){
                    dbWrite.execSQL("UPDATE chat_user SET chatkey = '$chatkey1' WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )")
                }
            }
        }catch (e: Exception){
            Log.e(TAG,"49999  "+e.toString())
        }
    }

    fun deleteFirebaseUser() {
        try {
            val dbWrite = writableDatabase
            dbWrite.execSQL("DELETE FROM chat_all_user")
        }catch (e: Exception){
            Log.e(TAG,"5555  "+e.toString())
        }

    }

    @SuppressLint("Range")
    fun getChatKeyFromUser(user_sender: String, user_receiver: String): String {

        Log.e(TAG,"66661   "+user_sender+"  :  "+user_receiver)
        var chatKey =""
        val dbRead = this.readableDatabase
        val cursor = dbRead.rawQuery("SELECT * FROM " + "chat_all_user WHERE (user_1= '$user_sender' AND user_2= '$user_receiver' ) OR (user_1= '$user_receiver' AND user_2= '$user_sender' )", null)
        Log.e(TAG,"66662   "+cursor.count)
        if (cursor.count > 0){
            if (cursor.moveToFirst()) {
                do {
                    chatKey = cursor.getString(cursor.getColumnIndex("chatkey"))
                }while (cursor.moveToNext())
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
            Log.e(TAG,"  40000235  Success "+address+"   "+battery)
        }catch (e: Exception){
            Log.e(TAG,"Exception  40000236   "+e.toString())
        }

    }

    @SuppressLint("Range")
    fun getLocations() {
        // as we want to read value from it
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + "travel_location WHERE date = '01-07-2023' ", null)
     //   val cursor = db.rawQuery("SELECT * FROM " + "travel_location where address='NotificationService' ", null)
        Log.e(TAG,"  400002351  cursor "+cursor.count)
        cursor!!.moveToFirst()
        while(cursor.moveToNext()){
           Log.e(TAG,"5222   "+cursor.getString(cursor.getColumnIndex("date"))+"  :  "+cursor.getString(cursor.getColumnIndex("time")))
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
        }catch (e: Exception){
            Log.e(TAG,"5555  "+e.toString())
        }
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "prodsuite"
        // below is the variable for database version
        private val DATABASE_VERSION = 2

    }
}
package com.perfect.prodsuit.Helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    var TAG = "DBHelper"

    override fun onCreate(db: SQLiteDatabase) {
        // TODO Auto-generated method stub
        db.execSQL("create table travel_location " + "(id integer primary key, date text,time text,battery text, address text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO Auto-generated method stub

        onCreate(db)
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

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "prodsuite"
        // below is the variable for database version
        private val DATABASE_VERSION = 1

    }
}
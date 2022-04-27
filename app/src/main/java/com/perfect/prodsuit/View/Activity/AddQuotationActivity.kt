package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.BuildConfig
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AddQuotationActivity : AppCompatActivity() , View.OnClickListener {

    internal var llattachmnt: LinearLayout? = null
    internal var ll_imgv: LinearLayout? = null
    internal var btnReset: Button? = null
    internal var btnSubmit: Button? = null
    internal var imgv_attachment: ImageView? = null
    internal var yr: Int =0
    internal var month:Int = 0
    internal var day:Int = 0
    internal var hr:Int = 0
    internal var min:Int = 0
    private var mYear: Int =0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    private var mHour:Int = 0
    private var mMinute:Int = 0
    private var chipNavigationBar: ChipNavigationBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addquotation)
        setRegViews()

    }

    private fun setRegViews() {

        llattachmnt= findViewById<LinearLayout>(R.id.llattachmnt)
        ll_imgv= findViewById<LinearLayout>(R.id.ll_imgv)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnReset = findViewById<Button>(R.id.btnReset)
        imgv_attachment= findViewById<ImageView>(R.id.imgv_attachment)

    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.btnSubmit->{

            }
            R.id.btnReset->{

            }
        }
    }

}
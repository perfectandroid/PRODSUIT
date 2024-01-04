package com.perfect.prodsuit.Helper

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.AttendanceMarkingActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Common {

    fun punchingRedirectionConfirm(context : Context,type : String,message : String){

        val dialog = BottomSheetDialog(context)

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.confirm_punch_popup, null)

        val img_confirm = view.findViewById<ImageView>(R.id.img_confirm)
        val btnNo = view.findViewById<TextView>(R.id.btnNo)
        val btnYes = view.findViewById<TextView>(R.id.btnYes)
        val tv_text = view.findViewById<TextView>(R.id.tv_text)

        if (type.equals("")){
            tv_text.setText("You have not  marked attendance yet, please mark attendance first")
            img_confirm!!.setImageResource(R.drawable.attendance_mark)
        }

        btnNo.setOnClickListener {
            dialog.dismiss()

        }
        btnYes.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(context, AttendanceMarkingActivity::class.java)
            context.startActivity(intent)
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)


        dialog.show()
    }

    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    fun getCurrentDateNTime(mode : String): String {

        var result = ""

        val currentDate1 = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime1 = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(Date())

        if (mode.equals("1")){
            result = currentDate1
        }
        else if (mode.equals("2")){
            result = currentTime1
        }

        return result
    }


}
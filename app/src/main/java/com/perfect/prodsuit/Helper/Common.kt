package com.perfect.prodsuit.Helper

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.AttendanceMarkingActivity

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
}
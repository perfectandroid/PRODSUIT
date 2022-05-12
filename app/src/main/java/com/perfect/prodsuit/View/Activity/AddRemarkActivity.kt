package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.perfect.prodsuit.R
import java.lang.Long
import java.util.*

class AddRemarkActivity : AppCompatActivity() {

    val TAG : String = "AddRemarkActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_remark)
        context = this@AddRemarkActivity

        getCallDetails()

    }

    private fun getCallDetails() {

        try {
            val sb = StringBuffer()
            val contacts: Uri = CallLog.Calls.CONTENT_URI
            val managedCursor: Cursor? = context.contentResolver.query(contacts, null, null, null, CallLog.Calls.DATE + " DESC limit 1")
            val number: Int = managedCursor!!.getColumnIndex(CallLog.Calls.NUMBER)
            val type: Int = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
            val date: Int = managedCursor.getColumnIndex(CallLog.Calls.DATE)
            val duration: Int = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
            sb.append("Call Details :")
            while (managedCursor.moveToNext()) {
                val rowDataCall = HashMap<String, String>()
                val phNumber: String = managedCursor.getString(number)
                val callType: String = managedCursor.getString(type)
                val callDate: String = managedCursor.getString(date)
                val callDayTime: String = Date(Long.valueOf(callDate)).toString()
                // long timestamp = convertDateToTimestamp(callDayTime);
                val callDuration: String = managedCursor.getString(duration)
                var dir: String? = null
                val dircode = callType.toInt()
                when (dircode) {
                    CallLog.Calls.OUTGOING_TYPE -> dir = "OUTGOING"
                    CallLog.Calls.INCOMING_TYPE -> dir = "INCOMING"
                    CallLog.Calls.MISSED_TYPE -> dir = "MISSED"
                }
                sb.append("\nPhone Number:--- $phNumber \nCall Type:--- $dir \nCall Date:--- $callDayTime \nCall duration in sec :--- $callDuration")
                sb.append("\n----------------------------------")
            }
            managedCursor.close()

            Log.e(TAG,"CALL DETAILS  5061   "+sb)
        }catch (e : Exception){
            Log.e(TAG,"CALL DETAILS  5062   "+e.toString())
        }

    }


}
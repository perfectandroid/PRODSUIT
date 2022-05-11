package com.perfect.prodsuit.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.perfect.prodsuit.View.Activity.AgendaActivity

class PhoneStatReceiver : BroadcastReceiver() {

    private var TAG: String = "PhoneStatReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {

       try {

           if (intent!!.getAction().equals(AgendaActivity.CUSTOM_INTENT)) {

               Log.e(TAG,"if    25122222    "+intent!!.getAction()+"     "+AgendaActivity.CUSTOM_INTENT)
           }
           else{
               Log.e(TAG,"else  25122221   "+intent!!.getAction()+"     "+AgendaActivity.CUSTOM_INTENT)
           }
//           val bundle: Bundle? = intent!!.extras
//           val result: String? = bundle!!.getString("number")
//           Log.e(TAG,"onTabSelected  251  "+result)
           val state = intent!!.getStringExtra(TelephonyManager.EXTRA_STATE)
           if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
               Toast.makeText(context,""+TAG+"  171    "+"Ringing State Number is -",Toast.LENGTH_SHORT).show();
               Log.e(TAG,"onTabSelected  251  EXTRA_STATE_RINGING")
           }
           if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
               Toast.makeText(context,""+TAG+"  172    "+"Received State",Toast.LENGTH_SHORT).show();
               Log.e(TAG,"onTabSelected  251  EXTRA_STATE_OFFHOOK")
           }
           if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
               Toast.makeText(context,""+TAG+"  173    "+"Idle State",Toast.LENGTH_SHORT).show();
               Log.e(TAG,"onTabSelected  251  EXTRA_STATE_IDLE")
           }
           Log.e(TAG,"onTabSelected  251  ")

           Toast.makeText(context, "Ringing!", Toast.LENGTH_SHORT).show();
       }
       catch (e : Exception){
           Log.e(TAG,"onTabSelected  252  ")
           Toast.makeText(context,""+TAG+"      174    "+e.toString(),Toast.LENGTH_SHORT).show();
       }

    }
}
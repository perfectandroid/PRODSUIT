package com.perfect.prodsuit.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.View.Activity.AddRemarkActivity

class PhoneStatReceiver : BroadcastReceiver() {

    private var TAG: String = "PhoneStatReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {

       try {

//           Log.e("MyReceiver", "   251222   "+intent!!.action);
//           Log.e("MyReceiver", "   2512221   "+intent!!.getStringExtra("mobileno"));

//           val b = intent.extras
//           val message = b!!.getString("message")
//
//           Log.e("MyReceiver", "   25122233   "+message);




           val state = intent!!.getStringExtra(TelephonyManager.EXTRA_STATE)
           if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
             //  Log.e(TAG,"onTabSelected  251331  EXTRA_STATE_RINGING")
           }
           if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
             //  Log.e(TAG,"onTabSelected  251332  EXTRA_STATE_OFFHOOK")
           }
           if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
               Log.e(TAG,"onTabSelected  25122212  EXTRA_STATE_IDLE")

               val BroadCallSP = context!!.getSharedPreferences(Config.SHARED_PREF16, 0)
               Log.e("MyReceiver", "   2512221   "+BroadCallSP.getString("BroadCall",""));
               if (BroadCallSP.getString("BroadCall","").equals("Yes")){


                   var ID_LeadGenerate = BroadCallSP.getString("ID_LeadGenerate","")
                   var ID_LeadGenerateProduct = BroadCallSP.getString("ID_LeadGenerateProduct","")

//                   Log.e(TAG,"4871     "+ID_LeadGenerate)
//                   Log.e(TAG,"4872     "+ID_LeadGenerateProduct)

                   val BroadCallSP = context.getSharedPreferences(Config.SHARED_PREF16, 0)
                   val BroadCallEditer = BroadCallSP.edit()
                   BroadCallEditer.putString("BroadCall", "")
                   BroadCallEditer.putString("ID_LeadGenerate", "")
                   BroadCallEditer.putString("ID_LeadGenerateProduct", "")
                   BroadCallEditer.commit()

                   val intent = Intent(context, AddRemarkActivity::class.java)
                   intent.putExtra("ID_LeadGenerate",ID_LeadGenerate)
                   intent.putExtra("ID_LeadGenerateProduct",ID_LeadGenerateProduct)
                   intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                   context.startActivity(intent)
               }




           }
//           Log.e(TAG,"onTabSelected  251  ")
//
//           Toast.makeText(context, "Ringing!", Toast.LENGTH_SHORT).show();
       }
       catch (e : Exception){
           Log.e(TAG,"onTabSelected  252  ")
           Toast.makeText(context,""+TAG+"      174    "+e.toString(),Toast.LENGTH_SHORT).show();
       }

    }
}
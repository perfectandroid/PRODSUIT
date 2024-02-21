package com.perfect.prodsuit.Helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.perfect.prodsuit.R

class NetworkChangeReceiver : BroadcastReceiver(){



    var dialog: BottomSheetDialog? = null
    override fun onReceive(context: Context?, intent: Intent?) {


        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnected) {

            Log.e("rrrrr","33wwe     1")
//            Toast.makeText(context,"Offline",Toast.LENGTH_LONG).show()
            ConnectionfailedBottomSheet(context)
        } else {


            if (dialog != null && dialog!!.isShowing()) {
                dialog!!.dismiss()
            }
//            dialog!!.dismiss()
            Log.e("rrrrr","33wwe     2")
//            Toast.makeText(context,"online",Toast.LENGTH_LONG).show()

        }
    }


    private fun ConnectionfailedBottomSheet(context: Context) {

        try {
            Log.e("ee","checknetworkwwwwww ")
            dialog = BottomSheetDialog(context)
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.network_failed_popup, null)

            dialog!!.setCancelable(false)
            dialog!!.setContentView(layout)
            dialog!!.show()


        }catch (e: Exception){
            Log.e("ee","exeption "+e)
            Log.e("ee","exeption "+e)
        }

    }
}
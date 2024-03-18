package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication_demmo.MapData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.RootViewActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat

class TravelDistanceAdapter(internal var context: Context, internal var jsonArray: JSONArray) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    internal val TAG: String = "AccountDetailAdapter"
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_travel_distance, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            val gsonString: String = ""
            val jsonObject = jsonArray.getJSONObject(position)
            Log.e(TAG, "jsonObject   354541051   "+jsonObject!!.getString("JSonData"))
            if (holder is MainViewHolder) {
//                var url =
//                    "https://maps.googleapis.com/maps/api/directions/json?origin=11.2475582,75.83422&destination=11.2475389,75.8342483&waypoints=11.2475582,75.83422|11.7074893,75.7330154|11.2475617,75.8342334|11.7069384,75.7327853|11.2475389,75.8342483&key=AIzaSyBpV-dAr8kv728_st35n8XMmt9L3qrqwhc"
//                Log.v("sdfsdfdsd", "url  " + url)
                holder.txtName.text = jsonObject!!.getString("EmployeeName")
                Log.v("dsfsdfsfsdf", "EmployeeName ID " + jsonObject!!.getString("EmployeeName"))
                val DistanceTravelled=loadDirection( jsonObject!!.getString("JSonData"))

                Log.v("dsfsdfsfsdf", "DistanceTravelled" + DistanceTravelled)
                try {
                    var km = DistanceTravelled / 1000
                    val pattern = "###,###.##"
                    val decimalFormat = DecimalFormat(pattern)
                    val formattedNumber = decimalFormat.format(km)
                    holder.txt_travelled.setText("" + DistanceTravelled + " m / " + formattedNumber + " km")
                } catch (e: java.lang.Exception) {
                    holder.txt_travelled.setText("NA")
                }

//                GetDirection(
//                    jsonObject!!.getString("JSonData"),
//                    holder.txt_travelled,
//                    holder.txt_response
//                ).execute()
//                Log.e(TAG, "onBindViewHolder   1051   ")
//                val pos = position + 1


                holder.llLocationMarking.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    val i = Intent(context, RootViewActivity::class.java)
                    i.putExtra("item_response", jsonObject!!.getString("JSonData"))
                    i.putExtra("StartingPoint", jsonObject!!.getString("StartingPoint"))
                    i.putExtra("EndingPoint", jsonObject!!.getString("EndingPoint"))
                    context.startActivity(i)
                })

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   105   " + e.toString())
        }
    }

    private fun loadDirection(jsondata: String):Double {
        val data = jsondata
        var DistanceTravelled = 0.0
        try {
            val respObj = Gson().fromJson(data, MapData::class.java)
            //responseObjectData=respObj
            Log.v("frtytrytry", "size " + respObj.routes[0].legs.size)
            val path = ArrayList<LatLng>()

            for (i in 0 until respObj.routes[0].legs.size) {
                var Dist = 0
                Log.v("frtytrytry", "i " + i)
                Log.v("frtytrytry", "valeu " + respObj.routes[0].legs[i].distance.value)
                try {
                    var Dist = respObj.routes[0].legs[i].distance.value
                    DistanceTravelled = DistanceTravelled + Dist
                } catch (e: Exception) {
                    Dist = 0
                    DistanceTravelled = DistanceTravelled + Dist
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.v("frtytrytry", "e " + e)
        }

        return DistanceTravelled

    }


//    @SuppressLint("StaticFieldLeak")
//    private inner class GetDirection(
//        val jsondata: String,
//        private val txt_travelled: TextView,
//        private var txt_response: TextView
//
//    ) : AsyncTask<Void, Void, String>() {
//        override fun doInBackground(vararg params: Void?): String? {
//            Log.v("frtytrytry", "GetDirection")
////            val client = OkHttpClient()
////            val request = Request.Builder().url(url).build()
////            val response = client.newCall(request).execute()
//            val data = jsondata
//            txt_response.setText(data)
//            var DistanceTravelled = 0.0
//            try {
//                val respObj = Gson().fromJson(data, MapData::class.java)
//                //responseObjectData=respObj
//                Log.v("frtytrytry", "size " + respObj.routes[0].legs.size)
//                val path = ArrayList<LatLng>()
//
//                for (i in 0 until respObj.routes[0].legs.size) {
//                    var Dist = 0
//                    Log.v("frtytrytry", "i " + i)
//                    Log.v("frtytrytry", "valeu " + respObj.routes[0].legs[i].distance.value)
//                    try {
//                        var Dist = respObj.routes[0].legs[i].distance.value
//                        DistanceTravelled = DistanceTravelled + Dist
//                    } catch (e: Exception) {
//                        Dist = 0
//                        DistanceTravelled = DistanceTravelled + Dist
//                    }
//
//                }
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.v("frtytrytry", "e " + e)
//            }
//            try {
//                var km = DistanceTravelled / 1000
//                val pattern = "###,###.#"
//                val decimalFormat = DecimalFormat(pattern)
//                val formattedNumber = decimalFormat.format(km)
//                txt_travelled.setText("" + DistanceTravelled + " m / " + formattedNumber + " km")
//            } catch (e: java.lang.Exception) {
//                txt_travelled.setText("NA")
//            }
//            return data
//        }
//
//        override fun onPostExecute(result: String?) {
//            Log.v("frtytrytry", "onPostExecute")
//            val lineoption = PolylineOptions()
//
////            callback.onTaskCompleted(formattedNumber)
////
//        }
//    }

    override fun getItemCount(): Int {
        return jsonArray.length()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }



    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtName: TextView
        internal var txt_travelled: TextView
//        internal var txt_response: TextView
        internal var llLocationMarking: LinearLayout

        //        internal var txtDate            : TextView
        init {
            txtName = v.findViewById<View>(R.id.txtName) as TextView
            txt_travelled = v.findViewById<View>(R.id.txt_travelled) as TextView
//            txt_response = v.findViewById<View>(R.id.til_Channel) as TextView
            llLocationMarking = v.findViewById<View>(R.id.llLocationMarking) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}
package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class AuthorizationMixedAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "AuthorizationMixedAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_auth_mixed, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")

                holder.tv_heading.text        = jsonObject!!.getString("Action")
                holder.tv_TransactionNo.text        = jsonObject!!.getString("TransactionNo")
                holder.tv_Date.text        = jsonObject!!.getString("Date")
                holder.tv_EnteredBy.text        = jsonObject!!.getString("EnteredBy")
                holder.tv_EnteredOn.text        = jsonObject!!.getString("EnteredOn")

//                holder.tv_values.text        = jsonObject!!.getString("Module_Name")
//                if (jsonObject!!.getString("Mode").equals("1")){
//                    holder.tv_lead_ticket.text        = jsonObject!!.getString("LeadNo")
//                    holder.tv_lead_nextdate.text        = jsonObject!!.getString("NextActionDate")
//                    if(jsonObject!!.getString("Preference").equals("Hot")){
//                        holder.img_lead_pref.setImageResource(R.drawable.preference2)
//                        holder.img_lead_pref.setColorFilter(context.getColor(R.color.ColorHot))
//                    }else if (jsonObject!!.getString("Preference").equals("Warm")){
//                        holder.img_lead_pref.setImageResource(R.drawable.preference3)
//                        holder.img_lead_pref.setColorFilter(context.getColor(R.color.ColorWarm))
//
//                    }else if (jsonObject!!.getString("Preference").equals("Cold")){
//                        holder.img_lead_pref.setImageResource(R.drawable.preference1)
//                        holder.img_lead_pref.setColorFilter(context.getColor(R.color.ColorCold))
//
//                    }
//                }

//
//
//                holder.rltv_main!!.setTag(position)
//                holder.rltv_main!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "approveClick")
//                })

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }
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
//        internal var lToDoList   : LinearLayout
        internal var tv_heading   : TextView
        internal var tv_TransactionNo   : TextView
        internal var tv_Date   : TextView
        internal var tv_EnteredBy   : TextView
        internal var tv_EnteredOn   : TextView
//        internal var img_lead_pref   : ImageView

        init {
//            lToDoList          = v.findViewById<View>(R.id.lToDoList) as LinearLayout
            tv_heading          = v.findViewById<View>(R.id.tv_heading) as TextView
            tv_TransactionNo          = v.findViewById<View>(R.id.tv_TransactionNo) as TextView
            tv_Date          = v.findViewById<View>(R.id.tv_Date) as TextView
            tv_EnteredBy          = v.findViewById<View>(R.id.tv_EnteredBy) as TextView
            tv_EnteredOn          = v.findViewById<View>(R.id.tv_EnteredOn) as TextView
//            img_lead_pref          = v.findViewById<View>(R.id.img_lead_pref) as ImageView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}
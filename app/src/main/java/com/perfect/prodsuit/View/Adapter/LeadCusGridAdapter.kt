package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class LeadCusGridAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "LeadCusGridAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.lead_cus_grid, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051ghgh   ")
                val pos = position+1


                holder.tvv_slnmbr.text        = pos.toString()
                holder.lead_txtno.text        = jsonObject!!.getString("LeadNo")
                holder.txt_cusname.text        = jsonObject!!.getString("CustomerName")
            //    holder.txt_cusMobile.text        = "("+jsonObject!!.getString("Mobile") +")"
//                holder.llProduct!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(
//                        position,
//                        "cus_"
//                    )
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
        internal var lead_txtno   : TextView
        internal var tvv_slnmbr         : TextView
        internal var txt_cusname         : TextView
    //    internal var txt_cusMobile         : TextView


     //   internal var llProduct    : LinearLayout
        init {
            lead_txtno          = v.findViewById<View>(R.id.lead_txtno) as TextView
            tvv_slnmbr                = v.findViewById<View>(R.id.tvv_slnmbr) as TextView
            txt_cusname                = v.findViewById<View>(R.id.txt_cusname) as TextView
//            txt_cusMobile                = v.findViewById<View>(R.id.txt_cusMobile) as TextView

//            llProduct           = v.findViewById<View>(R.id.llProduct) as LinearLayout

        }
    }
    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}
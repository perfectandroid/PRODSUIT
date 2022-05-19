package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ProductWiseReportAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProductWiseReportAdapter"
    internal var jsonObject: JSONObject? = null
//    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_productwise_report, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            Log.e(TAG,"Exception   49731   ")
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1
                holder.tv_SiNo.text                  = pos.toString()
                holder.tv_ProductName.text           = jsonObject!!.getString("ProductName")
                holder.tv_LeadNo.text                = jsonObject!!.getString("LeadNo")
                holder.tv_LeadBY.text                = jsonObject!!.getString("LeadBY")
                holder.tv_NextActionDate.text        = jsonObject!!.getString("NextActionDate")

//                if (position == row_index){
//                    holder.llaccontdetail!!.setBackgroundResource(R.drawable.shape_selected)
//                }else{
//                    holder.llaccontdetail!!.setBackgroundResource(R.drawable.shape_default)
//                }
//                holder.llaccontdetail!!.setTag(position)
//                holder.llaccontdetail!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, jsonObject!!.getString("name"))
//                    row_index=position;
//                    notifyDataSetChanged()
//                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   4973   "+e.toString())
        }
    }

    override fun getItemCount(): Int {
        return jsonArray.length()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var tv_SiNo               : TextView
//        internal var tv_ProjectName        : TextView
        internal var tv_ProductName        : TextView
        internal var tv_LeadBY             : TextView
        internal var tv_LeadNo             : TextView
        internal var tv_NextActionDate     : TextView
//        internal var tv_ActionModuleName   : TextView
//        internal var tv_ActionStatusname   : TextView
        init {

            tv_SiNo                      = v.findViewById<View>(R.id.tv_SiNo) as TextView
            tv_ProductName               = v.findViewById<View>(R.id.tv_ProductName) as TextView
            tv_LeadBY                    = v.findViewById<View>(R.id.tv_LeadBY) as TextView
            tv_LeadNo                    = v.findViewById<View>(R.id.tv_LeadNo) as TextView
            tv_NextActionDate            = v.findViewById<View>(R.id.tv_NextActionDate) as TextView

        }
    }



}
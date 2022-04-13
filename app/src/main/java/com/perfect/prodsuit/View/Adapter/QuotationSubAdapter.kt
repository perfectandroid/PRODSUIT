package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class QuotationSubAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "infoSubAdapter"
    internal var jsonObject: JSONObject? = null
    // private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_sub_quotation, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1
                holder.txtProduct.text     = jsonObject!!.getString("Product")
                holder.txtEnquiry.text     = jsonObject!!.getString("EnquiryAbout")
                holder.txtStatus.text      = jsonObject!!.getString("Status")

                if (position % 2 == 0){
                    holder.llSubQuotation!!.setBackgroundColor(context.getColor(R.color.greylight))
                }
                else{
                    holder.llSubQuotation!!.setBackgroundColor(context.getColor(R.color.white))
                }
//
//                holder.llfollowuptype!!.setTag(position)
//                holder.llfollowuptype!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "followuptype")
//
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
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var txtProduct      : TextView
        internal var txtEnquiry      : TextView
        internal var txtStatus       : TextView
        internal var llSubQuotation  : LinearLayout

        init {
            txtProduct        = v.findViewById<View>(R.id.txtProduct) as TextView
            txtEnquiry        = v.findViewById<View>(R.id.txtEnquiry) as TextView
            txtStatus         = v.findViewById<View>(R.id.txtStatus) as TextView
            llSubQuotation    = v.findViewById<View>(R.id.llSubQuotation) as LinearLayout
        }
    }

//    fun setClickListener(itemClickListener: ItemClickListener?) {
//        clickListener = itemClickListener
//    }

}
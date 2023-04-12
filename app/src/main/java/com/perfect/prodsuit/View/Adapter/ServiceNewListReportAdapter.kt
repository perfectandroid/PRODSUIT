package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ServiceNewListReportAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceNewListReportAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_newlist_report, parent, false
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

                holder.txtTicketNo.text      = jsonObject!!.getString("TicketNo")
                holder.txtTicketDate.text      = jsonObject!!.getString("TicketDate")
                holder.txtCustomer.text      = jsonObject!!.getString("Customer")
                holder.txtProduct.text      = jsonObject!!.getString("Product")
                holder.txtComplaint.text      = jsonObject!!.getString("Complaint")
                holder.txtCurrentStatus.text      = jsonObject!!.getString("CurrentStatus")

//                holder.ll_newList!!.setTag(position)
//                holder.ll_newList!!.setOnClickListener(View.OnClickListener {
//                    Log.e(TAG,"newListClick   5091")
//                    clickListener!!.onClick(position, "newListClick")
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
        internal var txtTicketNo        : TextView
        internal var txtTicketDate      : TextView
        internal var txtCustomer        : TextView
        internal var txtProduct         : TextView
        internal var txtComplaint       : TextView
        internal var txtCurrentStatus   : TextView

      //  internal var ll_newList          : LinearLayout
        init {

            txtTicketNo        = v.findViewById<View>(R.id.txtTicketNo) as TextView
            txtTicketDate      = v.findViewById<View>(R.id.txtTicketDate) as TextView
            txtCustomer        = v.findViewById<View>(R.id.txtCustomer) as TextView
            txtProduct         = v.findViewById<View>(R.id.txtProduct) as TextView
            txtComplaint       = v.findViewById<View>(R.id.txtComplaint) as TextView
            txtCurrentStatus   = v.findViewById<View>(R.id.txtCurrentStatus) as TextView

          //  ll_newList         = v.findViewById<View>(R.id.ll_newList) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}
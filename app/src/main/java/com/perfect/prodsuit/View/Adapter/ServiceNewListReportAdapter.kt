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

class ServiceNewListReportAdapter (internal var context: Context, internal var jsonArray: JSONArray,internal var ReportMode: String):
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
//
//
//                holder.txtCurrentStatus.text      = jsonObject!!.getString("CurrentStatus")

                holder.ll_newList!!.setBackgroundColor(context.getColor(R.color.greylight))

                if (ReportMode.equals("1")){
                 //   holder.ll_TicketNo!!.visibility = View.GONE
                    holder.ll_Product!!.visibility = View.VISIBLE
                    holder.ll_Complaint!!.visibility = View.VISIBLE
                    holder.txtProduct.text      = jsonObject!!.getString("Product")
                    holder.txtComplaint.text      = jsonObject!!.getString("Complaint")
                }
//
                if (ReportMode.equals("3")){

                    holder.ll_Product!!.visibility = View.VISIBLE
                    holder.ll_Complaint!!.visibility = View.VISIBLE
                 //   holder.ll_Mobile!!.visibility = View.VISIBLE

                    holder.txtProduct.text      = jsonObject!!.getString("Product")
                    holder.txtComplaint.text      = jsonObject!!.getString("Complaint")
                  //  holder.txtMobile.text      = jsonObject!!.getString("Mobile")

                }

                if (ReportMode.equals("6")){
                   // holder.ll_CurrentStatus!!.visibility = View.GONE

                    holder.ll_Service!!.visibility = View.VISIBLE
                    holder.txtService.text      = jsonObject!!.getString("Services")
                }

                holder.ll_newList!!.setTag(position)
                holder.ll_newList!!.setOnClickListener(View.OnClickListener {
                    Log.e(TAG,"serviceReportClick   5091")
                    clickListener!!.onClick(
                        position,
                        "serviceReportClick"
                    )
                })



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
        internal var txtService        : TextView
        internal var txtProduct         : TextView
        internal var txtComplaint       : TextView
        internal var txtMobile       : TextView
        internal var txtCurrentStatus   : TextView

        internal var ll_newList          : LinearLayout

        internal var ll_TicketNo         : LinearLayout
        internal var ll_TicketDate       : LinearLayout
        internal var ll_Customer         : LinearLayout
        internal var ll_Service         : LinearLayout
        internal var ll_Product          : LinearLayout
        internal var ll_Complaint        : LinearLayout
        internal var ll_Mobile        : LinearLayout
        internal var ll_CurrentStatus    : LinearLayout

        init {

            txtTicketNo        = v.findViewById<View>(R.id.txtTicketNo) as TextView
            txtTicketDate      = v.findViewById<View>(R.id.txtTicketDate) as TextView
            txtCustomer        = v.findViewById<View>(R.id.txtCustomer) as TextView
            txtService         = v.findViewById<View>(R.id.txtService) as TextView
            txtProduct         = v.findViewById<View>(R.id.txtProduct) as TextView
            txtComplaint       = v.findViewById<View>(R.id.txtComplaint) as TextView
            txtMobile          = v.findViewById<View>(R.id.txtMobile) as TextView
            txtCurrentStatus   = v.findViewById<View>(R.id.txtCurrentStatus) as TextView

            ll_TicketNo        = v.findViewById<View>(R.id.ll_TicketNo) as LinearLayout
            ll_TicketDate      = v.findViewById<View>(R.id.ll_TicketDate) as LinearLayout
            ll_Customer        = v.findViewById<View>(R.id.ll_Customer) as LinearLayout
            ll_Service        = v.findViewById<View>(R.id.ll_Service) as LinearLayout
            ll_Product         = v.findViewById<View>(R.id.ll_Product) as LinearLayout
            ll_Complaint       = v.findViewById<View>(R.id.ll_Complaint) as LinearLayout
            ll_Mobile       = v.findViewById<View>(R.id.ll_Mobile) as LinearLayout
            ll_CurrentStatus   = v.findViewById<View>(R.id.ll_CurrentStatus) as LinearLayout

            ll_newList         = v.findViewById<View>(R.id.ll_newList) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}
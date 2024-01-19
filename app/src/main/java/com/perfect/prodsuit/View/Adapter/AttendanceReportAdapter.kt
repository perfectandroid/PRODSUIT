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
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class AttendanceReportAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "AttendanceReportAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_attendance_report, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {

                Log.e(TAG,"onBindViewHolder   attendance   "+jsonArray.getJSONObject(position).toString())
                val pos = position+1


                    /*holder.txtv_slno.text = ""
                    holder.txtv_empid.text = jsonObject!!.getString("EmployeeName")
                    holder.txtv_punchintime.text = "Lead No : "+jsonObject!!.getString("EnteredTime")

                    holder.txtv_punchinlocation.text = "Category : "+jsonObject!!.getString("LocationName")*/

              /*  holder.ll_projectList!!.setTag(position)
                holder.ll_projectList!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "ProjectListReportClick"
                    )
                })
*/

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   attendance   "+e.toString())
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
        internal var txtv_slno        : TextView
        internal var txtv_empid      : TextView
        internal var txtv_punchintime        : TextView
        internal var txtv_punchinlocation        : TextView


/*

        internal var ll_TicketNo         : LinearLayout
        internal var ll_TicketDate       : LinearLayout
        internal var ll_Customer         : LinearLayout
        internal var ll_Service         : LinearLayout
        internal var ll_Product          : LinearLayout
        internal var ll_Complaint        : LinearLayout
        internal var ll_Mobile        : LinearLayout
        internal var ll_CurrentStatus    : LinearLayout
*/

        init {

            txtv_slno        = v.findViewById<View>(R.id.txtv_slno) as TextView
            txtv_empid      = v.findViewById<View>(R.id.txtv_empid) as TextView
            txtv_punchintime        = v.findViewById<View>(R.id.txtv_punchintime) as TextView
            txtv_punchinlocation         = v.findViewById<View>(R.id.txtv_punchinlocation) as TextView
        //    tv_Note1         = v.findViewById<View>(R.id.tv_Note1) as TextView
         //   tv_Note2       = v.findViewById<View>(R.id.tv_Note2) as TextView

           /* ll_TicketDate      = v.findViewById<View>(R.id.ll_TicketDate) as LinearLayout
            ll_Customer        = v.findViewById<View>(R.id.ll_Customer) as LinearLayout
            ll_Service        = v.findViewById<View>(R.id.ll_Service) as LinearLayout
            ll_Product         = v.findViewById<View>(R.id.ll_Product) as LinearLayout
            ll_Complaint       = v.findViewById<View>(R.id.ll_Complaint) as LinearLayout
            ll_Mobile       = v.findViewById<View>(R.id.ll_Mobile) as LinearLayout
            ll_CurrentStatus   = v.findViewById<View>(R.id.ll_CurrentStatus) as LinearLayout
            ll_newList         = v.findViewById<View>(R.id.ll_newList) as LinearLayout*/

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}
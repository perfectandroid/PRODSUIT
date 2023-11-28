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

class ProjectStatusListReportAdapter (internal var context: Context, internal var jsonArray: JSONArray, internal var ReportMode: String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceNewListReportAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_project_statuslist_report, parent, false
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

                if (ReportMode.equals("1")) {

                    holder.tv_SiteVisitID.text = jsonObject!!.getString("Status")
                    holder.tv_leadno.text = jsonObject!!.getString("ProjectName")
                    holder.tv_VisitDate.text = "Lead No : "+jsonObject!!.getString("LeadNumber")

                    holder.tv_VisitTime.text = "Category : "+jsonObject!!.getString("Category")
                    holder.tv_Note1.text = "Start Date "+jsonObject!!.getString("StartDate")
                    holder.tv_Note2.text = "Due Date : "+jsonObject!!.getString("DueDate")
                    holder.tv_CusNote.text = "Duration  : "+jsonObject!!.getString("Duration")
                    holder.tv_ExpenseAmount.text = "Final Amount : "+ Config.changeTwoDecimel(jsonObject!!.getString("FinalAmount_R"))
                   // holder.tv_Remarks.text = "Remark : "+jsonObject!!.getString("Remarks")



                  // "hdn_EntrOn": "16-11-2023 15:00:30",



                }

         /*       holder.ll_newList!!.setTag(position)
                holder.ll_newList!!.setOnClickListener(View.OnClickListener {
                    Log.e(TAG,"serviceReportClick   5091")
                    clickListener!!.onClick(
                        position,
                        "serviceReportClick"
                    )
                })
*/


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
        internal var tv_SiteVisitID        : TextView
        internal var tv_leadno      : TextView
        internal var tv_VisitDate        : TextView
        internal var tv_VisitTime        : TextView
        internal var tv_Note1         : TextView
        internal var tv_Note2       : TextView
        internal var tv_CusNote       : TextView
        internal var tv_ExpenseAmount   : TextView
        internal var tv_Remarks   : TextView


/*
        internal var ll_newList          : LinearLayout

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

            tv_SiteVisitID        = v.findViewById<View>(R.id.tv_SiteVisitID) as TextView
            tv_leadno      = v.findViewById<View>(R.id.tv_leadno) as TextView
            tv_VisitDate        = v.findViewById<View>(R.id.tv_VisitDate) as TextView
            tv_VisitTime         = v.findViewById<View>(R.id.tv_VisitTime) as TextView
            tv_Note1         = v.findViewById<View>(R.id.tv_Note1) as TextView
            tv_Note2       = v.findViewById<View>(R.id.tv_Note2) as TextView
            tv_CusNote          = v.findViewById<View>(R.id.tv_CusNote) as TextView
            tv_ExpenseAmount   = v.findViewById<View>(R.id.tv_ExpenseAmount) as TextView
            tv_Remarks   = v.findViewById<View>(R.id.tv_Remarks) as TextView
/*
            ll_TicketNo        = v.findViewById<View>(R.id.ll_TicketNo) as LinearLayout
            ll_TicketDate      = v.findViewById<View>(R.id.ll_TicketDate) as LinearLayout
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
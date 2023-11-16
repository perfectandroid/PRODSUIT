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
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ReportNameAdapter(internal var context: Context, internal var jsonArray: JSONArray,internal var report: String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ReportNameAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    var pos = 0
//    private var mItemClickListener: ItemClickListenerData? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_report_name, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")

                Log.i("responsereport","name="+jsonObject!!.getString("ReportName").toString())
                Log.i("responsereport","mode="+jsonObject!!.getString("ReportMode").toString())
                if (!jsonObject!!.getString("ReportMode").toString().equals("2") && !jsonObject!!.getString("ReportMode").toString().equals("5") && report.equals("Lead")){
                    holder.llReportName!!.visibility = View.GONE
                    Log.e(TAG,"onBindViewHolder   10512   ")
                }
                else if (!jsonObject!!.getString("ReportMode").toString().equals("1") && !jsonObject!!.getString("ReportMode").toString().equals("3") && !jsonObject!!.getString("ReportMode").toString().equals("6") && report.equals("Service")){
                    holder.llReportName!!.visibility = View.GONE
                    Log.e(TAG,"onBindViewHolder   105123   "+jsonObject!!.getString("ReportName"))
                }
                else if (report.equals("Project")){
                   // holder.llReportName!!.visibility = View.GONE
                    pos++
                    holder.txtsino.text        = pos.toString()
                    holder.txtReportName.text        = jsonObject!!.getString("ReportName")
                }
                else{
                    pos++
                    holder.txtsino.text        = pos.toString()
                    holder.txtReportName.text        = jsonObject!!.getString("ReportName")
                }





//                if (position % 2 == 0){
//                    holder.llbranch!!.setBackgroundColor(context.getColor(R.color.greylight))
//                }
//                else{
//                    holder.llbranch!!.setBackgroundColor(context.getColor(R.color.white))
//                }
                holder.llReportName!!.setTag(position)
                holder.llReportName!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "reportname"
                    )
                })
//                holder.llReportName!!.setOnClickListener(View.OnClickListener {
//                 //   clickListener!!.onClick(position, "reportname")
//
//                    if (mItemClickListener != null) {
//                        mItemClickListener!!.onClick(position, "report", jsonObject!!);
//                    }
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
        internal var txtReportName   : TextView
        internal var txtsino         : TextView
        internal var llReportName    : LinearLayout
        init {
            txtReportName      = v.findViewById<View>(R.id.txtReportName) as TextView
            txtsino            = v.findViewById<View>(R.id.txtsino) as TextView
            llReportName       = v.findViewById<View>(R.id.llReportName) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

//    fun addItemClickListener(listener: ItemClickListenerData) {
//        mItemClickListener = listener
//    }

}
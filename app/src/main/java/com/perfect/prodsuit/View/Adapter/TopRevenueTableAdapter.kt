package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class TopRevenueTableAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ExpenseAnalysisListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_expense_analysis_list, parent, false
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
                holder.tvv_slnmbr.text        = pos.toString()
                holder.tvv_Project1.text      = jsonObject!!.getString("MediaName")
                holder.tvv_ProjectAmount.text = jsonObject!!.getString("MediaAmount").toFloat().toString()
                holder.tvv_Expense.text       = jsonObject!!.getString("LeadAmount").toFloat().toString()


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
        internal var tvv_Project1           : TextView
        internal var tvv_slnmbr             : TextView
        internal var tvv_ProjectAmount      : TextView
        internal var tvv_Expense            : TextView
        init {
            tvv_Project1           = v.findViewById<View>(R.id.tvv_Project1)      as TextView
            tvv_slnmbr             = v.findViewById<View>(R.id.tvv_slnmbr)        as TextView
            tvv_ProjectAmount      = v.findViewById<View>(R.id.tvv_ProjectAmount) as TextView
            tvv_Expense            = v.findViewById<View>(R.id.tvv_Expense)       as TextView
        }
    }

//    fun setClickListener(itemClickListener: ItemClickListener?) {
//        clickListener = itemClickListener
//    }
}
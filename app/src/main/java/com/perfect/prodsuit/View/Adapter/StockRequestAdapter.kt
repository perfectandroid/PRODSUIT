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

class StockRequestAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "StockRequestAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_stock_request, parent, false
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
             //   holder.txtsino.text        = pos.toString()
                holder.tv_branch.text        = jsonObject!!.getString("BranchName")
                holder.tv_date.text        = jsonObject!!.getString("TransDate")
                holder.tv_department.text        = jsonObject!!.getString("DepartmentName")
                holder.tv_employee.text        = jsonObject!!.getString("EmployeeName")
                holder.tv_employeeTo.text        = jsonObject!!.getString("EmployeeName")

                holder.llstockrequest!!.setTag(position)
                holder.llstockrequest!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "stockrequest")
                })


//                holder.tv_view!!.setTag(position)
//                holder.tv_view!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "stockrequest")
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
    //    internal var txtsino         : TextView
        internal var tv_branch   : TextView
        internal var tv_date   : TextView
        internal var tv_department   : TextView
        internal var tv_employee   : TextView
        internal var tv_employeeTo   : TextView
        internal var tv_view   : TextView
        internal var llstockrequest    : LinearLayout
        init {
         //   txtsino            = v.findViewById<View>(R.id.txtsino) as TextView
            tv_branch          = v.findViewById<View>(R.id.tv_branch) as TextView
            tv_date          = v.findViewById<View>(R.id.tv_date) as TextView
            tv_department          = v.findViewById<View>(R.id.tv_department) as TextView
            tv_employee          = v.findViewById<View>(R.id.tv_employee) as TextView
            tv_employeeTo          = v.findViewById<View>(R.id.tv_employee) as TextView
            tv_view          = v.findViewById<View>(R.id.tv_view) as TextView
            llstockrequest           = v.findViewById<View>(R.id.llstockrequest) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}
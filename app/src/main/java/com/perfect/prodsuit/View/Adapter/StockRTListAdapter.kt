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

class StockRTListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "StockRTListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_stock_rtlist, parent, false
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
               // holder.txtsino.text        = pos.toString()
                holder.tv_date.text             = jsonObject!!.getString("Date")
                holder.tv_branch.text           = jsonObject!!.getString("Branch")
                holder.tv_department_from.text  = jsonObject!!.getString("DepartmentFrom")
                holder.tv_department_to.text    = jsonObject!!.getString("DepartmentTo")
                holder.tv_employee_from.text    = jsonObject!!.getString("EmployeesFrom")


                holder.ll_stockrt!!.setTag(position)
                holder.ll_stockrt!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "stockrtclicks")
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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tv_date            : TextView
        internal var tv_branch          : TextView
        internal var tv_department_from : TextView
        internal var tv_department_to   : TextView
        internal var tv_employee_from   : TextView
        internal var ll_stockrt     : LinearLayout
        init {
            tv_date                 = v.findViewById<View>(R.id.tv_date) as TextView
            tv_branch               = v.findViewById<View>(R.id.tv_branch) as TextView
            tv_department_from      = v.findViewById<View>(R.id.tv_department_from) as TextView
            tv_department_to        = v.findViewById<View>(R.id.tv_department_to) as TextView
            tv_employee_from        = v.findViewById<View>(R.id.tv_employee_from) as TextView
            ll_stockrt              = v.findViewById<View>(R.id.ll_stockrt) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}
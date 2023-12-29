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

class ProjectWiseEmployeeAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProjectWiseEmployeeAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_projectwise_employee, parent, false
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
                holder.txtsino.text        = pos.toString()
                holder.txtEmployee.text        = jsonObject!!.getString("EmployeeName")
                holder.txtDepartment.text        = jsonObject!!.getString("Department")
                holder.txtDesignation.text        = jsonObject!!.getString("Designation")

                holder.llemployee!!.setTag(position)
                holder.llemployee!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "ProjectEmployeeClick"
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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtEmployee   : TextView
        internal var txtDepartment   : TextView
        internal var txtDesignation   : TextView
        internal var txtsino         : TextView
        internal var llemployee    : LinearLayout
        init {
            txtEmployee          = v.findViewById<View>(R.id.txtEmployee) as TextView
            txtDepartment          = v.findViewById<View>(R.id.txtDepartment) as TextView
            txtDesignation          = v.findViewById<View>(R.id.txtDesignation) as TextView
            txtsino                = v.findViewById<View>(R.id.txtsino) as TextView
            llemployee           = v.findViewById<View>(R.id.llemployee) as LinearLayout
        }
    }
    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}
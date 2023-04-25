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
import com.perfect.prodsuit.View.Activity.PickUpAndDeliveryActivity
import org.json.JSONArray
import org.json.JSONObject

class AreaListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "AreaListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_employee_all, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is AreaListAdapter.MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1
                holder.txtsino.text        = pos.toString()
                holder.tv_namee.text        = jsonObject!!.getString("EmpName")+ "  ("+(jsonObject!!.getString("DesignationName"))+")"
                holder.tvBranch.text        = jsonObject!!.getString("Branch")
                holder.llemployee!!.setTag(position)
                holder.llemployee!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "EmpName")
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
        internal var tvBranch   : TextView
        internal var tv_namee   : TextView
        internal var txtsino       : TextView
        internal var llemployee    : LinearLayout
        init {
            tvBranch          = v.findViewById<View>(R.id.tvBranch) as TextView
            tv_namee          = v.findViewById<View>(R.id.tv_namee) as TextView
            txtsino              = v.findViewById<View>(R.id.txtsino) as TextView
            llemployee           = v.findViewById<View>(R.id.llemployee) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: PickUpAndDeliveryActivity) {
        clickListener = itemClickListener
    }


}
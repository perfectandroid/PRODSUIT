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

class DepartmentAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "DepartmentAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_department, parent, false
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
                holder.txtDepartment.text        = jsonObject!!.getString("DeptName")

                if (position % 2 == 0){
                    holder.lldepartment!!.setBackgroundColor(context.getColor(R.color.greylight))
                }
                else{
                    holder.lldepartment!!.setBackgroundColor(context.getColor(R.color.white))
                }

                holder.lldepartment!!.setTag(position)
                holder.lldepartment!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "department")

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

        internal var txtDepartment   : TextView
        internal var txtsino         : TextView
        internal var lldepartment    : LinearLayout

        init {
            txtDepartment          = v.findViewById<View>(R.id.txtDepartment) as TextView
            txtsino                = v.findViewById<View>(R.id.txtsino) as TextView
            lldepartment           = v.findViewById<View>(R.id.lldepartment) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}
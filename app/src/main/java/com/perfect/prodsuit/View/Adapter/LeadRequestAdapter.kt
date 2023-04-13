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

class LeadRequestAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "LeadRequestAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_lead_request, parent, false
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
                holder.txtName.text         = jsonObject!!.getString("Name")
                holder.txtDepartment.text         = jsonObject!!.getString("DepartmentName")


                holder.llRequest!!.setTag(position)
                holder.llRequest!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(position, "LeadrequestClick")
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
        internal var txtName         : TextView
        internal var txtDepartment        : TextView
        internal var llRequest    : LinearLayout
        init {
            txtName         = v.findViewById<View>(R.id.txtName) as TextView
            txtDepartment        = v.findViewById<View>(R.id.txtDepartment) as TextView
            llRequest    = v.findViewById<View>(R.id.llRequest) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}
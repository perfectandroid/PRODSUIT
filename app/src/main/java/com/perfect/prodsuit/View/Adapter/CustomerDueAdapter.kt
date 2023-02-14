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

class CustomerDueAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "CustomerDueAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_customer_due, parent, false)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                holder.tv_AccountDetails.text        = jsonObject!!.getString("CategoryName")
                holder.tv_Balance.text      = jsonObject!!.getString("ID_Category")
                holder.tv_Due.text       = jsonObject!!.getString("Project")


//                holder.llCustomerDue!!.setTag(position)
//                holder.llCustomerDue!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "customerDue")
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
        internal var tv_AccountDetails    : TextView
        internal var tv_Balance  : TextView
        internal var tv_Due   : TextView
        internal var llCustomerDue       : LinearLayout
        init {
            tv_AccountDetails      = v.findViewById<View>(R.id.tv_AccountDetails) as TextView
            tv_Balance    = v.findViewById<View>(R.id.tv_Balance) as TextView
            tv_Due     = v.findViewById<View>(R.id.tv_Due) as TextView
            llCustomerDue         = v.findViewById<View>(R.id.llCustomerDue) as LinearLayout
        }
    }

//    fun setClickListener(itemClickListener: ItemClickListener?) {
//        clickListener = itemClickListener
//    }
}
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
import java.text.SimpleDateFormat

class UpcmngtaskListAdapter(internal var context: Context, internal var jsonArray: JSONArray):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "TodoListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_upcmng, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")

                var date = jsonObject!!.getString("LgLeadDate")
                var spf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
                val newDate = spf.parse(date)
                spf = SimpleDateFormat("dd-MM-yyyy")
                date = spf.format(newDate)
                println(date)
                Log.i("upcoming Date1", date)
                holder.txtv_dte1.text        = date
                holder.tv_custmr.text        = jsonObject!!.getString("LgCusName")
                holder.txtv_prdctnme.text        = jsonObject!!.getString("ProdName")
                holder.txtv_clct1.text        = jsonObject!!.getString("LgCollectedBy")
                holder.txtv_asgndto.text        = jsonObject!!.getString("AssignedTo")

                holder.llUpComung!!.setTag(position)
                holder.llUpComung!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "upcoming")
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
        internal var llUpComung   : LinearLayout
        internal var txtv_dte1   : TextView
        internal var tv_custmr       : TextView
        internal var txtv_prdctnme       : TextView
        internal var txtv_clct1       : TextView
        internal var txtv_asgndto       : TextView
        init {
            llUpComung          = v.findViewById<View>(R.id.llUpComung) as LinearLayout
            txtv_dte1          = v.findViewById<View>(R.id.txtv_dte1) as TextView
            tv_custmr              = v.findViewById<View>(R.id.tv_custmr) as TextView
            txtv_prdctnme           = v.findViewById<View>(R.id.txtv_prdctnme) as TextView
            txtv_clct1  = v.findViewById<View>(R.id.txtv_clct1) as TextView
            txtv_asgndto  = v.findViewById<View>(R.id.txtv_asgndto) as TextView
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}
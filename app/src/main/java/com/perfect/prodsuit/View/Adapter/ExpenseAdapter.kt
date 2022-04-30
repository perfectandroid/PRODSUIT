package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat

class ExpenseAdapter(internal var context: Context, internal var jsonArray: JSONArray):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ExpenseAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_expense, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {

                holder.txtv_transdate.text        = jsonObject!!.getString("TrnsDate")
                holder.txtv_amt.text        = "$ "+jsonObject!!.getString("Amount")
                holder.txtv_expnsnme.text        = jsonObject!!.getString("ExpenseName")

            /*    holder.lToDoList!!.setTag(position)
                holder.lToDoList!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "todolist")
                })*/
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   105   " + e.toString())
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

        internal var txtv_transdate   : TextView
        internal var txtv_amt       : TextView
        internal var txtv_expnsnme    : TextView


        init {

           txtv_transdate          = v.findViewById<View>(R.id.txtv_transdate) as TextView
           txtv_amt          = v.findViewById<View>(R.id.txtv_amt) as TextView
           txtv_expnsnme          = v.findViewById<View>(R.id.txtv_expnsnme) as TextView

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}
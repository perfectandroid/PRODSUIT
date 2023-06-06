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

class AssignedToAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "AssignedToAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_assigned_to, parent, false
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
                holder.txtsino.setText(""+pos)
                holder.txtName.setText(jsonObject!!.getString("EmpName"))
                holder.txtDesignationName.setText(jsonObject!!.getString("DesignationName"))

                holder.llAssignedTo!!.setTag(position)
                holder.llAssignedTo!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "AssignedToClick")
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

        internal var txtsino        : TextView
        internal var txtName        : TextView
        internal var txtDesignationName        : TextView
        internal var llAssignedTo    : LinearLayout
        init {
            txtsino                  = v.findViewById<View>(R.id.txtsino) as TextView
            txtName               = v.findViewById<View>(R.id.txtName) as TextView
            txtDesignationName               = v.findViewById<View>(R.id.txtDesignationName) as TextView
            llAssignedTo           = v.findViewById<View>(R.id.llAssignedTo) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}
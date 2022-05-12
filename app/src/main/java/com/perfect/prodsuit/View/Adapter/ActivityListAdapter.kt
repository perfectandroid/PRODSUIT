package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ActivityListAdapter(internal var context: Context, internal var jsonobj: JSONObject):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "activitylistAdapter"
    internal var jsonObject: JSONObject? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_activity_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonobj.getJSONObject(position.toString())
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
               // val pos = position+1
                val pos = position
                holder.txtDate.text     = jsonobj!!.getString("Date")
                holder.txtActntype.text     = jsonobj!!.getString("ActionType")
                holder.txtFollowup.text     = jsonobj!!.getString("FollowUpBy")
                holder.txtv_custmrremrk.text     = jsonobj!!.getString("CustomerRemark")
                holder.txtv_empremrk.text     = jsonobj!!.getString("EmployeeRemark")
                holder.txtStatus.text      = jsonobj!!.getString("Status")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }

  /*  override fun getItemCount(): Int {
        return jsonArray.length()
    }*/

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 0
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtDate      : TextView
        internal var txtActntype      : TextView
        internal var txtFollowup       : TextView
        internal var txtv_custmrremrk       : TextView
        internal var txtv_empremrk       : TextView
        internal var txtStatus       : TextView

        init {
            txtDate        = v.findViewById<View>(R.id.txtDate) as TextView
            txtActntype        = v.findViewById<View>(R.id.txtActntype) as TextView
            txtFollowup         = v.findViewById<View>(R.id.txtFollowup) as TextView
            txtv_custmrremrk         = v.findViewById<View>(R.id.txtv_custmrremrk) as TextView
            txtv_empremrk         = v.findViewById<View>(R.id.txtv_empremrk) as TextView
            txtFollowup         = v.findViewById<View>(R.id.txtFollowup) as TextView
            txtStatus         = v.findViewById<View>(R.id.txtStatus) as TextView

        }
    }

    override fun getItemCount(): Int {
        return 0
    }

}
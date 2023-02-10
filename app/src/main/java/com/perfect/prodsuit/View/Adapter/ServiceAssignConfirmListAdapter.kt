package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ServiceAssignConfirmListAdapter  (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "ServiceAssignConfirmListAdapter"
    internal var jsonObject: JSONObject? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_service_assign_confirm_list, parent, false)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1
                Log.e(TAG,"onBindViewHolder   331   ")
                holder.tv_Conf_Employee.text  = jsonObject!!.getString("Employee")
                holder.tv_Conf_Role.text      = jsonObject!!.getString("EmployeeType") // Role

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   3312   "+e.toString())
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

        internal var tv_Conf_Employee  : TextView
        internal var tv_Conf_Role   : TextView

        init {
            tv_Conf_Employee    = v.findViewById<View>(R.id.tv_Conf_Employee) as TextView
            tv_Conf_Role     = v.findViewById<View>(R.id.tv_Conf_Role) as TextView



        }
    }
}
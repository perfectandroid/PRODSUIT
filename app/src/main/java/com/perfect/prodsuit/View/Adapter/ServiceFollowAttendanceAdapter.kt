package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Model.ModelFollowUpAttendance
import com.perfect.prodsuit.R

class ServiceFollowAttendanceAdapter (internal var context: Context, internal var modelFollowUpAttendance: List<ModelFollowUpAttendance>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceFollowAttendanceAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_follow_attendance, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)  {
        try {
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position
                val ItemsModel = modelFollowUpAttendance[position]

                holder.tv_Employee.text = ItemsModel.EmployeeName
                holder.tv_Department.text = ItemsModel.Department
                holder.tv_Role.text = ItemsModel.Role

                if (ItemsModel.isChecked.equals("0")){
                    holder.checkbox.isChecked = false
                }else{
                    holder.checkbox.isChecked = true
                }

                holder.checkbox.setTag(position)
                holder.checkbox.setOnClickListener {
                    if (holder.checkbox.isChecked){
                        holder.checkbox.isChecked = true
                        ItemsModel.isChecked = "1"
                    }else{
                        holder.checkbox.isChecked = false
                        ItemsModel.isChecked = "0"
                    }

                }


            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }



    override fun getItemCount(): Int {
        return modelFollowUpAttendance.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var checkbox: CheckBox
        var tv_Employee: TextView
        var tv_Department: TextView
        var tv_Role: TextView


        init {
            checkbox = v.findViewById(R.id.checkbox) as CheckBox
            tv_Employee = v.findViewById(R.id.tv_Employee) as TextView
            tv_Department = v.findViewById(R.id.tv_Department) as TextView
            tv_Role = v.findViewById(R.id.tv_Role) as TextView

        }
    }

}
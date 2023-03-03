package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Model.AttendanceFollowUpModel
import com.perfect.prodsuit.Model.ReplacedProductCostModel
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class AttendanceServiceFollowUpAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    val attendanceFollowUpArrayList: ArrayList<AttendanceFollowUpModel>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_attendance_followup, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            var jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                val pos = position
                holder.tv_name.text = jsonObject!!.getString("name")
                holder.tv_department.text = jsonObject!!.getString("department")
                holder.tv_role.text = jsonObject!!.getString("role")
                if (jsonObject!!.getString("isChecked").equals("true")) {
                    holder.checkbox.isChecked = true
                } else {
                    holder.checkbox.isChecked = false
                }
                holder.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                    attendanceFollowUpArrayList.removeAt(pos)
                    attendanceFollowUpArrayList.add(pos,
                        AttendanceFollowUpModel(
                            jsonObject!!.getString("name"),
                            jsonObject!!.getString("department"),
                            jsonObject!!.getString("role"),
                            "" + b
                        )
                    )
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.v("sadsdsssss", "e  " + e)
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
        var tv_name: TextView
        var tv_department: TextView
        var tv_role: TextView
        var checkbox: CheckBox

        init {
            tv_name = v.findViewById(R.id.tv_name) as TextView
            tv_department = v.findViewById(R.id.tv_department) as TextView
            tv_role = v.findViewById(R.id.tv_role) as TextView
            checkbox = v.findViewById(R.id.checkbox) as CheckBox
        }
    }

    fun getAttendance(): ArrayList<AttendanceFollowUpModel> {
        val attendanceFollowUpArrayListFinal = ArrayList<AttendanceFollowUpModel>()
        for(i in 0..attendanceFollowUpArrayList.size-1)
        {
            var getList:AttendanceFollowUpModel=attendanceFollowUpArrayList.get(i)
            Log.v("dsad33ffdf","isChecked "+getList.isChecked)
            if(getList.isChecked.equals("true"))
            {
                var name=getList.name
                var department=getList.department
                var role=getList.role
                var isChecked=getList.isChecked
                attendanceFollowUpArrayListFinal.add(AttendanceFollowUpModel(
                    name,
                    department,
                    role,
                    isChecked
                    )
                )
            }
        }
        return attendanceFollowUpArrayListFinal

    }

}
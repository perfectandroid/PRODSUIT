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
import com.perfect.prodsuit.Model.HistoryFollowUpModel
import com.perfect.prodsuit.Model.ReplacedProductCostModel
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class HistoryServiceFollowUpAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_followup_history_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            var jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                holder.tv_ticketNumber.text = jsonObject!!.getString("TicketNo")
                holder.tv_service.text = jsonObject!!.getString("Service")
                holder.tv_complaint.text = jsonObject!!.getString("Complaint")
                holder.tv_status.text = jsonObject!!.getString("CurrentStatus")
                holder.tv_closedDate.text = jsonObject!!.getString("ClosedDate")
                holder.tv_employeeNote.text = jsonObject!!.getString("EmployeeNotes")

            }
        } catch (e: Exception) {
            e.printStackTrace()
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
        var tv_ticketNumber: TextView
        var tv_service: TextView
        var tv_complaint: TextView
        var tv_status: TextView
        var tv_closedDate: TextView
        var tv_employeeNote: TextView

        init {
            tv_ticketNumber = v.findViewById(R.id.tv_ticketNumber) as TextView
            tv_service = v.findViewById(R.id.tv_service) as TextView
            tv_complaint = v.findViewById(R.id.tv_complaint) as TextView
            tv_status = v.findViewById(R.id.tv_status) as TextView
            tv_closedDate = v.findViewById(R.id.tv_closedDate) as TextView
            tv_employeeNote = v.findViewById(R.id.tv_employeeNote) as TextView
        }
    }

}
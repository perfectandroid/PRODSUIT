package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ServiceCostAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    val serviceCostArrayList: ArrayList<ServiceCostModelMain>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "HistoryActMeetingAdapter"
    internal var jsonObject: JSONObject? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_cost_followup, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                var isChecked = ""
                Log.e(TAG, "onBindViewHolder   1051   ")
                val pos = position
                holder.tv_component.text = jsonObject!!.getString("Components")
                holder.tv_service_name.text = jsonObject!!.getString("ServiceName")
                holder.edtServiceCost.setText(jsonObject!!.getString("serviceCost"))
                holder.edtRemarks.setText(jsonObject!!.getString("remark"))
                isChecked = "true"
                val serviceTypeMain =
                    arrayOf<String>("Paid Service", "Warranty Service", "AMC Service")
                var searchType = Array<String>(3) { "" }
                searchType[0] = jsonObject!!.getString("serviceType")
                Log.v("dfdfddddd", "searchType[0]  " + searchType[0])
                var i = 1;
                for (x in serviceTypeMain) {
                    Log.v("dfdfddddd", "value  " + x)
                    if (searchType.contains(x)) {
                        Log.v("dfdfddddd", "yes  ")
                    } else {
                        Log.v("dfdfddddd", "no  ")
                        searchType[i] = x
                        i = i + 1
                    }
                }
                val adapter =
                    ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, searchType)
                holder.edtServiceType!!.setAdapter(adapter)
                holder.edtServiceType!!.setText(searchType.get(0), false)
                holder.edtServiceType!!.setOnClickListener {
                    holder.edtServiceType!!.showDropDown()
                }
                holder.edtServiceType!!.setOnItemClickListener { parent, view, position, id ->
                    serviceCostArrayList.removeAt(pos)
                    serviceCostArrayList.add(
                        pos,
                        ServiceCostModelMain(
                            jsonObject!!.getString("Components"),
                            jsonObject!!.getString("ServiceName"),
                            holder.edtServiceCost.text.toString(),
                            holder.edtServiceType.text.toString(),
                            holder.edtRemarks.text.toString(),
                            isChecked
                        )
                    )
                }


                holder.edtServiceCost.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(s: Editable) {
                        serviceCostArrayList.removeAt(pos)
                        serviceCostArrayList.add(
                            pos,
                            ServiceCostModelMain(
                                jsonObject!!.getString("Components"),
                                jsonObject!!.getString("ServiceName"),
                                holder.edtServiceCost.text.toString(),
                                jsonObject!!.getString("serviceType"),
                                holder.edtRemarks.text.toString(),
                                isChecked
                            )
                        )
                    }

                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int,
                        before: Int, count: Int
                    ) {
                    }
                })
                holder.edtServiceType.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        serviceCostArrayList.removeAt(pos)
                        serviceCostArrayList.add(
                            pos,
                            ServiceCostModelMain(
                                jsonObject!!.getString("Components"),
                                jsonObject!!.getString("ServiceName"),
                                holder.edtServiceCost.text.toString(),
                                s.toString(),
                                holder.edtRemarks.text.toString(),
                                isChecked
                            )
                        )
                    }

                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int,
                        before: Int, count: Int
                    ) {
                    }
                })
                holder.edtRemarks.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(s: Editable) {
                        serviceCostArrayList.removeAt(pos)
                        serviceCostArrayList.add(
                            pos,
                            ServiceCostModelMain(
                                jsonObject!!.getString("Components"),
                                jsonObject!!.getString("ServiceName"),
                                holder.edtServiceCost.text.toString(),
                                jsonObject!!.getString("serviceType"),
                                s.toString(),
                                isChecked
                            )
                        )
                    }

                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int,
                        before: Int, count: Int
                    ) {
                    }
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
        var tv_component: TextView
        var tv_service_name: TextView
        var edtServiceCost: EditText
        var edtRemarks: EditText
        var checkbox: CheckBox
        var edtServiceType: AutoCompleteTextView

        init {
            tv_component = v.findViewById(R.id.tv_component) as TextView
            tv_service_name = v.findViewById(R.id.tv_service_name) as TextView
            edtServiceCost = v.findViewById(R.id.edtServiceCost) as EditText
            edtRemarks = v.findViewById(R.id.edtRemarks) as EditText
            edtServiceType = v.findViewById(R.id.edtServiceType) as AutoCompleteTextView
            checkbox = v.findViewById(R.id.checkbox) as CheckBox
        }
    }

    fun getServiceCost(): ArrayList<ServiceCostModelMain> {
        val serviceCostArrayListFinal = ArrayList<ServiceCostModelMain>()
        for (i in 0..serviceCostArrayList.size - 1) {
            var getList: ServiceCostModelMain = serviceCostArrayList.get(i)
            Log.v("dsad33ffdf", "isChecked " + getList.isChecked)
            if (getList.isChecked.equals("true")) {
                var Components = getList.Components
                var ServiceName = getList.ServiceName
                var serviceCost = getList.serviceCost
                var serviceType = getList.serviceType
                var remark = getList.remark
                var isChecked = getList.isChecked
                serviceCostArrayListFinal.add(
                    ServiceCostModelMain(
                        Components,
                        ServiceName,
                        serviceCost,
                        serviceType,
                        remark,
                        isChecked
                    )
                )
            }
        }

        return serviceCostArrayListFinal
    }

}